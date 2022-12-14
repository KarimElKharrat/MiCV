package dad.micv.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Conocimiento {

	private StringProperty denominacion = new SimpleStringProperty();
	private ObjectProperty<Nivel> nivel = new SimpleObjectProperty<>();

	public Conocimiento() {}
	
	public Conocimiento(String denominacion, Nivel nivel) {
		setDenominacion(denominacion);
		setNivel(nivel);
	}
	
	public final StringProperty denominacionProperty() {
		return this.denominacion;
	}

	public final String getDenominacion() {
		return this.denominacionProperty().get();
	}

	public final void setDenominacion(final String denominacion) {
		this.denominacionProperty().set(denominacion);
	}

	public final ObjectProperty<Nivel> nivelProperty() {
		return this.nivel;
	}

	public final Nivel getNivel() {
		return this.nivelProperty().get();
	}

	public final void setNivel(final Nivel nivel) {
		this.nivelProperty().set(nivel);
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Conocimiento) {
			Conocimiento nuevo = (Conocimiento) obj;
			return this.getDenominacion().equals(nuevo.getDenominacion()) &&
					this.getNivel().equals(nuevo.getNivel());
		}
		return super.equals(obj);
	}
}
