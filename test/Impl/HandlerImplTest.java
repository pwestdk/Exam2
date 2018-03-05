package Impl;
import static org.junit.jupiter.api.Assertions.*;
import java.io.IOException;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import ExamProject.Car;

class HandlerImplTest {
	
	// Fields
	HandlerImpl h = new HandlerImpl();
	private String carData;
	private ArrayList<Car> cars;

	@Test
	void testReadFile() throws IOException {
		String data;
		data = h.readFile("Cars.csv");
        
        int expected = 143;
        int actual = data.length();
        assertEquals(expected, actual);
        
        String _expected = "AF22454,1";
        String _actual = data.split("\n")[0];
        assertEquals(_expected, _actual);
        
        _expected = "CF22751,2";
        _actual = data.split("\n")[13];
        assertEquals(_expected, _actual);
	}


	
	void testGetCarData() {
		try {
			ArrayList<Car> cars = h.getCarData(h.readFile("Cars.csv"));
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
        int actual1 = h.calculateTicketPrice(car1);
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


	//Max time is 12 hours
	void testGetIllegallyParkedCars() {
	ArrayList<Car> illegal = h.getIllegallyParkedCars(12, cars);
	assertAll("Illegal",
			() -> assertTrue(illegal.contains(cars.get(2))),
			() -> assertTrue(illegal.contains(cars.get(4))),
			() -> assertTrue(illegal.contains(cars.get(9))));
	}

	@Test
	void testGetReportedCars() throws IOException {
			ArrayList<Car> stolen = h.getReportedCars("Stolen.csv", cars);
			assertAll("Cars",
					() -> assertEquals("AF22459", stolen.get(0).getNumberPlate()),
					() -> assertEquals("BF22414", stolen.get(1).getNumberPlate()));
	}

	//Philip
	@Test
	void testCalculateTotalTicketPrice() {
		Car car1 = new CarImpl("AF22455", 0);
		
		int expected1 = 0;
        int actual1 = h.calculateTicketPrice(car1);
        assertEquals(expected1, actual1);
        
        Car car2 = new CarImpl("AF22455", 10);
		
		int expected2 = 100;
        int actual2 = h.calculateTicketPrice(car2);
        assertEquals(expected2, actual2);
        
        Car car3 = new CarImpl("AF22455", 50);
		
		int expected3 = 500;
        int actual3 = h.calculateTicketPrice(car3);
        assertEquals(expected3, actual3);
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
