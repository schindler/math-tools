package br.com.schindler.math.matrix;

/**
 * 
 * @author Fernando
 *
 */
public interface Inversible<K, T extends OperationsSet<K>> extends Matrix<K, T> {
	
	/**
	 * 
	 * @return
	 */
	Matrix<K, T> inverse () throws SingularException;
}
