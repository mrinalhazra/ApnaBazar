package com.app.apnabazar.model;

import javax.persistence.*;
import javax.persistence.GeneratedValue;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Entity
@NoArgsConstructor
public class Roles{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    public Roles(Long id, String name) {
        this.id = id;
        this.name = name;
    }

}
