package com.bookshopapp.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.client.RestTemplate;
import com.bookshopapp.model.entities.Book;

public class ConsumerService {
      private final RestTemplate restTemplate;
      public ConsumerService(@Value("${user-service.base-url}") String baseUrl) {
	   this.restTemplate = new RestTemplateBuilder().rootUri(baseUrl).build();
      }
      @SuppressWarnings("unchecked")
      public List<Book> getBook(int id) {
           List<Book> book= (List<Book>) restTemplate.getForObject("/bookshopmaintenanceapplication/api/book/" + id, Book.class);
	   return book;
       }
}
