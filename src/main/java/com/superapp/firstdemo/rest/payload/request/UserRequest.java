package com.superapp.firstdemo.rest.payload.request;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class UserRequest {

    @NotNull
    private Integer identCard;
    @NotBlank
    @Size(max = 64)
    private String firstName;
    @NotBlank
    @Size(max = 64)
    private String lastName;
    @NotBlank
    private String email;

}
