package capstone.placer.user;

import capstone.placer.exception.LoginFailedException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @GetMapping("/get/{id}")
    public List<User> get(@PathVariable long id) {
        return userService.get(id);
    }

    @PostMapping("/register")
    public boolean register(@RequestParam("nickname") String nickname, @RequestParam("mail") String mail, @RequestParam("password") String password) {
        boolean registered = userService.register(nickname, mail, password);
        return registered;
    }

    @PostMapping("/login")
    public User login(@RequestParam("mail") String mail, @RequestParam("password") String password) {
        User user = userService.login(mail, password);
        if (Objects.isNull(user)) throw new LoginFailedException("login failed");
        return user;
    }
}
