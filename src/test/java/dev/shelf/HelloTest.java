package dev.shelf;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.assertj.MockMvcTester;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
public class HelloTest {

    @Autowired
    MockMvcTester mvc;

    @Test
    void saysTheShelfIsRunning() {
        assertThat(mvc.get().uri("/"))
                .hasStatus(HttpStatus.OK)
                .bodyJson().extractingPath("$.message").isEqualTo("Shelf is running!");
    }

}
