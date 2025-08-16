package com.challenge.aluralatam.library.repository;

import com.challenge.aluralatam.library.model.Autor;
import com.challenge.aluralatam.library.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;


public interface LibroRepository extends JpaRepository<Libro, Long> {

    @Query("SELECT l FROM Libro l JOIN FETCH l.authors")
    List<Libro> findAllWithAuthors();

    List<Libro> findByLanguage(String language);

    // Buscar libros que tengan un autor con nombre específico
    @Query("SELECT DISTINCT l FROM Libro l JOIN FETCH l.authors a WHERE LOWER(a.name) LIKE LOWER(CONCAT('%', :nombreAutor, '%'))")
    List<Libro> findByAuthorNameIgnoreCase(@Param("nombreAutor") String nombreAutor);

    @Query("SELECT DISTINCT l FROM Libro l JOIN FETCH l.authors WHERE LOWER(l.language) = LOWER(:idioma)")
    List<Libro> findByLanguageIgnoreCase(String idioma);

    @Query("SELECT DISTINCT l FROM Libro l " +
            "JOIN FETCH l.authors a " +
            "WHERE EXTRACT(YEAR FROM a.birthDate) <= :year " +
            "AND (a.deathDate IS NULL OR EXTRACT(YEAR FROM a.deathDate) >= :year)")
    List<Libro> findByAuthorsAliveInYear(@Param("year") int year);


}