package dad.micv.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import dad.micv.MiCVApp;
import dad.micv.model.Contacto;
import dad.micv.model.Email;
import dad.micv.model.Telefono;
import dad.micv.model.TipoTelefono;
import dad.micv.model.Web;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Pair;

public class ContactoController implements Initializable {

	// model
	
	private Contacto contacto = new Contacto();
	
	// view
    
	@FXML
    private Button eliminarEmailButton;
    @FXML
    private Button eliminarTelefonoButton;
    @FXML
    private Button eliminarWebButton;
	
	@FXML
	private TableView<Telefono> telefonosTable;
	@FXML
    private TableView<Email> direccionTable;
	@FXML
	private TableView<Web> webTable;
	
	@FXML
    private TableColumn<Telefono, String> numeroColumn;
	@FXML
    private TableColumn<Telefono, TipoTelefono> tipoColumn;
	@FXML
    private TableColumn<Email, String> direccionColumn;
	@FXML
    private TableColumn<Web, String> urlColumn;

    @FXML
    private SplitPane view;
	
	public ContactoController() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ContactoView.fxml"));
			loader.setController(this);
			loader.load();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		// bindings
		
		telefonosTable.itemsProperty().bind(contacto.telefonosProperty());
		numeroColumn.setCellValueFactory(v -> v.getValue().telefonoProperty());
		tipoColumn.setCellValueFactory(v -> v.getValue().tipoProperty());
		
		direccionTable.itemsProperty().bind(contacto.emailsProperty());
		direccionColumn.setCellValueFactory(v -> v.getValue().direccionProperty());
		
		webTable.itemsProperty().bind(contacto.websProperty());
		urlColumn.setCellValueFactory(v -> v.getValue().urlProperty());
		
		eliminarTelefonoButton.disableProperty().bind(telefonosTable.getSelectionModel().selectedItemProperty().isNull());
		eliminarEmailButton.disableProperty().bind(direccionTable.getSelectionModel().selectedItemProperty().isNull());
		eliminarWebButton.disableProperty().bind(webTable.getSelectionModel().selectedItemProperty().isNull());
		
	}
	
	@FXML
	void onAñadirTelefonoButton(ActionEvent event) {
		Dialog<Pair<String, TipoTelefono>> dialog = new Dialog<>();
		dialog.setTitle("Nuevo teléfono");
		dialog.setHeaderText("Introduzca el nuevo número de teléfono.");
		dialog.initOwner(MiCVApp.primaryStage);
		Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
		stage.getIcons().add(new Image(this.getClass().getResource("/images/cv64x64.png").toString()));
		
		
		ButtonType loginButtonType = new ButtonType("Añadir", ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

		// Create the username and password labels and fields.
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(10,10,10,10));
		grid.setAlignment(Pos.CENTER_LEFT);

		TextField telefono = new TextField();
		telefono.setPromptText("Número de Teléfono");
		ComboBox<TipoTelefono> tipoTelefono = new ComboBox<>();
		tipoTelefono.setPromptText("Seleccione un tipo");
		
		tipoTelefono.getItems().addAll(TipoTelefono.DOMICILIO, TipoTelefono.MOVIL);
		
		grid.add(new Label("Teléfono:"), 0, 0);
		grid.add(telefono, 1, 0);
		grid.add(new Label("Tipo:"), 0, 1);
		grid.add(tipoTelefono, 1, 1);

		// Enable/Disable login button depending on whether a username was entered.
		Node loginButton = dialog.getDialogPane().lookupButton(loginButtonType);
		loginButton.setDisable(true);

		// Do some validation (using the Java 8 lambda syntax).
		telefono.textProperty().addListener((observable, oldValue, newValue) -> {
		    loginButton.setDisable(newValue.trim().isEmpty());
		});

		dialog.getDialogPane().setContent(grid);

		// Request focus on the username field by default.
		Platform.runLater(() -> telefono.requestFocus());

		// Convert the result to a username-password-pair when the login button is clicked.
		dialog.setResultConverter(dialogButton -> {
		    if (dialogButton == loginButtonType) {
		        return new Pair<>(telefono.getText(), tipoTelefono.getSelectionModel().getSelectedItem());
		    }
		    return null;
		});
		
		Optional<Pair<String, TipoTelefono>> result = dialog.showAndWait();
		if (result.isPresent() && !result.get().getKey().isBlank() && !(result.get().getValue() == null) && !contacto.getTelefonos().contains(new Telefono(result.get().getKey(), result.get().getValue()))){
    		contacto.getTelefonos().add(new Telefono(result.get().getKey(), result.get().getValue()));
    	}
	}
	
	@FXML
    void onAñadirEmailButton(ActionEvent event) {
		TextInputDialog dialog = new TextInputDialog();
    	dialog.setTitle("Nuevo e-mail");
    	dialog.setHeaderText("Crear una nueva dirección de correo.");
    	dialog.setContentText("E-mail:");
    	dialog.initOwner(MiCVApp.primaryStage);
    	Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
    	stage.getIcons().add(new Image(this.getClass().getResource("/images/cv64x64.png").toString()));

    	// Traditional way to get the response value.
    	Optional<String> result = dialog.showAndWait();
    	if (result.isPresent()  && !result.get().isBlank() && !contacto.getEmails().contains(new Email(result.get()))){
    		contacto.getEmails().add(new Email(result.get()));
    	}
    }

    @FXML
    void onAñadirWebButton(ActionEvent event) {
		TextInputDialog dialog = new TextInputDialog("http://");
    	dialog.setTitle("Nuevo web");
    	dialog.setHeaderText("Crear una nueva dirección web.");
    	dialog.setContentText("URL:");
    	dialog.initOwner(MiCVApp.primaryStage);
    	Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
    	stage.getIcons().add(new Image(this.getClass().getResource("/images/cv64x64.png").toString()));

    	// Traditional way to get the response value.
    	Optional<String> result = dialog.showAndWait();
    	if (result.isPresent()  && !result.get().isBlank() && !contacto.getWebs().contains(new Web(result.get()))){
    		contacto.getWebs().add(new Web(result.get()));
    	}    	
    }

    @FXML
    void onEliminarTelefonoButton(ActionEvent event) {
    	if(createConfirmationDialog("teléfono"))
    		contacto.getTelefonos().remove(telefonosTable.getSelectionModel().getSelectedItem());
    }
    
    @FXML
    void onEliminarEmailButton(ActionEvent event) {
    	if(createConfirmationDialog("e-mail"))
    		contacto.getEmails().remove(direccionTable.getSelectionModel().getSelectedItem());
    }

    @FXML
    void onEliminarWebButton(ActionEvent event) {
    	if(createConfirmationDialog("web"))
    		contacto.getWebs().remove(webTable.getSelectionModel().getSelectedItem());
    }
    
    boolean createConfirmationDialog(String texto) {
    	Alert alert = new Alert(AlertType.CONFIRMATION);
    	alert.setTitle("Eliminar " + texto);
    	alert.setHeaderText("Está a punto de eliminar un " + texto);
    	alert.setContentText("¿Desea continuar?");
    	alert.initOwner(MiCVApp.primaryStage);
    	
    	Optional<ButtonType> result = alert.showAndWait();
    	if (result.get() == ButtonType.OK){
    	    return true;
    	} else {
    	    return false;
    	}
    }
    
    public Contacto getContacto() {
    	return contacto;
    }

	public void loadContacto(Contacto contacto) {
		telefonosTable.getItems().addAll(contacto.getTelefonos());
		direccionTable.getItems().addAll(contacto.getEmails());
		webTable.getItems().addAll(contacto.getWebs());
	}
    
    public SplitPane getView() {
		return view;
	}

}
