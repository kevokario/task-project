package com.test.a_project.apis;

import com.test.a_project.ejb.CourseBean;
import com.test.a_project.entities.Course;
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
@Path("course")
@Produces(MediaType.APPLICATION_JSON)
public class CourseApi {
    @EJB 
    private CourseBean cbean;
    
    @GET
    @Path("")
    //get all courses
    public Response getAllCourses(){
        JsonResponse jr = cbean.getAllCourses();
        return Response.status(200).entity(jr.getHm()).build();
    }
    
    @GET
    @Path("{courseid}")
    //get all courses
    public Response getCourse(@PathParam("courseid") Integer courseid){
        JsonResponse jr = cbean.getCourse(courseid);
        return Response.status(200).entity(jr.getHm()).build();
    }
    
    @POST
    @Path("")
    //get all courses
    public Response addCourse(Course course){
        JsonResponse jr = cbean.addCourse(course);
        return Response.status(200).entity(jr.getHm()).build();
    }
    
    @POST
    @Path("/update")
    //get all courses
    public Response updateCourse(Course course){
        JsonResponse jr = cbean.updateCourse(course);
        return Response.status(200).entity(jr.getHm()).build();
    }
    
    @DELETE
    @Path("{courseid}")
    //delete a course
    public Response deleteCourse(@PathParam("courseid") Integer courseid){
        JsonResponse jr = cbean.deleteCourse(courseid);
        return Response.status(jr.getResponse_code()).entity(jr.getHm()).build();
    }
}
