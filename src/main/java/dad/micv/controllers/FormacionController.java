package dad.micv.controllers;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;

import dad.micv.MiCVApp;
import dad.micv.model.Titulo;
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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;

public class FormacionController implements Initializable {

	// model
	
	private ListProperty<Titulo> formacion = new SimpleListProperty<>(FXCollections.observableArrayList());

	// controllers
	
	private AñadirTituloController añadirTituloController;
	
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
		
//		formacionTable.itemsProperty().bind(MainController.cv.formacionProperty());
		formacionTable.itemsProperty().bind(formacion);
		eliminarButton.disableProperty().bind(formacionTable.getSelectionModel().selectedItemProperty().isNull());
		
		// cell value factories
		
		desdeColumn.setCellValueFactory(v -> v.getValue().desdeProperty());
		hastaColumn.setCellValueFactory(v -> v.getValue().desdeProperty());
		denominacionColumn.setCellValueFactory(v -> v.getValue().denominacionProperty());
		organizacionColumn.setCellValueFactory(v -> v.getValue().organizadorProperty());
		
	}

	@FXML
    void onAñadirAction(ActionEvent event) {
		añadirTituloController = new AñadirTituloController();

		Dialog<Titulo> dialog = new Dialog<>();
		dialog.setTitle("Añadir título");
		dialog.setHeaderText("Añade un nuevo título.");
		dialog.initOwner(MiCVApp.primaryStage);
		
		ButtonType loginButtonType = new ButtonType("Crear", ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

		dialog.getDialogPane().setContent(añadirTituloController.getView());

		Node loginButton = dialog.getDialogPane().lookupButton(loginButtonType);
		loginButton.setDisable(true);
		
		añadirTituloController.getDenominacionText().textProperty().addListener((o, ov, nv) -> {
			loginButton.setDisable(disableAñadirButton());
		});
		añadirTituloController.getOrganizadorText().textProperty().addListener((o, ov, nv) -> {
			loginButton.setDisable(disableAñadirButton());
		});
		añadirTituloController.getDesdeDate().valueProperty().addListener((o, ov, nv) -> {
			loginButton.setDisable(disableAñadirButton());
		});
		añadirTituloController.getHastaDate().valueProperty().addListener((o, ov, nv) -> {
			loginButton.setDisable(disableAñadirButton());
		});
		
		Platform.runLater(() -> añadirTituloController.getDenominacionText());

		dialog.setResultConverter(dialogButton -> {
		    if (dialogButton == loginButtonType) {
		        return new Titulo(
	        		añadirTituloController.getDesdeDate().getValue(), 
	        		añadirTituloController.getHastaDate().getValue(),
	        		añadirTituloController.getDenominacionText().getText(),
	        		añadirTituloController.getOrganizadorText().getText()
        		);
		    }
		    return null;
		});

		Optional<Titulo> result = dialog.showAndWait();
		if(result.isPresent() && !result.get().getDenominacion().isBlank() && 
				!result.get().getOrganizador().isBlank() && 
				!(result.get().getDesde() == null) && 
				!(result.get().getHasta() == null) && 
				!formacion.contains(result.get())) {
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
    
    boolean disableAñadirButton() {
    	return añadirTituloController.getDenominacionText().getText().trim().isEmpty() || 
			añadirTituloController.getOrganizadorText().getText().trim().isEmpty() ||
			(añadirTituloController.getDesdeDate().getValue() == null) ||
			(añadirTituloController.getHastaDate().getValue() == null);
    }
    
    public void loadFormacion(ObservableList<Titulo> formacion) {
    	formacionTable.getItems().addAll(formacion);
	}
    
    public ListProperty<Titulo> getFormacion() {
		return formacion;
	}
    
    public BorderPane getView() {
		return view;
	}
    
}
