package com.example.demo;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.demo.exception.InvalidIdException;
import com.example.demo.model.Publisher;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.PublisherRepository;
import com.example.demo.service.PublisherServiceImpl;

class PublisherServiceImplTest {

    @Mock
    private PublisherRepository publisherRepository;

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private PublisherServiceImpl publisherServiceImpl;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testInsertPublisher() {
        Publisher publisher = new Publisher();
        publisher.setName("Subbu");

        when(publisherRepository.save(publisher)).thenReturn(publisher);

        Publisher savedPublisher = publisherServiceImpl.insert(publisher);
        assertNotNull(savedPublisher);
        assertEquals("Subbu", savedPublisher.getName());
    }

    @Test
    void testGetAllPublishers() {
        Publisher pub1 = new Publisher();
        pub1.setName("Sai");
        Publisher pub2 = new Publisher();
        pub2.setName("Sharma");

        when(publisherRepository.findAll()).thenReturn(Arrays.asList(pub1, pub2));

        List<Publisher> publishers = publisherServiceImpl.getAll();
        assertNotNull(publishers);
        assertEquals(2, publishers.size());
        assertEquals("Sai", publishers.get(0).getName());
        assertEquals("Sharma", publishers.get(1).getName());
    }

    @Test
    void testGetPublisherById() throws InvalidIdException {
        Publisher publisher = new Publisher();
        publisher.setId(1L);
        publisher.setName("Subbu");

        when(publisherRepository.findById(1L)).thenReturn(Optional.of(publisher));

        Publisher foundPublisher = publisherServiceImpl.getPublisherById(1L);
        assertNotNull(foundPublisher);
        assertEquals("Subbu", foundPublisher.getName());
    }

    @Test
    void testGetPublisherByIdThrowsException() {
        when(publisherRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(InvalidIdException.class, () -> {
            publisherServiceImpl.getPublisherById(1L);
        });
    }

    @Test
    void testDeleteAuthor() throws InvalidIdException {
    
       Publisher publisher = new Publisher();
       publisher.setId(1L);
       publisher.setName("Subbu");

       when(publisherRepository.findById(1L)).thenReturn(Optional.of(publisher));

       publisherServiceImpl.deletePublisher(1L);

       verify(bookRepository, times(1)).updatePublisherToNull(1L);
       verify(publisherRepository, times(1)).delete(publisher);
   }


    @Test
    void testDeletePublisherThrowsException() {
        when(publisherRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(InvalidIdException.class, () -> {
            publisherServiceImpl.deletePublisher(1L);
        });
    }
}
