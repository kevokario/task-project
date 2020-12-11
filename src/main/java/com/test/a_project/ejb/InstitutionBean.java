/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.test.a_project.ejb;

import com.test.a_project.entities.Course;
import com.test.a_project.entities.Institution;
import com.test.a_project.entities.Student;
import com.test.a_project.jpa.TransactionProvider;
import com.test.a_project.utils.JsonResponse;
import com.test.a_project.utils.Validator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.Query;

@Stateless
public class InstitutionBean {

    @EJB
    private TransactionProvider provider;

    //get all institutions
    public JsonResponse getAllInstitutions() {
        JsonResponse jr = new JsonResponse(500, "Server error", null);
        HashMap hm = new HashMap();
        try {
            EntityManager em = provider.getEM();
            List<Institution> institutions = new ArrayList();
            Query q = em.createQuery("Select i from Institution i ORDER BY i.institutionid DESC");
            institutions = provider.getManyFromQuery(q);

            List i = new ArrayList();
            for (Institution institution : institutions) {
                HashMap m = new HashMap();
                int course_size = 0;
                int student_size = 0;
                Query q_course = em.createQuery("SELECT c from Course c where c.institution.institutionid=:id");
                q_course.setParameter("id", institution.getInstitutionid());
                List<Course> course = provider.getManyFromQuery(q_course);
                
                for(Course c :course){
                    Query q_student = em.createQuery("SELECT s from Student s where s.course.courseid=:id");
                    q_student.setParameter("id", c.getCourseid());
                    List<Student> student = provider.getManyFromQuery(q_student);
                    student_size = student_size + student.size();
                }
                course_size = course.size();
                m.put("institutionid", institution.getInstitutionid());
                m.put("name", institution.getName());
                m.put("courses",course_size);
                m.put("students",student_size);
                i.add(m);
            }

            hm.put("institutions", i);
            hm.put("total", institutions.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
        jr.setHm(hm);
        jr.setMessage("");
        jr.setResponse_code(200);
        return jr;
    }

    //get an institution
    public JsonResponse getInstitution(Integer institutionid) {
        JsonResponse jr = new JsonResponse(500, "Server error", null);
        HashMap hm = new HashMap();
        try {
            EntityManager em = provider.getEM();
            // get institution details
            Query q_institution = em.createQuery("Select i from Institution i where i.institutionid = :institutionid");
            q_institution.setParameter("institutionid", institutionid);
            Institution i = provider.getSingleResult(q_institution);

            if (i == null) {
                hm.put("institution", new HashMap());
                hm.put("response", "no results found");
            } else {
                //get institution facultyies
                int faculties = 4;

                //get departments
                int departments = 8;

                //get courses offered
                Query q_course = em.createQuery("Select c from Course c where c.institution.institutionid = :institutionid");
                q_course.setParameter("institutionid", institutionid);
                List<Course> institution_courses = provider.getManyFromQuery(q_course);

                //get students enrolled
                Query q_student = em.createQuery("Select s from Student s where s.course.institution.institutionid = :institutionid");
                q_student.setParameter("institutionid", institutionid);
                List<Course> institution_students = provider.getManyFromQuery(q_student);

                HashMap m = new HashMap();
                m.put("institutionid", i.getInstitutionid());
                m.put("name", i.getName());
                m.put("schools", faculties);
                m.put("departments", departments);
                m.put("courses", institution_courses.size());
                m.put("students", institution_students.size());
                hm.put("institution", m);
                hm.put("response", "results found");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        jr.setHm(hm);
        jr.setMessage("");
        jr.setResponse_code(200);
        return jr;
    }

    //add an institution
    public JsonResponse addInstitution(Institution institution) {
        JsonResponse jr = new JsonResponse(500, "Server error", null);
        HashMap hm = new HashMap();
        HashMap rs = new HashMap();
        try {
            EntityManager em = provider.getEM();

            if (institution != null) {
                //institution object provided
                String name = institution.getName();
                Object[] name_valid = Validator.validateName(name, "Institution");

                if (name_valid[0].equals("true")) {
                    //validation pass
                    Query q = em.createQuery("Select i from Institution i WHERE i.name = :name");
                    q.setParameter("name", name);
                    List<Institution> i = provider.getManyFromQuery(q);

                    if (i.size() == 0) {
                        //proceed to save data into the database
                        if (provider.createEntity(institution)) {
                            //institution save success
                            hm.put("status", "success");
                            rs.put("message", "Institution " + name + " has been successfuly added!");
                            hm.put("response", rs);
                        } else {
                            //institution save error
                            hm.put("status", "error");
                            rs.put("message", "Institution " + name + " could not be added!");
                            hm.put("response", rs);
                        }
                    } else {
                        //institution exists
                        hm.put("status", "error");
                        rs.put("message", "Institution " + name + " Already Exists!");
                        hm.put("response", rs);
                    }
                } else {
                    //failed validation
                    hm.put("status", "error");
                    rs.put("message", name_valid[1]);
                    hm.put("response", rs);
                }
            } else {
                //no institution object provided
                hm.put("status", "error");
                rs.put("message", "Please provide Valid Data");
                hm.put("response", rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        jr.setHm(hm);
        jr.setResponse_code(200);
        return jr;
    }

    //update an institution
    public JsonResponse updateInstitution(Institution institution) {
        JsonResponse jr = new JsonResponse(500, "Server error", null);
        HashMap hm = new HashMap();
        HashMap rs = new HashMap();
        try {
            EntityManager em = provider.getEM();
            if (institution != null) {
                //institution object provided
                String name = institution.getName();
                Object[] name_valid = Validator.validateName(name, "Institution");

                if (name_valid[0].equals("true")) {
                    //validation pass
                    Query q = em.createQuery("Select i from Institution i WHERE i.name = :name and i.institutionid <> :institutionid");
                    q.setParameter("name", name);
                    q.setParameter("institutionid",institution.getInstitutionid());
                    List<Institution> i = provider.getManyFromQuery(q);

                    if (i.isEmpty()) {
                        //proceed to save data into the database
                        if (provider.updateEntity(institution)) {
                            //institution save success
                            hm.put("status", "success");
                            rs.put("message", "Institution " + name + " has been successfuly Updated!");
                            hm.put("response", rs);
                        } else {
                            //institution save error
                            hm.put("status", "error");
                            rs.put("message", "Institution " + name + " could not be Updated!");
                            hm.put("response", rs);
                        }
                    } else {
                        //institution exists
                        hm.put("status", "error");
                        rs.put("message", "This institution Already Exists!");
                        hm.put("response", rs);
                    }
                } else {
                    //failed validation
                    hm.put("status", "error");
                    rs.put("message", name_valid[1]);
                    hm.put("response", rs);
                }
            } else {
                //no institution object provided
                hm.put("status", "error");
                rs.put("message", "Please provide Valid Data");
                hm.put("response", rs);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        jr.setHm(hm);
        jr.setResponse_code(200);
        return jr;
    }

    //delete an institution
    public JsonResponse deleteInstitution(Integer institutionid) {
        JsonResponse jr = new JsonResponse(500, "Server error", null);
        HashMap hm = new HashMap();
        HashMap rs = new HashMap();
        try {
            EntityManager em = provider.getEM();
            Institution i = em.find(Institution.class, institutionid);
            if (i == null) {
                hm.put("institution", new HashMap());
                hm.put("response", "no results found");
            } else {
                //delete the institution
                String name = i.getName();

                //check if institution has course
                Query q_institution_course = em.createQuery("Select c from Course c WHERE c.institution.institutionid = :institutionid");
                q_institution_course.setParameter("institutionid", institutionid);
                List<Course> institution_course = provider.getManyFromQuery(q_institution_course);

                if (institution_course.isEmpty()) {
                    //no courses registered
                    if (provider.deleteEntity(i)) {
                        hm.put("status", "success");
                        rs.put("message", "Institution " + name + " was successfully deleted!");
                        hm.put("response", rs);
                    } else {
                        hm.put("status", "error");
                        rs.put("message", "Institution " + name + " could not be deleted");
                        hm.put("response", rs);
                    }
                } else {
                    //course registered
                    hm.put("status", "error");
                    rs.put("message", "Institution " + name + " has Registered Courses!");
                    hm.put("response", rs);
                }

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        jr.setHm(hm);
        jr.setMessage("");
        jr.setResponse_code(200);
        return jr;
    }
}
