package com.example.demo;

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
import com.example.demo.service.AuthorServiceImpl;

 class AuthorServiceImplTest {

    @Mock
    private AuthorRepository authorRepository;

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private AuthorServiceImpl authorService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
     void testInsertAuthor() {
        
        Author author = new Author();
        author.setName("Subbu");

        when(authorRepository.save(author)).thenReturn(author);

        Author savedAuthor = authorService.insert(author);

        assertNotNull(savedAuthor);
        assertEquals("Subbu", savedAuthor.getName());
    }

    @Test
     void testGetAllAuthors() {
       
        Author author1 = new Author();
        author1.setName("Subbu");
        Author author2 = new Author();
        author2.setName("Sharma");

        when(authorRepository.findAll()).thenReturn(Arrays.asList(author1, author2));

        List<Author> authors = authorService.getAll();

        assertNotNull(authors);
        assertEquals(2, authors.size());
        assertEquals("Subbu", authors.get(0).getName());
        assertEquals("Sharma", authors.get(1).getName());
    }

    @Test
     void testGetAuthorById() throws InvalidIdException {
      
        Author author = new Author();
        author.setId(1L);
        author.setName("Subbu");

        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));
        Author foundAuthor = authorService.getAuthorById(1L);

        assertNotNull(foundAuthor);
        assertEquals("Subbu", foundAuthor.getName());
    }

    @Test
     void testGetAuthorByIdInvalidId() {
       
        when(authorRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(InvalidIdException.class, () -> {
            authorService.getAuthorById(1L);
        });
    }

    @Test
     void testDeleteAuthor() throws InvalidIdException {
       
        Author author = new Author();
        author.setId(1L);
        author.setName("Subbu");

        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));

        authorService.deleteAuthor(1L);

        verify(bookRepository, times(1)).updateAuthorToNull(1L);
        verify(authorRepository, times(1)).delete(author);
    }

    @Test
    void testDeleteAuthorInvalidId() {
    
        when(authorRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(InvalidIdException.class, () -> {
            authorService.deleteAuthor(1L);
        });
    }
}
