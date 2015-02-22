package br.schindler.math.matrix.base;

import br.schindler.math.matrix.Matrix;
import br.schindler.math.matrix.fields.Field;
import br.schindler.math.matrix.operations.Multiplication;

/**
 * 
 * @author Fernando
 *
 * @param <T>
 */
public abstract class BaseMatrix<T extends Field> implements Matrix<T> {
	
	/*
	 * 
	 */
	public Multiplication<T> mul;
	
	/*
	 * 
	 */
	protected T zero;
	
	/**
	 * 
	 */
	protected int lines, columns;
	
	/**
	 * 
	 * @param zero
	 */
	public BaseMatrix(int lines, int columns, T zero){
		this.lines   = lines;
		this.columns = columns;
		this.zero    = zero;
		this.mul     = new Multiplication<T>(zero);
	}
	
	/**
	 * 
	 * @param lines
	 * @param columns
	 * @param zero
	 */
	public abstract BaseMatrix<T> create(int lines, int columns);
	
	/*
	 * (non-Javadoc)
	 * @see br.schindler.math.matrix.Matrix#set(int, int, java.lang.Object)
	 */
	@Override
	public void set(int i, int j, T elem) {
		if (i >= lines || j >= columns)
			throw new IndexOutOfBoundsException();		
		setByIndex(i*columns+j, elem);		
	}
	
	/*
	 * (non-Javadoc)
	 * @see br.schindler.math.matrix.Matrix#get(int, int)
	 */
	@Override
	public T get(int i, int j) {
		if (i >= lines || j >= columns)
			throw new IndexOutOfBoundsException();
		return getByIndex(i*columns+j);
	}
	
	/*
	 * (non-Javadoc)
	 * @see br.schindler.math.matrix.Matrix<Field>#get(int, int, int, int)
	 */
	@Override
	public Matrix<T> get(int fromLine, int toLine, int fromCol, int toCol) {
		throw new UnsupportedOperationException();
	}
	
	/*
	 * (non-Javadoc)
	 * @see br.schindler.math.matrix.Matrix#lines()
	 */
	@Override
	public int lines() {
		return this.lines;
	}
	
	/*
	 * (non-Javadoc)
	 * @see br.schindler.math.matrix.Matrix#columns()
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
 
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		
		if (obj instanceof Matrix){
			Matrix<?> other = (Matrix<?>) obj;
			
			if (lines() != other.lines() || columns() != other.columns()) 
				return false;
			
			for (int i = 0; i < columns()*lines(); i++) {
				if (!getByIndex(i).equals(other.getByIndex(i))) 
					return false;
			}
			
			return true;
		}
		
		return false;
	}
}
