package br.schindler.math.matrix.fields;

public interface Field  {
	/**
	 * 
	 * @param other
	 * @return
	 */
	Field add(Field other);
	
	/**
	 * 
	 * @param other
	 * @return
	 */
	Field sub(Field other);
	
    /**
     * 
     * @param other
     * @return
     */
	Field mul(Field other);
	
    /**
     * 
     * @param other
     * @return
     */
	Field div(Field other);
	
	/**
	 * 
	 * @param other
	 */
	void inc(Field other); 
	
	/**
	 * 
	 * @param other
	 */
	void dec(Field other); 
	
	/**
	 * 
	 * @param other
	 */
	void scale(Field other);
	
	/**
	 * @return -1 * this
	 */
	Field neg();
	
	/**
	 * 
	 * @return
	 */
	Field clone();
	
	/**
	 * 
	 * @return
	 */
	Object value();
}
