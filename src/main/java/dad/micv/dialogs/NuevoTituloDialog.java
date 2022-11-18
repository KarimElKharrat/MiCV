package dad.micv.dialogs;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import dad.micv.MiCVApp;
import dad.micv.model.Titulo;
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

public class NuevoTituloDialog extends Dialog<Titulo> implements Initializable {

	// model
	 
	private StringProperty denominacion = new SimpleStringProperty();
	private StringProperty organizador = new SimpleStringProperty();
	private ObjectProperty<LocalDate> desde = new SimpleObjectProperty<>();
	private ObjectProperty<LocalDate> hasta = new SimpleObjectProperty<>();
 
	// view
 
	@FXML
    private TextField denominacionText;

    @FXML
    private DatePicker desdeDate;
    @FXML
    private DatePicker hastaDate;

    @FXML
    private TextField organizadorText;

    @FXML
    private BorderPane view;
		
	public NuevoTituloDialog() {
		super();
		try { 
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/NuevoTituloView.fxml"));
			loader.setController(this);
			loader.load();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}		
	}
	 
	private Titulo onResultConverter(ButtonType button) {
		if (button.getButtonData() == ButtonData.OK_DONE) {
			Titulo titulo = new Titulo();
			titulo.setDenominacion(denominacion.get());
			titulo.setDesde(desde.get());
			titulo.setHasta(hasta.get());
			titulo.setOrganizador(organizador.get());
			return titulo;
		}		
		return null;
	}
 
	@Override
	public void initialize(URL location, ResourceBundle resources) {
 
		// init dialog
 
		ButtonType addButtonType = new ButtonType("Añadir", ButtonData.OK_DONE);
 
		setTitle("Nuevo título");
		setHeaderText("Introduzca el nuevo título.");
		initOwner(MiCVApp.primaryStage);
		getDialogPane().setContent(view);
		getDialogPane().getButtonTypes().setAll(addButtonType, ButtonType.CANCEL);		
 
		setResultConverter(this::onResultConverter);
 
		// bindings
 
		denominacion.bind(denominacionText.textProperty());
		organizador.bind(organizadorText.textProperty());
		desde.bind(desdeDate.valueProperty());
		hasta.bind(hastaDate.valueProperty());
 
		// disable add button
 
		Button addButton = (Button) getDialogPane().lookupButton(addButtonType);
		addButton.disableProperty().bind(denominacion.isEmpty().or(organizador.isEmpty()).or(desde.isNull()).or(hasta.isNull()));
 
	}

}
