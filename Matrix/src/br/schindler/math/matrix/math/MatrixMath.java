package br.schindler.math.matrix.math;

import br.schindler.math.matrix.Matrix;
import br.schindler.math.matrix.operations.Function;

public class MatrixMath {
	/**
	 * 
	 * @param matrix
	 * @return {@link Matrix}
	 */
	static Matrix<Double> sigmoid(Matrix<Double> matrix) {
		return matrix.call(new Function<Double>() {
			/*
			 * (non-Javadoc)
			 * @see br.schindler.math.matrix.operations.Function#perform(java.lang.Object)
			 */
			@Override
			public Double perform(Double val) {
				// TODO Auto-generated method stub
				return  (double) 1 / (double) (1 + Math.exp(-val));
			}
		});
	}

	/**
	 * Aplica função seno
	 * @param matrix
	 * @see {@link Math.sin}
	 * @return {@link Matrix}
	 */
	static Matrix<Double> sin(Matrix<Double> matrix) {
		return matrix.call(new Function<Double>() {
			/*
			 * (non-Javadoc)
			 * @see br.schindler.math.matrix.operations.Function#perform(java.lang.Object)
			 */
			@Override
			public Double perform(Double val) {
				return  Math.sin(val);
			}
		});
	}

	/**
	 * Aplica função cosseno
	 * @param matrix
	 * @see {@link Math.cos}
	 * @return {@link Matrix}
	 */
	static Matrix<Double> cos(Matrix<Double> matrix) {
		return matrix.call(new Function<Double>() {
			/*
			 * (non-Javadoc)
			 * @see br.schindler.math.matrix.operations.Function#perform(java.lang.Object)
			 */
			@Override
			public Double perform(Double val) {
				return  Math.cos(val);
			}
		});
	}
}
