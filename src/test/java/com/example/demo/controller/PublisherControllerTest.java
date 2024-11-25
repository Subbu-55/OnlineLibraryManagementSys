package com.example.demo.controller;

import com.example.demo.controller.PublisherController;
import com.example.demo.exception.InvalidIdException;
import com.example.demo.model.Publisher;
import com.example.demo.service. PublisherServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import static org.springframework.http.HttpStatus.*;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PublisherControllerTest {

    @InjectMocks
    private PublisherController publisherController;

    @Mock
    private PublisherServiceImpl publisherService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testInsertPublisher() {
       
        Publisher publisher1 = new Publisher();
        publisher1.setId(1L);
        publisher1.setName("Publisher One");

        
        when(publisherService.insert(any(Publisher.class))).thenReturn(publisher1);

        
        Publisher result = publisherController.insertPublisher(new Publisher());

        
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Publisher One", result.getName());
    }

    @Test
    void testGetAllPublishers() {
       
        Publisher publisher1 = new Publisher();
        publisher1.setName("Publisher One");
        Publisher publisher2 = new Publisher();
        publisher2.setName("Publisher Two");

        
        List<Publisher> publishers = Arrays.asList(publisher1, publisher2);

       
        when(publisherService.getAll()).thenReturn(publishers);

       
        List<Publisher> result = publisherController.getAllPublishers();

        
        assertEquals(2, result.size());
        assertEquals("Publisher One", result.get(0).getName());
        assertEquals("Publisher Two", result.get(1).getName());
    }

    @Test
    void testGetPublisherByIdSuccess() throws InvalidIdException {
        
        Publisher publisher1 = new Publisher();
        publisher1.setName("Publisher One");

       
        when(publisherService.getPublisherById(1L)).thenReturn(publisher1);

        
        ResponseEntity<Publisher> response = publisherController.getPublisherById(1L);

       
        assertEquals(OK, response.getStatusCode());
        assertEquals("Publisher One", response.getBody().getName());
    }

    @Test
    void testGetPublisherByIdFailure() throws InvalidIdException {
        when(publisherService.getPublisherById(1L)).thenThrow(new InvalidIdException("Invalid ID"));

        ResponseEntity<Publisher> response = publisherController.getPublisherById(1L);
        assertEquals(BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
     void testUpdatePublisherSuccess() throws InvalidIdException {
        
        Publisher publisher1 = new Publisher();
        publisher1.setId(1L);
        publisher1.setName("Publisher One");

        
        Publisher updatedPublisher = new Publisher();
        updatedPublisher.setId(1L);
        updatedPublisher.setName("Updated Publisher");

       
        when(publisherService.getPublisherById(1L)).thenReturn(publisher1);

        
        when(publisherService.insert(any(Publisher.class))).thenReturn(updatedPublisher);

      
        Publisher newPublisher = new Publisher();
        newPublisher.setName("Updated Publisher");

       
        ResponseEntity<Publisher> response = publisherController.updatePublisher(1L, newPublisher);

        
        assertEquals(OK, response.getStatusCode());
        assertEquals("Updated Publisher", response.getBody().getName());
    }

    @Test
    void testUpdatePublisherFailure() throws InvalidIdException {
        when(publisherService.getPublisherById(1L)).thenThrow(new InvalidIdException("Invalid ID"));

        Publisher newPublisher = new Publisher();
        ResponseEntity<Publisher> response = publisherController.updatePublisher(1L, newPublisher);
        assertEquals(BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void testDeletePublisherSuccess() throws InvalidIdException {
        doNothing().when(publisherService).deletePublisher(1L);

        ResponseEntity<String> response = publisherController.deletePublisher(1L);
        assertEquals(OK, response.getStatusCode());
        assertEquals("Publisher deleted successfully", response.getBody());
    }

    @Test
    void testDeletePublisherFailure() throws InvalidIdException {
        doThrow(new InvalidIdException("Invalid ID")).when(publisherService).deletePublisher(1L);

        ResponseEntity<String> response = publisherController.deletePublisher(1L);
        assertEquals(BAD_REQUEST, response.getStatusCode());
        assertEquals("Invalid ID", response.getBody());
    }
}
