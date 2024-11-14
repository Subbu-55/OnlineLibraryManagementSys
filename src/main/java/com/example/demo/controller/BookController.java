package com.example.demo.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.exception.InvalidIdException;
import com.example.demo.model.Author;
import com.example.demo.model.Book;
import com.example.demo.model.Publisher;
import com.example.demo.service.AuthorServiceImpl;
import com.example.demo.service.BookServiceImpl;
import com.example.demo.service.PublisherServiceImpl;



@RestController
@RequestMapping("/book")

public class BookController {

	private final BookServiceImpl bookService;
    private final AuthorServiceImpl authorService;
    private final PublisherServiceImpl publisherService;

    @Autowired
    public BookController(BookServiceImpl bookService, AuthorServiceImpl authorService, PublisherServiceImpl publisherService) {
        this.bookService = bookService;
        this.authorService = authorService;
        this.publisherService = publisherService;
    }

    @PostMapping("/add")
    public ResponseEntity<Book> createBook(@RequestBody Book book) throws InvalidIdException {
        try {
            Book createdBook = bookService.saveBook(book);
            return ResponseEntity.ok(createdBook);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/get/all")
    public List<Book> getAllBooks() {
        return bookService.getAll();
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable("id") Long id) {
        try {
            Book book = bookService.getBookById(id);
            return ResponseEntity.ok().body(book);
        } catch (InvalidIdException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Book> deleteBookById(@PathVariable("id") Long id) {
        try {
            Book book = bookService.getBookById(id);
            bookService.deleteBook(book.getId());
            return ResponseEntity.ok().body(book);
        } catch (InvalidIdException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable("id") Long id, @RequestBody Book newbook) {
        try {
            Book book = bookService.getBookById(id);
            if (newbook.getTitle() != null)
                book.setTitle(newbook.getTitle());
            if (newbook.getPublicationDate() != null)
                book.setPublicationDate(newbook.getPublicationDate());
            if (newbook.getAuthor() != null) {
                Author author = authorService.getAuthorById(newbook.getAuthor().getId());
                book.setAuthor(author);
            }

            if (newbook.getPublisher() != null) {
                Publisher publisher = publisherService.getPublisherById(newbook.getPublisher().getId());
                book.setPublisher(publisher);
            }
            book = bookService.saveBook(book);
            return ResponseEntity.ok().body(book);

        } catch (InvalidIdException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/{authorId}")
    public ResponseEntity<List<Book>> getBooksByAuthorId(@PathVariable("authorId") Long authorId) {
        try {
            List<Book> books = bookService.getBooksByAuthorId(authorId);
            return ResponseEntity.ok().body(books);
        } catch (InvalidIdException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<Book>> searchBooks(@RequestParam String keyword) {
        List<Book> books = bookService.searchBooks(keyword);
        return ResponseEntity.ok().body(books);
    }

    @GetMapping("/searchAndsort")
    public ResponseEntity<List<Book>> searchAndSortBooks(@RequestParam String keyword, @RequestParam String sortBy) {
        List<Book> books = bookService.searchAndSortBooks(keyword, sortBy);
        return ResponseEntity.ok().body(books);
    }

    @GetMapping("/report")
    public Map<String, Long> getCountByAuthor() {
        return bookService.countBooksByAuthor();
    }
}
