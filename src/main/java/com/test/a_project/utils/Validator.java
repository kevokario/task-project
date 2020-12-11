package com.test.a_project.utils;

import com.test.a_project.entities.Course;
import com.test.a_project.jpa.TransactionProvider;
import java.util.Date;
import javax.persistence.EntityManager;
import javax.persistence.Query;

public class Validator {

    public static String[] validateName(String name, String variable_type) {
        String[] result = new String[2];
        result[0] = "false";

        if (name.isEmpty() || name.length() == 0) {
            result[1] = variable_type + " name cannot be empty!";
        } else if (name.length() > 0 && name.length() < 3) {
            result[1] = variable_type + " name should contain atleast 3 characters";
        } else {
            result[0] = "true";
            result[1] = "";
        }
        return result;
    }

    public static String[] validateDate(Date date, String variable_type) {
        String[] result = new String[2];
        result[0] = "false";

        if (date == null) {
            result[1] = variable_type + " cannot be empty!";
        } else {
            result[0] = "true";
            result[1] = "";
        }

        return result;
    }

     public static String[] validateCourse(Integer course,Integer institutionid ,EntityManager em, TransactionProvider provider) {
        String[] result = new String[2];
        result[0] = "false";
        Query q = em.createQuery("SELECT c FROM Course c where c.courseid=:cid AND c.institution.institutionid=:i_id");
        q.setParameter("cid",course);
        q.setParameter("i_id",institutionid);
        Course c = provider.getSingleResult(q);
        if (c == null) {
            result[1] = "selected course doesn't Exist!";
        } else {
            result[0] = "true";
            result[1] = "";
        }

        return result;
    }

}
