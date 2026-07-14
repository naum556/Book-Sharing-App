package com.example.demo.repository;


import com.example.demo.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {


    List<Book> findByName(String title);
    List<Book> findByCategory(String category);
    List<Book> findByAuthor(String author);
}
