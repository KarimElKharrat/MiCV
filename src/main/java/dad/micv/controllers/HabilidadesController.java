package dad.micv.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import dad.micv.MiCVApp;
import dad.micv.dialogs.NuevoConocimientoDialog;
import dad.micv.dialogs.NuevoIdiomaDialog;
import dad.micv.model.Conocimiento;
import dad.micv.model.Idioma;
import dad.micv.model.Nivel;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;

public class HabilidadesController implements Initializable {

	// model

	private ListProperty<Conocimiento> habilidades = new SimpleListProperty<>(FXCollections.observableArrayList());

	// view

	@FXML
	private Button eliminarButton;

	@FXML
	private TableColumn<Conocimiento, String> denominacionColumn;
	@FXML
	private TableColumn<Conocimiento, Nivel> nivelColumn;

	@FXML
	private TableView<Conocimiento> conocimientosTable;

	@FXML
	private BorderPane view;

	public HabilidadesController() {
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

		// bindings

		eliminarButton.disableProperty().bind(conocimientosTable.getSelectionModel().selectedItemProperty().isNull());

		// listeners
		
		habilidades.addListener(this::onHabilidadesChanged);

	}

	private void onHabilidadesChanged(ObservableValue<? extends ObservableList<Conocimiento>> o,
			ObservableList<Conocimiento> ov, ObservableList<Conocimiento> nv) {
		
		if(ov != null) {
			
			conocimientosTable.itemsProperty().unbind();
			
		}
		
		if(nv != null) {
			
			conocimientosTable.itemsProperty().bind(habilidades);

			denominacionColumn.setCellValueFactory(v -> v.getValue().denominacionProperty());
			nivelColumn.setCellValueFactory(v -> v.getValue().nivelProperty());
			
		}
		
	}

	@FXML
	void onAñadirConocimientoAction(ActionEvent event) {

		NuevoConocimientoDialog dialog = new NuevoConocimientoDialog();

		Optional<Conocimiento> result = dialog.showAndWait();
		if (result.isPresent() && !habilidades.contains(result.get())) {
			habilidades.add(result.get());
		}

	}

	@FXML
	void onAñadirIdiomaAction(ActionEvent event) {
		
		NuevoIdiomaDialog dialog = new NuevoIdiomaDialog();

		Optional<Idioma> result = dialog.showAndWait();
		if (result.isPresent() && !habilidades.contains(result.get())) {
			habilidades.add(result.get());
		}
	}

	@FXML
	void onEliminarAction(ActionEvent event) {

		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Eliminar experiencia");
		alert.setHeaderText("Está a punto de eliminar una experiencia");
		alert.setContentText("¿Desea continuar?");
		alert.initOwner(MiCVApp.primaryStage);

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK) {
			habilidades.remove(conocimientosTable.getSelectionModel().getSelectedItem());
		}

	}

	public BorderPane getView() {
		return view;
	}

	public final ListProperty<Conocimiento> habilidadesProperty() {
		return this.habilidades;
	}

	public final ObservableList<Conocimiento> getHabilidades() {
		return this.habilidadesProperty().get();
	}

	public final void setHabilidades(final ObservableList<Conocimiento> habilidades) {
		this.habilidadesProperty().set(habilidades);
	}
	
}
