package br.schindler.ai;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import br.schindler.math.matrix.Matrix;
import br.schindler.math.matrix.load.Loader;

/**
 * 
 * @author fernando.schindler@gmail.com
 */
public class Atena {
	/*
	 * Camadas internas da rede
	 */
	private List<Matrix<Double>> thetas = new ArrayList<Matrix<Double>>();

	/**
	 * Criar uma rede neural tendo como base as camadas treinadas
	 * @param input
	 * @param loader
	 * @throws IOException 
	 * @throws InvalidNetworkFlowException 
	 */
	public Atena(BufferedReader input, Loader<Double> loader) throws IOException, InvalidNetworkFlowException {
		Matrix<Double> previous = null;
		/*
		 * Carregar todas as camadas
		 */
		for (Matrix<Double> layer = loader.load(input); layer != null; layer = loader.load(input)) {
			/*
			 * Valida fluxo de dados pela rede
			 */
			if (previous != null && (previous.lines() != (layer.columns()+1)))
				throw new InvalidNetworkFlowException();		
			/*
			 * Já deixo de forma correta
			 */
			thetas.add((previous = layer).transpose());
		}			
	}
	
	/**
	 * Aplicar uma entrada(s) a rede
	 * @param input entrada desejada para a rede
	 * @param activations (opcional) lista que receberá as ativações de cada camada
	 * @return Matrix<Double> sendo a cada de saída da rede
	 */
	public Matrix<Double> predict(Matrix<Double> input, List<Matrix<Double>> activations){
		
		
		
		return null;
	}
	
	/**
	 * Aplicar uma entrada(s) a rede
	 * @param input entrada desejada para a rede
	 * @return Matrix<Double> sendo a cada de saída da rede
	 */
	public Matrix<Double> predict(Matrix<Double> input){
		return predict(input, null);
	}
}
