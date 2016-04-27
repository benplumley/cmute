# User Documentation

### Booking a ride

Open the program and choose whether you are going to or from uni with the buttons at the top. Choose a date and time either by typing into the date-time picker, or using the up and down arrows to increase or decrease the selected field. Use the slider to set how many minutes either side you are willing to wait for the ride. Increasing this range will increase the number of available rides.

The pins now shown on the map represent the end points of all the rides that meet the criteria you've set. Choose one in a location that suits you, and click its pin. You will be presented with a confirmation box containing the full details of the ride you've chosen. If it all looks good, click 'Book this ride!' to finalise your booking.

### Listing a ride

Open the program and click on the 'New Listing' button. Choose whether you are going to or from uni with the buttons at the top. Choose a date and time either by typing into the date-time picker, or using the up and down arrows to increase or decrease the selected field. Use the 'Set Repetitions' button to choose which days this ride will repeat on, at the specified time. Use the dropdown box to choose how spare seats your car has - only this many passengers will be able to book a ride in your car before it will no longer appear on the map.

Finally, click the exact spot on the map that you plan to leave from. You will be presented with a confirmation box containing the full details of the ride you've created. If it all looks good, click 'Create this listing!' to finalise your listing.

# Administrator Documentation

### Starting the cmute server

The cmute server can be started by running

`java -cp mysql-connector-java-5.1.38-bin.jar:./ Server <port> <username> <password>`

on a \*nix machine and

`java -cp mysql-connector-java-5.1.38-bin.jar;. Server <port> <username> <password>`

on a Windows machine. If the client is not provided with a port number, it will default to 55511. The username and password refer to your *mysql5host.bath.ac.uk* credentials. A table with the correct name and columns (described in CREATE.sql) must be present.

### Client distribution arguments

If the client is provided no arguments, it will default to port 55511 using localhost as the address of the server. A server address can be provided, overriding localhost, or a server address and port number can be provided, overriding both. Usage, therefore, is

`java Client`

*or*

`java Client <hostname>`

*or*

`java Client <hostname> <portnumber>`
