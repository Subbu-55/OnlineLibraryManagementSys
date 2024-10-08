package com.example.demo.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.exception.InvalidIdException;
import com.example.demo.model.Author;
import com.example.demo.model.Book;
import com.example.demo.model.Publisher;
import com.example.demo.repository.AuthorRepository;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.PublisherRepository;

@Service
public class BookService {

	@Autowired
	private BookRepository bookRepository ;
	
	@Autowired
	private AuthorRepository authorRepository;
	
	@Autowired
	private PublisherRepository publisherRepository;
	
	@Autowired
	private AuthorService authorService;

	public Book insert(Book book) {
		// TODO Auto-generated method stub
		    Author author = authorRepository.findById(book.getAuthor().getId()).orElse(null);
	        Publisher publisher = publisherRepository.findById(book.getPublisher().getId()).orElse(null); 
	        book.setAuthor(author);
	        book.setPublisher(publisher);
		return bookRepository.save(book);
	}

	public List<Book> getAll() {
		// TODO Auto-generated method stub
		return bookRepository.findAll();
	}

	public Book getBookById(Long id) throws InvalidIdException{
		// TODO Auto-generated method stub
		Optional<Book> optional = bookRepository.findById(id);
		if(!optional.isPresent()) {
			throw new InvalidIdException("Book id is incorrect");
		}
		Book book = optional.get();
		return book;
	}

	public void deleteBook(Long id) {
		// TODO Auto-generated method stub
		bookRepository.deleteById(id);
		
	}

	public List<Book> getBooksByAuthorId(Long authorId) throws InvalidIdException {
	    Author author = authorService.getAuthorById(authorId);
	    if (author == null) {
	        throw new InvalidIdException("Invalid author ID");
	    }
	    return bookRepository.findByAuthor(author);
	}

	 public List<Book> searchBooks(String keyword) {
	        List<Book> books = bookRepository.findAll();
	        return books.stream()
	        		 .filter(book -> 
	                 book.getTitle().contains(keyword) ||
	                 book.getAuthor().getName().contains(keyword) ||
	                 book.getPublisher().getName().contains(keyword)
	             )	                
	        				.collect(Collectors.toList());
	    }

	 public List<Book> searchAndSortBooks(String keyword, String sortBy) {
		    List<Book> books = bookRepository.findAll();
		    return books.stream()
		            .filter(book -> book.getTitle().contains(keyword)||
		            		book.getAuthor().getName().contains(keyword)||
		            		book.getPublisher().getName().contains(keyword)
		            )
		            .sorted((book1, book2) -> {
		                if ("title".equalsIgnoreCase(sortBy)) {
		                    return book1.getTitle().compareTo(book2.getTitle());
		                } else if("PublicationDate".equalsIgnoreCase(sortBy)) {
		                    return book1.getPublicationDate().compareTo(book2.getPublicationDate());
		                } else {
		                    return 0;
		                }
		            })
		            .collect(Collectors.toList());
		}

	public Map<String, Long> countBooksByAuthor() {
		// TODO Auto-generated method stub
		 List<Book> books = bookRepository.findAll();
	        return books.stream()
	                .collect(Collectors.groupingBy(book -> book.getAuthor().getName(), Collectors.counting()));
	}	
	
	
	
}
