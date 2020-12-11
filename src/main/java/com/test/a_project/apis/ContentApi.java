package com.test.a_project.apis;

import com.test.a_project.ejb.ContentBean;
import com.test.a_project.entities.Institution;
import com.test.a_project.entities.Student;
import com.test.a_project.utils.JsonResponse;
import java.util.HashMap;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Stateless
@Path("content")
@Produces(MediaType.APPLICATION_JSON)
public class ContentApi {

    @EJB
    private ContentBean contentBean;

    @GET
    @Path("courses/{universityid}")
    public Response getCourseByInsitutionId(@PathParam("universityid") Integer instituion_id) {
        HashMap h = contentBean.getCoursesByInstitutionId(instituion_id);

        return Response.status(200).entity(h).build();
    }

    @POST
    @Path("institution")
    public Response addInstitution(Institution institution_from_fronend) {
        JsonResponse jr = contentBean.addInstitution(institution_from_fronend);

        return Response.status(jr.getResponse_code()).entity(jr).build();
    }

    @GET
    @Path("institution")
    public Response getAllInstitutions() {
        HashMap res = contentBean.getAllInstitutions();

        return Response.status(200).entity(res).build();
    }

    @DELETE
    @Path("institution/{institutionid}")
    public Response deleteInstitution(@PathParam("institutionid") Integer institutionid) {
        JsonResponse jr = contentBean.deleteInstitution(institutionid);

        return Response.status(jr.getResponse_code()).entity(jr).build();
    }

    //....../api/content/courses/10?randomparam=bryanmichael
    @POST
    @Path("student")
    public Response addStudent(Student student) {
        JsonResponse jr = contentBean.addStudent(student);

        return Response.status(jr.getResponse_code()).entity(jr).build();
    }
}
