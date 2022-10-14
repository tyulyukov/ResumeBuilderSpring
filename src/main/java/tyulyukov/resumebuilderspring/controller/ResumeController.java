package tyulyukov.resumebuilderspring.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tyulyukov.resumebuilderspring.model.Resume;
import tyulyukov.resumebuilderspring.service.ResumePDFService;
import tyulyukov.resumebuilderspring.service.ResumeService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@ResponseBody
@RestController
@CrossOrigin()
@RequestMapping("/resumes")
public class ResumeController {
  private final ResumeService resumeService;
  private final ResumePDFService resumePdfService;

  public ResumeController(ResumeService resumeService, ResumePDFService resumePdfService) {
    this.resumeService = resumeService;
    this.resumePdfService = resumePdfService;
  }

  @GetMapping("/get")
  @ResponseBody
  public ResponseEntity<Resume[]> get(HttpServletRequest req) {
    var resumes = resumeService.getUserResumes(req);

    return ResponseEntity.ok(resumes);
  }

  @PostMapping("/create")
  @ResponseBody
  public ResponseEntity<String> create(@RequestBody Resume resume, HttpServletRequest req) {
    resumeService.create(resume, req);

    return ResponseEntity
            .status(HttpStatus.CREATED)
            .body("Resume created successfully");
  }

  @GetMapping("/download/{id}")
  @ResponseBody
  public ResponseEntity<String> download(@PathVariable int id, HttpServletRequest req, HttpServletResponse res) {
    var resume = resumeService.get(id, req);

    try {
      resumePdfService.download(resume, res);
    } catch (IOException e) {
      return ResponseEntity
              .status(HttpStatus.INTERNAL_SERVER_ERROR)
              .body("Exception was thrown during loading PDF file");
    }

    return ResponseEntity.ok("Resume PDF downloaded");
  }

  @DeleteMapping("/delete/{id}")
  @ResponseBody
  public ResponseEntity<String> delete(@PathVariable int id, HttpServletRequest req) {
    resumeService.delete(id, req);

    return ResponseEntity.ok("Resume deleted successfully");
  }

  @PatchMapping("/edit/{id}")
  @ResponseBody
  public ResponseEntity<String> edit(@PathVariable int id, @RequestBody Resume newResume, HttpServletRequest req) {
    resumeService.edit(id, newResume, req);

    return ResponseEntity.ok("Resume deleted successfully");
  }
}
