package com.test.a_project.ejb;

import com.test.a_project.entities.Course;
import com.test.a_project.entities.Institution;
import com.test.a_project.entities.Student;
import com.test.a_project.jpa.TransactionProvider;
import com.test.a_project.utils.JsonResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class ContentBean {

    @EJB
    private TransactionProvider provider;

    public HashMap getCoursesByInstitutionId(Integer institution_id) {
        HashMap response_hashmap = new HashMap();

        try {
            EntityManager em = provider.getEM();

            Institution institution = provider.getEntity(Institution.class, institution_id);

            Query q = em.createQuery("SELECT m FROM Course m WHERE m.institution.institutionid = :institution_id");
            q.setParameter("institution_id", institution_id);
            List<Course> courses = provider.getManyFromQuery(q);

            if (!courses.isEmpty()) {
                List list = new ArrayList();

                for (Course c : courses) {
                    HashMap hm = new HashMap();
                    hm.put("courseid", c.getCourseid());
                    hm.put("name", c.getName());

                    list.add(hm);

                    if (institution == null) {
                        institution = c.getInstitution();
                    }
                }

                response_hashmap.put("courses", list);
            }

            if (institution != null) {
                response_hashmap.put("university_name", institution.getName());
                response_hashmap.put("university_id", institution.getInstitutionid());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return response_hashmap;
        }
    }

    public JsonResponse addInstitution(Institution i) {
        JsonResponse jr = new JsonResponse();
        jr.setResponse_code(500);
        jr.setMessage("Server Error");

        try {
            if (i == null) {
                jr.setMessage("Please specify valid data");
            } else {
                if (i.getName() == null) {
                    jr.setMessage("Please specify a valid name");
                } else {
                    if (provider.createEntity(i)) {
                        jr.setResponse_code(200);
                        jr.setMessage(i.getName() + " has been created successfully");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return jr;
        }
    }

    public HashMap getAllInstitutions() {
        HashMap res = new HashMap();

        try {
            EntityManager em = provider.getEM();

            Query q = em.createQuery("SELECT i FROM Institution i ORDER BY i.institutionid DESC");
            List<Institution> schools = provider.getManyFromQuery(q);

            if (!schools.isEmpty()) {
                List list = new ArrayList();

                for (Institution i : schools) {
                    HashMap hm = new HashMap();
                    hm.put("institutionid", i.getInstitutionid());
                    hm.put("name", i.getName());

                    list.add(hm);
                }

                res.put("institutions", list);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return res;
        }
    }

    public JsonResponse deleteInstitution(Integer institutionid) {
        JsonResponse jr = new JsonResponse();
        jr.setResponse_code(500);
        jr.setMessage("Server Error");

        try {
            EntityManager em = provider.getEM();

            if (institutionid == null) {
                jr.setMessage("Please specify a valid institution");
            } else {
                Query q = em.createQuery("SELECT i FROM Institution i WHERE i.institutionid = :institutionid");
                q.setParameter("institutionid", institutionid);
                Institution institution = provider.getSingleResult(q);

                if (provider.deleteEntity(institution)) {
                    jr.setResponse_code(200);
                    jr.setMessage("Institution deleted successfully");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return jr;
        }
    }

    public JsonResponse addStudent(Student student) {
        JsonResponse jr = new JsonResponse();
        jr.setResponse_code(500);
        jr.setMessage("Server Error");

        if (student == null) {
            jr.setMessage("Please specify valid data");
        } else {
            if (provider.createEntity(student)) {
                jr.setResponse_code(200);
                jr.setMessage(student.getName() + " has been added successfully");
            }
        }

        return jr;
    }
}
