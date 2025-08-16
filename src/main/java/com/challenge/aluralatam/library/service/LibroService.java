package com.challenge.aluralatam.library.service;

import com.challenge.aluralatam.library.dto.LibroDTO;
import com.challenge.aluralatam.library.model.Libro;
import com.challenge.aluralatam.library.model.Autor;
import com.challenge.aluralatam.library.repository.LibroRepository;
import com.challenge.aluralatam.library.repository.AutorRepository;
import com.challenge.aluralatam.library.model.GutendexBook;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LibroService {

    private final LibroRepository libroRepository;
    private final AutorRepository autorRepository;

    public LibroService(LibroRepository libroRepository, AutorRepository autorRepository) {
        this.libroRepository = libroRepository;
        this.autorRepository = autorRepository;
    }

    @Transactional
    public void guardarLibroDesdeAPI(GutendexBook apiBook) {
        Libro libro = new Libro(
                apiBook.title(),
                apiBook.languages().isEmpty() ? "Desconocido" : apiBook.languages().get(0),
                apiBook.download_count(),
                null
        );

        apiBook.authors().forEach(apiAuthor -> {
            Autor autor = autorRepository.findByName(apiAuthor.name())
                    .orElseGet(() -> new Autor(
                            apiAuthor.name(),
                            apiAuthor.birth_year() != null ? LocalDate.of(apiAuthor.birth_year(), 1, 1) : null,
                            apiAuthor.death_year() != null ? LocalDate.of(apiAuthor.death_year(), 1, 1) : null
                    ));
            autorRepository.save(autor);
            libro.getAuthors().add(autor);
        });

        libroRepository.save(libro);
        System.out.println("Libro guardado en la BD:");
        System.out.println("Título: " + libro.getTitle());
    }

    @Transactional(readOnly = true)
    public List<LibroDTO> listarLibros() {
        return libroRepository.findAllWithAuthors().stream()
                .map(l -> new LibroDTO(
                        l.getTitle(),
                        l.getAuthors().stream().map(Autor::getName).toList(),
                        l.getLanguage(),
                        l.getAuthors().stream()
                                .map(a -> a.getBirthDate() != null ? a.getBirthDate().toString() : "")
                                .toList(),
                        l.getAuthors().stream()
                                .map(a -> a.getDeathDate() != null ? a.getDeathDate().toString() : "")
                                .toList()
                ))
                .toList();
    }

    @Transactional(readOnly = true)
    public List<LibroDTO> listarLibrosPorAutorVivoEn(int year) {
        List<Libro> libros = libroRepository.findByAuthorsAliveInYear(year);

        return libros.stream()
                .map(l -> new LibroDTO(
                        l.getTitle(),
                        l.getAuthors().stream().map(Autor::getName).toList(),
                        l.getLanguage(),
                        l.getAuthors().stream()
                                .map(a -> a.getBirthDate() != null ? a.getBirthDate().toString() : "")
                                .toList(),
                        l.getAuthors().stream()
                                .map(a -> a.getDeathDate() != null ? a.getDeathDate().toString() : "")
                                .toList()
                ))
                .collect(Collectors.toList());
    }

    private List<LibroDTO> mapToDTO(List<Libro> libros) {
        return libros.stream()
                .map(l -> new LibroDTO(
                        l.getTitle(),
                        l.getAuthors().stream().map(Autor::getName).toList(),
                        l.getLanguage(),
                        l.getAuthors().stream()
                                .map(a -> a.getBirthDate() != null ? a.getBirthDate().toString() : "")
                                .toList(),
                        l.getAuthors().stream()
                                .map(a -> a.getDeathDate() != null ? a.getDeathDate().toString() : "")
                                .toList()
                ))
                .toList();
    }

    public List<LibroDTO> listarPorAutor(String nombreAutor) {
        return mapToDTO(libroRepository.findByAuthorNameIgnoreCase(nombreAutor));
    }

    public List<LibroDTO> listarPorIdioma(String idioma) {
        return mapToDTO(libroRepository.findByLanguageIgnoreCase(idioma));
    }

    public List<LibroDTO> listarPorAutoresVivos(int anio) {
        return mapToDTO(libroRepository.findByAuthorsAliveInYear(anio));
    }
}


