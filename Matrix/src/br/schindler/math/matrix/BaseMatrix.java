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
	 * 
	 */
	protected int lines, columns;
	
	/**
	 * 
	 * @param zero
	 */
	public BaseMatrix(int lines, int columns){
		this.lines   = lines;
		this.columns = columns;
	}
	
	/**
	 * 
	 * @param lines
	 * @param columns
	 * @param zero
	 */
	public abstract BaseMatrix<T> create(int lines, int columns);
	
	/*
	 * (non-Javadoc)
	 * @see br.schindler.math.matrix.Matrix#set(int, int, java.lang.Object)
	 */
	@Override
	public void set(int i, int j, T elem) {
		if (i >= lines || j >= columns)
			throw new IndexOutOfBoundsException();		
		setByIndex(i*columns+j, elem);		
	}
	
	/*
	 * (non-Javadoc)
	 * @see br.schindler.math.matrix.Matrix#get(int, int)
	 */
	@Override
	public T get(int i, int j) {
		if (i >= lines || j >= columns)
			throw new IndexOutOfBoundsException();
		return getByIndex(i*columns+j);
	}
	
	/*
	 * (non-Javadoc)
	 * @see br.schindler.math.matrix.Matrix<Field>#get(int, int, int, int)
	 */
	@Override
	public Matrix<T> get(int fromLine, int toLine, int fromCol, int toCol) {		
		if (toLine < 0)
			toLine = lines - toLine + 1;
		
		if (toCol < 0)
			toCol = columns - toCol + 1;
		
		if (fromLine < 0 || toLine < 0 ||  fromCol < 0 || toLine > lines || toCol > columns)
			throw new IndexOutOfBoundsException();
		
		Matrix<T> result = create(toLine-fromLine, toCol-fromCol);
		
		for (int i = fromLine, ic = 0; i < toLine; i++, ic++){
			for (int j = fromCol, jc = 0; j < toCol; j++, jc++){
				result.set(ic, jc, get(i, j));
			}
		}
		
		return result;
	}
	
	/*
	 * (non-Javadoc)
	 * @see br.schindler.math.matrix.Matrix#set(int, int, int, int)
	 */
	@Override
	public void set(int fromLine, int toLine, int fromCol, int toCol, Matrix<T> other) {
		if (toLine < 0)
			toLine = lines - toLine + 1;
		
		if (toCol < 0)
			toCol = columns - toCol + 1;
		
		if (fromLine < 0 || toLine < 0 ||  fromCol < 0 || toLine > lines || toCol > columns)
			throw new IndexOutOfBoundsException();
		
		Matrix<T> result = create(toLine-fromLine, toCol-fromCol);
		
		for (int i = fromLine, ic = 0; i < toLine; i++, ic++){
			for (int j = fromCol, jc = 0; j < toCol; j++, jc++){
				result.set(ic, jc, other.get(i, j));
			}
		}		
	}
	
	/*
	 * (non-Javadoc)
	 * @see br.schindler.math.matrix.Matrix#call(br.schindler.math.matrix.operations.Function)
	 */
	@Override
	public Matrix<T> call(Function<T> func) {
		Matrix<T> ret = create(lines, columns);
		for (int i = 0; i < lines; i++) {
			for (int j = 0; j < columns; j++){
				ret.set(i,j,func.perform(get(i,j)));
			}
		}
		return ret;
	}
	/*
	 * (non-Javadoc)
	 * @see br.schindler.math.matrix.Matrix#lines()
	 */
	@Override
	public int lines() {
		return this.lines;
	}
	
	/*
	 * (non-Javadoc)
	 * @see br.schindler.math.matrix.Matrix#columns()
	 */
	@Override
	public int columns() {
		return this.columns;
	}
 
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
	 
		for (int i = 0; i < lines(); i++){
			result.append("| ");
			
			for (int j = 0; j < columns(); j++){
				result.append(String.format("%s ", get(i,j)));
			}
			
			if (i +1 < lines())
				result.append("|\n");
			else
				result.append(String.format("|[%dx%d]", lines(), columns()));
		}
				
		return result.toString();
	}
 
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		
		if (obj instanceof Matrix){
			Matrix<?> other = (Matrix<?>) obj;
			
			if (lines() != other.lines() || columns() != other.columns()) 
				return false;
			
			for (int i = 0; i < columns()*lines(); i++) {
				if (!getByIndex(i).equals(other.getByIndex(i))) 
					return false;
			}
			
			return true;
		}
		
		return false;
	}
}
