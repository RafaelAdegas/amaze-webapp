package io.redshoes.zoone.admin.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import io.redshoes.zoone.admin.JsonViews;
import io.redshoes.zoone.admin.entity.Beacon;
import io.redshoes.zoone.admin.entity.Establishment;
import io.redshoes.zoone.admin.entity.User;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonBackReference;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonView;


/**
 * The persistent class for the establishment database table.
 * 
 * @author Rafael Adegas <adegasp.rafael@gmail.com>
 */
@Entity
@Table(name="ESTABLISHMENTS")
public class Establishment implements Serializable {

	private static final long serialVersionUID = 1L;

	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id_establishment")
	private Long idEstablishment;
	
	@Column(name="s_cnpj")
	private String cnpj;
	
	@Column(name="s_code")
	private String code;

	@Column(name="s_full_name")
	private String fullName;

	@Column(name="s_short_name")
	private String shortName;

	@Column(name="s_phone")
	private String phone;
	
	@Column(name="s_cep")
	private String cep;
	
	@Column(name="s_state")
	private String state;
	
	@Column(name="s_city")
	private String city;
	
	@Column(name="s_complement")
	private String complement;
	
	@Column(name="s_address")
	private String address;
	
	@OneToMany(cascade=CascadeType.MERGE, mappedBy="establishment")
	private List<User> adminCollection;
	
	@OneToMany(cascade=CascadeType.MERGE, mappedBy="establishment")
	private List<Beacon> beaconCollection;
	
	@JsonView(JsonViews.User.class)
	public Long getIdEstablishment() {
		return this.idEstablishment;
	}

	@JsonView(JsonViews.User.class)
	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	@JsonView(JsonViews.User.class)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@JsonView(JsonViews.User.class)
	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	@JsonView(JsonViews.User.class)
	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	@JsonView(JsonViews.User.class)
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@JsonView(JsonViews.User.class)
	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	@JsonView(JsonViews.User.class)
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@JsonView(JsonViews.User.class)
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@JsonView(JsonViews.User.class)
	public String getComplement() {
		return complement;
	}

	public void setComplement(String complement) {
		this.complement = complement;
	}

	@JsonView(JsonViews.User.class)
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@JsonIgnore
	public List<User> getAdminCollection() {
		return adminCollection;
	}

	public void setAdminCollection(List<User> adminCollection) {
		this.adminCollection = adminCollection;
	}
	
	public void addAdmin(User admin) {
		if(this.adminCollection == null)
			this.adminCollection = new ArrayList<User>();
		this.adminCollection.add(admin);
	}
	
	public void removeAdmin(User admin) {
		if(!this.adminCollection.isEmpty())
			this.adminCollection.remove(admin);
	}

	@JsonIgnore
	public List<Beacon> getBeaconCollection() {
		return beaconCollection;
	}

	public void setBeaconCollection(List<Beacon> beaconCollection) {
		this.beaconCollection = beaconCollection;
	}
	
	public void addBeacon(Beacon beacon) {
		if(this.beaconCollection == null)
			this.beaconCollection = new ArrayList<Beacon>();
		this.beaconCollection.add(beacon);
	}
	
	public void removeBeacon(Beacon beacon) {
		if(!this.beaconCollection.isEmpty())
				this.beaconCollection.remove(beacon);
	}
	
	@Override
	public String toString()
	{
		return String.format("Establishment[%d, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s]", 
				this.idEstablishment, this.code, this.address, this.cep, this.city, 
				this.cnpj, this.complement, this.fullName, this.phone, this.shortName, this.state);
	}
}