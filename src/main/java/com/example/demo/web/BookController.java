package com.example.demo.web;

import com.example.demo.models.Book;
import com.example.demo.models.SiteUser;
import com.example.demo.service.BookService;
import com.example.demo.service.SiteUserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;
    private final SiteUserService siteUserService;

    public BookController(BookService bookService, SiteUserService siteUserService) {
        this.bookService = bookService;
        this.siteUserService = siteUserService;
    }

    @GetMapping
    public String getAllBooks(Model model) {
        List<Book> books = bookService.getAllBooks();
        model.addAttribute("books", books);
        return "books";
    }

    @GetMapping("/add-form")
    public String addBookPage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SiteUser currentUser = (SiteUser) authentication.getPrincipal();
        model.addAttribute("currentUser", currentUser);
        return "add-form";
    }

    @PostMapping("/add")
    public String addBook(@RequestParam(required = false) Long id,
                          @RequestParam String name,
                          @RequestParam String author,
                          @RequestParam String desc,
                          @RequestParam float price,
                          @RequestParam String category,
                          @RequestParam String image,
                          @RequestParam Integer availableCopies,
                          @RequestParam String username) {
        if (id != null) {
            bookService.updateBook(id, name, author, desc, price, category, image, availableCopies,username);
        }else{
            bookService.createBook(name, author, desc, price, category, image, availableCopies,username);
        }
        return "redirect:/books";

    }

    @GetMapping("/search")
    public String searchTitle(@RequestParam String title, Model model){
        if(!title.isEmpty()){
            model.addAttribute("books", bookService.findByTitle(title));
        }
        else{
            model.addAttribute("books", bookService.getAllBooks());
        }
        return "books";
    }

    @GetMapping("/filter")
    public String filterByCategory(@RequestParam String category, Model model){
        if(!category.isEmpty()){
            model.addAttribute("books", bookService.findByCategory(category));
        }
        else{
            model.addAttribute("books", bookService.getAllBooks());
        }
        return "books";
    }

    @GetMapping("/author")
    public String filterByAuthor(@RequestParam String author, Model model){
        if(!author.isEmpty()){
            model.addAttribute("books", bookService.findByAuthor(author));
        }
        else{
            model.addAttribute("books", bookService.getAllBooks());
        }
        return "books";
    }

    @GetMapping("/edit-form/{id}")
    public String editBookPage(@PathVariable Long id, Model model){
        if(bookService.getBookById(id).isPresent()){
            Book book = bookService.getBookById(id).get();

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            SiteUser currentUser = (SiteUser) authentication.getPrincipal();
            model.addAttribute("currentUser", currentUser);

            model.addAttribute("book", book);

            return "add-form";
        }

        return "redirect:/books?error=BookNotFound";
    }

    @PostMapping("/delete/{id}")
    public String deleteBook(@PathVariable Long id){
        bookService.deleteBook(id);
        return "redirect:/books";
    }

    @PostMapping("/rent/{id}")
    public String rentBook(@PathVariable Long id){
        bookService.rentBook(id);
        return "redirect:/books";
    }

    @GetMapping("/myBooks")
    public String myBooks(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SiteUser currentUser = (SiteUser) authentication.getPrincipal();
        List<Book> myBooks = siteUserService.findBooksByUser(currentUser.getUsername());
        model.addAttribute("myBooks", myBooks);
        return "myBooks";
    }


}
