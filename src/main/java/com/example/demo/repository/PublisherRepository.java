package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.model.Author;
import com.example.demo.model.Publisher;

public interface PublisherRepository extends JpaRepository<Publisher, Long>{

	 
	Optional<Author> findByIdAndIsDeletedFalse(Long id);

	 @Query("SELECT a FROM Publisher a WHERE a.isDeleted = 'false'")
	List<Publisher> findAllPublisherAuthors();
}
