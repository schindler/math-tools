package br.schindler.math.matrix.load.octave;

import br.schindler.math.matrix.Matrix;
import br.schindler.math.matrix.load.MatrixFactory;
import br.schindler.math.matrix.primitive.DoubleMatrix;

 

/**
 * 
 * @author Fernando
 *
 */
public class DoubleFactory implements MatrixFactory<Double> {

	/*
	 * 
	 */
	public DoubleFactory() {
	}

	/*
	 * (non-Javadoc)
	 * @see br.schindler.math.matrix.load.MatrixFactory#create(int, int)
	 */
	@Override
	public Matrix<Double> create(int lines, int cols) {
		//return new DoubleMatrix(new SparseMatrix(lines, cols, new DoubleField(0.0)));
		return new DoubleMatrix(lines, cols);
	}

	/*
	 * (non-Javadoc)
	 * @see br.schindler.math.matrix.load.MatrixFactory#parseField(java.lang.String)
	 */
	@Override
	public Double parseField(String value) {
		return Double.parseDouble(value);
	} 
}
