package dad.micv.controllers;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;

import dad.micv.MiCVApp;
import dad.micv.dialogs.NuevoTituloDialog;
import dad.micv.model.Titulo;
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

public class FormacionController implements Initializable {

	// model
	
	private ListProperty<Titulo> formacion = new SimpleListProperty<>(FXCollections.observableArrayList());

	// view
	
	@FXML
    private Button eliminarButton;
	
	@FXML
	private TableColumn<Titulo, LocalDate> desdeColumn;
	@FXML
	private TableColumn<Titulo, LocalDate> hastaColumn;
	@FXML
    private TableColumn<Titulo, String> denominacionColumn;
    @FXML
    private TableColumn<Titulo, String> organizacionColumn;
	
	@FXML
    private TableView<Titulo> formacionTable;

    @FXML
    private BorderPane view;

	public FormacionController() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/FormacionView.fxml"));
			loader.setController(this);
			loader.load();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		// bindings
		
		eliminarButton.disableProperty().bind(formacionTable.getSelectionModel().selectedItemProperty().isNull());
		
		// listeners
		
		formacion.addListener(this::onFormacionChanged);
		
	}
	
	

	private void onFormacionChanged(ObservableValue<? extends ObservableList<Titulo>> o, ObservableList<Titulo> ov, ObservableList<Titulo> nv) {
		
		if(ov != null) {
			
			formacionTable.itemsProperty().unbind();
			
		}
		
		if(nv != null) {
			
			formacionTable.itemsProperty().bind(formacion);
			
			desdeColumn.setCellValueFactory(v -> v.getValue().desdeProperty());
			hastaColumn.setCellValueFactory(v -> v.getValue().desdeProperty());
			denominacionColumn.setCellValueFactory(v -> v.getValue().denominacionProperty());
			organizacionColumn.setCellValueFactory(v -> v.getValue().organizadorProperty());
			
		}
		
	}

	@FXML
    void onAñadirAction(ActionEvent event) {
		NuevoTituloDialog dialog = new NuevoTituloDialog();

		Optional<Titulo> result = dialog.showAndWait();
		if(result.isPresent() && !formacion.contains(result.get())) {
			formacion.add(result.get());
		}

    }

    @FXML
    void onEliminarAction(ActionEvent event) {
    	Alert alert = new Alert(AlertType.CONFIRMATION);
    	alert.setTitle("Eliminar título");
    	alert.setHeaderText("Está a punto de eliminar un título");
    	alert.setContentText("¿Desea continuar?");
    	alert.initOwner(MiCVApp.primaryStage);
    	
    	Optional<ButtonType> result = alert.showAndWait();
    	if (result.get() == ButtonType.OK){
    		formacion.remove(formacionTable.getSelectionModel().getSelectedItem());
    	}
    }
    
    public BorderPane getView() {
		return view;
	}

	public final ListProperty<Titulo> formacionProperty() {
		return this.formacion;
	}

	public final ObservableList<Titulo> getFormacion() {
		return this.formacionProperty().get();
	}

	public final void setFormacion(final ObservableList<Titulo> formacion) {
		this.formacionProperty().set(formacion);
	}
	
    
}
