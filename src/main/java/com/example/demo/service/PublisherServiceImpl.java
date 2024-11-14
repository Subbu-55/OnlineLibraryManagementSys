package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.exception.InvalidIdException;
import com.example.demo.model.Publisher;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.PublisherRepository;

import jakarta.transaction.Transactional;

@Service
public class PublisherServiceImpl implements PublisherService{


    private PublisherRepository publisherRepository;
    private BookRepository bookRepository;

    @Autowired
    public PublisherServiceImpl(PublisherRepository publisherRepository, BookRepository bookRepository) {
        this.publisherRepository = publisherRepository;
        this.bookRepository = bookRepository;
    }

	@Override
	public Publisher insert(Publisher publisher) {
		return publisherRepository.save(publisher);
	}

	@Override
	public List<Publisher> getAll() {
		return publisherRepository.findAll();
	}

	@Override
	public Publisher getPublisherById(Long id) throws InvalidIdException{
		Optional<Publisher> optional = publisherRepository.findById(id);
		if(!optional.isPresent()) {
			throw new InvalidIdException("Publisher id is incorrect");
		}
		return optional.get();
		 
	}

	@Override
	@Transactional 
	public void deletePublisher(Long id) throws InvalidIdException {
	    Publisher publisher = getPublisherById(id); 
	    if (publisher != null) {
	        bookRepository.updatePublisherToNull(id);
	        publisherRepository.delete(publisher);
	    } else {
	        throw new InvalidIdException("Publisher not found for id: " + id);
	    }
	}
	
}
