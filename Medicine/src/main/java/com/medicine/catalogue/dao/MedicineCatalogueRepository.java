package com.medicine.catalogue.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.medicine.model.MedicineCatalogue;
import com.medicine.model.MedicineOrderModel;

@Transactional
public interface MedicineCatalogueRepository extends CrudRepository<MedicineCatalogue, String> {
	
	@Query("select m from MedicineCatalogue m where m.name = :name")
	public List<MedicineCatalogue> findMedicineByName(@Param("name") String name);
	
	@Query("select m from MedicineCatalogue m where m.category = :category")
	public List<MedicineCatalogue> findMedicineByCategory(@Param("category") String category);
	
	@Query("select m from MedicineCatalogue m where m.genericName = :genericName")
	public List<MedicineCatalogue> findMedicineByGenericName(@Param("genericName") String genericName);

	@Modifying
	@Query("update MedicineCatalogue m set m.price=:price where m.name = :name")
	@Transactional
	public void updateMedicine(@Param("name") String name, @Param("price") double price);
	
	@Modifying
	@Query("update MedicineCatalogue m set m.quantity=:quantity where m.name = :name")
	@Transactional
	public void updateMedicineQuantityAfterPurchase(@Param("name") String name, @Param("quantity") int quantity);

	
}
