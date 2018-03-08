package Impl;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import org.hamcrest.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
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
        
		// Hamcrest 1
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

	@Test
	void testGetLongestParked() throws IOException {
		String expected = "BF22414";
		Object actual = h.getLongestParked(cars).getNumberPlate();
		assertEquals(expected, actual);
	}

	@Test
	void testSortCarsByNumberPlate() {
//			ArrayList<Car> cars = h.getCarData(h.readFile("Cars.csv"));
			h.sortCarsByNumberPlate(cars);
			assertAll("Cars",
					() -> assertEquals("AF22451", cars.get(0).getNumberPlate()),
					() -> assertEquals("AF22457", cars.get((cars.size()-1)/2).getNumberPlate()),
					() -> assertEquals("CF22751", cars.get(cars.size()-1).getNumberPlate()));
	}

	@Test
	void testSortByTime() {
		h.sortByTime(cars);
		assertAll("Cars",
				() -> assertEquals("AF22454", cars.get(0).getNumberPlate()),
				() -> assertEquals("BF22424", cars.get((cars.size()-1)/2).getNumberPlate()),
				() -> assertEquals("BF22414", cars.get(cars.size()-1).getNumberPlate()));
	}

	@ParameterizedTest
	@ValueSource(ints = {0, 100, 1000})
	void testCalculateTicketPrice(int time) {
		Car car1 = new CarImpl("AF22455", time);
		
		int expected1 = time * 10;
        int actual1 = h.calculateTicketPrice(10,car1);
        assertEquals(expected1, actual1);
	}

	@ParameterizedTest
	@CsvSource({"'AF22455', 0, 'Days: 0 Hours: 0'", 
				"'AF22455', 36, 'Days: 1 Hours: 12'", 
				"'AF22455', 5000, 'Days: 208 Hours: 8'",
				"'AF22455', 45, 'Days: 1 Hours: 21'",
				"'AF22455', 48, 'Days: 2 Hours: 0'"})
	void testCalculateParkingtimeInDays(String numberPlate, int hours, String exspectedResult) {
		Car car = new CarImpl(numberPlate, hours);		
        String actual = h.calculateParkingtimeInDays(car);
		assertEquals(exspectedResult, actual);        
	}

	//Max time is 12 hours
	@Test
	void testGetIllegallyParkedCars() {
	ArrayList<Car> illegal = h.getIllegallyParkedCars(12, cars);
/*	assertAll("Illegal",
			() -> assertTrue(illegal.contains(cars.get(2))),
			() -> assertTrue(illegal.contains(cars.get(4))),
			() -> assertTrue(illegal.contains(cars.get(9))));*/
	
	// Hamcrest 2
	assertThat(illegal, Matchers.contains(cars.get(2),cars.get(4),cars.get(9)));
	}

	@Test
	void testGetReportedCars() throws IOException {
			ArrayList<Car> stolen = h.getReportedCars("Stolen.csv", cars);
			assertAll("Cars",
					() -> assertEquals("AF22459", stolen.get(0).getNumberPlate()),
					() -> assertEquals("BF22414", stolen.get(1).getNumberPlate()));
	}

	@ParameterizedTest
	@CsvSource({"0,0","10,1000","50,5000","1,100","30,3000","12,1200"})
	void testCalculateTotalTicketPrice(int price, int exspectedResult) {
		int totalPrice = h.calculateTotalTicketPrice(price, cars);
		assertEquals(exspectedResult, totalPrice);
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