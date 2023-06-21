package com.example.bookstore_backend.DaoImpl;

import com.example.bookstore_backend.Dao.BookDao;
import com.example.bookstore_backend.Repository.BookRepository;
import com.example.bookstore_backend.entity.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BookDaoImpl implements BookDao {
    @Autowired
    private BookRepository bookRepository;

    @Override
    public Book findByBookid(Integer bookid) {
        return bookRepository.findByBookid(bookid);
    }
    @Override
    public List<Book> findAll(String keyword){
        if(keyword==null || keyword==""){
            return bookRepository.findAll();
        }
        return bookRepository.findAllByTitleContaining(keyword);
    }

    @Override
    public void delete(Book book){
        bookRepository.delete(book);
    }

    @Override
    public void save(Book book){
        bookRepository.save(book);
    }

    @Override
    public List<Book> getValidBooks(String searchType, String searchValue){
        if(searchType.equals("unfiltered")){
            return bookRepository.findAllByStatus(0);
        }
        else if(searchType.equals("title")){
            return bookRepository.findValidByTitle(searchValue);
        }
        else if(searchType.equals("author")){
            return bookRepository.findValidByAuthor(searchValue);
        }
        else if(searchType.equals("isbn")){
            return bookRepository.findValidByIsbn(searchValue);
        }
        else return null;
    }

}
