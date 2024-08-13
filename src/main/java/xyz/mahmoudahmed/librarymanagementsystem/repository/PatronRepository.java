package xyz.mahmoudahmed.librarymanagementsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.mahmoudahmed.librarymanagementsystem.model.Patron;

public interface PatronRepository extends JpaRepository<Patron, Long> {
}
