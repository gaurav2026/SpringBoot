package com.medicine.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.medicine.catalogue.dao.MedicineCatalogueRepository;
import com.medicine.model.MedicineCatalogue;

@Service
public class MedicineCatalogueService {

	
	@Autowired
	private MedicineCatalogueRepository medicineCatalogueRepository;
	

	public void addProducts(List<MedicineCatalogue> medicineCatalogues) {
		for (MedicineCatalogue medicine : medicineCatalogues) {
			medicineCatalogueRepository.save(medicine);
		}

	}

	/**
	 * update price based on medicine name
	 * @param medicineCatalogue
	 */
	public void updateMedicine(MedicineCatalogue medicineCatalogue) {
		medicineCatalogueRepository.updateMedicine(medicineCatalogue.getName(), medicineCatalogue.getPrice());
	}

	public List<MedicineCatalogue> getMedicine() {
		return Lists.newArrayList(medicineCatalogueRepository.findAll());
	}

	public List<MedicineCatalogue> getMedicineByName(String name) {
		return medicineCatalogueRepository.findMedicineByName(name);
	}

	public List<MedicineCatalogue> getMedicineByCategory(String category) {
		return medicineCatalogueRepository.findMedicineByName(category);
	}
	
	public List<MedicineCatalogue> getMedicineByGenericName(String genericName) {
		return medicineCatalogueRepository.findMedicineByGenericName(genericName);
	}
	
	
}
