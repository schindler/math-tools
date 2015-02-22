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
	 * @return
	 */
	Field clone();
	
	/**
	 * 
	 * @return
	 */
	Object value();
}