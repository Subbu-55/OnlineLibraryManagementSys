package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.exception.InvalidIdException;
import com.example.demo.model.Publisher;
import com.example.demo.service.PublisherServiceImpl;

@RestController
@RequestMapping("/publisher")
public class PublisherController {

    private final PublisherServiceImpl publisherService;

    @Autowired
    public PublisherController(PublisherServiceImpl publisherService) {
        this.publisherService = publisherService;
    }

    @PostMapping("/add")
    public Publisher insertPublisher(@RequestBody Publisher publisher) {
        return publisherService.insert(publisher);
    }

    @GetMapping("/get/all")
    public List<Publisher> getAllPublishers() {
        return publisherService.getAll();
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Publisher> getPublisherById(@PathVariable("id") Long id) {
        try {
            Publisher publisher = publisherService.getPublisherById(id);
            return ResponseEntity.ok().body(publisher);
        } catch (InvalidIdException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Publisher> updatePublisher(@PathVariable("id") Long id, @RequestBody Publisher newPublisher) {
        try {
            Publisher publisher = publisherService.getPublisherById(id);
            if (newPublisher.getName()!= null)
                publisher.setName(newPublisher.getName());
            publisher = publisherService.insert(publisher);
            return ResponseEntity.ok().body(publisher);
        } catch (InvalidIdException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deletePublisher(@PathVariable("id") Long id) {
        try {
            publisherService.deletePublisher(id);
            return ResponseEntity.ok().body("Publisher deleted successfully");
        } catch (InvalidIdException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
