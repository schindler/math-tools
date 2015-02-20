package br.com.schindler.math.matrix;

/**
 * Interface padrão para objetos que implementaão operações com matrizes
 * @author Fernando
 *
 */
public interface Matrix<K, T extends OperationsSet<K>> {
	/**
	 * 
	 * @param index
	 * @return
	 */
	T getByIndex(int index);
	
	/**
	 * Recuperar um elemento da matrix
	 * @param i Linha sendo 0 o primeiro elemento
	 * @param j Coluna sendo 0 o primeiro elemento 
	 * @return The matrix element
	 * @see set
	 */
	T get(int i, int j) throws IndexOutOfBoundsException;
	
	/**
	 * Ajustar um elemento da matrix
	 * @param i
	 * @param j
	 * @param elem
	 * @throws IndexOutOfBoundsException
	 */
	void set(int i, int j, T elem) throws IndexOutOfBoundsException;
		
	/**
	 * 
	 * @param i
	 * @return
	 */
	T [] row(int i);
	
	/**
	 * 
	 * @param elem
	 */
	void fill(T elem);
	
	/**
	 * 
	 * @param i
	 * @param elem
	 */
	void fill(int i, T elem) throws IndexOutOfBoundsException;
	
	/**
	 * 
	 * @param elem
	 * @return
	 */
	Matrix<K, T> addScalar(T elem);
	
	/**
	 * 
	 * @param elem
	 * @return
	 */
	Matrix<K, T> add(T elem);
	
	/**
	 * 
	 * @param other
	 * @return
	 */
	Matrix<K, T> add(Matrix<K, T> other);
	
	/**
	 * 
	 * @param elem
	 * @return
	 */
	void subScalar(T elem);
	
	/**
	 * 
	 * @param elem
	 * @return
	 */
	Matrix<K, T> sub(T elem);
	
	/**
	 * 
	 * @param other
	 * @return
	 */
	Matrix<K, T> sub(Matrix<K, T> other);	
	
	/**
	 * 
	 * @param elem
	 */
	Matrix<K, T> scale(T elem);
	
	/**
	 * 
	 * @param other
	 * @return
	 */
	Matrix<K, T> mul (T elem); 
	
	/**
	 * 
	 * @param other
	 * @return
	 */
	Matrix<K, T> mul (Matrix<K, T> other) throws IndexOutOfBoundsException; 
	
	/**
	 * 
	 */
	Matrix<K, T> transpose(); 
	
	/**
	 * 
	 * @return
	 */
	int lines();
	
	/**
	 * 
	 * @return
	 */
	int columns();
}
