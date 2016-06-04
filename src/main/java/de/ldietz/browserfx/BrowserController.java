package de.ldietz.browserfx;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebHistory;
import javafx.scene.web.WebHistory.Entry;
import javafx.scene.web.WebView;
import org.w3c.dom.Document;

public class BrowserController {
	
	@FXML private Button testButton;
	@FXML private WebView webview;
	@FXML private Label labelAdress;
	@FXML private TextField textfieldUrl;
	@FXML private Button buttonSearch;
	@FXML private Label labelLoading;
	
	@FXML private MenuItem menuItemClose;
	@FXML private MenuItem menuItemShowHistory;
	@FXML private MenuItem menuItemDelete;
	@FXML private MenuItem menuItemAbout;

	@FXML private Button buttonBack;
	@FXML private Button buttonForward;

	@FXML private ListView<Entry> listviewHistory;

	private WebEngine engine;
	
	@FXML
	private void initialize() {
		engine = webview.getEngine();
		engine.load("http://www.google.de");
		engine.documentProperty().addListener((v, o, n) -> onDocumentChanged(n));
		
		WebHistory history = engine.getHistory();
		history.setMaxSize(10);
		history.currentIndexProperty().addListener((v, o, n) -> listviewHistory.getSelectionModel().select(n.intValue()));
		
		EventHandler<ActionEvent> urlLoader = event -> loadUrl();
		textfieldUrl.setOnAction(urlLoader);
		
		menuItemClose.setOnAction(event -> close());
		menuItemShowHistory.setOnAction(event -> showHistory());
		
		buttonSearch.setOnAction(urlLoader);
		buttonBack.setOnAction(event -> back());
		buttonForward.setOnAction(event -> forward());
		buttonBack.disableProperty().bind(history.currentIndexProperty().greaterThan(0).not());
		buttonForward.disableProperty().bind(history.currentIndexProperty().isEqualTo(Bindings.size(history.getEntries()).subtract(1)));
		
		listviewHistory.setItems(history.getEntries());
		listviewHistory.managedProperty().bind(listviewHistory.visibleProperty());
		
	}
	
	private void showHistory() {
		listviewHistory.setVisible(!listviewHistory.isVisible());
	}

	private void forward() {
		engine.getHistory().go(1);
	}

	private void back() {
		engine.getHistory().go(-1);
	}

	private void loadUrl() {
		engine.load(textfieldUrl.getText());
		labelLoading.setText("Such nach: " + textfieldUrl.getText());
	}
	
	private void close() {
		System.exit(0);
	}

	private void onDocumentChanged(Document document) {
		if (document != null) {
			labelLoading.setText("Geladen: " + document.getDocumentURI());
		}
	}

}
