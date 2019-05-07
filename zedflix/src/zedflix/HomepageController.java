/*
 * It receives the input from the user
 * It can process urls and requests
 */
package zedflix;

import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 *
 * @author Ashraful
 */
public class HomepageController implements Initializable {
    
    
    @FXML private ComboBox h_movieGenre;
    ObservableList<String> genreList = FXCollections.observableArrayList("Action", "Sci fi", "Thriller", "Animated");
    
    @FXML private ComboBox h_movieReleaseDate;
    ObservableList<Integer> releaseYear = FXCollections.observableArrayList(2019, 2018, 2017);
    
    @FXML private ComboBox h_movieType;
    ObservableList<String> movieType = FXCollections.observableArrayList("English", "Hindi", "Foreign");
    
    // MVC Model Class
    Model mObj = new Model();
    @FXML private Button h_searchBtn;
    @FXML private TextField h_movieName;

    
    @FXML public ImageView home_topMoviePoster1;
    @FXML public ImageView home_topMoviePoster2;
    @FXML public ImageView home_topMoviePoster3;
    @FXML public ImageView home_topMoviePoster4;
    
    @FXML public Label home_topMovieName1;
    @FXML public Label home_topMovieName2;
    @FXML public Label home_topMovieName3;
    @FXML public Label home_topMovieName4;
    
    @FXML public ImageView more_moviePoster1;
    @FXML public ImageView more_moviePoster2;
    @FXML public ImageView more_moviePoster3;
    @FXML public ImageView more_moviePoster4;
    @FXML public ImageView more_moviePoster5;
    
    @FXML public Label more_movieName1;
    @FXML public Label more_movieName2;
    @FXML public Label more_movieName3;
    @FXML public Label more_movieName4;
    @FXML public Label more_movieName5;
    
    @FXML public Pane streaming_pane;
    
    public List<QueryDataList> movieData;
    
    @FXML
    public void setImageData() {
        
        
        ImageView[] topPosterArr={home_topMoviePoster1, home_topMoviePoster2, home_topMoviePoster3, home_topMoviePoster4};
        Label[] topLabelArr={home_topMovieName1, home_topMovieName2, home_topMovieName3, home_topMovieName4};
        
        ImageView[] morePosterArr={more_moviePoster1, more_moviePoster2, more_moviePoster3, more_moviePoster4, more_moviePoster5};
        Label[] moreLabelArr={more_movieName1, more_movieName2, more_movieName3, more_movieName4, more_movieName5};
        
        try {
            
            String[] attr={"ID", "NAME", "POSTER_URL", "VIDEO_URL"}, checkAttr={}, checkVal={};
            movieData = mObj.getData("MOVIES", attr, checkAttr, checkVal, "");
            //System.out.println(movieData.get(1).poster_url);
            
            //-------------------------------------
            
            String imgAddress;
            String tmpAddress;
            File file2;
            Image image;
            
            //-------------------------------------
            
            for(int i=0; i<4; i++) {
                
                imgAddress = movieData.get(i).poster_url;
                file2 = new File(imgAddress);
                tmpAddress = file2.toURI().toURL().toExternalForm();
                image = new Image(tmpAddress);
                topPosterArr[i].setImage(image);
                
                topLabelArr[i].setText(movieData.get(i).name);
                //System.out.println(tmpAddress);
            }
            for(int i=4, j=0; i<9; i++, j++) {
                
                imgAddress = movieData.get(i).poster_url;
                file2 = new File(imgAddress);
                tmpAddress = file2.toURI().toURL().toExternalForm();
                image = new Image(tmpAddress);
                morePosterArr[j].setImage(image);
                
                moreLabelArr[j].setText(movieData.get(i).name);
                //System.out.println(movieData.get(5).name);
            }
        
        } catch (MalformedURLException ex) {
            Logger.getLogger(HomepageController.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Error: "+ex.getMessage());
        }
        
        //Logger.getLogger(HomepageController.class.getName()).log(Level.SEVERE, null, ex);
    }
    
    @FXML
    public void getMovieVideo(Event event) {
        Object source = event.getSource();
        
        if (source instanceof Label){
            Label lbl = (Label) source;
            String name=lbl.getText();
            String url;
            
            if(movieData.size()>0){
                System.out.println(name);
                
                for(QueryDataList data:movieData){
                    if(data.name==name)
                    {
                        url = data.movie_url;
                        System.out.println(data.id);
                        //Go to the next movie page
                        showVideoPlayer(url);
                        break;
                    }
                }
                
            }
            
        }
        else {
            System.out.println("Nothing selected.");
        }
    }
    
    VideoPlayerController playerCntrl;
    @FXML
    private void showVideoPlayer(String url) {
        //System.out.println("zedflix.HomepageController.showVideoPlayer()");
        try {
            
            
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("videoPlayer.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            
            playerCntrl = fxmlLoader.getController();
            playerCntrl.setMovieUrl(url);
            
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.show();
            
        } catch(IOException e) {
            System.out.println(e.getMessage());
        }
    }
    
    //Go to search page
    @FXML public void showSearchPage() {
        
        try {
            String name="Avengers",genre="Action", release="2019", type="English";
            
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("searchResults.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            
            SearchResultsController srch = fxmlLoader.getController();
            
            if(h_movieName.getText()!= null) {
                name = h_movieName.getText();
            }
            if(h_movieGenre.getValue() != null) {
                genre = h_movieGenre.getSelectionModel().getSelectedItem().toString();
            }
            if(h_movieReleaseDate.getValue() != null) {
                release= h_movieReleaseDate.getSelectionModel().getSelectedItem().toString();
            }
            if(h_movieType.getValue() != null) {
                type= h_movieType.getSelectionModel().getSelectedItem().toString();
            }
            
            srch.setInfo(name, genre, release, type);
            
            System.out.println(genre+" "+release+" "+type);
            
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.show();
            
            Stage stage2 = (Stage)h_searchBtn.getScene().getWindow();
            stage2.close();
            
        } catch(RuntimeException | IOException e) {
            System.out.println("Error: "+e.getMessage());
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        h_movieGenre.setItems(genreList);
        h_movieReleaseDate.setItems(releaseYear);
        h_movieType.setItems(movieType);
        
        setImageData();
        
    }
    
        //================================================================
    // ListView HOMEPAGE
    //================================================================
    /*
    @FXML private ListView h_streamingMovieList;
    ObservableList mvList=FXCollections.observableArrayList();
    private void loadMovieListData() {
    mvList.removeAll(mvList);
    String a, b, c;
    a="Avengers Endgame";
    b="Shazam";
    c="John Wick 3";
    mvList.addAll(a, b, c);
    h_streamingMovieList.getItems().addAll(mvList);
    }
    @FXML
    public void displaySelectedMovie(javafx.scene.input.MouseEvent event) {
    if(movieName==null || movieName.isEmpty()) {
    System.out.println("Not Selected");
    } else {
    System.out.println(movieName+" Selected");
    movieName = "";
    }
    }
     */
}
