package com.chidee.back.adminview;

import com.chidee.back.appuser.*;
import com.chidee.back.appuser.AppUserRepository;
import com.chidee.back.appuser.AppUserService;
import com.chidee.back.appuser.Applicant;
import com.chidee.back.appuser.ApplicantRepository;
//import com.chidee.back.appuser.Number;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
//@Requ/estMapping(path = "/admin")
public class AdminController {

    @Autowired
    AppUserRepository appUserRepository;

    @Autowired
    ApplicantRepository applicantRepository;

    @Autowired
    AppUserService appUserService;


    @GetMapping("/users")
    public String getUsers(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        String check = (String) session.getAttribute("userName");
        if (check == null) {
            return "redirect:/adminlogin";
        }
        model.addAttribute("listUsers", appUserService.getUsers());
//        model.addAttribute("allowed", new Number());
//        model.addAttribute("admin", "Max no. Admin");
        return "users";
    }

//    @PostMapping("/admin/changeallowedAdmin")

    @GetMapping("/users/{id}")
    public String viewUser(@PathVariable("id") Long id, Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        String check = (String) session.getAttribute("userName");
        if (check == null) {
             return "redirect:/adminlogin";
//        }else if (!request.getHeader("api_key").isEmpty()) {
//            String head = (String)request.getHeader("api_key");
//            if (head.equals("chido")){
//            }
        }
        Applicant applicant = applicantRepository.findById(id).get();
        model.addAttribute("applicant", applicant);

        return "view_user";
    }

    @GetMapping(value = "/deleteuser/{id}")
    public String deleteUser(@PathVariable Long id, HttpServletRequest request) {
        HttpSession session = request.getSession();
        String check = (String) session.getAttribute("userName");
        if (check == null) {
            return "redirect:/adminlogin";
        }
        applicantRepository.deleteById(id);
        return "redirect:/users";
    }

    @GetMapping(path = "/deleteusers")
    public String deleteAllUsers(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String check = (String) session.getAttribute("userName");
        if (check.isEmpty()) {
            return "redirect:/adminlogin";
        }
        applicantRepository.deleteAll();
        return "redirect:/users";
    }
}
