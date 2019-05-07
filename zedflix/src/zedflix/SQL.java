/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedflix;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Ashraful
 */

public class SQL {

Connection connection = null;

public String dbUsername = "ASHRAFUL";
public String dbPassword = "123";
public String dbConnString = "jdbc:oracle:thin:@localhost:1521:xe";

public SQL(){}

    public Connection getConnection () {

        try { 
            //step1 load the driver class   
            Class.forName("oracle.jdbc.driver.OracleDriver");  

            //step3 create the statement object
            try ( //step2 create  the connection object
                  Connection con = DriverManager.getConnection(  
                  dbConnString,dbUsername, dbPassword)) {
                

            }

        }
        catch(ClassNotFoundException | SQLException e){
            System.out.println("CONNECT ERROR: "+e.getMessage());
        }
                
        return connection;
    }

    public boolean checkDbConnection() {
        try { 
            //step1 load the driver class   
            Class.forName("oracle.jdbc.driver.OracleDriver");  

            //step3 create the statement object
            try ( //step2 create  the connection object
                  Connection con = DriverManager.getConnection(  
                  dbConnString,dbUsername, dbPassword)) {
                
                
                System.out.println("Connection is okey");

            }

        }
        catch(ClassNotFoundException | SQLException e){
            System.out.println(e.getMessage());
            return false;
        }
        
        return true;
    }

}