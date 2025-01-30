package com.aj.model;

public class ResponseAux {

	private boolean error;
	private Object resultado;

	public boolean isError() {
		return error;
	}

	public void setError(boolean error) {
		this.error = error;
	}

	public Object getResultado() {
		return resultado;
	}

	public void setResultado(Object resultado) {
		this.resultado = resultado;
	}

	public ResponseAux(boolean error, Object resultado) {
		super();
		this.error = error;
		this.resultado = resultado;
	}

}
