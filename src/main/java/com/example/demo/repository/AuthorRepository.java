package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.model.Author;

public interface AuthorRepository extends JpaRepository<Author, Long>{

	 @Query("SELECT a FROM Author a WHERE a.isDeleted = 'false'")
	    List<Author> findAllActiveAuthors();

	Optional<Author> findByIdAndIsDeletedFalse(Long id);
}
