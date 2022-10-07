package tyulyukov.resumebuilderspring.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.*;
import tyulyukov.resumebuilderspring.model.User;
import tyulyukov.resumebuilderspring.service.UserService;

@RestController
@RequestMapping("/auth")
public class AuthController {
  private final UserService userService;

  public AuthController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping("/login")
  public String login(@RequestBody User user) {
    return userService.login(user.getUsername(), user.getPassword());
  }

  @PostMapping("/register")
  public String register(@RequestBody User user) {
    return userService.register(user);
  }

  @GetMapping(value = "/myself")
  public User myself(HttpServletRequest req) {
    return userService.myself(req);
  }

  @GetMapping("/refresh")
  public String refresh(HttpServletRequest req) {
    return userService.refresh(req.getRemoteUser());
  }
}
