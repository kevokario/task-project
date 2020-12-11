/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.test.a_project.entities;

import java.io.Serializable;
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
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author litem
 */
@Entity
@Table(name = "unit", catalog = "education", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Unit.findAll", query = "SELECT u FROM Unit u")
    , @NamedQuery(name = "Unit.findByUnitid", query = "SELECT u FROM Unit u WHERE u.unitid = :unitid")
    , @NamedQuery(name = "Unit.findByName", query = "SELECT u FROM Unit u WHERE u.name = :name")})
public class Unit implements Serializable {

    @Size(max = 25)
    @Column(name = "semester", length = 25)
    private String semester;
    @Size(max = 10)
    @Column(name = "unitcode", length = 10)
    private String unitcode;
    @Size(max = 30)
    @Column(name = "unitname", length = 30)
    private String unitname;
    @Size(max = 4)
    @Column(name = "unitpoint", length = 4)
    private String unitpoint;
    @JoinColumn(name = "academicyear", referencedColumnName = "academicyearid")
    @ManyToOne
    private Academicyear academicyear;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "unitid", nullable = false)
    private Integer unitid;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "name", nullable = false, length = 200)
    private String name;
    @JoinColumn(name = "course", referencedColumnName = "courseid", nullable = false)
    @ManyToOne(optional = false)
    private Course course;

    public Unit() {
    }

    public Unit(Integer unitid) {
        this.unitid = unitid;
    }

    public Unit(Integer unitid, String name) {
        this.unitid = unitid;
        this.name = name;
    }

    public Integer getUnitid() {
        return unitid;
    }

    public void setUnitid(Integer unitid) {
        this.unitid = unitid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (unitid != null ? unitid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Unit)) {
            return false;
        }
        Unit other = (Unit) object;
        if ((this.unitid == null && other.unitid != null) || (this.unitid != null && !this.unitid.equals(other.unitid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.test.a_project.entities.Unit[ unitid=" + unitid + " ]";
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getUnitcode() {
        return unitcode;
    }

    public void setUnitcode(String unitcode) {
        this.unitcode = unitcode;
    }

    public String getUnitname() {
        return unitname;
    }

    public void setUnitname(String unitname) {
        this.unitname = unitname;
    }

    public String getUnitpoint() {
        return unitpoint;
    }

    public void setUnitpoint(String unitpoint) {
        this.unitpoint = unitpoint;
    }

    public Academicyear getAcademicyear() {
        return academicyear;
    }

    public void setAcademicyear(Academicyear academicyear) {
        this.academicyear = academicyear;
    }
    
}
