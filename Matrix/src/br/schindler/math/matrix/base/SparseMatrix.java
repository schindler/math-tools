/**
 * 
 */
package br.schindler.math.matrix.base;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import br.schindler.math.matrix.Matrix;
import br.schindler.math.matrix.fields.Field;

/**
 * @author Fernando
 *
 */
public abstract class SparseMatrix implements Matrix<Field>{

	/**
	 * 
	 */
	private int lines, columns;
	
	/**
	 * 
	 */
	protected Field zero;
	
	/**
	 * 
	 */
	protected Map<Integer, Field> elements = new HashMap<Integer, Field>();
	
	/**
	 * 
	 * @param lines
	 * @param columns
	 */
	public SparseMatrix(int lines, int columns, Field zero) {
		this.lines   = lines;
		this.columns = columns;
		this.zero    = zero;
	}
	
	/*
	 * (non-Javadoc)
	 * @see br.schindler.math.matrix.Matrix<Field>#get(int, int)
	 */
	@Override
	public Field get(int i, int j) {
		return getByIndex(i*this.columns+j);
	}
	
	/*
	 * (non-Javadoc)
	 * @see br.schindler.math.matrix.Matrix<Field>#getByIndex(int)
	 */
	@Override
	public Field getByIndex(int index) {
		
		if (index >= lines*columns)
			throw new IndexOutOfBoundsException();
		
		Field result = this.elements.get(index);
		
		if (null == result)
			result = zero;
		
		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see br.schindler.math.matrix.Matrix<Field>#get(int, int, int, int)
	 */
	@Override
	public Matrix<Field> get(int fromLine, int toLine, int fromCol, int toCol) throws IndexOutOfBoundsException {
	 
		return null;
	}
	
	/*
	 * (non-Javadoc)
	 * @see br.schindler.math.matrix.Matrix<Field>#set(int, int, java.lang.Number)
	 */
	@Override
	public void set(int i, int j, Field elem) throws IndexOutOfBoundsException {
		if (i < lines && j < columns){
			if (elem.equals(zero))
				this.elements.remove(i*this.columns+j);
			else
				this.elements.put(i*this.columns+j, elem);
		}
		else
			throw new IndexOutOfBoundsException();
	}
	
	/*
	 * (non-Javadoc)
	 * @see br.schindler.math.matrix.Matrix<Field>#fill(int, java.lang.Number)
	 */
	@Override
	public Matrix<Field> fill(int i, Field elem) throws IndexOutOfBoundsException {
		i *= this.columns; 
		for (int j = 0; j < columns; j++){
			this.elements.put(i+j, elem);
		}
		return this;
	}
	
	/*
	 * (non-Javadoc)
	 * @see br.schindler.math.matrix.Matrix<Field>#fill(java.lang.Number)
	 */
	@Override
	public  Matrix<Field>  fill(Field elem) {
		this.zero = elem;
		this.elements.clear();
		return this;
	}
	
	/*
	 * (non-Javadoc)
	 * @see br.schindler.math.matrix.Matrix<Field>#lines()
	 */
	@Override
	public int lines() {
		return this.lines;
	}
	
	/*
	 * (non-Javadoc)
	 * @see br.schindler.math.matrix.Matrix<Field>#columns()
	 */
	@Override
	public int columns() {
		return this.columns;
	}
	 
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		
		if (obj instanceof SparseMatrix) {
			SparseMatrix other = (SparseMatrix) obj;
			if ((other.columns == columns) && (other.lines == lines)) {
				if (zero.equals(other.zero)) {
					Set<Integer> key1 = this.elements.keySet();
					Set<Integer> key2 = other.elements.keySet();
					for (Integer k : key1){
						if (!key2.contains(k)) {
							System.err.println("not key " + k + " elem: " + this.elements.get(k)); 
							return false; 
						}
						if(!this.elements.get(k).equals(other.elements.get(k))){
							System.err.println(this.elements.get(k) + " != "  + other.elements.get(k));
							return false;
						}
					}
					return true;
				}
			}			
		}
		System.err.println("not SparseMatrix");
		return false;
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
