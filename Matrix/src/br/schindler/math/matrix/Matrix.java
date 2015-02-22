package br.schindler.math.matrix;


/**
 * Interface padrão para objetos que implementaão operações com matrizes
 * @author Fernando
 *
 */
public interface Matrix<T> {
	/**
	 * 
	 * @param index
	 * @return
	 */
	T getByIndex(int index);
	
	/**
	 * Recuperar um elemento da Matrix<T>
	 * @param i Linha sendo 0 o primeiro elemento
	 * @param j Coluna sendo 0 o primeiro elemento 
	 * @return The Matrix<T> element
	 * @see set
	 */
	T get(int i, int j);
	
	/**
	 * 
	 * @return
	 */
	Matrix<T> get(int fromLine, int toLine, int fromCol, int toCol);
	
	/**
	 * Ajustar um elemento da Matrix<T>
	 * @param i
	 * @param j
	 * @param elem
	 */
	void set(int i, int j, T elem);
	
	/**
	 * 
	 * @param elem
	 */
	Matrix<T> fill(T elem);
	
	/**
	 * 
	 * @param i
	 * @param elem
	 */
	Matrix<T> fill(int i, T elem);
	
	/**
	 * 
	 * @param elem
	 * @return
	 */
	Matrix<T> addScalar(T elem);
	
	/**
	 * 
	 * @param elem
	 * @return
	 */
	Matrix<T> add(T elem);
	
	/**
	 * 
	 * @param other
	 * @return
	 */
	Matrix<T> add(Matrix<T> other);
	
	/**
	 * 
	 * @param elem
	 * @return
	 */
	Matrix<T> subScalar(T elem);
	
	/**
	 * 
	 * @param elem
	 * @return
	 */
	Matrix<T> sub(T elem);
	
	/**
	 * 
	 * @param other
	 * @return
	 */
	Matrix<T> sub(Matrix<T> other);	
	
	/**
	 * 
	 * @param elem
	 */
	Matrix<T> scale(T elem);
	
	/**
	 * 
	 * @param other
	 * @return
	 */
	Matrix<T> mul (T elem); 
	
	/**
	 * 
	 * @param other
	 * @return
	 */
	Matrix<T> mul (Matrix<T> other); 
	
	/**
	 * 
	 */
	Matrix<T> transpose(); 
	
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
