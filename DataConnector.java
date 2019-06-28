/*
*
*
*	Password Keeper app
*	Skelen - 05/06/2018
*
*	Data Connector
*	
*	Class for database connection
*	
*	database: passKeep.db	
*	tables: 
*	sys_usr		-	sys_usrid, sys_username, sys_password
*	data_usr	-	data_username, data_password, data_sitelink, data_id, sys_usrid 
*/

// For SQLITE connection
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;

// For getting data and querying in SQLITE
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class DataConnector
{
	// Global variables
	Connection conn = null; // Object for database connection
	private ArrayList <String> uname = new ArrayList<String>(); // variables for the queried data of user 
	private ArrayList <String> pword = new ArrayList<String>(); // variables for the queried data of user
	private ArrayList <String> slink = new ArrayList<String>(); // variables for the queried data of user
	private ArrayList <String> sname = new ArrayList<String>(); // variables for the queried data of user
	private ArrayList <Integer> uid = new ArrayList<Integer>(); // variables for the queried data of user

	DataConnector() 
	{
		
	} // End of DataConenctor constructor

	private void openConnection() throws ClassNotFoundException
	{
		// Method for opening connection
		// initializing and setting up the object for database connection
		String url = "jdbc:sqlite:E:\\Programming\\works\\Java_Works\\PasswordKeeper\\db\\passKeep.db";	// Insert database in here

		try
		{
			conn = DriverManager.getConnection(url); // establish connection
			//System.out.println("Connection to database established.");
		}catch(SQLException e)
		{
			System.out.println(e.getMessage());
			// print error if cannot connect to database	
		}
	}

	private void closeConnection()
	{
		// Method for closing connection
		try
		{
			if(conn != null)
				conn.close();
			//System.out.println("DB Connection closed.");
		}catch (SQLException e)
		{
			System.out.println(e.getMessage());
		}
	} // End of closeConnection method

	public int checkLoginUser(String usr, String pw)
	{	
		//open connection first
		try{
			openConnection();
		}catch (ClassNotFoundException e){
			System.out.println("checkLoginUser" + e.getMessage());
		}

		// Method for checking login user
		int data_output = -1; // value set to -1 to identify that there is no user in the database

		//Preparing the variable inputs for the query
		usr = "'" + usr + "'";
		pw = "'" + pw + "'";

		String sql_qry = "SELECT sys_usrid FROM sys_usr WHERE sys_username = " + usr + "AND sys_password = " + pw + ";";

		// Query in the DB
		try
		{
			Statement stmt = conn.createStatement();
			ResultSet query_result = stmt.executeQuery(sql_qry);
			
			if(query_result.next())
			{
				// This if statement is to check if there is a data returned from our SQL query
				// Also we need to use .next() to avoid the "Result Closed" exeption

				// Get the data result
				data_output = query_result.getInt("sys_usrid");
			}
		
		}catch( SQLException e)
		{
			System.out.println("SQL Error in DataConnector line checkLoginUser: " + e.getMessage());
		}

		closeConnection();
		return data_output;
	} // End of checkLoginUser method

	public boolean checkDataUser(int usrId)
	{	// Method for checking if the user has an existing data
		
		//open connection first
		try{
			openConnection();
		}catch (ClassNotFoundException e){
			System.out.println("checkDataUser" + e.getMessage());
		}

		String sql_qry = "SELECT data_id FROM data_usr WHERE sys_userid = " + usrId + ";";
		boolean data_output = false; // initialized to false
		try{
			Statement stmt = conn.createStatement();
			ResultSet query_result = stmt.executeQuery(sql_qry);

			if(query_result.next())
				data_output = true; //change value to true if login id has data

		}catch( SQLException e){
			System.out.println("SQL Error in DataConnector line checkDataUser: " + e.getMessage());
		}

		closeConnection();
		return data_output; // output initialized to false, change to true if data exist 
	}

	public void queryForUserID(int usrId)
	{	// Method for getting data in the DB based on the User ID
		// Call this method as well to get latest data incase there whas new data added or deleted
		
		//open connection first
		try{
			openConnection();
		}catch (ClassNotFoundException e){
			System.out.println("queryForUserID: " + e.getMessage());
		}

		String sql_qry = "SELECT * FROM data_usr WHERE sys_userid = " + usrId + ";";
		try{
			Statement stmt = conn.createStatement();
			ResultSet query_result = stmt.executeQuery(sql_qry);

			//clearing the list first - to ensure that list is empty before we add new data from DB
			uname.clear();
			pword.clear();
			slink.clear();
			sname.clear();
			uid.clear();

			// Get the data in the query and assign to the arraylist
			while(query_result.next())
			{	
				
				//adding the new data from DB to the list
				uname.add(query_result.getString("data_username"));
				pword.add(query_result.getString("data_password"));
				slink.add(query_result.getString("data_sitelink"));
				sname.add(query_result.getString("data_sitename"));
				uid.add(query_result.getInt("data_id"));
			}

		}catch( SQLException e){
			System.out.println("SQL Error in DataConnector line queryForUserID: " + e.getMessage());
		}
		closeConnection();
	}// End of queryForUserID method

	public void deleteDataInDB(int usrId)
	{
		//open connection first
		try{
			openConnection();
		}catch (ClassNotFoundException e){
			System.out.println("deleteDataInDB" + e.getMessage());
		}

		String sql_qry = "DELETE FROM data_usr WHERE data_id = " + usrId + ";";	

		try{
			Statement stmt = conn.createStatement();
			stmt.executeUpdate(sql_qry);

		}catch( SQLException e){
			System.out.println("SQL Error in DataConnector method deleteDataInDB: " + e.getMessage());
		}

		closeConnection();
	}// End of deleteDataInDB method

	// Method for creating new user in the DB
	public void createEntryInDB(int usrID, String sname, String uname, String pword, String slink){
		/* Method for creating/inserting new value in the database
		*/

		//open connection first
		try{
			openConnection();
		}catch (ClassNotFoundException e){
			System.out.println("createEntryInDB" + e.getMessage());
		}

		// SQL statement - for inserting using prepared statement in JDBC
		String sql_qry = "INSERT INTO data_usr(sys_userid, data_sitename, data_username, data_password, data_sitelink) VALUES(?,?,?,?,?)";

		// Use TRYnCATCH to execute the prepared statement
		try{
			// prepare the sql_qry
			PreparedStatement pstmt = conn.prepareStatement(sql_qry);
			pstmt.setInt(1, usrID); 	// user ID of sys_usr in DB - to know where to insert
			pstmt.setString(2, sname);	// site name
			pstmt.setString(3, uname);	// user name
			pstmt.setString(4, pword);	// password
			pstmt.setString(5, slink);	// site link
			
			// execute the prepared SQL statement
			pstmt.executeUpdate();

		}catch(SQLException e){
			System.out.println("SQL Error in DataConnector method createEntryInDB: " + e.getMessage());
		}

	}// End of createEntryInDB method

	// Method for editing existing user in the DB
	public void editEntryInDB(int usrID, String sname, String uname, String pword, String slink){

		//open connection to DB first
		try{
			openConnection();
		}catch (ClassNotFoundException e){
			System.out.println("editEntryInDB" + e.getMessage());
		}

		// SQL statement - for updating entry in DB
		String sql_qry = "UPDATE data_usr SET data_sitename = ?, data_username = ?, data_password = ?, data_sitelink = ? WHERE data_id = ?;";

		// try to execute the sqlite prepared statement
		try{
			PreparedStatement pstmt = conn.prepareStatement(sql_qry);
			pstmt.setString(1, sname);	// insert the site name to the '?' in sql statement
			pstmt.setString(2, uname);	// insert the user name to the '?' in sql statement
			pstmt.setString(3, pword);	// insert the password  to the '?' in sql statement
			pstmt.setString(4, slink);	// insert the site link to the '?' in sql statement
			pstmt.setInt(5, usrID);	// insert the user id to the '?' in sql statement

			//execute the prepared SQL statement
			pstmt.executeUpdate();

		}catch(SQLException e){
			System.out.println("SQL Error in DataConnector method editEntryInDB: " + e.getMessage());
		}

	}// End of editEntryInDB


	// Methods for getting the queried values to the Datapanel
	public ArrayList<String> getUname()
	{	// method for passing arraylist
		return uname;
	}

	public ArrayList<String> getPword()
	{	// method for passing arraylist
		return pword;
	}

	public ArrayList<String> getSlink()
	{	// method for passing arraylist
		return slink;
	}

	public ArrayList<String> getSname()
	{	// method for passing arraylist
		return sname;
	}

	public ArrayList<Integer> getUid()
	{	// method for passing arraylist
		return uid;
	}
}