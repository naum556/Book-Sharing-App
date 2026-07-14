package com.example.demo.service;


import com.example.demo.models.Book;
import com.example.demo.models.SiteUser;

import java.util.List;
import java.util.Optional;

public interface BookService {
    Optional<Book> getBookById(Long id);
    List<Book> getAllBooks();
    Optional<Book> createBook(String name, String author, String desc, float price, String category, String image, Integer availableCopies, String username);
    Book updateBook(Long id, String name, String author, String desc, float price, String category, String image, Integer availableCopies, String username);
    void deleteBook(Long id);
    boolean rentBook(Long id);

    List<Book> findByTitle(String name);
    List<Book> findByCategory(String category);
    List<Book> findByAuthor(String author);
}
