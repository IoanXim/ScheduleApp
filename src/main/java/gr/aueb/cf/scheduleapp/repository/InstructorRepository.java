package gr.aueb.cf.scheduleapp.repository;

import gr.aueb.cf.scheduleapp.model.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository
public interface InstructorRepository extends JpaRepository<Instructor, Long> {
    List<Instructor> findByLastnameStartingWith(String lastname);

    Instructor findInstructorById(Long id);

    Instructor findInstructorBySsn(Long ssn);
}
