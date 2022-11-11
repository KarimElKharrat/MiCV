package dad.micv.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import dad.micv.MiCVApp;
import dad.micv.json.GsonHandler;
import dad.micv.model.CV;
import dad.micv.model.Conocimiento;
import dad.micv.model.Contacto;
import dad.micv.model.Experiencia;
import dad.micv.model.Nacionalidad;
import dad.micv.model.Personal;
import dad.micv.model.Titulo;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

public class MainController implements Initializable {
	
	// model
	
	private CV cv = new CV();
	private ListProperty<Nacionalidad> nacionalidades = new SimpleListProperty<>(FXCollections.observableArrayList());
	private ListProperty<String> paises = new SimpleListProperty<>(FXCollections.observableArrayList());

	// controllers
	
	private PersonalController personalController = new PersonalController();
	private ContactoController contactoController = new ContactoController();
	private FormacionController formacionController = new FormacionController();
	private ExperienciaController experienciaController = new ExperienciaController();
	private HabilidadesController habilidadesController = new HabilidadesController();
	
	// view
	
	@FXML
    private ImageView abrirImage;
	@FXML
	private ImageView nuevoImage;
	@FXML
    private ImageView guardarImage;
	
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

		// initialize controllers
		
		initializeControllers();
		
		// load data
		
		nuevoImage.setImage(new Image(getClass().getResource("/images/nuevo.gif").toString()));
		abrirImage.setImage(new Image(getClass().getResource("/images/abrir.gif").toString()));
		guardarImage.setImage(new Image(getClass().getResource("/images/guardar.gif").toString()));
		
	}
	
	@FXML
	void onNuevoAction(ActionEvent event) {
		personalController = new PersonalController();
		contactoController = new ContactoController();
		formacionController = new FormacionController();
		experienciaController = new ExperienciaController();
		habilidadesController = new HabilidadesController();
		
		initializeControllers();
	}
	
	private void initializeControllers() {
		
		// tab content
		
		personalTab.setContent(personalController.getView());
		contactoTab.setContent(contactoController.getView());
		formacionTab.setContent(formacionController.getView());
		experienciaTab.setContent(experienciaController.getView());
		conocimientosTab.setContent(habilidadesController.getView());
		
		// bindings
		
		personalController.nacionalidadesProperty().bind(nacionalidades);
		personalController.paisesProperty().bind(paises);
		
		// reset data
		
		cv.setContacto(new Contacto());
		cv.setExperencias(new SimpleListProperty<Experiencia>(FXCollections.observableArrayList()));
		cv.setFormacion(new SimpleListProperty<Titulo>(FXCollections.observableArrayList()));
		cv.setHabilidades(new SimpleListProperty<Conocimiento>(FXCollections.observableArrayList()));
		cv.setPersonal(new Personal());
		
	}
	
	@FXML
    void onAbrirAction(ActionEvent event) {
		//TODO abrir explorador de archivos
		
		try {
			cv = GsonHandler.loadCV("prueba.cv");
			personalController.loadPersonal(cv.getPersonal());
			contactoController.loadContacto(cv.getContacto());
			formacionController.loadFormacion(cv.getFormacion());
			experienciaController.loadExperiencia(cv.getExperencias());
			habilidadesController.loadHabilidades(cv.getHabilidades());
		} catch (Exception e) {
			System.err.println("No se ha podido, jappens");
			e.printStackTrace();
		}
    }

    @FXML
    void onGuardarAction(ActionEvent event) {
    	//TODO implementar explorador de windows
    	
    	try {
    		cv.setPersonal(personalController.getPersonal());
    		cv.setContacto(contactoController.getContacto());
    		cv.setFormacion(formacionController.getFormacion());
    		cv.setExperencias(experienciaController.getExperencias());
    		cv.setHabilidades(habilidadesController.getHabilidades());
			GsonHandler.saveCV(cv, "prueba.cv");
		} catch (Exception e) {
			System.err.println("No se ha podido, jappens");
			e.printStackTrace();
		}
    }

    @FXML
    void onGuardarComoAction(ActionEvent event) {
    	try {
			Runtime.getRuntime().exec("explorer.exe");
		} catch (IOException e) {
			e.printStackTrace();
		}
    	System.out.println("guardar como");
    }

    @FXML
    void onSalirAction(ActionEvent event) {
    	MiCVApp.primaryStage.close();
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
