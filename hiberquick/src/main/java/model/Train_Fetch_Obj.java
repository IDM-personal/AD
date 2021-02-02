package model;

public class Train_Fetch_Obj {
	private int cod_linea;
	private int val_max;
	
	public Train_Fetch_Obj(int cod_linea, int val_max) {
		super();
		this.cod_linea = cod_linea;
		this.val_max = val_max;
	}
	public int getCod_linea() {
		return cod_linea;
	}
	public void setCod_linea(int cod_linea) {
		this.cod_linea = cod_linea;
	}
	public int getVal_max() {
		return val_max;
	}
	public void setVal_max(int val_max) {
		this.val_max = val_max;
	}
	@Override
	public String toString() {
		return "Train_Fetch_Obj [cod_linea=" + cod_linea + ", val_max=" + val_max + "]";
	}
	
}
