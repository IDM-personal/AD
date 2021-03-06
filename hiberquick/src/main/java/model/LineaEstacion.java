package model;
// Generated 24 ene. 2021 12:39:39 by Hibernate Tools 5.0.6.Final

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * LineaEstacion generated by hbm2java
 */
@Entity
@Table(name = "linea_estacion", catalog = "trenes", uniqueConstraints = @UniqueConstraint(columnNames = { "orden",
		"cod_linea" }))
public class LineaEstacion implements java.io.Serializable {

	private LineaEstacionId id;
	private Estaciones estaciones;
	private Lineas lineas;
	private Integer orden;

	public LineaEstacion() {
	}

	public LineaEstacion(LineaEstacionId id, Estaciones estaciones, Lineas lineas) {
		this.id = id;
		this.estaciones = estaciones;
		this.lineas = lineas;
	}

	public LineaEstacion(LineaEstacionId id, Estaciones estaciones, Lineas lineas, Integer orden) {
		this.id = id;
		this.estaciones = estaciones;
		this.lineas = lineas;
		this.orden = orden;
	}

	@EmbeddedId

	@AttributeOverrides({ @AttributeOverride(name = "codLinea", column = @Column(name = "cod_linea", nullable = false)),
			@AttributeOverride(name = "codEstacion", column = @Column(name = "cod_estacion", nullable = false)) })
	public LineaEstacionId getId() {
		return this.id;
	}

	public void setId(LineaEstacionId id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cod_estacion", nullable = false, insertable = false, updatable = false)
	public Estaciones getEstaciones() {
		return this.estaciones;
	}

	public void setEstaciones(Estaciones estaciones) {
		this.estaciones = estaciones;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cod_linea", nullable = false, insertable = false, updatable = false)
	public Lineas getLineas() {
		return this.lineas;
	}

	public void setLineas(Lineas lineas) {
		this.lineas = lineas;
	}

	@Column(name = "orden")
	public Integer getOrden() {
		return this.orden;
	}

	public void setOrden(Integer orden) {
		this.orden = orden;
	}

}
