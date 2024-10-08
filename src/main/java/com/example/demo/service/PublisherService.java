package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.exception.InvalidIdException;
import com.example.demo.model.Book;
import com.example.demo.model.Publisher;
import com.example.demo.repository.PublisherRepository;

@Service
public class PublisherService {

	@Autowired
	private PublisherRepository publisherRepository;

	public Publisher insert(Publisher publisher) {
		// TODO Auto-generated method stub
		return publisherRepository.save(publisher);
	}

	public List<Publisher> getAll() {
		// TODO Auto-generated method stub
		return publisherRepository.findAll();
	}

	public Publisher getPublisherById(Long id) throws InvalidIdException{
		// TODO Auto-generated method stub
		Optional<Publisher> optional = publisherRepository.findById(id);
		if(!optional.isPresent()) {
			throw new InvalidIdException("Publisher id is incorrect");
		}
		Publisher publisher = optional.get();
		return publisher;
	}

	
}
