package site.elven.planning.common.util.http;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.BiConsumer;

public class HttpUtils {

    public final static long DEFAULT_TIMEOUT = -1;

    public static enum Method {
        GET, POST, HEAD, OPTIONS, PUT, DELETE, TRACE, CONNECT, PATCH
    }

    public static HttpClient createClient() {
        return createClient(DEFAULT_TIMEOUT);
    }

    public static HttpClient createClient(long connectTimeout) {
        HttpClient.Builder builder = HttpClient.newBuilder();
        if(connectTimeout > 0){
            builder.connectTimeout(Duration.ofMillis(connectTimeout));
        }
        return builder.build();
    }

    public static HttpRequest createGetRequest(String url) {
        return createGetRequest(url, DEFAULT_TIMEOUT);
    }

    public static HttpRequest createGetRequest(String url, long timeout) {
        HttpRequest.Builder builder = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET();

        if(timeout > 0){
            builder.timeout(Duration.ofMillis(timeout));
        }

        return builder.build();
    }

    public static HttpRequest createPostRequest(String url, Map<String, Object> params, long timeout) {
        HttpRequest.Builder builder = HttpRequest.newBuilder()
                .uri(URI.create(url));

        builder.header("Content-Type", "application/x-www-form-urlencoded");
        if(params != null){
            builder.POST(HttpRequest.BodyPublishers.ofString(
                    params.entrySet().stream()
                            .map(entry -> entry.getKey() + "=" + entry.getValue())
                            .reduce((a, b) -> a + "&" + b)
                            .orElse("")
            ));
        }
        else {
            builder.POST(HttpRequest.BodyPublishers.noBody());
        }

        if(timeout > 0){
            builder.timeout(Duration.ofMillis(timeout));
        }

        return builder.build();
    }

    public static HttpRequest createRequest(String url, Method method, Map<String, String> headers, Map<String, Object> params, String json, long timeout) {
        HttpRequest.Builder builder = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .method(method.name(), HttpRequest.BodyPublishers.noBody());

        if(headers != null){
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                builder.header(entry.getKey(), entry.getValue());
            }
        }

        if(params != null){
            builder.header("Content-Type", "application/x-www-form-urlencoded");
            String queryParams = params.entrySet().stream()
                    .map(entry -> entry.getKey() + "=" + entry.getValue())
                    .reduce((a, b) -> a + "&" + b)
                    .orElse("");
            switch (method) {
                case POST, PUT, PATCH -> builder.POST(HttpRequest.BodyPublishers.ofString(
                        queryParams
                ));
                case GET, HEAD, OPTIONS, DELETE, TRACE, CONNECT -> {
                    if (url.contains("?")){
                        builder.uri(URI.create(url + "&" + queryParams));
                    }
                    else {
                        builder.uri(URI.create(url + "?" + queryParams));
                    }
                }
            }
        }

        if(json != null){
            builder.header("Content-Type", "application/json");
            builder.POST(HttpRequest.BodyPublishers.ofString(json));
        }

        if(timeout > 0){
            builder.timeout(Duration.ofMillis(timeout));
        }

        return builder.build();
    }

    public static HttpRequest createPostRequest(String url, String json) {
        return createPostRequest(url, json, DEFAULT_TIMEOUT);
    }

    public static HttpRequest createPostRequest(String url, String json, long timeout) {
        HttpRequest.Builder builder = HttpRequest.newBuilder()
                .uri(URI.create(url));
        builder.header("Content-Type", "application/json");
        if(json != null){
            builder.POST(HttpRequest.BodyPublishers.ofString(json));
        }
        else {
            builder.POST(HttpRequest.BodyPublishers.noBody());
        }

        if(timeout > 0){
            builder.timeout(Duration.ofMillis(timeout));
        }

        return builder.build();
    }

    public static String get(String url) throws IOException, InterruptedException, ExecutionException {
        return get(url, DEFAULT_TIMEOUT ,DEFAULT_TIMEOUT);
    }

    public static String get(String url, long connectTimeout, long readTimeout) throws IOException, InterruptedException, ExecutionException {
        return get(url, false, null, connectTimeout, readTimeout);
    }

    public static String get(String url, boolean async, BiConsumer<HttpResponse<String>, Throwable> action) throws IOException, InterruptedException, ExecutionException {
        return get(url, async, action, DEFAULT_TIMEOUT, DEFAULT_TIMEOUT);
    }

    public static String get(String url, boolean async, BiConsumer<HttpResponse<String>, Throwable> action, long connectTimeout, long readTimeout) throws IOException, InterruptedException, ExecutionException {
        HttpClient client = createClient(connectTimeout);
        HttpRequest request = createGetRequest(url, readTimeout);
        return send(client, request, async, action);
    }

    public static String post(String url, Map<String, Object> params) throws IOException, InterruptedException {
        return post(url, params, DEFAULT_TIMEOUT, DEFAULT_TIMEOUT);
    }

    public static String post(String url, Map<String, Object> params, long connectTimeout, long readTimeout) throws IOException, InterruptedException {
        return post(url, params, null, false, null, connectTimeout, readTimeout);
    }

    public static String post(String url, String json) throws IOException, InterruptedException {
        return post(url, json, DEFAULT_TIMEOUT, DEFAULT_TIMEOUT);
    }

    public static String post(String url, String json, boolean async, BiConsumer<HttpResponse<String>, Throwable> action) throws IOException, InterruptedException {
        return post(url, json, async, action, DEFAULT_TIMEOUT, DEFAULT_TIMEOUT);
    }

    public static String post(String url, String json, long connectTimeout, long readTimeout) throws IOException, InterruptedException {
        return post(url, json, false, null, connectTimeout, readTimeout);
    }

    public static String post(String url, String json, boolean async, BiConsumer<HttpResponse<String>, Throwable> action, long connectTimeout, long readTimeout) throws IOException, InterruptedException {
        return post(url, null, json, async, action, connectTimeout, readTimeout);
    }

    public static String post(String url, Map<String, Object> params, String json, boolean async, BiConsumer<HttpResponse<String>, Throwable> action, long connectTimeout, long readTimeout) throws IOException, InterruptedException {
        HttpClient client = createClient(connectTimeout);

        HttpRequest request;
        if (params != null){
            request = createPostRequest(url, params, readTimeout);
        }
        else {
            request = createPostRequest(url, json, readTimeout);
        }

        return send(client, request, async, action);
    }

    public static String send(HttpClient client, HttpRequest request, boolean async, BiConsumer<HttpResponse<String>, Throwable> action) throws IOException, InterruptedException {
        if(async){
            CompletableFuture<HttpResponse<String>> future = client.sendAsync(request, HttpResponse.BodyHandlers.ofString());
            if (action != null){
                future.whenComplete(action);
            }
            return null;
        }
        else {
            return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
        }
    }

}
