package dad.micv.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import dad.micv.model.Nivel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class AñadirConocimientoController implements Initializable {

	@FXML
    private Label certificacionLabel;
    @FXML
    private TextField certificacionText;
    
    @FXML
    private TextField denominacionText;

    @FXML
    private ComboBox<Nivel> nivelCombo;

    @FXML
    private GridPane view;
    
	public AñadirConocimientoController() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AñadirConocimientoView.fxml"));
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
	void onResetNivelAction(ActionEvent event) {
		nivelCombo.getSelectionModel().clearSelection();
		nivelCombo.setValue(null);
	}

	public Label getCertificacionLabel() {
		return certificacionLabel;
	}

	public TextField getCertificacionText() {
		return certificacionText;
	}

	public TextField getDenominacionText() {
		return denominacionText;
	}

	public ComboBox<Nivel> getNivelCombo() {
		return nivelCombo;
	}

	public GridPane getView() {
		return view;
	}

}
