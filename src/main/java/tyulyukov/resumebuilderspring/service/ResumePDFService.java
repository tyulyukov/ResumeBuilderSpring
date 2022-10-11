package tyulyukov.resumebuilderspring.service;

import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.List;
import com.lowagie.text.pdf.PdfWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tyulyukov.resumebuilderspring.model.Resume;

import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class ResumePDFService {
  public void download(Resume resume, HttpServletResponse res) throws IOException {
    res.setContentType("application/pdf");
    Document document = new Document(PageSize.A4);
    PdfWriter.getInstance(document, res.getOutputStream());

    applyTemplate(document, resume);
  }

  private void applyTemplate(Document document, Resume resume) {
    document.open();
    document.addTitle(resume.getName());

    var spacing = new Paragraph();

    var headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 32);
    headerFont.setColor(Color.BLACK);

    var subheaderFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 17);
    headerFont.setColor(Color.BLACK);

    var textFont = FontFactory.getFont(FontFactory.HELVETICA, 14);
    headerFont.setColor(Color.BLACK);

    var header = new Paragraph(resume.getContactInformation().getFirstName() + " " + resume.getContactInformation().getLastName(), headerFont);
    header.setAlignment(Paragraph.ALIGN_CENTER);
    document.add(header);

    var aboutMe = new Paragraph();

    var aboutMeHeader = new Chunk("ABOUT ME", subheaderFont);
    aboutMeHeader.setUnderline(0.1f, -2f);

    aboutMe.add(aboutMeHeader);
    aboutMe.add(spacing);
    aboutMe.add(new Chunk(resume.getDescription(), textFont));

    document.add(aboutMe);

    var contactInfo = new Paragraph();

    var contactInfoHeader = new Chunk("CONTACT INFORMATION", subheaderFont);
    contactInfoHeader.setUnderline(0.1f, -2f);

    contactInfo.add(contactInfoHeader);
    contactInfo.add(spacing);

    if (resume.getContactInformation().getPhone() != null) {
      contactInfo.add(new Chunk("Phone: " + resume.getContactInformation().getPhone(), textFont));
      contactInfo.add(spacing);
    }

    if (resume.getContactInformation().getEmail() != null) {
      contactInfo.add(new Chunk("Email: " + resume.getContactInformation().getEmail(), textFont));
      contactInfo.add(spacing);
    }

    if (resume.getContactInformation().getCountry() != null) {
      contactInfo.add(new Chunk("Country: " + resume.getContactInformation().getCountry(), textFont));
      contactInfo.add(spacing);
    }

    if (resume.getContactInformation().getCity() != null) {
      contactInfo.add(new Chunk("City: " + resume.getContactInformation().getCity(), textFont));
      contactInfo.add(spacing);
    }

    if (resume.getContactInformation().getDateOfBirth() != null) {
      contactInfo.add(new Chunk("Date of birth: " + resume.getContactInformation().getDateOfBirth().toString(), textFont));
      contactInfo.add(spacing);
    }

    document.add(contactInfo);

    if (resume.getEducations().size() > 0) {
      var educationInfo = new Paragraph();

      var educationHeader = new Chunk("EDUCATION", subheaderFont);
      educationHeader.setUnderline(0.1f, -2f);

      educationInfo.add(educationHeader);
      educationInfo.add(spacing);

      var educationsList = new List();

      for (var education : resume.getEducations()) {
        var educationItem = new ListItem();

        var educationItemHeader = new Chunk(education.getInstitution(), textFont);
        educationItem.add(educationItemHeader);
        educationItem.add(spacing);

        if (education.getDegree() != null) {
          educationItem.add(new Chunk("Degree: " + education.getDegree(), textFont));
          educationItem.add(spacing);
        }

        if (education.getDescription() != null) {
          educationItem.add(new Chunk("Description: " + education.getDescription(), textFont));
          educationItem.add(spacing);
        }

        educationsList.add(educationItem);
      }

      educationInfo.add(educationsList);

      document.add(educationInfo);
    }

    if (resume.getSkills().size() > 0) {
      var skillInfo = new Paragraph();

      var skillHeader = new Chunk("SKILLS", subheaderFont);
      skillHeader.setUnderline(0.1f, -2f);

      skillInfo.add(skillHeader);
      skillInfo.add(spacing);

      var skillsList = new List();

      for (var skill : resume.getSkills()) {
        var skillItem = new ListItem();

        var educationItemHeader = new Chunk(skill.getName(), textFont);
        skillItem.add(educationItemHeader);
        skillItem.add(spacing);

        skillsList.add(skillItem);
      }

      skillInfo.add(skillsList);

      document.add(skillInfo);
    }

    if (resume.getLanguages().size() > 0) {
      var languageInfo = new Paragraph();

      var languageHeader = new Chunk("LANGUAGES", subheaderFont);
      languageHeader.setUnderline(0.1f, -2f);

      languageInfo.add(languageHeader);
      languageInfo.add(spacing);

      var languagesList = new List();

      for (var language : resume.getLanguages()) {
        var languageItem = new ListItem();

        var languageItemHeader = new Chunk(language.getName(), textFont);
        languageItem.add(languageItemHeader);
        languageItem.add(spacing);

        if (language.getLevel() != null) {
          languageItem.add(new Chunk("Level: " + language.getLevel(), textFont));
          languageItem.add(spacing);
        }

        languagesList.add(languageItem);
      }

      languageInfo.add(languagesList);

      document.add(languageInfo);
    }

    document.close();
  }
}
