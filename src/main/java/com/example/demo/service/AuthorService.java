package com.example.demo.service;
 
import com.example.demo.exception.InvalidIdException;
import com.example.demo.model.Author;
import java.util.List;
 
public interface AuthorService {
    Author insert(Author author);
    List<Author> getAll();
    Author getAuthorById(Long id) throws InvalidIdException;
    void deleteAuthor(Long id) throws InvalidIdException;
}