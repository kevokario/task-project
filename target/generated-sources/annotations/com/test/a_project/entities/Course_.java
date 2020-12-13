package com.test.a_project.entities;

import com.test.a_project.entities.Institution;
import com.test.a_project.entities.Student;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-12-13T04:11:06")
@StaticMetamodel(Course.class)
public class Course_ { 

    public static volatile SingularAttribute<Course, Institution> institution;
    public static volatile SingularAttribute<Course, String> name;
    public static volatile ListAttribute<Course, Student> studentList;
    public static volatile SingularAttribute<Course, Integer> courseid;

}