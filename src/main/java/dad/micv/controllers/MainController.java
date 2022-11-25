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
import dad.micv.model.Nacionalidad;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Tab;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class MainController implements Initializable {

	public static final String DEFAULT_PATH = "cv_files";

	// model

	private ObjectProperty<CV> cv = new SimpleObjectProperty<>();
	private ListProperty<Nacionalidad> nacionalidades = new SimpleListProperty<>(FXCollections.observableArrayList());
	private ListProperty<String> paises = new SimpleListProperty<>(FXCollections.observableArrayList());
	private ObjectProperty<File> file = new SimpleObjectProperty<>();

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
			Files.createDirectories(Paths.get(DEFAULT_PATH));
		} catch (IOException e) {
			e.printStackTrace();
		}

		// load data

		cv.addListener(this::onCVChanged);
		cv.set(new CV());

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

	}

	private void onCVChanged(ObservableValue<? extends CV> o, CV ov, CV nv) {

		if (ov != null) {

			personalController.personalProperty().unbind();
			contactoController.contactoProperty().unbind();
			formacionController.formacionProperty().unbind();
			experienciaController.experienciasProperty().unbind();
			habilidadesController.habilidadesProperty().unbind();

		}

		if (nv != null) {

			personalController.personalProperty().bind(nv.personalProperty());
			contactoController.contactoProperty().bind(nv.contactoProperty());
			formacionController.formacionProperty().bind(nv.formacionProperty());
			experienciaController.experienciasProperty().bind(nv.experenciasProperty());
			habilidadesController.habilidadesProperty().bind(nv.habilidadesProperty());

		}

	}

	@FXML
	void onNuevoAction(ActionEvent event) {
		nuevo();
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
		if (selectedFile != null) {
			abrir(selectedFile);
		}

	}

	@FXML
	void onGuardarAction(ActionEvent event) {
		if (file.get() != null)
			guardar(file.get());
		else {
			guardarComo();
		}
	}

	@FXML
	void onGuardarComoAction(ActionEvent event) {

		guardarComo();

	}

	@FXML
	void onSalirAction(ActionEvent event) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Salir");
		alert.setHeaderText("Está a punto de salir de la aplicación.");
		alert.setContentText("¿Desea continuar?");
		alert.initOwner(MiCVApp.primaryStage);

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK) {
			MiCVApp.primaryStage.close();
		}
	}

	private void nuevo() {

		file.set(null);
		cv.set(new CV());

	}

	private void abrir(File selectedFile) {
		try {
			file.set(selectedFile);
			cv.set(GsonHandler.loadCV(selectedFile));
		} catch (Exception e) {
			System.err.println("No se ha podido, jappens");
			e.printStackTrace();
		}
	}

	private void guardar(File selectedFile) {

		file.set(selectedFile);

		try {
			file.get().createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			GsonHandler.saveCV(cv.get(), file.get());
		} catch (Exception e) {
			System.err.println("No se ha podido, jappens");
			e.printStackTrace();
		}
	}

	private void guardarComo() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Guardar archivo");
		fileChooser.setInitialDirectory(new File(DEFAULT_PATH));
		fileChooser.setInitialFileName(file.get() != null ? file.get().getName() : "");
		ExtensionFilter jpgFilter = new ExtensionFilter("CV files", "*.cv");
		fileChooser.getExtensionFilters().add(jpgFilter);
		fileChooser.setSelectedExtensionFilter(jpgFilter);

		File selectedFile = fileChooser.showSaveDialog(MiCVApp.primaryStage);
		if (selectedFile != null) {
			guardar(selectedFile);
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

	public final ObjectProperty<File> fileProperty() {
		return this.file;
	}

	public final File getFile() {
		return this.fileProperty().get();
	}

	public final void setFile(final File file) {
		this.fileProperty().set(file);
	}

}
