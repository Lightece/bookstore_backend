package com.example.bookstore_backend.serviceImpl;

import com.example.bookstore_backend.Dao.BookDao;
import com.example.bookstore_backend.Dao.OrderDao;
import com.example.bookstore_backend.Dao.UserDao;
import com.example.bookstore_backend.entity.Book;
import com.example.bookstore_backend.entity.Order;
import com.example.bookstore_backend.entity.OrderItem;
import com.example.bookstore_backend.entity.User;
import com.example.bookstore_backend.model.BookSale;
import com.example.bookstore_backend.model.Msg;
import com.example.bookstore_backend.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class BookServiceImpl implements BookService {
    @Autowired
    private BookDao bookDao;

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private UserDao userDao;
    @Override
    public Msg getBookById(Integer id){
        Book book = bookDao.findByBookid(id);
        if(book == null){
            return new Msg("Book does not exist!", false, null);
        }
        else{
            return new Msg("Get book successfully!", true, book);
        }
    }
    @Override
    public Msg getBookList(String keyword){
        List<Book> bookList = bookDao.findAll(keyword);
        if(bookList == null){
            return new Msg("Book list is empty!", false, null);
        }
        else{
            return new Msg("Get book list successfully!", true, bookList);
        }
    }

    @Override
    public Msg deleteBook(Book book) {
        bookDao.delete(book);
        return new Msg("Delete book successfully!", true, null);
    }
    @Override
    public Msg updateBook(Book book) {
        bookDao.save(book);
        return new Msg("Update book successfully!", true, null);
    }

    @Override
    public Msg getValidBooks(String searchType, String searchValue){
        return new Msg("Get valid books successfully!", true, bookDao.getValidBooks(searchType, searchValue));
    }
    @Override
    public Msg getBookSales(String startDate, String endDate){
        List<Book> bookList = bookDao.findAll("");
        List<Order> orders = orderDao.findFilteredOrders(null,"",  startDate, endDate);
        List<OrderItem> orderItemList = new ArrayList<OrderItem>();
        for(Order order:orders){
            orderItemList.addAll(order.getItems());
        }
        List<BookSale> saleList  = new ArrayList<BookSale>();
        for(Book book:bookList){
            saleList.add(new BookSale(book,0,0.0));
        }
        for(OrderItem orderItem:orderItemList){
            for(BookSale bookSale:saleList){
                if(orderItem.getBook().getBookid().equals(bookSale.getBook().getBookid())){
                    bookSale.setSales(bookSale.getSales()+orderItem.getQuantity());
                    bookSale.setTotal(bookSale.getTotal()+orderItem.getQuantity()*bookSale.getBook().getPrice());
                }
            }
        }
        saleList.sort((o1, o2) -> o2.getSales()-o1.getSales());
        return new Msg("Get book sales successfully!", true, saleList);
    }

    @Override
    public Msg getBoughtBooks(Integer userid, String startDate, String endDate){
        User user = userDao.getUserByUserid(userid);
        List<Book> bookList = bookDao.findAll("");
        List<Order> orders = orderDao.findFilteredOrders(user,"",  startDate, endDate);
        List<OrderItem> orderItemList = new ArrayList<OrderItem>();
        for(Order order:orders){
            orderItemList.addAll(order.getItems());
        }
        List<BookSale> saleList  = new ArrayList<BookSale>();
        for(Book book:bookList){
            saleList.add(new BookSale(book,0,0.0));
        }
        for(OrderItem orderItem:orderItemList){
            for(BookSale bookSale:saleList){
                if(orderItem.getBook().getBookid().equals(bookSale.getBook().getBookid())){
                    bookSale.setSales(bookSale.getSales()+orderItem.getQuantity());
                    bookSale.setTotal(bookSale.getTotal()+orderItem.getQuantity()*bookSale.getBook().getPrice());
                }
            }
        }
        List<BookSale> ansList = new ArrayList<BookSale>();
        for(BookSale bookSale:saleList){
            if(bookSale.getSales()>0){
                ansList.add(bookSale);
            }
        }
        saleList.clear();
        ansList.sort((o1, o2) -> o2.getSales()-o1.getSales());
        return new Msg("Get user book sales successfully!", true, ansList);
    }

}
