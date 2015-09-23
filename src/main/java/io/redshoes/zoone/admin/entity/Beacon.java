package io.redshoes.zoone.admin.entity;

import io.redshoes.zoone.admin.JsonViews;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonView;


/**
 * The persistent class for the beacon database table.
 * 
 * @author Rafael Adegas <adegasp.rafael@gmail.com>
 */
@Entity
@Table(name="BEACONS")
public class Beacon implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	
	@Id
	@GeneratedValue
	private Long idBeacon;
	
	@Column(name="s_code")
	private String code;

	@Column(name="s_major")
	private String major;

	@Column(name="s_minor")
	private String minor;

	@Column(name="s_uuid", nullable = false)
	private String uuid;

	@ManyToOne(targetEntity=Establishment.class)
	@JoinColumn(name="id_establishment")
	private Establishment establishment;
	
	@Column(name="s_description")
	private String description;
	
	@Column(name="s_locale")
	private String locale;
	
	

	public Long getIdBeacon() {
		return idBeacon;
	}

	@JsonView(JsonViews.User.class)
	public Establishment getEstablishment() {
		return establishment;
	}
	public void setEstablishment(Establishment establishment) {
		this.establishment = establishment;
	}
	
	@JsonView(JsonViews.User.class)
	public String getCode() {
		return this.code;
	}
	public void setCode(String code) {
		this.code = code;
	}

	@JsonView(JsonViews.User.class)
	public String getMajor() {
		return major;
	}
	public void setMajor(String major) {
		this.major = major;
	}

	@JsonView(JsonViews.User.class)
	public String getMinor() {
		return minor;
	}
	public void setMinor(String minor) {
		this.minor = minor;
	}

	@JsonView(JsonViews.User.class)
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	@JsonView(JsonViews.User.class)
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}

	@JsonView(JsonViews.User.class)
	public String getLocale() {
		return locale;
	}
	
	public void setLocale(String locale) {
		this.locale = locale;
	}
	
	@Override
	public String toString()
	{
		return String.format("Beacon[%d, %s, %s, %s, %s, %s]", this.idBeacon, this.major, this.minor, this.uuid, this.description, this.locale);
	}

}