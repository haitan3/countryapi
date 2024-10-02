package com.sdg.countryapi.controller;

import com.sdg.countryapi.dto.CountryDto;
import com.sdg.countryapi.service.CountryService;
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
    ResponseEntity<CountryDto> guardarOActualizarCountry(@RequestBody CountryDto countryDto){
        CountryDto countryGuardado = service.guardarOActualizarCountry(countryDto);
        return new ResponseEntity<>(countryGuardado, HttpStatus.OK);
    }

    @GetMapping
    ResponseEntity<List<CountryDto>> listarCountries(){

        List<CountryDto> countriesList = service.listarTodosCountries();
        return new ResponseEntity<>(countriesList, HttpStatus.OK);
    }
}
