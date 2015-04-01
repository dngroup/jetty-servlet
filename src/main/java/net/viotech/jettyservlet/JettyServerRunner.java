package net.viotech.jettyservlet;

import android.util.Log;

import com.enseirb.telecom.dngroup.dvd2c.CORSResponseFilter;
import com.enseirb.telecom.dngroup.dvd2c.CliConfSingleton;
import com.enseirb.telecom.dngroup.dvd2c.SecurityRequestFilter;
import com.enseirb.telecom.dngroup.dvd2c.db.BoxRepositoryMongo;
import com.enseirb.telecom.dngroup.dvd2c.service.BoxService;
import com.enseirb.telecom.dngroup.dvd2c.service.BoxServiceImpl;

import org.eclipse.jetty.server.Server;
import org.glassfish.jersey.jettison.JettisonFeature;
import org.glassfish.jersey.jetty.JettyHttpContainerFactory;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.message.filtering.SecurityEntityFilteringFeature;
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
        resources.packages("com.enseirb.telecom.dngroup.dvd2c.endpoints");
        resources.register(CORSResponseFilter.class);
        resources.register(MultiPartFeature.class);
        resources.register(JettisonFeature.class);
        /**
         * this two follow line is for security
         */
        resources.register(SecurityEntityFilteringFeature.class);
        resources.register(SecurityRequestFilter.class);


        // return GrizzlyServerFactory.createHttpServer(BASE_URI,
        // resourceConfig);
        Log.i("JettyServer", "Send information to the server central ...");
        try {
            BoxService boxManager = new BoxServiceImpl(new BoxRepositoryMongo("mediahome"));
            boxManager.updateBox();
            Log.i("JettyServer", "Sucess ");
        } catch (ProcessingException e) {
            Log.e("JettyServer", "Error for send information to the server central. Is running? " + e.getLocalizedMessage());
        } catch (Exception e) {
            Log.e("JettyServer", "Error for send information to the server central: " + e.getLocalizedMessage());

        }

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
