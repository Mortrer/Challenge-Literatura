package com.challenge.aluralatam.library;

import com.challenge.aluralatam.library.dto.LibroDTO;
import com.challenge.aluralatam.library.model.Autor;
import com.challenge.aluralatam.library.model.GutendexBook;
import com.challenge.aluralatam.library.model.Libro;
import com.challenge.aluralatam.library.repository.AutorRepository;
import com.challenge.aluralatam.library.repository.LibroRepository;
import com.challenge.aluralatam.library.service.LibraryGutendexService;
import com.challenge.aluralatam.library.service.LibroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

@SpringBootApplication
public class LibraryApplication implements CommandLineRunner {

	@Autowired
	private LibroRepository libroRepository;

	@Autowired
	private AutorRepository autorRepository;

	@Autowired
	private LibroService libroService;

	public static void main(String[] args) {
		SpringApplication.run(LibraryApplication.class, args);
	}

	@Override
	public void run(String... args) {
		Scanner scanner = new Scanner(System.in);
		int opcion = -1;

		do {
			System.out.println("----------- Menú Principal -----------");
			System.out.println("1- Búsqueda de libro por título (y guardar en BD)");
			System.out.println("2- Listar libros registrados");
			System.out.println("3- Listar autores registrados");
			System.out.println("4- Listar autores vivos en determinado año");
			System.out.println("5- Listar libros por idioma");
			System.out.println("0- Salir");

			// Bloque de ingreso seguro de opción
			try {
				opcion = Integer.parseInt(scanner.nextLine().trim());
			} catch (NumberFormatException e) {
				System.out.println("Por favor ingrese un número válido.");
				continue;
			}

			switch (opcion) {
				case 1 -> {
					System.out.println("Ingrese el título:");
					String tituloLibro = scanner.nextLine().trim();
					if (tituloLibro.isEmpty()) {
						System.out.println("Título vacío, no se puede buscar.");
						break;
					}

					Optional<GutendexBook> libroOpt = LibraryGutendexService.consultaLibrary(tituloLibro);

					if (libroOpt.isPresent()) {
						libroService.guardarLibroDesdeAPI(libroOpt.get());
					} else {
						System.out.println("No se encontró libro con el título ingresado.");
					}
				}

				case 2 -> {
					System.out.println("*----------Lista de Libros Registrados----------*");
					List<LibroDTO> libros = libroService.listarLibros();
					if (libros.isEmpty()) {
						System.out.println("No hay libros registrados.");
					} else {
						libros.forEach(dto -> System.out.println(dto.toString()));
					}
					System.out.println("------------------------------");
				}

				case 3 -> {
					System.out.println("Ingrese nombre del Autor:");
					String nombreAutor = scanner.nextLine().trim();
					if (nombreAutor.isEmpty()) {
						System.out.println("Nombre vacío, no se puede buscar.");
						break;
					}

					List<LibroDTO> libros = libroService.listarPorAutor(nombreAutor);
					if (libros.isEmpty()) {
						System.out.println("No se encontraron libros de ese autor.");
					} else {
						libros.forEach(System.out::println);
					}
				}

				case 4 -> {
					System.out.println("Ingrese año:");
					int anio;
					try {
						anio = Integer.parseInt(scanner.nextLine().trim());
					} catch (NumberFormatException e) {
						System.out.println("Año inválido.");
						break;
					}

					List<LibroDTO> libros = libroService.listarLibrosPorAutorVivoEn(anio);
					if (libros.isEmpty()) {
						System.out.println("No se encontraron autores vivos en ese año.");
					} else {
						libros.forEach(System.out::println);
					}
				}

				case 5 -> {
					System.out.println("Ingresar idioma (español o ingles)");
					String idioma = scanner.nextLine().trim().toLowerCase();

					// Mapear a los códigos de idioma usados en la Base de datos
					String idiomaBuscado = switch (idioma) {
						case "español" -> "es";
						case "ingles" -> "en";
						default -> idioma; // por si se ingresa directamente "es" o "en"
					};

					System.out.println("Lista de Libros por idioma: " + idioma);

					List<LibroDTO> libros = libroService.listarPorIdioma(idiomaBuscado);

					if (libros.isEmpty()) {
						System.out.println("No se encontraron libros en ese idioma.");
					} else {
						libros.forEach(System.out::println);
					}
				}

				case 0 -> {
					System.out.println("Saliendo...");
					System.exit(0);
				}

				default -> System.out.println("Opción no válida.");
			}

		} while (opcion != 0);

		scanner.close();
	}
}
