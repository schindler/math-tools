package br.schindler.ai;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPInputStream;

import br.schindler.math.matrix.Matrix;
import br.schindler.math.matrix.load.Loader;
import br.schindler.math.matrix.load.octave.DoubleFactory;
import br.schindler.math.matrix.load.octave.OctaveLoader;
import br.schindler.math.matrix.math.MatrixMath;
import br.schindler.math.matrix.util.Util;

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
	 * Por padr�o tentar� carregar do formato ASCII octave compactado
	 * @param input
	 */
	public Atena (InputStream input) throws IOException, InvalidNetworkFlowException {
		this(new BufferedReader(new InputStreamReader(new GZIPInputStream(input), "UTF8")), new OctaveLoader<Double>(new DoubleFactory())); 
	}
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
			if (previous != null && ((previous.lines()+1) != layer.columns()))
				throw new InvalidNetworkFlowException();		
			/*
			 * Ja deixo de forma correta
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
		Matrix<Double> result = null;
		Matrix<Double>   bias = input.create(input.lines(), 1).fill(1.0);
			
		/*
		 * Repassar na rede a entrada
		 */
		for (Matrix<Double> layer : thetas){
			/*
			 * Adicionar 1's (bias) e calcular a proxima entrada
			 */
			result = input = MatrixMath.sigmoid(Util.cat(bias, input).mul(layer));

			if (null != activations)
				activations.add(input);	
			
		}		
		
		return result;
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
