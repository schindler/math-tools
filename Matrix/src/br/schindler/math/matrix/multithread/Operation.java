package br.schindler.math.matrix.multithread;

import br.schindler.math.matrix.fields.Field;

/**
 * 
 * @author Fernando
 *
 */
public interface Operation<N extends Field> {

	/**
	 * Retorna o novo valor do index i
	 * @param i
	 * @return valor do elemento i na nova matrix
	 */
	N get(int i);
}
