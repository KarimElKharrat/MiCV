package dad.micv.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import dad.micv.MiCVApp;
import dad.micv.dialogs.NuevoTelefonoDialog;
import dad.micv.model.Contacto;
import dad.micv.model.Email;
import dad.micv.model.Telefono;
import dad.micv.model.TipoTelefono;
import dad.micv.model.Web;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;

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
		
		NuevoTelefonoDialog ntd = new NuevoTelefonoDialog();
		
		Optional<Telefono> result = ntd.showAndWait();
		if (result.isPresent() && !result.get().getTelefono().isBlank() && !(result.get() == null) && !contacto.getTelefonos().contains(result.get())){
    		contacto.getTelefonos().add(result.get());
    	}
	}
	
	@FXML
    void onAñadirEmailButton(ActionEvent event) {
		TextInputDialog dialog = crearTextInputDialog("", "Nuevo e-mail", "Crear una nueva dirección de correo.", "E-mail:");

    	// Traditional way to get the response value.
    	Optional<String> result = dialog.showAndWait();
    	if (result.isPresent()  && !result.get().isBlank() && !contacto.getEmails().contains(new Email(result.get()))){
    		contacto.getEmails().add(new Email(result.get()));
    	}
    }

    @FXML
    void onAñadirWebButton(ActionEvent event) {
    	TextInputDialog dialog = crearTextInputDialog("http://", "Nuevo web", "Crear una nueva dirección web.", "URL:"); 

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
    
    private TextInputDialog crearTextInputDialog(String prompt, String title, String header, String content) {
    	TextInputDialog dialog = new TextInputDialog(prompt);
    	dialog.setTitle(title);
    	dialog.setHeaderText(header);
    	dialog.setContentText(content);
    	dialog.initOwner(MiCVApp.primaryStage);
    	
    	StringProperty contentTextField = new SimpleStringProperty();
    	contentTextField.bind(dialog.getEditor().textProperty());
    	
    	dialog.getDialogPane().lookupButton(ButtonType.OK).disableProperty().bind(contentTextField.isEmpty());
    	
    	return dialog;
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
