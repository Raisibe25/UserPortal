package com.user.web;

import com.user.repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.security.access.prepost.PreAuthorize;

@Controller
public class AdminController {
    private final UserRepository repo;
    public AdminController(UserRepository repo) { this.repo = repo; }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin")
    public String dashboard(Model model) {
        model.addAttribute("userCount", repo.count());
        model.addAttribute("users", repo.findAll());
        return "admin";
    }
}