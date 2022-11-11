package dad.micv.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Email {

	private StringProperty direccion = new SimpleStringProperty();

	public Email(String direccion) {
		setDireccion(direccion);
	}
	
	public final StringProperty direccionProperty() {
		return this.direccion;
	}

	public final String getDireccion() {
		return this.direccionProperty().get();
	}

	public final void setDireccion(final String direccion) {
		this.direccionProperty().set(direccion);
	}
	

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Email) {
			Email nuevo = (Email) obj;
			return nuevo.getDireccion().equals(this.getDireccion());
		}
		return super.equals(obj);
	}

}
