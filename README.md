# cake-auth
A second artifact cake-auth has also been built for OAuth2 authentication and authorization of the cake-manager user.  It is responsible for generating tokens specific to the client and user.

The following is the project structure for our Cake Auth:

1) Configuration: This contains the Security Config, Application Config and Initializer.  
	- The Security config provides the usual Spring security configuration and bootstraps the authorization server using in memory token store.
	- The Application config imports the other configuration files as well as configures the properties source.
	- The Authorization Server config is responsible for generating tokens specific to the client and user.

2) User Details Service class - This has been implemented to retrieve the user data from the properties file.

3) Test classes - This consists of a test application config file and a unit test class for the service class.

To run a server locally execute the following commands:

1) In cake-auth, select from the menu bar

    Run -> Run 'CakeAuthMain'
