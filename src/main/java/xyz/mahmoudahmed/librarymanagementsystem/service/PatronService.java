package xyz.mahmoudahmed.librarymanagementsystem.service;

import xyz.mahmoudahmed.librarymanagementsystem.model.Patron;

import java.util.List;

public interface PatronService {
    List<Patron> findAllPatrons();

    Patron findPatronById(Long id);

    Patron savePatron(Patron patron);

    Patron updatePatron(Long id, Patron patronDetails);

    void deletePatron(Long id);
}
