/**
 * 
 */
package br.com.schindler.math.matrix.base;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import br.com.schindler.math.matrix.Matrix;
import br.com.schindler.math.matrix.Operation;
import br.com.schindler.math.matrix.OperationsSet;

/**
 * @author Fernando
 *
 */
public abstract class MultiThreadMatrix<K, T extends OperationsSet<K>> extends BasicMatrix<K, T> {

	/*
	 * Construtor
	 */
	public MultiThreadMatrix(int lines, int columns) {
		super(lines, columns);
	}
	

	/*
	 * (non-Javadoc)
	 * @see br.com.schindler.math.matrix.Matrix#transpose()
	 */
	@Override
	public synchronized Matrix<K, T> transpose() {
		try {			 
			return create(columns(), lines()).perform(new Operation<K, T>() {
				/*
				 * 
				 */
				@Override
				public T get(int i) {
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
	 * @see br.com.schindler.math.matrix.Matrix#addIScalar(java.lang.Number)
	 */
	@Override
	public synchronized Matrix<K, T> addScalar(final T elem) {
		try {
		      return perform(new Operation<K, T>() {
				/*
				 * 
				 */
				@Override
				public T get(int i) {
					return MultiThreadMatrix.this.elements[i].add(elem);
				}
			});
		} catch (Exception e) {
			throw new UnsupportedOperationException(e);
		}		
	}

	/**
	 * 
	 * @param columns
	 * @param lines
	 * @return
	 */
	protected abstract MultiThreadMatrix<K, T> create(int columns, int lines);


	/**
	 * 
	 * @param op
	 * @throws ExecutionException 
	 * @throws InterruptedException 
	 */
	public Matrix<K, T> perform(Operation<K, T> op) throws InterruptedException, ExecutionException {
		if (this.elements.length > 1000) {
			@SuppressWarnings("unchecked")
			Future<Matrix<K, T>>[] pThreads = new Future[2];
			ExecutorService service      = Executors.newCachedThreadPool();
			int middle = this.elements.length / 2;
			pThreads[0] = service.submit(new MultiThreadMatrixCall(op, 0, middle));
			pThreads[1] = service.submit(new MultiThreadMatrixCall(op, middle, this.elements.length));
			pThreads[0].get();
			return pThreads[1].get();
		} else {
			return perform(op, 0, this.elements.length);
		}
	}

	/**
	 * 
	 * @param op
	 */
	protected Matrix<K, T> perform(Operation<K, T> op, int from, int to) {
		for (int i = from; i < to; i++)
			this.elements[i] = op.get(i);
		return this;
	}

	private class MultiThreadMatrixCall implements Callable<Matrix<K, T>> {
		private Operation<K, T> op;
		private int from, to;

		/*
		 * Construtor
		 */
		public MultiThreadMatrixCall(Operation<K, T> op, int from, int to) {
			this.op = op;
			this.to = to;
			this.from = from;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.util.concurrent.Callable#call()
		 */
		@Override
		public Matrix<K, T> call() throws Exception {
			return MultiThreadMatrix.this.perform(op, from, to);
		}
	}
}
