package application;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;


import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXProgressBar;
import com.jfoenix.controls.JFXTextField;

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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class loginController implements Initializable{
    
	@FXML
	private JFXButton login;
	@FXML
    private JFXTextField user;
    @FXML
    private JFXPasswordField pass;
    @FXML
    private JFXCheckBox remem;
    @FXML
    private JFXProgressBar progress;
    @FXML
    private Label msg;
    
    private double x = 0;
    private double y = 0;
    private static loginController instance;
    static int id = 0;
    
    public loginController() {
		instance = this;
	}
    public static loginController getInstance() {
    	return instance;
    }
    public String getName() {
    	return user.getText();
    }
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Connection connection = DBConnection.connected();
		Statement statement = null;
		try {
			statement = connection.createStatement();
			statement.executeUpdate("create table carUser(id number,name varchar2(30),password varchar2(20),gender varchar2(15),location varchar2(30))");
			System.out.println("Table is Created");
		} catch (SQLException e) {
			try {
				ResultSet set = statement.executeQuery("select * from carUser");
				while(set.next()) {
					id++;
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			System.out.println("Table is already exist");
		}finally {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
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
    public void login_login(ActionEvent event) {
    	progress.setVisible(true);
    	Timeline transition = new Timeline();
    	KeyValue value1 = new KeyValue(progress.progressProperty(), 0.01);
    	KeyFrame frame1 = new KeyFrame(Duration.millis(500), value1);
    	KeyValue value2 = new KeyValue(progress.progressProperty(), 1);
    	KeyFrame frame2 = new KeyFrame(Duration.seconds(3), value2);
    	
    	transition.getKeyFrames().addAll(frame1,frame2);
    	transition.setOnFinished(ev -> {
    		Connection connection = DBConnection.connected();
    		String sql = "select * from carUser where name=? and password=?";
    		try {
				PreparedStatement statement = connection.prepareStatement(sql);
				statement.setString(1, user.getText());
				statement.setString(2, pass.getText());
				ResultSet set = statement.executeQuery();
				int count = 0;
				while (set.next()) {
					count++;
				}
				if(count == 1) {
					login.getScene().getWindow().hide();
					Visible("Home.fxml");
				}else {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setHeaderText(null);
					alert.setContentText("UserName or Password is not Correct");
					alert.show();
					progress.setVisible(false);
				}
				user.clear();
				pass.clear();
			} catch (SQLException e) {
				e.printStackTrace();
			}finally {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
    	});
    	transition.play();
    }
    @FXML
    public void login_signUp(ActionEvent event) {
    	login.getScene().getWindow().hide();
    	Visible("Signup.fxml");
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
			stage.setResizable(false);
			stage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
