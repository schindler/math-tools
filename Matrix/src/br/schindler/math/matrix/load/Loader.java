package br.schindler.math.matrix.load;

import java.io.BufferedReader;

import br.schindler.math.matrix.Matrix;

public interface Loader<N> {
	/**
	 * 
	 * @return
	 */
	Matrix<N> load (BufferedReader input);
}
