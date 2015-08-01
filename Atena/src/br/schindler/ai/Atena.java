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
 * Processa dados tendo como base uma rede treinada
 * 
 * <p/><h2>Formato esperado para cada camada no modelo: Input->hide-> out:</h2><p/>
 * 
 * <p/><b>ENTRADA:</b> Matrix NxM onde: N número de neurônios na cada intermediária e M número de entradas + bias
 * 
 * <p/><b>SAÍDA:</b>   Matrix UxN1 onde: U número de neuronios na cada de saída e N1 na cada intermediária + bias 
 * 
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
	 * 
	 * @param input Deve conter os valores dos pesos das conexões
	 * <p/>
	 * <b> Importante: </b> Por padrão tentará carregar do formato ASCII octave compactado
	 * 
	 * @throws IOException 
	 * @throws InvalidNetworkFlowException
	 * 
	 * @see br.schindler.math.matrix.Matrix
	 */
	public Atena (InputStream input) throws IOException, InvalidNetworkFlowException {
		this(new BufferedReader(new InputStreamReader(new GZIPInputStream(input), "UTF8")), new OctaveLoader<Double>(new DoubleFactory())); 
	}
	
	/**
	 * Criar uma rede neural tendo como base as camadas treinadas
	 * 
	 * @param input Deve conter os valores dos pesos das conexões
	 * @param loader Objeto para abstrair o formato no qual os pesos foram salvos
	 * 
	 * @throws IOException 
	 * @throws InvalidNetworkFlowException 
	 * 
	 * @see br.schindler.math.matrix.Matrix
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
	 * @param activations (opcional) lista que receberÃ¡ as ativaÃ§Ãµes de cada camada
	 * @return Matrix<Double> sendo a cada de saÃ­da da rede
	 */
	public Matrix<Double> predict(Matrix<Double> input, List<Matrix<Double>> activations){
		Matrix<Double> result = null;
		Matrix<Double>   bias = input.create(input.lines(), 1).fill(1.0);
			
		/*
		 * Repassar na rede a entrada
		 */
		for (Matrix<Double> layer : thetas){
			/*
			 * Adicionar 1's (bias) e calcular a próxima entrada
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
	 * @return Matrix<Double> sendo a cada de saÃ­da da rede
	 */
	public Matrix<Double> predict(Matrix<Double> input){
		return predict(input, null);
	}
}
