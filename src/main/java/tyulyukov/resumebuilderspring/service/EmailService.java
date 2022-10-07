package tyulyukov.resumebuilderspring.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {
  private final EmailValidator validator = EmailValidator.getInstance();

  public boolean isValidEmailAddress(String email) {
    return validator.isValid(email);
  }
}
