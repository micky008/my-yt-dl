package com.msc.my.yt.dl2;

import com.msc.my.yt.dl2.YtDLP.YtDLP_GetInfo;
import java.net.URI;
import javax.ws.rs.core.UriBuilder;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

/**
 *
 * @author mchinchole
 */
public class MyYtDl2 {

    public static void main(String[] args) {
        Config.init();
        URI baseUri = UriBuilder.fromUri("http://localhost/").port(Config.getInstance().getPort()).build();
        ResourceConfig config = new ResourceConfig();
        config.packages(true, "com.msc.my.yt.dl2.controller");
        HttpServer server = GrizzlyHttpServerFactory.createHttpServer(baseUri, config, true);
    }
}
