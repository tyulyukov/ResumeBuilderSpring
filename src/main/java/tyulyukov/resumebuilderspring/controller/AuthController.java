package tyulyukov.resumebuilderspring.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tyulyukov.resumebuilderspring.model.User;
import tyulyukov.resumebuilderspring.service.UserService;

@ResponseBody
@RestController
@CrossOrigin()
@RequestMapping("/auth")
public class AuthController {
  private final UserService userService;

  public AuthController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping("/login")
  @ResponseBody
  public ResponseEntity<String> login(@RequestBody User user) {
    return ResponseEntity.ok(userService.login(user.getUsername(), user.getPassword()));
  }

  @PostMapping("/register")
  @ResponseBody
  public ResponseEntity<String> register(@RequestBody User user) {
    return ResponseEntity.ok(userService.register(user));
  }

  @GetMapping(value = "/myself")
  @ResponseBody
  public ResponseEntity<User> myself(HttpServletRequest req) {
    return ResponseEntity.ok(userService.myself(req));
  }

  @GetMapping("/refresh")
  @ResponseBody
  public ResponseEntity<String> refresh(HttpServletRequest req) {
    return ResponseEntity.ok(userService.refresh(req.getRemoteUser()));
  }
}
