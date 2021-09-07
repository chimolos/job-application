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

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "uploads_id", referencedColumnName = "id")
    private FileDB fileDB;

    public Applicant(String firstName, String surname, String email, String phoneNumber,
                     String coverLetter, FileDB fileDB, AppUserRole appUserRole) {
        super(firstName, surname, email, appUserRole );
        this.phoneNumber = phoneNumber;
        this.coverLetter = coverLetter;
        this.fileDB = fileDB;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getCoverLetter() {
        return coverLetter;
    }

}
