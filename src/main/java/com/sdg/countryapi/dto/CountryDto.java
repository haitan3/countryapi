package com.sdg.countryapi.dto;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class CountryDto {
    @NotNull(message = "Country no puede ser null")
    private String country;
    @NotNull(message = "Population no puede ser null")
    @Positive(message = "Population debe ser un numero")
    private Integer population;

    public CountryDto(String country, int population) {
        this.country = country;
        this.population = population;
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
