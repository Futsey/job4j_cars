package cars.util;

import cars.model.User;
import org.springframework.ui.Model;

import javax.servlet.http.HttpSession;

public final class HttpSessionUtil {

    private HttpSessionUtil() {
    }

    public static void setGuest(Model model, HttpSession session) {
        User user = (User) session.getAttribute("visitor");
        if (user == null) {
            user = new User();
            user.setLogin("Гость");
        }
        model.addAttribute("user", user);
    }
}
