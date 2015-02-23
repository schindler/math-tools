package test.math;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.zip.GZIPInputStream;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import br.schindler.math.matrix.Matrix;
import br.schindler.math.matrix.load.octave.DoubleFactory;
import br.schindler.math.matrix.load.octave.OctaveLoader;
import br.schindler.math.matrix.math.MatrixMath;

@RunWith(Parameterized.class) 
public class MathTest {
	
	private String name;
	static private OctaveLoader<Double> loader  = new OctaveLoader<Double>(new DoubleFactory());
	
	/**
	 * Construtor por teste
	 * @param testParameter
	 */
	public MathTest(String test_name) {
		this.name = test_name;
	} 
	
	/*
	 * 
	 */
	@Parameters
	public static Collection<Object[]> data() {
		 List<Object[]> result = new ArrayList<Object[]>();	
		 int i = 1;
		 /*
		  * Testes sin
		  */
		 for (; i <= 3; i++) 
			 result.add(new Object[] {String.format("s%03d", i)});
		 
		 /*
		  * Testes cos
		  */
		 for (; i <= 6; i++) 
			 result.add(new Object[] {String.format("c%03d", i)});
		 
		 /*
		  * Testes sigmoid
		  */
		 for (; i <= 7; i++) 
			 result.add(new Object[] {String.format("m%03d", i)});
		 
		 return result;
	}
	
	/**
	 * Success
	 */
	@Test
	public void tests() {
		try {
			GZIPInputStream gz     = new GZIPInputStream(MathTest.class.getResourceAsStream(name));
			BufferedReader  stream = new BufferedReader(new InputStreamReader(gz, "UTF8"));

			Matrix<Double> m1 = loader.load(stream);					
			Matrix<Double> m2 = loader.load(stream);
			gz.close();

			System.err.println(String.format("Matrix M1 %dx%d", m1.lines(), m1.columns()));
			System.err.println(String.format("Matrix RT %dx%d", m2.lines(), m2.columns()));
 
			switch (name.charAt(0)) {
			case 's': for (int i = 0; i < 1000; i++) Assert.assertEquals(true, m2.equals(MatrixMath.sin(m1))); break;
			case 'c': for (int i = 0; i < 1000; i++) Assert.assertEquals(true, m2.equals(MatrixMath.cos(m1))); break;
			case 'm': for (int i = 0; i < 1000; i++) Assert.assertEquals(true, m2.equals(MatrixMath.sigmoid(m1))); break;
			}
			
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
