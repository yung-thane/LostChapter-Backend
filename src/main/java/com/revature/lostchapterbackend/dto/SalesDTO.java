package com.revature.lostchapterbackend.dto;

import java.util.Objects;

public class SalesDTO {

	public SalesDTO() {
		super();

	}

	public SalesDTO(boolean saleIsActive, double saleDiscountRate) {
		super();
		this.saleIsActive = saleIsActive;
		this.saleDiscountRate = saleDiscountRate;
	}

	private boolean saleIsActive;
	private double saleDiscountRate;

	public boolean isSaleIsActive() {
		return saleIsActive;
	}

	public void setSaleIsActive(boolean saleIsActive) {
		this.saleIsActive = saleIsActive;
	}

	public double getSaleDiscountRate() {
		return saleDiscountRate;
	}

	public void setSaleDiscountRate(double saleDiscountRate) {
		this.saleDiscountRate = saleDiscountRate;
	}

	@Override
	public int hashCode() {
		return Objects.hash(saleDiscountRate, saleIsActive);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SalesDTO other = (SalesDTO) obj;
		return Double.doubleToLongBits(saleDiscountRate) == Double.doubleToLongBits(other.saleDiscountRate)
				&& saleIsActive == other.saleIsActive;
	}

	@Override
	public String toString() {
		return "SalesDTO [saleIsActive=" + saleIsActive + ", saleDiscountRate=" + saleDiscountRate + "]";
	}

}
