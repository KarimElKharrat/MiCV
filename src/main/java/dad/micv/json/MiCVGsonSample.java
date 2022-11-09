package dad.micv.json;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.time.LocalDate;

import org.hildan.fxgson.FxGson;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

import dad.micv.model.CV;
import dad.micv.model.Nacionalidad;
import dad.micv.model.Personal;

public class MiCVGsonSample {

	public static void main(String[] args) throws Exception {
		
//		saveCV();
//		loadCV();
		
	}
	
	private static void loadCV() throws JsonSyntaxException, JsonIOException, FileNotFoundException {
		Gson gson = FxGson.fullBuilder()
				.setPrettyPrinting()
				.registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
				.create();
		
		CV cv = gson.fromJson(new FileReader("cnorris.cv"), CV.class);
		System.out.println(cv.getPersonal().getNombre() + " " + cv.getPersonal().getApellidos());
	}

	private static void saveCV() throws IOException {
		Personal personal = new Personal();
		personal.setIdentificacion("12345678Z");
		personal.setNombre("Chuck");
		personal.setApellidos("Norris");
		personal.setFechaNacimiento(LocalDate.of(1956, 11, 22));
		personal.getNacionalidades().add(new Nacionalidad("Española"));
		personal.getNacionalidades().add(new Nacionalidad("Americana"));
		
		CV cv = new CV();
		cv.setPersonal(personal);
		
		Gson gson = FxGson.fullBuilder()
				.setPrettyPrinting()
				.registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
				.create();
		
		String json = gson.toJson(cv, CV.class);
		Files.writeString(new File("cnorris.cv").toPath(), json, StandardCharsets.UTF_8);
	}
	
	
}
