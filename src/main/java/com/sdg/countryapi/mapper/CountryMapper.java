package com.sdg.countryapi.mapper;

import com.sdg.countryapi.client.data.ExternalResponse;
import com.sdg.countryapi.dto.CountryDto;
import com.sdg.countryapi.entity.Country;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CountryMapper {
    @Mapping(source = "name.common", target = "country")
    @Mapping(source = "population", target = "population")
    CountryDto toCountryDto(ExternalResponse externalResponse);

    @Mapping(target = "id", ignore = true)
    Country toCountry(CountryDto countryDto);

}
