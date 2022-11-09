package dad.micv.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import dad.micv.model.Contacto;
import dad.micv.model.Email;
import dad.micv.model.Telefono;
import dad.micv.model.TipoTelefono;
import dad.micv.model.Web;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class ContactoController implements Initializable {

	// model
	
	private Contacto contacto = new Contacto();
	
	// view
	
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
		
	}
	
	@FXML
    void onAñadirEmailButton(ActionEvent event) {
		//TODO añadir todos los botones
    }

    @FXML
    void onAñadirTelefonoButton(ActionEvent event) {

    }

    @FXML
    void onAñadirWebButton(ActionEvent event) {

    }

    @FXML
    void onEliminarEmailButton(ActionEvent event) {

    }

    @FXML
    void onEliminarTelefonoButton(ActionEvent event) {

    }

    @FXML
    void onEliminarWebButton(ActionEvent event) {

    }
    
    public SplitPane getView() {
		return view;
	}

}
