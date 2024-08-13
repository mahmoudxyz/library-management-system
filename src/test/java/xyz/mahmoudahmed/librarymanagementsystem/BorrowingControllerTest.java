package xyz.mahmoudahmed.librarymanagementsystem;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import xyz.mahmoudahmed.librarymanagementsystem.controller.BorrowingController;
import xyz.mahmoudahmed.librarymanagementsystem.exception.BookIsBorrowedException;
import xyz.mahmoudahmed.librarymanagementsystem.model.Book;
import xyz.mahmoudahmed.librarymanagementsystem.model.BorrowingRecord;
import xyz.mahmoudahmed.librarymanagementsystem.model.Patron;
import xyz.mahmoudahmed.librarymanagementsystem.service.BorrowingServiceImpl;

import java.time.LocalDateTime;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BorrowingController.class)
public class BorrowingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BorrowingServiceImpl borrowingService;

    private Book book;
    private Patron patron;
    private BorrowingRecord borrowingRecord;

    @BeforeEach
    public void setUp() {
        book = new Book();
        book.setId(1L);
        book.setTitle("Effective Java");
        book.setIsBorrowed(false);

        patron = new Patron();
        patron.setId(1L);
        patron.setName("John Doe");

        borrowingRecord = new BorrowingRecord();
        borrowingRecord.setId(1L);
        borrowingRecord.setBook(book);
        borrowingRecord.setPatron(patron);
        borrowingRecord.setBorrowDate(LocalDateTime.now());
    }

    @Test
    public void testBorrowBook_Success() throws Exception {
        when(borrowingService.borrowBook(1L, 1L)).thenReturn(borrowingRecord);

        mockMvc.perform(post("/api/borrow/1/patron/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.book.id").value(1L))
                .andExpect(jsonPath("$.patron.id").value(1L))
                .andExpect(jsonPath("$.borrowDate").exists());
    }

    @Test
    public void testBorrowBook_AlreadyBorrowed() throws Exception {
        when(borrowingService.borrowBook(1L, 1L)).thenThrow(new BookIsBorrowedException("Book is already borrowed"));

        mockMvc.perform(post("/api/borrow/1/patron/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.error").value("Book is already borrowed"));
    }

    @Test
    public void testReturnBook_Success() throws Exception {
        borrowingRecord.setReturnDate(LocalDateTime.now());

        when(borrowingService.returnBook(1L, 1L)).thenReturn(borrowingRecord);

        mockMvc.perform(put("/api/return/1/patron/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.returnDate").exists());
    }


}
