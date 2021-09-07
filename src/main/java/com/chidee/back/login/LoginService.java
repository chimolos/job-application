package com.chidee.back.login;

import com.chidee.back.appuser.*;
import com.chidee.back.appuser.AppUserRepository;
import com.chidee.back.appuser.AppUserService;
import com.chidee.back.appuser.AppUser;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class LoginService {

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    private final AppUserService appUserService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    public Boolean authenticateUser(String user, String password) {
//        String encodedPassword2 = bCryptPasswordEncoder.encode(password);
        Optional<AppUser> opFileDB = appUserRepository.findByEmail(user);
        if (opFileDB.isPresent()) {
            AppUser fileDb = opFileDB.get();
            System.out.println(fileDb.getUsername() + " " + fileDb.getPassword());
            if (fileDb.getPassword().equals(password))
                return true;
            else {
                throw new IllegalStateException("Incorrect password");
            }
        }
        return false;
    }
    public boolean isAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || AnonymousAuthenticationToken.class.
            isAssignableFrom(authentication.getClass())) {
            return false;
        }
        return authentication.isAuthenticated();
    }

}
