package br.schindler.math.matrix.math;

import java.util.Random;

import br.schindler.math.matrix.Matrix;
import br.schindler.math.matrix.operations.Function;

/*
 * 
 */
public class MatrixMath {
	/*
	 * 
	 */
	private static Random RANDOM = new Random();
	
	
	/*
	 * 
	 */
	static public double EPS;
	
	/*
	 * 
	 */
	static {
		EPS = 1;
		while ((1 + EPS) > 1) EPS /= 10;
		EPS *= 10;		
	}

	/**
	 * 
	 * @param matrix
	 * @return {@link Matrix}
	 */
	static public Matrix<Double> sigmoid(Matrix<Double> matrix) {
		return matrix.call(new Function<Double>() {
			/*
			 * (non-Javadoc)
			 * @see br.schindler.math.matrix.operations.Function#perform(java.lang.Object)
			 */
			@Override
			public Double perform(Double val) {
				return  (double) 1 / (double) (1 + Math.exp(-val));
			}
		});
	}

	/**
	 * Aplica função seno
	 * @param matrix
	 * @see {@link java.lang.Math}
	 * @return {@link Matrix}
	 */
	static public Matrix<Double> sin(Matrix<Double> matrix) {
		return matrix.call(new Function<Double>() {
			/*
			 * (non-Javadoc)
			 * @see br.schindler.math.matrix.operations.Function#perform(java.lang.Object)
			 */
			@Override
			public Double perform(Double val) {
				return Math.sin(val);
			}
		});
	}

	/**
	 * Aplica função cosseno
	 * @param matrix
	 * @see {@link Math.cos}
	 * @return {@link Matrix}
	 */
	static public Matrix<Double> cos(Matrix<Double> matrix) {
		return matrix.call(new Function<Double>() {
			/*
			 * (non-Javadoc)
			 * @see br.schindler.math.matrix.operations.Function#perform(java.lang.Object)
			 */
			@Override
			public Double perform(Double val) {
				return  Math.cos(val);
			}
		});
	}
	
	/**
	 * Criar uma pertubação nos itens da matrix
	 * @param matrix
	 * @return {@link Matrix}
	 */
	static public Matrix<Double> rand (Matrix<Double> matrix) {
		return matrix.call(new Function<Double>() {
			/*
			 * (non-Javadoc)
			 * @see br.schindler.math.matrix.operations.Function#perform(java.lang.Object)
			 */
			@Override
			public Double perform(Double val) {
				return RANDOM.nextDouble()+val;
			}
		});
	}
	
	/**
	 * 
	 */
	static public Double min (Matrix<Double> matrix) {
		return null;
	}
	
	/**
	 * Calcula a fatoração QR por meio de ortogonalização
	 * 
	 * <p/> {@code}  Q.mul(R).equal(A) == true   {@code}
	 * 
	 * @param matrix {@link Matrix} a ser fatorada
	 * 
	 * @return Array no qual a posição zero tem Q e a posição 1 R
	 */
	@SuppressWarnings("unchecked")
	static public Matrix<Double> [] qr(Matrix<Double> A){ 
		Double []      b = new Double [A.lines()];//TODO: Poderia ser Sparse?
		Double []      v = new Double [A.lines()];//TODO: Poderia ser Sparse?
		Matrix<Double> Q = A.create(A.lines(),   A.columns());
		Matrix<Double> R = A.create(A.columns(), A.columns());
 
		for (int c = 0, s = 0; c < A.columns(); c++, s++){				
			for (int i = 0; i < A.lines(); i++) b[i] = A.get(i, c);			
			R.set(s, c, 1.0);	
			for (int i = 0; i < s; i++){
				Double n2 = 0.0;
				Double vb = 0.0;
				for (int e = 0; e < Q.lines(); e++) {
					Double ve = Q.get(e, i);
					n2  += ve*ve;
					vb  += ve*b[e];
					v[e] = ve;
				}
				vb = (n2 > EPS) ? (vb / n2) : 0.0;
				R.set(i, c, vb);
				for (int e = 0; e < A.lines(); e++) b[e] -= (v[e] * vb);
			}
			for (int i = 0; i < A.lines(); i++) Q.set(i, c, b[i]);
		}
		for (int c = 0; c < A.columns(); c++){
			Double n2 = 0.0;
			Double n;
			for (int e = 0; e < Q.lines(); e++) {
				Double ve = Q.get(e, c);
				n2  += ve*ve;
				v[e] = ve;
			}
			n = Math.sqrt(n2);
			if (n2 > EPS){
				for (int e = 0; e < Q.lines();   e++) Q.set(e, c, v[e] / n);
				for (int e = 0; e < R.columns(); e++) R.set(c, e, R.get(c, e) * n);
			}						
		}
		return new Matrix [] {Q, R};
	}
	
	/**
	 * Resolve o sistema A*x=b
	 * @param A
	 * @param b
	 * @return @{link Matrix} coluna com o resultado
	 */
	static public Matrix<Double> solve(Matrix<Double> A, Matrix<Double> b) {
		Matrix<Double> [] qr = qr(A);
		return triangular_solve(qr[1], qr[0].transpose().mul(b));
	}
	
	/**
	 * 
	 * @param A
	 * @param b
	 * @return
	 */
	static public Matrix<Double> triangular_solve(Matrix<Double> A, Matrix<Double> b) {
		Matrix<Double> ret = A.create(A.columns(), 1); 		
		for (int i = A.columns() - 1; i >=0; i--){
			ret.set(i, 0, (b.get(i, 0) - A.get(i, i+1, 0, A.columns()).mul(ret).get(0, 0)) / A.get(i,i));
		}
		return ret;
	}
	
	/**
	 * 
	 * @param A
	 * @return
	 */
	static public Matrix<Double> inv (Matrix<Double> A) {
		
		Matrix<Double> [] qr = qr(A);
		Matrix<Double>    Qt = qr[0].transpose();
		Matrix<Double>     R = qr[1];		
		Matrix<Double>   ret = A.create(A.lines(), A.lines());
		Matrix<Double>     e = A.create(A.lines(),  1);
				
		for (int i = 0; i < A.columns(); i++){
			e.set(i, 0, 1.0);
			ret.set(0, A.lines(), i, i+1, triangular_solve(R, Qt.mul(e)));		
			e.set(i, 0, 0.0);
		}
		
		return ret;
	}
	
	/**
	 * 
	 * @param A
	 * @return
	 */
	static public Matrix<Double> pinv (Matrix<Double> A) {
		
		if (A.lines() < A.columns()){
			Matrix<Double> At = A.transpose();
			return At.mul(inv(A.mul(At)));
		}
		else
		{
			if (A.lines() > A.columns()){
				Matrix<Double> At = A.transpose();
				return (inv(At.mul(A))).mul(At);
			}
		}
		
		return inv(A);
	}
	
}
