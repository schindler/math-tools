package br.com.schindler.math.matrix.base;

import br.com.schindler.math.matrix.Matrix;
import br.com.schindler.math.matrix.Operation;

/*
 * 
 */
public final class GMatrix extends MultiThreadMatrix<Double> {
	/**
	 * 
	 * @param lines
	 * @param columns
	 */
	public GMatrix(int lines, int columns) {
		super(lines, columns);
	}
	
	/*
	 * (non-Javadoc)
	 * @see br.com.schindler.math.matrix.Matrix#getByIndex(int)
	 */
	@Override
	public Double getByIndex(int index) {
		return this.elements[index];
	}

	/*
	 * (non-Javadoc)
	 * @see br.com.schindler.math.matrix.Matrix#addIScalar(java.lang.Number)
	 */
	@Override
	public synchronized Matrix<Double> addScalar(final Double elem) {
		try {
		      return perform(new Operation<Double>() {
				/*
				 * 
				 */
				@Override
				public Double get(int i) {
					return GMatrix.this.elements[i]+elem;
				}
			});
		} catch (Exception e) {
			throw new UnsupportedOperationException(e);
		}		
	}

	/*
	 * (non-Javadoc)
	 * @see br.com.schindler.math.matrix.Matrix#add(java.lang.Number)
	 */
	@Override
	public synchronized Matrix<Double> add(final Double elem) {
		try {
			return new GMatrix(lines(), columns()).perform(new Operation<Double>() {
				/*
				 * 
				 */
				@Override
				public Double get(int i) {
					return GMatrix.this.elements[i]+elem;
				}
			});
		} catch (Exception e) {
			throw new UnsupportedOperationException(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see br.com.schindler.math.matrix.Matrix#add(br.com.schindler.math.matrix.Matrix)
	 */
	@Override
	public synchronized Matrix<Double> add(final Matrix<Double> other) {
		try {
 
			return new GMatrix(lines(), columns()).perform(new Operation<Double>() {
				/*
				 * 
				 */
				@Override
				public Double get(int i) {
					return GMatrix.this.elements[i]+other.getByIndex(i);
				}
			});
		} catch (Exception e) {
			throw new UnsupportedOperationException(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see br.com.schindler.math.matrix.Matrix#subScalar(java.lang.Number)
	 */
	@Override
	public void subScalar(Double elem) {
		addScalar(-elem);
	}

	/*
	 * (non-Javadoc)
	 * @see br.com.schindler.math.matrix.Matrix#sub(java.lang.Number)
	 */
	@Override
	public Matrix<Double> sub(Double elem) {
		return add(-elem);
	}

	/*
	 * (non-Javadoc)
	 * @see br.com.schindler.math.matrix.Matrix#sub(br.com.schindler.math.matrix.Matrix)
	 */
	@Override
	public Matrix<Double> sub(Matrix<Double> other) {
		return add(other.mul(-1.0));
	}

	/*
	 * (non-Javadoc)
	 * @see br.com.schindler.math.matrix.Matrix#mulScalar(java.lang.Number)
	 */
	@Override
	public synchronized Matrix<Double> scale(final Double elem) {
		try {
		      return perform(new Operation<Double>() {
				/*
				 * 
				 */
				@Override
				public Double get(int i) {
					return GMatrix.this.elements[i]*elem;
				}
			});
		} catch (Exception e) {
			throw new UnsupportedOperationException(e);
		}		
	}

	/*
	 * (non-Javadoc)
	 * @see br.com.schindler.math.matrix.Matrix#mul(java.lang.Number)
	 */
	@Override
	public synchronized Matrix<Double> mul(final Double elem) {
		try {
		      return new GMatrix(lines(), columns()).perform(new Operation<Double>() {
				/*
				 * 
				 */
				@Override
				public Double get(int i) {
					return GMatrix.this.elements[i]*elem;
				}
			});
		} catch (Exception e) {
			throw new UnsupportedOperationException(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see br.com.schindler.math.matrix.Matrix#mul(br.com.schindler.math.matrix.Matrix)
	 */
	@Override
	public synchronized Matrix<Double> mul(final Matrix<Double> other)
			throws IndexOutOfBoundsException {
		
		if(columns() != other.lines())
			throw new IndexOutOfBoundsException(String.format("mul: %d != %d", columns(), other.lines()));
		
		try {			 
			return new GMatrix(lines(), other.columns()).perform(new Operation<Double>() {
				/*
				 * 
				 */
				@Override
				public Double get(int i) {
					int col = i % other.columns();
					int lin = i / other.columns();
					int col1= lin * GMatrix.this.columns(); 
					Double ret = 0.0;
					
					for (int j = 0; j < GMatrix.this.columns(); j++){
						//TODO: Can I do it better?
						ret += (GMatrix.this.elements[col1+j] * other.get(j, col));
					}	
					
					return ret;
				}
			});
		} catch (Exception e) {
			throw new UnsupportedOperationException(e);
		}
	}


	/*
	 * (non-Javadoc)
	 * @see br.com.schindler.math.matrix.base.BasicMatrix#create(int)
	 */
	@Override
	protected Double[] create(int size) {
		return new Double[size];
	}
	
	/*
	 * (non-Javadoc)
	 * @see br.com.schindler.math.matrix.base.MultiThreadMatrix#create(int, int)
	 */
	@Override
	protected MultiThreadMatrix<Double> create(int columns, int lines) {
		return new GMatrix(columns, lines);
	}
	
	/**
	 * 
	 */
	public static void main(String[] args) {
		GMatrix m1 = new GMatrix(8, 5);
		GMatrix m2 = new GMatrix(5, 1);

		try {
			m1.fill(7.0);
			m2.fill(11.0);
 
			System.err.println(m1.mul(m2).transpose().scale(3.0));			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
