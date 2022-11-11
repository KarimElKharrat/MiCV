package dad.micv.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

public class AñadirExperienciaController implements Initializable {

	@FXML
    private BorderPane view;
	
	@FXML
	private DatePicker desdeDate;
	@FXML
	private DatePicker hastaDate;

    @FXML
    private TextField denominacionText;
    @FXML
    private TextField empleadorText;
    
    public AñadirExperienciaController() {
    	try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AñadirExperienciaView.fxml"));
			loader.setController(this);
			loader.load();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}
	
	public TextField getDenominacionText() {
		return denominacionText;
	}

	public DatePicker getDesdeDate() {
		return desdeDate;
	}

	public DatePicker getHastaDate() {
		return hastaDate;
	}

	public TextField getEmpleadorText() {
		return empleadorText;
	}

	public BorderPane getView() {
		return view;
	}

}
