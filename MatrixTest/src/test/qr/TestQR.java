package test.qr;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import br.schindler.math.matrix.Matrix;
import br.schindler.math.matrix.load.octave.DoubleFactory;
import br.schindler.math.matrix.math.MatrixMath;

public class TestQR {
	private DoubleFactory  F = new DoubleFactory();
	@Test
	public void test(){		
		Matrix<Double> A = MatrixMath.rand(F.create(20,20));
		List<Matrix<Double>> QR = MatrixMath.qr(A);
		Matrix<Double> Q = QR.get(0);
		Matrix<Double> R = QR.get(1);
		Assert.assertTrue(Q.mul(R).equals(A));
		Matrix<Double> Qt   = Q.transpose();
		Matrix<Double> QtxQ = Qt.mul(Q);
		Assert.assertTrue(QtxQ.mul(A).equals(A));
	}
	@Test
	public void test1(){
		Matrix<Double> A = MatrixMath.rand(F.create(3, 3));
		Matrix<Double> b = MatrixMath.rand(F.create(3, 1));
		Matrix<Double> x;
		Matrix<Double> e;
		A.set(0, 0, 3.0); A.set(0, 1, 7.0); A.set(0, 2, 1.0);
		A.set(1, 0, 2.0); A.set(1, 1, 3.0); A.set(1, 2, 4.0);
		A.set(2, 0, 6.0); A.set(2, 1, 5.0); A.set(2, 2, 4.0);
		b.set(0, 0, 3.0);
		b.set(1, 0, 7.0);
		b.set(2, 0, 1.0);
		x = MatrixMath.solve(A, b);
		e = A.mul(x).sub(b);
		Assert.assertEquals(0.0, e.transpose().mul(e).get(0, 0), 1e-8);
		//System.err.println(MatrixMath.inv(A, F).mul(b));
	}
}
