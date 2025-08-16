package com.challenge.aluralatam.library.service;
import com.challenge.aluralatam.library.model.GutendexBook;
import com.challenge.aluralatam.library.model.GutendexResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

public class LibraryGutendexService {

    public static Optional<GutendexBook> consultaLibrary(String tituloLibro){
        try {
            String busquedaLibro = tituloLibro.toLowerCase();
            String tituloC = URLEncoder.encode(busquedaLibro, StandardCharsets.UTF_8).replace("+", "%20");
            final String direccion = "https://gutendex.com/books/?search="+tituloC ;

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(direccion))
                    .build();

            HttpResponse<String> response = client
                    .send(request, HttpResponse.BodyHandlers.ofString());
            String json = response.body();

            ObjectMapper mapper = new ObjectMapper();
            GutendexResponse gutendexResponse = mapper.readValue(json, GutendexResponse.class);
            return gutendexResponse.results().stream()
                    .filter(book -> book.title().toLowerCase().contains(tituloLibro.toLowerCase()))
                    .findFirst();

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }
    }
