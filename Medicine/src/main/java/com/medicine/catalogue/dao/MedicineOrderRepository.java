package com.medicine.catalogue.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.medicine.model.MedicineOrderModel;

@Transactional
public interface MedicineOrderRepository extends CrudRepository<MedicineOrderModel, String> {

	@Query("select i from MedicineOrderModel i where invoiceNumber=:invoiceNumber")
	public MedicineOrderModel fetchBillDetailsBasedOnInvoice(@Param("invoiceNumber") String invoiceNumber);
	
	@Query(nativeQuery=true,value="select m.name from invoice m group by m.name order by SUM(m.quantity_sold) DESC")
	public List<String> fetchTopMedicines(Pageable pageRequest);

}
