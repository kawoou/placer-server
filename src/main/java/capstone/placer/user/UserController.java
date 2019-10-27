package capstone.placer.user;

import capstone.placer.exception.LoginFailedException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @GetMapping("/get")
    public List<User> get() {
        return userService.get();
    }

    @PostMapping("/register")
    public boolean register(@RequestParam("nickname")String nickname, @RequestParam("mail")String mail, @RequestParam("password") String password) {
        boolean registered = userService.register(nickname, mail, password);
        return registered;
    }

    @PostMapping("/login")
    public boolean login(@RequestParam("mail")String mail, @RequestParam("password")String password) {
        boolean loginAccepted = userService.login(mail, password);
        if (!loginAccepted) throw new LoginFailedException("login failed");

        return true;
    }
}
