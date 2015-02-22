/**
 * 
 */
package br.schindler.math.matrix.fields;


/**
 * @author Fernando
 *
 */
public class DoubleField implements Field {
	private Double value = 0.0;
		
	/**
	 * 
	 * @param value
	 */
	public DoubleField(){
	}
	
	/**
	 * 
	 * @return
	 */
	public Double value(){
		return value;
	}
	
	/**
	 * 
	 * @param value
	 */
	public DoubleField(Double value){
		this.value = value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.schindler.math.matrix.Field#add(br.schindler.math.matrix.Field
	 * )
	 */
	@Override
	public Field add(Field other) {
		return new DoubleField(((DoubleField)other).value  + value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.schindler.math.matrix.Field#sub(br.schindler.math.matrix.Field
	 * )
	 */
	@Override
	public Field sub(Field other) {
		return new DoubleField(value-((DoubleField)other).value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.schindler.math.matrix.Field#mul(br.schindler.math.matrix.Field
	 * )
	 */
	@Override
	public Field mul(Field other) {
		return new DoubleField(value*((DoubleField)other).value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.schindler.math.matrix.Field#div(br.schindler.math.matrix.Field
	 * )
	 */
	@Override
	public Field div(Field other) {
		return new DoubleField(value/((DoubleField)other).value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.schindler.math.matrix.Field#inc(br.schindler.math.matrix.Field
	 * )
	 */
	@Override
	public void inc(Field other) {
		value+=((DoubleField)other).value;
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return String.format("%.2f", value);
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object other) {
		return Math.abs(((DoubleField)other).value - value) < 10e-8;
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	@Override
	public Field clone()  {
		return new DoubleField(value);
	}
}
