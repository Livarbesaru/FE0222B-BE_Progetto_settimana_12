package com.crm.controller.territority;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.crm.service.territory.TerritoryService;

@RestController
@RequestMapping("/territory")
public class ProvinceController {

	@Autowired
	private TerritoryService territoryService;
	
	@PostMapping("/register")
	public ResponseEntity<?> registerTerrirory(){
		return territoryService.readMunicipality();
	}
}
