package com.challenge.aluralatam.library.model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="Libros")
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    private String language;
    private Integer downloadCount;
    private Integer publicationYear;

    @ManyToMany
    @JoinTable(
            name = "book_author",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id")
    )

    private Set<Autor> authors = new HashSet<>();

    public Libro(){}

    public Libro(String title, String language, Integer downloadCount, Integer publicationYear){
        this.title = title;
        this.language = language;
        this.downloadCount = downloadCount;
        this.publicationYear = publicationYear;
    }

    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getLanguage() { return language; }
    public Integer getDownloadCount() { return downloadCount; }
    public Integer getPublicationYear() { return publicationYear; }
    public Set<Autor> getAuthors() { return authors; }

    public void setTitle(String title) { this.title = title; }
    public void setLanguage(String language) { this.language = language; }
    public void setDownloadCount(Integer downloadCount) { this.downloadCount = downloadCount; }
    public void setPublicationYear(Integer publicationYear) { this.publicationYear = publicationYear; }




}
