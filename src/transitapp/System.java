package transitapp;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *  A class representing the backend of the transit system
 *  contains all of the global fare variables, Cardholders, etc,
 *  keeps track of all inputs to the system
 */
public class System {
	private Admin MANAGER = new Admin("manager", "manager@gmail.com", "p");
	
	private ArrayList<ArrayList<Double>> revenueStopsStorage;
	private double dailyRevenue;
	private double dailyStops;
	
	public static final double START_BALANCE = 19;
	static final double BUS_FARE = 2.0;
	static final double SUBWAY_FARE = 0.5;
	static final double CAPPED = 6;
	static final double TIME_PERIOD = 2;
	
	private int days;
	private ArrayList<CardHolder> cardHolders;
	private ArrayList<Admin> admins;
	
	public final String CONFIRMATION_MESSAGE = "Valid info: Processed Input!";
	
	//Defining individual bus routes
	static ArrayList<String> BUS_ROUTE_ONE = new ArrayList<>(Arrays.asList("DOG", 
            "CAT", 
            "BEAR", "DEER", "BROWN", "RAT")); 
	static ArrayList<String> BUS_ROUTE_TWO = new ArrayList<>(Arrays.asList("MOUSE", 
            "RED", 
            "DOG", "PURPLE", "BEAR", "FROG")); 
	
	//holder for all defined bus routes
	static ArrayList<ArrayList<String>> BUS_ALL_ROUTES = new ArrayList<>(Arrays.asList(BUS_ROUTE_ONE, BUS_ROUTE_TWO));
	
	//defining individual subway stations
	static ArrayList<String> SUBWAY_STATIONS = new ArrayList<>(Arrays.asList("WHITE", 
            "RED", 
            "BLUE", "GREEN", "YELLOW", "ORANGE", "PURPLE", "PINK", "BROWN", "BLACK")); 
	
	
	//holder for all subway stations
	static ArrayList<ArrayList<String>> ALL_STATIONS = new ArrayList<>(Arrays.asList(SUBWAY_STATIONS));
	
	
	//holder for all bus route stops and subway stations
	static ArrayList<ArrayList<String>> STOPS_AND_STATIONS = new ArrayList<>(Arrays.asList(BUS_ROUTE_ONE, BUS_ROUTE_TWO, SUBWAY_STATIONS));
	

	/**
	 *  creates a new transit system with an empty list of Cardholders, admins, events
	 *  
	 */
	public System() {
		this.dailyStops = 0;
		
		this.days = 1;
		this.cardHolders = new ArrayList<>();
		this.admins = new ArrayList<>();
		this.dailyRevenue = 0.00;
		this.revenueStopsStorage = new ArrayList<>();
		admins.add(MANAGER);
		
	}
	/**
	 *  getter method, returning this systems daily revenue
	 *  
	 *  @return this systems daily revenue
	 */
	public Double getDailyRevenue() {
		return this.dailyRevenue;
	}
	/**
	 *  setter method, adding an amount this systems daily revenue
	 *  
	 *  @param add amount of revenue to add to this systems daily revenue
	 */
	public void addDailyRevenue(Double add) {
		this.dailyRevenue += add;
	}
	/**
	 *  getter method, returning this systems daily stops passed by all passengers
	 *  @return the number of daily stops passed by all passengers in this system
	 */
	
	public double getDailyStops() {
		return this.dailyStops;
	}
	/**
	 *  setter method, adding integer amount to daily stops
	 *  
	 *  @param numStops the number of stops to add to dailyStops
	 */
	
	public void addDailyStops(int numStops) {
		this.dailyStops+=numStops;
	}
	
	/**
	 *  getter method for getting the revenue and stops storage of this system
	 *  
	 *  @return an arrayList holding arrayLists of daily revenue and stops passed by passengers on this system
	 */
	
	public ArrayList<ArrayList<Double>> getRevenueStopsStorage() {
		return this.revenueStopsStorage;
	}
	
	/**
	 *  getter method, returning this day of this system
	 *  
	 *  @return the day of this system
	 */
	public int getDay() {
		return this.days;
	}
	
	/**
	 *  setter, method, adding one to the days of this system
	 *  
	 */
	public void addDay() {
		this.days+=1;
	}
	/**
	 *  getter, method, returning the list of this systems card holders
	 *  
	 *  @return a list of this systems cardHolders
	 */
	public ArrayList<CardHolder> getCardHolders() {
		return this.cardHolders;
	}
	/**
	 *  setter, method, adding one to the days of this system
	 *  
	 */
	public void addCardHolder(CardHolder holder) {
		this.cardHolders.add(holder);
	}
	
	/**
	 *  getter, method, returning the list of this systems card holders
	 *  
	 *  @return a list of this systems cardHolders
	 */
	public ArrayList<Admin> getAdmins() {
		return this.admins;
	}
	/**
	 *  setter, method, adding one to the days of this system
	 *  
	 */
	public void addAdmin(Admin admin) {
		this.admins.add(admin);
	}
	
	
	/**
	 *  stores the daily revenue and total stops reached by all cardholders in storage
	 *  
	 */
	public void updateDailyStorage() {
		ArrayList<Double> RevenueStops = new ArrayList<>(Arrays.asList(this.dailyRevenue, this.dailyStops));
		this.revenueStopsStorage.add(RevenueStops);
		this.dailyRevenue = 0.00;
		this.dailyStops = 0;
	}
	

	/**
	 *  adds a input from  Cardholder into the system to the hashmap of all events
	 *  
	 *  @param method the method of transportation of the CardHolder (bus/train)
	 *  @param way  did the Cardholder enter or exit
	 *  @param place  The station/stop that the Cardholder entered or exited at
	 *  @param userId  The ID of the CardHolder
	 *  @param time  The time the Cardholder entered or exited
	 *   
	 */
    public void processInput(String method, String way, String place, String userId, String time, String cardName) {
    	CardHolder currentHolder = (CardHolder)this.findCardHolder(userId);
    	

    	if (way.equalsIgnoreCase("enter")) {
    		if (method.equalsIgnoreCase("bus")) {
   				currentHolder.enterBus(method, way, place, userId, time, cardName, this);
       				
       		} else if (method.equalsIgnoreCase("train")) {
       			currentHolder.enterStation(method, way, place, userId, time, cardName, this);
       				
       			}
    		} 
    	
    		
    	if (way.equalsIgnoreCase("exit")) {
    		if (method.equalsIgnoreCase("bus")) {
    			currentHolder.leaveBus(method, way, place, userId, time, cardName, this);
   				
   			} else if (method.equalsIgnoreCase("train")) {
   				currentHolder.leaveStation(method, way, place, userId, time, cardName, this);
    				
    		}			
    	}
    }
    
    /**
     * Takes user inputs and checks if it is a valid entry and also processes the input if it is valid.
     * 
     * @param method the method of transportation of the CardHolder (bus/train)
     * @param way did the Cardholder enter or exit
     * @param place The station/stop that the Cardholder entered or exited at
     * @param userId The ID of the CardHolder
     * @param time The time the Cardholder entered or exited
     * @param card the name of the TravelCard being used
     * @return a string either an error message if the input is inavlid or a confirmation method
     * if input is valid
     */
    public String checkInput(String method, String way, String place, String userId, String time, String card) {
		
    	if (! method.equalsIgnoreCase("bus") && !method.equalsIgnoreCase("train")) {
    		return "Error: Method of travel must be train or bus";
    	}
    	
    	if (! way.equalsIgnoreCase("enter") && ! way.equalsIgnoreCase("exit")) {
    		return "Error: Way must be enter or exit";
    	}
    	
    	if(!this.validPlace(method, place)) {

    		return "Error: Must enter a valid stop/station";
    	}
    	
    	if (this.findCardHolder(userId)==null) {

    		return "Error: Must enter valid cardHolder number";
    	}
    	
    	String[] times = time.split(":");
    	try {
	    	if (time.length()!=5 || !(0 <= Integer.parseInt(times[0]) && Integer.parseInt(times[0]) <= 24 && 0 <= Integer.parseInt(times[1]) && Integer.parseInt(times[0]) <= 59)) {
	    		return "Error: Must enter valid time in format HH:MM";
	    	}
    	}
	    catch (NumberFormatException e) {
	    	return "Error: Must enter valid time in format HH:MM";
	    }
    	
	    	

    	CardHolder currentHolder = (CardHolder) this.findCardHolder(userId);
    		
    	
    	if (currentHolder.findCard(card)==null) {
    		return "Error: Must enter valid travel card";
    	}
    	TravelCards currentCard = (TravelCards) currentHolder.findCard(card);
    	

    	if (!currentCard.validTransaction()){
    		if (currentCard.getBalance() <= 0) {
    			return "Error: Insufficient funds";
    		} 
    		return "Error: Travel Card is suspended";
    	}

    	processInput(method, way, place, userId, time, card);
    	return CONFIRMATION_MESSAGE;
    }

    
    /**
     * Checks if place is a valid location for either a bus or a train.
     * @param method the method of transportation of the CardHolder (bus/train)
     * @param place The station/stop that the Cardholder entered or exited at
     * @return a true if it is a valid place, false if not
     */
    private boolean validPlace(String method, String place) {
    	if(method.equalsIgnoreCase("bus")) {
    		for(ArrayList<String> route: System.BUS_ALL_ROUTES) {
    			if (route.contains(place.toUpperCase())) {
    				return true;
    		}
    	}
    		
    		
    	}else if (method.equalsIgnoreCase("train")) {
    		for(ArrayList<String> route: System.ALL_STATIONS) {
    			if (route.contains(place.toUpperCase())) {
    				return true;
    			}
    		
    		}
    	}
    	
    	
    	return false;
    	
    }
    
    /**
     * find the Cardholder associated with the corresponding id
     * @param id the id for the Cardholder being searched for
     * @return the Cardholder associted with the id, or null if cannot be found
     */
    public CardHolder findCardHolder(String id) {
		for(CardHolder holder:this.cardHolders) {
			if (holder.id.equals(id)) {
				return holder;
				}
			}
		
    		return null;
	}
    
    /**
     * find the Admin associated with the corresponding id
     * @param id the id for the Cardholder being searched for
     * @return the Admin associted with the id, or null if cannot be found
     */
    public Admin findAdmin(String id) {
		for(Admin admin:this.admins) {
			if (admin.id.equals(id)) {
				return admin;
				}
			}
    		
    		
    	
    		return null;
	}
    
    
    /**
     * checks if the user inputed password matches the password of the person with the corresponding id
     * @param person either Admin or CardHolder
     * @param id the id of the person the password is being checked for
     * @param pw the userinputed password.
     * @return true if the password matches false if not.
     */
    public boolean passwordCheck(String person, String id, String pw) {
		if (person.equalsIgnoreCase("A")) {
    		for(Admin admin:this.admins) {
    			if (admin.id.equals(id)) {
    				if (admin.pw.equals(pw)){
    					return true;
    				}
    			}
    		}
    		
    	}
    	else if (person.equalsIgnoreCase("C")){
    		for(CardHolder holder:this.cardHolders) {
    			if (holder.id.equals(id)) {
    				if (holder.pw.equals(pw)){
    					return true;
    				}
    			}
    		}
    		
    	}
		return false;
		
		
	}
    
}
