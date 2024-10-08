package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.exception.InvalidIdException;
import com.example.demo.model.Author;
import com.example.demo.model.Book;
import com.example.demo.model.Publisher;
import com.example.demo.service.AuthorService;
import com.example.demo.service.PublisherService;

@RestController
@RequestMapping("/publisher")
public class PublisherController {

	@Autowired
	private PublisherService publisherService;
	
	@PostMapping("/add")
	public Publisher insertAuthor(@RequestBody Publisher publisher) {
		return publisherService.insert(publisher);
		
	}
	@GetMapping("/get/all")
	public List<Publisher> getAllPublishers(){
		return publisherService.getAll();
	}
	
	@GetMapping("/get/{id}")
	public ResponseEntity<?> getPublisherById(@PathVariable("id") Long id) throws InvalidIdException{
		try {
			
			Publisher publisher =publisherService.getPublisherById(id);
			return ResponseEntity.ok().body(publisher);
		}
		catch(InvalidIdException e) {
			
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		
	}
	
	
	@PutMapping("/update/{id}")
	public ResponseEntity<?> updatePublisher(@PathVariable("id") Long id, @RequestBody Publisher newpublisher)throws InvalidIdException{
		try {
			 
			Publisher publisher = publisherService.getPublisherById(id);
			if(newpublisher.getName()!=null)
				publisher.setName(newpublisher.getName());
			publisher =publisherService.insert(publisher);
				return ResponseEntity.ok().body(publisher);
			
		}
		catch(InvalidIdException e) {
		    return ResponseEntity.badRequest().body(e.getMessage());
		}
		
	}
}
