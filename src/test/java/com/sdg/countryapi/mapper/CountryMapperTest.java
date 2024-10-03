package com.sdg.countryapi.mapper;

import com.sdg.countryapi.client.data.ExternalResponse;
import com.sdg.countryapi.dto.CountryDto;
import com.sdg.countryapi.entity.Country;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class CountryMapperTest {

    @Mock
    private CountryMapper countryMapper;
    private CountryDto countryDto;
    private Country country;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Inicializa los mocks

        countryDto = new CountryDto();
        countryDto.setCountry("Bulgaria");
        countryDto.setPopulation(6927288);

        country = new Country();
        country.setId(1L);
        country.setCountry("Bulgaria");
        country.setPopulation(6927288);
    }

    @Test
    void testMapeoToCountyDto() {
        // Simular el objeto JSON recibido de la API externa
        ExternalResponse externalResponse = new ExternalResponse(new ExternalResponse.Name("Bulgaria"), 6927288);
        when(countryMapper.toCountryDto(externalResponse)).thenReturn(countryDto);

        //Hacemos el mapeo del objeto que nos devuelve la API externa al objeto CountryDto
        CountryDto mappedCountryDto = countryMapper.toCountryDto(externalResponse);

        //Verificamos que los valores sean iguales
        assertEquals(countryDto.getCountry(), mappedCountryDto.getCountry());
        assertEquals(countryDto.getPopulation(), mappedCountryDto.getPopulation());
        verify(countryMapper,times(1)).toCountryDto(externalResponse);

    }


    @Test
    void testMapeoToCounty() {

        when(countryMapper.toCountry(countryDto)).thenReturn(country);

        //Hacemos el mapeo del objeto CountryDto a Country
        Country mappedCountry = countryMapper.toCountry(countryDto);

        //Verificamos que los valores sean iguales
        assertEquals(country.getCountry(), mappedCountry.getCountry());
        assertEquals(country.getPopulation(), mappedCountry.getPopulation());
        verify(countryMapper,times(1)).toCountry(countryDto);

    }

}
