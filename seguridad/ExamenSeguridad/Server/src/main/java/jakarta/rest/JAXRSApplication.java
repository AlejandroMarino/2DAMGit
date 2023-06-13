package jakarta.rest;

import common.ConstantsURL;
import jakarta.annotation.security.DeclareRoles;
import jakarta.common.ConstantsRoles;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

@ApplicationPath(ConstantsURL.PATH_API)
@DeclareRoles({"raton", "curioso", "biologo", "informe", "nivel1", "nivel2", "espia"})
public class JAXRSApplication extends Application {
}