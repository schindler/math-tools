package br.schindler.math.matrix.load.octave;

import java.io.BufferedReader;
import java.io.IOException;

import br.schindler.math.matrix.Matrix;
import br.schindler.math.matrix.load.Loader;
import br.schindler.math.matrix.load.MatrixFactory;

public class OctaveLoader<N> implements Loader<N> {

	/*
	 * 
	 */
	private MatrixFactory<N> factory;
 
	/**
	 * 
	 * @param f
	 */
	public OctaveLoader(MatrixFactory<N> f) {
		this.factory=f;
	}

	/*
	 * (non-Javadoc)
	 * @see br.schindler.math.matrix.load.Loader#load(java.io.BufferedReader)
	 */
	@Override
	public Matrix<N> load(BufferedReader input) {
		Matrix<N> result = null;
		try {
			String type = getFieldValue(input, "type");

			if (type.equals("matrix")) {
				int rows = Integer.parseInt(getFieldValue(input, "rows"));
				int columns = Integer.parseInt(getFieldValue(input, "columns"));

				result = this.factory.create(rows, columns);

				for (int i = 0; i < rows; i++) {
					String[] strcols = input.readLine().trim().split(" ");
					for (int j = 0; j < columns; j++) {
						result.set(i, j, this.factory.parseField(strcols[j]));
					}
				}
			} else if (type.equals("scalar")) {
				result = this.factory.create(1, 1);
				result.set(0, 0, this.factory.parseField( input.readLine().trim()));
			} else {
				throw new RuntimeException("invalid octave type " + type);
			}

		} catch (IOException e) {
			//throw new RuntimeException(e);
			result = null;
		}

		return result;
	}
	
	/**
	 * 
	 * @param input
	 * @param name
	 * @return
	 * @throws IOException
	 */
	protected String getFieldValue(BufferedReader input, String name)
			throws IOException {

		String line   = null;
		String result = "";
		boolean found = false;

		while ((line = input.readLine()) != null) {
			String[] fields = line.split(":");

			if (fields.length > 1) {
				if (fields[0].indexOf(name) >= 0) {
					result = (fields[1].trim());
					found = true;
					break;
				}
			}
		}

		if (!found)
			throw new IOException("field " + name + " not found!");

		return result;
	}
}
