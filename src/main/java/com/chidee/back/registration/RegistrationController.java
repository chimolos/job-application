package com.chidee.back.registration;

import com.chidee.back.appuser.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

@Controller
//@RequestMapping(path = "api/register")
//@AllArgsConstructor
public class RegistrationController {

    @Autowired
    private RegistrationService registrationService;

    @Autowired
    private AppUserService appUserService;

    private RegistrationRequestAdmin registrationRequestAdmin;

    @GetMapping("")
    public String viewHomePage(Model model) {
        if (appUserService.countUsers() == 4) {
            model.addAttribute("error", "Application closed");
            return "index";
        }
        model.addAttribute("okay", "Apply");
        return "index";
    }

    @GetMapping("/registeradmin")
    public String showAdminForm(Model model, RedirectAttributes result) {
        if (appUserService.countAdmin() == 1) {
            result.addFlashAttribute("message", "You are not allowed access");
            return "redirect:/adminlogin";
        }
        model.addAttribute("admin", new RegistrationRequestAdmin());
        return "admin_form";
    }

    @PostMapping(path = "/adminreg")
    public String registerAdmin(RegistrationRequestAdmin appUser, BindingResult result) {
        registrationService.registerAdmin(appUser);
        if (result.hasErrors()) {
            return "admin_form";
        }
        return "redirect:/adminlogin";
    }

    @GetMapping("/registeruser")
    public String showRegistrationForm(@ModelAttribute("applicant") RegistrationRequest request) {
        if (appUserService.countUsers() == 4) {
//            result.addAttribute("error", "Application closed");
            return "redirect:/";
        }
//        model.addAttribute("applicant", new Applicant());
        return "register_form";
    }

    @PostMapping(path = "/registeruser")
    public String register(@ModelAttribute("applicant") RegistrationRequest request, @RequestParam("passportPhoto") MultipartFile file1, @RequestParam("resume") MultipartFile file2, BindingResult result, RedirectAttributes redirectAttributes) throws IOException {
        if (result.hasErrors()) {
            return "register_form";
        }
        try {
            registrationService.register(request, file1, file2);
        } catch (Exception e) {
            result.rejectValue("email", request.getEmail(), "An account already has this email");
//            result.rejectValue("coverLetter", request.getCoverLetter(), "Complete your cover letter");
            return "register_form";
        }

        redirectAttributes.addFlashAttribute("message", "Your application is successful");
        return "redirect:/";
    }





}
