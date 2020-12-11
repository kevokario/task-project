package com.test.a_project.entities;

import com.test.a_project.entities.Academicyear;
import com.test.a_project.entities.Course;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-12-11T07:56:57")
@StaticMetamodel(Unit.class)
public class Unit_ { 

    public static volatile SingularAttribute<Unit, Academicyear> academicyear;
    public static volatile SingularAttribute<Unit, String> unitcode;
    public static volatile SingularAttribute<Unit, String> unitname;
    public static volatile SingularAttribute<Unit, String> name;
    public static volatile SingularAttribute<Unit, Integer> unitid;
    public static volatile SingularAttribute<Unit, Course> course;
    public static volatile SingularAttribute<Unit, String> semester;
    public static volatile SingularAttribute<Unit, String> unitpoint;

}