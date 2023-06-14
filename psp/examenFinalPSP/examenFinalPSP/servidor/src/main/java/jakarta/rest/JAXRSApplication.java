package jakarta.rest;


import jakarta.annotation.security.DeclareRoles;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

@ApplicationPath("/manga")
@DeclareRoles({"ARMAS", "Series"})
public class JAXRSApplication extends Application {


}
