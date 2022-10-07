package tyulyukov.resumebuilderspring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tyulyukov.resumebuilderspring.model.Education;

public interface EducationRepository extends JpaRepository<Education, Integer> {
}
