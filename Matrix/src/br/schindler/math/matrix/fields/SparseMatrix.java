/**
 * 
 */
package br.schindler.math.matrix.fields;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import br.schindler.math.matrix.BaseMatrix;
import br.schindler.math.matrix.Matrix;

/**
 * @author Fernando
 *
 */
public class SparseMatrix  extends BaseMatrix<Field> {
	
	/**
	 * 
	 */
	protected Map<Integer, Field> elements = new HashMap<Integer, Field>();
	
	protected Field zero;
 
	
	/**
	 * 
	 * @param lines
	 * @param columns
	 */
	public SparseMatrix(int lines, int columns, Field zero) {
		super(lines, columns);
		this.zero = zero;
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
	 * @see br.schindler.math.matrix.Matrix<Field>#fill(int, java.lang.Number)
	 */
	@Override
	public Matrix<Field> fill(int i, Field elem) throws IndexOutOfBoundsException {
		i *= this.columns; 
		for (int j = 0; j < columns; j++){
			this.elements.put(i+j, elem.clone());
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
	 * @see br.schindler.math.matrix.Matrix#scale(java.lang.Object)
	 */
	@Override
	public Matrix<Field> scale(Field elem) {
		for (Field f : elements.values())
			f.scale(elem);
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
		SparseMatrix result = new SparseMatrix(lines, other.columns(), zero);		
		for (int c = 0; c < other.columns(); c++){
			 for (Integer k : elements.keySet()){
				int l =(k % columns); 
				int rk = result.columns*l+c;
				Field v = result.elements.get(rk);				
				if (null == v)
					v = zero.clone();				
				v.inc(elements.get(k).mul(other.get(l, c)));
				if (!v.equals(zero))
					result.elements.put(rk, v);
			 }
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
		if (elem.equals(zero))
			return result;		
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
		if (elem.equals(zero))
			return this;		
		for (int i = 0; i < lines*columns; i++) { 
			Field v = elements.get(i);
			
			if (v != null)
				v.inc(elem);
			else
				elements.put(i, elem.clone());
		}			
		return this;
	}
	
	/*
	 * (non-Javadoc)
	 * @see br.schindler.math.matrix.Matrix#decrement(java.lang.Object)
	 */
	@Override
	public Matrix<Field> decrement(Field elem) {
		return increment(elem.neg());
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

	/*
	 * (non-Javadoc)
	 * @see br.schindler.math.matrix.Matrix#add(java.lang.Object)
	 */
	@Override
	public Matrix<Field> add(Field elem) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see br.schindler.math.matrix.Matrix#add(br.schindler.math.matrix.Matrix)
	 */
	@Override
	public Matrix<Field> add(Matrix<Field> other) {
		Matrix<Field>  ret = new SparseMatrix(lines, columns, zero);
		for (int l = 0; l < lines; l++){
			for (int c  = 0; c < columns; c++)
				ret.set(l, c, get(l, c).add(other.get(l, c)));
		}
		return ret;
	}

	/*
	 * (non-Javadoc)
	 * @see br.schindler.math.matrix.Matrix#sub(java.lang.Object)
	 */
	@Override
	public Matrix<Field> sub(Field elem) {
		return add(elem.neg());
	}

	/*
	 * (non-Javadoc)
	 * @see br.schindler.math.matrix.Matrix#sub(br.schindler.math.matrix.Matrix)
	 */
	@Override
	public Matrix<Field> sub(Matrix<Field> other) {
		Matrix<Field>  ret = new SparseMatrix(lines, columns, zero);
		for (int l = 0; l < lines; l++){
			for (int c  = 0; c < columns; c++)
				ret.set(l, c, get(l, c).sub(other.get(l, c)));
		}
		return ret;
	}

	/*
	 * (non-Javadoc)
	 * @see br.schindler.math.matrix.Matrix#transpose()
	 */
	@Override
	public Matrix<Field> transpose() {
		SparseMatrix ret = new SparseMatrix(columns, lines, zero);
		for (Integer k : elements.keySet()){
			int i =(k %    columns); 
			int j = k - (i*columns);
			ret.elements.put(j*lines+i, elements.get(k));
		}
		return ret;
	}

	/*
	 * (non-Javadoc)
	 * @see br.schindler.math.matrix.Matrix#get(int, int)
	 */
	@Override
	public Field get(int i, int j) {
		Field result = this.elements.get(i*columns+j); 
		return (null == result) ? zero.clone() : result;
	}

	/*
	 * (non-Javadoc)
	 * @see br.schindler.math.matrix.Matrix#set(int, int, java.lang.Object)
	 */
	@Override
	public Matrix<Field> set(int i, int j, Field elem) {		
		if (zero.equals(elem))
			elements.remove(i*columns+j);
		else
			elements.put(i*columns+j, elem.clone());
		return this;
	}
}
