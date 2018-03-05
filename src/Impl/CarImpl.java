package Impl;

import ExamProject.Car;

public class CarImpl implements Car{

	private String numberPlate;
	private int parkingHours;
	
	public CarImpl() {};
	
	public CarImpl(String numberPlate, int parkingHours) {
		this.numberPlate = numberPlate;
		this.parkingHours = parkingHours;
	}


	public String getNumberPlate() {
		return numberPlate;
	}

	public int getParkingHours() {
		return parkingHours;
	}
}
