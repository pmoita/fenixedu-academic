/*
 * Turma.java
 *
 * Created on 17 de Outubro de 2002, 19:07
 */

package Dominio;

/**
 *
 * @author  tfc130
 */

public class Turma implements ITurma {
	protected String _nome;
	
	protected Integer _anoCurricular;
	

	// c�digos internos da base de dados
	private Integer _codigoInterno;
	

	private ICursoExecucao executionDegree;
	private Integer keyExecutionDegree;
	
	private IExecutionPeriod executionPeriod;
	private Integer keyExecutionPeriod;

	/** Construtor sem argumentos p�blico requerido pela moldura de objectos OJB */
	public Turma() {
	}

/**
 * @deprecated
 * @param nome
 * @param semestre
 * @param anoCurricular
 * @param licenciatura
 */
	public Turma(
		String nome,
		Integer semestre,
		Integer anoCurricular,
		ICurso licenciatura) {
		setNome(nome);
	//	setSemestre(semestre);
		setAnoCurricular(anoCurricular);
	//	setLicenciatura(licenciatura);
	}
	
	public Turma(
			String nome,
			Integer anoCurricular,
			ICursoExecucao executionDegree,
			IExecutionPeriod executionPeriod) {
			setNome(nome);
			setAnoCurricular(anoCurricular);
			setExecutionDegree(executionDegree);
			setExecutionPeriod(executionPeriod);
		}

	public boolean equals(Object obj) {
		boolean resultado = false;
		if (obj instanceof ITurma) {
			ITurma turma = (ITurma) obj;
			resultado =
				getNome().equals(turma.getNome())
					&& getExecutionDegree().equals(turma.getExecutionDegree())
					&& getExecutionPeriod().equals(turma.getExecutionPeriod());
		}
		return resultado;
	}

	public String toString() {
		String result = "[TURMA";
		result += ", codigoInterno=" + _codigoInterno;
		result += ", nome=" + _nome;
		result += ", executionPeriod=" + executionPeriod;
		result += ", executionDegree=" + executionDegree;
		result += "]";
		return result;
	}

	/**
	 * Returns the anoCurricular.
	 * @return Integer
	 */
	public Integer getAnoCurricular() {
		return _anoCurricular;
	}

	

	/**
	 * Returns the nome.
	 * @return String
	 */
	public String getNome() {
		return _nome;
	}

	

	/**
	 * Returns the executionDegree.
	 * @return ICursoExecucao
	 */
	public ICursoExecucao getExecutionDegree() {
		return executionDegree;
	}

	/**
	 * Returns the keyExecutionDegree.
	 * @return Integer
	 */
	public Integer getKeyExecutionDegree() {
		return keyExecutionDegree;
	}

	/**
	 * Sets the anoCurricular.
	 * @param anoCurricular The anoCurricular to set
	 */
	public void setAnoCurricular(Integer anoCurricular) {
		_anoCurricular = anoCurricular;
	}

	
	/**
	 * Sets the nome.
	 * @param nome The nome to set
	 */
	public void setNome(String nome) {
		_nome = nome;
	}

	
	/**
	 * Sets the executionDegree.
	 * @param executionDegree The executionDegree to set
	 */
	public void setExecutionDegree(ICursoExecucao executionDegree) {
		this.executionDegree = executionDegree;
	}

	/**
	 * Sets the keyExecutionDegree.
	 * @param keyExecutionDegree The keyExecutionDegree to set
	 */
	public void setKeyExecutionDegree(Integer keyExecutionDegree) {
		this.keyExecutionDegree = keyExecutionDegree;
	}

	

	/**
	 * Returns the codigoInterno.
	 * @return Integer
	 */
	public Integer getCodigoInterno() {
		return _codigoInterno;
	}

	/**
	 * Returns the executionPeriod.
	 * @return IExecutionPeriod
	 */
	public IExecutionPeriod getExecutionPeriod() {
		return executionPeriod;
	}

	/**
	 * Returns the keyExecutionPeriod.
	 * @return Integer
	 */
	public Integer getKeyExecutionPeriod() {
		return keyExecutionPeriod;
	}

	

	/**
	 * Sets the codigoInterno.
	 * @param codigoInterno The codigoInterno to set
	 */
	public void setCodigoInterno(Integer codigoInterno) {
		_codigoInterno = codigoInterno;
	}

	/**
	 * Sets the executionPeriod.
	 * @param executionPeriod The executionPeriod to set
	 */
	public void setExecutionPeriod(IExecutionPeriod executionPeriod) {
		this.executionPeriod = executionPeriod;
	}

	/**
	 * Sets the keyExecutionPeriod.
	 * @param keyExecutionPeriod The keyExecutionPeriod to set
	 */
	public void setKeyExecutionPeriod(Integer keyExecutionPeriod) {
		this.keyExecutionPeriod = keyExecutionPeriod;
	}

}
