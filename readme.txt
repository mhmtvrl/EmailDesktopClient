Classes:

Connector: Handles basic connection operations
	
	Constructer: initialize connector's instances
	getMessagBox: Return MessageBox of account
	connect: Opens ssl socket to server
	login: logs in to account
	selectInbox: Operates INBOX select operation
	readInbox: Read headers
	writeMail: gets selected message body
	logOut: logs out from account
	disconnect: closes ssl socket

MessageBox: Class that provides connection service between gui and connection
Keep Email in vector
	
	Constructer: initialize object
	getsize: returns number of message
	printMessageBox: prints messages to standart input
	addMessageBox: Add message to message box
	getBox, setBox: Accessor of class

Email: Model for e-mail's.
	
	it has accessors

Session: keeps server, port and user password
	
	it has accessor functions

EmailInterfe: Keeps GUI components