package br.schindler.math.matrix.operations;

import br.schindler.math.matrix.Matrix;
import br.schindler.math.matrix.fields.Field;

public class Multiplication<T extends Field> implements Operation<T> {
	
	/*
	 * Valor inicial para uso de operação acumulativa
	 */
	private T zero;
	
	/**
	 * 
	 * @param zero
	 */
	public Multiplication(T zero) {
		this.zero = zero;
	}	
	
	/*
	 * (non-Javadoc)
	 * @see br.schindler.math.matrix.operations.Operation#get(int, int, java.lang.Object[])
	 */
	@SuppressWarnings("unchecked")
	@Override
	public T get(int line, int col, Object... params) {
		Matrix<T> p2 = (Matrix<T>) params[1];		
		return get(line*p2.columns()+col, params);
	}

	/*
	 * (non-Javadoc)
	 * @see br.schindler.math.matrix.operations.Operation#get(int, java.lang.Object[])
	 */
	@SuppressWarnings("unchecked")
	@Override
	public T get(int index, Object... params) {
		Matrix<T> p1 = (Matrix<T>) params[0];
		Matrix<T> p2 = (Matrix<T>) params[1];
		
		int col = index % p2.columns();
		int lin = index / p2.columns();
		int col1= lin   * p1.columns(); 
		
		T ret = (T) zero.clone();
		
		for (int j = 0; j < p1.columns(); j++){
			//TODO: Can I do it better?
			ret.inc(p2.get(j, col).mul(p1.getByIndex(col1+j)));
		}	
		
		return ret;
	}
}
