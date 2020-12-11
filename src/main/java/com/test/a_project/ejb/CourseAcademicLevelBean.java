package com.test.a_project.ejb;

import com.test.a_project.entities.Academiclevel;
import com.test.a_project.entities.Course;
import com.test.a_project.entities.Institution;
import com.test.a_project.jpa.TransactionProvider;
import com.test.a_project.utils.JsonResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.Query;

@Stateless
public class CourseAcademicLevelBean {

    @EJB
    private TransactionProvider provider;
    //get all academic levels for a course
    public JsonResponse getAcademicLevels(Integer institutionid, Integer courseid) {
        JsonResponse jr = new JsonResponse(500, "Server error", null);
        HashMap hm = new HashMap();
        try {
            EntityManager em = provider.getEM();
            Query q = em.createQuery("Select al from Academiclevel al where al.course.institution.institutionid =:institutionid AND al.course.courseid=:courseid");
            q.setParameter("institutionid", institutionid);
            q.setParameter("courseid", courseid);
            List<Academiclevel> academic_levels = provider.getManyFromQuery(q);
            List list = new ArrayList();

            Institution institution = em.find(Institution.class, institutionid);
            Course course = em.find(Course.class, courseid);

            if (academic_levels.isEmpty()) {
                hm.put("academiclevel", list);

            } else {
                for (Academiclevel a : academic_levels) {
                    HashMap ah = new HashMap();
                    ah.put("id", a.getAcademiclevelid());
                    ah.put("level", a.getLevel());
                    list.add(ah);
                }
            }

            HashMap i = new HashMap();
            i.put("name", institution.getName());
            i.put("institutionnid", institution.getInstitutionid());

            HashMap c = new HashMap();
            c.put("name", course.getName());
            c.put("courseid", course.getCourseid());

            hm.put("course", c);
            hm.put("institution", i);
            
            jr.setHm(hm);
            jr.setResponse_code(200);
            jr.setMessage("Query Success");

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return jr;
    }
    
    //add an academic level
    public JsonResponse addAcademicLevel(Integer institutionid,Integer courseid, Academiclevel academiclevel){
        JsonResponse jr = new JsonResponse(500,"Server Error",null);
        try{
            EntityManager em = provider.getEM();
            Institution i = em.find(Institution.class, institutionid);
            //validation
            if(academiclevel == null){
                
            }
        } catch(Exception ex){
            
        }
        return jr;
    }

}
