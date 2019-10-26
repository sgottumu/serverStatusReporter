package server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.Callable;

public class ServerPollCallable implements Callable {

    String url;
    public static final Logger logger = LoggerFactory.getLogger(ServerPollCallable.class);

    public ServerPollCallable(String url) {
        this.url = url;
    }

    public String call() {
        try {
            logger.debug("Polling server {}", url);
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            logger.info("Status received for {}", url);
            return response.body();
        }
        catch (Exception e){
            logger.error("Error making request for {}", url, e);
            return null;
        }
    }
}
