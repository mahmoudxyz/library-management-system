package xyz.mahmoudahmed.librarymanagementsystem.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import xyz.mahmoudahmed.librarymanagementsystem.exception.BookIsBorrowedException;
import xyz.mahmoudahmed.librarymanagementsystem.exception.ResourceNotFoundException;
import xyz.mahmoudahmed.librarymanagementsystem.model.Book;
import xyz.mahmoudahmed.librarymanagementsystem.model.BorrowingRecord;
import xyz.mahmoudahmed.librarymanagementsystem.model.Patron;
import xyz.mahmoudahmed.librarymanagementsystem.repository.BorrowingRecordRepository;

import java.time.LocalDateTime;

@Service
public class BorrowingServiceImpl implements BorrowingService {
    private final BorrowingRecordRepository borrowingRecordRepository;
    private final BookServiceImpl bookService;
    private final PatronServiceImpl patronService;

    public BorrowingServiceImpl(BorrowingRecordRepository borrowingRecordRepository, BookServiceImpl bookService, PatronServiceImpl patronService) {
        this.borrowingRecordRepository = borrowingRecordRepository;
        this.bookService = bookService;
        this.patronService = patronService;
    }

    @Override
    @Transactional
    public BorrowingRecord borrowBook(Long bookId, Long patronId) {
        Book book = bookService.findBookById(bookId);
        if (book.getIsBorrowed()) {
            throw new BookIsBorrowedException("Book is already borrowed");
        }

        Patron patron = patronService.findPatronById(patronId);

        BorrowingRecord borrowingRecord = new BorrowingRecord();
        borrowingRecord.setBook(book);
        borrowingRecord.setPatron(patron);
//        borrowingRecord.setBorrowDate(LocalDateTime.now());

        book.setIsBorrowed(true);
        bookService.updateBook(bookId, book);

        return borrowingRecordRepository.save(borrowingRecord);
    }

    @Override
    @Transactional
    public BorrowingRecord returnBook(Long bookId, Long patronId) {
        Book book = bookService.findBookById(bookId);
        book.setIsBorrowed(false);
        bookService.updateBook(bookId, book);
        Patron patron = patronService.findPatronById(patronId);

        BorrowingRecord borrowingRecord = borrowingRecordRepository.findByBookAndPatronAndReturnDateIsNull(book, patron)
                .orElseThrow(() -> new ResourceNotFoundException("No active borrowing record found for bookId: " + bookId + " and patronId: " + patronId));

        borrowingRecord.setReturnDate(LocalDateTime.now());

        return borrowingRecordRepository.save(borrowingRecord);
    }
}
