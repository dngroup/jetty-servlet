package net.viotech.jettyservlet;

import android.util.Log;




import org.eclipse.jetty.server.Server;
import org.glassfish.jersey.jetty.JettyHttpContainerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.io.IOException;
import java.net.URI;

import javax.ws.rs.ProcessingException;
import javax.ws.rs.core.UriBuilder;

public class JettyServerRunner {

    private Server jettyServer;

    private static int getPort(int defaultPort) {
        // grab port from environment, otherwise fall back to default port 9998
        return CliConfSingleton.port;
    }

    private static URI getBaseURI() {
        String ip = CliConfSingleton.ip;
        return UriBuilder.fromUri("http://" + ip + "/")
                .port(getPort(CliConfSingleton.port)).build();
    }

    protected static Server startServer() throws IOException {

        ResourceConfig resources = new ResourceConfig();
        resources.packages("net.viotech.jettyservlet");
        
   
 

        return JettyHttpContainerFactory.createServer(getBaseURI(), resources);
    }
    public void start() throws Exception {

        CliConfSingleton.defaultValue();
        Log.i("JettyServer", "The box ID is: " + CliConfSingleton.boxID);

        jettyServer = startServer();
        try {
            jettyServer.start();
            Log.d("JettyServer", "Server started");
        } catch (Exception e) {
            Log.d("JettyServer", "Error starting server: " + e.getLocalizedMessage());
        }
    }

    public void stop() throws Exception {
        if (jettyServer != null) {
            jettyServer.stop();
            Log.d("JettyServer", "Server stopped");
        }
    }

}
