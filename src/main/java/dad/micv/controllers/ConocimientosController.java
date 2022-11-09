package dad.micv.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import dad.micv.model.Conocimiento;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;

public class ConocimientosController implements Initializable {

	// view
	
	@FXML
    private TableView<Conocimiento> conocimientosTable;

    @FXML
    private BorderPane view;
	
	public ConocimientosController() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ConocimientosView.fxml"));
			loader.setController(this);
			loader.load();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}
	
	@FXML
	void onAñadirConocimientoAction(ActionEvent event) {
		
	}
	
	@FXML
	void onAñadirIdiomaAction(ActionEvent event) {
		
	}
	
	@FXML
	void onEliminarAction(ActionEvent event) {
		
	}
	
	public BorderPane getView() {
		return view;
	}

}
