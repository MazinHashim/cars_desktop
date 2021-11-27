package application;

import java.io.IOException;

import com.jfoenix.controls.JFXButton;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class HomePageController {

    @FXML
    private AnchorPane ancorPane;
    @FXML
    private JFXButton maruti;
    @FXML
    private JFXButton hyundai;
    @FXML
    private JFXButton tata;
    @FXML
    private JFXButton chevr;

    AnchorPane anc;
    @FXML
    public void carsAction(ActionEvent event) {
    	if(event.getSource() == maruti) {
    		loadFXML(anc,"MARUTI.fxml");
    	}
    	if(event.getSource() == hyundai) {
    		loadFXML(anc,"HYUNDAI.fxml");
    	}
    	if(event.getSource() == tata) {
    		loadFXML(anc,"TATA.fxml");
    	}
    	if(event.getSource() == chevr) {
    		loadFXML(anc,"CHEVROLET.fxml");
    	}
    }
    public void setNode(Node node) {
		ancorPane.getChildren().clear();
		ancorPane.getChildren().add(node);
		
		FadeTransition transition = new FadeTransition();
		transition.setNode(node);
		transition.setDuration(Duration.millis(2000));
		transition.setFromValue(0.0);
		transition.setToValue(1.0);
		transition.setAutoReverse(false);
		transition.setCycleCount(1);
		transition.play();
	}
	private void loadFXML(AnchorPane child,String fxml) {
		try {
			child = FXMLLoader.load(getClass().getResource(fxml));
			setNode(child);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
