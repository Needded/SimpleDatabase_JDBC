

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class Control {

	private static final String URL = "jdbc:postgresql://localhost/app";
	private static final String user = "postgres";
	private static final String password = "password";
	Connection conn=null;
	Statement statement=null;
	PreparedStatement ps=null;
	ResultSet resultSet=null;
	
	//Create table method.
	public void create(String table) throws SQLException{
	   
	    String create = "CREATE TABLE " + table + " (id SERIAL PRIMARY KEY, names VARCHAR(20))";
	    
	    //Method to verify if the table exists.
        boolean b=verifyTable(table);
        if(b) {Main.main(null);}
		
	    
	    try {
	        conn = DriverManager.getConnection(URL, user, password);
	        statement = conn.createStatement();
	        statement.executeUpdate(create);
	        
	        System.out.println("Table created sucessfully! \n");
	       
	    } catch (SQLException e) {
	        e.printStackTrace();
	    
	    }finally {
	    	conn.close();
            statement.close();
	    }
	    Main.main(null);
	    
	}

	//Method to add data to a table.
	public void add(String table, String name) throws SQLException {
	    String add = "INSERT INTO " + table + " (names) VALUES (?)";

	    //Method to verify if the table exists.
	    boolean b= verifyTable(table);
        if(!b) {Main.main(null);}
		
        boolean c= verifyName(table, name);
        if(c) {Main.main(null);}
	    
	    try {
	    	
	    	conn = DriverManager.getConnection(URL, user, password);
	    	ps = conn.prepareStatement(add);
	        ps.setString( 1, name);
	        ps.executeUpdate();

	        
	        System.out.println("Name created sucessfully!\n");
	    	
	    } catch (SQLException e) {
	        e.printStackTrace();
	 
	    }finally {
	    	conn.close();
            ps.close();
	    }
	    Main.main(null);
	}
	
	//Method to query a table.
	public void queryAll(String table) throws SQLException {
	    String consult = "SELECT * FROM " + table;
	    String sql="SELECT COUNT(*) AS total_records FROM " + table;
	    
	   //Method to verify if the table exists.
        boolean b= verifyTable(table);
        if(!b) {Main.main(null);}
		
	    
	  		try {
	  			    	
	    	 conn = DriverManager.getConnection(URL, user, password);
	         statement = conn.createStatement();
	         
	         //Check if the table is empty.
	         resultSet = statement.executeQuery(sql);
	         
	         int totalRecords = 0;
	            if (resultSet.next()) {
	                totalRecords = resultSet.getInt("total_records");
	            }
	            if (totalRecords == 0) {
	                System.out.println("The table is empty. \n");
	                Main.main(null);
	            }
	           
	         //Execute the query   
	         resultSet = statement.executeQuery(consult);
	            
	        ResultSetMetaData metaData = resultSet.getMetaData();
	        int columnCount = metaData.getColumnCount();

	        while (resultSet.next()) {
	            for (int i = 1; i <= columnCount; i++) {
	                String column = metaData.getColumnName(i);
	                Object value = resultSet.getObject(i);
	                if(column.matches("id")) {
	                	System.out.println("Id: " + column +"\n");
	                }else{
	                	System.out.println("Nome: " + value +"\n");
	                }
	               
	            }
	        }
	       
	            
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }finally {
	    	conn.close();
            resultSet.close();
	    }
	  		Main.main(null);
	}

    //Method to update a table.
	public void update(String table, String name1, String name2) throws SQLException {
		
		String update ="UPDATE "+table+" SET names = ?  WHERE names= ?";
		
		
		//Method to verify if the table exists.
		boolean b = verifyTable(table);
        if(!b) {Main.main(null);}
		
		 //Method to verify if the name exists on table.
        boolean c=verifyName(table,name1);
        if(!c) {Main.main(null);}
		
      
		//Execute update.
		try {
	    	conn = DriverManager.getConnection(URL, user, password);
	    	ps = conn.prepareStatement(update);
	    	ps.setString(1, name2);
	    	ps.setString(2, name1);
	    	ps.executeUpdate();
	    	
	    	
	        System.out.println(name1+" updated to: "+ name2+".\n");
	        	
		}catch(SQLException e) {
			e.printStackTrace();	

		}finally {
			conn.close();
            ps.close();
		}
		Main.main(null);
	}
	
	public void deleteTable(String table) throws SQLException {
		
		        String deletarTabela="DROP TABLE "+table+";";
		
		      //Method to verify if the table exists.
                boolean b = verifyTable(table);
                if(!b) {Main.main(null);}
   			
   			 
				try {
					conn = DriverManager.getConnection(URL, user, password);
			    	statement = conn.createStatement();
			    	statement.execute(deletarTabela);
			    	
			    	System.out.println(" Table: "+ table+", deleted.\n");
			    	
			    
			        
				}catch(SQLException e) {
					e.printStackTrace();
				}finally {
					conn.close();
		            statement.close();
				}
				
				Main.main(null);
		
	}

	//Method to delete a table.
    public void delete(String table,String name) throws SQLException {
		
                 String delete="DELETE FROM "+table+" WHERE names= ?";
    	
                //Method to verify if the table exists.
                 boolean b=verifyTable(table);
                 if(!b) {Main.main(null);}
    			
    			 //Method to verify if the name exists on table.
                 boolean c=verifyName(table,name);
                 if(!c) {Main.main(null);}
         	   
                 
				
				try {
					conn=DriverManager.getConnection(URL,user,password);
					ps=conn.prepareStatement(delete);
					ps.setString(1, name);
					ps.executeUpdate();
					
					System.out.println("Name: "+name+", deleted from table: "+table+". \n");
					
					
					
				}catch(SQLException e) {
					e.printStackTrace();
					}finally {
						conn.close();
			            ps.close();
					}
				
				Main.main(null);
		
	}
	
	
	//Method to verify if the table exists.
	public boolean verifyTable(String table) throws SQLException {
		
		String verify = "SELECT 1 FROM information_schema.tables WHERE table_name = ?";
		
		try {
			conn=DriverManager.getConnection(URL,user,password);
			ps = conn.prepareStatement(verify);
		    ps.setString(1, table);
            resultSet = ps.executeQuery();
	        
	        if (resultSet.next()) {
	           System.out.println("Table exist. \n"); 
               return true;
	        }else {
	        	System.out.println("Table doesn´t exist. \n");
	        }
	        
		
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			ps.close();
			resultSet.close();
			
		}
		return false;
		
	}
	
	//Method to verify if the name exists.
	public boolean verifyName(String table, String name) {
		
		String consult = "SELECT 1 FROM "+table+" WHERE names = ?";
		
		
		try {
			ps = conn.prepareStatement(consult);
			ps.setString(1, name);		   
            resultSet = ps.executeQuery();
			
			
			if (resultSet.next()) {
                System.out.println("The name exists in the table. \n");
                return true;
            } else {
                System.out.println("The name does not exist in the table. \n");
            }
	           
			
			
		} catch (SQLException e) {

			e.printStackTrace();
		}
		
		return false;
	}


}
