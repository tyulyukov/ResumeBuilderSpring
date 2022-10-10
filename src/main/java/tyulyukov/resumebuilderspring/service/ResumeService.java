package tyulyukov.resumebuilderspring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import tyulyukov.resumebuilderspring.exception.CustomException;
import tyulyukov.resumebuilderspring.model.Resume;
import tyulyukov.resumebuilderspring.repository.*;
import tyulyukov.resumebuilderspring.security.JwtTokenProvider;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;

@Service
@RequiredArgsConstructor
public class ResumeService {
  private final ResumeRepository resumeRepository;
  private final UserRepository userRepository;
  private final JwtTokenProvider jwtTokenProvider;
  private final LanguageRepository languageRepository;
  private final EducationRepository educationRepository;
  private final SkillRepository skillRepository;

  public void create(Resume resume, HttpServletRequest req) {
    var user = userRepository.findByUsername(jwtTokenProvider.getUsername(jwtTokenProvider.resolveToken(req)));
    resume.setUser(user);

    var skills = resume.getSkills();
    var educations = resume.getEducations();
    var languages = resume.getLanguages();

    resume.setSkills(new HashSet<>());
    resume.setEducations(new HashSet<>());
    resume.setLanguages(new HashSet<>());
    resumeRepository.save(resume);

    /*resume.getSkills().stream().forEach(skill -> skill.setResume(resume));
    resume.getLanguages().stream().forEach(language -> language.setResume(resume));
    resume.getEducations().stream().forEach(education -> education.setResume(resume));*/

    for (var language:
          languages) {
      language.setResume(resume);
      languageRepository.save(language);
    }

    for (var education:
            educations) {
      education.setResume(resume);
      educationRepository.save(education);
    }

    for (var skill:
            skills) {
      skill.setResume(resume);
      skillRepository.save(skill);
    }
  }

  public void delete() {

  }

  public void edit() {

  }

  public Resume[] getUserResumes(HttpServletRequest req) {
    var user = userRepository.findByUsername(jwtTokenProvider.getUsername(jwtTokenProvider.resolveToken(req)));
    var resumes = resumeRepository.findResumesByUser(user);

    return resumes;
  }

  public Resume getById(int id, HttpServletRequest req) {
    var user = userRepository.findByUsername(jwtTokenProvider.getUsername(jwtTokenProvider.resolveToken(req)));
    var resumes = resumeRepository.findResumesByUser(user);

    Resume resumeById = null;
    for (var resume:
            resumes) {
      if (resume.getId() == id) {
        resumeById = resume;
        break;
      }
    }

    if (resumeById == null)
      throw new CustomException("Resume not found", HttpStatus.NOT_FOUND);

    return resumeById;
  }
}
