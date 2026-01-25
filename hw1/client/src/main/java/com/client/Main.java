package com.client;

public class Main {
    public static void main(String[] args) {
        if (args.length < 5) return;
        String ip = args[0];
        int port = Integer.parseInt(args[1]);
        int N = Integer.parseInt(args[2]);
        int M = Integer.parseInt(args[3]);
        int Q = Integer.parseInt(args[4]);
        try (java.net.Socket s = new java.net.Socket(ip, port)) {
            s.setTcpNoDelay(true);
            java.io.DataOutputStream out = new java.io.DataOutputStream(s.getOutputStream());
            java.io.BufferedReader in = new java.io.BufferedReader(new java.io.InputStreamReader(s.getInputStream(), java.nio.charset.StandardCharsets.UTF_8));
            java.util.Random rnd = new java.util.Random();
            java.io.FileWriter fw = new java.io.FileWriter("results.csv");
            fw.write("bytes,avg_ms\n");
            for (int k = 0; k < M; k++) {
                int size = N * k + 8;
                long sum = 0;
                for (int q = 0; q < Q; q++) {
                    byte[] buf = new byte[size];
                    rnd.nextBytes(buf);
                    long t0 = System.currentTimeMillis();
                    out.writeInt(buf.length);
                    out.write(buf);
                    out.flush();
                    String resp = in.readLine();
                    long t1 = System.currentTimeMillis();
                    sum += (t1 - t0);
                }
                long avg = sum / Q;
                fw.write(size + "," + avg + "\n");
            }
            fw.flush();
            fw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}