package br.schindler.math.matrix;

import br.schindler.math.matrix.operations.Function;


/**
 * Interface padr�o para objetos que implementa�o opera��es com matrizes
 * @author fernando.schindler@gmail.com
 */
public interface Matrix<T> {

	/**
	 * Recuperar um elemento da Matrix<T>
	 * @param i Linha sendo 0 (ZERO) o primeiro elemento
	 * @param j Coluna sendo 0 (ZERO) o primeiro elemento 
	 * @return The Matrix<T> element
	 * @see set
	 */
	T get(int i, int j);

	/**
	 * Recuperar uma sub-matrix
	 * @param fromLine linha inicial
	 * @param lines    numero de linhas
	 * @param fromCol  coluna inicial
	 * @param columns  numero de colunas
	 * @return NOVA sub matrix formada 
	 */
	Matrix<T> get(int fromLine, int lines, int fromCol, int toCol);


	/**
	 * Copiar valores de outra matrix para essa
	 * @param fromLine linha inicial
	 * @param lines    numero de linhas
	 * @param fromCol  coluna inicial
	 * @param columns  numero de colunas
	 * @param other    Matrix con os novos valores
	 * @return Essa matrix com as altera��es efetuadas
	 */
	Matrix<T> set(int fromLine, int lines, int fromCol, int columns, Matrix<T> other);
	
	
	/**
	 * Recuperar uma sub-matrix
	 * @param lines    Linhas as quais ser�o ajustadas
	 * @param columns  Colunas as quais ser�o ajustadas
	 * @return NOVA sub matrix formada 
	 */	
	Matrix<T> set(Iterable<Integer> lines, Iterable<Integer> columns, Matrix<T> other);

	/**
	 * Ajustar um elemento da Matrix<T>
	 * @param i linha
	 * @param j coluna 
	 * @param elem novo valor
	 * @return Essa matrix com as altera��es efetuadas
	 */
	Matrix<T> set(int i, int j, T elem);

	/**
	 * Preencher todos os elementos com o valor {@code elem}
	 * @param elem novo valor para todos os elementos da matrix
	 * @return Essa matrix com as altera��es efetuadas
	 */
	Matrix<T> fill(T elem);

	/**
	 * Preenche uma linha com {@code elem}
	 * @param i linha
	 * @param elem novo valor
	 * @return Essa matrix com as altera��es efetuadas 
	 */
	Matrix<T> fill(int i, T elem);

	/**
	 * Adiciona {@code elem} para todos os elementos
	 * @param elem elemento a ser adicionado
	 * @return Essa matrix com as altera��es efetuadas 
	 */
	Matrix<T> increment(T elem);
	
	/**
	 * Subtrai {@code elem} para todos os elementos
	 * @param elem elemento a ser adicionado
	 * @return Essa matrix com as altera��es efetuadas 
	 */
	Matrix<T> decrement(T elem);
	
	/**
	 * Adiciona {@code elem} para todos os elementos
	 * @param elem  elemento a ser adicionado
	 * @return NOVA matrix com as altera��es efetuadas 
	 */
	Matrix<T> add(T elem);
	
	/**
	 * Opera��o de Adi��o
	 * @param other Deve ter a mesma dimens�o desta
	 * @return  NOVA matrix resultado da SOMA
	 */
	Matrix<T> add(Matrix<T> other);
	
	/**
	 * Opera��o de subtra��o {@code elem} para todos os elementos
	 * @param elem  elemento a ser adicionado
	 * @return NOVA matrix com as altera��es efetuadas 
	 */
	Matrix<T> sub(T elem);
	
	/**
	 * Subtra��o
	 * @param other Deve ter a mesma dimens�o desta
	 * @return  NOVA matrix resultado da subtra��o
	 */
	Matrix<T> sub(Matrix<T> other);
	
	/**
	 * Multiplica todos os elementos por {@code elem}
	 * @param elem elemento a ser multiplicado
	 * @return  Essa matrix com o resultado da multiplica��o
	 * @see mul
	 */
	Matrix<T> scale(T elem);
 	
	/**
	 * Multiplica todos os elementos por {@code elem}
	 * @param elem
	 * @return NOVA matrix com as altera��es efetuadas 
	 * @see scale
	 */
	Matrix<T> mul(T elem); 
	
	/**
	 * Multipli��o 
	 * @param other 
	 * @return NOVA matrix com as altera��es efetuadas 
	 */
	Matrix<T> mul(Matrix<T> other); 
	
	/**
	 * @return NOVA matrix transposta
	 */
	Matrix<T> transpose();
	
	/**
	 * Aplicar a fun��o em todos elementos
	 * @param func
	 * @return Essa matrix com as altera��es efetuadas
	 */
	Matrix<T> call(Function<T> func);
	
	/**
	 * Aplicar a fun��o em todos elementos
	 * @param lines    Linhas as quais {@code func} ser� aplicada
	 * @param columns  Colunas as quais {@code func} ser� aplicada
	 * @param func
	 * @return Essa matrix com as altera��es efetuadas
	 */
	Matrix<T> call(Iterable<Integer> lines, Iterable<Integer> columns, Function<T> func);
	
	/**
	 * Criar uma matrix com as dimens�es especificadas
	 * @param lines
	 * @param columns
	 * @return Nova matrix
	 */
	Matrix<T> create(int lines, int columns);

	/**
	 * @return numero de linhas
	 */
	int lines();
	
	/**
	 * @return numero de colunas
	 */
	int columns();
}
