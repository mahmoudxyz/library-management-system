package xyz.mahmoudahmed.librarymanagementsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.mahmoudahmed.librarymanagementsystem.model.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
}

