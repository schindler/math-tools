package br.schindler.math.matrix;

import br.schindler.math.matrix.error.SingularException;

/**
 * 
 * @author Fernando
 *
 */
public interface Inversible<T> extends Matrix<T> {
	
	/**
	 * 
	 * @return
	 */
	Matrix<T> inverse () throws SingularException;
}
