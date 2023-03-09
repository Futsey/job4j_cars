package cars.controller;

import cars.model.User;
import cars.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.Optional;

import static cars.util.HttpSessionUtil.setGuest;

@Controller
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @GetMapping("/addUser")
    public String addUser(Model model, HttpSession session) {
        setGuest(model, session);
        model.addAttribute("user", new User(0, "Enter login", "Enter password",
                LocalDateTime.now()));
        return "users/addUser";
    }

    @PostMapping("/registration")
    public String registration(Model model, @ModelAttribute User user) {
        Optional<User> regUser = userService.add(user);
        if (regUser.isEmpty()) {
            return "redirect:/users/fail";
        }
        return "redirect:/users/success";
    }

    @GetMapping("/fail")
    public String fail(Model model, HttpSession session) {
        setGuest(model, session);
        model.addAttribute("fail", "Login already in use. Please try again");
        return "/users/registrationFailed";
    }

    @GetMapping("/success")
    public String success(Model model, HttpSession session) {
        setGuest(model, session);
        model.addAttribute("success", "Registration successful");
        return "/users/registrationSuccess";
    }

    @GetMapping("/formUpdateUser/{userId}")
    public String formUpdateCandidate(Model model, @PathVariable("userId") int id, HttpSession session) {
        model.addAttribute("candidates", userService.findById(id));
        setGuest(model, session);
        return "updateUser";
    }

    @PostMapping("/updateUser")
    public String updateUser(@ModelAttribute User user) {
        userService.update(user.getId());
        return "redirect:/formUpdateUser/{userId}";
    }

    @GetMapping("/loginPage")
    public String loginPage(Model model, @RequestParam(name = "fail", required = false) Boolean fail, HttpSession session) {
        setGuest(model, session);
        model.addAttribute("fail", fail != null);
        return "users/login";
    }

    @PostMapping("/login")
    public String loginUser(@ModelAttribute User user, HttpSession session) {
        Optional<User> userDb = userService.findByLoginAndPass(user.getLogin(), user.getPassword());
        if (userDb.isEmpty()) {
            return "redirect:/users/loginPage?fail=true";
        }
        session.setAttribute("user", userDb.get());
        return "redirect:/index";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/users/loginPage";
    }
}
