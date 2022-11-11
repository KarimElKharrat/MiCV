package dad.micv.controllers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Tab;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class MainController implements Initializable {
	
	// model
	
	private CV cv = new CV();
	private ListProperty<Nacionalidad> nacionalidades = new SimpleListProperty<>(FXCollections.observableArrayList());
	private ListProperty<String> paises = new SimpleListProperty<>(FXCollections.observableArrayList());
	private static final String DEFAULT_PATH = System.getProperty("user.home") + "\\Documents\\cv";
	private File file = new File(DEFAULT_PATH + "\\datos.cv");
	
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
		
		try {
			Files.createDirectories(Paths.get(file.getParent()));
			file.createNewFile();
			guardar();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// load data
		
		nuevoImage.setImage(new Image(getClass().getResource("/images/nuevo.gif").toString()));
		abrirImage.setImage(new Image(getClass().getResource("/images/abrir.gif").toString()));
		guardarImage.setImage(new Image(getClass().getResource("/images/guardar.gif").toString()));
		
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
	void onNuevoAction(ActionEvent event) {
		try {
			if(file.exists())
				file.delete();
			file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		nuevo(0);
	}
	
	@FXML
    void onAbrirAction(ActionEvent event) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Abrir archivo");
		fileChooser.setInitialDirectory(new File(DEFAULT_PATH));
		ExtensionFilter jpgFilter = new ExtensionFilter("CV files", "*.cv");
		fileChooser.getExtensionFilters().add(jpgFilter);
		fileChooser.setSelectedExtensionFilter(jpgFilter);
		
		File selectedFile = fileChooser.showOpenDialog(MiCVApp.primaryStage);
		if(selectedFile != null) {
			nuevo(1);
			file = selectedFile;
		}
		
		try {
			cv = GsonHandler.loadCV(file);
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
    	guardar();
    }

    @FXML
    void onGuardarComoAction(ActionEvent event) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Guardar archivo");
		fileChooser.setInitialDirectory(new File(DEFAULT_PATH));
		fileChooser.setInitialFileName("datos.cv");
		ExtensionFilter jpgFilter = new ExtensionFilter("CV files", "*.cv");
		fileChooser.getExtensionFilters().add(jpgFilter);
		fileChooser.setSelectedExtensionFilter(jpgFilter);
		
		File selectedFile = fileChooser.showSaveDialog(MiCVApp.primaryStage);
    	if(selectedFile != null) {
    		file = selectedFile;
    		guardar();
    	}
    }

    @FXML
    void onSalirAction(ActionEvent event) {
    	Alert alert = new Alert(AlertType.CONFIRMATION);
    	alert.setTitle("Salir");
    	alert.setHeaderText("Está a punto de salir de la aplicación.");
    	alert.setContentText("¿Desea continuar?");
    	alert.initOwner(MiCVApp.primaryStage);
    	
    	Optional<ButtonType> result = alert.showAndWait();
    	if (result.get() == ButtonType.OK){
    		MiCVApp.primaryStage.close();
    	}
    }

	private void nuevo(int tipo) {
	
		personalController = new PersonalController();
		contactoController = new ContactoController();
		formacionController = new FormacionController();
		experienciaController = new ExperienciaController();
		habilidadesController = new HabilidadesController();
		
		initializeControllers();
		
		if(tipo == 0)
			guardar();
	}
	
    private void guardar() {
    	
    	try {
			file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    	try {
    		cv.setPersonal(personalController.getPersonal());
    		cv.setContacto(contactoController.getContacto());
    		cv.setFormacion(formacionController.getFormacion());
    		cv.setExperencias(experienciaController.getExperencias());
    		cv.setHabilidades(habilidadesController.getHabilidades());
			GsonHandler.saveCV(cv, file);
		} catch (Exception e) {
			System.err.println("No se ha podido, jappens");
			e.printStackTrace();
		}
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
