package com.sdg.countryapi.dto;

public class CountryDto {
    private String country;
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
