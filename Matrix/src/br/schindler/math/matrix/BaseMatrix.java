package br.schindler.math.matrix;

import br.schindler.math.matrix.operations.Function;

/**
 * 
 * @author Fernando
 *
 * @param <T>
 */
public abstract class BaseMatrix<T> implements Matrix<T> {

	/**
	 * Indica as linhas e colunas dessa matrix
	 */
	protected int lines, columns;

	/**
	 * 
	 * @param zero
	 */
	public BaseMatrix(int lines, int columns) {
		this.lines = lines;
		this.columns = columns;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.schindler.math.matrix.Matrix#get(int, int, int, int)
	 */
	@Override
	public Matrix<T> get(int fromLine, int nlines, int fromCol, int ncols) {
		if (nlines < 0)
			nlines = lines - nlines + 1;

		if (ncols < 0)
			ncols = columns - ncols + 1;

		if (fromLine < 0 || nlines < 0 || fromCol < 0 || nlines > lines || ncols > columns)
			throw new IndexOutOfBoundsException();

		Matrix<T> result = create(nlines - fromLine, ncols - fromCol);

		for (int i = fromLine, ic = 0; i < nlines; i++, ic++) {
			for (int j = fromCol, jc = 0; j < ncols; j++, jc++) {
				result.set(ic, jc, get(i, j));
			}
		}

		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.schindler.math.matrix.Matrix#set(int, int, int, int,
	 * br.schindler.math.matrix.Matrix)
	 */
	@Override
	public Matrix<T> set(int fromLine, int nlines, int fromCol, int ncols, Matrix<T> other) {
		if (nlines < 0)
			nlines = lines - nlines + 1;

		if (ncols < 0)
			ncols = columns - ncols + 1;

		if (fromLine < 0 || nlines < 0 || fromCol < 0 || nlines > lines || ncols > columns)
			throw new IndexOutOfBoundsException();

		for (int i = fromLine, ic = 0; i < nlines; i++, ic++) {
			for (int j = fromCol, jc = 0; j < ncols; j++, jc++) {
				set(i, j, other.get(ic, jc));
			}
		}

		return this;
	}
 	
	/*
	 * (non-Javadoc)
	 * @see br.schindler.math.matrix.Matrix#transpose()
	 */
	@Override
	public Matrix<T> transpose() {		
		Matrix<T> result = create(columns, lines);
				
		for (int i = 0; i < lines; i++){
			for (int j = 0; j < columns; j++)
				result.set(j, i, get(i,j));
		}
		
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.schindler.math.matrix.Matrix#call(br.schindler.math.matrix.operations
	 * .Function)
	 */
	@Override
	public Matrix<T> call(Function<T> func) {
		for (int i = 0; i < lines; i++) {
			for (int j = 0; j < columns; j++) {
				 set(i, j, func.perform(get(i, j)));
			}
		}
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.schindler.math.matrix.Matrix#call(java.lang.Iterable,
	 * java.lang.Iterable, br.schindler.math.matrix.operations.Function)
	 */
	@Override
	public Matrix<T> call(Iterable<Integer> lines, Iterable<Integer> columns, Function<T> func) {
		for (int i :  lines)
			for (int j : columns)
				 set(i, j, func.perform(get(i, j)));
		return this;
	}
	
	/*
	 * (non-Javadoc)
	 * @see br.schindler.math.matrix.Matrix#set(java.lang.Iterable, java.lang.Iterable, br.schindler.math.matrix.Matrix)
	 */
	@Override
	public Matrix<T> set(Iterable<Integer> lines, Iterable<Integer> columns, Matrix<T> other) {
		int oi = 0, oj = 0;
		for (int i :  lines){
			for (int j : columns){
				set(i,j, other.get(oi, oj++));
			}
			oi++;
			oj=0;
		}
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.schindler.math.matrix.Matrix#lines()
	 */
	@Override
	public int lines() {
		return this.lines;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.schindler.math.matrix.Matrix#columns()
	 */
	@Override
	public int columns() {
		return this.columns;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();

		for (int i = 0; i < lines(); i++) {
			result.append("| ");

			for (int j = 0; j < columns(); j++) {
				result.append(String.format("%s ", get(i, j)));
			}

			if (i + 1 < lines())
				result.append("|\n");
			else
				result.append(String.format("|[%dx%d]", lines(), columns()));
		}

		return result.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {

		if (obj instanceof Matrix) {
			Matrix<?> other = (Matrix<?>) obj;

			if (lines() != other.lines() || columns() != other.columns())
				return false;
			
			for (int i = 0; i <lines(); i++)
				for (int j = 0; j < columns(); j++)
					if (!get(i,j).equals(other.get(i, j))) return false;

			return true;
		}

		return false;
	}
}
