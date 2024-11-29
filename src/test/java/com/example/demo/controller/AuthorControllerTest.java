package com.example.demo.controller;

import com.example.demo.controller.AuthorController;
import com.example.demo.exception.InvalidIdException;
import com.example.demo.model.Author;
import com.example.demo.service.AuthorServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import static org.springframework.http.HttpStatus.*;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

 class AuthorControllerTest {

    @Mock
    private AuthorServiceImpl authorService;

    @InjectMocks
    private AuthorController authorController;

    @BeforeEach
     void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
     void testInsertAuthor() {
        Author author = new Author();
        when(authorService.insert(any(Author.class))).thenReturn(author);

        Author result = authorController.insertAuthor(author);

        assertEquals(author, result);
        verify(authorService, times(1)).insert(any(Author.class));
    }

    @Test
     void testGetAllAuthors() {
        List<Author> authors = Arrays.asList(new Author(), new Author());
        when(authorService.getAll()).thenReturn(authors);

        List<Author> result = authorController.getAllAuthors();

        assertEquals(authors, result);
        verify(authorService, times(1)).getAll();
    }

    @Test
     void testGetAuthorById() throws InvalidIdException {
        Author author = new Author();
        when(authorService.getAuthorById(anyLong())).thenReturn(author);

        ResponseEntity<Author> response = authorController.getAuthorById(1L);

        assertEquals(OK, response.getStatusCode());
        assertEquals(author, response.getBody());
        verify(authorService, times(1)).getAuthorById(anyLong());
    }

    @Test
     void testGetAuthorByIdInvalidIdException() throws InvalidIdException {
        when(authorService.getAuthorById(anyLong())).thenThrow(new InvalidIdException("Invalid ID"));

        ResponseEntity<Author> response = authorController.getAuthorById(1L);

        assertEquals(BAD_REQUEST, response.getStatusCode());
        assertEquals(null, response.getBody());
        verify(authorService, times(1)).getAuthorById(anyLong());
    }

    @Test
     void testUpdateAuthor() throws InvalidIdException {
        Author author = new Author();
        when(authorService.getAuthorById(anyLong())).thenReturn(author);
        when(authorService.insert(any(Author.class))).thenReturn(author);

        Author newAuthor = new Author();
        newAuthor.setName("Updated Name");

        ResponseEntity<Author> response = authorController.updateAuthor(1L, newAuthor);

        assertEquals(OK, response.getStatusCode());
        assertEquals(author, response.getBody());
        verify(authorService, times(1)).getAuthorById(anyLong());
        verify(authorService, times(1)).insert(any(Author.class));
    }

    @Test
     void testUpdateAuthorInvalidIdException() throws InvalidIdException {
        when(authorService.getAuthorById(anyLong())).thenThrow(new InvalidIdException("Invalid ID"));

        Author newAuthor = new Author();
        newAuthor.setName("Updated Name");

        ResponseEntity<Author> response = authorController.updateAuthor(1L, newAuthor);

        assertEquals(BAD_REQUEST, response.getStatusCode());
        assertEquals(null, response.getBody());
        verify(authorService, times(1)).getAuthorById(anyLong());
    }

    @Test
     void testDeleteAuthorSuccess() throws InvalidIdException {
        doNothing().when(authorService).deleteAuthor(anyLong());

        ResponseEntity<String> response = authorController.deleteAuthor(1L);

        assertEquals(OK, response.getStatusCode());
        assertEquals("Author deleted successfully", response.getBody());
        verify(authorService, times(1)).deleteAuthor(anyLong());
    }

    @Test
     void testDeleteAuthorInvalidIdException() throws InvalidIdException {
        doThrow(new InvalidIdException("Invalid ID")).when(authorService).deleteAuthor(anyLong());

        ResponseEntity<String> response = authorController.deleteAuthor(1L);

        assertEquals(BAD_REQUEST, response.getStatusCode());
        assertEquals("Invalid ID", response.getBody());
        verify(authorService, times(1)).deleteAuthor(anyLong());
    }
}
