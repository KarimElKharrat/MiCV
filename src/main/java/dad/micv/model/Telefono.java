package dad.micv.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Telefono {

	private StringProperty telefono = new SimpleStringProperty();
	private ObjectProperty<TipoTelefono> tipo = new SimpleObjectProperty<>();
	
	public Telefono(String telefono, TipoTelefono tipo) {
		setTelefono(telefono);
		setTipo(tipo);
	}

	public final StringProperty telefonoProperty() {
		return this.telefono;
	}

	public final String getTelefono() {
		return this.telefonoProperty().get();
	}

	public final void setTelefono(final String telefono) {
		this.telefonoProperty().set(telefono);
	}

	public final ObjectProperty<TipoTelefono> tipoProperty() {
		return this.tipo;
	}

	public final TipoTelefono getTipo() {
		return this.tipoProperty().get();
	}

	public final void setTipo(final TipoTelefono tipo) {
		this.tipoProperty().set(tipo);
	}
	

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Telefono) {
			Telefono nuevo = (Telefono) obj;
			return nuevo.getTipo().equals(this.getTipo()) && nuevo.getTelefono().equals(this.getTelefono());
		}
		return super.equals(obj);
	}

}
