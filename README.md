# iVotas
Eletronic voting system for the University of Coimbra

The system is based on four applications:
  
  __RMIServer__ - a RMI server (primary and secondary). The user should run two instances of this application in order to get a primary server and a secondary server. If the primary server fails the secondary server will take it's place as a primary server. If the primary server recovers from the problem this server will become the secondary server.
  
  __AdminConsole__ - an admin console responsible for the application's managment.
  
  __TCPServer__ - a TCP server used by the voting tables.
  
  __TCPClient__ - a TCP client used by the voting terminals.
  
  Before running the application the user must install an updated version of MYSQL. Next enter in the user's MYSQL account and execute the create_table.sql script that can be found in the "bd_scripts" folder. After that run the "populate_db.sql" script to populate the database with my case tests.
  Next go to the "config.properties" file and change the configuration values (addresses,ports and database username/ password). __The updated version of this file must be copied to the "jar" folder.__
  Finally execute the jars available in the "jar" folder in the following order:
  
    java -jar RMIServer.jar (two instances of the same application to get the primary and secondary RMI servers);
    
    java -jar AdminConsole.jar
    
  	java -jar TCPServer.jar 
    
	java -jar TCPClient.jar
    
    
  Have fun!!
  
