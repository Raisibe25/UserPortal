package com.user.web;

import com.user.dto.ProfileUpdateRequest;
import com.user.dto.UserResponse;
import com.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/profile")
public class ProfileController {
    private final UserService users;
    public ProfileController(UserService users) { this.users = users; }

    @GetMapping
    public String view(Model model, Authentication auth) {
        UserResponse me = users.getByUsername(auth.getName());
        model.addAttribute("user", me);
        model.addAttribute("profileUpdateRequest", new ProfileUpdateRequest());
        return "profile";
    }

    @PostMapping
    public String update(@Valid @ModelAttribute ProfileUpdateRequest req,
                         BindingResult br, Authentication auth, Model model) {
        if (br.hasErrors()) {
            model.addAttribute("user", users.getByUsername(auth.getName()));
            return "profile";
        }
        try {
            UserResponse updated = users.updateProfile(auth.getName(), req);
            model.addAttribute("user", updated);
            model.addAttribute("message", "Profile updated");
            return "profile";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("user", users.getByUsername(auth.getName()));
            return "profile";
        }
    }
}