package transitapp;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class creates Travel Cards that a Card Holder can add their total collection of cards.
 */
public class TravelCards {
	
	double tripCost;
	public String name;
	public Double balance;
	public Boolean isSuspended;
	HashMap<String, ArrayList<String>> events;
	
	/**
	 *  Construct a new Travel Card with a starting balance of $19.
	 */
	public TravelCards(String name) {
		this.balance = System.START_BALANCE;
		this.isSuspended = false;
		this.name = name;
		this.events = new HashMap<String, ArrayList<String>>();
		this.events.put("method", new ArrayList<String>());
		this.events.put("way", new ArrayList<String>());
		this.events.put("place", new ArrayList<String>());
		this.events.put("userId", new ArrayList<String>());
		this.events.put("time", new ArrayList<String>());
		this.tripCost = 0;
	}
	
	
	/**
	 *  Checks if a valid transaction can be made with this card
	 */
	public boolean validTransaction(){
		if (this.balance>0 && !this.isSuspended){
			return true;
		}
		return false;
		
		
	}
	
	/**
	 * Get the current amount on this Travel Card.
	 * 
	 * @return The current balance on this card
	 */
	public double getBalance() {
		return this.balance;
	}
	
	/**
	 * Add an amount to this travel Card.
	 *  
	 * @param amount  The amount added to this card of $10, $20, or $50.
	 */
	public void setBalance(int amount) {
		this.balance += amount;
	}
	
	/**
    * Remove the cost of the fare from the cards balance, if the resulting balance
    * is negative, set the balance to 0.
    * 
    * @param fare  The amount to be removed
    */
   public void removeBusBalance(double fare) {
	   this.balance-=fare;
		  
	    if (this.balance < 0) {
	    	this.balance = 0.0;
	     }
	      
   }
   
   /**
   * Remove the cost of the fare from the cards balance, if the resulting balance
   * is negative, set the balance to 0.
   * 
   * @param fare  The amount to be removed
   */
  public void removeSubwayBalance(double fare) {
	  this.balance-=fare;
	  
      if (this.balance < 0) {
          this.balance = 0.0;
      }
      
      
  }
	
	/**
	 * Suspend this card.
	 */
	public void suspendCard() {
		this.isSuspended = true;
	}
	
	/**
	 * Unsuspend this card. 
	 */
	public void unsuspendCard() {
		this.isSuspended = false;
	}
}
