package Impl;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import org.hamcrest.*;

import javax.swing.SpringLayout.Constraints;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import ExamProject.Car;
import Impl.CarImpl;
import Impl.HandlerImpl;

class HandlerImplTest {
	
	// Fields
	HandlerImpl h = new HandlerImpl();
	private String carData;
	private ArrayList<Car> cars;

	@Test
	void testReadFile() throws IOException {
		
		String data;
		data = h.readFile(new BufferedReader(new FileReader("Cars.csv")), new StringBuilder());
        
        int expected = 143;
        int actual = data.length();
        assertThat(data.length(), Matchers.is(143));
        
        String _expected = "AF22454,1";
        String _actual = data.split("\n")[0];
        assertEquals(_expected, _actual);
        
        _expected = "CF22751,2";
        _actual = data.split("\n")[13];
        assertEquals(_expected, _actual);
	}


	@Test
	void testGetCarData() {
		try {
			ArrayList<Car> cars = h.getCarData(h.readFile(new BufferedReader(new FileReader("Cars.csv")), new StringBuilder()));
			assertAll("Cars",
					() -> assertEquals("AF22454", cars.get(0).getNumberPlate()),
					() -> assertEquals("AF22451", cars.get((cars.size()-1)/2).getNumberPlate()),
					() -> assertEquals("CF22751", cars.get(cars.size()-1).getNumberPlate()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	//Philip
	@Test
	void testGetLongestParked() throws IOException {
		String expected = "BF22414";
		Object actual = h.getLongestParked(cars).getNumberPlate();
		assertEquals(expected, actual);
	
	}

	//Kasper
	@Test
	void testSortCarsByNumberPlate() {
//			ArrayList<Car> cars = h.getCarData(h.readFile("Cars.csv"));
			h.sortCarsByNumberPlate(cars);
			assertAll("Cars",
					() -> assertEquals("AF22451", cars.get(0).getNumberPlate()),
					() -> assertEquals("AF22457", cars.get((cars.size()-1)/2).getNumberPlate()),
					() -> assertEquals("CF22751", cars.get(cars.size()-1).getNumberPlate()));
	}

	//Philip
	@Test
	void testSortByTime() {
		h.sortByTime(cars);
		assertAll("Cars",
				() -> assertEquals("AF22454", cars.get(0).getNumberPlate()),
				() -> assertEquals("BF22424", cars.get((cars.size()-1)/2).getNumberPlate()),
				() -> assertEquals("BF22414", cars.get(cars.size()-1).getNumberPlate()));
		
//		cars = new ArrayList<Car>() {{
//			add(new CarImpl("AF22454",1));
//			add(new CarImpl("AF22455",2));
//			add(new CarImpl("AF22456",3));
//		}};
//		
//		System.out.println(cars.get(0).getNumberPlate());
//		
//		String expected = "AF22454" + " " + "AF22456";
//		String actual = h.sortByTime(cars.);
	}

	@ParameterizedTest
	@ValueSource(ints = {0, 100, 1000})
	void testCalculateTicketPrice(int time) {
		Car car1 = new CarImpl("AF22455", time);
		
		int expected1 = time * 10;
        int actual1 = h.calculateTicketPrice(10,car1);
        assertEquals(expected1, actual1);
	}

	//Philip
	@Test
	void testCalculateParkingtimeInDays() {
		Car car1 = new CarImpl("AF22455", 36);
		
		String expected1 = "Days: 1 Hours: 12";
        String actual1 = h.calculateParkingtimeInDays(car1);
        assertEquals(expected1, actual1);
        
        Car car2 = new CarImpl("AF22455", 0);
		
		String expected2 = "Days: 0 Hours: 0";
        String actual2 = h.calculateParkingtimeInDays(car2);
        assertEquals(expected2, actual2);
        
        Car car3 = new CarImpl("AF22455", 5000);
		
		String expected3 = "Days: 208 Hours: 8";
        String actual3 = h.calculateParkingtimeInDays(car3);
        assertEquals(expected3, actual3);
	}


	@Test	
	//Max time is 12 hours
	void testGetIllegallyParkedCars() {
	ArrayList<Car> illegal = h.getIllegallyParkedCars(12, cars);
/*	assertAll("Illegal",
			() -> assertTrue(illegal.contains(cars.get(2))),
			() -> assertTrue(illegal.contains(cars.get(4))),
			() -> assertTrue(illegal.contains(cars.get(9))));*/
	
	assertThat(illegal, Matchers.contains(cars.get(2),cars.get(4),cars.get(9)));
	}

	@Test
	void testGetReportedCars() throws IOException {
			ArrayList<Car> stolen = h.getReportedCars("Stolen.csv", cars);
			assertAll("Cars",
					() -> assertEquals("AF22459", stolen.get(0).getNumberPlate()),
					() -> assertEquals("BF22414", stolen.get(1).getNumberPlate()));
	}

	//Philip
	@ParameterizedTest
	@ValueSource(ints = {0, 10, 50})
	void testCalculateTotalTicketPrice(int price) {
		int totalPrice = h.calculateTotalTicketPrice(price, cars);
		assertEquals(price * 100, totalPrice);
	}

	@BeforeEach
	void setUp() {
		carData = "";
		cars = new ArrayList<Car>() {{
			add(new CarImpl("AF22454",1));
			add(new CarImpl("AF22455",1));
			add(new CarImpl("AF22456",21));
			add(new CarImpl("AF22457",3));
			add(new CarImpl("AF22458",13));
			add(new CarImpl("AF22459",5));
			add(new CarImpl("AF22451",8));
			add(new CarImpl("AF22452",3));
			add(new CarImpl("AF22453",9));
			add(new CarImpl("BF22414",22));
			add(new CarImpl("BF22424",4));
			add(new CarImpl("BF22434",5));
			add(new CarImpl("BF22444",3));
			add(new CarImpl("CF22751",2));
		}};
	}
}
