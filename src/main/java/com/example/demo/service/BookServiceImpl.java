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
 
<<<<<<< HEAD
	private  BookRepository bookRepository;
    private  AuthorRepository authorRepository;
    private  PublisherRepository publisherRepository;
    private  AuthorServiceImpl authorService;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository, AuthorRepository authorRepository, PublisherRepository publisherRepository, AuthorServiceImpl authorService) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.publisherRepository = publisherRepository;
        this.authorService = authorService;
    }

=======
    @Autowired
    private BookRepository bookRepository;
 
    @Autowired
    private AuthorRepository authorRepository;
 
    @Autowired
    private PublisherRepository publisherRepository;
 
    @Autowired
    private AuthorServiceImpl authorService;
>>>>>>> abaccced76184e5b6e4a23cd87941991a4cd4ada
 
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
<<<<<<< HEAD
                .toList();
=======
                .collect(Collectors.toList());
>>>>>>> abaccced76184e5b6e4a23cd87941991a4cd4ada
    }
 
    @Override
    public List<Book> searchAndSortBooks(String keyword, String sortBy) {
        List<Book> books = bookRepository.findAll();
 
        if (keyword == null || keyword.isEmpty()) {
            return books.stream()
                    .sorted((book1, book2) -> "title".equalsIgnoreCase(sortBy) ?
                            book1.getTitle().compareTo(book2.getTitle()) : 0)
<<<<<<< HEAD
                    .toList();
=======
                    .collect(Collectors.toList());
>>>>>>> abaccced76184e5b6e4a23cd87941991a4cd4ada
        }
 
        List<Book> exactMatchBooks = books.stream()
                .filter(book -> book.getTitle().equalsIgnoreCase(keyword))
<<<<<<< HEAD
                .toList();
=======
                .collect(Collectors.toList());
>>>>>>> abaccced76184e5b6e4a23cd87941991a4cd4ada
 
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
<<<<<<< HEAD
                .toList();
=======
                .collect(Collectors.toList());
>>>>>>> abaccced76184e5b6e4a23cd87941991a4cd4ada
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
<<<<<<< HEAD
        if (book.getAuthor() != null && book.getAuthor().getId() != null) {
            Optional<Author> authorOptional = authorRepository.findById(book.getAuthor().getId());
            if (authorOptional.isPresent()) {
                book.getAuthor().setName(authorOptional.get().getName());
            } else {
                throw new InvalidIdException("Author with the given ID does not exist.");
            }
        }

        // Fetch and set the publisher name using the publisher ID
        if (book.getPublisher() != null && book.getPublisher().getId() != null) {
            Optional<Publisher> publisherOptional = publisherRepository.findById(book.getPublisher().getId());
            if (publisherOptional.isPresent()) {
                book.getPublisher().setName(publisherOptional.get().getName());
            } else {
                throw new InvalidIdException("Publisher with the given ID does not exist.");
            }
        }
=======
>>>>>>> abaccced76184e5b6e4a23cd87941991a4cd4ada
        return bookRepository.save(book);
    }
}