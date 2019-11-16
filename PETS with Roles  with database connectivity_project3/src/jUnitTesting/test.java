package jUnitTesting;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.jupiter.api.Test;

/**************
 * This class test the creation of the database and retrieve it check is teh saved 
 * and existin is the same
 * 
 * @author Sumit
 *
 */

class test {
	
	
	@Test
	void testGetListItemName() {
		
		assertEquals("value", setconnection("test_table", "read_it"));
		
	}

	String setconnection(String table_name, String read_it)  {
		
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
					
		
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/pets?", "root","Naven@30");
		
		java.sql.Statement stat;
		
		stat = con.createStatement(); // Creating statement 
		String statement = "Insert into "+ table_name +" values('"+read_it+ "')" ;
	    stat.executeUpdate(statement); // Executing Query			
		
		PreparedStatement ps = con.prepareStatement("select * from "+table_name);
		ResultSet rs = ps.executeQuery();
		
		String value = "";
		
		while(rs.next()) 
			value =  rs.getString(1);
		
		stat = con.createStatement();
		statement = "truncate "+table_name;
		stat.execute(statement);
		
		return value;
		
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return read_it;
		}
	

}
