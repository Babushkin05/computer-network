package org.cn;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Server {

    private static final int PORT = 10000;
    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss");

    public static void main(String[] args) {
        System.out.println("Server started on port " + PORT);

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            Socket socket = serverSocket.accept();
            System.out.println("Client connected");

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream())
            );
            BufferedWriter out = new BufferedWriter(
                    new OutputStreamWriter(socket.getOutputStream())
            );

            String clientMessage;
            while ((clientMessage = in.readLine()) != null) {
                System.out.println("Received from client: " + clientMessage);

                String dateTime = LocalDateTime.now().format(FORMATTER);
                out.write(dateTime);
                out.newLine();
                out.flush();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
