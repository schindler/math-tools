/**
 * 
 */
package br.schindler.math.matrix.fields.binary;

import br.schindler.math.matrix.fields.Field;
import br.schindler.math.matrix.fields.real.DoubleField;

/**
 * @author Fernando
 */
public class BinaryField implements Field {
	private Short value = 0;
	
	public static Field ONE = new BinaryField((short)1);
	public static Field ZRO = new BinaryField((short)0);
	
	/**
	 * 
	 */
	public BinaryField() {
	}

	/**
	 * 
	 */
	public BinaryField(Short value) {
		this.value = value;
	}

	/* (non-Javadoc)
	 * @see br.schindler.math.matrix.fields.Field#add(br.schindler.math.matrix.fields.Field)
	 */
	@Override
	public Field add(Field other) {
		return new BinaryField(check(other) == 0 ? 0 : value);
	}

	/* (non-Javadoc)
	 * @see br.schindler.math.matrix.fields.Field#sub(br.schindler.math.matrix.fields.Field)
	 */
	@Override
	public Field sub(Field other) {
		return add(other);
	}

	/* (non-Javadoc)
	 * @see br.schindler.math.matrix.fields.Field#mul(br.schindler.math.matrix.fields.Field)
	 */
	@Override
	public Field mul(Field other) {
		return new BinaryField(check(other) == 0 ? value : 0);
	}

	/* (non-Javadoc)
	 * @see br.schindler.math.matrix.fields.Field#div(br.schindler.math.matrix.fields.Field)
	 */
	@Override
	public Field div(Field other) {
		if (0 == check(other))
			throw new ArithmeticException();
        return clone();
	}

	/* (non-Javadoc)
	 * @see br.schindler.math.matrix.fields.Field#inc(br.schindler.math.matrix.fields.Field)
	 */
	@Override
	public void inc(Field other) {
		if (check(other) != 0)
			value = (short) ((value == 0) ? 1 : 0);
	}

	/* (non-Javadoc)
	 * @see br.schindler.math.matrix.fields.Field#dec(br.schindler.math.matrix.fields.Field)
	 */
	@Override
	public void dec(Field other) {
		inc(other);
	}

	/* (non-Javadoc)
	 * @see br.schindler.math.matrix.fields.Field#scale(br.schindler.math.matrix.fields.Field)
	 */
	@Override
	public void scale(Field other) {
		
	}

	/* (non-Javadoc)
	 * @see br.schindler.math.matrix.fields.Field#neg()
	 */
	@Override
	public Field neg() {
		return add(ONE);
	}

	/* (non-Javadoc)
	 * @see br.schindler.math.matrix.fields.Field#clone()
	 */
	@Override
	public Field clone() {
		return new BinaryField(value);
	}

	/* (non-Javadoc)
	 * @see br.schindler.math.matrix.fields.Field#value()
	 */
	@Override
	public Short value() {
		return (value);
	}
	
	/**
	 * 
	 * @param other
	 * @return Short convertido conforme other
	 */
	private Short check(Field other) {
		
		if (other  instanceof BinaryField)
			return ((BinaryField)other).value;
		
		if (other.value() instanceof Double)
			return (short) (((Double)other.value()) > 0 ? 1 : 0);
		
		throw new RuntimeException("Invalid type");
	}
}
