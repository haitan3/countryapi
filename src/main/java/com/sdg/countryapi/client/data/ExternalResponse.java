package com.sdg.countryapi.client.data;

public class ExternalResponse {
    private Name name;
    private Integer population;

    public ExternalResponse() {
    }

    public ExternalResponse(Name name, Integer population) {
        this.name = name;
        this.population = population;
    }

    public static class Name {
        private String common;

        public Name() {
        }
        public Name(String common) {
            this.common = common;
        }

        public String getCommon() {
            return common;
        }

        public void setCommon(String common) {
            this.common = common;
        }
    }

    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public Integer getPopulation() {
        return population;
    }

    public void setPopulation(Integer population) {
        this.population = population;
    }
}
