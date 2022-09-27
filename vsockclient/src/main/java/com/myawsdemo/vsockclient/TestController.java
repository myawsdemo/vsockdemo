package com.myawsdemo.vsockclient;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import solutions.cloudarchitects.vsockj.VSock;
import solutions.cloudarchitects.vsockj.VSockAddress;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@RestController
@Slf4j
public class TestController {

    @GetMapping("test")
    public String test() {
        try {
            VSock sock = new VSock(new VSockAddress(5, 5000));
            sock.getOutputStream()
                    .write(String.valueOf(System.currentTimeMillis()).getBytes(StandardCharsets.UTF_8));
            byte[] b = new byte[4096];
            sock.getInputStream().read(b, 0, 4096);
            String x = new String(b, StandardCharsets.UTF_8);
            System.out.println(x);
            sock.close();
            return x;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        }
        return null;
    }

}
