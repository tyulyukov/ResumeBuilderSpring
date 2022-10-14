package tyulyukov.resumebuilderspring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tyulyukov.resumebuilderspring.exception.CustomException;
import tyulyukov.resumebuilderspring.model.User;
import tyulyukov.resumebuilderspring.model.UserRole;
import tyulyukov.resumebuilderspring.repository.UserRepository;
import tyulyukov.resumebuilderspring.security.JwtTokenProvider;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtTokenProvider jwtTokenProvider;
  private final AuthenticationManager authenticationManager;
  private final EmailService emailService;

  public String login(String username, String password) {
    try {
      authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
      return jwtTokenProvider.createToken(username, userRepository.findByUsername(username).getUserRoles());
    } catch (AuthenticationException e) {
      throw new CustomException("Invalid username/password supplied", HttpStatus.UNPROCESSABLE_ENTITY);
    }
  }

  public String register(User user) {
    if (!emailService.isValidEmailAddress(user.getUsername()))
      throw new CustomException("Username is invalid", HttpStatus.UNPROCESSABLE_ENTITY);

    if (!userRepository.existsByUsername(user.getUsername())) {
      user.setUserRoles(new ArrayList<>(List.of(UserRole.ROLE_CLIENT)));
      user.setPassword(passwordEncoder.encode(user.getPassword()));
      userRepository.save(user);
      return jwtTokenProvider.createToken(user.getUsername(), user.getUserRoles());
    } else {
      throw new CustomException("Username is already in use", HttpStatus.BAD_REQUEST);
    }
  }

  public void delete(String username) {
    userRepository.deleteByUsername(username);
  }

  public User search(String username) {
    User appUser = userRepository.findByUsername(username);
    if (appUser == null) {
      throw new CustomException("The user doesn't exist", HttpStatus.NOT_FOUND);
    }
    return appUser;
  }

  public User myself(HttpServletRequest req) {
    var user = userRepository.findByUsername(jwtTokenProvider.getUsername(jwtTokenProvider.resolveToken(req)));
    user.setPassword(null);
    user.setUserResumes(null);
    return user;
  }

  public String refresh(String username) {
    return jwtTokenProvider.createToken(username, userRepository.findByUsername(username).getUserRoles());
  }
}
