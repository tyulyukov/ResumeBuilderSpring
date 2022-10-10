package tyulyukov.resumebuilderspring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tyulyukov.resumebuilderspring.model.Resume;
import tyulyukov.resumebuilderspring.model.User;

public interface ResumeRepository extends JpaRepository<Resume, Integer> {
    Resume[] findResumesByUser(User user);
}
