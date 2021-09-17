package com.superapp.demo.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

@Entity
@Data
@Table(name = "employee")
public class Employee implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    //Registrado solo por el Admin
    @Size(min = 10, max = 10)
    //@NotNull(message = "First Name cannot be null")
    @Column(nullable = false, unique = true)
    private String identCard; //cédula    
    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;
    @Column(nullable = false)
    private String email;
    // actualizable por el empleado 
    @Temporal(TemporalType.DATE)
    private Date dateOfBirth;
    private String homeAddress;
    private String cellPhoneNumber;
    private boolean isVaccinated;
    @Temporal(TemporalType.DATE)
    private Date dateOfVaccination;
    private String numberOfDoses;
    //-----------------------------------------
    // extras generaodos  automáticamente al crear empleado
    // @Column(name = "username", nullable = false, unique = true)
    private String username;
    //@Column(name = "password", nullable = false, unique = false)
    private String password;
    //-------------------------------------------

    // actualizable solo por el empleado
    //private String vaccineType;
    @ManyToOne
    @JoinColumn(name = "id_vaccine")
    private Vaccine vacinne;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    /*@PrePersist
    private void setUUID() {
        id = UUID.randomUUID().toString().replace("-", "");
        //password = PasswordUtil.digestPassword(password);
    }*/
}
