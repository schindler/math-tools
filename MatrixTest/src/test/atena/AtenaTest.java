package test.atena;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.zip.GZIPInputStream;

import org.junit.Assert;
import org.junit.Test;

import br.schindler.ai.Atena;
import br.schindler.ai.InvalidNetworkFlowException;
import br.schindler.math.matrix.Matrix;
import br.schindler.math.matrix.load.octave.DoubleFactory;
import br.schindler.math.matrix.load.octave.OctaveLoader;

public class AtenaTest {
	/*
	 * 
	 */
	@Test
	public void atena (){
		try {
			OctaveLoader<Double> loader =  new OctaveLoader<Double>(new DoubleFactory());
			Matrix<Double> input = loader.load(new BufferedReader(new InputStreamReader(new GZIPInputStream(AtenaTest.class.getResourceAsStream("image.data")))));
			Atena atena = new Atena(AtenaTest.class.getResourceAsStream("handw.data"));
			Matrix<Double> result = atena.predict(input);
			System.out.println(result);
			Assert.assertEquals(9.1585e-1, result.get(0, 2), 1e-4);
		} catch (IOException | InvalidNetworkFlowException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
