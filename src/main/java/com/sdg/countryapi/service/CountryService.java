package com.sdg.countryapi.service;

import com.sdg.countryapi.dto.CountryDto;

import java.util.List;

public interface CountryService {

    public CountryDto guardarOActualizarCountry(CountryDto countryDto);
    public List<CountryDto> listarTodosCountries();
}
