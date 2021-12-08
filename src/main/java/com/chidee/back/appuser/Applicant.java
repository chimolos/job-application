package com.chidee.back.appuser;

import com.chidee.back.appuser.uploadtypes.FileDB;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@NoArgsConstructor
@Data
public class Applicant extends AppUser{

    @NotBlank
    private String phoneNumber;

    @NotBlank
//    @Size(min = 4, message = "Cover letter too short. Please complete")
    private String coverLetter;

    @NotBlank
    private String passportPhoto;

    private String passportPath;

    @NotBlank
    private String resume;

    private String resumePath;

    public Applicant(String firstName, String surname, String email,  String phoneNumber, String coverLetter, String passportPhoto,  String resume, AppUserRole appUserRole) {
        super(firstName, surname, email, appUserRole);
        this.phoneNumber = phoneNumber;
        this.coverLetter = coverLetter;
        this.passportPhoto = passportPhoto;
        this.passportPath = passportPath;
        this.resume = resume;
        this.resumePath = resumePath;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getCoverLetter() {
        return coverLetter;
    }

}
