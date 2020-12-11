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

/**
 * This Bean is for courses module!
 */
@Stateless
public class CourseBean {

    @EJB
    private TransactionProvider provider;

    //get all courses by institution
    public JsonResponse getAllCourses() {
        JsonResponse jr = new JsonResponse(500, "Server error", null);
        HashMap hm = new HashMap();

        try {
            EntityManager em = provider.getEM();
            Query q = em.createQuery("Select c from Course c");
            List<Course> courses = provider.getManyFromQuery(q);
            List list = new ArrayList();
            //empty course
            if (courses.isEmpty()) {
                hm.put("courses", new HashMap());
                hm.put("institution", new HashMap());
            } else {

                for (Course course : courses) {
                    //get Students taking course
                    Query q_student = em.createQuery("Select s from Student s where s.course.courseid = :courseid ");
                    q_student.setParameter("courseid", course.getCourseid());
                    List<Student> students = provider.getManyFromQuery(q_student);
                    HashMap c = new HashMap();
                    HashMap i = new HashMap();
                    c.put("name", course.getName());
                    c.put("courseid", course.getCourseid());
                    c.put("students", students.size());
                    i.put("name", course.getInstitution().getName());
                    i.put("institutionid", course.getInstitution().getInstitutionid());
                    c.put("institution", i);
                    list.add(c);
                }
                hm.put("courses", list);
            }
            jr.setHm(hm);
            jr.setResponse_code(200);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return jr;
    }

    //get all courses by institution id
    public JsonResponse getCoursesByInstitutionId(Integer institution_id) {
        JsonResponse jr = new JsonResponse(500, "Server error", null);
        HashMap hm = new HashMap();
        try {
            EntityManager em = provider.getEM();

            Institution institution = provider.getEntity(Institution.class, institution_id);

            Query q = em.createQuery("SELECT m FROM Course m WHERE m.institution.institutionid = :institution_id");
            q.setParameter("institution_id", institution_id);
            List<Course> courses = provider.getManyFromQuery(q);
            List list = new ArrayList();
            if (courses.isEmpty()) {
                hm.put("courses", list);
                HashMap i = new HashMap();
                i.put("name", institution.getName());
                i.put("institutionnid", institution.getInstitutionid());
                hm.put("institution", i);
            } else {
                for (Course course : courses) {
                    //get Students taking course
                    Query q_student = em.createQuery("Select s from Student s where s.course.courseid = :courseid ");
                    q_student.setParameter("courseid", course.getCourseid());
                    List<Student> students = provider.getManyFromQuery(q_student);
                    HashMap c = new HashMap();
                    HashMap i = new HashMap();
                    c.put("name", course.getName());
                    c.put("courseid", course.getCourseid());
                    c.put("students", students.size());
                    i.put("name", institution.getName());
                    i.put("institutionid", course.getInstitution().getInstitutionid());
                    c.put("institution", i);
                    list.add(c);
                }
                hm.put("courses", list);
            }
            jr.setHm(hm);
            jr.setResponse_code(200);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return jr;
        }
    }
    
    //get a course by institution id,courseid
    public JsonResponse getCourseByInstitutionId(Integer institution_id, Integer courseid) {
        JsonResponse jr = new JsonResponse(500, "Server error", null);
        HashMap hm = new HashMap();
        try {
            EntityManager em = provider.getEM();
            Institution institution = provider.getEntity(Institution.class, institution_id);
            Query q = em.createQuery("SELECT m FROM Course m WHERE m.institution.institutionid = :institution_id and m.courseid=:courseid");
            q.setParameter("institution_id", institution_id);
            q.setParameter("courseid", courseid);
            List<Course> courses = provider.getManyFromQuery(q);
            List list = new ArrayList();
            if (courses.isEmpty()) {
                hm.put("courses", list);
                HashMap i = new HashMap();
                i.put("name", institution.getName());
                i.put("institutionnid", institution.getInstitutionid());
                hm.put("institution", i);
            } else {
                for (Course course : courses) {
                    //get Students taking course
                    Query q_student = em.createQuery("Select s from Student s where s.course.courseid = :courseid ");
                    q_student.setParameter("courseid", course.getCourseid());
                    List<Student> students = provider.getManyFromQuery(q_student);
                    HashMap c = new HashMap();
                    HashMap i = new HashMap();
                    c.put("name", course.getName());
                    c.put("courseid", course.getCourseid());
                    c.put("students", students.size());
                    i.put("name", institution.getName());
                    i.put("institutionid", course.getInstitution().getInstitutionid());
                    c.put("institution", i);
                    list.add(c);
                }
                hm.put("courses", list);
            }
            jr.setHm(hm);
            jr.setResponse_code(200);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return jr;
        }
    }

    //get a course by id
    public JsonResponse getCourse(Integer courseid) {
        JsonResponse jr = new JsonResponse(500, "Server error", null);
        HashMap hm = new HashMap();
        try {
            EntityManager em = provider.getEM();

            Query q = em.createQuery("Select c from Course c where c.courseid = :courseid ");
            q.setParameter("courseid", courseid);
            List<Course> courses = provider.getManyFromQuery(q);
            List list = new ArrayList();

            if (courses.isEmpty()) {
                hm.put("courses", list);
                hm.put("institution", new HashMap());
            } else {

                //get Students taking course
                Query q_student = em.createQuery("Select s from Student s where s.course.courseid = :courseid ");
                q_student.setParameter("courseid", courseid);
                List<Student> students = provider.getManyFromQuery(q_student);

                for (Course course : courses) {
                    HashMap c = new HashMap();
                    HashMap i = new HashMap();
                    c.put("name", course.getName());
                    c.put("courseid", course.getCourseid());
                    c.put("students", students.size());
                    i.put("name", course.getInstitution().getName());
                    i.put("institutionid", course.getInstitution().getInstitutionid());
                    c.put("institution", i);
                    list.add(c);
                }
                hm.put("course", list);

            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        jr.setResponse_code(200);
        jr.setHm(hm);
        return jr;
    }

    //add a course
    public JsonResponse addCourse(Course course) {
        JsonResponse jr = new JsonResponse(500, "Server error", null);
        HashMap hm = new HashMap();
        HashMap rs = new HashMap();

        try {
            EntityManager em = provider.getEM();
            //validation            
            if (course != null) {
                //course object not null
                String course_name = course.getName();
                int institutionid = course.getInstitution().getInstitutionid();
                Institution institute = em.find(Institution.class, institutionid);
                String[] course_name_valid = Validator.validateName(course_name, "Course");

                if (course_name_valid[0].equals("true")) {
                    //successful validation
                    Query q = em.createQuery("Select c from Course c where c.name = :name AND c.institution.institutionid =:institutionid");
                    q.setParameter("name", course_name);
                    q.setParameter("institutionid", institutionid);
                    List<Course> c = provider.getManyFromQuery(q);

                    if (c.isEmpty()) {
                        //no duplate course name found
                        if (provider.createEntity(course)) {
                            //save to db successful
                            hm.put("status", "success");
                            rs.put("message", "Course " + course_name + " has been successfuly added!");
                            hm.put("response", rs);
                        } else {
                            //save to db unsuccessful
                            hm.put("status", "error");
                            rs.put("message", "Course " + course_name + " could not be added!");
                            hm.put("response", rs);
                        }
                    } else {
                        //duplicate found
                        hm.put("status", "error");
                        rs.put("message", "Course " + course_name + " is already Registered!");
                        hm.put("response", rs);
                    }

                } else {
                    //unsuccessful validation
                    hm.put("status", "error");
                    rs.put("message", course_name_valid[1]);
                    hm.put("response", rs);
                }

            } else {
                //course object is null
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

    //update a course
    public JsonResponse updateCourse(Course course) {
        JsonResponse jr = new JsonResponse(500, "Server error", null);
        HashMap hm = new HashMap();
        HashMap rs = new HashMap();

        try {
            EntityManager em = provider.getEM();
            //validation            
            if (course != null) {
                //course object not null
                String course_name = course.getName();
                int institutionid = course.getInstitution().getInstitutionid();
                Institution institute = em.find(Institution.class, institutionid);
                String[] course_name_valid = Validator.validateName(course_name, "Course");

                if (course_name_valid[0].equals("true")) {
                    //successful validation
                    Query q = em.createQuery("Select c from Course c where c.name = :name AND c.institution.institutionid =:institutionid AND c.courseid !=:courseid");
                    q.setParameter("name", course_name);
                    q.setParameter("institutionid", institutionid);
                    q.setParameter("courseid", course.getCourseid());
                    List<Course> c = provider.getManyFromQuery(q);

                    if (c.isEmpty()) {
                        //no duplate course name found
                        if (provider.updateEntity(course)) {
                            //save to db successful
                            hm.put("status", "success");
                            rs.put("message", "Course " + course_name + " has been successfuly Updated!");
                            hm.put("response", rs);
                        } else {
                            //save to db unsuccessful
                            hm.put("status", "error");
                            rs.put("message", "Course " + course_name + " could not be Updated!");
                            hm.put("response", rs);
                        }
                    } else {
                        //duplicate found
                        hm.put("status", "error");
                        rs.put("message", "Course " + course_name + " is Already Regustered!");
                        hm.put("response", rs);
                    }

                } else {
                    //unsuccessful validation
                    hm.put("status", "error");
                    rs.put("message", course_name_valid[1]);
                    hm.put("response", rs);
                }

            } else {
                //course object is null
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

    //delete a course
    public JsonResponse deleteCourse(Integer courseid) {
        JsonResponse jr = new JsonResponse(500, "Server error", null);
        HashMap hm = new HashMap();
        HashMap rs = new HashMap();
        try {
            EntityManager em = provider.getEM();
            Course course = em.find(Course.class, courseid);
            if (course == null) {
                //no course exists!
                hm.put("course", new HashMap());
                hm.put("response", "no results found");
            } else {
                //delete the course
                String name = course.getName();
                //check if course has students
                Query q_course_student = em.createQuery("SELECT s from Student s where s.course.courseid = :courseid");
                q_course_student.setParameter("courseid", courseid);
                List<Student> course_Student = provider.getManyFromQuery(q_course_student);

                if (course_Student.isEmpty()) {
                    //delete no students registered
                    if (provider.deleteEntity(course)) {
                        //delete success
                        hm.put("status", "success");
                        rs.put("message", "Course " + name + " was successfully deleted!");
                        hm.put("response", rs);
                    } else {
                        //delete error
                        hm.put("status", "error");
                        rs.put("message", "Course " + name + " could not be deleted");
                        hm.put("response", rs);
                    }
                } else {
                    //student registered under this course
                    hm.put("status", "error");
                    rs.put("message", "Course " + name + " has students registered!");
                    hm.put("response", rs);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        jr.setHm(hm);
        jr.setResponse_code(200);
        return jr;
    }
    
    //delete a course
    public JsonResponse deleteInstitutionCourse(Integer institutionid, Integer courseid) {
        JsonResponse jr = new JsonResponse(500, "Server error", null);
        HashMap hm = new HashMap();
        HashMap rs = new HashMap();

        try {
            EntityManager em = provider.getEM();
            Query q = em.createQuery("select c from Course c WHERE c.institution.institutionid = :institutionid AND c.courseid=:courseid");
            q.setParameter("institutionid", institutionid);
            q.setParameter("courseid", courseid);
            Course course = provider.getSingleResult(q);
            if (course == null) {
                //no course exists!
                hm.put("course", new HashMap());
                hm.put("response", "no results found");
            } else {
                //delete the course
                String name = course.getName();
                //check if course has students
                Query q_course_student = em.createQuery("SELECT s from Student s where s.course.courseid = :courseid");
                q_course_student.setParameter("courseid", courseid);
                List<Student> course_Student = provider.getManyFromQuery(q_course_student);

                if (course_Student.isEmpty()) {
                    //delete no students registered
                    if (provider.deleteEntity(course)) {
                        //delete success
                        hm.put("status", "success");
                        rs.put("message", "Course " + name + " was successfully deleted!");
                        hm.put("response", rs);
                    } else {
                        //delete error
                        hm.put("status", "error");
                        rs.put("message", "Course " + name + " could not be deleted");
                        hm.put("response", rs);
                    }
                } else {
                    //student registered under this course
                    hm.put("status", "error");
                    rs.put("message", "Course " + name + " has students registered!");
                    hm.put("response", rs);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        jr.setHm(hm);
        jr.setResponse_code(200);
        return jr;
    }

}
