package com.sdg.countryapi.controller;

import com.sdg.countryapi.dto.CountryDto;
import com.sdg.countryapi.exception.CountryNotFoundException;
import com.sdg.countryapi.service.CountryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/data/country")
public class CountryController {

    private final CountryService service;

    public CountryController(CountryService service) {
        this.service = service;
    }

    @PostMapping
    ResponseEntity<CountryDto> guardarOActualizarCountry(@Valid @RequestBody CountryDto countryDto){
        CountryDto countryGuardado = service.guardarOActualizarCountry(countryDto);
        return new ResponseEntity<>(countryGuardado, HttpStatus.OK);
    }

    @GetMapping
    ResponseEntity<List<CountryDto>> listarCountries(){

        List<CountryDto> countriesList = service.listarTodosCountries();

        if (countriesList.isEmpty()) {
            throw new CountryNotFoundException("¡Lista vacía! No se encontraron países en la base de datos.");
        }
        return new ResponseEntity<>(countriesList, HttpStatus.OK);
    }
}
