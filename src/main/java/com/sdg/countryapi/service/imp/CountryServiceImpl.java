package com.sdg.countryapi.service.imp;

import com.sdg.countryapi.client.ExternalCountryClient;
import com.sdg.countryapi.dto.CountryDto;
import com.sdg.countryapi.entity.Country;
import com.sdg.countryapi.exception.CountryNotFoundException;
import com.sdg.countryapi.mapper.CountryMapper;
import com.sdg.countryapi.repository.CountryRepository;
import com.sdg.countryapi.service.CountryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CountryServiceImpl implements CountryService {


    @Autowired
    private ExternalCountryClient externalCountryApi;
    @Autowired
    private CountryRepository repository;
    @Autowired
    private CountryMapper mapper;


    @Override
    public CountryDto  guardarOActualizarCountry(CountryDto countryDto) {
       Optional<Country> countryRepository = repository.findByCountry(countryDto.getCountry());

       if (countryRepository.isPresent()){
           log.info("El Country con nombre {} ya existe en la BBDD, pero se ha actualizado." ,countryDto.getCountry());
           //si está presente se actualiza el registro
           Country country = countryRepository.get();
           country.setPopulation(countryDto.getPopulation());
           repository.save(country);

           return new CountryDto(country.getCountry(),country.getPopulation());

       }else {

           log.info("El Country con nombre {} no existente en BBDD , lo guardamos a través de la API Externa." ,countryDto.getCountry());
        try {
            CountryDto externalCountryDto = externalCountryApi.obtenerCountryApiExt(countryDto.getCountry());
            //Antes de guardar el nuevo registro hay que hacer el mapeo al objeto Entity Country
            repository.save(mapper.toCountry(externalCountryDto));
            return externalCountryDto;
        } catch (CountryNotFoundException e){

        log.warn("No se pudo obtener información del país {} desde la API externa. Lo guardamos en la BBDD con los datos por defecto: population -> 0.", countryDto.getCountry());
        // Guardar el país con datos predeterminados
        Country countryToSave = new Country(countryDto.getCountry(), 0);
        repository.save(countryToSave);
        // Devolver un DTO con población 0
        return new CountryDto(countryDto.getCountry(), 0);
        }
    }
}

    public List<CountryDto> listarTodosCountries() {
        List<Country> countriesRepository= repository.findAll();

        return countriesRepository.stream()
                .map(country -> {
                    Integer population = country.getPopulation();
                    // Maneja el caso donde population sea null
                    return new CountryDto(
                            country.getCountry(),
                            population != null ? population : 0
                    );
                })
                .collect(Collectors.toList());
    }
}
