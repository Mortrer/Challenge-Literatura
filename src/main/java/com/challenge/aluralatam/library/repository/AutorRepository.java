package com.challenge.aluralatam.library.repository;

import com.challenge.aluralatam.library.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface AutorRepository extends JpaRepository<Autor, Long> {
    Optional<Autor> findByName(String name);
}