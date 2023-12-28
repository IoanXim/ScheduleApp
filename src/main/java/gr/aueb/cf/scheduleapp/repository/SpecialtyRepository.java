package gr.aueb.cf.scheduleapp.repository;

import gr.aueb.cf.scheduleapp.model.Specialty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpecialtyRepository extends JpaRepository<Specialty, Long> {
    List<Specialty> findByNameStartingWith(String name);
    Specialty findSpecialtyById(Long id);
}
