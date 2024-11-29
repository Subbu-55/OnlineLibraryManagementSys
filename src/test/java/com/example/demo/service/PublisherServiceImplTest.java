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
import com.example.demo.model.Publisher;
import com.example.demo.repository.PublisherRepository;
import com.example.demo.repository.BookRepository;

class PublisherServiceImplTest {

    @Mock
    private PublisherRepository publisherRepository;

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private PublisherServiceImpl publisherService;

    private Publisher publisher;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        publisher = new Publisher();
        publisher.setId(1L);
        publisher.setName("Sample Publisher");
        publisher.setIsDeleted("false");
    }

    // Test cases for insert
    @Test
    void testInsertPublisher() {
        when(publisherRepository.save(publisher)).thenReturn(publisher);

        Publisher savedPublisher = publisherService.insert(publisher);

        assertNotNull(savedPublisher);
        assertEquals("Sample Publisher", savedPublisher.getName());
    }


    // Test cases for getAll
    @Test
    void testGetAllPublishers() {
        Publisher publisher1 = new Publisher();
        publisher1.setName("Publisher One");
        publisher1.setIsDeleted("false");
        Publisher publisher2 = new Publisher();
        publisher2.setName("Publisher Two");
        publisher2.setIsDeleted("false");

        when(publisherRepository.findAllPublisherAuthors()).thenReturn(Arrays.asList(publisher1, publisher2));

        List<Publisher> publishers = publisherService.getAll();

        assertNotNull(publishers);
        assertEquals(2, publishers.size());
        assertEquals("Publisher One", publishers.get(0).getName());
        assertEquals("Publisher Two", publishers.get(1).getName());
    }

    @Test
    void testGetAllPublishers_NoPublishers() {
        when(publisherRepository.findAllPublisherAuthors()).thenReturn(Arrays.asList());

        List<Publisher> publishers = publisherService.getAll();

        assertNotNull(publishers);
        assertTrue(publishers.isEmpty());
    }

    // Test cases for getPublisherById
    @Test
    void testGetPublisherById() throws InvalidIdException {
        when(publisherRepository.findById(1L)).thenReturn(Optional.of(publisher));

        Publisher foundPublisher = publisherService.getPublisherById(1L);

        assertNotNull(foundPublisher);
        assertEquals("Sample Publisher", foundPublisher.getName());
    }

    @Test
    void testGetPublisherById_InvalidId() {
        when(publisherRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(InvalidIdException.class, () -> {
            publisherService.getPublisherById(1L);
        });
    }

    // Test cases for deletePublisher
    @Test
    void testDeletePublisher() throws InvalidIdException {
        when(publisherRepository.findById(1L)).thenReturn(Optional.of(publisher));
        
        publisherService.deletePublisher(1L);
        
        verify(publisherRepository).save(publisher);
        assertEquals("true", publisher.getIsDeleted());
    }

    @Test
    void testDeletePublisher_InvalidId() {
        when(publisherRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(InvalidIdException.class, () -> {
            publisherService.deletePublisher(1L);
        });
    }

    @Test
    void testDeletePublisher_AlreadyDeleted() throws InvalidIdException {
        publisher.setIsDeleted("true");
        when(publisherRepository.findById(1L)).thenReturn(Optional.of(publisher));

        publisherService.deletePublisher(1L);

        verify(publisherRepository).save(publisher);
        assertEquals("true", publisher.getIsDeleted());
    }
}