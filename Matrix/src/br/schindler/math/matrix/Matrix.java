package br.schindler.math.matrix;

import br.schindler.math.matrix.operations.Function;


/**
 * Interface padrão para objetos que implementaão operações com matrizes
 * @author Fernando
 *
 */
public interface Matrix<T> {
	/**
	 * Recupear um elemento da matrix
	 * @param index number in [0..columns*lines[
	 * @return T
	 * @see get
	 */
	T getByIndex(int index);
	
	/**
	 * 
	 * @param index
	 * @param elem
	 */
	void setByIndex(int index, T elem);
	
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
	 * 
	 * @return
	 */
	void     set(int fromLine, int toLine, int fromCol, int toCol, Matrix<T> other);
	
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
	Matrix<T> increment(T elem);
	
	/**
	 * 
	 * @param elem
	 * @return
	 */
	Matrix<T> decrement(T elem);
	
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
	Matrix<T> mul(T elem); 
	
	/**
	 * 
	 * @param other
	 * @return
	 */
	Matrix<T> mul(Matrix<T> other); 
	
	/**
	 * 
	 */
	Matrix<T> transpose(); 
	
	/**
	 * Aplicar a função em todos elementos
	 * @param func
	 * @return this matrix
	 */
	Matrix<T> call(Function<T> func);	
	
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
