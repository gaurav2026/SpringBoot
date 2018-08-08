package com.medicine.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.medicine.model.MedicineCatalogue;
import com.medicine.model.MedicineOrderModel;
import com.medicine.service.MedicineOrderService;

@RestController
public class MedicineOrderController {
	
	@Autowired
	private MedicineOrderService medicineOrderService;

	@RequestMapping(method = RequestMethod.POST, value = "/buy")
	public ResponseEntity buyMedicine(@RequestBody MedicineCatalogue medicineRequired) throws Exception {
		return medicineOrderService.buyMedicine(medicineRequired);

	}

	@RequestMapping(method = RequestMethod.GET, value = "/top")
	public List<String> getTopProducts(@RequestParam("count") int count) {
		return medicineOrderService.fetchTopProducts(count);
	}

	@RequestMapping(method = RequestMethod.GET, value = "find/{invoice}")
	public MedicineOrderModel getBillingHistory(@PathVariable("invoice") String invoice) {
		return medicineOrderService.fetchBillDetails(invoice);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/find")
	public List<MedicineOrderModel> getBillingHistory() {
		return medicineOrderService.fetchOrderDetails();
	}

}
