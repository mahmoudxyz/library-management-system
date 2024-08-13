package xyz.mahmoudahmed.librarymanagementsystem.service;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import xyz.mahmoudahmed.librarymanagementsystem.exception.ResourceNotFoundException;
import xyz.mahmoudahmed.librarymanagementsystem.model.Patron;
import xyz.mahmoudahmed.librarymanagementsystem.repository.PatronRepository;

import java.util.List;

@Service
public class PatronServiceImpl implements PatronService {

    private final PatronRepository patronRepository;

    public PatronServiceImpl(PatronRepository patronRepository) {
        this.patronRepository = patronRepository;
    }

    @Override
    public List<Patron> findAllPatrons() {
        return patronRepository.findAll();
    }

    @Override
    @Cacheable(value = "patrons", key = "#id")
    public Patron findPatronById(Long id) {
        return patronRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patron not found with id: " + id));
    }

    @Override
    public Patron savePatron(Patron patron) {
        return patronRepository.save(patron);
    }

    @Override
    @CacheEvict(value = "patrons", key = "#id")
    public Patron updatePatron(Long id, Patron patronDetails) {
        Patron patron = findPatronById(id);

        patron.setName(patronDetails.getName());
        patron.setEmail(patronDetails.getEmail());
        patron.setAddress(patronDetails.getAddress());

        return patronRepository.save(patron);
    }

    @Override
    @CacheEvict(value = "patrons", key = "#id")
    public void deletePatron(Long id) {
        Patron patron = findPatronById(id);
        patronRepository.delete(patron);
    }

}
