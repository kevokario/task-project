/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.test.a_project.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author kario
 */
@Entity
@Table(name = "academicyear", catalog = "education", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Academicyear.findAll", query = "SELECT a FROM Academicyear a")
    , @NamedQuery(name = "Academicyear.findByAcademicyearid", query = "SELECT a FROM Academicyear a WHERE a.academicyearid = :academicyearid")
    , @NamedQuery(name = "Academicyear.findByYear", query = "SELECT a FROM Academicyear a WHERE a.year = :year")})
public class Academicyear implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "academicyearid", nullable = false)
    private Integer academicyearid;
    @Size(max = 10)
    @Column(name = "year", length = 10)
    private String year;
    @JoinColumn(name = "academiclevel", referencedColumnName = "academiclevelid")
    @ManyToOne
    private Academiclevel academiclevel;
    @OneToMany(mappedBy = "academicyear")
    private List<Unit> unitList;

    public Academicyear() {
    }

    public Academicyear(Integer academicyearid) {
        this.academicyearid = academicyearid;
    }

    public Integer getAcademicyearid() {
        return academicyearid;
    }

    public void setAcademicyearid(Integer academicyearid) {
        this.academicyearid = academicyearid;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public Academiclevel getAcademiclevel() {
        return academiclevel;
    }

    public void setAcademiclevel(Academiclevel academiclevel) {
        this.academiclevel = academiclevel;
    }

    @XmlTransient
    public List<Unit> getUnitList() {
        return unitList;
    }

    public void setUnitList(List<Unit> unitList) {
        this.unitList = unitList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (academicyearid != null ? academicyearid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Academicyear)) {
            return false;
        }
        Academicyear other = (Academicyear) object;
        if ((this.academicyearid == null && other.academicyearid != null) || (this.academicyearid != null && !this.academicyearid.equals(other.academicyearid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.test.a_project.entities.Academicyear[ academicyearid=" + academicyearid + " ]";
    }
    
}
