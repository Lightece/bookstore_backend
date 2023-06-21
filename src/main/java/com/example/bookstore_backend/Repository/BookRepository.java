package com.example.bookstore_backend.Repository;

import com.example.bookstore_backend.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
    Book findByBookid(@Param("bookid") Integer bookid);
    List<Book> findAllByStatus(@Param("status") Integer status);
    @Query(value = "SELECT * FROM books b WHERE b.title LIKE %:title% AND b.status = 0", nativeQuery = true)
    List<Book> findValidByTitle(@Param("title") String title);
    @Query(value = "SELECT * FROM books b WHERE b.author LIKE %:author% AND b.status=0", nativeQuery = true)
    List<Book> findValidByAuthor(@Param("author") String author);
    @Query(value = "SELECT * FROM books b WHERE b.isbn LIKE %:isbn% AND b.status=0", nativeQuery = true)
    List<Book> findValidByIsbn(@Param("isbn") String isbn);

    List<Book> findAllByTitleContaining(@Param("keyword") String keyword);
}
