package xyz.mahmoudahmed.librarymanagementsystem.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.mahmoudahmed.librarymanagementsystem.model.BorrowingRecord;
import xyz.mahmoudahmed.librarymanagementsystem.service.BorrowingServiceImpl;

@RestController
@RequestMapping("/api")
public class BorrowingController {
    private final BorrowingServiceImpl borrowingService;

    public BorrowingController(BorrowingServiceImpl borrowingService) {
        this.borrowingService = borrowingService;
    }

    @PostMapping("/borrow/{bookId}/patron/{patronId}")
    public ResponseEntity<BorrowingRecord> borrowBook(@PathVariable Long bookId, @PathVariable Long patronId) {
        BorrowingRecord borrowingRecord = borrowingService.borrowBook(bookId, patronId);
        return ResponseEntity.status(HttpStatus.CREATED).body(borrowingRecord);
    }

    @PutMapping("/return/{bookId}/patron/{patronId}")
    public ResponseEntity<BorrowingRecord> returnBook(@PathVariable Long bookId, @PathVariable Long patronId) {
        BorrowingRecord returnedRecord = borrowingService.returnBook(bookId, patronId);
        return ResponseEntity.ok(returnedRecord);
    }


}
