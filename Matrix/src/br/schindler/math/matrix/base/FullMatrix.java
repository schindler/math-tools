package br.schindler.math.matrix.base;

import br.schindler.math.matrix.Matrix;
import br.schindler.math.matrix.fields.Field;

 

/**
 * @author Fernando
 *
 */
public class FullMatrix implements Matrix<Field>{

	/**
	 * 
	 */
	private int lines, columns;
	
	/*
	 * 
	 */
	private Field zero;
	
	/**
	 * 
	 */
	protected Field [] elements;
	
	
	/**
	 * 
	 * @param lines
	 * @param columns
	 */
	public FullMatrix(int lines, int columns, Field zero) {
		this.lines    = lines;
		this.columns  = columns;
		this.elements = new Field [lines*columns];
		this.zero     = zero;
		fill(zero);
	}
	
	/*
	 * (non-Javadoc)
	 * @see br.schindler.math.matrix.Matrix<Field>#get(int, int)
	 */
	@Override
	public Field get(int i, int j) throws IndexOutOfBoundsException {
		return this.elements[i*this.columns+j];
	}
	
	/*
	 * (non-Javadoc)
	 * @see br.schindler.math.matrix.Matrix<Field>#set(int, int, java.lang.Number)
	 */
	@Override
	public void set(int i, int j, Field elem) throws IndexOutOfBoundsException {
		this.elements[i*this.columns+j] = elem;	
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
	 * @see br.schindler.math.matrix.Matrix<Field>#getByIndex(int)
	 */
	@Override
	public Field getByIndex(int index) {
		return this.elements[index];
	}

	/*
	 * (non-Javadoc)
	 * @see br.schindler.math.matrix.Matrix<Field>#get(int, int, int, int)
	 */
	@Override
	public Matrix<Field> get(int fromLine, int toLine, int fromCol, int toCol) {
		throw new UnsupportedOperationException();
	}

	/*
	 * (non-Javadoc)
	 * @see br.schindler.math.matrix.Matrix<Field>#addScalar(br.schindler.math.matrix.fields.Field)
	 */
	@Override
	public Matrix<Field> addScalar(Field elem) {
		 
		for (int i = 0; i < lines*columns; i++) {
			this.elements[i].inc(elem);
		}
		
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see br.schindler.math.matrix.Matrix<Field>#add(br.schindler.math.matrix.fields.Field)
	 */
	@Override
	public Matrix<Field> add(Field elem) {
		FullMatrix result = new FullMatrix (lines, columns, null);
		
		for (int i = 0; i < lines*columns; i++) {
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
		
		for (int i = 0; i < lines*columns; i++) {
			result.elements[i] = other.getByIndex(i).add(this.elements[i]);
		}
		
		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see br.schindler.math.matrix.Matrix<Field>#subScalar(br.schindler.math.matrix.fields.Field)
	 */
	@Override
	public Matrix<Field> subScalar(Field elem) {
		FullMatrix result = this;
		
		for (int i = 0; i < lines*columns; i++) {
			result.elements[i] = result.elements[i].sub(elem);
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
		
		for (int i = 0; i < lines*columns; i++) {
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
		
		for (int i = 0; i < lines*columns; i++) {
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
 
		for (int i = 0; i < lines*columns; i++) {
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
			int col = i % other.columns();
			int lin = i / other.columns();
			int col1= lin * this.columns(); 
			
			Field ret = (Field) zero.clone();
			
			for (int j = 0; j < this.columns(); j++){
				//TODO: Can I do it better?
				Field val =  this.getByIndex(col1+j);
				
				if (null != val)
					ret.inc(other.get(j, col).mul(val));
			}	
			
			result.elements[i] = ret;
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
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		
		if (!(obj instanceof FullMatrix)) return false;
		
		FullMatrix other = (FullMatrix) obj;
		
		if (lines != other.lines || columns != other.columns) return false;
		
		for (int i = 0; i < lines*columns; i++) {
			 if (!elements[i].equals(other.elements[i])) return false;
		}
		return true;
	}
}