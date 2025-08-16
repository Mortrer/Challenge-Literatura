package com.challenge.aluralatam.library.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record GutendexBook(
        Long id,
        String title,
        List<GutendexAutor> authors,
        List<String> languages,
        Integer download_count,
        List<String> summaries
) { }
