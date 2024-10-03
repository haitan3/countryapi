package com.sdg.countryapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sdg.countryapi.client.ExternalCountryClient;
import com.sdg.countryapi.dto.CountryDto;
import com.sdg.countryapi.repository.CountryRepository;
import com.sdg.countryapi.service.CountryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@SpringBootTest
@AutoConfigureMockMvc
public class CountryControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Mock
    private CountryRepository repository;

    @MockBean
    private CountryService service;

    @InjectMocks
    private CountryController controller;

    @Mock
    private ExternalCountryClient externalCountryClient;

    @Autowired
    ObjectMapper mapperJson ;


    private CountryDto countryDto;

    @BeforeEach
    void setup(){
        MockitoAnnotations.openMocks(this);

        countryDto = new CountryDto("Bulgaria",6927288);
    }

    @Test
    void testGuardarOActualizarCountrySuccess() throws Exception {
        when(service.guardarOActualizarCountry(any(CountryDto.class))).thenReturn(countryDto);

        when(externalCountryClient.obtenerCountryApiExt(countryDto.getCountry())).thenReturn(countryDto);



        String requestBodyJson = mapperJson.writeValueAsString(countryDto);

        mockMvc.perform(post("/api/v1/data/country")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBodyJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.country").value("Bulgaria"))
                .andExpect(jsonPath("$.population").value(6927288));

        verify(service,times(1)).guardarOActualizarCountry(any(CountryDto.class));

        verify(externalCountryClient,times(0)).obtenerCountryApiExt(countryDto.getCountry());

    }


    @Test
    void testListarCountriesSuccess() throws Exception {
        List<CountryDto> countryList = Collections.singletonList(countryDto);
        when(service.listarTodosCountries()).thenReturn(countryList);

        mockMvc.perform(get("/api/v1/data/country"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].country").value("Bulgaria"))
                .andExpect(jsonPath("$[0].population").value(6927288));

        verify(service, times(1)).listarTodosCountries();
    }

    @Test
    void testListarCountriesNotFound() throws Exception {
        when(service.listarTodosCountries()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/v1/data/country"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("¡Lista vacía! No se encontraron países en la base de datos."));

        verify(service, times(1)).listarTodosCountries();
    }
}
