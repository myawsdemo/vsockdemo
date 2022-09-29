package com.myawsdemo.vsockserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.nio.charset.StandardCharsets;

@SpringBootApplication
public class VsockserverApplication {

    public static void main(String[] args) {
        SpringApplication.run(VsockserverApplication.class, args);
        /*try {
            ServerVSock server = new ServerVSock();
            try (server) {
                server.bind(new VSockAddress(VSockAddress.VMADDR_CID_ANY, 5000));
                System.out.println("Bound on Cid: " + server.getLocalCid());
                while (true) {
                    try (VSock peerVSock = server.accept()) {
                        byte[] b = new byte[4096];
                        peerVSock.getInputStream().read(b, 0, 4096);
                        peerVSock.getOutputStream()
                                .write(("Got: " + new String(b, StandardCharsets.UTF_8)).getBytes(StandardCharsets.UTF_8));
                    } catch (Exception ex) {
                        System.out.println("Error: " + ex.getMessage());
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        byte[] buffer = "Test vsock protocol".getBytes(StandardCharsets.UTF_8);
        int cid = 5, port = 5000;
        System.out.printf("open(%d, %d)\n", cid, port);
        long vsock = VSock.open();
        long error = VSock.connect(vsock, cid, port);
        System.out.printf("connect returned %d\n", error);

        error = VSock.send(vsock, buffer, 0, buffer.length);
        System.out.printf("send returned %d\n", error);
        byte[] received = VSock.receive(vsock, 5);
        System.out.println(encode(received));
    }

    static private char[] hexCode = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    static public int hexToBin(char ch) {
        if (ch >= '0' && ch <= '9') return ch - '0';
        else if (ch >= 'a' && ch <= 'f') return ch - 'a' + 10;
        else if (ch >= 'A' && ch <= 'F') return ch - 'A' + 10;
        return 0;
    }

    static public byte[] decode(String hex) {
        int offset = 0;
        int len = hex.length();
        assert (len % 2 == 0);
        byte[] out = new byte[len / 2];
        for (int i = 0; i < out.length; i++) {
            int b1 = hexToBin(hex.charAt(offset + 2 * i));
            int b2 = hexToBin(hex.charAt(offset + 2 * i + 1));
            int b = b1 * 16 + b2;
            out[i] = (byte) (b & 0xff);
        }
        return out;
    }

    static public String encode(byte[] input, int offset, int len) {
        StringBuilder r = new StringBuilder(len * 2);
        for (int i = 0; i < len; i++) {
            byte b = input[offset + i];
            r.append(hexCode[(b >> 4) & 0xF]);
            r.append(hexCode[b & 0xF]);
        }
        return r.toString();
    }

    static public String encode(byte[] input) {
        return encode(input, 0, input.length);
    }
}
