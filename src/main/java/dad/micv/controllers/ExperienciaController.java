package dad.micv.controllers;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;

import dad.micv.MiCVApp;
import dad.micv.model.Experiencia;
import javafx.application.Platform;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;

public class ExperienciaController implements Initializable {

	// model
	
	private ListProperty<Experiencia> experencias = new SimpleListProperty<>(FXCollections.observableArrayList());
	
	// controllers
	
	AñadirExperienciaController añadirExperienciaController;
	
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

    	añadirExperienciaController = new AñadirExperienciaController();

		Dialog<Experiencia> dialog = new Dialog<>();
		dialog.setTitle("Añadir experiencia");
		dialog.setHeaderText("Añade una nueva experiencia.");
		dialog.initOwner(MiCVApp.primaryStage);
		
		ButtonType loginButtonType = new ButtonType("Crear", ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

		dialog.getDialogPane().setContent(añadirExperienciaController.getView());

		Node loginButton = dialog.getDialogPane().lookupButton(loginButtonType);
		loginButton.setDisable(true);
		
		añadirExperienciaController.getDenominacionText().textProperty().addListener((o, ov, nv) -> {
		    loginButton.setDisable(disableAñadirButton());
		});
		añadirExperienciaController.getEmpleadorText().textProperty().addListener((o, ov, nv) -> {
			loginButton.setDisable(disableAñadirButton());
		});
		añadirExperienciaController.getDesdeDate().valueProperty().addListener((o, ov, nv) -> {
			loginButton.setDisable(disableAñadirButton());
		});
		añadirExperienciaController.getHastaDate().valueProperty().addListener((o, ov, nv) -> {
			loginButton.setDisable(disableAñadirButton());
		});
		
		Platform.runLater(() -> añadirExperienciaController.getDenominacionText());

		dialog.setResultConverter(dialogButton -> {
		    if (dialogButton == loginButtonType) {
		        return new Experiencia(
	        		añadirExperienciaController.getDesdeDate().getValue(), 
	        		añadirExperienciaController.getHastaDate().getValue(),
	        		añadirExperienciaController.getDenominacionText().getText(),
	        		añadirExperienciaController.getEmpleadorText().getText()
        		);
		    }
		    return null;
		});

		Optional<Experiencia> result = dialog.showAndWait();
		if(result.isPresent() && !result.get().getDenominacion().isBlank() && 
				!result.get().getEmpleador().isBlank() && 
				!(result.get().getDesde() == null) && 
				!(result.get().getHasta() == null) && 
				!experencias.contains(result.get())) {
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
    
    private boolean disableAñadirButton() {
    	return añadirExperienciaController.getDenominacionText().getText().trim().isEmpty() || 
			añadirExperienciaController.getEmpleadorText().getText().trim().isEmpty() ||
			(añadirExperienciaController.getDesdeDate().getValue() == null) ||
			(añadirExperienciaController.getHastaDate().getValue() == null);
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
