package com.example.demo.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;
import java.util.List;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.demo.exception.InvalidIdException;
import com.example.demo.model.Author;
import com.example.demo.repository.AuthorRepository;
import com.example.demo.repository.BookRepository;

class AuthorServiceImplTest {

    @Mock
    private AuthorRepository authorRepository;

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private AuthorServiceImpl authorService;

    private Author author;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        author = new Author();
        author.setId(1L);
        author.setName("Subbu");
        author.setIsDeleted("false");
    }

    // Test cases for insert
    @Test
    void testInsertAuthor() {
        when(authorRepository.save(author)).thenReturn(author);

        Author savedAuthor = authorService.insert(author);

        assertNotNull(savedAuthor);
        assertEquals("Subbu", savedAuthor.getName());
    }

   

    // Test cases for getAll
    @Test
    void testGetAllAuthors() {
        Author author1 = new Author();
        author1.setName("Subbu");
        author1.setIsDeleted("false");
        Author author2 = new Author();
        author2.setName("Sharma");
        author2.setIsDeleted("false");

        when(authorRepository.findAllActiveAuthors()).thenReturn(Arrays.asList(author1, author2));

        List<Author> authors = authorService.getAll();

        assertNotNull(authors);
        assertEquals(2, authors.size());
        assertEquals("Subbu", authors.get(0).getName());
        assertEquals("Sharma", authors.get(1).getName());
    }

    @Test
    void testGetAllAuthors_NoAuthors() {
        when(authorRepository.findAllActiveAuthors()).thenReturn(Arrays.asList());

        List<Author> authors = authorService.getAll();

        assertNotNull(authors);
        assertTrue(authors.isEmpty());
    }

    // Test cases for getAuthorById
    @Test
    void testGetAuthorById() throws InvalidIdException {
        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));

        Author foundAuthor = authorService.getAuthorById(1L);

        assertNotNull(foundAuthor);
        assertEquals("Subbu", foundAuthor.getName());
    }

    @Test
    void testGetAuthorById_InvalidId() {
        when(authorRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(InvalidIdException.class, () -> {
            authorService.getAuthorById(1L);
        });
    }

    // Test cases for deleteAuthor
    @Test
    void testDeleteAuthor_Success() throws InvalidIdException {
        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));

        authorService.deleteAuthor(1L);

        assertEquals("true", author.getIsDeleted());
        verify(authorRepository, times(1)).save(author);
    }

    @Test
    void testDeleteAuthor_AuthorNotFound() {
        when(authorRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(InvalidIdException.class, () -> {
            authorService.deleteAuthor(1L);
        });
    }

    @Test
    void testDeleteAuthor_AlreadyDeleted() throws InvalidIdException {
        author.setIsDeleted("true");
        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));

        authorService.deleteAuthor(1L);

        assertEquals("true", author.getIsDeleted());
        verify(authorRepository, times(1)).save(author);
    }
}