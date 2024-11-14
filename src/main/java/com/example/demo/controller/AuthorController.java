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
<<<<<<< HEAD
=======
import com.example.demo.model.Book;
import com.example.demo.model.Publisher;
>>>>>>> abaccced76184e5b6e4a23cd87941991a4cd4ada
import com.example.demo.service.AuthorServiceImpl;

@RestController
@RequestMapping("/author")
public class AuthorController {

<<<<<<< HEAD
	private  AuthorServiceImpl authorService;

    @Autowired
    public AuthorController(AuthorServiceImpl authorService) {
        this.authorService = authorService;
    }
=======
	@Autowired
	private AuthorServiceImpl authorService;
>>>>>>> abaccced76184e5b6e4a23cd87941991a4cd4ada
	
	@PostMapping("/add")
	public Author insertAuthor(@RequestBody Author author) {
		return authorService.insert(author);
		
	}
	
	@GetMapping("/get/all")
	public List<Author> getAllAuthors(){
		return authorService.getAll();
	}
	
	@GetMapping("/get/{id}")
<<<<<<< HEAD
	public ResponseEntity<Author> getAuthorById(@PathVariable("id") Long id) throws InvalidIdException{
=======
	public ResponseEntity<?> getAuthorById(@PathVariable("id") Long id) throws InvalidIdException{
>>>>>>> abaccced76184e5b6e4a23cd87941991a4cd4ada
		try {
			
			Author author =authorService.getAuthorById(id);
			return ResponseEntity.ok().body(author);
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
	public ResponseEntity<Author> updateAuthor(@PathVariable("id") Long id, @RequestBody Author newauthor)throws InvalidIdException{
=======
	public ResponseEntity<?> updateAuthor(@PathVariable("id") Long id, @RequestBody Author newauthor)throws InvalidIdException{
>>>>>>> abaccced76184e5b6e4a23cd87941991a4cd4ada
		try {
			 
			Author author = authorService.getAuthorById(id);
			if(newauthor.getName()!=null)
				author.setName(newauthor.getName());
			author =authorService.insert(author);
				return ResponseEntity.ok().body(author);
			
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
            authorService.deleteAuthor(id);
            return ResponseEntity.ok().body("Author deleted successfully");
        } catch (InvalidIdException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
	
}
