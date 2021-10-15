package transitapp;
import java.util.ArrayList;


/**
 * a class representing an admin of the traffic system, having access to 
 * more details about the data in the transit system
 * 
 */
public class Admin extends Person {


	private static int idIncrementer = 0;
	
	
	/**
	 * constructs a new admin with a name, email, password and id
	 * @param name the admins name
	 * @param email the admins email
	 * @param password the admins password
	 * 
	 */
	public Admin(String name, String email, String password) {
		super(name,email, password);
		this.id = Integer.toString(idIncrementer);
		idIncrementer++;
		

	}
	
	/**
	 * Give a daily report of revenue and stops.
	 * @param dailyRev the systems daily revenue
	 * @param dailyStops the systems daily stops passed
	 * @return String of the daily revenue and stops.
	 */
	public String dailyReport(double dailyRev, double dailyStops) {
		return "today's revenue earned was a total of $" + Double.toString(dailyRev) + " with a total of " + Integer.toString((int)dailyStops) + " stops/stations passed" + "\n";
		
	}
	
	
	/**
	 * Give a total report of revenue and stops.
	 * @param StopsStorage arraylist that stores arraylists of daily revenue and stops passed
	 * @return String of the total revenue and stops
	 */
	public String totalReport(ArrayList<ArrayList<Double>> StopsStorage) {
		Double[] revAndStops = this.totalRevenueandDays(StopsStorage);
		String rev = Double.toString(revAndStops[0]);
		int stopsInt = revAndStops[1].intValue();
		String stops = Integer.toString(stopsInt);
		return "total revenue earned is a total of $" + rev + " with a total of " + stops + " stops/stations passed \n";
	}
	
	/**
	 * find total revenue and stops
	 * @param StopsStorage ArrayList of revenues and stops
	 * @return Double Arraylist of total revenue and stops.
	 */
	
	private Double [] totalRevenueandDays(ArrayList<ArrayList<Double>> StopsStorage) {
		Double revAndStops[];
		revAndStops = new Double[2];
		revAndStops[0] = 0.0;
		revAndStops[1] = 0.0;
		
		for (ArrayList<Double> subList : StopsStorage) {
			revAndStops[0] += subList.get(0);
			revAndStops[1] += subList.get(1);
		}
		return revAndStops;
	}
}
