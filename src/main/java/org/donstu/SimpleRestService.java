package org.donstu;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpServer;
import org.donstu.domain.Movie;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SimpleRestService {
    private static final int PORT = 8090;
    private static final int OK = 200;
    private static final int NOT_ALLOWED = 405;
    private static final int NOT_FOUND = 404;

    private static List<Movie> movies = new ArrayList<>();

    static {
        movies.add(new Movie("Goodfellas", new Date(), 225, "First"));
        movies.add(new Movie("The Shawshank Redemption", new Date(), 222, "Second"));
        movies.add(new Movie("12 Angry Men", new Date(), 136, "Second"));
        movies.add(new Movie("It's a Wonderful Life", new Date(), 210, "First"));
    }

    public static void main(String[] args) {
        try {
            HttpServer httpServer = HttpServer.create(new InetSocketAddress(PORT), 0);
            httpServer.createContext("/movie/list", httpExchange -> {
                if ("GET".equals(httpExchange.getRequestMethod())) {
                    httpExchange.getResponseHeaders().set("Content-Type", "application/json");
                    httpExchange.sendResponseHeaders(OK, 0);
                    ObjectMapper mapper = new ObjectMapper();
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    mapper.writeValue(baos, movies);
                    byte[] body = baos.toByteArray();
                    OutputStream os = httpExchange.getResponseBody();
                    os.write(body);
                    os.close();
                } else {
                    httpExchange.sendResponseHeaders(NOT_ALLOWED, -1);
                }
            });

            httpServer.createContext("/movie/book", httpExchange -> {
                if ("GET".equals(httpExchange.getRequestMethod())) {
                    String[] requestParts = httpExchange.getRequestURI().getPath().split("/");
                    if (requestParts.length == 4) {
                        String bookName = requestParts[3];
                        Movie foundMovie = null;
                        for (Movie movie : movies) {
                            if (movie.getTitle().equalsIgnoreCase(bookName)) {
                                foundMovie = movie;
                                break;
                            }
                        }
                        if (foundMovie != null) {
                            httpExchange.getResponseHeaders().set("Content-Type", "application/json");
                            httpExchange.sendResponseHeaders(OK, 0);
                            ObjectMapper mapper = new ObjectMapper();
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            mapper.writeValue(baos, foundMovie);
                            byte[] body = baos.toByteArray();
                            OutputStream os = httpExchange.getResponseBody();
                            os.write(body);
                            os.close();
                        } else {
                            httpExchange.getResponseHeaders().set("Content-Type", "application/json");
                            httpExchange.sendResponseHeaders(OK, 0);
                            OutputStream os = httpExchange.getResponseBody();
                            os.write("{}".getBytes());
                            os.close();
                        }
                    } else {
                        httpExchange.sendResponseHeaders(NOT_FOUND, -1);
                    }
                } else {
                    httpExchange.sendResponseHeaders(NOT_ALLOWED, -1);
                }
            });

            httpServer.setExecutor(null);
            httpServer.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
