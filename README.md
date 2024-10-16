Ticket Service

Java version - 17

SpringBoot version - 3.3.4

Database Used - H2 [in-memory]

Server - Tomcat

Steps to build the project:
1. mvn clean install


Steps to run the jar;
1. Go to target folder
2. java -jar ticket-reservation-0.0.1-SNAPSHOT.jar


Steps to run Test cases:
1. mvn test


API calls:
1. GET API call to fetch the available seats. In below, the levelId field is optional.
    localhost:8080/tickets/available?levelId=1

2. POST API call to hold the seats
   localhost:8080/tickets/hold
   request object :
           {
           "numSeats": 500,
           "customerEmail": "abc1@mail.com",
           "minLevel": 3,
           "maxLevel": 4
           }
   In above request object, min and max level fields are optional.
    The response will be SeatHold object containing setHoldId and the seats held.

3. POST API call to reserve the holded seats.
   localhost:8080/tickets/reserve
   request object :
   {
   "seatHoldId":3,
   "customerEmail":"abc1@mail.com"
   }
    Response will be a String containing the reservation ID.