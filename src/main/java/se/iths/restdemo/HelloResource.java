package se.iths.restdemo;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/hello-world")
public class HelloResource {
    @GET
    @Produces("application/json")
    public String hello() {
        return "Hello, World!";
    }
    @GET
    @Path("status")
    public Response test(){
        return Response.status(401)
                .header("Custom","Martin")
                .entity("Hello World")
                .build();
    }
}
