package com.chidee.back.appuser.uploadtypes;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "uploads")
@NoArgsConstructor
@Data
public class FileDB {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    private String passportPhoto;

    private String passportType;

    @Lob
    private byte[] passportData;

    private String resume;

    private String resumeType;

    @Lob
    private byte[] resumeData;

    public FileDB(String passportPhoto, String passportType, byte[] passportData, String resume, String resumeType, byte[] resumeData) {
        this.passportPhoto = passportPhoto;
        this.passportType = passportType;
        this.passportData = passportData;
        this.resume = resume;
        this.resumeType = resumeType;
        this.resumeData = resumeData;
    }

    public String getId() {
        return id;
    }

    public String getPassportPhoto() {
        return passportPhoto;
    }

    public void setPassportPhoto(String passportPhoto) {
        this.passportPhoto = passportPhoto;
    }

    public String getPassportType() {
        return passportType;
    }

    public void setPassportType(String passportType) {
        this.passportType = passportType;
    }

    public byte[] getPassportData() {
        return passportData;
    }

    public void setPassportData(byte[] passportData) {
        this.passportData = passportData;
    }

    public String getResume() {
        return resume;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }

    public String getResumeType() {
        return resumeType;
    }

    public void setResumeType(String resumeType) {
        this.resumeType = resumeType;
    }

    public byte[] getResumeData() {
        return resumeData;
    }

    public void setResumeData(byte[] resumeData) {
        this.resumeData = resumeData;
    }
}





