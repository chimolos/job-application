package com.chidee.back.registration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
//@RequestMapping(path = "api/register")
//@AllArgsConstructor
public class ApplicantRegController {

    @Autowired
    private RegistrationService registrationService;

    private RegistrationRequestAdmin registrationRequestAdmin;

    @PostMapping(path = "/applicantreg")
    public String register(@ModelAttribute("applicant") RegistrationRequest request, @RequestParam("passportPhoto") MultipartFile file1, @RequestParam("resume") MultipartFile file2) throws IOException {
        return registrationService.register(request, file1, file2);

    }





}
