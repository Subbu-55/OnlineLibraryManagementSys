package com.example.demo.service;
 
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
 
import com.example.demo.exception.InvalidIdException;

import com.example.demo.model.Author;
import com.example.demo.model.Book;
import com.example.demo.model.Publisher;
import com.example.demo.repository.AuthorRepository;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.PublisherRepository;
 
@Service
public class BookServiceImpl implements BookService {
 
    @Autowired
    private BookRepository bookRepository;
 
    @Autowired
    private AuthorRepository authorRepository;
 
    @Autowired
    private PublisherRepository publisherRepository;
 
    @Autowired
    private AuthorServiceImpl authorService;
 
    @Override
    public List<Book> getAll() {
        return bookRepository.findAll();
    }
 
    @Override
    public Book getBookById(Long id) throws InvalidIdException {
        Optional<Book> optional = bookRepository.findById(id);
        if (!optional.isPresent()) {
            throw new InvalidIdException("Book ID is incorrect");
        }
        return optional.get();
    }
 
    @Override
    public void deleteBook(Long id) throws InvalidIdException {
        if (!bookRepository.existsById(id)) {
            throw new InvalidIdException("Book ID is incorrect");
        }
        bookRepository.deleteById(id);
    }
 
    @Override
    public List<Book> getBooksByAuthorId(Long authorId) throws InvalidIdException {
        Author author = authorService.getAuthorById(authorId);
        if (author == null) {
            throw new InvalidIdException("Invalid author ID");
        }
        return bookRepository.findByAuthor(author);
    }
 
    @Override
    public List<Book> searchBooks(String keyword) {
        List<Book> books = bookRepository.findAll();
        return books.stream()
                .filter(book ->
                        book.getTitle().contains(keyword) ||
                        (book.getAuthor() != null && book.getAuthor().getName().contains(keyword)) ||
                        (book.getPublisher() != null && book.getPublisher().getName().contains(keyword)))
                .collect(Collectors.toList());
    }
 
    @Override
    public List<Book> searchAndSortBooks(String keyword, String sortBy) {
        List<Book> books = bookRepository.findAll();
 
        if (keyword == null || keyword.isEmpty()) {
            return books.stream()
                    .sorted((book1, book2) -> "title".equalsIgnoreCase(sortBy) ?
                            book1.getTitle().compareTo(book2.getTitle()) : 0)
                    .collect(Collectors.toList());
        }
 
        List<Book> exactMatchBooks = books.stream()
                .filter(book -> book.getTitle().equalsIgnoreCase(keyword))
                .collect(Collectors.toList());
 
        if (!exactMatchBooks.isEmpty()) {
            return exactMatchBooks;
        }
 
        return books.stream()
                .filter(book -> book.getTitle().contains(keyword) ||
                        (book.getAuthor() != null && book.getAuthor().getName().contains(keyword)) ||
                        (book.getPublisher() != null && book.getPublisher().getName().contains(keyword)))
                .sorted((book1, book2) -> {
                    if ("title".equalsIgnoreCase(sortBy)) {
                        return book1.getTitle().compareTo(book2.getTitle());
                    } else if ("publicationDate".equalsIgnoreCase(sortBy)) {
                        return book1.getPublicationDate().compareTo(book2.getPublicationDate());
                    } else {
                        return 0;
                    }
                })
                .collect(Collectors.toList());
    }
 
    @Override
    public Map<String, Long> countBooksByAuthor() {
        List<Book> books = bookRepository.findAll();
        return books.stream()
                .collect(Collectors.groupingBy(book -> book.getAuthor().getName(), Collectors.counting()));
    }
 
    @Override
    public Book saveBook(Book book) throws InvalidIdException{
        if (bookRepository.existsByTitle(book.getTitle())) {
            throw new InvalidIdException("A book with the same title already exists.");
        }
        return bookRepository.save(book);
    }
}