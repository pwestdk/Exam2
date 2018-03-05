package Impl;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import ExamProject.Car;
import ExamProject.Handler;

public class HandlerImpl implements Handler {

	@Override
	public String readFile(String fileName) throws IOException {
	    BufferedReader br = new BufferedReader(new FileReader(fileName));
	    try {
	        StringBuilder sb = new StringBuilder();
	        String line = br.readLine();

	        while (line != null) {
	            sb.append(line);
	            sb.append("\n");
	            line = br.readLine();
	        }
	        return sb.toString();
	    } finally {
	        br.close();
	    }
	}

	//What exception too throw and where??
	@Override
	public ArrayList<Car> getCarData(String data) throws IOException {
		ArrayList<Car> cars = new ArrayList<>();
		
		String[] carData = data.split("\n");
		for(String s : carData) {
			String[] temp = s.split(",");
			cars.add(new CarImpl(temp[0], Integer.parseInt(temp[1])));
		}
		
		return cars;
	}

	@Override
	public Car getLongestParked(ArrayList<Car> cars) throws IOException {
		Car car = new CarImpl();
		for(Car c : cars) {
			if (car.getParkingHours() < c.getParkingHours())
				car = c;
		}
		return car;
	}

	@Override
	public void sortCarsByNumberPlate(ArrayList<Car> cars) {
		cars.sort(new Comparator<Car>() {

			@Override
			public int compare(Car c1, Car c2) {
				return c1.getNumberPlate().compareTo(c2.getNumberPlate());
			}
			
		});;
	}

	@Override
	public void sortByTime(ArrayList<Car> cars) {
		
		cars.sort(new Comparator<Car>() {

			@Override
			public int compare(Car c1, Car c2) {
				Integer c1Hours = c1.getParkingHours();
				Integer c2Hours = c2.getParkingHours();
				return c1Hours.compareTo(c2Hours) ;
			}
			
		});;
	}

	@Override
	public int calculateTicketPrice(Car car) {
		int hourPrice = 10;
		return car.getParkingHours() * hourPrice;
	}

	@Override
	public String calculateParkingtimeInDays(Car car) {
		int days = car.getParkingHours() / 24;
		int hours = car.getParkingHours() - days * 24;
		return "Days: " + days + " Hours: " + hours;
	}

	@Override
	public ArrayList<Car> getIllegallyParkedCars(int limit, ArrayList<Car> cars) {
		ArrayList<Car> results = new ArrayList<Car>();
		Car car = new CarImpl();
		for(Car c : cars) {
			if (car.getParkingHours() >= limit)
				results.add(c);
		}
		return results;
	}

	@Override
	public ArrayList<Car> getReportedCars(String filename, ArrayList<Car> cars) throws IOException {
		ArrayList<Car> stolenCarsInParkingLot = new ArrayList<Car>();
		String[] stolen = readFile("Stolen.csv").split("\n");
		for(String s : stolen) {
			for (Car c: cars) {
				if (c.getNumberPlate().equals(s)) {
					stolenCarsInParkingLot.add(c);
				}
			}
		}
		return stolenCarsInParkingLot;
	}
	
	@Override
	public int calculateTotalTicketPrice(int ticketPrice, ArrayList<Car> cars) {
		int totalPrice = 0;
		for(Car c : cars) {
			totalPrice += c.getParkingHours() * ticketPrice;
		}
		return totalPrice;
	}

}
