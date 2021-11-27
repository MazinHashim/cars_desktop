package application;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXProgressBar;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;

import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class SignupController implements Initializable{
    @FXML
    private JFXTextField userName;
    @FXML
    private JFXButton signup;
    @FXML
    private JFXPasswordField password;
    @FXML
    private JFXRadioButton male;
    @FXML
    private JFXRadioButton female;
    @FXML
    private JFXTextField locat;
    @FXML
    private JFXProgressBar prog;
    @FXML
    private Label mass;
    
    private double x = 0;
    private double y = 0;
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}
	
	private String getGender() {
		
		if(male.isSelected())
			return male.getText();
		if(female.isSelected()) 
			return female.getText();
		return "";
	}
	public void exitStage(ActionEvent e) {
		System.exit(0);
		Platform.exit();
	}
	public void dragged(MouseEvent e) {
		Node node = (Node) e.getSource();
		Stage stage = (Stage) node.getScene().getWindow();
		stage.setX(e.getScreenX()-x);
		stage.setY(e.getScreenY()-y);
	}
	public void pressed(MouseEvent e) {
		x = e.getSceneX();
		y = e.getSceneY();
				
	}
	@FXML
	public void signUp_signUp(ActionEvent event) {
    	prog.setVisible(true);
    	Timeline transition = new Timeline();
    	KeyValue value1 = new KeyValue(prog.progressProperty(), 0.01);
    	KeyFrame frame1 = new KeyFrame(Duration.millis(500), value1);
    	KeyValue value2 = new KeyValue(prog.progressProperty(), 1);
    	KeyFrame frame2 = new KeyFrame(Duration.seconds(3), value2);
    	
    	transition.getKeyFrames().addAll(frame1,frame2);
    	transition.setOnFinished(ev -> {
    		
    		loginController.id++;
    		Connection connection = DBConnection.connected();
    		String sql = "insert into carUser values(?,?,?,?,?)";
    		try {
				PreparedStatement statement = connection.prepareStatement(sql);
				statement.setInt(1, loginController.id);
				statement.setString(2, userName.getText());
				statement.setString(3, password.getText());
				statement.setString(4, getGender());
				statement.setString(5, locat.getText());
				statement.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
    		showMessage();
    		prog.setVisible(false);
    		userName.clear();
    		password.clear();
    		male.setSelected(false);
    		female.setSelected(false);
    		locat.clear();
    	});
    	transition.play();
    	
	}
	private void showMessage() {
		FadeTransition transition = new FadeTransition();
		transition.setNode(mass);
		transition.setDuration(Duration.millis(3000));
		transition.setFromValue(0.0);
		transition.setToValue(10.0);
		mass.setVisible(true);
		transition.play();		
	}

	@FXML
    public void signUp_login(ActionEvent event) {
		signup.getScene().getWindow().hide();
		Visible("login.fxml");
	}
	public void Visible(String fxml) {
		Stage stage = new Stage();
		try {
			Parent root = FXMLLoader.load(getClass().getResource(fxml));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			stage.initStyle(StageStyle.TRANSPARENT);
			scene.setFill(Color.TRANSPARENT);
			stage.setScene(scene);
			stage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
