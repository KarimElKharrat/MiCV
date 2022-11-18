package dad.micv.controllers;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;

import dad.micv.MiCVApp;
import dad.micv.dialogs.NuevaExperienciaDialog;
import dad.micv.model.Experiencia;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
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

public class ExperienciaController implements Initializable {

	// model
	
	private ListProperty<Experiencia> experencias = new SimpleListProperty<>(FXCollections.observableArrayList());
	
	// view
	
	@FXML
	private Button eliminarButton;
	
	@FXML
	private TableColumn<Experiencia, LocalDate> desdeColumn;
	@FXML
	private TableColumn<Experiencia, LocalDate> hastaColumn;
	@FXML
    private TableColumn<Experiencia, String> denominacionColumn;
    @FXML
    private TableColumn<Experiencia, String> empleadorColumn;
    
	@FXML
    private TableView<Experiencia> experienciaTable;

    @FXML
    private BorderPane view;
	
	public ExperienciaController() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ExperienciaView.fxml"));
			loader.setController(this);
			loader.load();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		// bindings
		
		experienciaTable.itemsProperty().bind(experencias);
		eliminarButton.disableProperty().bind(experienciaTable.getSelectionModel().selectedItemProperty().isNull());
		
		// cell value factories
		
		desdeColumn.setCellValueFactory(v -> v.getValue().desdeProperty());
		hastaColumn.setCellValueFactory(v -> v.getValue().desdeProperty());
		denominacionColumn.setCellValueFactory(v -> v.getValue().denominacionProperty());
		empleadorColumn.setCellValueFactory(v -> v.getValue().empleadorProperty());
		
	}

    @FXML
    void onAñadirAction(ActionEvent event) {
    	NuevaExperienciaDialog dialog = new NuevaExperienciaDialog();
    	
		Optional<Experiencia> result = dialog.showAndWait();
		if(result.isPresent() && !experencias.contains(result.get())) {
			experencias.add(result.get());
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
    	if (result.get() == ButtonType.OK){
    		experencias.remove(experienciaTable.getSelectionModel().getSelectedItem());
    	}
    }
    
    public void loadExperiencia(ObservableList<Experiencia> experiencias) {
    	experienciaTable.getItems().addAll(experiencias);
    }
    
    public ListProperty<Experiencia> getExperencias() {
		return experencias;
	}
    
    public BorderPane getView() {
		return view;
	}

}
