package br.com.schindler.math.matrix;
/**
 * 
 * @author Fernando
 *
 */
public interface Operation<K, T extends OperationsSet<K>> {

	/**
	 * Retorna o novo valor do index i
	 * @param i
	 * @return valor do elemento i na nova matrix
	 */
	T get(int i);
}
