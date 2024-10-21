package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.model.Author;
import com.example.demo.model.Book;


public interface BookRepository extends JpaRepository<Book, Long>{

	List<Book> findByAuthor(Author author);

	@Modifying
	@Query("UPDATE Book b SET b.author = NULL WHERE b.author.id =:authorid")
	void updateAuthorToNull(@Param("authorid")Long id);
	
	@Modifying
	@Query("UPDATE Book b SET b.publisher = NULL WHERE b.publisher.id =:publisherid")
	void updatePublisherToNull(@Param("publisherid")Long id);

	boolean existsByTitle(String title);

	
}
