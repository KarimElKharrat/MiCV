package dad.micv.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Idioma extends Conocimiento {

	private StringProperty certificacion = new SimpleStringProperty();
	
	public Idioma() { super(); }
	
	public Idioma(String denominacion, Nivel nivel) {
		super(denominacion, nivel);
	}
	
	public Idioma(String denominacion, Nivel nivel, String certificado) {
		super(denominacion, nivel);
		setCertificacion(certificado);
	}

	public final StringProperty certificacionProperty() {
		return this.certificacion;
	}

	public final String getCertificacion() {
		return this.certificacionProperty().get();
	}

	public final void setCertificacion(final String certificacion) {
		this.certificacionProperty().set(certificacion);
	}
	
	public Conocimiento toConocimiento() {
		return new Conocimiento(this.getDenominacion(), this.getNivel());
	}

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Idioma) {
			Idioma nuevo = (Idioma) obj;
			return this.getDenominacion().equals(nuevo.getDenominacion()) &&
					this.getNivel().equals(nuevo.getNivel()) &&
					this.getCertificacion().equals(nuevo.getCertificacion());
		}
		return super.equals(obj);
	}

}
