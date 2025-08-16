package com.challenge.aluralatam.library.dto;

import java.util.List;

public class LibroDTO {
    private String title;
    private List<String> authors;
    private String language;
    private List<String> birthDates;
    private List<String> deathDates;

    public LibroDTO(String title, List<String> authors, String language, List<String> birthDates, List<String> deathDates) {
        this.title = title;
        this.authors = authors;
        this.language = language;
        this.birthDates = birthDates;
        this.deathDates = deathDates;
    }

    public String getTitle() { return title; }
    public List<String> getAuthors() { return authors; }
    public String getLanguage() { return language; }
    public List<String> getBirthDates() { return birthDates; }
    public List<String> getDeathDates() { return deathDates; }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Libro: ").append(title)
                .append("\n   Idioma: ").append(language)
                .append("\n   Autores: ");
        for (int i = 0; i < authors.size(); i++) {
            sb.append("\n      ").append(authors.get(i))
                    .append(" (Nacimiento: ").append(birthDates.get(i))
                    .append(", Muerte: ").append(deathDates.get(i)).append(")");
        }
        sb.append("\n");
        return sb.toString();
    }
}
