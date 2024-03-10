package org.donstu;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class SimpleRestClient {
    public static void main(String[] args) {
        HttpClient client = new DefaultHttpClient();
        HttpGet request = new HttpGet("http://localhost:8090/movie/list");
        HttpResponse response = null;

        try {
            response = client.execute(request);
            BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            String line = "";
            while (true) {
                if ((line = reader.readLine()) == null) {
                    break;
                }
                System.out.println(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
