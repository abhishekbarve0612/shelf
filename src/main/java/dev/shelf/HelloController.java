package dev.shelf;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    record Hello(String message) { }

    @GetMapping("/")
    Hello hello() {
        return new Hello("Shelf is running!");
    }
}
