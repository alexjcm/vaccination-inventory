package com.superapp.firstdemo.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.validation.constraints.Size;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Entity
@Data
@Table(name = "usuario")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Size(min = 10, max = 10)
    private Integer identCard;
    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private String password;
    private Date dateOfBirth;
    private String homeAddress;
    private Integer cellPhoneNumber;
    private Boolean status;
    private Boolean isVaccinated;
    private Date dateOfVaccinated;
    private Integer numberOfDoses;

    @ManyToOne
    @JoinColumn(name = "role_id", referencedColumnName = "id")
    private Role role;

    @ManyToOne
    @JoinColumn(name = "vaccine_id", referencedColumnName = "id")
    private Vaccine vaccine;

}
