### N player Tambola game implemented using multithreading

This assignment is submitted for the partial fulfillment of the course Object Oriented Programming.
### Design Patterns used:
1. Singleton - GameInfo Logic
2. Builder - TicketBuilding Logic

##### Instructions to run
1. Open the Play.java file and execute its main method. 
2. Ensure that all the class files are in the same directory.

###### Description of various java files
1. GamInfo.java contains all the common info that is being accessed by both Moderator and Player.
2. Moderator and Player are the class files for describing the moderator and players respectively.
3. Ticket is an Interface being implemented by the TicketBuild class for implementing Builder Pattern
4. PositiveNumberException is the custom exception file.
5. Play.java contains the main method.