package transitapp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;



/**
 * A class for the user interface of a transit system that is able
 * to accept input from the user and alternate between different views
 * 
 */
public class Events extends Application {
	
	Label method;
	TextField methodAns;
	Label way;
	TextField wayAns;
	Label where;
	TextField whereAns;
	Label userID;
	TextField userIDAns;
	Label time;
	TextField timeAns;
	Label card;
	TextField cardAns;
	Button enter;

	Button userInteraction;
	
	
	Label output = new Label("Hello, pls enter information");


	public static void main(String[] args) {
		
		launch(args);
	
	}
	
	/**
	 * calls the creation and display of the stage and all of its components
	 * entry point to the GUI application
	 * 
	 * @param stage a stage to be initializes
	 */
	@Override
	public void start(Stage stage) {
		
		initUI(stage);
	}

	/**
	 * creates the stage and all of its components
	 * 
	 * @param stage a stage where all the components will be added to
	 */
	private void initUI(Stage stage) {
		System system = new System();

		GridPane pane = new GridPane();
		
		method = new Label("Method ('bus' or 'train'):");
		methodAns = new TextField("");
		way = new Label("Way('enter' or 'exit'):");
		wayAns = new TextField("");
		where = new Label("Where(stop/station name):");
		whereAns = new TextField("");
		userID = new Label("UserID (Number):");
		userIDAns = new TextField("");
		time = new Label("Time (HH:MM, 24Hr format):");
		timeAns = new TextField("");
		card = new Label("Travel Card (Card name):");
		cardAns = new TextField("");
		enter = new Button("Enter");
		userInteraction = new Button("User Interaction");
		TextField [] fields = {methodAns, wayAns, whereAns, userIDAns, timeAns, cardAns};
		for (TextField field: fields) {
			field.setMaxWidth(200);
			field.setMinWidth(200);
		}
		
		
		Label output = new Label("Hello, Please Enter Information");
		output.setTextFill(Color.web("#0076a3"));
		output.setMinWidth(50);
		output.setMinHeight(50);
		pane.setHgap(20);
		pane.setVgap(10);
		
		pane.add(method, 1, 1);
		pane.add(methodAns, 1, 2);
		pane.add(way, 1, 3);
		pane.add(wayAns, 1, 4);
		pane.add(where, 1, 5);
		pane.add(whereAns, 1, 6);
		pane.add(userID, 1, 7);
		pane.add(userIDAns, 1, 8);
		pane.add(time, 1, 9);
		pane.add(timeAns, 1, 10);
		pane.add(card, 1, 11);
		pane.add(cardAns, 1, 12);
		pane.add(enter, 1, 13);
		pane.add(userInteraction, 1, 14);
		pane.add(output, 1, 0);
		
		enter.setOnAction(new Userinput(system , methodAns, wayAns, whereAns, userIDAns, timeAns, cardAns, output));

		userInteraction.setOnAction(new UserInteraction(system));
		
		Scene scene = new Scene(pane, 400, 550);
		
		stage.setTitle("Transit System");
		stage.setScene(scene);
		stage.show();
		
	}
	
}

/**
 * a class to handle the events pertaining to users inputting information onto the GUi
 * 
 */
class Userinput implements EventHandler<ActionEvent> {
	public System system;
	public TextField method;
	public TextField way;
	public TextField where;
	public TextField userID;
	public TextField time;
	public TextField card;
	public Label output;

	/**
	 * constructs all the data pertaining to an instance of user input
	 *  
	 * @param system the system the user has inputted data into
	 * @param method textField where user enters the method of travel of the user 
	 * @param way textField where user enters if the user entered or exited
	 * @param where textField where user enters the stop/station they have entered or exited
	 * @param userID textField where user enters where user enters
	 * @param time textField where user enters the time they entered or exited
	 * @param card textField where user enters the card they are using
	 * @param output label where confirmation or error messages are displayed
	 * 
	 */
	public Userinput(System system, TextField method, TextField way, TextField where, TextField userID, TextField time, TextField card, Label output) {
		this.system = system;
		this.method = method;
		this.way = way;
		this.where = where;
		this.userID = userID;
		this.time = time;
		this.card = card;
		this.output = output;
		
	}
	
	/**
	 * converts a list of texFields to a list of strings containing the inputted info in the textFields
	 *  
	 * @param array an array of TextFields
	 * @return an array of strings with the text in each textField
	 */
	private String[] TextFieldtoString(TextField[] array){
		String [] str = new String[array.length];
		for(int i=0;i<array.length;i++) {
			str[i] = array[i].getText().trim();
			
		}
		return str;
	}
	
	/**
	 * resets all textfields in this instance of user input to the enpty string 
	 *  
	 */
	//resets the textfields to blank
	private void resetTextFields(){
		this.method.setText("");
		this.way.setText("");
		this.where.setText("");
		this.userID.setText("");
		this.time.setText("");
		this.card.setText("");
	}
	
	/**
	 * gathers the information in each textField
	 * processes the information inputted by the user when the enter button is pressed
	 * accepts valid input and declines invalid input, 
	 * displays a relevant message at the top of the GUI
	 *  
	 * @param event information about the event
	 * 
	 */
	@Override
	public void handle(ActionEvent event) {
		
		String[] inputStr = TextFieldtoString(new TextField[] {this.method, this.way, this.where, this.userID, this.time, this.card});
		
		String outputMessage = this.system.checkInput(inputStr[0], inputStr[1], inputStr[2] ,inputStr[3], inputStr[4], inputStr[5]);
		
		if(outputMessage.equals(system.CONFIRMATION_MESSAGE)){
			output.setTextFill(Color.web("#12c93a"));
			this.output.setText(system.CONFIRMATION_MESSAGE);
			this.resetTextFields();
		}
		
		else {
			output.setTextFill(Color.web("#e61010"));
			this.output.setText(outputMessage);
		}
			
	}
	
}


/**
 * a class for allowing interaction between cardholder/admins and the transit system
 */
class UserInteraction implements EventHandler<ActionEvent> {
	String CARDHOLDER_ACTIONS = "CARDHOLDER ACTIONS:\n'CN': change name"
			+ "\n'AM': add money to card\n'AC': add card\n'RC': remove card\n'VCB': view card balance\n'SC': suspend a travel card\n'UC': unsuspend travel card\n'TH': view travel history\n'ATC': view average monthly travel cost\n'E': exit CardHolder interaction view";
			
	String ADMIN_ACTIONS = "ADMIN ACTIONS:\n'ID': Increment day\n'ACH': add cardholder\n"
			+ "'AA': add admin\n'DR': view daily report\n'TR': view total report\n"
			+ "'E': exit Admin interaction view";
			
	String INVALID_INPUT_MESSAGE = "Invalid Input! \n";
	String INDEX_ERROR_MESSAGE = "Invalid number of fields, try again \n";
	String ERROR_MESSAGE = "Something went Wrong, Try Again! \n";
	System system;
	
	/**
	 * creates a new user interaction with a given System
	 *  
	 * @param system  a System containing all data for the users
	 * 
	 */
	public UserInteraction(System system) {
		this.system = system;	
	}
	
	/**
	 * allows both cardholders and admins to login to their account, view
	 * statistics, information and make changes to their account via the console.
	 *  
	 * @param system the System containing all the data for the users
	 * 
	 */
	private void interactions(System system) {
		
		Scanner scan = new Scanner(java.lang.System.in);
		
		while (true) {
			
			try {
				boolean result = false;
				
				java.lang.System.out.println("Enter: <'A' for admin, 'C' for cardholder> <id> <password> |OR| <'E'> to exit");
				String person = scan.nextLine().trim();
				String[] details = person.split(" ");
				if (details[0].equalsIgnoreCase("E")) {
					java.lang.System.out.println("you have exited user interaction view");
					break;
				}
				if(this.system.passwordCheck(details[0], details[1], details[2])) {
					
					while(true) {
					if (details[0].equalsIgnoreCase("C")) {
						CardHolder holder = this.system.findCardHolder(details[1]);
						java.lang.System.out.println(CARDHOLDER_ACTIONS);
						String action = scan.nextLine().trim();
						
						
						if(action.equalsIgnoreCase("CN")) {
							java.lang.System.out.print("Enter: <new first name> <new last name>\n");
							String newUser = scan.nextLine();
							String[] userInfo = newUser.trim().split(" ");
							if (userInfo.length == 2) {
								holder.name=userInfo[0]+" "+userInfo[1];
								java.lang.System.out.print("Thank you, name changed to "+ holder.name+"\n");
							}else {
								java.lang.System.out.println(INVALID_INPUT_MESSAGE);
							}
							

						}
						
						else if(action.equalsIgnoreCase("AC")) {
							java.lang.System.out.print("Enter: <card name> \n");
							String newUser = scan.nextLine();
							String[] userInfo = newUser.trim().split(" ");
							if (userInfo.length == 1) {
								holder.addTravelCard(userInfo[0]);
								java.lang.System.out.print("Thank you, new card added with name: "+ userInfo[0]+"\n");
							}else {
								java.lang.System.out.println(INVALID_INPUT_MESSAGE);
							}

						}
						else if(action.equalsIgnoreCase("RC")) {
							java.lang.System.out.print("Enter: <card name> \n");
							String newUser = scan.nextLine();
							String[] userInfo = newUser.trim().split(" ");
							
							if (userInfo.length == 1) {
								boolean cardRemoved = holder.removeTravelCard(userInfo[0]);
								if(cardRemoved) {
									java.lang.System.out.print("Thank you, card removed with name: "+ userInfo[0]+"\n");
								}else {
									java.lang.System.out.print("Invalid Card Name"+"\n");
								}
								
							}else {
								java.lang.System.out.println(INVALID_INPUT_MESSAGE);
							}

						}
						
						else if(action.equalsIgnoreCase("VCB")) {
							java.lang.System.out.print("Enter: <card name> \n");
							String newUser = scan.nextLine();
							String[] userInfo = newUser.trim().split(" ");
							
							if (userInfo.length == 1) {
								TravelCards card = holder.findCard(userInfo[0]);
								if(card!=null) {
									java.lang.System.out.print("Thank you, balance on card: "+userInfo[0]+" is $"+card.balance+"\n");
								}else {
									java.lang.System.out.print("Invalid Card Name"+"\n");
								}
								
							}else {
								java.lang.System.out.println(INVALID_INPUT_MESSAGE);
							}

						}
						
						else if(action.equalsIgnoreCase("AM")) {
							java.lang.System.out.print("Enter: <card name> <amount (10, 20, or 50)> \n");
							String newUser = scan.nextLine();
							String[] userInfo = newUser.trim().split(" ");
							TravelCards currentCard = null;
							if (userInfo.length == 2) {
								if(holder.findCard(userInfo[0])!=null) {
									currentCard = holder.findCard(userInfo[0]);
									result = holder.addAmount(currentCard, Integer.parseInt(userInfo[1]));
									
									
								}if (result){
									java.lang.System.out.println("Thank you " +"$"+userInfo[1]+" added to "+currentCard.name + "\n");
									
									
								}else {
									java.lang.System.out.println(INVALID_INPUT_MESSAGE);
									
								}

							}else {
								java.lang.System.out.println(INVALID_INPUT_MESSAGE);
							}
						}
						else if(action.equalsIgnoreCase("SC")) {
							java.lang.System.out.print("Enter: <card name>\n");
							String newUser = scan.nextLine();
							String userInfo = newUser.trim();
							TravelCards card = holder.findCard(userInfo);
							if(card==null) {
								java.lang.System.out.println("invalid card");
							}else {
								card.suspendCard();
								java.lang.System.out.println("Your card named: "+card.name+", is now suspended \n");
								
							}
						
						}else if(action.equalsIgnoreCase("UC")) {
							java.lang.System.out.print("Enter: <card name>\n");
							String newUser = scan.nextLine();
							String userInfo = newUser.trim();
							TravelCards card = holder.findCard(userInfo);
							if(card==null) {
								java.lang.System.out.println("invalid card");
							}else {
								card.unsuspendCard();
								java.lang.System.out.println("Your card named: "+card.name+", is now unsuspended \n");
								
							}

						}else if(action.equalsIgnoreCase("TH")) {
							java.lang.System.out.println(holder.viewTripHistory());
							
						}else if(action.equalsIgnoreCase("ATC")) {
							java.lang.System.out.println(holder.viewAvgCost());
							
						}
						else if(action.equalsIgnoreCase("E")) {
							break;
							
						}else {
							java.lang.System.out.println(INVALID_INPUT_MESSAGE);
						}


					}else if (details[0].equalsIgnoreCase("A"))  {

						Admin admin = this.system.findAdmin(details[1]);
						java.lang.System.out.println(ADMIN_ACTIONS);
						String action = scan.nextLine().trim();
						
						if(action.equalsIgnoreCase("ACH")) {
							java.lang.System.out.print("Enter: <firstName> <lastName> <email> <password> \n");
							String newUser = scan.nextLine();
							String[] userInfo = newUser.trim().split(" ");
							if (userInfo.length==4) {
								CardHolder newHolder = new CardHolder(userInfo[0]+" "+userInfo[1], userInfo[2], userInfo[3]);
								system.addCardHolder(newHolder);
								java.lang.System.out.print("New CardHolder created with info... "+newHolder+"\n");
								
							}else {
								java.lang.System.out.println(INVALID_INPUT_MESSAGE);
							}
							
							
						}
						else if(action.equalsIgnoreCase("AA")) {
							java.lang.System.out.print("Enter: <new firstName> <new lastName> <new admin email> <new password> \n");
							String info = scan.nextLine();
							String[] adminInfo = info.trim().split(" ");
							if (adminInfo.length==4) {
								Admin newAdmin = new Admin(adminInfo[0]+" "+adminInfo[1], adminInfo[2], adminInfo[3]);
								system.addAdmin(newAdmin);
								java.lang.System.out.print("New Admin created with info... "+newAdmin+"\n");
								
								
							}else {
								java.lang.System.out.println(INVALID_INPUT_MESSAGE);
							}
							
						}
						else if(action.equalsIgnoreCase("DR")) {
							java.lang.System.out.println(admin.dailyReport(this.system.getDailyRevenue(), this.system.getDailyStops()));
								
							
						}
						
						else if(action.equalsIgnoreCase("ID")) {

							this.system.updateDailyStorage();

							this.system.addDay();
							if(this.system.getDay()%31==0) {
								for(CardHolder holder: this.system.getCardHolders()) {
									holder.monthlyExpenseStorage.add(holder.monthlyExpense);
									holder.monthlyExpense = 0.00;
								}

							}
							java.lang.System.out.println("Thank you, day "+this.system.getDay()+" has started \n");

						}
						else if(action.equalsIgnoreCase("TR")) {
							java.lang.System.out.println(admin.totalReport(this.system.getRevenueStopsStorage()));

							

						}
						
						else if(action.equalsIgnoreCase("E")) {
							break;
							
						}
						else {
							java.lang.System.out.println(INVALID_INPUT_MESSAGE);
						}

					}

					}
				}
				else {
					java.lang.System.out.println(INVALID_INPUT_MESSAGE);
	
				}

			}catch (ArrayIndexOutOfBoundsException e) {
				java.lang.System.out.println(INDEX_ERROR_MESSAGE);
			} catch (Exception e) {
				java.lang.System.out.println(ERROR_MESSAGE);
			}
		}
		
}
		
	/**
	 * handles the press of the user interaction button
	 *  
	 * @param event contains information about the event
	 * 
	 */
	@Override
	public void handle(ActionEvent event) {
		interactions(this.system);
		
		
		
	}

	
}
