package com.example.demo.service.impl;

import com.example.demo.models.Book;
import com.example.demo.models.SiteUser;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.SiteUserRepository;
import com.example.demo.service.BookService;
import com.example.demo.service.SiteUserService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final SiteUserService siteUserService;
    private final SiteUserRepository siteUserRepository;

    public BookServiceImpl(BookRepository bookRepository, SiteUserService siteUserService, SiteUserRepository siteUserRepository) {
        this.bookRepository = bookRepository;
        this.siteUserService= siteUserService;
        this.siteUserRepository = siteUserRepository;
    }

    @Override
    public Optional<Book> getBookById(Long id) {
        return bookRepository.findById(id);
    }

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Optional<Book> createBook(String name, String author, String desc, float price, String category, String image, Integer availableCopies, String username) {
        SiteUser user = siteUserService.findById(username);
        Book book = new Book(name, author, desc, price,category, image, availableCopies, user);

        bookRepository.save(book);
        List<Book> books = user.getBooks();
        books.add(book);
        user.setBooks(books);
        siteUserRepository.save(user);


        return Optional.of(book);
    }



    @Override
    public Book updateBook(Long id, String name, String author, String desc, float price, String category, String image, Integer availableCopies,String username) {
        SiteUser user = siteUserService.findById(username);
        Book existingBook = bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException("Book not found"));
        List<Book> books = user.getBooks();
        books.remove(existingBook);


        existingBook.setName(name);
        existingBook.setAuthor(author);
        existingBook.setDesc(desc);
        existingBook.setPrice(price);
        existingBook.setCategory(category);
        existingBook.setImage(image);
        existingBook.setAvailableCopies(availableCopies);
        existingBook.setUser(user);


        bookRepository.save(existingBook);

        books.add(existingBook);
        user.setBooks(books);
        siteUserRepository.save(user);

        return existingBook;
    }

    @Override
    public void deleteBook(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException("Book not found"));

        bookRepository.deleteById(id);
    }

    @Override
    public List<Book> findByTitle(String name){
        List<Book> books = bookRepository.findByName(name);
        return books;
    }

    @Override
    public List<Book> findByCategory(String category) {
        List<Book> books = bookRepository.findByCategory(category);
        return books;
    }

    @Override
    public List<Book> findByAuthor(String author){
        List<Book> books = bookRepository.findByAuthor(author);
        return books;
    }

    @Override
    public boolean rentBook(Long id){
        Book book = bookRepository.findById(id).orElseThrow(()-> new RuntimeException());
        if(book.getAvailableCopies()>0){
            book.setAvailableCopies(book.getAvailableCopies()-1);
            bookRepository.save(book);
            return true;
        }
        return false;
    }
}
