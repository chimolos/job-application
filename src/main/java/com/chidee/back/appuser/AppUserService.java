package com.chidee.back.appuser;

import com.chidee.back.appuser.uploadtypes.FileDB;
import com.chidee.back.appuser.uploadtypes.FileStorageService;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@Service
@AllArgsConstructor
public class AppUserService implements UserDetailsService {

    @Autowired
    Cloudinary cloudinaryConfig;

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

//        String passportPhotoFileName = StringUtils.cleanPath(file1.getOriginalFilename());
        String resumeFileName = StringUtils.cleanPath(file2.getOriginalFilename());

        try {
            File uploadedFile1 = convertMultiPartToFile(file1);
            File uploadedFile2 = convertMultiPartToFile(file2);

            Map uploadResult1 = cloudinaryConfig.uploader().upload(uploadedFile1, ObjectUtils.asMap("use_filename", true, "folder", "passportPhotos"));
            Map uploadResult2 = cloudinaryConfig.uploader().upload(uploadedFile2, ObjectUtils.asMap("use_filename", true, "folder", "resumes", "resource_type", "raw"));
            String url1 = uploadResult1.get("url").toString();
            appUser.setPassportPath(url1);
            String url2 = uploadResult2.get("url").toString();
            appUser.setResumePath(url2);

            applicantRepository.save(appUser);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // TODO: Send confirmation token
        return "Application successful";
    }

    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }

    public String signUpAdmin(AppUser admin) {
        boolean userExists = appUserRepository
                .findByEmail(admin.getEmail())
                .isPresent();

        if (userExists) {
            throw new IllegalStateException("email already taken");
        }


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
