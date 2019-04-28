/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package onclickimageloader;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

/**
 *
 * @author Ashraful
 */
public class FXMLDocumentController implements Initializable {
    
    
    @FXML
    ImageView imageId;
    
    @FXML
    private void handleButtonAction(ActionEvent event) throws MalformedURLException {
        
        /*
        //Using image path from pc
        String imgAddress = "C:\\Users\\Ashraful\\Desktop\\JavaFX\\onCLickImageLoader\\src\\onclickimageloader\\car.jpg";
        String tmpAddress;
        File file2 = new File(imgAddress);
        tmpAddress = file2.toURI().toURL().toExternalForm();
        Image image = new Image(tmpAddress);
        imageId.setImage(image);
        System.out.println(tmpAddress);
        */
        
        /*
        //Using External link
        String imageAddress2="https://i.ebayimg.com/images/g/lAgAAOSwWAhcFLHg/s-l300.jpg";
        Image image2 = new Image(imageAddress2);
        imageId.setImage(image);
        System.out.println(imageAddress2);
        */
        
        //----------------------------------------------------------------
        
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(null);
        
        
        String imgAddress3;
        if (file != null) {
            try {
                imgAddress3 = file.toURI().toURL().toExternalForm();
                Image image = new Image(imgAddress3);
                imageId.setImage(image);
                
                System.out.println(imgAddress3);
            } catch (MalformedURLException ex) {
                throw new IllegalStateException(ex);
            }
        }
        
        
    }
    


    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
