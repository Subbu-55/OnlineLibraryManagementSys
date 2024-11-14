package com.example.demo.model;


import jakarta.persistence.Entity;
<<<<<<< HEAD
=======
import jakarta.persistence.FetchType;
>>>>>>> abaccced76184e5b6e4a23cd87941991a4cd4ada
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Book {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String title;
	
	
	private String publicationDate;
	
	@ManyToOne
	@JoinColumn(name = "author_id", nullable = true)
	private Author author;
	
	@ManyToOne
	@JoinColumn(name = "publisher_id", nullable = true)
	private Publisher publisher;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPublicationDate() {
		return publicationDate;
	}

	public void setPublicationDate(String publicationDate) {
		this.publicationDate = publicationDate;
	}

	public Author getAuthor() {
		return author;
	}

	public void setAuthor(Author author) {
		this.author = author;
	}

	public Publisher getPublisher() {
		return publisher;
	}

	public void setPublisher(Publisher publisher) {
		this.publisher = publisher;
	}

	@Override
	public String toString() {
		return "Book [id=" + id + ", title=" + title + ", publicationDate=" + publicationDate + ", author=" + author
				+ ", publisher=" + publisher + "]";
	}
	
	

}
