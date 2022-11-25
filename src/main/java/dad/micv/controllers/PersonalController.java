package dad.micv.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import dad.micv.MiCVApp;
import dad.micv.model.Nacionalidad;
import dad.micv.model.Personal;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class PersonalController implements Initializable {

	// model

	private ListProperty<Nacionalidad> nacionalidadesTotales = new SimpleListProperty<>(FXCollections.observableArrayList());
	private ListProperty<String> paises = new SimpleListProperty<>(FXCollections.observableArrayList());
	private ObjectProperty<Personal> personal = new SimpleObjectProperty<>();

	// view

	@FXML
	private Button quitarButton;

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

		paisesCombo.itemsProperty().bind(paises);

		quitarButton.disableProperty().bind(nacionalidadesList.getSelectionModel().selectedItemProperty().isNull());

		// listeners

		personal.addListener(this::onPersonalChanged);

	}

	private void onPersonalChanged(ObservableValue<? extends Personal> o, Personal ov, Personal nv) {
		
		if (ov != null) {

			dniText.textProperty().unbindBidirectional(ov.identificacionProperty());
			nombreText.textProperty().unbindBidirectional(ov.nombreProperty());
			apellidosText.textProperty().unbindBidirectional(ov.apellidosProperty());
			nacimientoDate.valueProperty().unbindBidirectional(ov.fechaNacimientoProperty());
			direccionText.textProperty().unbindBidirectional(ov.direccionProperty());
			codPostalText.textProperty().unbindBidirectional(ov.codigoPostalProperty());
			localidadText.textProperty().unbindBidirectional(ov.localidadProperty());

			ov.paisProperty().unbind();
			nacionalidadesList.itemsProperty().unbind();

		}

		if (nv != null) {

			dniText.textProperty().bindBidirectional(nv.identificacionProperty());
			nombreText.textProperty().bindBidirectional(nv.nombreProperty());
			apellidosText.textProperty().bindBidirectional(nv.apellidosProperty());
			nacimientoDate.valueProperty().bindBidirectional(nv.fechaNacimientoProperty());
			direccionText.textProperty().bindBidirectional(nv.direccionProperty());
			codPostalText.textProperty().bindBidirectional(nv.codigoPostalProperty());
			localidadText.textProperty().bindBidirectional(nv.localidadProperty());

			paisesCombo.getSelectionModel().select(nv.getPais());
			nv.paisProperty().bind(paisesCombo.getSelectionModel().selectedItemProperty());

			nacionalidadesList.itemsProperty().bind(nv.nacionalidadesProperty());

		}

	}

	@FXML
	void onAñadirAction(ActionEvent event) {

		ChoiceDialog<Nacionalidad> dialog = new ChoiceDialog<>(nacionalidadesTotales.get(0), nacionalidadesTotales);
		dialog.initOwner(MiCVApp.primaryStage);
		dialog.setTitle("Nueva nacionalidad");
		dialog.setHeaderText("Añadir nacionalidad");

		Optional<Nacionalidad> nuevaNacionalidad = dialog.showAndWait();
		if (nuevaNacionalidad.isPresent() && !nuevaNacionalidad.get().toString().isBlank()
				&& !getPersonal().getNacionalidades().contains(nuevaNacionalidad.get())) {
			getPersonal().getNacionalidades().add(nuevaNacionalidad.get());
		}

	}

	@FXML
	void onQuitarAction(ActionEvent event) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Eliminar nacionalidad");
		alert.setHeaderText("Está a punto de eliminar una nacionalidad");
		alert.setContentText("¿Quiere continuar?");
		alert.initOwner(MiCVApp.primaryStage);

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK) {
			getPersonal().getNacionalidades().remove(nacionalidadesList.getSelectionModel().getSelectedItem());
		}
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

	public final ObjectProperty<Personal> personalProperty() {
		return this.personal;
	}

	public final Personal getPersonal() {
		return this.personalProperty().get();
	}

	public final void setPersonal(final Personal personal) {
		this.personalProperty().set(personal);
	}

}
