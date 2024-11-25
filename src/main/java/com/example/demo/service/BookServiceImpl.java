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

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final PublisherRepository publisherRepository;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository, AuthorRepository authorRepository, PublisherRepository publisherRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.publisherRepository = publisherRepository;
    }

    @Override
    public List<Book> getAll() {
        return bookRepository.findAll();
    }

    @Override
    public Book getBookById(Long id) throws InvalidIdException {
        return bookRepository.findById(id)
                .orElseThrow(() -> new InvalidIdException("Book ID is incorrect"));
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
        Author author = authorRepository.findById(authorId)
                .orElseThrow(() -> new InvalidIdException("Invalid author ID"));
        return bookRepository.findByAuthor(author);
    }

    @Override
    public List<Book> searchBooks(String keyword) {
        return bookRepository.findAll().stream()
                .filter(book -> book.getTitle().contains(keyword) ||
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
        return bookRepository.findAll().stream()
                .collect(Collectors.groupingBy(book -> book.getAuthor().getName(), Collectors.counting()));
    }

    @Override
    public Book saveBook(Book book) throws InvalidIdException {
        if (book.getTitle() == null) {
            throw new InvalidIdException("A book with the same title already exists.");
        }
        
        if (bookRepository.existsByTitle(book.getTitle())) {
            throw new InvalidIdException("A book with the same title already exists.");
        }

        // Validate and set the author
        if (book.getAuthor() != null && book.getAuthor().getId() != null) {
            Author author = authorRepository.findById(book.getAuthor().getId())
                    .orElseThrow(() -> new InvalidIdException("Author with the given ID does not exist."));
            book.setAuthor(author);
        }

        // Validate and set the publisher
        if (book.getPublisher() != null && book.getPublisher().getId() != null) {
            Publisher publisher = publisherRepository.findById(book.getPublisher().getId())
                    .orElseThrow(() -> new InvalidIdException("Publisher with the given ID does not exist."));
            book.setPublisher(publisher);
        }

        return bookRepository.save(book);
    }

    @Override
    public Book updateBook(Long id, Book newBook) throws InvalidIdException {
        Book existingBook = getBookById(id); // Ensure this method is correctly fetching the book

        // Check if the existing book is found
        if (existingBook == null) {
            throw new InvalidIdException("Book not found with ID: " + id);
        }

        // Update the title only if it's different
        if (newBook.getTitle() != null && !newBook.getTitle().equals(existingBook.getTitle())) {
            existingBook.setTitle(newBook.getTitle());
        }

        // Update author if provided
        if (newBook.getAuthor() != null) {
            Author author = authorRepository.findById(newBook.getAuthor().getId())
                    .orElseThrow(() -> new InvalidIdException("Author with the given ID does not exist."));
            existingBook.setAuthor(author);
        }

        // Update publisher if provided
        if (newBook.getPublisher() != null) {
            Publisher publisher = publisherRepository.findById(newBook.getPublisher().getId())
                    .orElseThrow(() -> new InvalidIdException("Publisher with the given ID does not exist."));
            existingBook.setPublisher(publisher);
        }

        return bookRepository.save(existingBook); // Ensure this returns the updated book
    }
}