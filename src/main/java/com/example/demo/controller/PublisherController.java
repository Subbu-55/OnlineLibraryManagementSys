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
<<<<<<< HEAD
import com.example.demo.model.Publisher;
=======
import com.example.demo.model.Author;
import com.example.demo.model.Book;
import com.example.demo.model.Publisher;
import com.example.demo.service.AuthorServiceImpl;
>>>>>>> abaccced76184e5b6e4a23cd87941991a4cd4ada
import com.example.demo.service.PublisherServiceImpl;

@RestController
@RequestMapping("/publisher")
public class PublisherController {

<<<<<<< HEAD
	
	private PublisherServiceImpl publisherService;
	@Autowired
	public PublisherController(PublisherServiceImpl publisherService) {
		this.publisherService= publisherService;
	}
=======
	@Autowired
	private PublisherServiceImpl publisherService;
>>>>>>> abaccced76184e5b6e4a23cd87941991a4cd4ada
	
	@PostMapping("/add")
	public Publisher insertAuthor(@RequestBody Publisher publisher) {
		return publisherService.insert(publisher);
		
	}
	@GetMapping("/get/all")
	public List<Publisher> getAllPublishers(){
		return publisherService.getAll();
	}
	
	@GetMapping("/get/{id}")
<<<<<<< HEAD
	public ResponseEntity<Publisher> getPublisherById(@PathVariable("id") Long id) throws InvalidIdException{
=======
	public ResponseEntity<?> getPublisherById(@PathVariable("id") Long id) throws InvalidIdException{
>>>>>>> abaccced76184e5b6e4a23cd87941991a4cd4ada
		try {
			
			Publisher publisher =publisherService.getPublisherById(id);
			return ResponseEntity.ok().body(publisher);
		}
		catch(InvalidIdException e) {
			
<<<<<<< HEAD
			return ResponseEntity.badRequest().body(null);
=======
			return ResponseEntity.badRequest().body(e.getMessage());
>>>>>>> abaccced76184e5b6e4a23cd87941991a4cd4ada
		}
		
	}
	
	
	@PutMapping("/update/{id}")
<<<<<<< HEAD
	public ResponseEntity<Publisher> updatePublisher(@PathVariable("id") Long id, @RequestBody Publisher newpublisher)throws InvalidIdException{
=======
	public ResponseEntity<?> updatePublisher(@PathVariable("id") Long id, @RequestBody Publisher newpublisher)throws InvalidIdException{
>>>>>>> abaccced76184e5b6e4a23cd87941991a4cd4ada
		try {
			 
			Publisher publisher = publisherService.getPublisherById(id);
			if(newpublisher.getName()!=null)
				publisher.setName(newpublisher.getName());
			publisher =publisherService.insert(publisher);
				return ResponseEntity.ok().body(publisher);
			
		}
		catch(InvalidIdException e) {
<<<<<<< HEAD
		    return ResponseEntity.badRequest().body(null);
=======
		    return ResponseEntity.badRequest().body(e.getMessage());
>>>>>>> abaccced76184e5b6e4a23cd87941991a4cd4ada
		}
		
	}
	
	@DeleteMapping("/delete/{id}")
<<<<<<< HEAD
    public ResponseEntity<String> deleteAuthor(@PathVariable("id") Long id) {
=======
    public ResponseEntity<?> deleteAuthor(@PathVariable("id") Long id) {
>>>>>>> abaccced76184e5b6e4a23cd87941991a4cd4ada
        try {
            publisherService.deletePublisher(id);
            return ResponseEntity.ok().body("Publisher deleted successfully");
        } catch (InvalidIdException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
