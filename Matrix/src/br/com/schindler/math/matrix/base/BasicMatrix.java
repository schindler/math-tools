/**
 * 
 */
package br.com.schindler.math.matrix.base;

import java.util.Arrays;

import br.com.schindler.math.matrix.Matrix;
import br.com.schindler.math.matrix.OperationsSet;

/**
 * @author Fernando
 *
 */
public abstract class BasicMatrix<K, T extends OperationsSet<K>> implements Matrix<K, T> {

	/**
	 * 
	 */
	private int lines, columns;
	
	/**
	 * 
	 */
	protected T[] elements;
	
	/**
	 * 
	 * @param lines
	 * @param columns
	 */
	public BasicMatrix(int lines, int columns) {
		this.lines    = lines;
		this.columns  = columns;
		this.elements = create(lines*columns);
	}
	
	/**
	 * 
	 * @param size
	 * @return
	 */
	protected abstract T[] create(int size);
	
	/*
	 * (non-Javadoc)
	 * @see br.com.schindler.math.matrix.Matrix#get(int, int)
	 */
	@Override
	public T get(int i, int j) throws IndexOutOfBoundsException {
		return this.elements[i*this.columns+j];
	}
	
	/*
	 * (non-Javadoc)
	 * @see br.com.schindler.math.matrix.Matrix#row(int)
	 */
	@Override
	public T[] row(int i) {
	    T[] result = create(columns());
	    System.arraycopy(this.elements, columns()*i, result, 0, columns());
		return result;
	}
	
	/*
	 * (non-Javadoc)
	 * @see br.com.schindler.math.matrix.Matrix#set(int, int, java.lang.Number)
	 */
	@Override
	public void set(int i, int j, T elem) throws IndexOutOfBoundsException {
		this.elements[i*this.columns+j] = elem;	
	}
	
	/*
	 * (non-Javadoc)
	 * @see br.com.schindler.math.matrix.Matrix#fill(int, java.lang.Number)
	 */
	@Override
	public void fill(int i, T elem) throws IndexOutOfBoundsException {
		i *= this.columns;
		Arrays.fill(this.elements, i, i + this.columns, elem);
	}
	
	/*
	 * (non-Javadoc)
	 * @see br.com.schindler.math.matrix.Matrix#fill(java.lang.Number)
	 */
	@Override
	public void fill(T elem) {
		Arrays.fill(this.elements, elem);		
	}
	
	/*
	 * (non-Javadoc)
	 * @see br.com.schindler.math.matrix.Matrix#lines()
	 */
	@Override
	public int lines() {
		return this.lines;
	}
	
	/*
	 * (non-Javadoc)
	 * @see br.com.schindler.math.matrix.Matrix#columns()
	 */
	@Override
	public int columns() {
		return this.columns;
	}
	 	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
	 
		for (int i = 0; i < lines(); i++){
			result.append("| ");
			
			for (int j = 0; j < columns(); j++){
				result.append(String.format("%s ", get(i,j)));
			}
			
			if (i +1 < lines())
				result.append("|\n");
			else
				result.append(String.format("|[%dx%d]", lines(), columns()));
		}
				
		return result.toString();
	}
}
