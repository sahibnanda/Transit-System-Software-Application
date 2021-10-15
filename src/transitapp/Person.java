package transitapp;

/**
 * This class represents a Person and all of their associated characteristics.
 */
public class Person {
	String name;
	String pw;
	String email;
	String id;
	
	/**
	 * Creates a Person with a name, email and password.
	 * @param name
	 * @param email
	 * @param password
	 */
	public Person(String name, String email, String password) {
		this.name = name;
		this.email = email;
		this.pw = password;
	}
	
	/**
	 * Store the attributes of a Person in a string format.
	 * @return the name, id and email in a string format.
	 */
	@Override
	public String toString() {
		return "name: "+this.name+", id: "+this.id +", email: "+this.email;
	}

}
