package com.medicine.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.medicine.catalogue.dao.MedicineCatalogueRepository;
import com.medicine.catalogue.dao.MedicineOrderRepository;
import com.medicine.model.MedicineCatalogue;
import com.medicine.model.MedicineOrderModel;
import com.medicine.response.pojo.ResponsePojo;

@Service
public class MedicineOrderService {

	private AtomicInteger invoiceCount = new AtomicInteger(0);

	@Autowired
	private MedicineCatalogueRepository medicineCatalogueRepository;

	@Autowired
	private MedicineOrderRepository medicineOrderRepository;

	public MedicineOrderModel fetchBillDetails(String invoice) {
		return medicineOrderRepository.fetchBillDetailsBasedOnInvoice(invoice);
	}

	public List<String> fetchTopProducts(int count) {
		List<String> topProductList = new ArrayList<String>();
		//PageRequest pageRequest = new PageRequest(count, count,Sort.Direction.DESC,"quantity_sold");
		PageRequest pageRequest = new PageRequest(0, count);
		topProductList = medicineOrderRepository.fetchTopMedicines(pageRequest);
		return topProductList;
	}

	public ResponseEntity buyMedicine(MedicineCatalogue medicineRequired) throws Exception {
		ResponsePojo responsePojo = new ResponsePojo();
		try {
			MedicineCatalogue medicineAvailable = medicineCatalogueRepository.findById(medicineRequired.getName())
					.get();
			if (null == medicineAvailable || medicineAvailable.getQuantity() == 0) {
				responsePojo.setCode(500);
				responsePojo.setName(medicineRequired.getName());
				responsePojo.setFailure("Medicine not available");
				return new ResponseEntity(responsePojo, HttpStatus.INTERNAL_SERVER_ERROR);
			} else if (medicineAvailable.getQuantity() < medicineRequired.getQuantity()) {
				responsePojo.setCode(500);
				responsePojo.setName(medicineRequired.getName());
				responsePojo.setFailure("Only " + medicineAvailable.getQuantity() + " are available");
				return new ResponseEntity(responsePojo, HttpStatus.INTERNAL_SERVER_ERROR);
			} else {
				MedicineOrderModel medicineSellModel = new MedicineOrderModel();
				medicineSellModel.setSoldDate(new Date());
				medicineSellModel.setTotalSoldPrice(medicineRequired.getQuantity() * medicineAvailable.getPrice());
				medicineSellModel.setName(medicineRequired.getName());
				medicineSellModel.setQuantitySold(medicineRequired.getQuantity());
				medicineSellModel.setInvoiceNumber(String.valueOf(invoiceCount.incrementAndGet()));
				medicineSellModel.setPricePerUnit(medicineAvailable.getPrice());

				medicineOrderRepository.save(medicineSellModel);

				// update medicine catalogue table
				medicineCatalogueRepository.updateMedicineQuantityAfterPurchase(medicineSellModel.getName(),
						medicineAvailable.getQuantity() - medicineSellModel.getQuantitySold());

				responsePojo.setCode(200);
				responsePojo.setName(medicineSellModel.getName());
				responsePojo.setInvoice(String.valueOf(medicineSellModel.getInvoiceNumber()));
				return new ResponseEntity(responsePojo, HttpStatus.OK);
			}
		} catch (Exception e) {
			e.printStackTrace();
			responsePojo.setCode(500);
			responsePojo.setFailure("Issue while purchasing " + medicineRequired.getName());
			return new ResponseEntity(responsePojo, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	public List<MedicineOrderModel> fetchOrderDetails() {
		return Lists.newArrayList(medicineOrderRepository.findAll());
	}
}
