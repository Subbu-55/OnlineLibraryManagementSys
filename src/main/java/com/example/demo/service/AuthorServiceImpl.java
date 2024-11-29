package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.exception.InvalidIdException;
import com.example.demo.model.Author;

import com.example.demo.repository.AuthorRepository;
import com.example.demo.repository.BookRepository;

import jakarta.transaction.Transactional;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;

    @Autowired
    public AuthorServiceImpl(AuthorRepository authorRepository, BookRepository bookRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    public Author insert(Author author) {
        return authorRepository.save(author);
    }

    @Override
    public List<Author> getAll() {
        return authorRepository.findAllActiveAuthors();
    }

    @Override
    public Author getAuthorById(Long id) throws InvalidIdException {
        Optional<Author> optional = authorRepository.findById(id);
        if (!optional.isPresent()) {
            throw new InvalidIdException("Author id is incorrect");
        }
        return optional.get();
    }

    @Override
    @Transactional
    public void deleteAuthor(Long id) throws InvalidIdException {
        Author author = getAuthorById(id); 

        // No need to check if author is null, as getAuthorById will throw an exception if not found
        author.setIsDeleted("true");
        authorRepository.save(author); 
    }
}
