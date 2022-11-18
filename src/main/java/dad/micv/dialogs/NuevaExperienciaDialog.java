package dad.micv.dialogs;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import dad.micv.MiCVApp;
import dad.micv.model.Experiencia;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

public class NuevaExperienciaDialog extends Dialog<Experiencia> implements Initializable {

	// model
	 
	private StringProperty denominacion = new SimpleStringProperty();
	private StringProperty empleador = new SimpleStringProperty();
	private ObjectProperty<LocalDate> desde = new SimpleObjectProperty<>();
	private ObjectProperty<LocalDate> hasta = new SimpleObjectProperty<>();
 
	// view
 
	@FXML
    private TextField denominacionText;
	@FXML
	private TextField empleadorText;

    @FXML
    private DatePicker desdeDate;
    @FXML
    private DatePicker hastaDate;

    @FXML
    private BorderPane view;
		
	public NuevaExperienciaDialog() {
		super();
		try { 
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/NuevaExperienciaView.fxml"));
			loader.setController(this);
			loader.load();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}		
	}
	 
	private Experiencia onResultConverter(ButtonType button) {
		if (button.getButtonData() == ButtonData.OK_DONE) {
			Experiencia experiencia = new Experiencia();
			experiencia.setDenominacion(denominacion.get());
			experiencia.setDesde(desde.get());
			experiencia.setHasta(hasta.get());
			experiencia.setEmpleador(empleador.get());
			return experiencia;
		}		
		return null;
	}
 
	@Override
	public void initialize(URL location, ResourceBundle resources) {
 
		// init dialog
 
		ButtonType addButtonType = new ButtonType("Añadir", ButtonData.OK_DONE);
 
		setTitle("Nueva experiencia");
		setHeaderText("Introduzca la nueva experiencia.");
		initOwner(MiCVApp.primaryStage);
		getDialogPane().setContent(view);
		getDialogPane().getButtonTypes().setAll(addButtonType, ButtonType.CANCEL);		
 
		setResultConverter(this::onResultConverter);
 
		// bindings
 
		denominacion.bind(denominacionText.textProperty());
		empleador.bind(empleadorText.textProperty());
		desde.bind(desdeDate.valueProperty());
		hasta.bind(hastaDate.valueProperty());
 
		// disable add button
 
		Button addButton = (Button) getDialogPane().lookupButton(addButtonType);
		addButton.disableProperty().bind(denominacion.isEmpty().or(empleador.isEmpty()).or(desde.isNull()).or(hasta.isNull()));
 
	}

}
