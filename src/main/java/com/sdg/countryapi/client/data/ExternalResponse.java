package com.sdg.countryapi.client.data;

public class ExternalResponse {
    private Name name;
    private int population;

    public ExternalResponse(Name name, int population) {
        this.name = name;
        this.population = population;
    }

    public static class Name {
        private String common;

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

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }
}
