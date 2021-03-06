/**
 * 
 */
package br.schindler.math.matrix.fields.real;

import br.schindler.math.matrix.Matrix;
import br.schindler.math.matrix.fields.Field;
import br.schindler.math.matrix.operations.Function;

/**
 * @author Fernando
 *
 */
public class DoubleMatrix implements Matrix<Double> {
	
	/*
	 * 
	 */
	private Matrix<Field> matrix;
	
	/**
	 * 
	 * @param m
	 */
	public DoubleMatrix(Matrix<Field> m) {
		this.matrix =  m;
	}
 
	/*
	 * (non-Javadoc)
	 * @see br.schindler.math.matrix.Matrix#get(int, int)
	 */
	@Override
	public Double get(int i, int j) {
		return (Double) this.matrix.get(i, j).value();
	}
 
	/*
	 * (non-Javadoc)
	 * @see br.schindler.math.matrix.Matrix#get(int, int, int, int)
	 */
	@Override
	public Matrix<Double> get(int fromLine, int toLine, int fromCol, int toCol) {
		return new DoubleMatrix(this.matrix.get(fromLine, toLine, fromCol, toCol));
	}
	
	/*
	 * (non-Javadoc)
	 * @see br.schindler.math.matrix.Matrix#getCol(int)
	 */
	@Override
	public Double[] getCol(int col) {
		Field [] c = this.matrix.getCol(col);
		Double[] r = new Double[c.length];	
		int i      = 0;
		for (Field f : c) r[i++] = (Double) f.value();
		return r;
	}
	
	/*
	 * (non-Javadoc)
	 * @see br.schindler.math.matrix.Matrix#getRow(int)
	 */
	@Override
	public Double[] getRow(int line) {
		Field [] c = this.matrix.getRow(line);
		Double[] r = new Double[c.length];	
		int i      = 0;
		for (Field f : c) r[i++] = (Double) f.value();
		return r;
	}
	
	/*
	 * (non-Javadoc)
	 * @see br.schindler.math.matrix.Matrix#set(int, int, int, int, br.schindler.math.matrix.Matrix)
	 */
	@Override
	public Matrix<Double> set(int fromLine, int toLine, int fromCol, int toCol, Matrix<Double> other) {
		this.matrix.set(fromLine, toLine, fromCol, toCol, ((DoubleMatrix)other).matrix);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see br.schindler.math.matrix.Matrix#set(int, int, java.lang.Object)
	 */
	@Override
	public Matrix<Double> set(int i, int j, Double elem) { 
		this.matrix.set(i, j, new DoubleField(elem));
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see br.schindler.math.matrix.Matrix#fill(java.lang.Object)
	 */
	@Override
	public Matrix<Double> fill(Double elem) {
		return new DoubleMatrix(this.matrix.fill(new DoubleField(elem)));		
	}

	/*
	 * (non-Javadoc)
	 * @see br.schindler.math.matrix.Matrix#fill(int, java.lang.Object)
	 */
	@Override
	public  Matrix<Double> fill(int i, Double elem) {
		return new DoubleMatrix(this.matrix.fill(i, new DoubleField(elem)));		
	}

	/*
	 * (non-Javadoc)
	 * @see br.schindler.math.matrix.Matrix#addScalar(java.lang.Object)
	 */
	@Override
	public Matrix<Double> increment(Double elem) {
		this.matrix.increment(new DoubleField(elem));
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see br.schindler.math.matrix.Matrix#add(java.lang.Object)
	 */
	@Override
	public Matrix<Double> add(Double elem) {
		return new DoubleMatrix(this.matrix.add(new DoubleField(elem)));
	}

	/*
	 * (non-Javadoc)
	 * @see br.schindler.math.matrix.Matrix#add(br.schindler.math.matrix.Matrix)
	 */
	@Override
	public Matrix<Double> add(Matrix<Double> other) {
		return new DoubleMatrix(this.matrix.add(((DoubleMatrix)other).matrix));
	}

	/*
	 * (non-Javadoc)
	 * @see br.schindler.math.matrix.Matrix#subScalar(java.lang.Object)
	 */
	@Override
	public Matrix<Double> decrement(Double elem) {
		this.matrix.decrement(new DoubleField(elem));
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see br.schindler.math.matrix.Matrix#sub(java.lang.Object)
	 */
	@Override
	public Matrix<Double> sub(Double elem) {
		return new DoubleMatrix(this.matrix.sub(new DoubleField(elem)));
	}

	/*
	 * (non-Javadoc)
	 * @see br.schindler.math.matrix.Matrix#sub(br.schindler.math.matrix.Matrix)
	 */
	@Override
	public Matrix<Double> sub(Matrix<Double> other) {
		return new DoubleMatrix(this.matrix.sub(((DoubleMatrix)other).matrix));
	}

	/*
	 * (non-Javadoc)
	 * @see br.schindler.math.matrix.Matrix#scale(java.lang.Object)
	 */
	@Override
	public Matrix<Double> scale(Double elem) {
		this.matrix.scale(new DoubleField(elem));
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see br.schindler.math.matrix.Matrix#mul(java.lang.Object)
	 */
	@Override
	public Matrix<Double> mul(Double elem) {
		return new DoubleMatrix(this.matrix.mul(new DoubleField(elem)));
	}

	/*
	 * (non-Javadoc)
	 * @see br.schindler.math.matrix.Matrix#mul(br.schindler.math.matrix.Matrix)
	 */
	@Override
	public Matrix<Double> mul(Matrix<Double> other) {
		return new DoubleMatrix(this.matrix.mul(((DoubleMatrix)other).matrix));
	}

	/*
	 * (non-Javadoc)
	 * @see br.schindler.math.matrix.Matrix#transpose()
	 */
	@Override
	public Matrix<Double> transpose() {
		return new DoubleMatrix(this.matrix.transpose());
	}

	/*
	 * (non-Javadoc)
	 * @see br.schindler.math.matrix.Matrix#lines()
	 */
	@Override
	public int lines() {
		return this.matrix.lines();
	}

	/*
	 * (non-Javadoc)
	 * @see br.schindler.math.matrix.Matrix#columns()
	 */
	@Override
	public int columns() {
		return this.matrix.columns();
	} 
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return this.matrix.toString();
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof DoubleMatrix) 
			return this.matrix.equals(((DoubleMatrix)obj).matrix);
		else
			return false;
	}
 
	/*
	 * (non-Javadoc)
	 * @see br.schindler.math.matrix.Matrix#call(br.schindler.math.matrix.operations.Function)
	 */
	@Override
	public Matrix<Double> call(final Function<Double> func) {
		return new DoubleMatrix(this.matrix.call(new Function<Field>() {
			@Override
			public Field perform(Field val) {
				return new DoubleField(func.perform((Double)val.value()));
			}
		}));
	}

	@Override
	public Matrix<Double> set(Iterable<Integer> lines,
			Iterable<Integer> columns, Matrix<Double> other) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Matrix<Double> call(Iterable<Integer> lines,
			Iterable<Integer> columns, Function<Double> func) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see br.schindler.math.matrix.Matrix#create(int, int)
	 */
	@Override
	public Matrix<Double> create(int lines, int columns) {
		return new DoubleMatrix(this.matrix.create(lines, columns));
	}
}
