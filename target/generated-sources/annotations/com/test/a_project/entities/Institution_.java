package com.test.a_project.entities;

import com.test.a_project.entities.Course;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-12-11T07:56:57")
@StaticMetamodel(Institution.class)
public class Institution_ { 

    public static volatile SingularAttribute<Institution, Integer> institutionid;
    public static volatile SingularAttribute<Institution, String> name;
    public static volatile ListAttribute<Institution, Course> courseList;

}