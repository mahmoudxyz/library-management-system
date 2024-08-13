package xyz.mahmoudahmed.librarymanagementsystem;

import static java.util.Optional.ofNullable;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import xyz.mahmoudahmed.librarymanagementsystem.model.Book;
import xyz.mahmoudahmed.librarymanagementsystem.service.BookServiceImpl;


@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Application.class)
public class BookServiceCacheTest {

    @Autowired
    private BookServiceImpl bookService;

    @Autowired
    private CacheManager cacheManager;


    private Book getCachedBook(Long id) {
        return ofNullable(cacheManager.getCache("books")).map(c -> c.get(id, Book.class)).get();
    }

    @Test
    void givenBookThatShouldBeCached_whenFindById_thenResultShouldBePutInCache() {
        Book book = Book.builder().title("Effective Java").author("Joshua Bloch").publicationYear(2002).isbn("9780134685991").build();
        Book savedBook = bookService.saveBook(book);
        Book retrievedBook = bookService.findBookById(savedBook.getId());
        assertEquals(retrievedBook, getCachedBook(savedBook.getId()));
    }


}
