package com.chidee.back.registration;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationRequestAdmin {
    private  String firstName;
    private  String surname;
    private  String email;
    private String password;
}
