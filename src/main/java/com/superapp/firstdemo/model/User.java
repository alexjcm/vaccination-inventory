package com.superapp.firstdemo.model;

import java.io.Serializable;
import java.time.Instant;
import java.util.Date;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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
    @Size(max = 64)
    private String firstName;
    @NotBlank
    @Size(max = 64)
    private String lastName;
    @NotBlank
    private String email;
    private String username;
    private String password;
    private Date dateOfBirth;
    private String homeAddress;
    @Size(max = 15) //UIT-T Standard, E.164 Format
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

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private List<Role> roles;

    @ManyToOne
    @JoinColumn(name = "vaccine_id", referencedColumnName = "id")
    private Vaccine vaccine;

}
