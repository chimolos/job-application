package com.chidee.back.appuser;

import com.chidee.back.appuser.uploadtypes.FileDB;
import com.chidee.back.appuser.uploadtypes.FileStorageService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

@Service
@AllArgsConstructor
public class AppUserService implements UserDetailsService {

    private final static String USER_NOT_FOUND = "user with email %S not found";

    private final AppUserRepository appUserRepository;
    private final ApplicantRepository applicantRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final FileStorageService fileStorageService;

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        return (appUserRepository.findByEmail(username))
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                String.format(USER_NOT_FOUND, username)));
    }

    public String signUpUser(Applicant appUser, MultipartFile file1, MultipartFile file2) throws IOException {
        boolean userExists = appUserRepository
                .findByEmail(appUser.getEmail())
                .isPresent();

        if (userExists) {
            throw new IllegalStateException("email already taken");
        }

        FileDB fileDB = fileStorageService.store(file1, file2);

        appUser.setFileDB(fileDB);
        applicantRepository.save(appUser);

        // TODO: Send confirmation token
        return "Application successful";
    }

    public String signUpAdmin(AppUser admin) {
        boolean userExists = appUserRepository
                .findByEmail(admin.getEmail())
                .isPresent();

        if (userExists) {
            throw new IllegalStateException("email already taken");
        }

//        String encodedPassword = bCryptPasswordEncoder
//                .encode(admin.getPassword());
//
//        admin.setPassword(encodedPassword);

        appUserRepository.save(admin);

        // TODO: Send confirmation token
        return "Application successful";
    }

    public int countUsers(){
        return (int) appUserRepository.countByAppUserRole(AppUserRole.USER);
    }

    public int countAdmin(){
        return (int) appUserRepository.countByAppUserRole(AppUserRole.ADMIN);
    }

    public Stream<Applicant> getAllApplicants() {
        return applicantRepository.findAll().stream();
    }

    public List<Applicant> getUsers() {
        return applicantRepository.findAll();
    }

}
