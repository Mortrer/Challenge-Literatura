package com.challenge.aluralatam.library.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record GutendexAutor (
        String name,
        Integer birth_year,
        Integer death_year
){ }
