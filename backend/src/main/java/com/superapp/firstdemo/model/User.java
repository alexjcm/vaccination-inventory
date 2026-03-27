package com.superapp.firstdemo.model;

import java.io.Serializable;
import java.time.Instant;
import java.util.Date;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

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
@Table(name = "usuario", uniqueConstraints = {@UniqueConstraint(columnNames = {"auth0Id"}),
        @UniqueConstraint(columnNames = {"email"})})
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank
    @jakarta.persistence.Column(unique = true, nullable = false)
    private String auth0Id;

    private Integer identCard;
    @Size(max = 64)
    private String firstName;
    @Size(max = 64)
    private String lastName;
    private String email;
    private Date dateOfBirth;
    private String homeAddress;
    //@Size(max = 15) //UIT-T Standard, E.164 Format
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
