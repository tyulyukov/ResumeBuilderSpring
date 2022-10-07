package tyulyukov.resumebuilderspring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tyulyukov.resumebuilderspring.model.ContactInformation;

public interface ContactInformationRepository extends JpaRepository<ContactInformation, Integer> {
}
