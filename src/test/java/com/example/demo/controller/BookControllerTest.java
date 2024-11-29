package com.example.demo.controller;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.demo.exception.InvalidIdException;
import com.example.demo.model.Book;
import com.example.demo.service.BookService;

import java.util.List;
import java.util.Map;

public class BookControllerTest {

    @InjectMocks
    private BookController bookController;

    @Mock
    private BookService bookService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Test case for POST /book/add
    @Test
    public void testCreateBookSuccessfully() throws InvalidIdException {
        Book book = new Book();
        book.setTitle("New Book");
        when(bookService.saveBook(book)).thenReturn(book);

        ResponseEntity<Book> response = bookController.createBook(book);
        
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(book, response.getBody());
    }

    @Test
    public void testCreateBookThrowsInvalidIdException() throws InvalidIdException {
        Book book = new Book();
        book.setTitle("New Book");
        when(bookService.saveBook(book)).thenThrow(new InvalidIdException("Error"));

        assertThrows(InvalidIdException.class, () -> bookController.createBook(book));
    }

    // Test case for GET /book/get/all
    @Test
    public void testGetAllBooks() {
        Book book1 = new Book();
        Book book2 = new Book();
        when(bookService.getAll()).thenReturn(List.of(book1, book2));

        List<Book> response = bookController.getAllBooks();
        
        assertEquals(2, response.size());
    }

    // Test case for GET /book/get/{id}
    @Test
    public void testGetBookByIdSuccessfully() throws InvalidIdException {
        Book book = new Book();
        book.setId(1L);
        when(bookService.getBookById(1L)).thenReturn(book);

        ResponseEntity<Book> response = bookController.getBookById(1L);
        
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(book, response.getBody());
    }

    @Test
    public void testGetBookByIdThrowsInvalidIdException() throws InvalidIdException {
        when(bookService.getBookById(1L)).thenThrow(new InvalidIdException("Error"));

        assertThrows(InvalidIdException.class, () -> bookController.getBookById(1L));
    }

    // Test case for DELETE /book/delete/{id}
    @Test
    public void testDeleteBookByIdSuccessfully() throws InvalidIdException {
        doNothing().when(bookService).deleteBook(1L);

        ResponseEntity<Void> response = bookController.deleteBookById(1L);
        
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void testDeleteBookByIdThrowsInvalidIdException() throws InvalidIdException {
        doThrow(new InvalidIdException("Error")).when(bookService).deleteBook(1L);

        assertThrows(InvalidIdException.class, () -> bookController.deleteBookById(1L));
    }

    // Test case for PUT /book/update/{id}
    @ Test
    public void testUpdateBookSuccessfully() throws InvalidIdException {
        Book existingBook = new Book();
        existingBook.setId(1L);
        Book newBook = new Book();
        newBook.setTitle("Updated Book");
        when(bookService.updateBook(1L, newBook)).thenReturn(existingBook);

        ResponseEntity<Book> response = bookController.updateBook(1L, newBook);
        
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(existingBook, response.getBody());
    }

    @Test
    public void testUpdateBookThrowsInvalidIdException() throws InvalidIdException {
        Book newBook = new Book();
        when(bookService.updateBook(1L, newBook)).thenThrow(new InvalidIdException("Error"));
        assertThrows(InvalidIdException.class, () -> bookController.updateBook(1L, newBook));
    }

    // Test case for GET /book/{authorId}
    @Test
    public void testGetBooksByAuthorIdSuccessfully() throws InvalidIdException {
        Book book1 = new Book();
        Book book2 = new Book();
        when(bookService.getBooksByAuthorId(1L)).thenReturn(List.of(book1, book2));

        ResponseEntity<List<Book>> response = bookController.getBooksByAuthorId(1L);
        
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
    }

    @Test
    public void testGetBooksByAuthorIdThrowsInvalidIdException() throws InvalidIdException {
        when(bookService.getBooksByAuthorId(1L)).thenThrow(new InvalidIdException("Error"));

        assertThrows(InvalidIdException.class, () -> bookController.getBooksByAuthorId(1L));
    }

    // Test case for GET /book/search
    @Test
    public void testSearchBooksSuccessfully() {
        Book book1 = new Book();
        book1.setTitle("Java Programming");
        when(bookService.searchBooks("Java")).thenReturn(List.of(book1));

        ResponseEntity<List<Book>> response = bookController.searchBooks("Java");
        
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals("Java Programming", response.getBody().get(0).getTitle());
    }

    // Test case for GET /book/searchAndsort
    @Test
    public void testSearchAndSortBooksSuccessfully() {
        Book book1 = new Book();
        book1.setTitle("A Book");
        Book book2 = new Book();
        book2.setTitle("B Book");
        when(bookService.searchAndSortBooks("Book", "title")).thenReturn(List.of(book1, book2));

        ResponseEntity<List<Book>> response = bookController.searchAndSortBooks("Book", "title");
        
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
    }

    // Test case for GET /book/report
    @Test
    public void testGetCountByAuthorSuccessfully() {
        // Arrange
        Map<String, Long> authorCount = Map.of("Author 1", 2L, "Author 2", 3L);
        when(bookService.countBooksByAuthor()).thenReturn(authorCount);

        // Act
        Map<String, Long> response = bookController.getCountByAuthor();

        // Assert
        assertEquals(authorCount, response); // Check if the response matches the expected author count
        verify(bookService).countBooksByAuthor(); // Verify that the service method was called
    }
}