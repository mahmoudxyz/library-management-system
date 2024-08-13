package xyz.mahmoudahmed.librarymanagementsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import xyz.mahmoudahmed.librarymanagementsystem.model.Book;
import xyz.mahmoudahmed.librarymanagementsystem.model.BorrowingRecord;
import xyz.mahmoudahmed.librarymanagementsystem.model.Patron;

import java.util.Optional;


public interface BorrowingRecordRepository extends JpaRepository<BorrowingRecord, Long> {

    @Query("SELECT br FROM BorrowingRecord br WHERE br.book = :book AND br.patron = :patron AND br.returnDate IS NULL")
    Optional<BorrowingRecord> findByBookAndPatronAndReturnDateIsNull(@Param("book") Book book, @Param("patron") Patron patron);

}
