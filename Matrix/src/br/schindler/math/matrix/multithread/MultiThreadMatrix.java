/**
 * 
 */
package br.schindler.math.matrix.multithread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import br.schindler.math.matrix.BaseMatrix;
import br.schindler.math.matrix.Matrix;
import br.schindler.math.matrix.fields.Field;
import br.schindler.math.matrix.operations.Operation;

/**
 * @author Fernando
 *
 */
public class MultiThreadMatrix implements Matrix<Field>  {
	
	private BaseMatrix<Field> matrix;
	
	@SuppressWarnings("unchecked")
	private Future<MultiThreadMatrix>[] pThreads = new Future[2];
	private ExecutorService service = Executors.newFixedThreadPool(2);

	/*
	 * Construtor
	 */
	public MultiThreadMatrix(BaseMatrix<Field> m) {
		this.matrix = m;
	}  

	/**
	 * 
	 * @param op
	 * @throws ExecutionException 
	 * @throws InterruptedException 
	 */
	public MultiThreadMatrix execute(Operation<? extends Field> op, Object ... params) {
		int total = columns()*lines();
		if (total > 4) {	
			int middle = total / 2;
			
			pThreads[0] = service.submit(new MultiThreadMatrixCall(op,      0, middle, params));
			pThreads[1] = service.submit(new MultiThreadMatrixCall(op, middle,  total, params));
 
			try {
				pThreads[0].get();
				pThreads[1].get();
		 
			} catch (InterruptedException | ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
 
			return this;
		} else {
			return perform(op, 0, total, params);
		}
	}

	/**
	 * 
	 * @param op
	 */
	protected MultiThreadMatrix perform(Operation<? extends Field> op, int from, int to, Object ... params) {
		System.err.println("start->" + from);
		for (int i = from; i < to; i++){
			this.matrix.setByIndex(i, op.get(i, params));
		}
		System.err.println("end->" + from);
		return this;
	}

	/**
	 * 
	 * @author Fernando
	 *
	 */
	private class MultiThreadMatrixCall implements Callable<MultiThreadMatrix> {
		private Operation<? extends Field> op;
		private int from, to;
		private Object [] params;

		/*
		 * Construtor
		 */
		public MultiThreadMatrixCall(Operation<? extends Field> op, int from, int to, Object ... params) {
			this.op   = op;
			this.from = from;
			this.to   = to;
			this.params = params;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.util.concurrent.Callable#call()
		 */
		@Override
		public MultiThreadMatrix call() throws Exception {
			return MultiThreadMatrix.this.perform(op, from, to, params);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see br.schindler.math.matrix.Matrix#getByIndex(int)
	 */
	@Override
	public Field getByIndex(int index) {
		return this.matrix.getByIndex(index);
	}
	
	/*
	 * (non-Javadoc)
	 * @see br.schindler.math.matrix.Matrix#get(int, int)
	 */
	@Override
	public Field get(int i, int j) {
		return this.matrix.get(i, j);
	}

	/*
	 * (non-Javadoc)
	 * @see br.schindler.math.matrix.Matrix#get(int, int, int, int)
	 */
	@Override
	public Matrix<Field> get(int fromLine, int toLine, int fromCol, int toCol) {
		return this.matrix.get(fromLine, toLine, fromCol, toCol);
	}

	@Override
	public void set(int i, int j, Field elem) {
		this.matrix.set(i, j, elem);
	}

	/*
	 * (non-Javadoc)
	 * @see br.schindler.math.matrix.Matrix#fill(java.lang.Object)
	 */
	@Override
	public Matrix<Field> fill(Field elem) {
		return this.matrix.fill(elem);
	}

	/*
	 * (non-Javadoc)
	 * @see br.schindler.math.matrix.Matrix#fill(int, java.lang.Object)
	 */
	@Override
	public Matrix<Field> fill(int i, Field elem) {
		return this.matrix.fill(i, elem);
	}

	/*
	 * (non-Javadoc)
	 * @see br.schindler.math.matrix.Matrix#increment(java.lang.Object)
	 */
	@Override
	public Matrix<Field> increment(Field elem) {
		return this.matrix.increment(elem);
	}

	/*
	 * (non-Javadoc)
	 * @see br.schindler.math.matrix.Matrix#add(java.lang.Object)
	 */
	@Override
	public Matrix<Field> add(Field elem) {
		return this.matrix.add(elem);
	}

	/*
	 * (non-Javadoc)
	 * @see br.schindler.math.matrix.Matrix#add(br.schindler.math.matrix.Matrix)
	 */
	@Override
	public Matrix<Field> add(Matrix<Field> other) {
		return this.matrix.add(other);
	}

	/*
	 * (non-Javadoc)
	 * @see br.schindler.math.matrix.Matrix#decrement(java.lang.Object)
	 */
	@Override
	public Matrix<Field> decrement(Field elem) {
		return this.matrix.decrement(elem);
	}

	/*
	 * (non-Javadoc)
	 * @see br.schindler.math.matrix.Matrix#sub(java.lang.Object)
	 */
	@Override
	public Matrix<Field> sub(Field elem) {
		return this.matrix.sub(elem);
	}

	/*
	 * (non-Javadoc)
	 * @see br.schindler.math.matrix.Matrix#sub(br.schindler.math.matrix.Matrix)
	 */
	@Override
	public Matrix<Field> sub(Matrix<Field> other) {
		return this.matrix.sub(other);
	}

	/*
	 * (non-Javadoc)
	 * @see br.schindler.math.matrix.Matrix#scale(java.lang.Object)
	 */
	@Override
	public Matrix<Field> scale(Field elem) {
		return this.matrix.scale(elem);
	}

	/*
	 * (non-Javadoc)
	 * @see br.schindler.math.matrix.Matrix#mul(java.lang.Object)
	 */
	@Override
	public Matrix<Field> mul(Field elem) {
		return this.matrix.mul(elem);
	}

	/*
	 * (non-Javadoc)
	 * @see br.schindler.math.matrix.Matrix#mul(br.schindler.math.matrix.Matrix)
	 */
	@Override
	public Matrix<Field> mul(Matrix<Field> other) {
		/*if (columns() != other.lines())
			throw new IndexOutOfBoundsException();		
		return new MultiThreadMatrix(matrix.create(matrix.lines(), other.columns())).execute(matrix.mul, this, other);*/
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see br.schindler.math.matrix.Matrix#transpose()
	 */
	@Override
	public Matrix<Field> transpose() {
		return this.matrix.transpose();
	}

	/*
	 * (non-Javadoc)
	 * @see br.schindler.math.matrix.Matrix#lines()
	 */
	@Override
	public int lines() {
		return this.matrix.lines();
	}

	/*
	 * (non-Javadoc)
	 * @see br.schindler.math.matrix.Matrix#columns()
	 */
	@Override
	public int columns() {
		return this.matrix.columns();
	}

	/*
	 * (non-Javadoc)
	 * @see br.schindler.math.matrix.Matrix#setByIndex(int, java.lang.Object)
	 */
	@Override
	public void setByIndex(int index, Field elem) {
	    this.matrix.setByIndex(index, elem);		
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) { 
		return this.matrix.equals(obj);
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return this.matrix.toString();
	}

	@Override
	public void set(int fromLine, int toLine, int fromCol, int toCol,
			Matrix<Field> other) {
		// TODO Auto-generated method stub
		
	}
}
