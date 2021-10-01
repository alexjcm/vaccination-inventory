package com.superapp.firstdemo.model;

import java.io.Serializable;
import java.time.Instant;
import java.util.Date;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(
        value = {"createdAt", "updatedAt"},
        allowGetters = true
)
@Data
//@Table(name = "usuario")
@Table(name = "usuario", uniqueConstraints = {@UniqueConstraint(columnNames = {"username"}),
        @UniqueConstraint(columnNames = {"email"})})
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private Integer identCard;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @NotBlank
    private String email;
    private String username;
    private String password;
    private Date dateOfBirth;
    private String homeAddress;
    //@Pattern(regexp = "\\d{3}-\\d{3}-\\d{4}")
    private Integer cellPhoneNumber;
    private Boolean status;
    //private boolean enabled;
    private Boolean isVaccinated;
    private Date dateOfVaccinated;
    private Integer numberOfDoses;
    //// date audit ////
    @CreatedDate
    //@Column(nullable = false, updatable = false)
    private Instant createdAt;
    @LastModifiedDate
    //@Column(nullable = false)
    private Instant updatedAt;
    //////////////

    // old version
//    @ManyToOne
//    @JoinColumn(name = "role_id", referencedColumnName = "id")
//    private Role role;

    //new version
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private List<Role> roles;

    @ManyToOne
    @JoinColumn(name = "vaccine_id", referencedColumnName = "id")
    private Vaccine vaccine;

}
