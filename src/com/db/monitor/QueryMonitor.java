package com.db.monitor;

import com.dynatrace.diagnostics.pdk.*;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.Authenticator;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Logger;
import java.util.logging.Level;
import com.ibm.db2.jcc.licenses.*;

public class QueryMonitor implements Monitor {
	private static final int MAXCOLS = 10;
	private static final Logger log = Logger.getLogger(QMonitor.class.getName());
	private static final String CONFIG_PORT = "SQLPort";
	private static final String CONFIG_DATABASE = "Database";
	private static final String CONFIG_URL = "URL";
	private static final String CONFIG_USERNAME = "Username";
	private static final String CONFIG_PASSWORD = "Password";
	private static final String CONFIG_STATEMENT = "SQLState";
	private static final String CONFIG_AD = "Windows";
	private static final String CONFIG_TIMEOUT = "Timeout";
	private static final String CONFIG_MATCHCONTENT = "MatchContent";
	private static final String CONFIG_MATCHCOlUMN = "MatchColumn";
	private static final String CONFIG_MATCHVALUE = "MatchValue";
	private static final String CONFIG_GETCOLUMN = "ColumnCapture";
	private static final String CONFIG_TYPE = "SQLType";
	private static final String CONFIG_CNAME = "CName";
	private int rowcount = 0;
	private int dbconnect = 0;
	private int contentMatched = 0;
	private double[] columnValue;
	private String[] CName;
	private String connectionUrl = "";
	private String sqlclass = "";
	private long startTime = 0;
	private long connectStartTime = 0;
	private long queryStartTime = 0;
	private long endTime = 0;
	private long connectEndTime = 0;
	private long queryEndTime = 0;
	
	private String Username;
	private String Password;
	private boolean windows;
	private String SQLServer;
	private String Port;
	private String Database;
	private String URL;
	private boolean matchContent;
	private String matchColumn;
	private String matchValue;
	private boolean getc;
	private String SQLType;
	private long timeout;

	@Override
	public Status setup(MonitorEnvironment env) throws Exception {
		return new Status(Status.StatusCode.Success);
	}

	@Override
	public Status execute(MonitorEnvironment env) throws Exception {
		//define the connection to the database

		Username = env.getConfigString(CONFIG_USERNAME);
		Password = env.getConfigPassword(CONFIG_PASSWORD);
		windows = env.getConfigBoolean(CONFIG_AD);
		SQLServer = env.getHost().getAddress();
		Port = env.getConfigString(CONFIG_PORT);
		Database = env.getConfigString(CONFIG_DATABASE);
		URL = env.getConfigString(CONFIG_URL);
		matchContent = env.getConfigBoolean(CONFIG_MATCHCONTENT);
		matchColumn = env.getConfigString(CONFIG_MATCHCOlUMN);
		matchValue = env.getConfigString(CONFIG_MATCHVALUE);
		getc = env.getConfigBoolean(CONFIG_GETCOLUMN);
		SQLType = env.getConfigString(CONFIG_TYPE);
		timeout = env.getConfigLong(CONFIG_TIMEOUT);
		CName = new String[MAXCOLS];
		columnValue = new double[MAXCOLS];
		
		/*
		 * Extract out the column names and save them out as the names of the dynamic measures. Retain backward
		 * compatibility with the v2 version of this plug-in
		 */
		String columnName;
		if (getc) {
			columnName = env.getConfigString(CONFIG_CNAME);
			if (columnName != null && columnName.trim().length() > 0) {
				CName[0] = columnName;
			}
			for (int i=1;i<MAXCOLS;i++) {
				columnName = env.getConfigString(CONFIG_CNAME+Integer.toString(i));
				if (columnName != null && columnName.trim().length() > 0) {
					CName[i] = columnName;
				}
			}
		}
		
		/*
		 * Set the connectionUrl and sqltype variables, to be used to connect to the database
		 */
		Status status = null;
		status = setConnectionConfig(env, SQLType, SQLServer, Port, Database, URL);
		if (status != null)
			return status;
		
		String SQL = env.getConfigString(CONFIG_STATEMENT);
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		dbconnect = 0;
		
		/*
		 * Start timing the database call
		 */
		startTime = System.nanoTime();
		connectStartTime = startTime;
		
		//connect to the database and check the number of rows returned
		log.info("Connecting to " + SQLType + " database, host " + SQLServer + ":" + Port + " database name " + Database);
		try {
	         // Establish the connection.
	        Class.forName(sqlclass);
	        if(SQLType.equals("Microsoft SQL Server")) {
	        	con = DriverManager.getConnection(connectionUrl);
	 		}
	 		else if(SQLType.equals("Oracle")) {
	 			Properties props = new Properties();
	 	        props.setProperty("user", Username);
	 	        props.setProperty("password", Password);
	 	        con = DriverManager.getConnection(connectionUrl,props);
	 		}
	 		else if(SQLType.equals("IBM DB2")) {
	 			con = DriverManager.getConnection(connectionUrl, Username, Password);
	 		}
	 		else if(SQLType.equals("Ingres")) {
	 			con = DriverManager.getConnection(connectionUrl, Username, Password);
	 		}
	 		else if(SQLType.equals("Postgres")) {
	 			con = DriverManager.getConnection(connectionUrl, Username, Password);
	 		}
	 		else if(SQLType.equals("MySQL")) {
	 			con = DriverManager.getConnection(connectionUrl, Username, Password);
	 		}
	 		else if(SQLType.equals("IBM Netezza")) {
	 			con = DriverManager.getConnection(connectionUrl, Username, Password);
	 		}	
	 		else {
	 			return new Status(Status.StatusCode.ErrorInternalConfigurationProblem);
	 		}
	        
	        /*
	         * We have a database connection, stop timing.
	         */
	        connectEndTime = System.nanoTime();
	        queryStartTime = connectEndTime;
	         
	        // Create and execute an SQL statement that returns some data.
	        log.info("Executing SQL: " + SQL);
	        stmt = con.createStatement();
	     
	        if (timeout > 0) {
	        	if (log.isLoggable(Level.FINE))
	        		log.fine("Query Timeout (seconds): " + timeout);
	         
	        	stmt.setQueryTimeout((int)timeout);
	        }
	         
	        rs = stmt.executeQuery(SQL);
	        dbconnect = 1;
	        rowcount = 0;
	        contentMatched = 0;
	         
	        /*
	         *	Iterate through the data in the result set to count how many records exist. Note that
	         * if you are retrieving column values, the result set should only have a single record in it to
	         * improve performance and provide predictability on the values being returned.
	         *
	         * Errors are captured but do not cause the iteration to abort; instead all of the errors are captured
	         * and logged, to become available for later analysis.
	         */
	        while (rs.next()) {
	        	 if((rowcount == 0) && matchContent == true) {
	        		 status = compareColumnValue(matchColumn, matchValue, rs);
	        	 }
	        	 
	        	 if((rowcount == 0) && getc == true) {
	        		 for (int i = 0; i<MAXCOLS; i++) {
        				 if (CName[i] != null  && CName[i].trim().length() > 0) {
        					 String columnValueStr;
        					 
			        		 try {
			        			 if (log.isLoggable(Level.FINER))
			        				 log.finer("Retrieving column value for column " + CName[i]);
			        			 
			        			 columnValueStr = rs.getString(CName[i]);
			        			 
			        			 try {
				        			 columnValue[i] = Double.parseDouble(columnValueStr);
				        		 }
				        		 catch(Exception e) {
				        			 log.log(Level.SEVERE, "Error converting column " + CName[i] + " to a numeric value. ", e);
				        			 if (status == null)
				        				 status = new Status(Status.StatusCode.ErrorInternalConfigurationProblem);
				        		 }
			        		 }
			        		 catch(Exception e) {
			        			 log.log(Level.SEVERE, "Error retrieving column " + CName[i] + " from the resultset. ", e);
			        			 if (status == null)
			        				 status = new Status(Status.StatusCode.ErrorInternalConfigurationProblem);
			        		 }
        				 }
	        		 }
	        	 }
	        	 rowcount++;
	         }
	         
	      }
		  catch (SQLException e) {
			  /*
			   * Handle any unexpected database errors that may have occurred, including the timeout condition. Unfortunately, there is no way
			   * between the timeout condition and other errors, and we want to be able to generate incidents if the end time is too long. 
			   * 
			   * So the tradeoff we'll try is to have no records get returned, indicate that the database didn't open correct, but flag the plug-in status
			   * as success.
			   * 
			   * Update from Rick B: It's better to show something is wrong from the monitor status to indicate to the user to look at the log.
			   * In the previous case a user can misconfigure something like the port number and the plugin looks like it runs successfully with valid 
			   * values for all measures, even though they're not retrieving any data.
			   */
	    	  StringWriter sw = new StringWriter();
	    	  PrintWriter pw = new PrintWriter(sw);
	    	  e.printStackTrace(pw);
	    	  dbconnect = 0;
	    	  rowcount=0;
	    	  log.severe(sw.toString());
		      status = new Status(Status.StatusCode.PartialSuccess);
		  }
	      catch (Exception e) {
	    	  /*
	    	   * Not a timeout error; flag the fact that the plug-in didn't run correctly.
	    	   */
	    	  StringWriter sw = new StringWriter();
	    	  PrintWriter pw = new PrintWriter(sw);
	    	  e.printStackTrace(pw);
	    	  dbconnect = 0;
	    	  log.severe(sw.toString());
		      status = new Status(Status.StatusCode.ErrorInternalException);
		  }
		  finally {
		    	  
		          if (rs != null) try { rs.close(); } catch(Exception e) {}
		          if (stmt != null) try { stmt.close(); } catch(Exception e) {}
		          if (con != null) try { con.close(); } catch(Exception e) {}
		 }
		
		/*
		 * Everything's done; capture the time. 
		 */
		endTime = System.nanoTime();
		queryEndTime = endTime;
		
		/*
		 * Flag the call as successful; otherwise keep failure code and indicate that no records were retrieved.
		 */
		if (status == null) {
			status = new Status(Status.StatusCode.Success);
		}
		else {
			rowcount = 0;
		}
		
		if (log.isLoggable(Level.FINE))
			log.fine("Rowcount: " + rowcount);

		status.setMessage("Rowcount: " + rowcount);		
		return status;
	}
	
	/**
	 * setConnectionConfig
	 * 	Formats and sets the 'connectionUrl' and 'sqlclass' variables based on the configuration data set for the plugin.
	 * 
	 * Returns
	 * 	null if everything worked ok
	 *  Status with error code if the data entered by the user was determined to be invalid.
	 */
	protected Status setConnectionConfig(MonitorEnvironment env, String SQLType, String SQLServer, String Port, String Database, String URL) {		
		/*
		 * Format the connection URL to use to connect to the database, and determine which sql driver should be used.
		 */
		if(SQLType.equals("Microsoft SQL Server")) {
			connectionUrl = "jdbc:sqlserver://" + SQLServer + ":" + Port + ";" + "databaseName=" + Database + ";username=" + Username + ";password=" + Password + ";";
			sqlclass = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
			if(windows) {
				connectionUrl = "jdbc:sqlserver://" + SQLServer + ":" + Port + ";" + "databaseName=" + Database + ";integratedSecurity=true;";
			}
		}
		else if(SQLType.equals("Oracle")) {
			sqlclass = "oracle.jdbc.driver.OracleDriver";
			connectionUrl = "jdbc:oracle:thin:@" + SQLServer + ":" + Port + ":" + Database;
		}
		else if(SQLType.equals("IBM DB2"))
		{
			sqlclass = "com.ibm.db2.jcc.DB2Driver";
			boolean schema = env.getConfigBoolean("Schema");
			if(schema) {
				String Svalue = env.getConfigString("Svalue");
				connectionUrl = "jdbc:db2://" + SQLServer + ":" + Port + "/" + Database + ":currentSchema=" + Svalue  + ";" ;
			}
			else {
				connectionUrl = "jdbc:db2://" + SQLServer + ":" + Port + "/" + Database;
			}
		}
		else if (SQLType.equals("Ingres")) 
		{
			sqlclass = "com.ingres.jdbc.IngresDriver";
			connectionUrl = "jdbc:ingres://" + SQLServer + ":" + Port + "/" + Database + ";";
		}
		else if (SQLType.equals("Postgres")) 
		{
			sqlclass = "org.postgresql.Driver";
			connectionUrl = "jdbc:postgresql://" + SQLServer + ":" + Port + "/" + Database;
		}
		else if (SQLType.equals("MySQL")) 
		{
			sqlclass = "com.mysql.jdbc.Driver";
			connectionUrl = "jdbc:mysql://" + SQLServer + ":" + Port + "/" + Database;
		}
		else if (SQLType.equals("IBM Netezza")) 
		{
			sqlclass = "org.netezza.Driver";
			connectionUrl = "jdbc:netezza://" + SQLServer + ":" + Port + "/" + Database;
		}
		else {
			log.warning("Unknown SQLType: " + SQLType);
 			return new Status(Status.StatusCode.ErrorInternalConfigurationProblem);
 		}
		
		/*
		 * If the URL is explicitly specified, use it instead of what was calculated out...
		 */
		if (URL != null && URL.trim().length() > 0) {
			connectionUrl = URL;
		}
		
		return null;
	}
	
	/**
	 * compareColumnValue
	 * 	Compares the contents of the columnName column against the value in the columnValue variable. Sets the contentMatched
	 *  flag to true or false, depending on on the result. 
	 *  
	 * Returns
	 * 	null if everything worked ok
	 *  Status with error code if the data entered by the user was determined to be invalid.
	 */
	protected Status compareColumnValue(String matchColumn, String matchValue, ResultSet rs) {
		 String columnValueStr = null;
		 contentMatched = 0;
		 Status status = null;
		 if (matchColumn == null || matchValue == null || matchColumn.trim().length()==0 || matchValue.trim().length()==0) {
			 log.severe("A column value comparison was specified but either the column name or the column value is blank");
			 
			 status = new Status(Status.StatusCode.ErrorInternalConfigurationProblem);
		 }
		 else {
			 if (log.isLoggable(Level.FINE))
				 log.fine("Checking to see if column '" + matchColumn + "' contains value '" + matchValue + "'");
			 
			 try {
    			 columnValueStr = rs.getString(matchColumn);
    		 }
    		 catch(Exception e) {
    			 log.log(Level.SEVERE, "Error retrieving column " + matchColumn + " from the resultset. ", e);
    		     status = new Status(Status.StatusCode.ErrorInternalConfigurationProblem);
    		     return status;
    		 }
			 
			 if (columnValueStr != null && columnValueStr.matches(matchValue)) {
				 if (log.isLoggable(Level.FINER))
    				 log.finer("Column '" + matchColumn + "', value '" + columnValueStr + "'," + " matches value '" + matchValue + "'");
				 contentMatched = 1;
			 }
			 else {
				 if (log.isLoggable(Level.FINER))
    				 log.finer("Column '" + matchColumn + "', value '" + columnValueStr + "'," + " does not match value '" + matchValue + "'");
			 }
		 }
		 
		 return status;
	}

	protected int numRows() {
		return rowcount;
	}

	protected int numConnect() {
		return dbconnect;
	}

	protected double[] cValue() {
		return columnValue;
	}
	
	protected String[] getMeasureNames() {
		return CName;
	}
	
	protected double responseTime() {
		if (endTime == 0)
			return 0;
		
		/*
		 *	Convert from nanoseconds to milliseconds, to be more manageable but still relatively high precision. 
		 */
		long nanoSeconds = (endTime - startTime);
		double milliSeconds = nanoSeconds * 0.000001;
		
		if (log.isLoggable(Level.FINE))
			log.fine("Elapsed Response Time (milliseconds): " + milliSeconds);
		
		/*
		 * catch potential documented edge case where nanoTime could return a timestamp in the future, and thus return a negative
		 * number. In theory should never happen, but normalize to 0 just in case.
		 */
		if (milliSeconds < 0) {
			log.warning("Calculated a negative duration of " + milliSeconds);
			return 0;
		}
		
		return milliSeconds;
	}
	
	protected double connectResponseTime() {
		if (connectEndTime == 0)
			return 0;
		
		/*
		 *	Convert from nanoseconds to milliseconds, to be more manageable but still relatively high precision. 
		 */
		long nanoSeconds = (connectEndTime - connectStartTime);
		double milliSeconds = nanoSeconds * 0.000001;
		
		if (log.isLoggable(Level.FINE))
			log.fine("Elapsed Connect Response Time (milliseconds): " + milliSeconds);
		
		/*
		 * catch potential documented edge case where nanoTime could return a timestamp in the future, and thus return a negative
		 * number. In theory should never happen, but normalize to 0 just in case.
		 */
		if (milliSeconds < 0) {
			log.warning("Calculated a negative connection duration of " + milliSeconds);
			return 0;
		}
		
		return milliSeconds;
	}
	
	protected double queryResponseTime() {
		if (queryEndTime == 0)
			return 0;
		
		/*
		 *	Convert from nanoseconds to milliseconds, to be more manageable but still relatively high precision. 
		 */
		long nanoSeconds = (queryEndTime - queryStartTime);
		double milliSeconds = nanoSeconds * 0.000001;
		
		if (log.isLoggable(Level.FINE))
			log.fine("Elapsed Query Response Time (milliseconds): " + milliSeconds);
		
		/*
		 * catch potential documented edge case where nanoTime could return a timestamp in the future, and thus return a negative
		 * number. In theory should never happen, but normalize to 0 just in case.
		 */
		if (milliSeconds < 0) {
			log.warning("Calculated a negative query duration of " + milliSeconds);
			return 0;
		}
		
		return milliSeconds;
	}
	
	protected int contentVerified() {
		return contentMatched;
	}

	@Override
	public void teardown(MonitorEnvironment env) throws Exception {
		// TODO
	}
}
