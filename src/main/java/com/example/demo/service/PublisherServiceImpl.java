package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.exception.InvalidIdException;
<<<<<<< HEAD
=======
import com.example.demo.model.Author;
import com.example.demo.model.Book;
>>>>>>> abaccced76184e5b6e4a23cd87941991a4cd4ada
import com.example.demo.model.Publisher;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.PublisherRepository;

import jakarta.transaction.Transactional;

@Service
public class PublisherServiceImpl implements PublisherService{

<<<<<<< HEAD

    private PublisherRepository publisherRepository;
    private BookRepository bookRepository;

    @Autowired
    public PublisherServiceImpl(PublisherRepository publisherRepository, BookRepository bookRepository) {
        this.publisherRepository = publisherRepository;
        this.bookRepository = bookRepository;
    }

	@Override
	public Publisher insert(Publisher publisher) {
=======
	@Autowired
	private PublisherRepository publisherRepository;
	
	@Autowired
	private BookRepository bookRepository;

	@Override
	public Publisher insert(Publisher publisher) {
		// TODO Auto-generated method stub
>>>>>>> abaccced76184e5b6e4a23cd87941991a4cd4ada
		return publisherRepository.save(publisher);
	}

	@Override
	public List<Publisher> getAll() {
<<<<<<< HEAD
=======
		// TODO Auto-generated method stub
>>>>>>> abaccced76184e5b6e4a23cd87941991a4cd4ada
		return publisherRepository.findAll();
	}

	@Override
	public Publisher getPublisherById(Long id) throws InvalidIdException{
<<<<<<< HEAD
=======
		// TODO Auto-generated method stub
>>>>>>> abaccced76184e5b6e4a23cd87941991a4cd4ada
		Optional<Publisher> optional = publisherRepository.findById(id);
		if(!optional.isPresent()) {
			throw new InvalidIdException("Publisher id is incorrect");
		}
<<<<<<< HEAD
		return optional.get();
		 
=======
		Publisher publisher = optional.get();
		return publisher;
>>>>>>> abaccced76184e5b6e4a23cd87941991a4cd4ada
	}

	@Override
	@Transactional 
	public void deletePublisher(Long id) throws InvalidIdException {
	    Publisher publisher = getPublisherById(id); 
	    if (publisher != null) {
	        bookRepository.updatePublisherToNull(id);
	        publisherRepository.delete(publisher);
	    } else {
<<<<<<< HEAD
	        throw new InvalidIdException("Publisher not found for id: " + id);
=======
	        throw new InvalidIdException("Author not found for id: " + id);
>>>>>>> abaccced76184e5b6e4a23cd87941991a4cd4ada
	    }
	}
	
}
