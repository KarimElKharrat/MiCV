package dad.micv.dialogs;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import dad.micv.MiCVApp;
import dad.micv.model.Telefono;
import dad.micv.model.TipoTelefono;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
 
public class NuevoTelefonoDialog extends Dialog<Telefono> implements Initializable {
 
	// model
 
	private StringProperty numero = new SimpleStringProperty();
	private ObjectProperty<TipoTelefono> tipo = new SimpleObjectProperty<>();
 
	// view
 
    @FXML
    private TextField numeroText;
 
    @FXML
    private ComboBox<TipoTelefono> tipoCombo;
 
    @FXML
    private GridPane view;
 
	public NuevoTelefonoDialog() {
		super();
		try { 
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/NuevoTelefonoView.fxml"));
			loader.setController(this);
			loader.load();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}		
	}
 
	private Telefono onResultConverter(ButtonType button) {
		if (button.getButtonData() == ButtonData.OK_DONE) {
			Telefono telefono = new Telefono();
			telefono.setTelefono(numero.get());
			telefono.setTipo(tipo.get());
			return telefono;
		}		
		return null;
	}
 
	@Override
	public void initialize(URL location, ResourceBundle resources) {
 
		// init dialog
 
		ButtonType addButtonType = new ButtonType("Añadir", ButtonData.OK_DONE);
 
		setTitle("Nuevo teléfono");
		setHeaderText("Introduzca el nuevo número de teléfono.");
		initOwner(MiCVApp.primaryStage);
		getDialogPane().setContent(view);
		getDialogPane().getButtonTypes().setAll(addButtonType, ButtonType.CANCEL);		
 
		setResultConverter(this::onResultConverter);
 
		// load combo
 
		tipoCombo.getItems().setAll(TipoTelefono.values());
 
		// bindings
 
		numero.bind(numeroText.textProperty());
		tipo.bind(tipoCombo.getSelectionModel().selectedItemProperty());
 
		// disable add button
 
		Button addButton = (Button) getDialogPane().lookupButton(addButtonType);
		addButton.disableProperty().bind(numero.isEmpty().or(tipo.isNull()));
 
	}
 
}