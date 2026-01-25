package com.server;

public class Main {
    public static void main(String[] args) {
        if (args.length < 1) return;
        int port = Integer.parseInt(args[0]);
        try (java.net.ServerSocket ss = new java.net.ServerSocket(port)) {
            while (true) {
                try (java.net.Socket s = ss.accept()) {
                    java.io.DataInputStream in = new java.io.DataInputStream(s.getInputStream());
                    java.io.OutputStream out = s.getOutputStream();
                    while (true) {
                        int len;
                        try {
                            len = in.readInt();
                        } catch (java.io.EOFException e) {
                            break;
                        }
                        byte[] buf = new byte[len];
                        in.readFully(buf);
                        java.time.format.DateTimeFormatter f = java.time.format.DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss");
                        String resp = java.time.ZonedDateTime.now().format(f) + "\n";
                        out.write(resp.getBytes(java.nio.charset.StandardCharsets.UTF_8));
                        out.flush();
                    }
                } catch (Exception ignored) {}
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}