package com.example.a76952.login2.network;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class HttpsConnect {

    public static void sendHttpRequest(
            final String address,
            final String method,
            final JSONObject jsonData,
            final HttpCallBackListener listener) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpsURLConnection connection = null;
                try {
                    URL url = new URL(address);
                    connection = (HttpsURLConnection)url.openConnection();
                    connection.setRequestMethod(method);
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    connection.setDoInput(true);
                    connection.setDoOutput(true);
                    connection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                    connection.setRequestProperty("Accept", "application/json");
                    //connection.setRequestProperty("Accept-Charset", "UTF-8");
                    DataOutputStream ostream = new DataOutputStream(connection.getOutputStream());
                    //ostream.writeBytes(jsonData.toString());
                    ostream.write(jsonData.toString().getBytes());
                    InputStream istream = connection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(istream));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    if (listener != null) {
                        listener.success(response.toString());
                    }
                } catch (Exception exception) {
                    if (listener != null) {
                        listener.error(exception);
                    }
                } finally {
                    if (connection != null) {
                        connection.disconnect();
                    }
                }
            }
        }).start();
    }
}
