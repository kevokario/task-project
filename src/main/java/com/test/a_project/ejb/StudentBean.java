package com.test.a_project.ejb;

import com.test.a_project.entities.Institution;
import com.test.a_project.entities.Student;
import com.test.a_project.jpa.TransactionProvider;
import com.test.a_project.utils.JsonResponse;
import com.test.a_project.utils.Validator;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.eclipse.persistence.jpa.jpql.parser.DateTime;

@Stateless
public class StudentBean {

    @EJB
    private TransactionProvider provider;

    //get all students
    public JsonResponse getAllStudents(Integer institutionid) {
        JsonResponse jr = new JsonResponse(500, "Server error", null);
        HashMap hm = new HashMap();
        try {
            EntityManager em = provider.getEM();
            Query q = em.createQuery("Select s from Student s where s.course.institution.institutionid = :institutionid");
            q.setParameter("institutionid", institutionid);
            List<Student> students = provider.getManyFromQuery(q);
            Institution institution = em.find(Institution.class, institutionid);
            HashMap h = new HashMap();
            h.put("name", institution.getName());
            h.put("id", institution.getInstitutionid());

            List list = new ArrayList();
            if (students.isEmpty()) {
                hm.put("students", list);
                hm.put("institution", h);
            } else {
                for (Student st : students) {
                    HashMap shm = new HashMap();
                    shm.put("studentname", st.getName());
                    shm.put("studentid", st.getStudentid());
                    shm.put("student_dob", st.getDateOfBirth());
                    shm.put("course", st.getCourse().getName());
                    shm.put("course_id", st.getCourse().getCourseid());
                    list.add(shm);
                }
                hm.put("students", list);

                hm.put("institution", h);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        jr.setHm(hm);
        jr.setResponse_code(200);
        return jr;
    }

    //get a student
    public JsonResponse getStudent(Integer institutionid, Integer studentid) {
        JsonResponse jr = new JsonResponse(500, "Server error", null);
        HashMap hm = new HashMap();
        try {
            EntityManager em = provider.getEM();
            Query q = em.createQuery("Select s from Student s where s.course.institution.institutionid=:institutionid AND s.studentid =:studentid");
            q.setParameter("studentid", studentid);
            q.setParameter("institutionid", institutionid);
            List<Student> student = provider.getManyFromQuery(q);

            List list = new ArrayList();
            if (student.isEmpty()) {
                hm.put("student", list);
                hm.put("response", "absent");
            } else {
                HashMap shm = new HashMap();
                shm.put("studentname", student.get(0).getName());
                shm.put("studentid", student.get(0).getStudentid());
                shm.put("student_dob", student.get(0).getDateOfBirth());
                HashMap chm = new HashMap();
                chm.put("courseid", student.get(0).getCourse().getCourseid());
                chm.put("name", student.get(0).getCourse().getName());
                shm.put("course",chm);

                hm.put("student", shm);
                HashMap h = new HashMap();
                h.put("name", student.get(0).getCourse().getInstitution().getName());
                h.put("id", student.get(0).getCourse().getInstitution().getInstitutionid());
                hm.put("institution", h);
                hm.put("response", "present");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        jr.setHm(hm);
        jr.setResponse_code(200);
        return jr;
    }

    //add a student
    public JsonResponse addStudent(Student student, Integer institutionid) {
        JsonResponse jr = new JsonResponse(500, "Server error", null);
        HashMap hm = new HashMap();
        HashMap rs = new HashMap();
        try {
            EntityManager em = provider.getEM();
            //add logic here
            //validate.
            if (student != null) {

                String[] name_valid = Validator.validateName(student.getName(), "Student");
                String[] course_valid = Validator.validateCourse(student.getCourse().getCourseid(), institutionid, em, provider);
                String[] dob_valid = Validator.validateDate(student.getDateOfBirth(), "Date of birth");

                if (name_valid[0].equals("true") && course_valid[0].equals("true") && dob_valid[0].equals("true")) {
                    //successful validation
//                    student.setUpdatedOn(new ());
                    student.setUpdatedOn(new Date());
                    if (provider.createEntity(student)) {
                        //successful save
                        hm.put("status", "success");
                        rs.put("message", "Student " + student.getName() + " has been successfuly added!");
                        hm.put("response", rs);
                    } else {
                        //unsuccessful save
                        hm.put("status", "error");
                        rs.put("message", "Student " + student.getName() + " could not be added!");
                        hm.put("response", rs);
                    }
                } else {
                    //failed validation
                    List error_list = new ArrayList();
                    if (name_valid[0].equals("false")) {
                        HashMap h = new HashMap();
                        h.put("name", name_valid[1]);
                        error_list.add(h);
                    }
                    if (course_valid[0].equals("false")) {
                        HashMap h = new HashMap();
                        h.put("course", name_valid[1]);
                        error_list.add(h);
                    }
                    if (dob_valid[0].equals("false")) {
                        HashMap h = new HashMap();
                        h.put("dob", name_valid[1]);
                        error_list.add(h);
                    }
                    hm.put("status", "error");
                    rs.put("message", "Input failed to pass validation");
                    rs.put("validations", error_list);
                    hm.put("response", rs);
                }

            } else {
                hm.put("status", "error");
                rs.put("message", "Please provide Valid Data");
                rs.put("provided", student);
                hm.put("response", rs);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        jr.setHm(hm);
        jr.setResponse_code(200);
        return jr;
    }

    //update student
    public JsonResponse updateStudent(Student student, Integer institutionid) {
        JsonResponse jr = new JsonResponse(500, "Server error", null);
        HashMap hm = new HashMap();
        HashMap rs = new HashMap();
        try {
            EntityManager em = provider.getEM();
            //add logic here
            //validate.
            if (student != null) {

                String[] name_valid = Validator.validateName(student.getName(), "Student");
                String[] course_valid = Validator.validateCourse(student.getCourse().getCourseid(), institutionid, em, provider);
                String[] dob_valid = Validator.validateDate(student.getDateOfBirth(), "Date of birth");

                if (name_valid[0].equals("true") && course_valid[0].equals("true") && dob_valid[0].equals("true")) {
                    //successful validation
                    student.setUpdatedOn(new Date());
                    if (provider.updateEntity(student)) {
                        //successful save
                        hm.put("status", "success");
                        rs.put("message", "Student records has been successfuly updated!");
                        hm.put("response", rs);
                    } else {
                        //unsuccessful save
                        hm.put("status", "error");
                        rs.put("message", "Student records could not be updated!");
                        hm.put("response", rs);
                    }
                } else {
                    //failed validation
                    List error_list = new ArrayList();
                    if (name_valid[0].equals("false")) {
                        HashMap h = new HashMap();
                        h.put("name", name_valid[1]);
                        error_list.add(h);
                    }
                    if (course_valid[0].equals("false")) {
                        HashMap h = new HashMap();
                        h.put("course", name_valid[1]);
                        error_list.add(h);
                    }
                    if (dob_valid[0].equals("false")) {
                        HashMap h = new HashMap();
                        h.put("dob", name_valid[1]);
                        error_list.add(h);
                    }
                    hm.put("status", "error");
                    rs.put("message", "Inpt failed to pass validation");
                    rs.put("", error_list);
                    hm.put("response", rs);
                }

            } else {
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
    
    //update student institution
    public JsonResponse udpateStudentInstitution(Student student){
        JsonResponse jr = new JsonResponse(500, "Server error", null);
        HashMap hm = new HashMap();
        HashMap rs = new HashMap();
        try {
            EntityManager em = provider.getEM();
            //add logic here
            //validate.
            if (student != null) {

                String[] name_valid = Validator.validateName(student.getName(), "Student");
                 String[] dob_valid = Validator.validateDate(student.getDateOfBirth(), "Date of birth");

                if (name_valid[0].equals("true") && dob_valid[0].equals("true")) {
                    //successful validation
                    student.setUpdatedOn(new Date());
                    if (provider.updateEntity(student)) {
                        //successful save
                        hm.put("status", "success");
                        rs.put("message", "Student records has been successfuly updated!");
                        hm.put("response", rs);
                    } else {
                        //unsuccessful save
                        hm.put("status", "error");
                        rs.put("message", "Student records could not be updated!");
                        hm.put("response", rs);
                    }
                } else {
                    //failed validation
                    List error_list = new ArrayList();
                    if (name_valid[0].equals("false")) {
                        HashMap h = new HashMap();
                        h.put("name", name_valid[1]);
                        error_list.add(h);
                    }
                    if (dob_valid[0].equals("false")) {
                        HashMap h = new HashMap();
                        h.put("dob", name_valid[1]);
                        error_list.add(h);
                    }
                    hm.put("status", "error");
                    rs.put("message", "Inpt failed to pass validation");
                    rs.put("", error_list);
                    hm.put("response", rs);
                }

            } else {
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

    //delete student
    public JsonResponse deleteStudent(Integer institutionid, Integer studentid) {
        JsonResponse jr = new JsonResponse(500, "Server error", null);
        HashMap hm = new HashMap();
        HashMap rs = new HashMap();
        try {
            EntityManager em = provider.getEM();
            Query q = em.createQuery("Select s from Student s where s.course.institution.institutionid=:institutionid AND s.studentid =:studentid");
            q.setParameter("studentid", studentid);
            q.setParameter("institutionid", institutionid);
            Student student = provider.getSingleResult(q);
            if (student == null) {
                hm.put("student", new HashMap());
                hm.put("response", "no results found");
            } else {
                //delete the institution
                String name = student.getName();
                //no courses registered
                if (provider.deleteEntity(student)) {
                    hm.put("status", "success");
                    rs.put("message", "Student " + name + " was successfully deleted!");
                    hm.put("response", rs);
                } else {
                    hm.put("status", "error");
                    rs.put("message", "Student " + name + " could not be deleted");
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
