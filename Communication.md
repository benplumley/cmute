- Client -> Server  
	Finding a ride  
		Client sends server a Query object  
    Booking a ride  
        Client sends server a Ride object  
	Posting a ride  
		Client sends server a Ride object (TODO maybe with a 'new' flag set)  
    Disconnect  
        Client sends server a Boolean false (?) so that the ServerClientThread can close  
    View my rides  
        TODO maybe a query object with a user ID set? or a different object  
- Server -> Client  
	Found rides matching a query  
		Server sends client a Ride[] (TODO what if none found? can empty arrays be sent over object streams?)  
    Booking a ride was successful  
        Server sends client a Boolean true  
    Booking a ride was unsuccessful  
        Server sends client a Boolean false  
	Found a particular user's posted rides  
        Server sends a Ride[] (TODO what if none found? can empty arrays be sent over object streams?)  

Each transaction includes a Client ID - how does the server know which client sent an object if they're all going to the same port? How does it decide which ServerClientThrad responds to each client? How does the output stream end up at the right client? I don't really understand networking when there's more than one client
