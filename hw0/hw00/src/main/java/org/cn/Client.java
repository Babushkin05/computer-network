package org.cn;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

public class Client {

    private static final String HOST = "127.0.0.1";
    private static final int PORT = 10000;

    public static void main(String[] args) {

        try (Socket socket = new Socket(HOST, PORT)) {
            System.out.println("Connected to server");

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream())
            );
            BufferedWriter out = new BufferedWriter(
                    new OutputStreamWriter(socket.getOutputStream())
            );

            int counter = 1;
            while (true) {
                String message = "Hello server " + counter++;
                out.write(message);
                out.newLine();
                out.flush();

                String response = in.readLine();
                System.out.println(response);

                TimeUnit.SECONDS.sleep(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
