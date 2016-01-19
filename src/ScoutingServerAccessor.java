/** 
 * @author Thomas Hayden
 */

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.mysql.jdbc.exceptions.jdbc4.CommunicationsException;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

public class ScoutingServerAccessor {
	private String serverUsername;
	private String serverPassword;
	private String serverIP;
	private String databaseName;
	
	/**
	 * Constructs a default ScoutingServerAccessor
	 * serverUsername = root
	 * serverPassword = root
	 * serverIP = 127.0.0.1
	 * databaseName = samplescoutingserverschema
	 */
	public ScoutingServerAccessor() {
		serverUsername = "root";
		serverPassword = "root";
		serverIP = "localhost";
		databaseName = "samplescoutingserverschema";
	}
	
	/**
	 * Construct a new ScoutingServerAccessor using the given information
	 * @param serverUsername the username used to connect to the server
	 * @param serverPassword the password used to connect to the server
	 * @param serverIP the ip of the server
	 * @param databaseName the name of the database in the server
	 */
	public ScoutingServerAccessor(String serverUsername, String serverPassword, String serverIP, String databaseName) {
		this.serverUsername = serverUsername;
		this.serverPassword = serverPassword;
		this.serverIP = serverIP;
		this.databaseName = databaseName;
	}
	
	/**
	 * Execute a query command
	 * @param cmd the command to run
	 * @return the ResultSet containing the returned information
	 * @throws SQLException
	 */
	public ResultSet executeQuery(String cmd) throws SQLException {
		MysqlDataSource dataSource = connect();
		
		Connection conn = null;
		try {
			conn = dataSource.getConnection();
		} catch(CommunicationsException e) {
			System.err.println("CommunicationsException! The server is likely not running, or we cannot connect to it!\nMore information below!\n\n");
			e.printStackTrace();
			System.exit(0);
		}
		Statement stmt = conn.createStatement();
		stmt.executeQuery("USE " + databaseName);
		ResultSet rs2 = stmt.executeQuery(cmd);
		
		return rs2;
	}
	/**
	 * Execute an editing command
	 * @param cmd the command to run
	 * @return whether or not the command completed sucessfully
	 * @throws SQLException
	 */
	public boolean execute(String cmd) throws SQLException {
		MysqlDataSource dataSource = connect();
		
		Connection conn = null;
		try {
			conn = dataSource.getConnection();
		} catch(CommunicationsException e) {
			System.err.println("CommunicationsException! The server is likely not running, or we cannot connect to it!\nMore information below!\n\n");
			e.printStackTrace();
			System.exit(0);
		}
		Statement stmt = conn.createStatement();
		stmt.executeQuery("USE " + databaseName);
		return stmt.execute(cmd);
	}
	
	/**
	 * Use the given server information to connect to a server
	 * @return the DataSource for the sql server
	 */
	private MysqlDataSource connect() {
		MysqlDataSource dataSource = new MysqlDataSource();
		dataSource.setUser(serverUsername);
		dataSource.setPassword(serverPassword);
		dataSource.setServerName(serverIP);
		return dataSource;
	}
}
