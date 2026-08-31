package com.example.demo.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data
public class Book{
     @Id
     @GeneratedValue(strategy = GenerationType.AUTO)
     private Long id;
     private String name;
     private String author;

     @Column(name = "description")
     private String desc;

     private float price;
     private String category;
     private String image;
     private Integer availableCopies;

     @ManyToOne(fetch = FetchType.EAGER)
     private SiteUser user;



     public Book (String name, String author, String desc, float price, String category, String image, Integer availableCopies, SiteUser user) {
         this.name = name;
         this.author = author;
         this.desc = desc;
         this.price = price;
         this.category = category;
         this.image = image;
         this.availableCopies = availableCopies;
         this.user = user;
     }


}



