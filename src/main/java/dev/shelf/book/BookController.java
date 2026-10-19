package dev.shelf.book;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    BookResponse add(@RequestBody CreateBookRequest request) {
        Book saved = bookService.add(request.getTitle(), request.getAuthor());

        return new BookResponse(saved);
    }

    @GetMapping
    List<BookResponse> list() {
        List<BookResponse> result = new ArrayList<>();
        for (Book book: bookService.list()) {
            result.add(new BookResponse(book));
        }

        return result;
    }
}
