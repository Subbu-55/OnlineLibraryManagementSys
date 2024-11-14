package com.example.demo;

import com.example.demo.exception.InvalidIdException;
import com.example.demo.model.Author;
import com.example.demo.model.Book;
import com.example.demo.model.Publisher;
import com.example.demo.repository.AuthorRepository;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.PublisherRepository;
import com.example.demo.service.AuthorServiceImpl;
import com.example.demo.service.BookServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class BookServiceImplTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private AuthorRepository authorRepository;

    @Mock
    private PublisherRepository publisherRepository;

    @Mock
    private AuthorServiceImpl authorService;

    @InjectMocks
    private BookServiceImpl bookService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAll() {
        Book book1 = new Book();
        Book book2 = new Book();
        when(bookRepository.findAll()).thenReturn(Arrays.asList(book1, book2));

        List<Book> books = bookService.getAll();
        assertEquals(2, books.size());
        verify(bookRepository, times(1)).findAll();
    }

    @Test
    void testGetBookById() throws InvalidIdException {
        Book book = new Book();
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        Book foundBook = bookService.getBookById(1L);
        assertEquals(book, foundBook);
        verify(bookRepository, times(1)).findById(1L);
    }

    @Test
    void testGetBookByIdInvalidId() {
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(InvalidIdException.class, () -> bookService.getBookById(1L));
        verify(bookRepository, times(1)).findById(1L);
    }

    @Test
    void testDeleteBook() throws InvalidIdException {
        when(bookRepository.existsById(1L)).thenReturn(true);

        bookService.deleteBook(1L);
        verify(bookRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteBookInvalidId() {
        when(bookRepository.existsById(1L)).thenReturn(false);

        assertThrows(InvalidIdException.class, () -> bookService.deleteBook(1L));
        verify(bookRepository, times(0)).deleteById(1L);
    }

    @Test
    void testGetBooksByAuthorId() throws InvalidIdException {
        Author author = new Author();
        when(authorService.getAuthorById(1L)).thenReturn(author);
        Book book1 = new Book();
        Book book2 = new Book();
        when(bookRepository.findByAuthor(author)).thenReturn(Arrays.asList(book1, book2));

        List<Book> books = bookService.getBooksByAuthorId(1L);
        assertEquals(2, books.size());
        verify(bookRepository, times(1)).findByAuthor(author);
    }

   
    @Test
    void testSearchBooks() {
        Book book1 = new Book();
        book1.setTitle("Sharma");
        Book book2 = new Book();
        book2.setTitle("Subrahmanyam");
        when(bookRepository.findAll()).thenReturn(Arrays.asList(book1, book2));

        List<Book> books = bookService.searchBooks("Sharma");
        assertEquals(1, books.size());
        assertEquals("Sharma", books.get(0).getTitle());
    }

    @Test
    void testSaveBook() throws InvalidIdException {
        Book book = new Book();
        book.setTitle("New Book");
        Author author = new Author();
        author.setId(1L);
        book.setAuthor(author);
        Publisher publisher = new Publisher();
        publisher.setId(1L);
        book.setPublisher(publisher);

        when(bookRepository.existsByTitle("New Book")).thenReturn(false);
        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));
        when(publisherRepository.findById(1L)).thenReturn(Optional.of(publisher));
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        Book savedBook = bookService.saveBook(book);
        assertEquals(book, savedBook);
        verify(bookRepository, times(1)).save(book);
    }

    @Test
    void testSaveBookDuplicateTitle() {
        Book book = new Book();
        book.setTitle("Duplicate Book");

        when(bookRepository.existsByTitle("Duplicate Book")).thenReturn(true);

        assertThrows(InvalidIdException.class, () -> bookService.saveBook(book));
        verify(bookRepository, times(0)).save(book);
    }

    @Test
    void testSaveBookInvalidAuthorId() {
        Book book = new Book();
        book.setTitle("New Book");
        Author author = new Author();
        author.setId(1L);
        book.setAuthor(author);

        when(bookRepository.existsByTitle("New Book")).thenReturn(false);
        when(authorRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(InvalidIdException.class, () -> bookService.saveBook(book));
        verify(bookRepository, times(0)).save(book);
    }

    @Test
    void testSaveBookInvalidPublisherId() {
        Book book = new Book();
        book.setTitle("New Book");
        Publisher publisher = new Publisher();
        publisher.setId(1L);
        book.setPublisher(publisher);

        when(bookRepository.existsByTitle("New Book")).thenReturn(false);
        when(publisherRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(InvalidIdException.class, () -> bookService.saveBook(book));
        verify(bookRepository, times(0)).save(book);
    }
}
