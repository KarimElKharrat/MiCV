package dad.micv.model;

public enum Nivel {
	BASICO(0), 
	MEDIO(1), 
	AVANZADO(2);
	
	private final int valor;

	private Nivel(int valor) {
		this.valor = valor;
	}
	
	public int getValor() {
		return valor;
	}
}
