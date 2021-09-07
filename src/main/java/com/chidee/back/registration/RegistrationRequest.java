package com.chidee.back.registration;

import lombok.*;

import javax.validation.constraints.NotBlank;


@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Setter
public class RegistrationRequest {
    @NotBlank
    private final String firstName;
    private final String surname;
    private final String email;
    private final String password;
    private final String phoneNumber;
    private final String coverLetter;

}
