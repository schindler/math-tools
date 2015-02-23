package br.schindler.math.matrix.operations;

/**
 * 
 * @author Fernando
 *
 * @param <T>
 */
public interface Function<T> {
	/**
	 * Aplica a função ao elemento passado
	 * @param val
	 * @return
	 */
	T perform (T val);
}
