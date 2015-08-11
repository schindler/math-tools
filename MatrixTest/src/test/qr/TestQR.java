package test.qr;

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
		Matrix<Double> [] QR = MatrixMath.qr(A);
		Matrix<Double> Q = QR[0];
		Matrix<Double> R = QR[1];
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
	
	@Test
	public void test2(){		
		Matrix<Double> A = F.create(2, 4);
		Matrix<Double> I = F.create(2, 2);
		int l = 0;				
		A.set(l, 0, 7.0); A.set(l, 1, 8.0); A.set(l, 2, 9.0); A.set(l, 3, 4.0); l++;
		A.set(l, 0, 4.0); A.set(l, 1, 5.0); A.set(l, 2, 6.0); A.set(l, 3, 9.0);
		I.set(0, 0, 1.0); I.set(1, 1, 1.0); 
		Assert.assertTrue(A.mul(MatrixMath.pinv(A)).equals(I));	 
	}
	
	@Test
	public void test3(){		
		Matrix<Double> A = F.create(5, 3);
		Matrix<Double> b = F.create(5, 1);
		Matrix<Double> x = F.create(3, 1);
		
		int l = 0;
		
		A.set(l, 0, 7.0); A.set(l, 1, 8.0); A.set(l, 2, 9.0); l++;
		A.set(l, 0, 4.0); A.set(l, 1, 5.0); A.set(l, 2, 1.0); l++;
		A.set(l, 0, 7.0); A.set(l, 1, 3.0); A.set(l, 2, 1.0); l++;
		A.set(l, 0, 6.0); A.set(l, 1, 5.0); A.set(l, 2, 4.0); l++;
		A.set(l, 0, 4.0); A.set(l, 1, 5.0); A.set(l, 2, 7.0);
 
		b.set(0, 0, 8.0);
		b.set(1, 0, 4.0);
		b.set(2, 0, 1.0);
		b.set(3, 0, 6.0);
		b.set(4, 0, 3.0);
		
		x.set(0, 0, -0.2067351);
		x.set(1, 0, 1.1000720);
		x.set(2, 0, -0.0031155);
	 
		Assert.assertTrue(MatrixMath.solve(A, b).equals(x));	
 
	}
	
	@Test
	public void test4(){		
		Matrix<Double> A = MatrixMath.rand(F.create(20,10));
		Matrix<Double> [] QR = MatrixMath.qr(A);
		Matrix<Double> Q = QR[0];
		Matrix<Double> R = QR[1];
		Assert.assertTrue(Q.mul(R).equals(A));
		Assert.assertTrue(Q.mul(Q.transpose()).mul(A).equals(A));
	}
	
	@Test
	public void test5(){		
		Matrix<Double> A = MatrixMath.rand(F.create(1000,2000));
		Matrix<Double> [] QR = MatrixMath.qr(A);
		Matrix<Double> Q = QR[0];
		Matrix<Double> R = QR[1];
		Assert.assertTrue(Q.mul(R).equals(A)); 
		Assert.assertTrue(Q.mul(Q.transpose()).mul(A).equals(A));
	}
}
