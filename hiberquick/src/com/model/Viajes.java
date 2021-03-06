package com.model;
// Generated 24 ene. 2021 15:49:25 by Hibernate Tools 5.2.12.Final

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Viajes generated by hbm2java
 */
@Entity
@Table(name = "viajes", catalog = "trenes")
public class Viajes implements java.io.Serializable {

	private int codViaje;
	private Estaciones estacionesByEstacionDst;
	private Estaciones estacionesByEstacionOg;
	private Integer nombre;

	public Viajes() {
	}

	public Viajes(int codViaje) {
		this.codViaje = codViaje;
	}

	public Viajes(int codViaje, Estaciones estacionesByEstacionDst, Estaciones estacionesByEstacionOg, Integer nombre) {
		this.codViaje = codViaje;
		this.estacionesByEstacionDst = estacionesByEstacionDst;
		this.estacionesByEstacionOg = estacionesByEstacionOg;
		this.nombre = nombre;
	}

	@Id

	@Column(name = "cod_viaje", unique = true, nullable = false)
	public int getCodViaje() {
		return this.codViaje;
	}

	public void setCodViaje(int codViaje) {
		this.codViaje = codViaje;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "estacion_dst")
	public Estaciones getEstacionesByEstacionDst() {
		return this.estacionesByEstacionDst;
	}

	public void setEstacionesByEstacionDst(Estaciones estacionesByEstacionDst) {
		this.estacionesByEstacionDst = estacionesByEstacionDst;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "estacion_og")
	public Estaciones getEstacionesByEstacionOg() {
		return this.estacionesByEstacionOg;
	}

	public void setEstacionesByEstacionOg(Estaciones estacionesByEstacionOg) {
		this.estacionesByEstacionOg = estacionesByEstacionOg;
	}

	@Column(name = "nombre")
	public Integer getNombre() {
		return this.nombre;
	}

	public void setNombre(Integer nombre) {
		this.nombre = nombre;
	}

}
