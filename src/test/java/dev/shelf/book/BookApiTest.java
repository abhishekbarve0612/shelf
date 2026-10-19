package dev.shelf.book;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class BookApiTest {

    @Autowired
    MockMvcTester mvc;

    @Test
    void savesABookAndListsIt() {
        assertThat(mvc.post().uri("/books").contentType(MediaType.APPLICATION_JSON).content(
                """
                        { "title": "Sherlock Holmes", "author": "Arthur Conan Doyle" }
                """
        )).hasStatus(HttpStatus.CREATED)
                .bodyJson().extractingPath("$.title").isEqualTo("Sherlock Holmes");

        assertThat(mvc.get().uri("/books"))
                .hasStatusOk()
                .bodyJson().extractingPath("$[0].author").isEqualTo("Arthur Conan Doyle");
    }
}
