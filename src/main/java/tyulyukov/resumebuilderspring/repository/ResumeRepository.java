package tyulyukov.resumebuilderspring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tyulyukov.resumebuilderspring.model.Resume;

public interface ResumeRepository extends JpaRepository<Resume, Integer> {
}
