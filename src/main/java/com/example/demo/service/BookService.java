package com.example.demo.service;

import java.util.List;
import java.util.Map;

import com.example.demo.exception.InvalidIdException;
import com.example.demo.model.Book;

public interface BookService {
    Book saveBook(Book book) throws InvalidIdException;
    List<Book> getAll();
    Book getBookById(Long id) throws InvalidIdException;
    void deleteBook(Long id) throws InvalidIdException; // Updated to return the deleted book
    Book updateBook(Long id, Book newBook) throws InvalidIdException; // New method for updating a book
    List<Book> getBooksByAuthorId(Long authorId) throws InvalidIdException;
    List<Book> searchBooks(String keyword);
    List<Book> searchAndSortBooks(String keyword, String sortBy);
    Map<String, Long> countBooksByAuthor();
}