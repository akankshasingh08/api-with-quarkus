package service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import repository.StudentRepository;
import repository.SubjectRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import dto.AverageMarksDTO;
import dto.StudentDTO;
import dto.SubjectDTO;
import entity.Student;
import entity.Subject;

@ApplicationScoped
public class StudentService {
	@Inject
	StudentRepository studentRepository;

	@Inject
	SubjectRepository subjectRepository;

	@Transactional
	public void deleteStudent(Long studentId) {
		Student existingStudent = studentRepository.findById(studentId);
		if (existingStudent != null) {
			for (Subject subject : existingStudent.getSubject()) {

				subjectRepository.delete(subject);
			}
			studentRepository.delete(existingStudent);
		}

	}

	public StudentDTO getStudentById(Long studentId) {
		Student student = studentRepository.findById(studentId);
		return convertToDTO(student);
	}

	public AverageMarksDTO getAverageMarks(Long studentId) {
		double avgMarks = 0;
		Student student = studentRepository.findById(studentId);
		AverageMarksDTO averageMarksDTO = new AverageMarksDTO();
		if (student != null) {

			List<Subject> subjectList = student.getSubject();

			List<Integer> marks = subjectList.stream().map(x -> x.getMarks()).collect(Collectors.toList());
			if (marks != null && !marks.isEmpty()) {
				avgMarks = marks.stream().mapToInt(Integer::intValue).average().orElse(0.0);
			}

			averageMarksDTO.setName(student.getName());
			averageMarksDTO.setAverageMarks(avgMarks);
			if (avgMarks >= 65) {
				averageMarksDTO.setPassStatus("Passed");
			} else {
				averageMarksDTO.setPassStatus("Failed");
			}

		}
		return averageMarksDTO;
	}

	@Transactional
	public StudentDTO createStudentWithSubjects(StudentDTO studentDTO) {

		Student student = convertToEntity(studentDTO);

		if (student.getSubject() != null) {
			for (Subject subject : student.getSubject()) {
				subject.setStudent(student);
			}
		}

		studentRepository.persist(student);

		return convertToDTO(student);
	}

	private StudentDTO convertToDTO(Student student) {
		StudentDTO studentDTO = new StudentDTO();
		studentDTO.setId(student.getId());
		studentDTO.setName(student.getName());
		studentDTO.setAge(student.getAge());

		List<SubjectDTO> subjectDTOs = new ArrayList<>();
		if (student.getSubject() != null) {
			subjectDTOs = student.getSubject().stream().map(this::convertSubjectToDTO).collect(Collectors.toList());
		}
		studentDTO.setSubjects(subjectDTOs);

		return studentDTO;
	}

	private SubjectDTO convertSubjectToDTO(Subject subject) {
		SubjectDTO subjectDTO = new SubjectDTO();
		subjectDTO.setSubjectId(subject.getSubjectId());
		subjectDTO.setSubjectName(subject.getSubjectName());
		subjectDTO.setMarks(subject.getMarks());
		return subjectDTO;
	}

	private Student convertToEntity(StudentDTO studentDTO) {
		Student student = new Student();
		student.setId(studentDTO.getId());
		student.setName(studentDTO.getName());
		student.setAge(studentDTO.getAge());

		List<Subject> subjects = new ArrayList<>();
		if (studentDTO.getSubjects() != null) {
			for (SubjectDTO subjectDTO : studentDTO.getSubjects()) {
				Subject subject = new Subject();
				subject.setSubjectId(subjectDTO.getSubjectId());
				subject.setSubjectName(subjectDTO.getSubjectName());
				subject.setMarks(subjectDTO.getMarks());
				subject.setStudent(student); // Associate the subject with the student
				subjects.add(subject);
			}
		}
		student.setSubject(subjects);

		return student;
	}

	@Transactional
	public StudentDTO updateStudent(Long studentId, StudentDTO updatedStudentData) {
		Student existingStudent = studentRepository.findById(studentId);
		if (existingStudent != null) {
			existingStudent.setName(updatedStudentData.getName());
			existingStudent.setAge(updatedStudentData.getAge());
			existingStudent.setSubject(existingStudent.getSubject());
			studentRepository.persist(existingStudent);
			return convertToDTO(existingStudent);

		}
		return null;
	}
}
