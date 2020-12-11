package com.test.a_project.apis;

import com.test.a_project.ejb.CourseBean;
import com.test.a_project.ejb.InstitutionBean;
import com.test.a_project.ejb.StudentBean;
import com.test.a_project.entities.Course;
import com.test.a_project.entities.Institution;
import com.test.a_project.entities.Student;
import com.test.a_project.utils.JsonResponse;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Stateless
@Path("institution")
@Produces(MediaType.APPLICATION_JSON)
public class InstitutionApi {

    @EJB
    private InstitutionBean ibean;

    @EJB
    private CourseBean cbean;
    
    @EJB
    private StudentBean sbean;

    @GET
    @Path("")
    //get all institutions
    public Response getAllInstitutions() {
        JsonResponse jr = ibean.getAllInstitutions();
        return Response.status(jr.getResponse_code()).entity(jr.getHm()).build();
    }

    @GET
    @Path("{institutionid}")
    //get single institution
    public Response getInstitution(@PathParam("institutionid") Integer institutionId) {
        JsonResponse jr = ibean.getInstitution(institutionId);
        return Response.status(jr.getResponse_code()).entity(jr.getHm()).build();
    }

    @POST
    @Path("")
    //add institution
    public Response addInstitution(Institution institution) {
        JsonResponse jr = ibean.addInstitution(institution);
        return Response.status(jr.getResponse_code()).entity(jr.getHm()).build();
    }

    @POST
    @Path("update")
    //update institution
    public Response updateInstitution(Institution institution) {
        JsonResponse jr = ibean.updateInstitution(institution);
        return Response.status(jr.getResponse_code()).entity(jr.getHm()).build();
    }

    @DELETE
    @Path("{institutionid}")
    //delete institution
    public Response deleteInstitution(@PathParam("institutionid") Integer institutionid) {
        JsonResponse jr = ibean.deleteInstitution(institutionid);
        return Response.status(jr.getResponse_code()).entity(jr.getHm()).build();
    }
    //Student apis
//    ----------------------------------------------------------------------------------
//    ----------------------------------------------------------------------------------
    @GET
    @Path("{institutionid}/students")
    //get all student
    public Response institutionStudents(@PathParam("institutionid") Integer institutionid) {
        JsonResponse jr = sbean.getAllStudents(institutionid);
        return Response.status(jr.getResponse_code()).entity(jr.getHm()).build();
    }

    @GET
    @Path("{institutionid}/students/{studentid}")
    public Response institutionStudent(@PathParam("institutionid") Integer institutionid, @PathParam("studentid") Integer studentid) {
        JsonResponse jr = sbean.getStudent(institutionid, studentid);
        return Response.status(jr.getResponse_code()).entity(jr.getHm()).build();
    }

    @POST
    @Path("{institutionid}/students")
    //Add a course
    public Response addStudent(Student student, @PathParam("institutionid")Integer institutionid) {
        JsonResponse jr = sbean.addStudent(student, institutionid);
        return Response.status(jr.getResponse_code()).entity(jr.getHm()).build();
    }

    @POST
    @Path("{institutionid}/students/update")
    //Update a course
    public Response updateStudent(Student student, @PathParam("institutionid")Integer institutionid) {
        JsonResponse jr = sbean.updateStudent(student,institutionid);
        return Response.status(jr.getResponse_code()).entity(jr.getHm()).build();
    }
    
     @POST
    @Path("{institutionid}/students/update-institution")
    //Update a course
    public Response updateStudentInstitution(Student student, @PathParam("institutionid")Integer institutionid) {
        JsonResponse jr = sbean.udpateStudentInstitution(student);
        return Response.status(jr.getResponse_code()).entity(jr.getHm()).build();
    }

    @DELETE
    @Path("{institutionid}/students/{studentid}")
    //delete institution Course
    public Response deleteStudent(@PathParam("institutionid") Integer institutionid, @PathParam("studentid") Integer studentid) {
        JsonResponse jr = sbean.deleteStudent(institutionid, studentid);
        return Response.status(jr.getResponse_code()).entity(jr.getHm()).build();
    }
    
       //institution course url
//    ----------------------------------------------------------------------------------
//    ----------------------------------------------------------------------------------
    @GET
    @Path("{institutionid}/courses")
    //get all courses
    public Response institutionCourses(@PathParam("institutionid") Integer institutionid) {
        JsonResponse jr = cbean.getCoursesByInstitutionId(institutionid);
        return Response.status(jr.getResponse_code()).entity(jr.getHm()).build();
    }

    @GET
    @Path("{institutionid}/courses/{courseid}")
    public Response institutionCourse(@PathParam("institutionid") Integer institutionid, @PathParam("courseid") Integer courseid) {
        JsonResponse jr = cbean.getCourseByInstitutionId(institutionid, courseid);
        return Response.status(jr.getResponse_code()).entity(jr.getHm()).build();
    }

    @POST
    @Path("{institutionid}/courses")
    //Add a course
    public Response addInstitutionCourse(Course course) {
        JsonResponse jr = cbean.addCourse(course);
        return Response.status(jr.getResponse_code()).entity(jr.getHm()).build();
    }

    @POST
    @Path("{institutionid}/courses/update")
    //Update a course
    public Response updateInstitutionCourse(Course course) {
        JsonResponse jr = cbean.updateCourse(course);
        return Response.status(jr.getResponse_code()).entity(jr.getHm()).build();
    }

    @DELETE
    @Path("{institutionid}/courses/{courseid}")
    //delete institution Course
    public Response deleteInstitutionCourse(@PathParam("institutionid") Integer institutionid, @PathParam("courseid") Integer courseid) {
        JsonResponse jr = cbean.deleteInstitutionCourse(institutionid, courseid);
        return Response.status(jr.getResponse_code()).entity(jr.getHm()).build();
    }
    
//    //Course Academic Levels
//    @GET
//    @Path("")
//    public void getCourseAcademicLevels(){
//        
//    }

}
