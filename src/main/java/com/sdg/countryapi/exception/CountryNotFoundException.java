package com.sdg.countryapi.exception;

public class CountryNotFoundException extends RuntimeException{

    public CountryNotFoundException(String message) {
        //para enviar el mensaje que queremos
        super(message);
    }
}
