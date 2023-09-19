package by.teachmeskills.soap;


import by.teachmeskills.soap.repository.StudentRepository;
import by.teachmeskills.soap.model.GetStudentRequest;
import by.teachmeskills.soap.model.GetStudentResponse;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class StudentEndpoint {

    private static final String NAMESPACE_URI = "http://www.baeldung.com/springsoap/gen";

    private final StudentRepository studentRepository;

    public StudentEndpoint(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getStudentRequest")
    @ResponsePayload
    public GetStudentResponse getStudent(@RequestPayload GetStudentRequest request) {
        GetStudentResponse response = new GetStudentResponse();
        response.setStudent(studentRepository.findStudent(request.getName()));

        return response;
    }
}

