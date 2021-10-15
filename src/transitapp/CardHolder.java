package transitapp;

import java.util.ArrayList;

/**
 * A class representing a CardHolder (a user of the transit system)
 * containing all of their information and TravelCards
 *  
 */
public class CardHolder extends Person {
	
	ArrayList<Object> initial_ent;
	double monthlyExpense;
	ArrayList<TravelCards> TravelCards;
	ArrayList<ArrayList<Object>> trips;
	ArrayList<Double> monthlyExpenseStorage;

	private static int idIncrementer = 0;
	
	/**
	 * creates a new CardHolder with a name, email and travel card
	 *  
	 * @param name the name of the CardHolder
	 * @param email the email of the CardHolder
	 * @param card a TravelCard for the CardHolder
	 * 
	 */
	public CardHolder(String name, String email, String password) {
		super(name, email, password);
		this.TravelCards = new ArrayList<>();
		this.TravelCards.add(new TravelCards("card-a"));
		this.trips = new ArrayList<>();
		this.monthlyExpense = 0;
		this.monthlyExpenseStorage = new ArrayList<>();
		this.id = Integer.toString(idIncrementer);
		this.initial_ent = new ArrayList<>();
		idIncrementer++;
		
	}

	/**
	 * Input the trips information into an ArrayList of events
	 *  
	 * @param method  The method in which the CardHolder moved stops/stations, being by bus or train
	 * @param way  Whether the CardHolder entered or exited from the stop/station
	 * @param place  The stop/station which the CardHolder got off at
	 * @param userId  The userId that belongs to this specific CardHolder
	 * @param time  The time the CardHolder got off the stop/station
	 * @param card  The TravelCard that the CardHolder used for this trip
	 */
	public void inputEventStorage(String method, String way, String place, String userId, String time, String cardName) {
		TravelCards card = this.findCard(cardName);
		card.events.get("method").add(method);
		card.events.get("way").add(way);
		card.events.get("place").add(place);
		card.events.get("userId").add(userId);
		card.events.get("time").add(time);	
		
	}
	
	/**
	 * searches for a travel card in the list of CardHolders travel cards
	 *  
	 * @param cardName  name of the TravelCard to search for
	 * @return A TravelCard if it exists, or null
	 */
	public TravelCards findCard(String cardName) {
		for(TravelCards card: this.TravelCards) {
			if (card.name.equals(cardName)){
				return card;
			}
		}
		return null;	
	}	
	
	/**
	 * changes the name of the CardHolder
	 *  
	 * @param newName  the new name of the CardHolder
	 */
	public void changeName(String newName) {
		this.name = newName;	
	}	
	
	/**
	 * Remove the most recent elements from the users events if a stop/station overlaps with one another, which signifies
	 * the continuation of a trip but with a different method 
	 * 
	 * @param method  How the user traveled, by bus or train
	 * @param way  Whether the user entered or exited  
	 * @param place  The stop/station which the CardHolder got off at
	 * @param userId  The userId that belongs to this specific CardHolder
	 * @param time  The time the CardHolder got off the stop/station
	 * @param card  The TravelCard that the CardHolder used for this trip
	 */
	public void removeSomeEvents(String method, String way, String place, String userId, String time, String cardName) {
		TravelCards card = this.findCard(cardName);
		card.events.get("method").remove(card.events.get("method").get((card.events.get("method")).size() - 1));
		card.events.get("way").remove(card.events.get("way").get((card.events.get("way")).size() -1));	
		card.events.get("place").remove(card.events.get("place").get((card.events.get("place")).size() -1));
		card.events.get("userId").remove(card.events.get("userId").get((card.events.get("userId")).size() -1));
		card.events.get("time").remove(card.events.get("time").get((card.events.get("time")).size() -1));
	
	}
	
	/**
	 * Remove all elements from the users events if a trip has been completed or if an error was found
	 * 
	 * @param method  How the user traveled, by bus or train
	 * @param way  Whether the user entered or exited  
	 * @param place  The stop/station which the CardHolder got off at
	 * @param userId  The userId that belongs to this specific CardHolder
	 * @param time  The time the CardHolder got off the stop/station
	 * @param card  The TravelCard that the CardHolder used for this trip
	 */
	public void removeAllEvents(String method, String way, String place, String userId, String time, String cardName) {
		TravelCards card = this.findCard(cardName);
		card.events.get("method").clear();
		card.events.get("way").clear();
		card.events.get("place").clear();
		card.events.get("userId").clear();
		card.events.get("time").clear();
	
	}
	/**
	 * Charge the user for forgetting to tap or for a system error, and update the systems daily revenue
	 * 
	 * @param cardName  The name of the TravelCard to be charged
	 * @param system  The transportation system that keeps track of all the information 
	 */
	public void chargeBus(String cardName, System system) {
		this.findCard(cardName).removeBusBalance(System.BUS_FARE);
		this.monthlyExpense += System.BUS_FARE;
		system.addDailyRevenue(System.BUS_FARE);
	}
	
	/**
	 * Charge the user for forgetting to tap or for a system error, and update the systems daily revenue
	 * 
	 * @param cardName  The name of the TravelCard to be charged
	 * @param system  The transportation system that keeps track of all the information 
	 */
	public void chargeTrain(String cardName, System system) {
		this.findCard(cardName).removeSubwayBalance(System.SUBWAY_FARE);
		this.monthlyExpense += System.SUBWAY_FARE;
		system.addDailyRevenue(System.SUBWAY_FARE);
		
	}
	
	/**
	 * Charge the user for each of the stations passed without going over the trip cap
	 * 
	 * @param card  The card to be charged by the system
	 * @param place  The current station the user is at
	 * @return  The amount charged to the user for using the subway system
	 */
	public double stationsPassed(TravelCards card, String place) {
		String start_station = card.events.get("place").get((card.events.get("place")).size() - 1);
		String stop_station = place;
		int strt = System.SUBWAY_STATIONS.indexOf(start_station.toUpperCase());
		int stop = System.SUBWAY_STATIONS.indexOf(stop_station.toUpperCase());
		if (Math.abs(strt - stop) * System.SUBWAY_FARE >= System.CAPPED) {
			card.tripCost = System.CAPPED;
			return 0;
		}
		return Math.abs(strt - stop) * System.SUBWAY_FARE;
	}
	
	/**
	 * Calculate the time between the start of the trip and the current time
	 * 
	 * @param initial_time  Holds the start time of the trip and the day it began 
	 * @param current_time  Holds the current time of the trip and the day it began 
	 * @return  The difference between the two times
	 */
	public double timePeriod(ArrayList<Object> initial_time, ArrayList<Object> current_time) {
		String[] init_items =  ((String) initial_time.get(0)).split(":");		
		String[] curr_items = ((String) initial_time.get(0)).split(":");
		int start_time = Integer.parseInt(init_items[0] + init_items[1]);
		int end_time = Integer.parseInt(curr_items[0] + curr_items[1]);
		int days_passed = ((Integer) initial_time.get(1)) - ((Integer) current_time.get(1));
		return (2400 - start_time + end_time + (2400 * (days_passed - 1))) / 100;
	}
	
	/**
	 * Charge the CardHolder for entering a bus and add the stop as a potential start trip, and possibly add a finished trip
	 * to the users ArrayList of trips
	 * 
	 * @param method  How the user traveled, by bus or train
	 * @param way  Whether the user entered or exited  
	 * @param place  The stop/station which the CardHolder got off at
	 * @param userId  The userId that belongs to this specific CardHolder
	 * @param time  The time the CardHolder got off the stop/station
	 * @param card  The TravelCard that the CardHolder used for this trip
	 * @param system  The system used by the Admins to keep track of all taps, earning, CardHolders, etc.
	 */
	public void enterBus(String method, String way, String place, String userId, String time, String cardName, System system) {
		TravelCards card = this.findCard(cardName);
		if (card.events.get("way").size() == 0) {
			this.chargeBus(cardName, system);
			this.inputEventStorage(method, way, place, userId, time, cardName);
			this.initial_ent.clear();
			this.initial_ent.add(time);
			this.initial_ent.add(system.getDay());
			card.tripCost = System.BUS_FARE;
		}
		else {
			if (card.events.get("way").get((card.events.get("way")).size() - 1) == "enter") {
				if (card.events.get("method").get((card.events.get("method")).size() - 1) == "bus") {
					this.chargeBus(cardName, system);
				}
				else {
					this.chargeTrain(cardName, system);
				}
				this.removeAllEvents(method, way, place, userId, time, cardName);
				this.initial_ent.clear();
				this.initial_ent.add(time);
				this.initial_ent.add(system.getDay());
				this.chargeBus(cardName, system);
				card.tripCost = System.BUS_FARE;
				this.inputEventStorage(method, way, place, userId, time, cardName);
			}	
			else {
				if (card.events.get("place").get((card.events.get("place")).size() - 1) == place) {
					ArrayList<Object> current_time = new ArrayList<>();
					
					this.trips.remove(0);
					this.removeSomeEvents(method, way, place, userId, time, cardName);
					
					current_time.add(time);
					current_time.add(system.getDay());
					double time_diff = this.timePeriod(this.initial_ent, current_time);
					if (time_diff >= System.TIME_PERIOD || card.tripCost < System.CAPPED) {
						if ((card.tripCost += System.BUS_FARE) > System.CAPPED) {
							card.tripCost = System.CAPPED;
						}
						card.tripCost += System.BUS_FARE;
						this.chargeBus(cardName, system);	
					}
					
					this.inputEventStorage(method, way, place, userId, time, cardName);
				}
				else {
					
					this.removeAllEvents(method, way, place, userId, time, cardName);
					this.chargeBus(cardName, system);
					card.tripCost = System.BUS_FARE;
					this.inputEventStorage(method, way, place, userId, time, cardName);
					this.initial_ent.clear();
					this.initial_ent.add(time);
					this.initial_ent.add(system.getDay());
				}
			}
		}
	}
	
	/**
	 * Check for any errors in this users events and fix them, or add this stop to their events as a potential end
	 * to their trip 
	 * 
	 * @param method  How the user traveled, by bus or train
	 * @param way  Whether the user entered or exited  
	 * @param place  The stop/station which the CardHolder got off at
	 * @param userId  The userId that belongs to this specific CardHolder
	 * @param time  The time the CardHolder got off the stop/station
	 * @param card  The TravelCard that the CardHolder used for this trip
	 * @param system  The system used by the Admins to keep track of all taps, earning, CardHolders, etc.
	 */
	public void leaveBus(String method, String way, String place, String userId, String time, String cardName, System system) {
		TravelCards card = this.findCard(cardName);
		if (card.events.get("way").size() == 0 || card.events.get("way").get((card.events.get("way")).size() - 1) == "exit") {
			this.removeAllEvents(method, way, place, userId, time, cardName);
			this.chargeBus(cardName, system);
			card.tripCost = 0;
		}
		else if (card.events.get("method").get((card.events.get("method")).size() - 1) == "train" ) {
			this.chargeTrain(cardName, system);
			this.chargeBus(cardName, system);
			this.removeAllEvents(method, way, place, userId, time, cardName);
			card.tripCost = 0;
		}
		else {
			
			String start_place = card.events.get("place").get((card.events.get("place")).size() - 1);
			int numStops = this.numBusStopsPassed(start_place, place);
			system.addDailyStops(numStops);
			
			this.inputEventStorage(method, way, place, userId, time, cardName);
			String start_time = card.events.get("time").get(0);
			String end_time = card.events.get("time").get((card.events.get("time")).size() - 1);
			String start = "Trip started on " + card.events.get("method").get(0) + " at stop/station "  + card.events.get("place").get(0); 
			String stop = "Trip ended on " + card.events.get("method").get((card.events.get("method")).size()-1) + " at stop/station "
					+ card.events.get("place").get((card.events.get("place")).size()-1); 
			
			ArrayList<Object> curr_trip = new ArrayList<>();
			
			curr_trip.add(start);
			curr_trip.add(stop);
			curr_trip.add(start_time);
			curr_trip.add(end_time);
			curr_trip.add(card.tripCost);
			this.trips.add(0, curr_trip);
		
		}
	}
	/**
	 * Update the events for a potential new start of trip, check for any errors in this cards events, and possibly add 
	 * a finished trip to the users ArrayList of trips
	 * 
	 * @param method  How the user traveled, by bus or train
	 * @param way  Whether the user entered or exited  
	 * @param place  The stop/station which the CardHolder got off at
	 * @param userId  The userId that belongs to this specific CardHolder
	 * @param time  The time the CardHolder got off the stop/station
	 * @param card  The TravelCard that the CardHolder used for this trip
	 * @param system  The system used by the Admins to keep track of all taps, earning, CardHolders, etc.
	 */
	public void enterStation(String method, String way, String place, String userId, String time, String cardName, System system) {
		TravelCards card = this.findCard(cardName);
		if (card.events.get("way").size() == 0) {
			this.inputEventStorage(method, way, place, userId, time, cardName);
			this.initial_ent.clear();
			this.initial_ent.add(time);
			this.initial_ent.add(system.getDay());
		}
		else {
			if (card.events.get("way").get((card.events.get("way")).size() - 1) == "enter") {
				if (card.events.get("method").get((card.events.get("method")).size() - 1) == "bus") {
					this.chargeBus(cardName, system);
				}
				else {
					this.chargeTrain(cardName, system);
				}
				this.removeAllEvents(method, way, place, userId, time, cardName);
				this.inputEventStorage(method, way, place, userId, time, cardName);
				this.initial_ent.clear();
				this.initial_ent.add(time);
				this.initial_ent.add(system.getDay());
			}	
			else {
				if (card.events.get("place").get((card.events.get("place")).size() - 1) == place) {
					
					this.trips.remove(0);
					this.removeSomeEvents(method, way, place, userId, time, cardName);
					this.inputEventStorage(method, way, place, userId, time, cardName);
				}
				else {
					
					this.removeAllEvents(method, way, place, userId, time, cardName);
					card.tripCost = 0;
					this.initial_ent.clear();
					this.initial_ent.add(time);
					this.initial_ent.add(system.getDay());
					this.inputEventStorage(method, way, place, userId, time, cardName);
				}
			}
		}
	}
	
	/**
	 * Charge the CardHolder for each of the stations passed and update their trips, or charge them a 
	 * fee in case of an error with the system.
	 * 
	 * @param method  How the user traveled, by bus or train
	 * @param way  Whether the user entered or exited  
	 * @param place  The stop/station which the CardHolder got off at
	 * @param userId  The userId that belongs to this specific CardHolder
	 * @param time  The time the CardHolder got off the stop/station
	 * @param card  The TravelCard that the CardHolder used for this trip
	 * @param system  The system used by the Admins to keep track of all taps, earning, CardHolders, etc.
	 */
	public void leaveStation(String method, String way, String place, String userId, String time, String cardName, System system) {
		TravelCards card = this.findCard(cardName);
		if (card.events.get("way").size() == 0 || card.events.get("way").get((card.events.get("way")).size() - 1) == "exit") {
			this.removeAllEvents(method, way, place, userId, time, cardName);
			this.chargeTrain(cardName, system);
			card.tripCost = 0;
		}
		else if (card.events.get("method").get((card.events.get("method")).size() - 1) == "bus" ) {
			this.chargeTrain(cardName, system);
			this.chargeBus(cardName, system);
			this.removeAllEvents(method, way, place, userId, time, cardName);
			card.tripCost = 0;
		}
		else {
			ArrayList<Object> current_time = new ArrayList<>();
			current_time.add(time);
			current_time.add(system.getDay());
			double time_diff = this.timePeriod(this.initial_ent, current_time);
			String start_place = card.events.get("place").get((card.events.get("place")).size() - 1);
			int numStops = this.numTrainStationsPassed(start_place, place);
			
			if (card.tripCost < System.CAPPED || time_diff >= System.TIME_PERIOD) {
				double temp = card.tripCost;
				for(int i = 0;i<numStops;i++) {
					
					if(temp+System.SUBWAY_FARE <= System.CAPPED) {
						this.chargeTrain(cardName, system);	
						temp += System.SUBWAY_FARE;
					}
					else {
						card.removeSubwayBalance(System.CAPPED-temp);
						this.monthlyExpense += System.CAPPED-temp;
						system.addDailyRevenue(System.CAPPED-temp);
						break;
					}
				}
				card.tripCost += this.stationsPassed(card, place);
				
				
			}
			
			
			system.addDailyStops(numStops);
			
			this.inputEventStorage(method, way, place, userId, time, cardName);
			
			String start_time = card.events.get("time").get(0);
			String end_time = card.events.get("time").get((card.events.get("time")).size() - 1);
			String start = "Trip started on " + card.events.get("method").get(0) + " at stop/station "  + card.events.get("place").get(0); 
			String stop = "Trip ended on " + card.events.get("method").get((card.events.get("method")).size()-1) + " at stop/station "
					+ card.events.get("place").get((card.events.get("place")).size()-1); 
			
			ArrayList<Object> curr_trip = new ArrayList<>();
			
			curr_trip.add(start);
			curr_trip.add(stop);
			curr_trip.add(start_time);
			curr_trip.add(end_time);
			curr_trip.add(card.tripCost);
			this.trips.add(0, curr_trip);
			
		}
	}
	
	/**
	 * finds the number of stops passed by a user traveling on a bus given
	 * where they entered and exited
	 *  
	 * @param enter the stop that the user entered at
	 * @param exit the stop that the user exited at
	 * @return num of stops passed if they entered and exited on one route. return -1 if there exit 
	 * and enter are on different routes
	 */
	public int numBusStopsPassed(String enter, String exit) {
		int numStops = 0;
		for(ArrayList<String> routes: System.BUS_ALL_ROUTES) {
			if (routes.contains(enter.toUpperCase()) && (routes.contains(exit.toUpperCase())) ){
				int i_enter = routes.indexOf(enter.toUpperCase());
				int i_exit = routes.indexOf(exit.toUpperCase());
				numStops = Math.abs(i_enter-i_exit);
			}
		}
		if(numStops==0 && !enter.equals(exit)) {
			return 0;
		}
		return numStops;
	}
	
	/**
	 * Return the number of train stations passed by the user
	 * 
	 * @param enter  The station the user started at
	 * @param exit  The station the user exited at
	 * @return  The number of stations passed by the user
	 */
	public int numTrainStationsPassed(String enter, String exit) {
		
		int start = System.SUBWAY_STATIONS.indexOf(enter.toUpperCase());
		int stop = System.SUBWAY_STATIONS.indexOf(exit.toUpperCase());
		return Math.abs(stop - start);
	}
		
	/**
	 * suspends a TravelCard from being used
	 *  
	 * @param card  the TravelCard to be suspended.
	 */
	public void suspendCard(TravelCards card) {
		card.suspendCard();
	}
	
	
	/**
	 * Unsuspends a TravelCard from being used
	 *  
	 * @param card  the TravelCard to be unsuspended.
	 */
	public void unsuspendCard(TravelCards card) {
		card.unsuspendCard();
	}
	
	
	/**
	 * Views the CardHolders three most recent trips
	 *  
	 * @return An ArrayList of up to 3 of the CardHolders most recent 
	 */
	public String viewTripHistory() {
		
		String recent_trips = "";
		int i = 3;
		for(ArrayList<Object> trip: this.trips) {
			recent_trips += "TRIP "+i+"\n"+"start: "+(String)trip.get(0)+"\ndestination: "+trip.get(1)+"\nstart time: "+trip.get(2)+"\nstop time: "+trip.get(3)+"\ncost: $" +trip.get(4)+"\n";
			i -= 1;
			if (i == 0) {
				return recent_trips;
			}
		}	
		if(i==3) {
			return "You have no recent trips \n";
		}
		return recent_trips;
	}	
	
	
	/**
	 *  Returns the CardHolders average transit cost per month.
	 *  
	 *  @return The CardHolders average transit cost 
	 */
	public String viewAvgCost() {
		double avg;
		int length = this.monthlyExpenseStorage.size();
		double total = 0;
		for (int i=0; i < length; i++) {
			total += this.monthlyExpenseStorage.get(i);
		}
		if (length >0) {
			avg = total / length;
		}else {
			avg = this.monthlyExpense;
		}
		return "Average Transit Cost Per Month: $"+avg+"\n";	
	}
		
	
	/**
	 * Add a travel card to this CardHolders ArrayList of cards
	 *
	 * @param name  the name of the travel card to be added
	 */
	public void addTravelCard(String name) {
		this.TravelCards.add(new TravelCards(name));
	
	
	}
	/**
	 * Remove a travel card from this CardHolders ArrayList of cards
	 *
	 * @param card  The card that will be removed
	 * @return true if card is in holders list of cards and was removed
	 * false if this card does not exist
	 */
	public boolean removeTravelCard(String cardName) {
		for(TravelCards card:this.TravelCards) {
			if (card.name.equals(cardName)){
				this.TravelCards.remove(card);
				return true;
			}
		}
		
		return false;
		
	}
	
	
	/**
	 * Add an amount to this travel card
	 * 
	 * @param card The card that the money will be added to
	 * @param amount The amount added to the card
	 *
	 * @return  True if a viable amount is added to the card, false otherwise
	 */
	public boolean addAmount(TravelCards card, int amount) {
		if (amount == 10 || amount == 20 || amount == 50) {
			card.setBalance(amount);
			return true;
		}
		return false;
	}
	
}
