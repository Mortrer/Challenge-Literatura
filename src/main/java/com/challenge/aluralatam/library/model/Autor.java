package com.challenge.aluralatam.library.model;

import com.challenge.aluralatam.library.model.Libro;
import jakarta.persistence.*;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="authors")
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private LocalDate birthDate;
    private LocalDate deathDate;

    @ManyToMany(mappedBy ="authors")
    private Set<Libro> books = new HashSet<>();

    public Autor(){}

    public Autor(String name, LocalDate birthDate, LocalDate deathDate ){
        this.name = name;
        this.birthDate = birthDate;
        this.deathDate = deathDate;

    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public LocalDate getBirthDate() { return birthDate; }
    public LocalDate getDeathDate() { return deathDate; }
    public Set<Libro> getBooks() { return books; }

    public void setName(String name) { this.name = name; }
    public void setBirthDate(LocalDate birthDate) { this.birthDate = birthDate; }
    public void setDeathDate(LocalDate deathDate) { this.deathDate = deathDate; }
}
