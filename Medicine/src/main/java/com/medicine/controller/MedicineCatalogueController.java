package com.medicine.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.medicine.model.MedicineCatalogue;
import com.medicine.service.MedicineCatalogueService;

@RestController
public class MedicineCatalogueController {

	@Autowired
	private MedicineCatalogueService medicineService;

	@RequestMapping(method = RequestMethod.POST, value = "/add")
	public void addMedicine(@RequestBody List<MedicineCatalogue> medicineCatalogue) {
		medicineService.addProducts(medicineCatalogue);
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/update")
	public void updateMedicine(@RequestBody MedicineCatalogue medicineCatalogue) {
		medicineService.updateMedicine(medicineCatalogue);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/list")
	public List<MedicineCatalogue> getMedicine() {
		return medicineService.getMedicine();
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/name")
	public List<MedicineCatalogue> getMedicineByName(@RequestParam("param")  String param) {
		return medicineService.getMedicineByName(param);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/category")
	public List<MedicineCatalogue> getMedicineByCategory(@RequestParam("param")  String param) {
		System.out.println(param);
		return medicineService.getMedicineByCategory(param);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/genericName")
	public List<MedicineCatalogue> getMedicineByGenericName(@RequestParam("param")  String param) {
		return medicineService.getMedicineByGenericName(param);
	}

}
