package gr.aueb.cf.scheduleapp.repository;

import gr.aueb.cf.scheduleapp.model.SpecInstr;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpecInstrRepository extends JpaRepository<SpecInstr, Long> {

    List<SpecInstr> findByInstructorId(Long instrId);
    List<SpecInstr> findBySpecialtyId(Long specId);
    SpecInstr findSpecInstrByInstructorIdAndSpecialtyId(Long instrId, Long specId);
}
