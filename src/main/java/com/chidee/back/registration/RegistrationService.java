package com.chidee.back.registration;

import com.chidee.back.appuser.AppUser;
import com.chidee.back.appuser.AppUserRole;
import com.chidee.back.appuser.AppUserService;
import com.chidee.back.appuser.Applicant;
import com.chidee.back.appuser.uploadtypes.FileDB;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@AllArgsConstructor
public class RegistrationService {

    private final AppUserService appUserService;
    private final EmailValidator emailValidator;

    public String register(RegistrationRequest request, MultipartFile file1, MultipartFile file2) throws IOException {
        //to check if email is valid
        boolean isValidEmail = emailValidator.test(request.getEmail());
        if (!isValidEmail) {
            throw new IllegalStateException("email not valid");
        }
        if (appUserService.countUsers() == 4) {
            throw new IllegalStateException("Application closed");
        }
        return appUserService.signUpUser(
                new Applicant(
                        request.getFirstName(),
                        request.getSurname(),
                        request.getEmail(),
                        request.getPhoneNumber(),
                        request.getCoverLetter(),
                        new FileDB(file1.getOriginalFilename(), file1.getContentType(),file1.getBytes(),file2.getOriginalFilename(), file2.getContentType(), file2.getBytes()),
                        AppUserRole.USER
                ),
                file1, file2
        );
    }

    public String registerAdmin(RegistrationRequestAdmin request) {
        boolean isValidEmail = emailValidator.test(request.getEmail());
        if (!isValidEmail) {
            throw new IllegalStateException("email not valid");
        }
        if (appUserService.countAdmin() == 1) {
            throw new IllegalStateException("You are not allowed to register as an admin");
        }
            return appUserService.signUpAdmin(
                    new AppUser(
                            request.getFirstName(),
                            request.getSurname(),
                            request.getEmail(),
                            request.getPassword(),
                            AppUserRole.ADMIN
                    )
            );
    }
}
