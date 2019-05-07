package zedflix;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.util.Duration;


public class VideoPlayerController
  implements Initializable
{
  @FXML
  private MediaView mediaView;
  private MediaPlayer mediaPlayer;
  private String filePath;
  @FXML
  private Slider slider;
  @FXML
  private HBox menuBar;
  @FXML
  private Slider seekSlider;
  
  @FXML Label urlLabel;
  
  
  //String name2="C:\\Users\\Ashraful\\Desktop\\JavaFX\\zedflix\\app_data\\movies\\1_Avengers_Endgame.mp4";
  
  @FXML
  public void setMovieUrl(String text) throws MalformedURLException {
      urlLabel.setText(text);
  }
  
  @FXML
  public void playTheVideo() {
    File file=new File(urlLabel.getText());
    filePath = file.toURI().toString();
    System.out.println("Hi: "+filePath);
    //System.out.println("Hi2: "+ urlLabel.getText() );
    
    
    
    Media media = new Media(filePath);
    mediaPlayer = new MediaPlayer(media);
    mediaView.setMediaPlayer(mediaPlayer);
    setPlayerSliders();
  }
  
  @FXML
  public void setPlayerSliders() {
    
    slider.setValue(mediaPlayer.getVolume() * 100.0D);
    slider.valueProperty().addListener(new InvalidationListener()
    {
        public void invalidated(Observable observable) {
            mediaPlayer.setVolume(slider.getValue() / 100.0D);
        }


    });
    
    seekSlider.setOnMouseClicked((MouseEvent event1) -> {
        mediaPlayer.seek(Duration.seconds(seekSlider.getValue()));
    });
    
    mediaPlayer.currentTimeProperty().addListener((ObservableValue<? extends Duration> observable, Duration oldValue, Duration newValue) -> {
        seekSlider.setValue(newValue.toSeconds());
    });
  }
  
  @FXML
  private void handleButtonAction() throws MalformedURLException
  {
    
    FileChooser fileChooser = new FileChooser();
    FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("select your media(*.mp4)", new String[] { "*.mp4", "*.mkv" });
    fileChooser.getExtensionFilters().add(filter);
    File file = fileChooser.showOpenDialog(null);
    filePath = file.toURI().toString();
    
    if (filePath != null) {
        //System.out.println("Hi: "+filePath);
        
        Media media = new Media(filePath);
        mediaPlayer = new MediaPlayer(media);
        mediaView.setMediaPlayer(mediaPlayer);
        
        slider.setValue(mediaPlayer.getVolume() * 100.0D);
        slider.valueProperty().addListener(new InvalidationListener()
        {
            public void invalidated(Observable observable) {
                mediaPlayer.setVolume(slider.getValue() / 100.0D);
            }
            
            
        });
        
        mediaPlayer.currentTimeProperty().addListener((ObservableValue<? extends Duration> observable, Duration oldValue, Duration newValue) -> {
            seekSlider.setValue(newValue.toSeconds());
        });
        
        seekSlider.setOnMouseClicked((MouseEvent event1) -> {
            mediaPlayer.seek(Duration.seconds(seekSlider.getValue()));
        });
        mediaPlayer.play();
    } else {
    }
  }
  
  @FXML
  private void pauseVideo(ActionEvent event)
  {
    mediaPlayer.pause();
  }
  
  @FXML
  private void exitVideo(ActionEvent event) { System.exit(0); }
  
  @FXML
  private void stopVideo(ActionEvent event) {
    mediaPlayer.stop();
    
  }
  
  @FXML
  private void playVideo(ActionEvent event) {
    mediaPlayer.play();
    mediaPlayer.setRate(1.0D);
  }
  
  @FXML
  private void fastVideo(ActionEvent event) {
    mediaPlayer.setRate(1.5D);
  }
  
  @FXML
  private void fasterVideo(ActionEvent event) {
    mediaPlayer.setRate(2.0D);
  }
  
  @FXML
  private void slowVideo(ActionEvent event) {
    mediaPlayer.setRate(0.75D);
  }
  
  @FXML
  private void slowerVideo(ActionEvent event) { mediaPlayer.setRate(0.5D); }
  
  public void initialize(URL url, ResourceBundle rb) {

        
  }
}
