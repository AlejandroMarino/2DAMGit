package jakarta.filters;


import common.ConstantsErrors;
import jakarta.common.Constants;
import jakarta.common.ConstantsAttributes;
import jakarta.common.ConstantsRoles;
import jakarta.inject.Inject;
import jakarta.keyconfig.KeyConfig;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import jakarta.ws.rs.ext.Provider;
import model.error.ApiError;

import java.time.LocalDateTime;

@Provider
@MaxRequestsForUserFilter
public class FilterRoleUser implements ContainerRequestFilter {
    private final KeyConfig keyConfig;
    @Context
    private HttpServletRequest request;

    @Inject
    public FilterRoleUser(KeyConfig keyConfig) {
        this.keyConfig = keyConfig;
    }

    @Override
    public void filter(ContainerRequestContext containerRequestContext) {
        SecurityContext securityContext = containerRequestContext.getSecurityContext();
        if (securityContext.isUserInRole(ConstantsRoles.ROL_USER)) {
            Integer numRequests = getNumRequests();
            request.getSession().setAttribute(ConstantsAttributes.ATTRIBUTE_NUM_REQUESTS, numRequests);

            LocalDateTime timeLimit = (LocalDateTime) request.getSession().getAttribute(ConstantsAttributes.ATTRIBUTE_TIME_REQUESTS);
            int maxRequests = Integer.parseInt(keyConfig.getProperty(Constants.PROPERTY_NUM_REQUESTS));

            if (timeLimit.isBefore(LocalDateTime.now())) {
                resetAttributes();
            } else if (numRequests > maxRequests) {
                containerRequestContext.abortWith(Response.status(Response.Status.FORBIDDEN)
                        .entity(new ApiError(ConstantsErrors.TOO_MANY_REQUESTS, LocalDateTime.now()))
                        .type(MediaType.APPLICATION_JSON_TYPE).build());
            }
        }
    }

    private Integer getNumRequests() {
        Integer numRequests = (Integer) request.getSession().getAttribute(ConstantsAttributes.ATTRIBUTE_NUM_REQUESTS);
        if (numRequests != null) {
            numRequests += 1;
        } else {
            numRequests = 1;
            int secsLimit = Integer.parseInt(keyConfig.getProperty(Constants.PROPERTY_TIME_SEC));
            request.getSession().setAttribute(ConstantsAttributes.ATTRIBUTE_TIME_REQUESTS
                    , LocalDateTime.now().plusSeconds(secsLimit));
        }
        return numRequests;
    }

    private void resetAttributes() {
        request.getSession().removeAttribute(ConstantsAttributes.ATTRIBUTE_NUM_REQUESTS);
        request.getSession().removeAttribute(ConstantsAttributes.ATTRIBUTE_TIME_REQUESTS);
    }
}