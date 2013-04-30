/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app.entities;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author JB
 */
@Entity
@Table(uniqueConstraints = {
    @UniqueConstraint(columnNames = {"name"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Buildings.findAll", query = "SELECT b FROM Buildings b"),
    @NamedQuery(name = "Buildings.findByBuildingId", query = "SELECT b FROM Buildings b WHERE b.buildingId = :buildingId"),
    @NamedQuery(name = "Buildings.findByName", query = "SELECT b FROM Buildings b WHERE b.name = :name"),
    @NamedQuery(name = "Buildings.findByDescription", query = "SELECT b FROM Buildings b WHERE b.description = :description"),
    @NamedQuery(name = "Buildings.findByStreet", query = "SELECT b FROM Buildings b WHERE b.street = :street"),
    @NamedQuery(name = "Buildings.findByCity", query = "SELECT b FROM Buildings b WHERE b.city = :city"),
    @NamedQuery(name = "Buildings.findByPostalCode", query = "SELECT b FROM Buildings b WHERE b.postalCode = :postalCode")})
public class Buildings implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "building_id", nullable = false)
    private Integer buildingId;
    @Basic(optional = false)
    @Column(nullable = false, length = 128)
    private String name;
    @Column(length = 256)
    private String description;
    @Basic(optional = false)
    @Column(nullable = false, length = 128)
    private String street;
    @Basic(optional = false)
    @Column(nullable = false, length = 58)
    private String city;
    @Basic(optional = false)
    @Column(name = "postal_code", nullable = false, length = 12)
    private String postalCode;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "buildingId")
    private Collection<Rooms> roomsCollection;

    public Buildings() {
    }

    public Buildings(Integer buildingId) {
        this.buildingId = buildingId;
    }

    public Buildings(Integer buildingId, String name, String street, String city, String postalCode) {
        this.buildingId = buildingId;
        this.name = name;
        this.street = street;
        this.city = city;
        this.postalCode = postalCode;
    }

    public Integer getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(Integer buildingId) {
        this.buildingId = buildingId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    @XmlTransient
    public Collection<Rooms> getRoomsCollection() {
        return roomsCollection;
    }

    public void setRoomsCollection(Collection<Rooms> roomsCollection) {
        this.roomsCollection = roomsCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (buildingId != null ? buildingId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Buildings)) {
            return false;
        }
        Buildings other = (Buildings) object;
        if ((this.buildingId == null && other.buildingId != null) || (this.buildingId != null && !this.buildingId.equals(other.buildingId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "app.entities.Buildings[ buildingId=" + buildingId + " ]";
    }
    
}
