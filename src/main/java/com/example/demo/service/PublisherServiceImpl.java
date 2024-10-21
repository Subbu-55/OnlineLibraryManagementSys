package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.exception.InvalidIdException;
import com.example.demo.model.Author;
import com.example.demo.model.Book;
import com.example.demo.model.Publisher;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.PublisherRepository;

import jakarta.transaction.Transactional;

@Service
public class PublisherServiceImpl implements PublisherService{

	@Autowired
	private PublisherRepository publisherRepository;
	
	@Autowired
	private BookRepository bookRepository;

	@Override
	public Publisher insert(Publisher publisher) {
		// TODO Auto-generated method stub
		return publisherRepository.save(publisher);
	}

	@Override
	public List<Publisher> getAll() {
		// TODO Auto-generated method stub
		return publisherRepository.findAll();
	}

	@Override
	public Publisher getPublisherById(Long id) throws InvalidIdException{
		// TODO Auto-generated method stub
		Optional<Publisher> optional = publisherRepository.findById(id);
		if(!optional.isPresent()) {
			throw new InvalidIdException("Publisher id is incorrect");
		}
		Publisher publisher = optional.get();
		return publisher;
	}

	@Override
	@Transactional 
	public void deletePublisher(Long id) throws InvalidIdException {
	    Publisher publisher = getPublisherById(id); 
	    if (publisher != null) {
	        bookRepository.updatePublisherToNull(id);
	        publisherRepository.delete(publisher);
	    } else {
	        throw new InvalidIdException("Author not found for id: " + id);
	    }
	}
	
}
