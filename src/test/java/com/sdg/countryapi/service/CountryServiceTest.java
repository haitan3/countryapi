package com.sdg.countryapi.service;

import com.sdg.countryapi.client.ExternalCountryClient;
import com.sdg.countryapi.client.data.ExternalResponse;
import com.sdg.countryapi.dto.CountryDto;
import com.sdg.countryapi.entity.Country;
import com.sdg.countryapi.exception.CountryNotFoundException;
import com.sdg.countryapi.mapper.CountryMapper;
import com.sdg.countryapi.repository.CountryRepository;
import com.sdg.countryapi.service.imp.CountryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CountryServiceTest {

    @Mock
    private CountryRepository repository;

    @Mock
    private CountryMapper mapper;

    @InjectMocks
    private CountryServiceImpl service;

    @Mock
    private ExternalCountryClient externalCountryClient;

    private CountryDto countryDto;
    private Country country;
    private ExternalResponse externalResponse;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        countryDto = new CountryDto();
        countryDto.setCountry("Bulgaria");
        countryDto.setPopulation(6927288);

        country = new Country();
        country.setId(1L);
        country.setCountry("Bulgaria");
        country.setPopulation(6927288);
        externalResponse = new ExternalResponse(new ExternalResponse.Name("Bulgaria"), 6927288);

    }

    @Test
    void testGuardarOActualizarCountrySuccess(){
        when(externalCountryClient.obtenerCountryApiExt(countryDto.getCountry())).thenReturn(countryDto);
        when(mapper.toCountryDto(externalResponse)).thenReturn(countryDto);

        when(mapper.toCountry(any(CountryDto.class))).thenReturn(country);

        when(repository.save(any(Country.class))).thenReturn(country);


        CountryDto result = service.guardarOActualizarCountry(countryDto);

        //Verificamos que los valores sean iguales y que no sea null el resultado

        assertNotNull(result);
        assertEquals(countryDto.getCountry(),result.getCountry());
        assertEquals(countryDto.getPopulation(),result.getPopulation());
        //Verificamos que un registro de la clase Country ha sido guardado
        verify(repository,times(1)).save(any(Country.class));
        verify(externalCountryClient, times(1)).obtenerCountryApiExt(countryDto.getCountry());

    }


    @Test
    void testGuardarOActualizarCountryExistente() {

        when(repository.findByCountry(countryDto.getCountry())).thenReturn(Optional.of(country));

        when(repository.save(any(Country.class))).thenReturn(country);

        CountryDto result=service.guardarOActualizarCountry(countryDto);
        assertNotNull(result);
        assertEquals(countryDto.getCountry(),result.getCountry());
        assertEquals(countryDto.getPopulation(),result.getPopulation());

        //verificamos que el registro se ha actualizado ya que existia en la BBDD
        verify(repository,times(1)).save(any(Country.class));

        //Verificamos que no se ha ejecutado el método obtenerCountryApiExt() ya que el registro existía en la BBDD
        verify(externalCountryClient,times(0)).obtenerCountryApiExt(any(String.class));

    }

    @Test
    void testGuardarOActualizarCountryNotFoundApi() {

        //cuando el country no fue encontrado, me devuelve vacío
        when(repository.findByCountry(countryDto.getCountry())).thenReturn(Optional.empty());
        when(externalCountryClient.obtenerCountryApiExt(countryDto.getCountry())).thenThrow(new CountryNotFoundException("País no encontrado"));
        when(repository.save(any(Country.class))).thenReturn(new Country(countryDto.getCountry(),0));

        CountryDto result = service.guardarOActualizarCountry(countryDto);

        assertNotNull(result);

        assertEquals(countryDto.getCountry(), result.getCountry());
        assertEquals(0, result.getPopulation());

        verify(repository,times(1)).save(any(Country.class));
        verify(externalCountryClient,times(1)).obtenerCountryApiExt(countryDto.getCountry());

    }

    @Test
    void listarTodoslosCountries(){
        List<Country> countriesList = new ArrayList<>();
        countriesList.add(new Country("Bulgaria", 33333));
        countriesList.add(new Country("Spain", 22222));

        when(repository.findAll()).thenReturn(countriesList);

        List<CountryDto> result = service.listarTodosCountries();

        assertNotNull(result);
        assertEquals(2,result.size());
        assertEquals(countriesList.get(0).getCountry(),result.get(0).getCountry());
        assertEquals(countriesList.get(0).getPopulation(),result.get(0).getPopulation());
        assertEquals(countriesList.get(1).getCountry(),result.get(1).getCountry());
        assertEquals(countriesList.get(1).getPopulation(),result.get(1).getPopulation());

        verify(repository,times(1)).findAll();

    }

    @Test
    void testListarTodosCountriesEmpty() {
        when(repository.findAll()).thenReturn(new ArrayList<>());

        List<CountryDto> result = service.listarTodosCountries();

        assertNotNull(result);
        assertEquals(0,result.size());
        verify(repository,times(1)).findAll();

    }
}
