package dev.shelf.book;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class BookService {

    private final BookRepository bookRepository;

    BookService(BookRepository books) {
        this.bookRepository = books;
    }

    public Book add(String title, String author) {
        return bookRepository.save(new Book(title, author));
    }

    @Transactional(readOnly = true)
    public List<Book> list() {
        return bookRepository.findAllByOrderByIdAsc();
    }
}
