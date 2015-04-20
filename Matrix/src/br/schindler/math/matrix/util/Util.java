package br.schindler.math.matrix.util;

import br.schindler.math.matrix.BaseMatrix;

public class Util {

	/**
	 * 
	 * @param m1
	 * @param m2
	 * @return
	 */
	public static <D> BaseMatrix<D> cat(BaseMatrix<D> m1, BaseMatrix<D> m2) {
		if (m1.lines() != m2.lines())
			throw new IndexOutOfBoundsException();

		int tCols = m2.columns() + m1.columns();

		BaseMatrix<D> result = m1.create(m1.lines(), tCols);

		result.set(0, m1.lines(), 0, m1.columns(), m1);
		result.set(0, m1.lines(), m1.columns(), tCols, m2);

		return result;
	}
	
	/**
	 * 
	 * @param m1
	 * @param m2
	 * @return
	 */
	public static <D> BaseMatrix<D> append(BaseMatrix<D> m1, BaseMatrix<D> m2) {
		if (m1.columns() != m2.columns())
			throw new IndexOutOfBoundsException();

		int tLins = m2.lines() + m1.lines();

		BaseMatrix<D> result = m1.create(tLins, m1.columns());

		result.set(0, m1.lines(), 0, m1.columns(), m1);
		result.set(m1.lines(), tLins, 0, m1.columns(), m2);

		return result;
	}
}
