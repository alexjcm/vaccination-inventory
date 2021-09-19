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
    private Integer identCard; // cedula
    private String firstName; //nombres
    private String lastName; //apellidos
    private String email; //correo
    private String username;
    private String password; //clave
    private Date dateOfBirth; //fecha_nacimiento
    private String homeAddress; //direccion
    private Integer cellPhoneNumber; //celular
    private Boolean isVaccinated; //estado_vacuna
    //@Column(name="vaccine_id")
    //private Integer vaccine_id;
    private Date dateOfVaccinated; //fecha_vacuna
    private Integer numberOfDoses; //dosis
    //@Column(name="role_id")
    //private Integer role_id;
    private Boolean status; //estado
    @ManyToOne
    @JoinColumn(name = "role_id", referencedColumnName = "id")
    private Role role;

    @ManyToOne
    @JoinColumn(name = "vaccine_id", referencedColumnName = "id")
    private Vaccine vaccine;

}
