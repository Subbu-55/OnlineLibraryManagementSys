package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.exception.InvalidIdException;
import com.example.demo.model.Author;
<<<<<<< HEAD
=======
import com.example.demo.model.Book;
>>>>>>> abaccced76184e5b6e4a23cd87941991a4cd4ada
import com.example.demo.repository.AuthorRepository;
import com.example.demo.repository.BookRepository;

import jakarta.transaction.Transactional;

@Service
public class AuthorServiceImpl implements AuthorService{

<<<<<<< HEAD
	 private AuthorRepository authorRepository;
	    private BookRepository bookRepository;

	    @Autowired
	    public AuthorServiceImpl(AuthorRepository authorRepository, BookRepository bookRepository) {
	        this.authorRepository = authorRepository;
	        this.bookRepository = bookRepository;
	    }

	
	@Override
	public Author insert(Author author) {

=======
	@Autowired
	private AuthorRepository authorRepository;
	
	@Autowired
	private BookRepository bookRepository;
	
	@Override
	public Author insert(Author author) {
		// TODO Auto-generated method stub
>>>>>>> abaccced76184e5b6e4a23cd87941991a4cd4ada
		return authorRepository.save(author);
	}

	@Override
	public List<Author> getAll() {
<<<<<<< HEAD
=======
		// TODO Auto-generated method stub
>>>>>>> abaccced76184e5b6e4a23cd87941991a4cd4ada
		return authorRepository.findAll();
	}
	
	@Override
	public Author getAuthorById(Long id) throws InvalidIdException{
<<<<<<< HEAD
=======
		// TODO Auto-generated method stub
>>>>>>> abaccced76184e5b6e4a23cd87941991a4cd4ada
		Optional<Author> optional = authorRepository.findById(id);
		if(!optional.isPresent()) {
			throw new InvalidIdException("Author id is incorrect");
		}
<<<<<<< HEAD
		return optional.get();
		
=======
		Author author = optional.get();
		return author;
>>>>>>> abaccced76184e5b6e4a23cd87941991a4cd4ada
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
