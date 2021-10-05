package com.superapp.firstdemo.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NaturalId;

@Entity
@Data
//@NoArgsConstructor
@Table(name = "roles")
public class Role implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @NaturalId
    @Column(name = "name")
    private RoleName name;

   /* public Role(RoleName name) {
        this.name = name;
    }*/
}
