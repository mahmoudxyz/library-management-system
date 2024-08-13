package xyz.mahmoudahmed.librarymanagementsystem.service;

import xyz.mahmoudahmed.librarymanagementsystem.model.Book;

import java.util.List;

public interface BookService {
    List<Book> findAllBooks();

    Book findBookById(Long id);

    Book saveBook(Book book);

    Book updateBook(Long id, Book bookDetails);

    void deleteBook(Long id);
}

