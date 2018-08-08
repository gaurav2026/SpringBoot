package com.medicine.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "medicine")
public class MedicineCatalogue {

	@Id
	private String name;
	private int id;
	private double price;
	private String manufacturer = null;
	private String category = null;
	@Column(name = "generic_name")
	private String genericName = null;
	private int quantity;
	@Column(name = "expiry_date")
	private String expiryDate = null;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getGenericName() {
		return genericName;
	}

	public void setGenericName(String genericName) {
		this.genericName = genericName;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}

	public MedicineCatalogue(String name, int id, double price, String manufacturer, String category, String genericName,
			int quantity, String expiryDate) {
		this.name = name;
		this.id = id;
		this.price = price;
		this.manufacturer = manufacturer;
		this.category = category;
		this.genericName = genericName;
		this.quantity = quantity;
		this.expiryDate = expiryDate;
	}

	public MedicineCatalogue() {
		super();
	}

}
