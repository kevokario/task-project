package com.test.a_project.entities;

import com.test.a_project.entities.Academicyear;
import com.test.a_project.entities.Course;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-12-11T07:56:57")
@StaticMetamodel(Academiclevel.class)
public class Academiclevel_ { 

    public static volatile SingularAttribute<Academiclevel, String> level;
    public static volatile SingularAttribute<Academiclevel, Integer> academiclevelid;
    public static volatile SingularAttribute<Academiclevel, Course> course;
    public static volatile ListAttribute<Academiclevel, Academicyear> academicyearList;

}