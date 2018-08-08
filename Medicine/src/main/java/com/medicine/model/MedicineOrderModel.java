package com.medicine.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "invoice")
public class MedicineOrderModel {
	
	private String name = null;

	@Column(name = "sold_price")
	private double totalSoldPrice;

	@Column(name = "quantity_sold")
	private int quantitySold;

	@Id
	@Column(name = "invoice_number")
	private String invoiceNumber;

	@Column(name = "sold_date")
	private Date soldDate = null;

	@Column(name = "price_per_unit")
	private double pricePerUnit;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getTotalSoldPrice() {
		return totalSoldPrice;
	}

	public void setTotalSoldPrice(double d) {
		this.totalSoldPrice = d;
	}

	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public Date getSoldDate() {
		return soldDate;
	}

	public void setSoldDate(Date soldDate) {
		this.soldDate = soldDate;
	}

	public MedicineOrderModel() {
	}

	public int getQuantitySold() {
		return quantitySold;
	}

	public void setQuantitySold(int quantitySold) {
		this.quantitySold = quantitySold;
	}

	public double getPricePerUnit() {
		return pricePerUnit;
	}

	public void setPricePerUnit(double pricePerUnit) {
		this.pricePerUnit = pricePerUnit;
	}

	public MedicineOrderModel(String name, double totalSoldPrice, int quantitySold, String invoiceNumber, Date soldDate,
			double pricePerUnit) {
		this.name = name;
		this.totalSoldPrice = totalSoldPrice;
		this.quantitySold = quantitySold;
		this.invoiceNumber = invoiceNumber;
		this.soldDate = soldDate;
		this.pricePerUnit = pricePerUnit;
	}

}
