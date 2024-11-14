package com.example.demo.model;

<<<<<<< HEAD
=======
import java.util.List;

import jakarta.persistence.CascadeType;
>>>>>>> abaccced76184e5b6e4a23cd87941991a4cd4ada
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
<<<<<<< HEAD

=======
import jakarta.persistence.OneToMany;
>>>>>>> abaccced76184e5b6e4a23cd87941991a4cd4ada

@Entity
public class Publisher {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String name;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Publisher [id=" + id + ", name=" + name + "]";
	}

	
	
}
