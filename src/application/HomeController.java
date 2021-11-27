package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import com.jfoenix.controls.JFXButton;

import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class HomeController implements Initializable{
	
	@FXML
	private JFXButton logout;
	@FXML
	private JFXButton home;
    @FXML
    private AnchorPane ancor;
    @FXML
    private StackPane stackPane;
    @FXML
    private Text well;
    
    private double x = 0;
    private double y = 0;
    AnchorPane homePage;
    String theUser = loginController.getInstance().getName();
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		createPage(homePage,"HomePage.fxml");
		well.setText("wellcome : "+ theUser);
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
	
	private void createPage(AnchorPane home, String loc) {
		try {
			home = FXMLLoader.load(getClass().getResource(loc));
			setNode(home);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setNode(Node node) {
		ancor.getChildren().clear();
		ancor.getChildren().add(node);
		
		FadeTransition transition = new FadeTransition();
		transition.setNode(node);
		transition.setDuration(Duration.millis(2000));
		transition.setFromValue(0.0);
		transition.setToValue(1.0);
		transition.setAutoReverse(false);
		transition.setCycleCount(1);
		transition.play();
	}
	@FXML
	public void homeAction(ActionEvent event) {
		createPage(homePage, "HomePage.fxml");

    }

	public void logoutAction(ActionEvent event) {
		int log = JOptionPane.showConfirmDialog(null,
				theUser+",do you want to logout?", "Confirm logout", JOptionPane.YES_NO_OPTION);
		if(log == JOptionPane.YES_OPTION) {
			logout.getScene().getWindow().hide();
			Visible("login.fxml");	
		}
	}
	private void Visible(String fxml) {
		Stage stage = new Stage();
		try {
			Parent root = FXMLLoader.load(getClass().getResource(fxml));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			stage.setScene(scene);
			scene.setFill(Color.TRANSPARENT);
			stage.initStyle(StageStyle.TRANSPARENT);
			stage.setResizable(false);
			stage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
