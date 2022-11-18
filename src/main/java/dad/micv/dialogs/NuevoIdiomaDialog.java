package dad.micv.dialogs;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import dad.micv.MiCVApp;
import dad.micv.model.Idioma;
import dad.micv.model.Nivel;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class NuevoIdiomaDialog extends Dialog<Idioma> implements Initializable {

	// model
	 
	private StringProperty denominacion = new SimpleStringProperty();
	private StringProperty certificacion = new SimpleStringProperty();
	private ObjectProperty<Nivel> nivel = new SimpleObjectProperty<>();
 
	// view
 
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
		
	public NuevoIdiomaDialog() {
		super();
		try { 
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/NuevoConocimientoView.fxml"));
			loader.setController(this);
			loader.load();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}		
	}
	 
	private Idioma onResultConverter(ButtonType button) {
		if (button.getButtonData() == ButtonData.OK_DONE) {
			Idioma idioma = new Idioma();
			idioma.setDenominacion(denominacion.get());
			idioma.setNivel(nivel.get());
			idioma.setCertificacion(certificacion.get());
			return idioma;
		}		
		return null;
	}
 
	@Override
	public void initialize(URL location, ResourceBundle resources) {
 
		// init dialog
 
		ButtonType addButtonType = new ButtonType("Añadir", ButtonData.OK_DONE);
 
		setTitle("Nuevo conocimiento");
		setHeaderText("Introduzca el nuevo conocimiento.");
		initOwner(MiCVApp.primaryStage);
		getDialogPane().setContent(view);
		getDialogPane().getButtonTypes().setAll(addButtonType, ButtonType.CANCEL);		
 
		setResultConverter(this::onResultConverter);
 
		// load combo
		
		nivelCombo.getItems().setAll(Nivel.values());
		
		// bindings
 
		denominacion.bind(denominacionText.textProperty());
		certificacion.bind(certificacionText.textProperty());
		nivel.bind(nivelCombo.getSelectionModel().selectedItemProperty());
 
		// disable add button
 
		Button addButton = (Button) getDialogPane().lookupButton(addButtonType);
		addButton.disableProperty().bind(denominacion.isEmpty().or(nivel.isNull()).or(certificacion.isEmpty()));
 
	}
	
	@FXML
	void onResetNivelAction(ActionEvent event) {
		nivelCombo.getSelectionModel().clearSelection();
		nivelCombo.setValue(null);
	}

}
