package dad.micv.model;

import java.time.LocalDate;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Experiencia {

	private ObjectProperty<LocalDate> desde = new SimpleObjectProperty<>();
	private ObjectProperty<LocalDate> hasta = new SimpleObjectProperty<>();
	private StringProperty denominacion = new SimpleStringProperty();
	private StringProperty empleador = new SimpleStringProperty();
	
	public Experiencia(LocalDate desde, LocalDate hasta, String denominacion, String empleador) {
		setDesde(desde);
		setHasta(hasta);
		setDenominacion(denominacion);
		setEmpleador(empleador);
	}

	public final ObjectProperty<LocalDate> desdeProperty() {
		return this.desde;
	}

	public final LocalDate getDesde() {
		return this.desdeProperty().get();
	}

	public final void setDesde(final LocalDate desde) {
		this.desdeProperty().set(desde);
	}

	public final ObjectProperty<LocalDate> hastaProperty() {
		return this.hasta;
	}

	public final LocalDate getHasta() {
		return this.hastaProperty().get();
	}

	public final void setHasta(final LocalDate hasta) {
		this.hastaProperty().set(hasta);
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

	public final StringProperty empleadorProperty() {
		return this.empleador;
	}

	public final String getEmpleador() {
		return this.empleadorProperty().get();
	}

	public final void setEmpleador(final String empleador) {
		this.empleadorProperty().set(empleador);
	}

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Experiencia) {
			Experiencia nuevo = (Experiencia) obj;
			return this.getDenominacion().equals(nuevo.getDenominacion()) &&
					this.getEmpleador().equals(nuevo.getEmpleador()) &&
					this.getDesde().isEqual(nuevo.getDesde()) &&
					this.getHasta().isEqual(nuevo.getHasta());
		}
		return super.equals(obj);
	}

}
