package test.get;

import java.util.Arrays;

import org.junit.Test;

import br.schindler.math.matrix.BaseMatrix;
import br.schindler.math.matrix.Matrix;
import br.schindler.math.matrix.load.octave.DoubleFactory;
import br.schindler.math.matrix.math.MatrixMath;
import br.schindler.math.matrix.util.Util;

public class GetTest {
	private DoubleFactory f = new DoubleFactory();
	
	public GetTest() {
	}
	
	@Test
	public void t001() {
		BaseMatrix<Double> m1 = (BaseMatrix<Double>) f.create(5,  5).increment(1.0);
		BaseMatrix<Double> m2 = (BaseMatrix<Double>) f.create(5,  5).increment(2.0);
		Matrix<Double> rt = f.create(5, 10).increment(1.0);
		Matrix<Double> cc;
		
		for (int i = 0; i < 5; i++)
			for (int j = 5; j < 10; j++)
				rt.set(i, j, 2.0);
		
		System.err.println(m1);
		System.err.println(m2);
		System.err.println(rt);
		
		
		cc = Util.cat(m1, m2);
		
		
		System.err.println(cc);
		
		cc = Util.append(m1, m2);
		
		System.err.println(cc);
	}
	
	@Test
	public void t002() {
		Matrix<Double> m1 = MatrixMath.rand(f.create(15,  5));
		System.err.println(m1);
		
		for (int i = 0; i < m1.lines(); i++)
			System.err.println(Arrays.toString(m1.getRow(i)));
		
		for (int i = 0; i < m1.columns(); i++)
			System.err.println(Arrays.toString(m1.getCol(i)));
		
	}

}
