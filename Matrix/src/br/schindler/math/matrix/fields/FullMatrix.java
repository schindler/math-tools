package br.schindler.math.matrix.fields;

import br.schindler.math.matrix.BaseMatrix;
import br.schindler.math.matrix.Matrix;
import br.schindler.math.matrix.operations.Function;

 

/**
 * Implementação de uma full matrix para fields genericos
 * @author fernando.schindler@gmail.com
 */
public class FullMatrix extends BaseMatrix<Field> {
	
	/**
	 * 
	 */
	protected Field [][] elements;
	
	
	protected Field zero;
	 
	
	/**
	 * 
	 * @param lines
	 * @param columns
	 */
	public FullMatrix(int lines, int columns, Field zero) {
		super(lines, columns); 
		this.elements = new Field [lines][columns];
		this.zero     = zero;
		fill(zero);	
	}
	
	/*
	 * (non-Javadoc)
	 * @see br.schindler.math.matrix.Matrix#get(int, int)
	 */
	@Override
	public Field get(int i, int j) {
		return this.elements [i][j];
	}
	
	/*
	 * (non-Javadoc)
	 * @see br.schindler.math.matrix.Matrix#set(int, int, java.lang.Object)
	 */
	@Override
	public Matrix<Field> set(int i, int j, Field elem) {
		this.elements [i][j] = elem;
		return this;
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
		Field [] row = this.elements[i];
		for (int j = 0; j < columns; j++)
			row[j] = elem.clone();
		return this;
	}
	
	/*
	 * (non-Javadoc)
	 * @see br.schindler.math.matrix.Matrix<Field>#fill(java.lang.Number)
	 */
	@Override
	public Matrix<Field> fill(Field elem) {
		for (int i =0; i < lines; i++)
			fill(i, elem);
		return this;
	}
	
	/*
	 * (non-Javadoc)
	 * @see br.schindler.math.matrix.BaseMatrix#call(br.schindler.math.matrix.operations.Function)
	 */
	@Override
	public Matrix<Field> call(Function<Field> func) {
		for (int i = 0; i < lines; i++){
			Field [] row = elements[i];
			for (int j = 0; j < columns; j++){
				row[j] = func.perform(row[j]);
			}
		}
		return this;
	}
  
	/*
	 * (non-Javadoc)
	 * @see br.schindler.math.matrix.Matrix#increment(java.lang.Object)
	 */
	@Override
	public Matrix<Field> increment(final Field elem) {		
		return call(new Function<Field>() {
			/*
			 * (non-Javadoc)
			 * @see br.schindler.math.matrix.operations.Function#perform(java.lang.Object)
			 */
			@Override
			public Field perform(Field val) {
				val.inc(elem);
				return val;
			}
		}); 
	}
   
	/*
	 * (non-Javadoc)
	 * @see br.schindler.math.matrix.Matrix#add(java.lang.Object)
	 */
	@Override
	public Matrix<Field> add(Field elem) {
		FullMatrix result = new FullMatrix (lines, columns, null);
		
		for (int i = 0; i < lines(); i++) {
			for (int j = 0; j < columns(); j++) 
				result.elements[i][j] = elem.add(this.elements[i][j]);
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
		
		for (int i = 0; i < lines(); i++) {
			for (int j = 0; j < columns(); j++) 
				result.elements[i][j] = other.get(i,j).add(this.elements[i][j]);
		}
		
		return result;
	}

    /*
     * (non-Javadoc)
     * @see br.schindler.math.matrix.Matrix#decrement(java.lang.Object)
     */
	@Override
	public Matrix<Field> decrement(Field elem) {
		for (int i = 0; i < lines(); i++) {
			for (int j = 0; j < columns(); j++) 
				this.elements[i][j].dec(elem);
		}
		
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see br.schindler.math.matrix.Matrix<Field>#sub(br.schindler.math.matrix.fields.Field)
	 */
	@Override
	public Matrix<Field> sub(final Field elem) {
		FullMatrix result = new FullMatrix (lines, columns, null);
		return result.call(new Function<Field>() {
			@Override
			public Field perform(Field val) {
				return val.sub(elem);
			}
		});	 
	}

	/*
	 * (non-Javadoc)
	 * @see br.schindler.math.matrix.Matrix<Field>#sub(br.schindler.math.matrix.Matrix<Field>)
	 */
	@Override
	public Matrix<Field> sub(final Matrix<Field> other) {
		FullMatrix result = new FullMatrix (lines, columns, null);		
		for (int i = 0; i < lines(); i++) {
			Field rowr [] = result.elements[i];
			Field row  [] = elements[i];			
			for (int j = 0; j < columns(); j++) 
				rowr[j] = other.get(i,j).sub(row[j]);
		}		
		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see br.schindler.math.matrix.Matrix<Field>#scale(br.schindler.math.matrix.fields.Field)
	 */
	@Override
	public Matrix<Field> scale(Field elem) {
 
		for (int i = 0; i < lines(); i++) {
			for (int j = 0; j < columns(); j++) 
				elements[i][j].scale(elem);
		}
		
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see br.schindler.math.matrix.Matrix<Field>#mul(br.schindler.math.matrix.fields.Field)
	 */
	@Override
	public Matrix<Field> mul(final Field elem) {
		FullMatrix result = new FullMatrix (lines, columns, null);
		return result.call(new Function<Field>() {
			@Override
			public Field perform(Field val) {
				return elem.mul(val);
			}
		});	 
	}
	
	/*
	 * (non-Javadoc)
	 * @see br.schindler.math.matrix.Matrix#mul(br.schindler.math.matrix.Matrix)
	 */
	@Override
	public Matrix<Field> mul(Matrix<Field> other) {
		
		if (columns() != other.lines())
			throw new IndexOutOfBoundsException("mul columns() has to be equal other.lines()");
		
		FullMatrix result = new FullMatrix(lines, other.columns(), null);		
		int aCols         = columns; 
		int bCols         = other.columns();
		Field[][] rst     = result.elements;
		Field[] bcj       = new Field[aCols];

		for (int j = 0; j < bCols; j++) {
			for (int k = 0; k < aCols; k++) {
				bcj[k] = other.get(k, j);
			}
			for (int i = 0; i < lines; i++) {
				Field[] ari = elements[i];
				Field     s = zero.clone();
				for (int k = 0; k < aCols; k++) {
					s.inc(ari[k].mul(bcj[k]));
				}
				rst[i][j] = s;
			}
		} 
		return result;
	}
 
}