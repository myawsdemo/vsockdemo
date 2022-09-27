package com.myawsdemo.vsockserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import solutions.cloudarchitects.vsockj.ServerVSock;
import solutions.cloudarchitects.vsockj.VSock;
import solutions.cloudarchitects.vsockj.VSockAddress;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@SpringBootApplication
public class VsockserverApplication {

    public static void main(String[] args) {
        SpringApplication.run(VsockserverApplication.class, args);
        try {
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
        }
    }
}
