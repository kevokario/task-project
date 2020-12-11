package com.test.a_project.entities;

import com.test.a_project.entities.Academiclevel;
import com.test.a_project.entities.Unit;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-12-11T07:56:57")
@StaticMetamodel(Academicyear.class)
public class Academicyear_ { 

    public static volatile SingularAttribute<Academicyear, Academiclevel> academiclevel;
    public static volatile SingularAttribute<Academicyear, String> year;
    public static volatile ListAttribute<Academicyear, Unit> unitList;
    public static volatile SingularAttribute<Academicyear, Integer> academicyearid;

}