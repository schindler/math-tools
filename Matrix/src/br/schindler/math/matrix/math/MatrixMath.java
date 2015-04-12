package br.schindler.math.matrix.math;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import br.schindler.math.matrix.Matrix;
import br.schindler.math.matrix.load.MatrixFactory;
import br.schindler.math.matrix.operations.Function;

public class MatrixMath {
	/*
	 * 
	 */
	private static Random RANDOM = new Random();

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
	 * Aplica fun��o seno
	 * @param matrix
	 * @see {@link Math.sin}
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
				return  Math.sin(val);
			}
		});
	}

	/**
	 * Aplica fun��o cosseno
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
	 * Criar uma pertuba��o nos itens da matrix
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
	 * @param matrix
	 * @return
	 */
	static public List<Matrix<Double>> qr(Matrix<Double> A, MatrixFactory<Double> factory){
		List<Matrix<Double>> ret = new ArrayList<Matrix<Double>>();
		Double []      b = new Double [A.lines()];
		Double []      v = new Double [A.lines()];
		Matrix<Double> Q = factory.create(A.lines(),   A.columns());
		Matrix<Double> R = factory.create(A.columns(), A.columns());
		ret.add(Q);
		ret.add(R);	
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
				vb = (n2 > 1e-20) ? (vb / n2) : 0.0;
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
			if (n2 > 1e-20){
				for (int e = 0; e < Q.lines();   e++) Q.set(e, c, v[e] / n);
				for (int e = 0; e < R.columns(); e++) R.set(c, e, R.get(c, e) * n);
			}						
		}
		return ret;
	}
	
	
	static public Matrix<Double> solve(Matrix<Double> A, Matrix<Double> b, MatrixFactory<Double> factory) {
		List<Matrix<Double>> qr = qr(A, factory);
		return triangular_solve(qr.get(1), qr.get(0).transpose().mul(b), factory);
	}
	
	static public Matrix<Double> triangular_solve(Matrix<Double> A, Matrix<Double> b, MatrixFactory<Double> factory) {
		Matrix<Double> ret = factory.create(A.columns(), 1); 		
		for (int i = A.columns() - 1; i >=0; i--){
			ret.set(i, 0, (b.get(i, 0) - A.get(i, i+1, 0, A.columns()).mul(ret).get(0, 0)) / A.get(i,i));
		}
		return ret;
	}
	
	static public Matrix<Double> inv (Matrix<Double> A, MatrixFactory<Double> factory) {
		Matrix<Double> ret = factory.create(A.lines(), A.columns());
		Matrix<Double>   e = factory.create(A.lines(),  1);
		List<Matrix<Double>> qr = qr(A, factory);
		Matrix<Double> Qt = qr.get(0).transpose();
		Matrix<Double> R  = qr.get(1);
				
		for (int i = 0; i < A.columns(); i++){
			e.set(i, 0, 1.0);
			ret.set(0, A.lines(), i, i+1, triangular_solve(R, Qt.mul(e), factory));		
			e.set(i, 0, 0.0);
		}
		
		return ret;
	}
	
}
