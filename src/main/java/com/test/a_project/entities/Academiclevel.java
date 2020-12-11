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
@Table(name = "academiclevel", catalog = "education", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Academiclevel.findAll", query = "SELECT a FROM Academiclevel a")
    , @NamedQuery(name = "Academiclevel.findByAcademiclevelid", query = "SELECT a FROM Academiclevel a WHERE a.academiclevelid = :academiclevelid")
    , @NamedQuery(name = "Academiclevel.findByLevel", query = "SELECT a FROM Academiclevel a WHERE a.level = :level")})
public class Academiclevel implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "academiclevelid", nullable = false)
    private Integer academiclevelid;
    @Size(max = 50)
    @Column(name = "level", length = 50)
    private String level;
    @JoinColumn(name = "course", referencedColumnName = "courseid")
    @ManyToOne
    private Course course;
    @OneToMany(mappedBy = "academiclevel")
    private List<Academicyear> academicyearList;

    public Academiclevel() {
    }

    public Academiclevel(Integer academiclevelid) {
        this.academiclevelid = academiclevelid;
    }

    public Integer getAcademiclevelid() {
        return academiclevelid;
    }

    public void setAcademiclevelid(Integer academiclevelid) {
        this.academiclevelid = academiclevelid;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    @XmlTransient
    public List<Academicyear> getAcademicyearList() {
        return academicyearList;
    }

    public void setAcademicyearList(List<Academicyear> academicyearList) {
        this.academicyearList = academicyearList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (academiclevelid != null ? academiclevelid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Academiclevel)) {
            return false;
        }
        Academiclevel other = (Academiclevel) object;
        if ((this.academiclevelid == null && other.academiclevelid != null) || (this.academiclevelid != null && !this.academiclevelid.equals(other.academiclevelid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.test.a_project.entities.Academiclevel[ academiclevelid=" + academiclevelid + " ]";
    }
    
}
