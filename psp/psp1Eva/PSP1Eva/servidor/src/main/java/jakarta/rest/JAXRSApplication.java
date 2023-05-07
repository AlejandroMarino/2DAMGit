package jakarta.rest;


import common.Constants;
import jakarta.annotation.security.DeclareRoles;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

@ApplicationPath("/warehouse")
@DeclareRoles({Constants.ADMIN, Constants.USER})
public class JAXRSApplication extends Application {


}
