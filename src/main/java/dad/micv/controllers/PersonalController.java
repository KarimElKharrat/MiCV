package dad.micv.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import dad.micv.MiCVApp;
import dad.micv.model.Nacionalidad;
import dad.micv.model.Personal;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class PersonalController implements Initializable {

	// model
	
	private Personal personal = new Personal();
	
	private ListProperty<Nacionalidad> nacionalidadesTotales = new SimpleListProperty<>(FXCollections.observableArrayList());
	private ListProperty<String> paises = new SimpleListProperty<>(FXCollections.observableArrayList());
	
	// view
	
	@FXML
    private TextField dniText;
	@FXML
	private TextField nombreText;
	@FXML
    private TextField apellidosText;
	@FXML
    private TextArea direccionText;
    @FXML
    private TextField codPostalText;
    @FXML
    private TextField localidadText;
    
    @FXML
    private DatePicker nacimientoDate;    
    
    @FXML
    private ComboBox<String> paisesCombo;

    @FXML
    private ListView<Nacionalidad> nacionalidadesList;
    
    @FXML
    private GridPane view;
	
	public PersonalController() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/PersonalView.fxml"));
			loader.setController(this);
			loader.load();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		// bindings
		
		personal.identificacionProperty().bind(dniText.textProperty());
		personal.nombreProperty().bind(nombreText.textProperty());
		personal.apellidosProperty().bind(apellidosText.textProperty());
		personal.fechaNacimientoProperty().bind(nacimientoDate.valueProperty());
		personal.direccionProperty().bind(direccionText.textProperty());
		personal.codigoPostalProperty().bind(codPostalText.textProperty());
		personal.localidadProperty().bind(localidadText.textProperty());
		personal.paisProperty().bind(paisesCombo.getSelectionModel().selectedItemProperty());
		nacionalidadesList.itemsProperty().bind(personal.nacionalidadesProperty());
		
		paisesCombo.itemsProperty().bind(paises);
		
	}
	
	@FXML
	void onAñadirAction(ActionEvent event) {
		
		ChoiceDialog<Nacionalidad> dialog = new ChoiceDialog<>(); //TODO meter las nacionalidades
		dialog.initOwner(MiCVApp.primaryStage);
		dialog.setTitle("Nueva nacionalidad");
		dialog.setHeaderText("Añadir nacionalidad");
		dialog.getItems().addAll(nacionalidadesTotales);
		
		Optional<Nacionalidad> nuevaNacionalidad = dialog.showAndWait();
		if(nuevaNacionalidad.isPresent() && !nuevaNacionalidad.get().toString().isBlank() && !personal.getNacionalidades().contains(nuevaNacionalidad.get())) {
			personal.getNacionalidades().add(nuevaNacionalidad.get());
		}
		
	}
	
	@FXML
	void onQuitarAction(ActionEvent event) {
		personal.getNacionalidades().remove(nacionalidadesList.getSelectionModel().getSelectedItem());
	}
	
	public final ListProperty<Nacionalidad> nacionalidadesProperty() {
		return this.nacionalidadesTotales;
	}
	
	public final ListProperty<String> paisesProperty() {
		return this.paises;
	}
	
	public GridPane getView() {
		return view;
	}

}
