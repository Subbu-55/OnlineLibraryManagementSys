package com.example.demo.service;
 
import com.example.demo.exception.InvalidIdException;
import com.example.demo.model.Publisher;
 
import java.util.List;
 
public interface PublisherService {
    Publisher insert(Publisher publisher);
    List<Publisher> getAll();
    Publisher getPublisherById(Long id) throws InvalidIdException;
    void deletePublisher(Long id) throws InvalidIdException; 
}