package xyz.mahmoudahmed.librarymanagementsystem.service;

import xyz.mahmoudahmed.librarymanagementsystem.model.BorrowingRecord;

public interface BorrowingService {
    BorrowingRecord borrowBook(Long bookId, Long patronId);

    BorrowingRecord returnBook(Long bookId, Long patronId);
}

