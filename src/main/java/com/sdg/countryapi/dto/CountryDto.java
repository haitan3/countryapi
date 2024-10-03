package com.sdg.countryapi.dto;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class CountryDto {
    @NotNull(message = "Country no puede ser null")
    private String country;
    @NotNull(message = "Population no puede ser null")
    @Min(value = 0, message = "Population debe ser un número positivo")
    private Integer population;

    public CountryDto(String country, int population) {
        this.country = country;
        this.population = population;
    }

    public CountryDto() {

    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Integer getPopulation() {
        return population;
    }

    public void setPopulation(Integer population) {
        this.population = population;
    }
}
