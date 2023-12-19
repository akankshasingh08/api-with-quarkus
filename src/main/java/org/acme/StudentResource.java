package org.acme;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import service.StudentService;

import java.util.List;

import dto.AverageMarksDTO;
import dto.StudentDTO;

@Path("/students")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class StudentResource {
	@Inject
	StudentService studentService;

	@POST
	public Response createStudent(StudentDTO student) {
		StudentDTO createdStudent = studentService.createStudentWithSubjects(student);
		if (createdStudent != null) {
			return Response.status(Response.Status.CREATED).entity(createdStudent).build();
		} else {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
	}

	@PUT
	@Path("/{studentId}")
	public Response updateStudent(@PathParam("studentId") Long studentId, StudentDTO updatedStudentData) {
		StudentDTO updatedStudent = studentService.updateStudent(studentId, updatedStudentData);
		if (updatedStudent != null) {
			return Response.ok(updatedStudent).build();
		} else {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
	}

	@DELETE
	@Path("/{studentId}")
	public Response deleteStudent(@PathParam("studentId") Long studentId) {
		studentService.deleteStudent(studentId);
		return Response.noContent().build();
	}

	@GET
	@Path("/{studentId}")
	public Response getStudentById(@PathParam("studentId") Long studentId) {
		StudentDTO student = studentService.getStudentById(studentId);
		if (student != null) {
			return Response.ok(student).build();
		} else {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
	}

	@GET
	@Path("/average-marks/{studentId}")
	public Response getAverageMarks(@PathParam("studentId") Long studentId) {
		AverageMarksDTO averageMarks = studentService.getAverageMarks(studentId);
		return Response.ok(averageMarks).build();
	}

}
