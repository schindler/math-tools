/**
 * 
 */
package br.schindler.math.matrix.multithread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import br.schindler.math.matrix.Matrix;
import br.schindler.math.matrix.base.SparseMatrix;
import br.schindler.math.matrix.fields.Field;

/**
 * @author Fernando
 *
 */
public class MultiThreadMatrix extends SparseMatrix {

	/*
	 * Construtor
	 */
	public MultiThreadMatrix(int lines, int columns, Field zero) {
		super(lines, columns, zero);
	}

	/*
	 * (non-Javadoc)
	 * @see br.schindler.math.matrix.Matrix#transpose()
	 */
	@Override
	public synchronized MultiThreadMatrix transpose() {
		try {			 
			return new MultiThreadMatrix(columns(), lines(), zero).perform(new Operation<Field>() {
				/*
				 * 
				 */
				@Override
				public Field get(int i) {
					//TODO: Can I do it better?					
					int col = i % MultiThreadMatrix.this.columns(); 
					int lin = i / MultiThreadMatrix.this.columns();	
					return MultiThreadMatrix.this.get(col, lin);
				}
			});
		} catch (Exception e) {
			throw new UnsupportedOperationException(e);
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see br.schindler.math.matrix.Matrix#mul(br.schindler.math.matrix.Matrix)
	 */
	@Override
	public synchronized MultiThreadMatrix mul(final Matrix<Field> other) {
		final Matrix<Field> _this = this;
		try {			 
			return new MultiThreadMatrix(lines(), other.columns(), zero).perform(new Operation<Field>() {
				@Override
				public Field get(int i) {
					int col = i % other.columns();
					int lin = i / other.columns();
					int col1= lin * _this.columns(); 
					
					Field ret = (Field) zero.clone();
					
					for (int j = 0; j < _this.columns(); j++){
						//TODO: Can I do it better?
						Field val =  _this.getByIndex(col1+j);
						
						if (null != val)
							ret.inc(other.get(j, col).mul(val));
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
	 * @see br.schindler.math.matrix.Matrix#mul(br.schindler.math.matrix.Field)
	 */
	@Override
	public synchronized MultiThreadMatrix mul(final Field elem) {
		final Matrix<Field> _this = this;
		try {
			return new MultiThreadMatrix(lines(), columns(), zero).perform(
					new Operation<Field>() {
						/*
						 * (non-Javadoc)
						 * @see br.schindler.math.matrix.base.Operation#get(int)
						 */
						@Override
						public Field get(int i) {
							Field val = _this.getByIndex(i);

							if (null != val)
								val = val.mul(elem);

							return val;
						}
					});
		} catch (Exception e) {
			throw new UnsupportedOperationException(e);
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see br.schindler.math.matrix.Matrix#scale(br.schindler.math.matrix.Field)
	 */
	@Override
	public synchronized Matrix<Field> scale(final Field elem) {
		try {
			return perform(
					new Operation<Field>() {
						/*
						 * (non-Javadoc)
						 * @see br.schindler.math.matrix.base.Operation#get(int)
						 */
						@Override
						public Field get(int i) {
							Field val = MultiThreadMatrix.this.elements.get(i);

							if (null != val)
								val = val.mul(elem);

							return val;
						}
					});
		} catch (Exception e) {
			throw new UnsupportedOperationException(e);
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see br.schindler.math.matrix.Matrix#addScalar(br.schindler.math.matrix.Field)
	 */
	@Override
	public synchronized Matrix<Field> addScalar(final Field elem) {
		try {
			return perform(
					new Operation<Field>() {
						/*
						 * (non-Javadoc)
						 * @see br.schindler.math.matrix.base.Operation#get(int)
						 */
						@Override
						public Field get(int i) {
							Field val = MultiThreadMatrix.this.elements.get(i);

							if (null != val)
								val = val.add(elem);

							return val;
						}
					});
		} catch (Exception e) {
			throw new UnsupportedOperationException(e);
		}
	}
	
	
	
	/*
	 * (non-Javadoc)
	 * @see br.schindler.math.matrix.Matrix#add(br.schindler.math.matrix.Field)
	 */
	@Override
	public synchronized Matrix<Field> add(final Field elem) {
		try {
			return new MultiThreadMatrix(lines(), columns(), zero).perform(
					new Operation<Field>() {
						/*
						 * (non-Javadoc)
						 * @see br.schindler.math.matrix.base.Operation#get(int)
						 */
						@Override
						public Field get(int i) {				 
							return  MultiThreadMatrix.this.getByIndex(i).add(elem);
						}
					});
		} catch (Exception e){
			throw new UnsupportedOperationException(e);
		}			
	}
	
	/*
	 * (non-Javadoc)
	 * @see br.schindler.math.matrix.Matrix#add(br.schindler.math.matrix.Matrix)
	 */
	@Override
	public synchronized MultiThreadMatrix add(final Matrix<Field> other) {
		try {			 
			return new MultiThreadMatrix(lines(), columns(), zero).perform(new Operation<Field>() {
		        /*
		         * (non-Javadoc)
		         * @see br.schindler.math.matrix.multithread.Operation#get(int)
		         */
				@Override
				public Field get(int i) {			 
					return MultiThreadMatrix.this.getByIndex(i).add(other.getByIndex(i));
				}
			});
		} catch (Exception e) {
			throw new UnsupportedOperationException(e);
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see br.schindler.math.matrix.Matrix#sub(br.schindler.math.matrix.Field)
	 */
	@Override
	public synchronized MultiThreadMatrix sub(final Field elem) {
		try {			 
			return new MultiThreadMatrix(lines(), columns(), zero).perform(new Operation<Field>() {
				/*
				 * (non-Javadoc)
				 * @see br.schindler.math.matrix.multithread.Operation#get(int)
				 */
				@Override
				public Field get(int i) {			 
					return MultiThreadMatrix.this.getByIndex(i).sub(elem);
				}
			});
		} catch (Exception e) {
			throw new UnsupportedOperationException(e);
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see br.schindler.math.matrix.Matrix#subScalar(br.schindler.math.matrix.Field)
	 */
	@Override
	public synchronized MultiThreadMatrix subScalar(final Field elem) {
		try {			 
			return perform(new Operation<Field>() {
                /*
                 * (non-Javadoc)
                 * @see br.schindler.math.matrix.multithread.Operation#get(int)
                 */
				@Override
				public Field get(int i) {			 
					return MultiThreadMatrix.this.getByIndex(i).sub(elem);
				}
			});
		} catch (Exception e) {
			throw new UnsupportedOperationException(e);
		}
		
	}
	
	/*
	 * (non-Javadoc)
	 * @see br.schindler.math.matrix.Matrix#sub(br.schindler.math.matrix.Matrix)
	 */
	@Override
	public synchronized MultiThreadMatrix sub(final Matrix<Field> other) {
		try {			 
			return new MultiThreadMatrix(lines(), columns(), zero).perform(new Operation<Field>() {
				/*
				 * (non-Javadoc)
				 * @see br.schindler.math.matrix.multithread.Operation#get(int)
				 */
				@Override
				public Field get(int i) {			 
					return MultiThreadMatrix.this.getByIndex(i).add(other.getByIndex(i));
				}
			});
		} catch (Exception e) {
			throw new UnsupportedOperationException(e);
		}
	}
 

	/**
	 * 
	 * @param op
	 * @throws ExecutionException 
	 * @throws InterruptedException 
	 */
	public MultiThreadMatrix perform(Operation<Field> op) throws InterruptedException, ExecutionException {
		int total = columns()*lines();
		if (total > 4) {
			@SuppressWarnings("unchecked")
			Future<MultiThreadMatrix>[] pThreads = new Future[2];
			ExecutorService service = Executors.newFixedThreadPool(2);
			int middle = total / 2;
			
			pThreads[0] = service.submit(new MultiThreadMatrixCall(op,      0, middle));
			pThreads[1] = service.submit(new MultiThreadMatrixCall(op, middle,  total));
 
			pThreads[0].get();
			pThreads[1].get();
 
			return this;
		} else {
			return perform(op, 0, total);
		}
	}

	/**
	 * 
	 * @param op
	 */
	protected MultiThreadMatrix perform(Operation<Field> op, int from, int to) {
		for (int i = from; i < to; i++){
			Field val = op.get(i);
			if (null != val){
				if (!val.equals(zero)){		 
					synchronized(this.elements){
						this.elements.put(i, val);
					}
				}
			}
		}
		return this;
	}

	/**
	 * 
	 * @author Fernando
	 *
	 */
	private class MultiThreadMatrixCall implements Callable<MultiThreadMatrix> {
		private Operation<Field> op;
		private int from, to;

		/*
		 * Construtor
		 */
		public MultiThreadMatrixCall(Operation<Field> op, int from, int to) {
			this.op   = op;
			this.from = from;
			this.to   = to;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.util.concurrent.Callable#call()
		 */
		@Override
		public MultiThreadMatrix call() throws Exception {
			return MultiThreadMatrix.this.perform(op, from, to);
		}
	}
}
