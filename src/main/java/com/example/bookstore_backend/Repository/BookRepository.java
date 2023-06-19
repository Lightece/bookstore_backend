package com.example.bookstore_backend.Repository;

import com.example.bookstore_backend.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
    Book findByBookid(@Param("bookid") Integer bookid);
}
