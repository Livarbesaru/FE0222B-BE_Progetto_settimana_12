package com.crm.util.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.crm.model.company.Headquarter;
import com.crm.model.company.dto.HeadquarterDTO;
import com.crm.repository.company.CustomerRepository;
import com.crm.repository.company.HeadquarterRepository;
import com.crm.repository.territory.MunicipalityRepository;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.databind.util.Converter;

@Component
public class HeadQuartersDTOConverter implements Converter<HeadquarterDTO, Headquarter> {
	
	@Autowired
	private MunicipalityRepository municipalityRepository;
	
	@Override
	public Headquarter convert(HeadquarterDTO value) {
		Headquarter headquarters=new Headquarter();
			headquarters.setId(value.getId());
			headquarters.setLocality(value.getLocality());
			headquarters.setMunicipality(municipalityRepository.findById(value.getMunicipality()).get());
			headquarters.setPostalCode(value.getPostalCode());
			headquarters.setStreet(value.getStreet());
			headquarters.setStreetNumber(value.getStreetNumber());
		return headquarters;
	}

	@Override
	public JavaType getInputType(TypeFactory typeFactory) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JavaType getOutputType(TypeFactory typeFactory) {
		// TODO Auto-generated method stub
		return null;
	}

}
