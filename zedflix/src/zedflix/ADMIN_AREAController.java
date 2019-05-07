/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedflix;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Ashraful
 */
public class ADMIN_AREAController implements Initializable {
    
    @FXML private TableView s_table;
    @FXML private TableColumn s_idColumn;
    @FXML private TableColumn s_nameColumn;
    @FXML private TableColumn s_genreColumn;
    @FXML private TableColumn s_releaseColumn;
    @FXML private TableColumn s_typeColumn;
    @FXML private TableColumn s_posterColumn;
    @FXML private TableColumn s_videoColumn;
    
    @FXML private TextField ap_id;
    @FXML private TextField ap_name;
    @FXML private TextField ap_genre;
    @FXML private TextField ap_release;
    @FXML private TextField ap_type;
    @FXML private TextField ap_posterUrl;
    @FXML private TextField ap_videoUrl;
    
    @FXML private Label ap_message;
    
    Model mObj = new Model();
    public int update_id;
    
    @FXML
    public void updateMovie() {
        String id=ap_id.getText(),name=ap_name.getText(), genre=ap_genre.getText(),release=ap_release.getText(), type=ap_type.getText(), photo=ap_posterUrl.getText(), video=ap_videoUrl.getText();
        String sqlQuery="";
        int flag=0, i=0;
        
        if(update_id > -1) {
            sqlQuery="UPDATE MOVIES SET ";
            
            //Check , where does the id belong
            ResultSet rs=mObj.runSimpleQuery("SELECT COUNT(id) as vals FROM MOVIES WHERE ID="+id);
            String val="";
                    
            try {
                rs.next();
                val = rs.getString(1);
            } catch (SQLException ex) {
                Logger.getLogger(ADMIN_AREAController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            //System.out.println("result0: "+val);
            if(val.charAt(0)=='0') {
                //System.out.println("result1: "+val);
                sqlQuery="UPDATE demo.MOVIES@DATABASE_LINK1 SET ";
            }

            
            if(id.length()>0) {
            ap_message.setText("Can't update ID.! ");
            }
            if(name.length()>0) {
                sqlQuery += "NAME = '"+name+"',";
            }
            if(genre.length()>0) {
                sqlQuery += "GENRE = '"+genre+"',";
            }
            if(release.length()>0) {
                sqlQuery += "RELEASE = "+release+",";
            }
            if(type.length()>0) {
                sqlQuery += "TYPE = '"+type+"',";
            }
            if(photo.length()>0) {
                sqlQuery += "POSTER_URL = '"+photo+"',";
            }
            if(video.length()>0) {
                sqlQuery += "VIDEO_URL = '"+video+"',";
            }
            
            else {
                ap_message.setText("Nothing to update");
            }
        }
        
        else {
            ap_message.setText("Select a row to update.");
        }
        
        if(sqlQuery.endsWith(",")) {
            sqlQuery = sqlQuery.substring(0, sqlQuery.lastIndexOf(","));
            sqlQuery += " WHERE ID="+update_id;
            
            System.out.println(sqlQuery);
            ResultSet rs = mObj.runSimpleQuery(sqlQuery);
            ap_message.setText("Data Updated Successfully");
            getMovieListFromDB();
            
        }
    }
    
    @FXML
    public void addNewMovie() {
        String id=ap_id.getText(),name=ap_name.getText(), genre=ap_genre.getText(),release=ap_release.getText(), type=ap_type.getText(), photo=ap_posterUrl.getText(), video=ap_videoUrl.getText();
        
        if(id.length()>0 && name.length()>0 && genre.length()>0 && release.length()>0 && type.length()>0 && photo.length()>0 && video.length()>0) {
            String[] val1 = {"ID"}, val2={id};
            if(mObj.checkValueFromDb("MOVIES", val1, val2)) {
                ap_message.setText("ID already exist.");
            } else {
                String sqlQuery;
                sqlQuery = "INSERT INTO MOVIES(ID,NAME,GENRE,RELEASE,TYPE,POSTER_URL,VIDEO_URL) values("+id+",'"+name+"','"+genre+"',"+release+",'"+type+"','"+photo+"','"+video+"')";
                ResultSet rs = mObj.runSimpleQuery(sqlQuery);
                getMovieListFromDB();
                ap_message.setText("New Record inserted.");
            }
        } else {
            ap_message.setText("Fill out all fields to insert.");
        }
    }
    
    /**
     *
     * @param movieTable
     */
    @FXML
    public void getMovieListFromDB() {
        ap_message.setText("Message:");
        ResultSet rs;
        final ObservableList<QueryDataList> data = FXCollections.observableArrayList();
        
        try {

            String query = "SELECT * FROM MOVIES";
            //System.out.println(query);
            rs = mObj.runSimpleQuery(query);

            //int id, String name, String genre, int release, String type, String poster_url, String movie_url
            while(rs.next()){
                data.add(new QueryDataList(rs.getInt("ID"), rs.getString("NAME"),rs.getString("GENRE"),rs.getInt("RELEASE"),rs.getString("TYPE"),rs.getString("POSTER_URL"),rs.getString("VIDEO_URL") ));
            }
    
            s_idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
            s_nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
            s_genreColumn.setCellValueFactory(new PropertyValueFactory<>("genre"));
            s_releaseColumn.setCellValueFactory(new PropertyValueFactory<>("release"));
            s_typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
            s_posterColumn.setCellValueFactory(new PropertyValueFactory<>("poster_url"));
            s_videoColumn.setCellValueFactory(new PropertyValueFactory<>("movie_url"));
            s_table.setItems(data);
            
            //Doing again for another table
            query = "SELECT * FROM demo.MOVIES@DATABASE_LINK1";
            //System.out.println(query);
            rs = mObj.runSimpleQuery(query);

            //int id, String name, String genre, int release, String type, String poster_url, String movie_url
            while(rs.next()){
                data.add(new QueryDataList(rs.getInt("ID"), rs.getString("NAME"),rs.getString("GENRE"),rs.getInt("RELEASE"),rs.getString("TYPE"),rs.getString("POSTER_URL"),rs.getString("VIDEO_URL") ));
            }
    
            s_idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
            s_nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
            s_genreColumn.setCellValueFactory(new PropertyValueFactory<>("genre"));
            s_releaseColumn.setCellValueFactory(new PropertyValueFactory<>("release"));
            s_typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
            s_posterColumn.setCellValueFactory(new PropertyValueFactory<>("poster_url"));
            s_videoColumn.setCellValueFactory(new PropertyValueFactory<>("movie_url"));
            s_table.setItems(data);
            
        } catch (SQLException | NullPointerException e) {
            System.out.println("Error: "+e.getMessage());
        }
        
    } 
    
    public int delMvFlg=0;
    public void deleteMovie(MouseEvent event) {
        
        try {
            //getting the url from the list
            QueryDataList person = (QueryDataList) s_table.getSelectionModel().getSelectedItem();
            int movieId;

            movieId = person.getId();
            System.out.println(movieId);
            
            if(movieId > -1) {
                ResultSet rs = mObj.runSimpleQuery("DELETE FROM MOVIES WHERE ID="+movieId);
                
                rs.next();
                String val = rs.getString(1);
                System.out.println("Answer: "+val);
                
                getMovieListFromDB();
                ap_message.setText("Deletion successful");
            }
            System.out.println("Hi");
            
        } catch (Exception e) {
            System.out.println("Error: "+e.getMessage());
            tryToDeleteFromAnotherTable();
        }
    }
    
    //Try Deleting from the other table too now
    public void tryToDeleteFromAnotherTable() {
        
        try {
            //getting the url from the list
            QueryDataList person = (QueryDataList) s_table.getSelectionModel().getSelectedItem();
            int movieId;

            movieId = person.getId();
            System.out.println(movieId);
            
            if(movieId > -1) {
                ResultSet rs = mObj.runSimpleQuery("DELETE FROM demo.MOVIES@DATABASE_LINK1 WHERE ID="+movieId);
                
                rs.next();
                String val = rs.getString(1);
                System.out.println("Answer: "+val);
                
                getMovieListFromDB();
                //ap_message.setText("Deletion successful");
            }
            System.out.println("Hi");
            
        } catch (Exception e) {
            System.out.println("Error: "+e.getMessage());
            ap_message.setText("Deletion successful");
        }
    }
    
    public void fillOutTopFields() {
        try {
            //getting the url from the list
            QueryDataList person = (QueryDataList) s_table.getSelectionModel().getSelectedItem();
            String movieId, release;
            
            ap_name.setText(person.getName());
            ap_genre.setText(person.getGenre());
            ap_type.setText(person.getType());
            ap_posterUrl.setText(person.getPoster_url());
            ap_videoUrl.setText(person.getMovie_url());
            
            update_id = person.getId();
            
        } catch (Exception e) {
            System.out.println("Error: "+e.getMessage());
        }
    }
    
    public void clearFields() {
        
        ap_id.setText("");
        ap_name.setText("");
        ap_genre.setText("");
        ap_release.setText("");
        ap_type.setText("");
        ap_posterUrl.setText("");
        ap_videoUrl.setText("");
        
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        getMovieListFromDB();
        
        
        
    }    
    
}
