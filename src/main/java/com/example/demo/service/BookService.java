package com.example.demo.service;
 
import com.example.demo.exception.InvalidIdException;
import com.example.demo.model.Book;
import java.util.List;
import java.util.Map;
 
public interface BookService {
<<<<<<< HEAD

=======
//    Book insert(Book book);
>>>>>>> abaccced76184e5b6e4a23cd87941991a4cd4ada
	Book saveBook(Book book) throws InvalidIdException;
    List<Book> getAll();
    Book getBookById(Long id) throws InvalidIdException;
    void deleteBook(Long id) throws InvalidIdException;
    List<Book> getBooksByAuthorId(Long authorId) throws InvalidIdException;
    List<Book> searchBooks(String keyword);
    List<Book> searchAndSortBooks(String keyword, String sortBy);
    Map<String, Long> countBooksByAuthor();
    
   
}
