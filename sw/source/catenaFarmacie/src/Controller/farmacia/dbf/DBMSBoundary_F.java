package Controller.farmacia.dbf;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBMSBoundary_F {
	
	protected DBMSBoundary_F() {}
	
	//attributi
	private static final String URL = "jdbc:mysql://localhost:3306/farmacie?";
    private static final String USER = "root";
    private static final String PASSWORD = "";
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    
    private static Connection connection;

    
	//getters
    protected static Connection conn() {
    	
    	try {
    		
	    	if(null == connection || connection.isClosed()) {
				Class.forName(DRIVER);
				connection = DriverManager.getConnection(URL, USER, PASSWORD);
	    	}
    	}
    	
		catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
		
		return connection;
    }
    
    //metodi
    public static void closeConnection() throws SQLException {
    	connection.close();
    }
}