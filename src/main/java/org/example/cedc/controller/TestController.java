package org.example.cedc.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author hamza mustafa khan
 * @mailto : hamzamkhan@outlook.com
 */

@RestController
@RequestMapping("/api")
public class TestController {

    @GetMapping("/")
    public String index() {
        return "Hello World";
    }
}
