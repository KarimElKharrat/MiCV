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

public class GsonHandler {

	public static void main(String[] args) throws Exception {
		
//		saveCV();
//		loadCV();
		
	}
	
	public static CV loadCV(File file) throws JsonSyntaxException, JsonIOException, FileNotFoundException {
		Gson gson = FxGson.fullBuilder()
				.setPrettyPrinting()
				.registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
				.create();
		
//		CV cv = gson.fromJson(new FileReader(path), CV.class);
		return gson.fromJson(new FileReader(file), CV.class);
	}

	public static void saveCV(CV cv, File file) throws IOException {
		Gson gson = FxGson.fullBuilder()
				.setPrettyPrinting()
				.registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
				.create();
		
		String json = gson.toJson(cv, CV.class);
		Files.writeString(file.toPath(), json, StandardCharsets.UTF_8);
	}
	
	
}
