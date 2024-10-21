package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.exception.InvalidIdException;
import com.example.demo.model.Author;
import com.example.demo.model.Book;
import com.example.demo.repository.AuthorRepository;
import com.example.demo.repository.BookRepository;

import jakarta.transaction.Transactional;

@Service
public class AuthorServiceImpl implements AuthorService{

	@Autowired
	private AuthorRepository authorRepository;
	
	@Autowired
	private BookRepository bookRepository;
	
	@Override
	public Author insert(Author author) {
		// TODO Auto-generated method stub
		return authorRepository.save(author);
	}

	@Override
	public List<Author> getAll() {
		// TODO Auto-generated method stub
		return authorRepository.findAll();
	}
	
	@Override
	public Author getAuthorById(Long id) throws InvalidIdException{
		// TODO Auto-generated method stub
		Optional<Author> optional = authorRepository.findById(id);
		if(!optional.isPresent()) {
			throw new InvalidIdException("Author id is incorrect");
		}
		Author author = optional.get();
		return author;
	}

	@Override
	@Transactional 
	public void deleteAuthor(Long id) throws InvalidIdException {
	    Author author = getAuthorById(id); 
	    
	    if (author != null) {
	        bookRepository.updateAuthorToNull(id);
	        authorRepository.delete(author);
	    } else{
	        throw new InvalidIdException("Author not found for id: " + id);
	    }
	}
	

	
}
