package xyz.mahmoudahmed.librarymanagementsystem;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import xyz.mahmoudahmed.librarymanagementsystem.controller.BookController;
import xyz.mahmoudahmed.librarymanagementsystem.exception.ResourceNotFoundException;
import xyz.mahmoudahmed.librarymanagementsystem.model.Book;
import xyz.mahmoudahmed.librarymanagementsystem.service.BookServiceImpl;

import java.util.Arrays;
import java.util.List;

@WebMvcTest(BookController.class)
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookServiceImpl bookService;

    private Book book;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        book = new Book();
        book.setId(1L);
        book.setTitle("Effective Java");
        book.setAuthor("Joshua Bloch");
    }

    @Test
    public void testGetBookById_Success() throws Exception {
        when(bookService.findBookById(1L)).thenReturn(book);

        mockMvc.perform(get("/api/books/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Effective Java"))
                .andExpect(jsonPath("$.author").value("Joshua Bloch"));
    }

    @Test
    public void testGetBookById_NotFound() throws Exception {
        when(bookService.findBookById(1L)).thenThrow(new ResourceNotFoundException("Book not found"));

        mockMvc.perform(get("/api/books/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Book not found"));
    }


    @Test
    public void testGetAllBooks_Success() throws Exception {
        List<Book> books = Arrays.asList(
                Book.builder().title("Effective Java").author("Joshua Bloch").publicationYear(2002).isbn("9780134685991").build(),
                Book.builder().title("Clean Code").author("Robert C. Martin").publicationYear(2002).isbn("9780134685991").build()
        );

        when(bookService.findAllBooks()).thenReturn(books);

        mockMvc.perform(get("/api/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].title").value("Effective Java"))
                .andExpect(jsonPath("$[1].title").value("Clean Code"));
    }

    @Test
    public void testCreateBook_Success() throws Exception {
        Book book = new Book();
        book.setTitle("Clean Code");
        book.setAuthor("Robert C. Martin");
        book.setPublicationYear(2008);
        book.setIsbn("9780136083238");

        when(bookService.saveBook(any(Book.class))).thenReturn(book);

        mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": \"Clean Code\", \"author\": \"Robert C. Martin\", \"isbn\": \"9780136083238\", \"publicationYear\": 2008}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Clean Code"))
                .andExpect(jsonPath("$.author").value("Robert C. Martin"));
    }

    @Test
    public void testDeleteBook_Success() throws Exception {
        doNothing().when(bookService).deleteBook(1L);

        mockMvc.perform(delete("/api/books/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testCreateBook_ValidationError() throws Exception {
        mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"author\": \"Robert C. Martin\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors").exists());
    }


}
