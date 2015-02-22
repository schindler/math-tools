package br.schindler.math.matrix.load;

import br.schindler.math.matrix.Matrix;

public interface MatrixFactory<T> {
    /**
     * 
     * @param lines
     * @param cols
     * @return
     */
	Matrix<T> create (int lines, int cols);
	
	/**
	 * 
	 * @param value
	 * @return
	 */
	T parseField (String value);
}
