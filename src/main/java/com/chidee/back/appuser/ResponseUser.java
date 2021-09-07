package com.chidee.back.appuser;

public class ResponseUser {
    private Long id;
    private String firstName;
    private String surname;
    private String email;
    private String phoneNumber;
    private String coverLetter;
    private String filedb_id;

    public ResponseUser(Long id, String firstName, String surname, String email, String phoneNumber, String coverLetter, String filedb_id) {
        this.id = id;
        this.firstName = firstName;
        this.surname = surname;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.coverLetter = coverLetter;
        this.filedb_id = filedb_id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCoverLetter() {
        return coverLetter;
    }

    public void setCoverLetter(String coverLetter) {
        this.coverLetter = coverLetter;
    }

    public String getFiledb_id() {
        return filedb_id;
    }

    public void setFiledb_id(String filedb_id) {
        this.filedb_id = filedb_id;
    }
}
