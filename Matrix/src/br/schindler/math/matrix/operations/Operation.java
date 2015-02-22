package br.schindler.math.matrix.operations;



/**
 * 
 * @author Fernando
 *
 */
public interface Operation<T> {

    /**
     * Recuperar o elemento line, col aplicada essa operação
     * @param line
     * @param col
     * @param params
     * @return Elemento T
     */
	T get(int line, int col, Object... params);
	
    /**
     * Recuperar o elemento line, col aplicada essa operação
     * @param line
     * @param params
     * @return Elemento T
     */
	T get(int index, Object... params);
}
