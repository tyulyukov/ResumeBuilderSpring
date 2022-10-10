package tyulyukov.resumebuilderspring.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tyulyukov.resumebuilderspring.model.Resume;
import tyulyukov.resumebuilderspring.service.ResumeService;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/resumes")
public class ResumeController {
  private final ResumeService resumeService;

  public ResumeController(ResumeService resumeService) {
    this.resumeService = resumeService;
  }

  @GetMapping("/")
  public ResponseEntity<Resume[]> get(HttpServletRequest req) {
    var resumes = resumeService.getUserResumes(req);

    return ResponseEntity.ok(resumes);
  }

  @PostMapping("/create")
  public ResponseEntity<String> create(@RequestBody Resume resume, HttpServletRequest req) throws URISyntaxException {
    resumeService.create(resume, req);

    return ResponseEntity
            .status(HttpStatus.CREATED)
            .body("Resume created successfully");
  }

  @GetMapping("/download/{id}")
  public void download(@PathVariable long id) {

  }
}
