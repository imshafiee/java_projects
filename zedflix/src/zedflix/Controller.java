/*
 * CONTROLLER of Signup, Login page
 */
package zedflix;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.stage.Window;

/**
 *
 * @author Ashraful
 */
public class Controller implements Initializable {
    
    @FXML private TextField s_name;
    @FXML private TextField s_email;
    @FXML private PasswordField s_password;
    @FXML private Label s_message;
    
    @FXML private Button l_signupBtn;
    @FXML private Button l_loginBtn;
    @FXML private Button s_signupBtn;
    @FXML private Button s_loginBtn;
    
    // MVC Model Class
    Model mObj = new Model();
    SQL dbObj = new SQL();
    
    public Stage stg = null;
    //================================================================
    // 1. SIGNUP Page
    //================================================================
    @FXML
    private void onSignupPress(ActionEvent event) {
        
        String name, email, pass, message=" ", role="subscriber";
        String afterSignupMsg = "";
        name = s_name.getText();
        email = s_email.getText().toLowerCase();
        pass = s_password.getText();

        if(name.length() > 3 && email.length() > 7 && pass.length()>3) {
            boolean isEmailOkey = mObj.validateEmail(email);
           
            //System.out.println(isEmailOkey);
            
            if(!isEmailOkey) {
                message = "Invalid email format.";
            }
            else {
                String[] tmpMail={"EMAIL"};
                String[] tmpVal = {email};
                boolean emailExist = mObj.checkValueFromDb("USERS", tmpMail ,tmpVal );
                if(emailExist) {
                    message = "User already exists.";
                }
                else {
                    // insert now
                    String[] insertAttr = {"NAME", "EMAIL", "PASSWORD", "ROLE"};
                    String[] insertValues = {name, email, pass, role};
                    afterSignupMsg = mObj.insertData("USERS", insertAttr, insertValues);
                    
                    message = afterSignupMsg;
                }
            }
            
        }
        else {
            message = "The informations are short in length.";
        }
        
        s_message.setText(message);

    }
    
    @FXML
    private void showSignupPage(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("signup.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));  
            stage.show();
            
            //((Node)(event.getSource())).getScene().getWindow().hide();
            
            Stage stage2 = (Stage)l_signupBtn.getScene().getWindow();
            stage2.close();
            
        } catch(IOException e) {
            System.out.println(e.getMessage());
        }
    }
    
    
    //================================================================
    // 1.2 LOGIN Page
    //================================================================
    @FXML
    public void showLoginPage(ActionEvent event) {               
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("login.fxml"));
            Parent root2 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root2));  
            stage.show();
            
            //((Node)(event.getSource())).getScene().getWindow().hide();
            
            Stage stage2 = (Stage)s_loginBtn.getScene().getWindow();
            stage2.close();
            
            
        } catch(IOException e) {
            System.out.println(e.getMessage());
        }
    }
    
    @FXML private TextField l_email;
    @FXML private TextField l_password;
    @FXML private Label l_message;
    @FXML
    public void onLoginPress(ActionEvent event) throws Exception {
                
        String email, pass, message=" ";
        email = l_email.getText().toLowerCase();
        pass = l_password.getText();
        
        boolean isEmailOkey = mObj.validateEmail(email);   
        /*
        if(!isEmailOkey) {
            message = "Invalid email format.";
        }
        System.out.println(isEmailOkey);
        */
        
        if(email.length() > 7 && pass.length()>2 && isEmailOkey) {
            
            String[] tmpMail={"EMAIL", "PASSWORD"};
            String[] tmpVal = {email, pass};
            boolean emailExist = mObj.checkValueFromDb("USERS", tmpMail, tmpVal );
            if(emailExist) {
                // Move to homepage
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("homepage.fxml"));
                    Parent root2 = (Parent) fxmlLoader.load();
                    Stage stage = new Stage();
                    stage.setScene(new Scene(root2));  
                    stage.show();

                    //((Node)(event.getSource())).getScene().getWindow().hide();

                    Stage stage2 = (Stage)l_loginBtn.getScene().getWindow();
                    stage2.close();

                } catch(IOException e) {
                    System.out.println(e.getMessage());
                }
            } 
            else if(!emailExist) {
                
                
                
                message = "Invalid login information.";
            }
            
        }
        else {
            message = "Format isn't right.";
        }
        
        l_message.setText(message);
    }

    @FXML
    public void showHomepage(ActionEvent event) {               
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("homepage.fxml"));
            Parent root2 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root2));  
            stage.show();

            //((Node)(event.getSource())).getScene().getWindow().hide();

            Stage stage2 = (Stage)l_loginBtn.getScene().getWindow();
            stage2.close();


        } catch(IOException e) {
            System.out.println(e.getMessage());
        }
    }
    
    //================================================================
    // ADMIN LOGGER
    //================================================================
    @FXML private Button l_adminLogin;
    
    @FXML
    public void adminLogin(ActionEvent event) {
        
        String email, pass, message=" ";
        email = l_email.getText().toLowerCase();
        pass = l_password.getText();
        
        boolean isEmailOkey = mObj.validateEmail(email);   
        /*
        if(!isEmailOkey) {
            message = "Invalid email format.";
        }
        System.out.println(isEmailOkey);
        */
        
        if(email.length() > 7 && pass.length()>2 && isEmailOkey) {
            
            String[] tmpMail={"EMAIL", "PASSWORD", "ROLE"};
            String[] tmpVal = {email, pass, "admin"};
            boolean emailExist = mObj.checkValueFromDb("USERS", tmpMail, tmpVal );
            if(emailExist) {
                // Move to homepage
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ADMIN_AREA.fxml"));
                    Parent root2 = (Parent) fxmlLoader.load();
                    Stage stage = new Stage();
                    stage.setScene(new Scene(root2));  
                    stage.show();

                    //((Node)(event.getSource())).getScene().getWindow().hide();

                    Stage stage2 = (Stage)l_loginBtn.getScene().getWindow();
                    stage2.close();

                } catch(IOException e) {
                    System.out.println(e.getMessage());
                }
            } 
            else if(!emailExist) {
                message = "Invalid login information.";
            }
            
        }
        else {
            message = "Format isn't right.";
        }
        
        l_message.setText(message);
        
    }
    
    //================================================================
    // INITIALIZER
    //================================================================
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        dbObj.checkDbConnection();
    }
    
}
