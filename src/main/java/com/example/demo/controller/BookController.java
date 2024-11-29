package com.example.demo.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.exception.InvalidIdException;
import com.example.demo.model.Book;
import com.example.demo.service.BookService;

@RestController
@RequestMapping("/book")
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping("/add")
    public ResponseEntity<Book> createBook(@RequestBody Book book) throws InvalidIdException {
        Book createdBook = bookService.saveBook(book);
        return ResponseEntity.ok(createdBook);
    }

    @GetMapping("/get/all")
    public List<Book> getAllBooks() {
        return bookService.getAll();
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable("id") Long id) throws InvalidIdException {
        Book book = bookService.getBookById(id);
        return ResponseEntity.ok().body(book);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteBookById(@PathVariable("id") Long id) throws InvalidIdException {
        bookService.deleteBook(id); // Call the service method
        return ResponseEntity.noContent().build(); // Return 204 No Content
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable("id") Long id, @RequestBody Book newbook) throws InvalidIdException {
        Book updatedBook = bookService.updateBook(id, newbook);
        return ResponseEntity.ok().body(updatedBook);
    }

    @GetMapping("/{authorId}")
    public ResponseEntity<List<Book>> getBooksByAuthorId(@PathVariable("authorId") Long authorId) throws InvalidIdException {
        List<Book> books = bookService.getBooksByAuthorId(authorId);
        return ResponseEntity.ok().body(books);
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