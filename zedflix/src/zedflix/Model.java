/*
 * Model interacts with the db.
 * it operates the all operations
 */
package zedflix;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Ashraful
 */
public class Model {

    SQL sqlObj = new SQL();
    
    public String dbUsername = "ASHRAFUL";
    public String dbPassword = "123";
    public String dbConnString = "jdbc:oracle:thin:@localhost:1521:xe";

    public boolean validateEmail(String email) {
        String pattern = "^([a-zA-Z0-9_\\-\\.]+)@([a-zA-Z0-9_\\-\\.]+)\\.([a-zA-Z]{2,5})$";
        Matcher matcher;
        boolean ans = Pattern.matches(pattern, email);
        
        System.out.println("Email is formattet "+ans);
        return ans;
    }
    
    public ResultSet runSimpleQuery(String queryText){
        ResultSet rs=null;
            try{  
        //step1 load the driver class  
        Class.forName("oracle.jdbc.driver.OracleDriver");  

        //step2 create  the connection object  
        Connection con=DriverManager.getConnection(  
        "jdbc:oracle:thin:@localhost:1521:xe","ASHRAFUL","123");  

        //step3 create the statement object  
        Statement stmt=con.createStatement();  

        //step4 execute query  
        rs=stmt.executeQuery(queryText);  
        
        
        //while(rs.next())  
        //System.out.println(rs.getInt(1)+"  "+rs.getString(2)+"  "+rs.getString(3));  

        //step5 close the connection object  
        //con.close();  

        }catch(Exception e){ System.out.println(e);}  
            
        return rs;
    }
    public List<QueryDataList> getData(String table, String[] attrSelect, String[] checkAttr, String[] checkVal, String additionalQ) {
        String message = "Nothing yet";
        List<QueryDataList> bilers = new ArrayList<QueryDataList>();
        ResultSet resultData = null;
        
        if(sqlObj.checkDbConnection()) {
            String SQLQuery;
            
            try {
                //step1 load the driver class   
                Class.forName("oracle.jdbc.driver.OracleDriver");  
                
                //step3 create the statement object
                try ( //step2 create  the connection object
                      Connection con = DriverManager.getConnection(
                      dbConnString,dbUsername, dbPassword)) {
                    
                    // For only selection query
                    int attrSelectLen = attrSelect.length;
                    String attr="";
                    for(int i=0; i<attrSelectLen; i++) {
                        attr+=attrSelect[i];
                        if(i<attrSelectLen-1) {
                            attr += ",";
                        }
                    }
                    SQLQuery = "SELECT "+attr+" FROM "+table;
                    
                    //For additional operations
                    String checkClause="";
                    int attrLen = checkAttr.length;
                    
                    if(attrLen>0 && checkAttr[0]!="") {
                        for(int i=0;i<attrLen ;i++ ) {
                            if(i>0) {
                                checkClause += " AND ";
                            }
                            checkClause += checkAttr[i]+" "+checkVal[i];
                        }
                        SQLQuery += " WHERE "+checkClause;
                    }
                    if(additionalQ != ""){
                        SQLQuery +=" "+additionalQ;
                    }
                    
                    System.out.println(SQLQuery);
                    
                    Statement stmt = con.createStatement();
                    resultData = stmt.executeQuery(SQLQuery);
                    
                    while (resultData.next()) {
                        QueryDataList biler = new QueryDataList();
                        biler.setId(resultData.getInt("id"));
                        biler.setName(resultData.getString("name"));
                        biler.setPoster_url(resultData.getString("poster_url"));
                        biler.setMovie_url(resultData.getString("video_url"));
                        bilers.add(biler);
                    }
                    
                }

                
            } catch (ClassNotFoundException | SQLException e) {
                message = "Error: "+e.getMessage();
                System.out.println("Error: "+e.getMessage());
            }
            /*
             finally {
                if (resultSet != null) try { resultSet.close(); } catch (SQLException ignore) {}
                if (statement != null) try { statement.close(); } catch (SQLException ignore) {}
                if (connection != null) try { connection.close(); } catch (SQLException ignore) {}
            }
            */
        }
        
        System.out.println("LOG-"+message);
        
        return bilers;
    }
    
    public String insertData(String table, String[] checkAttr, String[] checkVal) {
        String message = "Nothing yet";
        if(sqlObj.checkDbConnection()) {
            String insertSQLQuery;
            
            try {
                //step1 load the driver class   
                Class.forName("oracle.jdbc.driver.OracleDriver");  
                
                //step3 create the statement object
                try ( //step2 create  the connection object
                      Connection con = DriverManager.getConnection(
                      dbConnString,dbUsername, dbPassword)) {

                    String attrString="", attrVals="";
                    int attrLen = checkAttr.length;
                    
                    for(int i=0; i<attrLen; i++) {
                        attrString+=checkAttr[i];
                        if(i<attrLen-1) {
                            attrString += ",";
                        }
                    }
                    
                    for(int i=0;i<attrLen ;i++ ) {
                        attrVals+="'"+checkVal[i]+"'";
                        if(i<attrLen-1) {
                            attrVals += ",";
                        }
                    }
                    //System.out.println(attrString + "  " + attrVals);
                    
                    insertSQLQuery = "INSERT INTO "+table+"("+ attrString +") VALUES("+attrVals+")";

                    //System.out.println(insertSQLQuery);
                    
                    Statement stmt = con.createStatement();
                    ResultSet resultData = stmt.executeQuery(insertSQLQuery);
                    
                    message = "Hurray, you are registered, Login now.";
                    con.close();
                    

                }

                
            } catch (ClassNotFoundException | SQLException e) {
                message = "Error: "+e.getMessage();
                System.out.println("Error: "+e.getMessage());
            }
        }
        return message;
    }
    
    public int flgCheckValue=0;
    public boolean checkValueFromDb(String table, String[] checkAttr, String[] checkVal) {
        
        if(sqlObj.checkDbConnection()) {
            String checkSQLQuery="";
            try {
                //step1 load the driver class   
                Class.forName("oracle.jdbc.driver.OracleDriver");  
                
                //step3 create the statement object
                try ( //step2 create  the connection object
                      Connection con = DriverManager.getConnection(
                      dbConnString,dbUsername, dbPassword)) {


                    checkSQLQuery = "SELECT COUNT("+checkAttr[0]+") as total FROM "+table+" WHERE "+checkAttr[0]+"='"+checkVal[0]+"' ";
                    if(checkAttr.length == 2) {
                        checkSQLQuery += "AND "+checkAttr[1]+" = '"+checkVal[1]+"'";
                    }
                    if(checkAttr.length == 3) {
                        checkSQLQuery += "AND "+checkAttr[2]+" = '"+checkVal[2]+"'";
                    }

                    System.out.println(checkSQLQuery);

                    Statement stmt = con.createStatement();
                    ResultSet resultData = stmt.executeQuery(checkSQLQuery);
                    
                    resultData.next();
                    String val = resultData.getString(1);
                    //System.out.println("result0: "+val);
                    if(val.charAt(0)=='1') {
                        //System.out.println("result1: "+val);
                        
                        return true;
                    }
                    else {
                        if(flgCheckValue>-1) {
                            flgCheckValue++;
                            
                            String newTable = "demo."+table+"@DATABASE_LINK1";
                            checkValueFromDb(newTable, checkAttr, checkVal);
                            if(checkValueFromDb(newTable, checkAttr, checkVal)) {
                                return true;
                            }
                        }
                    }
                    con.close();
                }

                
            } catch (ClassNotFoundException | SQLException e) {
                System.out.println("Error: "+e.getMessage());
            }
        }
        return false;
    }
    
    
}
