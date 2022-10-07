package tyulyukov.resumebuilderspring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tyulyukov.resumebuilderspring.model.Skill;

public interface SkillRepository extends JpaRepository<Skill, Integer> {
}
