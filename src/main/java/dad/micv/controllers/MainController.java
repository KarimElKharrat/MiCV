package dad.micv.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import dad.micv.model.Nacionalidad;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

public class MainController implements Initializable {

	// controllers
	
	private PersonalController personalController = new PersonalController();
	private ContactoController contactoController = new ContactoController();
	private FormacionController formacionController = new FormacionController();
	private ExperienciaController experienciaController = new ExperienciaController();
	private ConocimientosController conocimientosController = new ConocimientosController();

	// model
	
	private ListProperty<Nacionalidad> nacionalidades = new SimpleListProperty<>(FXCollections.observableArrayList());
	private ListProperty<String> paises = new SimpleListProperty<>(FXCollections.observableArrayList());
	
	// view
	
	@FXML
    private ImageView abrirImage;
	@FXML
	private ImageView nuevoImage;
	@FXML
    private ImageView guardarImage;
	
	@FXML
	private MenuItem nuevoItem;
	@FXML
	private MenuItem abrirItem;
	@FXML
	private MenuItem guardarItem;
	@FXML
	private MenuItem guardarcomoItem;
	@FXML
	private MenuItem salirItem;
	@FXML
	private MenuItem acercadeItem;
	
	@FXML
	private Tab personalTab;
	@FXML
	private Tab contactoTab;
	@FXML
	private Tab formacionTab;
	@FXML
	private Tab experienciaTab;
	@FXML
	private Tab conocimientosTab;
	
	@FXML
	private BorderPane view;

	public MainController() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MainView.fxml"));
			loader.setController(this);
			loader.load();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		// tab content
		
		personalTab.setContent(personalController.getView());
		contactoTab.setContent(contactoController.getView());
		formacionTab.setContent(formacionController.getView());
		experienciaTab.setContent(experienciaController.getView());
		conocimientosTab.setContent(conocimientosController.getView());
		
		// bindings
		
		personalController.nacionalidadesProperty().bind(nacionalidades);
		personalController.paisesProperty().bind(paises);
		
		// listeners
		
		
		
		// load data
		
		nuevoImage.setImage(new Image(getClass().getResource("/images/nuevo.gif").toString()));
		abrirImage.setImage(new Image(getClass().getResource("/images/abrir.gif").toString()));
		guardarImage.setImage(new Image(getClass().getResource("/images/guardar.gif").toString()));
		
	}

	public BorderPane getView() {
		return view;
	}

	public final ListProperty<Nacionalidad> nacionalidadesProperty() {
		return this.nacionalidades;
	}
	
	public final ListProperty<String> paisesProperty() {
		return this.paises;
	}

}
