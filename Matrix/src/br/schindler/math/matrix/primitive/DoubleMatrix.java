/**
 * 
 */
package br.schindler.math.matrix.primitive;

import java.util.Arrays;

import br.schindler.math.matrix.BaseMatrix;
import br.schindler.math.matrix.Matrix;

/**
 * Matrix para uma melhor performance com Double
 * @author Fernando
 *
 */
public class DoubleMatrix extends BaseMatrix<Double> {
	
	/**
	 * Elementos
	 */
     private double [][] elements;
     
	/**
	 * @param lines
	 * @param columns
	 * @param zero
	 */
	public DoubleMatrix(int lines, int columns) {
		super(lines, columns);
		this.elements = new double [lines][columns];
	}

	/* (non-Javadoc)
	 * @see br.schindler.math.matrix.Matrix#getByIndex(int)
	 */
	@Override
	public Double getByIndex(int index) {
		int col = index % columns;
		int lin = index / columns;
		return this.elements[lin][col];
	}

	/* (non-Javadoc)
	 * @see br.schindler.math.matrix.Matrix#setByIndex(int, java.lang.Object)
	 */
	@Override
	public void setByIndex(int index, Double elem) {
		int col = index % columns;
		int lin = index / columns;
		this.elements[lin][col]= elem;
	}
	
	/*
	 * (non-Javadoc)
	 * @see br.schindler.math.matrix.BaseMatrix#get(int, int)
	 */
	@Override
	public Double get(int i, int j) {
		return this.elements[i][j];
	}
	
	/*
	 * (non-Javadoc)
	 * @see br.schindler.math.matrix.BaseMatrix#set(int, int, java.lang.Object)
	 */
	@Override
	public void set(int i, int j, Double elem) {
		this.elements[i][j] = elem;
	}

	/* (non-Javadoc)
	 * @see br.schindler.math.matrix.Matrix#fill(java.lang.Object)
	 */
	@Override
	public Matrix<Double> fill(Double elem) {
		for (double [] line : this.elements)
			Arrays.fill(line, elem);
		return this;
	}

	/* (non-Javadoc)
	 * @see br.schindler.math.matrix.Matrix#fill(int, java.lang.Object)
	 */
	@Override
	public Matrix<Double> fill(int i, Double elem) {
		Arrays.fill(this.elements[i], elem);
		return this;
	}

	/* (non-Javadoc)
	 * @see br.schindler.math.matrix.Matrix#increment(java.lang.Object)
	 */
	@Override
	public Matrix<Double> increment(Double elem) {
		for (int i =0; i < lines; i++) {
			double [] r1 = elements[i];
			for (int j = 0; j < columns; j++) {
				r1[j]+=elem;
			}
		}
		return this;
	}

	/* (non-Javadoc)
	 * @see br.schindler.math.matrix.Matrix#decrement(java.lang.Object)
	 */
	@Override
	public Matrix<Double> decrement(Double elem) {
		for (int i =0; i < lines; i++) {
			double [] r1 = elements[i];
			for (int j = 0; j < columns; j++) {
				r1[j]-=elem;
			}
		}
		return this;
	}

	/* (non-Javadoc)
	 * @see br.schindler.math.matrix.Matrix#add(java.lang.Object)
	 */
	@Override
	public Matrix<Double> add(Double elem) {
		DoubleMatrix result = new DoubleMatrix(lines, columns);
		for (int i =0 ; i < lines; i++) {
			double [] r1 = elements[i];
			for (int j = 0; j < columns; j++) {
				result.elements[j][i] = r1[j]+elem;
			}
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see br.schindler.math.matrix.Matrix#add(br.schindler.math.matrix.Matrix)
	 */
	@Override
	public Matrix<Double> add(Matrix<Double> other) {
		if (lines() != other.lines() || columns() != other.columns()) 
			 throw new IndexOutOfBoundsException();		
		DoubleMatrix result = new DoubleMatrix(lines, columns);
		for (int i =0 ; i < lines; i++) {
			double [] r1 = elements[i];
			for (int j = 0; j < columns; j++) {
				result.elements[j][i] = r1[j]+other.get(i, j);
			}
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see br.schindler.math.matrix.Matrix#sub(java.lang.Object)
	 */
	@Override
	public Matrix<Double> sub(Double elem) {
		DoubleMatrix result = new DoubleMatrix(lines, columns);
		for (int i =0 ; i < lines; i++) {
			double [] r1 = elements[i];
			for (int j = 0; j < columns; j++) {
				result.elements[j][i] = r1[j]-elem;
			}
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see br.schindler.math.matrix.Matrix#sub(br.schindler.math.matrix.Matrix)
	 */
	@Override
	public Matrix<Double> sub(Matrix<Double> other) {
		if (lines() != other.lines() || columns() != other.columns()) 
			 throw new IndexOutOfBoundsException();		
		DoubleMatrix result = new DoubleMatrix(lines, columns);
		for (int i =0 ; i < lines; i++) {
			double [] r1 = elements[i];
			for (int j = 0; j < columns; j++) {
				result.elements[j][i] = r1[j]*other.get(i, j);
			}
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see br.schindler.math.matrix.Matrix#scale(java.lang.Object)
	 */
	@Override
	public Matrix<Double> scale(Double elem) {
		for (int i =0; i < lines; i++) {
			double [] r1 = elements[i];
			for (int j = 0; j < columns; j++) {
				r1[j]*=elem;
			}
		}
		return this;
	}

	/* (non-Javadoc)
	 * @see br.schindler.math.matrix.Matrix#mul(java.lang.Object)
	 */
	@Override
	public Matrix<Double> mul(Double elem) {
		DoubleMatrix result = new DoubleMatrix(lines, columns);
		for (int i =0 ; i < lines; i++) {
			double [] r1 = elements[i];
			for (int j = 0; j < columns; j++) {
				result.elements[j][i] = r1[j]*elem;
			}
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see br.schindler.math.matrix.Matrix#mul(br.schindler.math.matrix.Matrix)
	 */
	@Override
	public Matrix<Double> mul(Matrix<Double> other) {
		if (columns() != other.lines())
			throw new IndexOutOfBoundsException();
		
		DoubleMatrix result = new DoubleMatrix(lines, other.columns());
		double[][] p2       =  ((DoubleMatrix) other).elements;		
		
		int aCols      = columns; 
		int bCols      = other.columns();
		double[][] rst = result.elements;
		double[] bcj   = new double[aCols];

		for (int j = 0; j < bCols; j++) {
			for (int k = 0; k < aCols; k++) {
				bcj[k] = p2[k][j];
			}

			for (int i = 0; i < lines; i++) {
				double[] ari = elements[i];
				double     s = 0;

				for (int k = 0; k < aCols; k++) {
					s += ari[k] * bcj[k];
				}
				rst[i][j] = s;
			}
		}
 
		return result;
	}

	/* (non-Javadoc)
	 * @see br.schindler.math.matrix.Matrix#transpose()
	 */
	@Override
	public Matrix<Double> transpose() {
		DoubleMatrix result = new DoubleMatrix(columns, lines);
		for (int i =0 ; i < lines; i++) {
			double [] r1 = elements[i];
			for (int j = 0; j < columns; j++) {
				result.elements[j][i] = r1[j];
			}
		}
		return result;
	}
	
	/*
	 * (non-Javadoc)
	 * @see br.schindler.math.matrix.BaseMatrix#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof DoubleMatrix){
			DoubleMatrix other = (DoubleMatrix) obj;
			
			if (lines() != other.lines() || columns() != other.columns()) 
				return false;			

			for (int i =0 ; i < lines; i++) {
				double [] r1 = elements[i];
				double [] r2 = other.elements[i];
				for (int j = 0; j < columns; j++) {
					if (Math.abs(r1[j]-r2[j]) > 10e-8) {
						return false;
					}
				}
			}			
			return true;
		}
		
		return false;
	}

	/* (non-Javadoc)
	 * @see br.schindler.math.matrix.BaseMatrix#create(int, int)
	 */
	@Override
	public BaseMatrix<Double> create(int lines, int columns) {
		return new DoubleMatrix(lines, columns);
	}
}
