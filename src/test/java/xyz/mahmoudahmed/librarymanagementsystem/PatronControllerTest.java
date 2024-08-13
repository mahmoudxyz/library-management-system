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
import com.fasterxml.jackson.databind.ObjectMapper;
import xyz.mahmoudahmed.librarymanagementsystem.controller.PatronController;
import xyz.mahmoudahmed.librarymanagementsystem.exception.ResourceNotFoundException;
import xyz.mahmoudahmed.librarymanagementsystem.model.Patron;
import xyz.mahmoudahmed.librarymanagementsystem.service.PatronServiceImpl;

import java.util.List;

@WebMvcTest(PatronController.class)
public class PatronControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PatronServiceImpl patronService;

    @Autowired
    private ObjectMapper objectMapper;

    private Patron patron;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        patron = new Patron();
        patron.setId(1L);
        patron.setName("John Doe");
        patron.setEmail("john.doe@example.com");
    }

    @Test
    public void testGetAllPatrons_Success() throws Exception {
        when(patronService.findAllPatrons()).thenReturn(List.of(patron));

        mockMvc.perform(get("/api/patrons"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("John Doe"))
                .andExpect(jsonPath("$[0].email").value("john.doe@example.com"));
    }

    @Test
    public void testGetPatronById_Success() throws Exception {
        when(patronService.findPatronById(1L)).thenReturn(patron);

        mockMvc.perform(get("/api/patrons/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.email").value("john.doe@example.com"));
    }

    @Test
    public void testGetPatronById_NotFound() throws Exception {
        when(patronService.findPatronById(1L)).thenThrow(new ResourceNotFoundException("Patron not found"));

        mockMvc.perform(get("/api/patrons/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Patron not found"));
    }

    @Test
    public void testAddPatron_Success() throws Exception {
        when(patronService.savePatron(any(Patron.class))).thenReturn(patron);

        mockMvc.perform(post("/api/patrons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(patron)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.email").value("john.doe@example.com"));
    }

    @Test
    public void testUpdatePatron_Success() throws Exception {
        when(patronService.updatePatron(eq(1L), any(Patron.class))).thenReturn(patron);

        mockMvc.perform(put("/api/patrons/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(patron)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.email").value("john.doe@example.com"));
    }

    @Test
    public void testUpdatePatron_NotFound() throws Exception {
        when(patronService.updatePatron(eq(1L), any(Patron.class))).thenThrow(new ResourceNotFoundException("Patron not found"));

        mockMvc.perform(put("/api/patrons/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(patron)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Patron not found"));
    }

    @Test
    public void testDeletePatron_Success() throws Exception {
        doNothing().when(patronService).deletePatron(1L);

        mockMvc.perform(delete("/api/patrons/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testDeletePatron_NotFound() throws Exception {
        doThrow(new ResourceNotFoundException("Patron not found")).when(patronService).deletePatron(1L);

        mockMvc.perform(delete("/api/patrons/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Patron not found"));
    }
}
