package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.exception.InvalidIdException;
import com.example.demo.model.Author;
import com.example.demo.model.Book;
import com.example.demo.repository.AuthorRepository;

@Service
public class AuthorService {

	@Autowired
	private AuthorRepository authorRepository;
	
	
	public Author insert(Author author) {
		// TODO Auto-generated method stub
		return authorRepository.save(author);
	}

	public List<Author> getAll() {
		// TODO Auto-generated method stub
		return authorRepository.findAll();
	}
	
	public Author getAuthorById(Long id) throws InvalidIdException{
		// TODO Auto-generated method stub
		Optional<Author> optional = authorRepository.findById(id);
		if(!optional.isPresent()) {
			throw new InvalidIdException("Author id is incorrect");
		}
		Author author = optional.get();
		return author;
	}

	

	
}
