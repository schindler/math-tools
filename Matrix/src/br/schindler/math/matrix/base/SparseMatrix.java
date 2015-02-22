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
public class SparseMatrix  extends BaseMatrix<Field> {
	
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
		super(lines, columns, zero);
	}

	/*
	 * (non-Javadoc)
	 * @see br.schindler.math.matrix.base.BaseMatrix#create(int, int, br.schindler.math.matrix.fields.Field)
	 */
	@Override
	public BaseMatrix<Field> create(int lines, int columns) {
		return new SparseMatrix(lines, columns, zero);
	}
	
	/*
	 * (non-Javadoc)
	 * @see br.schindler.math.matrix.Matrix<Field>#getByIndex(int)
	 */
	@Override
	public synchronized Field getByIndex(int index) {
		
		if (index >= lines*columns)
			throw new IndexOutOfBoundsException();
		
		Field result = this.elements.get(index);
		
		if (null == result)
			result = zero;
		
		return result;
	}
	
	/*
	 * (non-Javadoc)
	 * @see br.schindler.math.matrix.Matrix#setByIndex(int, java.lang.Object)
	 */
	@Override
	public synchronized void setByIndex(int index, Field elem) {
		if (index < lines * columns){
			if (elem.equals(zero))
				this.elements.remove(index);
			else
				this.elements.put(index, elem);
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
	public  Matrix<Field> fill(Field elem) {
		this.zero = elem.clone();
		this.elements.clear();
		return this;
	}
	
	/*
	 * (non-Javadoc)
	 * @see br.schindler.math.matrix.Matrix#mul(br.schindler.math.matrix.Matrix)
	 */
	@Override
	public Matrix<Field> mul(Matrix<Field> other) {
		if (columns() != other.lines())
			throw new IndexOutOfBoundsException(String.format("mul %d != %d", columns(), other.lines()));
		
		SparseMatrix result = new SparseMatrix (lines, other.columns(), zero);
 
		for (int i = 0; i < lines*other.columns(); i++) { 
			Field v = mul.get(i, this, other);
			
			if (!v.equals(zero))
				result.elements.put(i, v);
		}
		
		return result;
	}
	
	/*
	 * (non-Javadoc)
	 * @see br.schindler.math.matrix.Matrix#mul(java.lang.Object)
	 */
	@Override
	public Matrix<Field> mul(Field elem) {
		SparseMatrix result = new SparseMatrix(lines, columns, zero); 
 
		for (Integer k : elements.keySet()){
			Field v = elements.get(k).mul(elem);
			
			if (!zero.equals(v))
				result.elements.put(k, v);	 
		}	 		
		
		return result;
	}
	
	/*
	 * (non-Javadoc)
	 * @see br.schindler.math.matrix.Matrix#increment(java.lang.Object)
	 */
	@Override
	public Matrix<Field> increment(Field elem) {
		for (int i = 0; i < lines*columns; i++) { 
			Field v = elements.get(i);
			
			if (v != null)
				v.inc(elem);
			else
				if (!zero.equals(elem))
					elements.put(i, elem.clone());
		}	
		
		return this;
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
					
					if (key1.size()!=key2.size()) 
						return false;
					
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

	@Override
	public Matrix<Field> add(Field elem) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Matrix<Field> add(Matrix<Field> other) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Matrix<Field> decrement(Field elem) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Matrix<Field> sub(Field elem) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Matrix<Field> sub(Matrix<Field> other) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Matrix<Field> scale(Field elem) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Matrix<Field> transpose() {
		// TODO Auto-generated method stub
		return null;
	}
}
