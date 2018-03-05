package ExamProject;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public interface Handler {
	
	public String readFile(BufferedReader br, StringBuilder sb) throws IOException;

    public ArrayList<Car> getCarData(String data) throws IOException;

    public Car getLongestParked(ArrayList<Car> cars) throws IOException;

    public void sortCarsByNumberPlate(ArrayList<Car> cars);

    public void sortByTime(ArrayList<Car> cars);

    public int calculateTicketPrice(int hourPrice, Car car);

    public String calculateParkingtimeInDays(Car car);

    public ArrayList<Car> getIllegallyParkedCars(int limit, ArrayList<Car> cars);

    public ArrayList<Car> getReportedCars(String filename, ArrayList<Car> cars) throws IOException;

    public int calculateTotalTicketPrice(int ticketPrice, ArrayList<Car> cars);

}
