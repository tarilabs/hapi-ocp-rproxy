package org.drools;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpClient.Redirect;
import java.net.http.HttpClient.Version;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Duration;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@ApplicationScoped
@Path("/hl7v2")
public class HL7v2Resource {

    @POST
    @Path("new")
    @Consumes({ "application/hl7-v2", MediaType.TEXT_PLAIN })
    @Produces("application/hl7-v2")
    public String post(String hl7v2Message) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newBuilder()
                .version(Version.HTTP_1_1)
                .followRedirects(Redirect.ALWAYS)
                .build();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(
                        "http://quarkus-content-based-routing-mmortari-dev.apps.sandbox-m2.ll9k.p1.openshiftapps.com/hl7v2/new"))
                .timeout(Duration.ofSeconds(10))
                .header("Content-Type", "application/hl7-v2")
                .POST(BodyPublishers.ofString(hl7v2Message))
                .build();
        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
        System.out.println(response.statusCode());
        System.out.println(response.body());

        return response.body();
    }
}