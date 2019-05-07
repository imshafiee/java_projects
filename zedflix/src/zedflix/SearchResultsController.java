/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedflix;

import java.io.IOException;
import java.net.URL;
import static java.rmi.Naming.list;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import static java.util.Collections.list;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Ashraful
 */
public class SearchResultsController implements Initializable {

    @FXML private ListView theList;
    @FXML Label srch_name;
    @FXML Label srch_genre;
    @FXML Label srch_release;
    @FXML Label srch_type;
    @FXML Label srch_message;
    
    @FXML private Button s_watchBtn;
     @FXML private Button s_backButton;
    
    @FXML private TableView s_table;
    @FXML private TableColumn s_idColumn;
    @FXML private TableColumn s_nameColumn;
    @FXML private TableColumn s_genreColumn;
    @FXML private TableColumn s_releaseColumn;
    @FXML private TableColumn s_typeColumn;
    @FXML private TableColumn s_posterColumn;
    @FXML private TableColumn s_videoColumn;
    
    private final Image IMAGE_RUBY  = new Image("https://upload.wikimedia.org/wikipedia/commons/f/f1/Ruby_logo_64x64.png");
    private final Image IMAGE_APPLE  = new Image("https://i.ibb.co/jfhDYX1/apple.png");
    private final Image IMAGE_VISTA  = new Image("http://antaki.ca/bloom/img/windows_64x64.png");
    private final Image IMAGE_TWITTER = new Image("http://files.softicons.com/download/social-media-icons/fresh-social-media-icons-by-creative-nerds/png/64x64/twitter-bird.png");

    private Image[] listOfImages = {IMAGE_RUBY, IMAGE_APPLE, IMAGE_VISTA, IMAGE_TWITTER};
    
    
    public void setMovieList() {
        ObservableList<String> items =FXCollections.observableArrayList ("RUBY", "APPLE", "VISTA", "TWITTER");
        theList.setItems(items);
        
        theList.setCellFactory(param -> new ListCell<String>() {
            private ImageView imageView = new ImageView();
            @Override
            public void updateItem(String name, boolean empty) {
                super.updateItem(name, empty);
                if (empty) {
                    setText(null);
                    setGraphic(null);
                } else {
                    switch (name) {
                        case "RUBY":
                            imageView.setImage(listOfImages[0]);
                            break;
                        case "APPLE":
                            imageView.setImage(listOfImages[1]);
                            break;
                        case "VISTA":
                            imageView.setImage(listOfImages[2]);
                            break;
                        case "TWITTER":
                            imageView.setImage(listOfImages[3]);
                            break;
                        default:
                            imageView.setImage(listOfImages[3]);
                            break;
                    }
                    setText(name);
                    setGraphic(imageView);
                }
            }
        });
    }
    
    @FXML
    public void setInfo(String name,String genre, String release, String type){
        srch_name.setText(name);
        srch_genre.setText(genre);
        srch_release.setText(release);
        srch_type.setText(type);
    }
    
    //===========================================================
    Model mObj = new Model();
    
    public void getMovieListFromDB() {
        String name, genre, release, type;
        int year;
        ResultSet rs;
        final ObservableList<QueryDataList> data = FXCollections.observableArrayList();
        
        try {
            name= srch_name.getText();
            genre = srch_genre.getText();
            release = srch_release.getText();
            type = srch_type.getText();
            year = Integer.parseInt(release);

            String query = "SELECT * FROM MOVIES WHERE lower(name) like LOWER('"+name+"%') OR genre='"+genre+"' AND release>="+year+" AND type='"+type+"'";
            //System.out.println(query);
            rs = mObj.runSimpleQuery(query);

            //int id, String name, String genre, int release, String type, String poster_url, String movie_url
            while(rs.next()){
                data.add(new QueryDataList(rs.getInt("ID"), rs.getString("NAME"),rs.getString("GENRE"),rs.getInt("RELEASE"),rs.getString("TYPE"),rs.getString("POSTER_URL"),rs.getString("VIDEO_URL") ));
            }
            
            if(data.size() < 1) {
                srch_message.setText("No value found");
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
   
    VideoPlayerController playerCntrl;
    @FXML
    public void playMovieNow(MouseEvent event) {
        
        //getting the url from the list
        QueryDataList person = (QueryDataList) s_table.getSelectionModel().getSelectedItem();
        String movieUrl;
        
        movieUrl = person.getMovie_url();
        System.out.println(movieUrl);
        
        //Executing the url now
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("videoPlayer.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            
            playerCntrl = fxmlLoader.getController();
            playerCntrl.setMovieUrl(movieUrl);
            
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.show();
            
        } catch(IOException e) {
            System.out.println(e.getMessage());
        }
        

    }
    
    // HOMEPAGE
    @FXML
    public void showHomePage(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("homepage.fxml"));
            Parent root2 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root2));  
            stage.show();
            
            ((Node)(event.getSource())).getScene().getWindow().hide();
            
            
        } catch(IOException e) {
            System.out.println(e.getMessage());
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {


    }
    
}

/*
//This function gets alue from db and set to listview

    public void getMovieListFromDB() {
        String genre, release, type;
        int year;
        ResultSet rs;
        ObservableList<String> list = FXCollections.observableArrayList();
        
        try {
            genre = srch_genre.getText();
            release = srch_release.getText();
            type = srch_type.getText();
            year = Integer.parseInt(release);

            String query = "SELECT * FROM MOVIES WHERE genre='"+genre+"' AND release>="+year+" AND type='"+type+"'";
            rs = mObj.runSimpleQuery(query);

            while(rs.next()){
                list.add(rs.getInt("id") +"\t"+ rs.getString("name") 
                              +"\t"+ rs.getString("poster_url"));
            }
            
            theList.setItems(list);
            
            System.out.println(Arrays.asList(list.toArray()));
            
        } catch (SQLException | NullPointerException e) {
            System.out.println("Error: "+e.getMessage());
        }
        
    }
*/
