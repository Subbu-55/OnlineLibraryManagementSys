package com.example.demo.service;

import com.example.demo.exception.InvalidIdException;
import com.example.demo.model.Author;
import com.example.demo.model.Book;
import com.example.demo.model.Publisher;
import com.example.demo.repository.AuthorRepository;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.PublisherRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookServiceImplTest {

    private BookServiceImpl bookService;
    private BookRepository bookRepository;
    private AuthorRepository authorRepository;
    private PublisherRepository publisherRepository;

    @BeforeEach
    void setUp() {
        bookRepository = mock(BookRepository.class);
        authorRepository = mock(AuthorRepository.class);
        publisherRepository = mock(PublisherRepository.class);
        bookService = new BookServiceImpl(bookRepository, authorRepository, publisherRepository);
    }

    @Test
    void testGetAllBooks() {
        Book book1 = new Book();
        Book book2 = new Book();
        when(bookRepository.findAll()).thenReturn(Arrays.asList(book1, book2));

        assertEquals(2, bookService.getAll().size());
    }

    @Test
    void testGetBookById_Success() throws InvalidIdException {
        Book book = new Book();
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        assertEquals(book, bookService.getBookById(1L));
    }

    @Test
    void testGetBookById_InvalidId() {
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(InvalidIdException.class, () -> bookService.getBookById(1L));
        assertEquals("Book ID is incorrect", exception.getMessage());
    }

    @Test
    void testDeleteBook_Success() throws InvalidIdException {
        when(bookRepository.existsById(1L)).thenReturn(true);

        bookService.deleteBook(1L);
        verify(bookRepository).deleteById(1L);
    }

    @Test
    void testDeleteBook_InvalidId() {
        when(bookRepository.existsById(1L)).thenReturn(false);

        Exception exception = assertThrows(InvalidIdException.class, () -> bookService.deleteBook(1L));
        assertEquals("Book ID is incorrect", exception.getMessage());
    }

    @Test
    void testGetBooksByAuthorId_Success() throws InvalidIdException {
        Author author = new Author();
        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));
        Book book = new Book();
        when(bookRepository.findByAuthor(author)).thenReturn(Collections.singletonList(book));

        assertEquals(1, bookService.getBooksByAuthorId(1L).size());
    }

    @Test
    void testGetBooksByAuthorId_InvalidAuthorId() {
        when(authorRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(InvalidIdException.class, () -> bookService.getBooksByAuthorId(1L));
        assertEquals("Invalid author ID", exception.getMessage());
    }

    @Test
    void testSearchBooks() {
        Book book1 = new Book();
        book1.setTitle("Java Programming");
        Book book2 = new Book();
        book2.setTitle("Python Programming");
        when(bookRepository.findAll()).thenReturn(Arrays.asList(book1, book2));

        assertEquals(1, bookService.searchBooks("Java").size());
    }

    @Test
    void testSearchAndSortBooks_NoKeyword() {
        Book book1 = new Book();
        book1.setTitle("A Book");
        Book book2 = new Book();
        book2.setTitle("B Book");
        when(bookRepository.findAll()).thenReturn(Arrays.asList(book1, book2));

        // No keyword, should return all books sorted by title
        assertEquals(2, bookService.searchAndSortBooks("", "title").size());
        assertEquals("A Book", bookService.searchAndSortBooks("", "title").get(0).getTitle());
    }

    @Test
    void testSearchAndSortBooks_ExactMatch() {
        Book book1 = new Book();
        book1.setTitle("Exact Match");
        when(bookRepository.findAll()).thenReturn(Collections.singletonList(book1));

        // Exact match found
        assertEquals(1, bookService.searchAndSortBooks("Exact Match", "title").size());
        assertEquals("Exact Match", bookService.searchAndSortBooks ("Exact Match", "title").get(0).getTitle());
    }

    @Test
    void testSearchAndSortBooks_PartialMatch() {
        Book book1 = new Book();
        book1.setTitle("Java Programming");
        Book book2 = new Book();
        book2.setTitle("Python Programming");
        when(bookRepository.findAll()).thenReturn(Arrays.asList(book1, book2));

        // Partial match should return Java Programming
        assertEquals(1, bookService.searchAndSortBooks("Java", "title").size());
        assertEquals("Java Programming", bookService.searchAndSortBooks("Java", "title").get(0).getTitle());
    }

    @Test
    void testSearchAndSortBooks_SortedByTitle() {
        Book book1 = new Book();
        book1.setTitle("B Book");
        Book book2 = new Book();
        book2.setTitle("A Book");
        when(bookRepository.findAll()).thenReturn(Arrays.asList(book1, book2));

        // Should return sorted by title
        assertEquals("A Book", bookService.searchAndSortBooks("", "title").get(0).getTitle());
    }


    @Test
    void testSearchAndSortBooks_NoBooksFound() {
        when(bookRepository.findAll()).thenReturn(Collections.emptyList());

        // No books should be returned
        assertTrue(bookService.searchAndSortBooks("Nonexistent", "title").isEmpty());
    }

    @Test
    void testSaveBook_Success() throws InvalidIdException {
        Book book = new Book();
        book.setTitle("New Book");
        when(bookRepository.existsByTitle("New Book")).thenReturn(false);
        when(bookRepository.save(book)).thenReturn(book);

        assertEquals(book, bookService.saveBook(book));
    }

    @Test
    void testSaveBook_ExistingTitle() {
        Book book = new Book();
        book.setTitle("Existing Book");
        when(bookRepository.existsByTitle("Existing Book")).thenReturn(true);

        Exception exception = assertThrows(InvalidIdException.class, () -> bookService.saveBook(book));
        assertEquals("A book with the same title already exists.", exception.getMessage());
    }

    @Test
    void testSaveBook_ValidAuthor() throws InvalidIdException {
        Book book = new Book();
        book.setTitle("New Book");
        Author author = new Author();
        author.setId(1L);
        book.setAuthor(author);
        when(bookRepository.existsByTitle("New Book")).thenReturn(false);
        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));
        when(bookRepository.save(book)).thenReturn(book);

        assertEquals(book, bookService.saveBook(book));
    }

    @Test
    void testSaveBook_InvalidAuthor() {
        Book book = new Book();
        book.setTitle("New Book");
        Author author = new Author();
        author.setId(1L);
        book.setAuthor(author);
        when(bookRepository.existsByTitle("New Book")).thenReturn(false);
        when(authorRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(InvalidIdException.class, () -> bookService.saveBook(book));
        assertEquals("Author with the given ID does not exist.", exception.getMessage());
    }

    @Test
    void testSaveBook_ValidPublisher() throws InvalidIdException {
        Book book = new Book();
        book.setTitle("New Book");
        Publisher publisher = new Publisher();
        publisher.setId(1L);
        book.setPublisher(publisher);
        when(bookRepository.existsByTitle("New Book")).thenReturn(false);
        when(publisherRepository.findById(1L)).thenReturn(Optional.of(publisher));
        when(bookRepository.save(book)).thenReturn(book);

        assertEquals(book, bookService.saveBook(book));
    }

    @Test
    void testSaveBook_InvalidPublisher() {
        Book book = new Book();
        book.setTitle("New Book");
        Publisher publisher = new Publisher();
        publisher.setId(1L);
        book.setPublisher(publisher);
        when(bookRepository.existsByTitle("New Book")).thenReturn(false);
        when(publisherRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows( InvalidIdException.class, () -> bookService.saveBook(book));
        assertEquals("Publisher with the given ID does not exist.", exception.getMessage());
    }

    @Test
    void testUpdateBook_Success() throws InvalidIdException {
        Book existingBook = new Book();
        existingBook.setId(1L);
        existingBook.setTitle("Old Title");
        when(bookRepository.findById(1L)).thenReturn(Optional.of(existingBook));

        Book newBook = new Book();
        newBook.setTitle("New Title");
        when(bookRepository.save(existingBook)).thenReturn(existingBook);

        Book updatedBook = bookService.updateBook(1L, newBook);
        assertEquals("New Title", updatedBook.getTitle());
    }

    @Test
    void testUpdateBook_InvalidId() {
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(InvalidIdException.class, () -> bookService.updateBook(1L, new Book()));
        assertEquals("Book ID is incorrect", exception.getMessage());
    }

    @Test
    void testUpdateBook_InvalidAuthor() {
        Book existingBook = new Book();
        existingBook.setId(1L);
        when(bookRepository.findById(1L)).thenReturn(Optional.of(existingBook));

        Book newBook = new Book();
        Author author = new Author();
        author.setId(2L);
        newBook.setAuthor(author);
        when(authorRepository.findById(2L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(InvalidIdException.class, () -> bookService.updateBook(1L, newBook));
        assertEquals("Author with the given ID does not exist.", exception.getMessage());
    }

    @Test
    void testUpdateBook_InvalidPublisher() {
        Book existingBook = new Book();
        existingBook.setId(1L);
        when(bookRepository.findById(1L)).thenReturn(Optional.of(existingBook));

        Book newBook = new Book();
        Publisher publisher = new Publisher();
        publisher.setId(2L);
        newBook.setPublisher(publisher);
        when(publisherRepository.findById(2L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(InvalidIdException.class, () -> bookService.updateBook(1L, newBook));
        assertEquals("Publisher with the given ID does not exist.", exception.getMessage());
    }

    @Test
    void testCountBooksByAuthor_NoBooks() {
        when(bookRepository.findAll()).thenReturn(Collections.emptyList());

        Map<String, Long> result = bookService.countBooksByAuthor();
        assertTrue(result.isEmpty());
    }

    @Test
    void testCountBooksByAuthor_NoAuthors() {
        Author author = new Author();
        author.setName("Author Name");
        Book book1 = new Book();
        book1.setAuthor(author);
        Book book2 = new Book();
        book2.setAuthor(author);
        when(bookRepository.findAll()).thenReturn(Arrays.asList(book1, book2));

        Map<String, Long> result = bookService.countBooksByAuthor();
        assertEquals(1, result.size());
        assertEquals(2L, result.get("Author Name"));
    }

    @Test
    void testSearchAndSortBooks_NoBooks() {
        when(bookRepository.findAll()).thenReturn(Collections.emptyList());

        // No books should be returned
        assertTrue(bookService.searchAndSortBooks("Some Title", "title").isEmpty());
    }

    @Test
    void testUpdateBook_NoAuthorOrPublisher() throws InvalidIdException {
        Book existingBook = new Book();
        existingBook.setId(1L);
        existingBook.setTitle("Old Title");
        when(bookRepository.findById(1L)).thenReturn(Optional.of(existingBook));

        Book newBook = new Book();
        newBook.setTitle("New Title");
        when(bookRepository.save(existingBook)).thenReturn(existingBook);

        Book updatedBook = bookService.updateBook(1L, newBook);
        assertEquals("New Title", updatedBook.getTitle());
        assertNull(updatedBook.getAuthor());
        assertNull(updatedBook.getPublisher());
    }
    
    @Test
    void testSearchAndSortBooks_SortedByPublicationDate() {
        Book book1 = new Book();
        book1.setTitle("Book A");
        book1.setPublicationDate("2021-01-01");
        
        Book book2 = new Book();
        book2.setTitle("Book B");
        book2.setPublicationDate("2022-01-01");
        
        when(bookRepository.findAll()).thenReturn(Arrays.asList(book1, book2));

        // Should return sorted by publication date
        assertEquals(book1, bookService.searchAndSortBooks("", "publicationDate").get(0));
    }

    @Test
    void testSearchAndSortBooks_InvalidSortBy() {
        Book book1 = new Book();
        book1.setTitle("B Book");
        Book book2 = new Book();
        book2.setTitle("A Book");
        when(bookRepository.findAll()).thenReturn(Arrays.asList(book1, book2));

        // Invalid sortBy should return unsorted list
        var books = bookService.searchAndSortBooks("", "invalidSort");
        assertEquals(2, books.size());
        assertEquals("B Book", books.get(0).getTitle());
        assertEquals("A Book", books.get(1).getTitle());
    }
    
    @Test
    void testSearchBooks_NoMatches() {
        when(bookRepository.findAll()).thenReturn(Collections.emptyList());

        List<Book> foundBooks = bookService.searchBooks("Nonexistent Title");
        assertTrue(foundBooks.isEmpty());
    }

    @Test
    void testSearchAndSortBooks_NullKeyword() {
        Book book1 = new Book();
        book1.setTitle("A Book");
        Book book2 = new Book();
        book2.setTitle("B Book");
        when(bookRepository.findAll()).thenReturn(Arrays.asList(book1, book2));

        List<Book> sortedBooks = bookService.searchAndSortBooks(null, "title");
        assertEquals(2, sortedBooks.size());
        assertEquals("A Book", sortedBooks.get(0).getTitle());
        assertEquals("B Book", sortedBooks.get(1).getTitle());
    }

    @Test
    void testSearchAndSortBooksEmptyKeyword() {
        Book book1 = new Book();
        book1.setTitle("A Book");
        Book book2 = new Book();
        book2.setTitle("B Book");
        when(bookRepository.findAll()).thenReturn(Arrays.asList(book1, book2));

        List<Book> sortedBooks = bookService.searchAndSortBooks("", "title");
        assertEquals(2, sortedBooks.size());
        assertEquals("A Book", sortedBooks.get(0).getTitle());
        assertEquals("B Book", sortedBooks.get(1).getTitle());
    }

    @Test
    void testSearchAndSortBooksInvalidSortBy() {
        Book book1 = new Book();
        book1.setTitle("B Book");
        Book book2 = new Book();
        book2.setTitle("A Book");
        when(bookRepository.findAll()).thenReturn(Arrays.asList(book1, book2));

        List<Book> sortedBooks = bookService.searchAndSortBooks("Book", "invalidSort");
        assertEquals(2, sortedBooks.size());
        assertEquals("B Book", sortedBooks.get(0).getTitle());
        assertEquals("A Book", sortedBooks.get(1).getTitle());
    }

  

    @Test
    void testUpdateBook_NoChanges() throws InvalidIdException {
        // Arrange
        Book existingBook = new Book();
        existingBook.setId(1L);
        existingBook.setTitle("Same Title");
        when(bookRepository.findById(1L)).thenReturn(Optional.of(existingBook));
        when(bookRepository.save(any(Book.class))).thenReturn(existingBook); // Mock save method

        Book newBook = new Book();
        newBook.setTitle("Same Title"); // No change in title

        // Act
        Book updatedBook = bookService.updateBook(1L, newBook);

        // Debugging output
        System.out.println("Existing Book: " + existingBook);
        System.out.println("Updated Book: " + updatedBook);

        // Assert
        assertNotNull(updatedBook, "Updated book should not be null");
        assertEquals("Same Title", updatedBook.getTitle());
    }
    
    @Test
    void testSaveBook_NullTitle() {
        Book book = new Book();
        book.setTitle(null);
        Exception exception = assertThrows(InvalidIdException.class, () -> bookService.saveBook(book));
        assertEquals("A book with the same title already exists.", exception.getMessage());
    }

    @Test
    void testSaveBook_NullAuthorAndPublisher() throws InvalidIdException {
        Book book = new Book();
        book.setTitle("New Book");
        when(bookRepository.existsByTitle("New Book")).thenReturn(false);
        when(bookRepository.save(book)).thenReturn(book);

        assertEquals(book, bookService.saveBook(book));
    }

    @Test
    void testCountBooksByAuthor_MultipleAuthors() {
        Author author1 = new Author();
        author1.setName("Author 1");
        Author author2 = new Author();
        author2.setName("Author 2");
        
        Book book1 = new Book();
        book1.setAuthor(author1);
        Book book2 = new Book();
        book2.setAuthor(author1);
        Book book3 = new Book();
        book3.setAuthor(author2);
        
        when(bookRepository.findAll()).thenReturn(Arrays.asList(book1, book2, book3));

        Map<String, Long> result = bookService.countBooksByAuthor();
        assertEquals(2, result.size());
        assertEquals(2L, result.get("Author 1"));
        assertEquals(1L, result.get("Author 2"));
    }

    @Test
    void testSearchBooks_ByAuthorName() {
        Author author = new Author();
        author.setName("John Doe");
        Book book = new Book();
        book.setTitle("Some Title");
        book.setAuthor(author);
        
        when(bookRepository.findAll()).thenReturn(Collections.singletonList(book));
        
        List<Book> foundBooks = bookService.searchBooks("John Doe");
        assertEquals(1, foundBooks.size());
        assertEquals("Some Title", foundBooks.get(0).getTitle());
    }

    @Test
    void testSearchBooks_ByPublisherName() {
        Publisher publisher = new Publisher();
        publisher.setName("Best Publisher");
        Book book = new Book();
        book.setTitle("Another Title");
        book.setPublisher(publisher);
        
        when(bookRepository.findAll()).thenReturn(Collections.singletonList(book));
        
        List<Book> foundBooks = bookService.searchBooks("Best Publisher");
        assertEquals(1, foundBooks.size());
        assertEquals("Another Title", foundBooks.get(0).getTitle());
    }
}