# Transit-System-Software-Application
A java software application which mimics a transit system with a functional transit GUI implemented utilizing JavaFX. This project was completed in a group of 5 members in which the scrum framework was heavily emphasized.

=====
INSTRUCTIONS FOR HOW TO RUN/USE YOUR PROGRAM HERE:
=====
To run our program, you must run Events.java which is the interface in which you can input information (the GUI).

1. At the bottom of the GUI, there is a "User Interaction" button. This will pause the execution of the GUI and prompt user input in the console. This button should be used for any interaction between an individual (admin/cardholder) and the system. This includes, cardholders adding money to card, viewing trip history etc. or admins viewing reports and adding cardholders etc.

When information is needed from the user, the user interaction view will make a prompt in the format: "Enter: <input 1> <input 2> <input 3> ..."
If the prompt has an "|OR|", you must enter what is specified either on the left of the "|OR|" sign or the right of the "|OR|" sign.
All input between a "<" and ">" must have no spaces AND you must include a space between each requested input field.
For example, Enter: <new firstName> <new lastName> <new admin email> <new password> 
example of valid input would be: "John Bob john@gmail.com johnpassword"

Any request by a card holder or admin in the user interaction view will require the id and password of said cardholder/admin. This is important to ensure privacy of all clients' accounts and privacy of the systems data. On creation of either an admin or cardholder, their id is automatically generated and displayed in the console and they must input a password. These pieces of information are necessary to keep track of in order to access the specific user's information later on and to enter events for a cardholder in the GUI.

NOTE: Every System contains a manager (an admin) when the program is instantiated. The details for this manager are contained in the System class and can be changed. The information of this manager as of now is as follows:
name: "manager"
email: "manager@gmail.com"
password: "p"
Id: "0"

This information is needed in order to create more admins in the user interaction view at the beginning of the travel system 

Once you are done using the user interaction view, you must repeatedly exit until you see a message in the console stating you have exited the user interaction view. Only then will you be able to continue using the GUI.


2. Upon opening up the GUI, there is a message at the top reserved for any necessary output that the user may need to know in relation to the information they have entered

Following this, there are 6 areas for user input and each area is labeled with the expected input choices (ie. method(bus/train)). Users can input the relevant information for an individual event into each area and click the "enter" button when all necessary information is inputted.

Failure to adhere to the expected input prompts will cause an error, telling the user specifically which input is incorrect at the top of the GUI. If multiple inputs are incorrect, the error message will only refer to the most top input.

If the information you have entered is valid, only then will a confirmation message be displayed at the top of the GUI

Important Information:

1. On creation of a cardholder (see features for how to do this). Three pieces of information are displayed. This is their name, email and ID. It is important to keep note of their ID and their password to access information for this cardholder in the future or to input events for this cardholder in the GUI

2. On the creation of a cardholder, they are given a card with name: "card-a". It is important to note that when adding a card to a cardholders list of cards, the name cannot have spaces in it.

4. At the start of the system, in order to add a cardHolder or an admin, you must use the credentials of the manager of the traffic system. These credentials can be modified in the System class but as of now are...
name: "manager"
email: "manager@gmail.com"
password: "p"
Id: "0"

Once another admin has been added, any of the admins' credentials can be used to access the admin user interactions.

=====
LIST ALL FEATURES WORKED ON HERE, AND HOW TO INPUT RELEVANT DATA FOR THE FEATURE
=====

===========GENERAL FEATURES===========

FEATURE 3

Description: representing a Person in our transit system

Design Decision: We decided to create a parent class, Person, that both Admin and CardHolder inherit from. Despite both classes not sharing many methods, both classes share similar attributes and a toString() method which displays all the public details of the individual. In order to avoid repetition of the instantiation of these variables and the creation of the toString() method in both classes, we pulled it out into a parent, person class.

===========GUI FEATURES (Bonus)===========

FEATURE 1

Description: Tapping a card at a station

Usage Instructions: Tapping a card at a station is simulated in our program through the user inputting information into the GUI. A tap must be done when a user is either entering or exiting a bus or a train. The information we need is:
1. Method (bus or train)
2. Way (entering or exiting)
3. Where (stop or station number)
4. ID (the id of the user displayed on creation of a user)
5. Time (the time of the event in 24 hour format, HH:MM)
6. Card name (the name of the card that the user is using for the event)

If the information is valid, a confirmation message will be displayed at the top of the GUI and all text fields will be cleared. If the input is invalid, an error message will display which input specifically is invalid and why.

Design Decisions: 
- for our transit system, we decided to follow a MCV design pattern. Our GUI that represents the tapping at a station is one of our two views and a controller for our program. Every time information is inputted using the "enter" button, conditional on the validity of the input, our view is updated to display an appropriate message and our model is manipulated (see other features). 
- We also decided to use a GUI to focus on giving users a high level means to use our program (abstraction), rather than solely working in the console or by file reading.


===========USER INTERACTION FEATURES===========
FEATURE 1

Description: User Interactions (general)

Usage Instructions: The user interactions portion of our system contains any interaction between a person (admin/cardholder) and the transit system. Examples include cardholders adding money to cards and admins creating a cardholder or an admin.

To use this feature, once the GUI is opened, a button named "user interaction" will be displayed at the bottom. When this is clicked, the execution of the GUI will be paused and the user will prompted in the console to make an input. See section 1 from "INSTRUCTIONS FOR HOW TO RUN/USE YOUR PROGRAM HERE" for how to make a valid input. 

Before any user can view or change information pertaining to the system, they must enter whether they are a cardholder/admin, their ID, and their password. This is to ensure privacy of all cardholders and the data in the system.

All user interactions are listed below..

CARDHOLDER ACTIONS:
'CN': change name
'AM': add money to card
'AC': add card
'RC': remove card
'VCB': view card balance
'SC': suspend a travel card
'UC': unsuspend a travel card
'TH': view travel history
'ATC': view average monthly travel cost
'E': exit CardHolder interaction view

ADMIN ACTIONS:
'ID': increment day
'ACH': add cardholder
'AA': add admin
'AR': view daily report
'TR': view total report
'E': exit Admin interaction view


Design Decisions: This portion of our program is also a view and a controller in our MCV design pattern. Every time an input is made in the console as a controller, an appropriate message will follow, updating the view to continue the user interaction. If all input is valid, the user can manipulate the model and make changes to the data stored in the system (model) through one of the various interactions.

FEATURE 2 (bonus)

Description: Extra Statistical features

Usage Instructions: in the user interactions view in the console, there is an extra statistical feature for both the admins and cardholders. Cardholders are able to see the balance on any one of their cards and admins can view a total report of all transit systems' total revenue and total stops passed by all cardholders. This is separate from the daily report. This is important if the admins of the transit system wish to extend the amount information available to them in the future

Note:
- the total revenue report for admins is updated after each day is completed. For example, data for day 1 will not show on the total revenue report until day 2

design decision: we decided to store daily data about our system in a global variable accessible to other classes through getter methods to disallow any unforeseen changes to system data (encapsulation)

===========BUS TRIPS===========
FEATURE 1

Description: Fares are deducted from a card balance when riding a bus

Usage: Done automatically, conditional on input of info into GUI

Note:
- Fares are deducted from a card when a bus is entered unless trip cost is capped and current time of trip is within the time limit
- Fares are not deducted for exiting a bus
- Can go from a bus stop to a subway station all in one trip

Design Decision: 
- Illegal entries/system malfunctions result in card being deducted the cost of one fare

- All calculations regarding cardholder accounts are done strictly in the Cardholder class to avoid unnecessary changes to cardholders balances in other classes



===========SUBWAY TRIPS===========
FEATURE 1

Description: Fares are deducted from a card balance when riding a subway

Usage: Done automatically, conditional on input of info into GUI

Notes:
- Fares are accumulated and deducted for every subway station passed up to the capped fare amount if trip is within the time limit
- Fares are not deducted for entering a subway
- Can go from a subway station to a bus stop all in one trip

Design Decision: 
- Illegal entries/system malfunctions result in card being deducted the cost of one fare
- All calculations regarding cardholder accounts are done strictly in the Cardholder class to avoid unnecessary changes to holders balances in other classes

===========STORING TRIPS===========
FEATURE 1

Description: System malfunctions and/or failure to tap

Usage: Done automatically, conditional on input of info into GUI

Design Decision: 
- We decided the likely hood for a system malfunction is the same as the likely hood of a Cardholder to forget to tap on/off at a stop/station, and so the Cardholder will be charged for any and all enters/exits in the system, but if there is an error in the persons stop/station history (such as forgetting to tap) the entire trip up to that point is cleared as it is considered an invalid trip, and a new trip is started from that point on
- Whenever there is a system malfunction, or mistake on the Cardholder's side, they are charged a bus or subway fee, depending on whether the trip error occurred on a bus or on the subway. This "error fee" is independent of the travel cap.
- Trips are only stored in Cardholders recent trips if there was no input error/system malfunction


FEATURE 2

Description: recent trips info

Usage: accessed through user interaction view (see user interaction feature)

Design Decision: 
- The method, way, where, ID, time, and cardname are noted for each tap (see usage instructions for what method, way, where, etc... mean) 
- Looking at stored trips gives the starting transportation method (bus/train), the starting stop/station, the starting time, the ending transportation method (bus/train), the ending stop/station, the ending time, and the total cost of the trip
- The trips are displayed from most recent to oldest

===========SYSTEM===========
FEATURE 1

Description: Transit System Backend

Usage Instructions: This is used whenever input is made into the GUI or into the user interaction view within the console

Notes:
- Keeps track of all bus routes and the subway line, with possible intersections between the two
- Subway line does not intersect with itself
- Can set the starting travel card amount, the bus fare, the subway fare, the price cap, and the time limit of the trip to use the cap
- Bus routes all have a starting stop and ending stop, do not return to the same location, and have an intersecting subway station
- Time cap does not apply to disjointed trips, however if time limit is reached while on the subway/bus, then users can be charged again until they reach time limit again

Design Decisions: As we are using the MCV design patter, the backend of our application is represented by our System class. This is the model for our program and contains all of the private and public information pertaining to the data stored in the traffic system. To avoid any unforeseen changes to the model of our application. We wanted to avoid accessing variables directly from other classes. To do this, we used encapsulation and created getters and setters for methods that were used the most by other classes. This way, the state of the Systems variables can only be changed the way they were intended to.

===========CARDHOLDER===========
FEATURE 1

Description: cardholders account

Usage Instructions: can use these features via user interactions (see other features)

Notes:
- Have an account that stores their name, email address, ids, travel cards, trips, and monthly expenses
Via user interactions (see User interaction feature), cardholders can...
- Can change their name but not email address
- Can add/remove as many travel cards as they want 
- Can suspend/unsuspend a travel card
- Can view card balances (bonus)
- Any card will alway have a positive balance, however if a card's balance drops below $0 after a trip, they will make the payment but the card balance will be set to $0 
- Cardholders can add $10, $20, or $50 to any of their cards
- All cards start with the same balance, set by the system
- Cardholders can track their monthly expenses and average monthly costs

Design Decisions:
Inherits from the Person class, as both admins and cardholders share similar attributes and toString() method.

===========ADMINS===========
FEATURE 1

Description: Admin account

Usage Instructions: admins can access information about the transit system through the user interaction view (see user interaction feature)

Notes:
- Can compare overall revenue to their operating costs (all stops stations passed by all Cardholders)
- Have access to daily reports and all-time reports

Design Decisions: Inherits from the Person class, as both admins and cardholders share similar attributes and toString() method.

===========BUS ROUTES/SUBWAY STATIONS===========
FEATURE 1

Description: bus routes general info

Usage Instructions: This is used whenever input is made into the GUI or into the user interaction view in the console

Design Decisions:
- All defined bus routes and subway stations are defined in the system class. As of right now, all subway stations are named after colours and all bus routes after animals, except where a bus route intersects a subway station.
- If a bus route intersects a subway station, the station will be named after the name of the subway station. This is why each bus route has at least one colour on its route (each bus route must intersect the subway station at least once)
- To add a bus route to the transit system, in the system class, simply define an array list of bus stops that intersect the subway at least one time (contains the name of at least one station) then add it to the arrayList containing all bus routes (BUS_ALL_ROUTES) and the arrayList containing all stops and stations (STOPS_AND_STATIONS) 

===========TRAVEL CARDS===========

FEATURE 1

Description: travel cards general info

Usage Instructions: cards can be used when inputting info into the GUI (see GUI feature) and data about cards can be viewed in the user interaction view (see user interaction feature)

Design Decisions:
- Each Travel Card has a unique name, balance, and keeps track of trip information as the Cardholder is on the trip, but is cleared once the trip is completed and added to the users ArrayList of trips
