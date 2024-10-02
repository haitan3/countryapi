package com.sdg.countryapi.client;

import com.sdg.countryapi.client.data.ExternalResponse;
import com.sdg.countryapi.dto.CountryDto;
import com.sdg.countryapi.exception.CountryNotFoundException;
import com.sdg.countryapi.mapper.CountryMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
public class ExternalCountryClient {

    private final RestTemplate restTemplate;

    private final CountryMapper countryMapper;

    @Autowired
    public ExternalCountryClient(RestTemplate restTemplate, CountryMapper countryMapper) {
        this.restTemplate = restTemplate;
        this.countryMapper = countryMapper;
    }

    //Tomamos el valor de la URL desde el application.yml
    @Value("${external.api.url}")
    private String apiUrl;

    /**
     * Método para hacer la búsqueda por nombre a la API Externa con RestTemplate y devolver un objeto
     * mapeado a CountryDto
     * @param countryName
     * @return CountryDto
     */
    public CountryDto obtenerCountryApiExt(String countryName) {
        try {
            log.info("Consultando la API externa en la URL: {}", apiUrl + countryName);

            ResponseEntity< ExternalResponse[]> respuestaApi = restTemplate.getForEntity(apiUrl+countryName, ExternalResponse[].class);

        if (respuestaApi.getStatusCode()==HttpStatus.OK && respuestaApi.getBody() != null){
            //realizamos el mapping al objeto CountryDto
            return countryMapper.toCountryDto(respuestaApi.getBody()[0]);
        }
        }catch (HttpClientErrorException e){
            log.error("Error al consultar la API externa: Código de estado: {}, Mensaje: {}", e.getStatusCode(), e.getResponseBodyAsString());
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                log.error("El Country con nombre {} no fue encontrado en la API externa.", countryName);
                throw new CountryNotFoundException("El Country no ha sido encontrado en la API externa."); // Lanzar la excepción aquí
            }
        }
        throw new CountryNotFoundException("Error al consultar la API externa.");

    }

}
