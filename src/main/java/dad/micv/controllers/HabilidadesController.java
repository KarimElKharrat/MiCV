package dad.micv.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import dad.micv.MiCVApp;
import dad.micv.model.Conocimiento;
import dad.micv.model.Idioma;
import dad.micv.model.Nivel;
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

public class HabilidadesController implements Initializable {

	// model
	
	private ListProperty<Conocimiento> habilidades = new SimpleListProperty<>(FXCollections.observableArrayList());
	
	// controllers
	
	AñadirConocimientoController añadirConocimientoController;
	
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
		
		conocimientosTable.itemsProperty().bind(habilidades);
		eliminarButton.disableProperty().bind(conocimientosTable.getSelectionModel().selectedItemProperty().isNull());
		
		// cell value factories
		
		denominacionColumn.setCellValueFactory(v -> v.getValue().denominacionProperty());
		nivelColumn.setCellValueFactory(v -> v.getValue().nivelProperty());
		
	}
	
	@FXML
	void onAñadirConocimientoAction(ActionEvent event) {
		
		añadirConocimientoController = new AñadirConocimientoController();
		añadirConocimientoController.getCertificacionLabel().setVisible(false);
		añadirConocimientoController.getCertificacionText().setVisible(false);
		añadirConocimientoController.getNivelCombo().getItems().addAll(Nivel.BASICO, Nivel.MEDIO, Nivel.AVANZADO);
		
		Dialog<Conocimiento> dialog = new Dialog<>();
		dialog.setTitle("Añadir conocimiento");
		dialog.setHeaderText("Añade un nuevo conocimiento.");
		dialog.setResizable(true);
		dialog.initOwner(MiCVApp.primaryStage);
		
		ButtonType loginButtonType = new ButtonType("Crear", ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

		dialog.getDialogPane().setContent(añadirConocimientoController.getView());

		Node loginButton = dialog.getDialogPane().lookupButton(loginButtonType);
		loginButton.setDisable(true);
		
		añadirConocimientoController.getDenominacionText().textProperty().addListener((o, ov, nv) -> {
		    loginButton.setDisable(disableAñadirButton(1));
		});
		añadirConocimientoController.getNivelCombo().valueProperty().addListener((o, ov, nv) -> {
			loginButton.setDisable(disableAñadirButton(1));
		});
		
		Platform.runLater(() -> añadirConocimientoController.getDenominacionText());

		dialog.setResultConverter(dialogButton -> {
		    if (dialogButton == loginButtonType) {
		        return new Conocimiento(
	        		añadirConocimientoController.getDenominacionText().getText(), 
	        		añadirConocimientoController.getNivelCombo().getValue()
        		);
		    }
		    return null;
		});

		Optional<Conocimiento> result = dialog.showAndWait();
		if(result.isPresent() && !result.get().getDenominacion().isBlank() && 
				!(result.get().getNivel() == null) && 
				!habilidades.contains(result.get())) {
			habilidades.add(result.get());
		}
		
	}

	@FXML
	void onAñadirIdiomaAction(ActionEvent event) {
		añadirConocimientoController = new AñadirConocimientoController();
		añadirConocimientoController.getNivelCombo().getItems().addAll(Nivel.BASICO, Nivel.MEDIO, Nivel.AVANZADO);
		
		Dialog<Idioma> dialog = new Dialog<>();
		dialog.setTitle("Añadir idioma");
		dialog.setHeaderText("Añade un nuevo idioma.");
		dialog.initOwner(MiCVApp.primaryStage);
		
		ButtonType loginButtonType = new ButtonType("Crear", ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

		dialog.getDialogPane().setContent(añadirConocimientoController.getView());

		Node loginButton = dialog.getDialogPane().lookupButton(loginButtonType);
		loginButton.setDisable(true);
		
		añadirConocimientoController.getDenominacionText().textProperty().addListener((o, ov, nv) -> {
		    loginButton.setDisable(disableAñadirButton(2));
		});
		añadirConocimientoController.getNivelCombo().valueProperty().addListener((o, ov, nv) -> {
			loginButton.setDisable(disableAñadirButton(2));
		});
		añadirConocimientoController.getCertificacionText().textProperty().addListener((o, ov, nv) -> {
			loginButton.setDisable(disableAñadirButton(2));
		});
		
		Platform.runLater(() -> añadirConocimientoController.getDenominacionText());

		dialog.setResultConverter(dialogButton -> {
		    if (dialogButton == loginButtonType) {
		        return new Idioma(
	        		añadirConocimientoController.getDenominacionText().getText(), 
	        		añadirConocimientoController.getNivelCombo().getValue(),
	        		añadirConocimientoController.getCertificacionText().getText()
        		);
		    }
		    return null;
		});

		Optional<Idioma> result = dialog.showAndWait();
		if(result.isPresent() && !result.get().getDenominacion().isBlank() && 
				!(result.get().getNivel() == null) && 
				!result.get().getCertificacion().isBlank() &&
				!habilidades.contains(result.get())) {
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
    	if (result.get() == ButtonType.OK){
    		habilidades.remove(conocimientosTable.getSelectionModel().getSelectedItem());
    	}
    	
	}
	

	private boolean disableAñadirButton(int mode) {
		if(mode == 1) {
			return añadirConocimientoController.getDenominacionText().getText().trim().isEmpty() || 
				(añadirConocimientoController.getNivelCombo().getValue() == null);
		} else {
			return añadirConocimientoController.getDenominacionText().getText().trim().isEmpty() || 
					(añadirConocimientoController.getNivelCombo().getValue() == null) ||
					añadirConocimientoController.getCertificacionText().getText().trim().isEmpty();
		}
	}
	
	public void loadHabilidades(ObservableList<Conocimiento> habilidades) {
    	conocimientosTable.getItems().addAll(habilidades);
    }
    
    public ListProperty<Conocimiento> getHabilidades() {
		return habilidades;
	}
	
	public BorderPane getView() {
		return view;
	}

}
