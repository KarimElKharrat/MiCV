package dad.micv;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Optional;
import java.util.stream.Collectors;

import dad.micv.controllers.MainController;
import dad.micv.model.Nacionalidad;
import javafx.application.Application;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class MiCVApp extends Application {

	private MainController controller = new MainController();
	
	public static Stage primaryStage;
	
	private static final File NACIONALIDADES_FILE = new File("src/main/resources/csv/nacionalidades.csv");
	private static final File PAISES_FILE = new File("src/main/resources/csv/paises.csv");
	
	private ListProperty<Nacionalidad> nacionalidades = new SimpleListProperty<>(FXCollections.observableArrayList());
	private ListProperty<String> paises = new SimpleListProperty<>(FXCollections.observableArrayList());
	
	@Override
	public void init() throws Exception {
		if(NACIONALIDADES_FILE.exists()) {
			nacionalidades.addAll(
				Files.readAllLines(
					NACIONALIDADES_FILE.toPath(),
					StandardCharsets.UTF_8
				).stream()
				.map(texto -> {
					return new Nacionalidad(texto);
				}).collect(Collectors.toList())
			);
		}
		
		if(PAISES_FILE.exists()) {
			paises.addAll(
				Files.readAllLines(
					PAISES_FILE.toPath(),
					StandardCharsets.UTF_8
				)
			);
		}
		
		controller.nacionalidadesProperty().bind(nacionalidades);
		controller.paisesProperty().bind(paises);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		MiCVApp.primaryStage = primaryStage;
		
		primaryStage.setTitle("MiCV");
		primaryStage.setScene(new Scene(controller.getView()));
		primaryStage.getIcons().add(new Image(getClass().getResource("/images/cv64x64.png").toString()));
		primaryStage.show();
		
		MiCVApp.primaryStage.setOnCloseRequest(e -> {
			Alert alert = new Alert(AlertType.CONFIRMATION);
	    	alert.setTitle("Salir");
	    	alert.setHeaderText("Está a punto de salir de la aplicación.");
	    	alert.setContentText("¿Desea continuar?");
	    	alert.initOwner(MiCVApp.primaryStage);
	    	
	    	Optional<ButtonType> result = alert.showAndWait();
	    	if (result.get() == ButtonType.OK){
	    		MiCVApp.primaryStage.close();
	    	} else {
	    		e.consume();
	    		MiCVApp.primaryStage.show();
	    	}
		});	
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
