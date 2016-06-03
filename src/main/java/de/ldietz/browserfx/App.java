package de.ldietz.browserfx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
	
    public static void main(String[] args) {
    	launch(args);
    }

	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent browser = FXMLLoader.load(getClass().getResource("browser.fxml"));
		
		Scene scene = new Scene(browser);
		
		primaryStage.setScene(scene);
		primaryStage.setTitle("BrowserFX");
		primaryStage.show();
	}
    
}