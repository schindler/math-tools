package test.mul;

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

@RunWith(Parameterized.class) 
public class MulTest {
	
	private String name;
	static private OctaveLoader<Double> loader  = new OctaveLoader<Double>(new DoubleFactory());
	
	/**
	 * Construtor por teste
	 * @param testParameter
	 */
	public MulTest(String test_name) {
		this.name = test_name;
	} 
	
	/*
	 * 
	 */
	@Parameters
	public static Collection<Object[]> data() {
		 List<Object[]> result = new ArrayList<Object[]>();		 
		 for (int i = 1; i <= 6; i++) 
			 result.add(new Object[] {String.format("t%03d", i)});
		 return result;
	}
	
	/**
	 * Success
	 */
	@Test
	public void tests() {
		try {
			System.err.println(name);
			GZIPInputStream gz     = new GZIPInputStream(MulTest.class.getResourceAsStream(name));
			BufferedReader  stream = new BufferedReader(new InputStreamReader(gz, "UTF8"));
			Matrix<Double> m1 = loader.load(stream);					
			Matrix<Double> m2 = loader.load(stream);
			Matrix<Double> rt = loader.load(stream);
			gz.close();				
			System.err.println(String.format("Matrix M1 %dx%d", m1.lines(), m1.columns()));
			System.err.println(String.format("Matrix M2 %dx%d", m2.lines(), m2.columns()));
			System.err.println(String.format("Matrix RT %dx%d", rt.lines(), rt.columns()));
			
			long start = System.currentTimeMillis();
		 
			for (int i = 0; i < 1000; i++){
				Matrix<Double> r = m1.mul(m2);
				Assert.assertEquals(String.format("ID: %d", i), true, rt.equals(r));
			}
			
			System.err.println("Time: " + (System.currentTimeMillis()-start) + "ms");
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
