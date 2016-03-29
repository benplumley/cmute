- Client -> Server  
	- Finding a ride  
		- Client sends server a Query object  
    - Booking a ride  
        - Client sends server a Ride object  
	- Posting a ride  
		- Client sends server a Ride object (TODO maybe with a 'new' flag set)  
    - Disconnect  
        - Client sends server a Boolean false (?) so that the ServerClientThread can close  
    - View my rides  
        - TODO maybe a query object with a user ID set? or a different object

- Server -> Client  
	- Found rides matching a query  
		- Server sends client a Ride array (TODO what if none found? can empty arrays be sent over object streams?)  
    - Booking a ride was successful  
        - Server sends client a Boolean true  
    - Booking a ride was unsuccessful  
        - Server sends client a Boolean false  
	- Found a particular user's posted rides  
        - Server sends a Ride array (TODO what if none found? can empty arrays be sent over object streams?)  

Each transaction includes a Client ID - 
	how does the server know which client sent an object if they're all going to the same port?
	How does it decide which ServerClientThrad responds to each client?
	How does the output stream end up at the right client? I don't really understand networking when there's more than one client

RE Above issue:
	As far as I understand it each thread is created as a new client connects. 
	Therefore the ServerClientThread knows where to send and recieve it's data,
	it has a socket to it's client. The main Server class just spawns new threads
	as clients connects and manages the access to the DB (via a synchronised
	method to control concurrency issues).
	
Idea:
	I think we should set up a class system of objects for our protocol.
	Each object should have a purpose and specific direction 
	i.e. Server to Client. Most of our communication objects have fundementally
	different purposes and thus will carry different data. Even if they both relate
	to rides. E.g. booking a ride can involve only sending a ride ID rather than
	sending a ride in its entirety. See outline of idea below:
	
	Server -> Client
	
		RideCollection object
			- is the collection of all rides matching the query criteria
		
		Communication Object (NEEDS BETTER NAME) (Perhaps split into two objects?)
			-Either confirms that an action has been succesful
			 or that a failure needs to be dealt with.
			-Used to confirm that a ride has been booked.
			-Used to confirm that a ride has been posted.
			-Used to signal that no appropriate results found for query
			
	Client -> Server
	
		RideQuery
			-Is a set of criteria for the server to use
		
		NewRide
			-Is the details of a new ride
			CAN NOT HAVE A RIDE ID YET
			
		BookRide
			-Is just the ride ID of the ride being booked
			
		Quit
			-Signals user wants to quit thus close the thread