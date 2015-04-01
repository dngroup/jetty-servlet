package net.viotech.jettyservlet;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

@Path("myresource")
public class MyResource {

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @POST
    @Consumes("text/plain")
    public Response getIt(String message) {
        return Response.accepted().header("X-MEDIA-HOME-PLAIN", message).build();
    }
    
    @POST
    @Consumes("*/*")
    public Response getIt2(String message) {
    	return Response.accepted().header("X-MEDIA-HOME-STREAM", message).build();
    }
    
    
}