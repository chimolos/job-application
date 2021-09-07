package com.chidee.back.login;

import com.chidee.back.appuser.*;
//import org.jetbrains.annotations.NotNull;
import com.chidee.back.appuser.AppUserRepository;
import com.chidee.back.appuser.AppUserService;
import com.chidee.back.appuser.ApplicantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
public class LoginController {

    @Autowired
    AppUserRepository appUserRepository;

    @Autowired
    ApplicantRepository applicantRepository;

    @Autowired
    AppUserService appUserService;

    @Autowired
    LoginService loginService;

    @Autowired
    AuthenticationManager authenticationManager;

    @GetMapping("/adminlogin")
    public String loginForm(Model model, HttpServletRequest request) {
//        Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);
//        if (flashMap != null) {
//            String emailId2 =  (String) flashMap.get("message");
//            System.out.println(emailId2);
//        }
        model.addAttribute("login", new LoginRequest());
        return "login_form";
    }

    @PostMapping("/adminlogin")
    public String Login(@ModelAttribute("login") LoginRequest loginRequest, HttpServletRequest request, BindingResult result, RedirectAttributes redirAttrs) {
        if (result.hasErrors()) {
            return "login_form";
        }
//        try {
//            loginService.authenticateUser(loginRequest.getUsername(), loginRequest.getPassword());
//        } catch (Exception e) {
//           result.rejectValue("",  loginRequest.getPassword(), "Invalid Username or Password");
//           return "login_form";
//        }
        try {
            Boolean login = loginService.authenticateUser(loginRequest.getUsername(), loginRequest.getPassword());
            if (login == true) {
                HttpSession session = request.getSession();
                session.setAttribute("userName", loginRequest.getUsername());
//            return "users";
//                if (!session.isNew()) {
//                    return "redirect:/users";
//                }
                return "redirect:/users";
            }
        } catch (Exception e) {
            result.rejectValue("",  loginRequest.getPassword(), "Invalid Username or Password");
           return "login_form";
        }
            return "redirect:/users";
        }
//        if (!everythingOkay()) {
//            redirAttrs.addFlashAttribute("error", "The error XYZ occurred.");
//            return "redirect:/settings/";
//        }
//        myService.doSomething();
//        redirAttrs.addFlashAttribute("success", "Everything went just fine.");
//        return "redirect:/settings/";


    @PostMapping("/invalidate")
    public String destroySession(HttpServletRequest request, RedirectAttributes result) {
        //invalidate the session , this will clear the data from configured database (Mysql/redis/hazelcast)
        request.getSession().invalidate();

        result.addFlashAttribute("message", "Successfully signed out");
        return "redirect:/adminlogin";
    }

}


