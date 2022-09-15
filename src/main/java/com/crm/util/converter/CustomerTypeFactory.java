package com.crm.util.converter;

import org.springframework.stereotype.Component;

import com.crm.model.company.CustomerType;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.databind.util.Converter;

@Component
public class CustomerTypeFactory implements Converter<String, CustomerType> {

	@Override
	public CustomerType convert(String value) {
		switch (value.toLowerCase()) {
		case "pa":
			return CustomerType.PA;
		case "sas":
			return CustomerType.SAS;
		case "spa":
			return CustomerType.SPA;
		case "srl":
			return CustomerType.SRL;
		default:
			return CustomerType.SRL;
		}
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
