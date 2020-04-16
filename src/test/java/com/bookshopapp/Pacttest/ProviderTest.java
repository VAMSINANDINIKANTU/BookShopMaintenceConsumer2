package com.bookshopapp.Pacttest;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.bookshopapp.model.entities.Book;
import com.bookshopapp.service.ConsumerService;
import au.com.dius.pact.consumer.Pact;
import au.com.dius.pact.consumer.PactProviderRuleMk2;
import au.com.dius.pact.consumer.PactVerification;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.model.RequestResponsePact;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE, classes = ConsumerService.class)
public class ProviderTest {
	@Rule
	public PactProviderRuleMk2 provider = new PactProviderRuleMk2("mybookservice", null, 8090, this);
	@Autowired
	private ConsumerService consumerService;
	@Pact(consumer = "consumer1", provider = "mybookservice")
	public RequestResponsePact createPact(PactDslWithProvider builder) {
		return builder.given("Head First Java is created").uponReceiving("request a book")
				.path("/bookshopmaintenanceapplication/api/book/7").method("GET").willRespondWith().status(200)
				.body("{\"bookName\":\"Head First Java\",\"bookPrice\":\"498\",\"publisherName\":\"Raj\",\"publishingYear\":\"2005\"}")
				.toPact();
	}
	@PactVerification(value = "mybookservice")
	@Test
	public void userExists() throws IOException {
		try {
			final List<Book> book = (List<Book>) consumerService.getBook(7);
			Assert.assertTrue(((Book) book).getBookName().equals("Head First Java")
					&& ((Book) book).getBookPrice() == 498 && ((Book) book).getPublisherName().equals("Raj")
					&& ((Book) book).getPublishingYear() == 2005);
		} catch (Exception ignored) {
		}
	}
}
