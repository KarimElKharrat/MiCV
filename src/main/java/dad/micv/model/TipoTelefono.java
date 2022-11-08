package dad.micv.model;

public enum TipoTelefono {
	DOMICILIO(0),
	MOVIL(1);
	
	private final int valor;
	
	private TipoTelefono(int valor) {
		this.valor = valor;
	}
	
	public int getValor() {
		return valor;
	}
	
}
