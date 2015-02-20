/**
 * 
 */
package br.com.schindler.math.matrix.base;

import br.com.schindler.math.matrix.OperationsSet;

/**
 * @author Fernando
 *
 */
public final class OperationsSetDouble extends OperationsSet<Double> {
	 
	/**
	 * Por valor
	 * @param initial
	 */
	public OperationsSetDouble(Double initial) {
		super(initial);
	}
	
	/**
	 * Default
	 */
	public OperationsSetDouble() {
		super(0.0);
	}
	
	
	/*
	 * (non-Javadoc)
	 * @see br.com.schindler.math.matrix.OperationsSet#sub(java.lang.Object)
	 */
	@Override
	public Double sub(Double other) {
		return this.value-other;
	}

	/*
	 * (non-Javadoc)
	 * @see br.com.schindler.math.matrix.OperationsSet#mul(java.lang.Object)
	 */
	@Override
	public Double mul(Double other) {
		return  this.value*other;
	}

	/*
	 * (non-Javadoc)
	 * @see br.com.schindler.math.matrix.OperationsSet#div(java.lang.Object)
	 */
	@Override
	public Double div(Double other) {
		return this.value-other;
	}

	/*
	 * 
	 */
	@Override
	public OperationsSet<Double> add(OperationsSet<Double> other) {
		return new OperationsSetDouble(other.value+this.value);
	} 
}
