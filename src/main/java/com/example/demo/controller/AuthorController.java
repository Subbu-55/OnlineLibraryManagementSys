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
import com.example.demo.service.AuthorServiceImpl;

@RestController
@RequestMapping("/author")
public class AuthorController {

	private  AuthorServiceImpl authorService;

    @Autowired
    public AuthorController(AuthorServiceImpl authorService) {
        this.authorService = authorService;
    }
	
	@PostMapping("/add")
	public Author insertAuthor(@RequestBody Author author) {
		return authorService.insert(author);
		
	}
	
	@GetMapping("/get/all")
	public List<Author> getAllAuthors(){
		return authorService.getAll();
	}
	
	@GetMapping("/get/{id}")
	public ResponseEntity<Author> getAuthorById(@PathVariable("id") Long id) throws InvalidIdException{
		try {
			
			Author author =authorService.getAuthorById(id);
			return ResponseEntity.ok().body(author);
		}
		catch(InvalidIdException e) {
			
			return ResponseEntity.badRequest().body(null);
		}
		
	}
	
	
	@PutMapping("/update/{id}")
	public ResponseEntity<Author> updateAuthor(@PathVariable("id") Long id, @RequestBody Author newauthor)throws InvalidIdException{
		try {
			 
			Author author = authorService.getAuthorById(id);
			if(newauthor.getName()!=null)
				author.setName(newauthor.getName());
			author =authorService.insert(author);
				return ResponseEntity.ok().body(author);
			
		}
		catch(InvalidIdException e) {
		    return ResponseEntity.badRequest().body(null);
		}
		
	}
	@DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteAuthor(@PathVariable("id") Long id) {
        try {
            authorService.deleteAuthor(id);
            return ResponseEntity.ok().body("Author deleted successfully");
        } catch (InvalidIdException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
	
}
