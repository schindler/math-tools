package br.schindler.math.matrix.fields;

import br.schindler.math.matrix.BaseMatrix;
import br.schindler.math.matrix.Matrix;
import br.schindler.math.matrix.operations.Multiplication;

 

/**
 * @author Fernando
 *
 */
public class FullMatrix extends BaseMatrix<Field> {
	
	/**
	 * 
	 */
	protected Field [] elements;
	
	
	protected Field zero;
	
	public Multiplication<Field> mul;
	
	/**
	 * 
	 * @param lines
	 * @param columns
	 */
	public FullMatrix(int lines, int columns, Field zero) {
		super(lines, columns); 
		this.elements = new Field [lines*columns];
		this.zero     = zero;
		this.mul      = new Multiplication<Field>(zero);
		fill(zero);
		
	}
	
	/*
	 * (non-Javadoc)
	 * @see br.schindler.math.matrix.base.BaseMatrix#create(int, int, br.schindler.math.matrix.fields.Field)
	 */
	@Override
	public BaseMatrix<Field> create(int lines, int columns) {
		return new FullMatrix(lines, columns, zero);
	}
	
	/*
	 * (non-Javadoc)
	 * @see br.schindler.math.matrix.Matrix<Field>#fill(int, java.lang.Number)
	 */
	@Override
	public Matrix<Field> fill(int i, Field elem) throws IndexOutOfBoundsException {
		i *= this.columns;
		for (; i < columns; i++)
			this.elements[i] = elem.clone();
		return this;
	}
	
	/*
	 * (non-Javadoc)
	 * @see br.schindler.math.matrix.Matrix<Field>#fill(java.lang.Number)
	 */
	@Override
	public Matrix<Field> fill(Field elem) {
		if (null != elem)
			for (int i = 0; i < lines*columns; i++) {
				this.elements[i] = elem.clone();
			}	
		return this;
	}
	 	
	/*
	 * (non-Javadoc)
	 * @see br.schindler.math.matrix.Matrix<Field>#getByIndex(int)
	 */
	@Override
	public Field getByIndex(int index) {
		return this.elements[index];
	}
	
	/*
	 * (non-Javadoc)
	 * @see br.schindler.math.matrix.Matrix#setByIndex(int, java.lang.Object)
	 */
	@Override
	public void setByIndex(int index, Field elem) {
		this.elements[index] = elem.clone();			
	}
  
	/*
	 * (non-Javadoc)
	 * @see br.schindler.math.matrix.Matrix#increment(java.lang.Object)
	 */
	@Override
	public Matrix<Field> increment(Field elem) {		 
		for (int i = 0; i < elements.length; i++) {
			this.elements[i].inc(elem);
		}
		
		return this;
	}
   
	/*
	 * (non-Javadoc)
	 * @see br.schindler.math.matrix.Matrix#add(java.lang.Object)
	 */
	@Override
	public Matrix<Field> add(Field elem) {
		FullMatrix result = new FullMatrix (lines, columns, null);
		
		for (int i = 0; i < elements.length; i++) {
			result.elements[i] = elem.add(this.elements[i]);
		}
		
		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see br.schindler.math.matrix.Matrix<Field>#add(br.schindler.math.matrix.Matrix<Field>)
	 */
	@Override
	public Matrix<Field> add(Matrix<Field> other) {
		FullMatrix result = new FullMatrix (lines, columns, null);
		
		for (int i = 0; i < elements.length; i++) {
			result.elements[i] = other.getByIndex(i).add(this.elements[i]);
		}
		
		return result;
	}

    /*
     * (non-Javadoc)
     * @see br.schindler.math.matrix.Matrix#decrement(java.lang.Object)
     */
	@Override
	public Matrix<Field> decrement(Field elem) {
		FullMatrix result = this;
		
		for (int i = 0; i < elements.length; i++) {
			 result.elements[i].dec(elem);
		}
		
		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see br.schindler.math.matrix.Matrix<Field>#sub(br.schindler.math.matrix.fields.Field)
	 */
	@Override
	public Matrix<Field> sub(Field elem) {
		FullMatrix result = new FullMatrix (lines, columns, null);
		
		for (int i = 0; i < elements.length; i++) {
			result.elements[i] = elem.sub(this.elements[i]);
		}
		
		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see br.schindler.math.matrix.Matrix<Field>#sub(br.schindler.math.matrix.Matrix<Field>)
	 */
	@Override
	public Matrix<Field> sub(Matrix<Field> other) {
		FullMatrix result = new FullMatrix (lines, columns, null);
		
		for (int i = 0; i < elements.length; i++) {
			result.elements[i] = other.getByIndex(i).sub(this.elements[i]);
		}
		
		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see br.schindler.math.matrix.Matrix<Field>#scale(br.schindler.math.matrix.fields.Field)
	 */
	@Override
	public Matrix<Field> scale(Field elem) {
 
		for (int i = 0; i < elements.length; i++) {
			 elements[i] = elements[i].mul(elem);
		}
		
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see br.schindler.math.matrix.Matrix<Field>#mul(br.schindler.math.matrix.fields.Field)
	 */
	@Override
	public Matrix<Field> mul(Field elem) {
		FullMatrix result = new FullMatrix (lines, columns, null);
		
		for (int i = 0; i < lines*columns; i++) {
			result.elements[i] = elem.mul(this.elements[i]);
		}
		
		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see br.schindler.math.matrix.Matrix<Field>#mul(br.schindler.math.matrix.Matrix<Field>)
	 */
	@Override
	public Matrix<Field> mul(Matrix<Field> other) {
		
		if (columns() != other.lines())
			throw new IndexOutOfBoundsException(String.format("mul %d != %d", columns(), other.lines()));
		
		FullMatrix result = new FullMatrix (lines, other.columns(), null);
		
		for (int i = 0; i < result.elements.length; i++) { 
			result.elements[i] = mul.get(i, this, other);
		}
		
		
		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see br.schindler.math.matrix.Matrix<Field>#transpose()
	 */
	@Override
	public Matrix<Field> transpose() {
		FullMatrix result = new FullMatrix (columns, lines, null);
		
		for (int i = 0; i < lines; i++) {
			for (int j = 0; j < columns; j++)
				result.set(j, i, get(i, j).clone());
		}
		
		return null;
	}
}