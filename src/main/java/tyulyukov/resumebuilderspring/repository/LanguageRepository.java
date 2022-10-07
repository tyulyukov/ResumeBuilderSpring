package tyulyukov.resumebuilderspring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tyulyukov.resumebuilderspring.model.Language;

public interface LanguageRepository extends JpaRepository<Language, Integer> {
}
