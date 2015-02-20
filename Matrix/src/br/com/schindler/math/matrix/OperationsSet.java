package br.com.schindler.math.matrix;

public abstract class OperationsSet<K> {
	
	/**
	 * Valor atual
	 */
	public K value;
	
	/**
	 * 
	 * @param initial
	 */
	public OperationsSet(K initial){
		value = initial;
	}
 
	/**
	 * 
	 * @param other
	 * @return
	 */
	public abstract OperationsSet<K> add(OperationsSet<K> other);
	
	/**
	 * 
	 * @param other
	 * @return
	 */
	public abstract K sub(K other);
	
    /**
     * 
     * @param other
     * @return
     */
	public abstract K mul(K other);
	
    /**
     * 
     * @param other
     * @return
     */
	public abstract K div(K other);
}
