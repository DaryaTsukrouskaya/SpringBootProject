package by.teachmeskills.soap.repository;


import by.teachmeskills.soap.model.Student;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class StudentRepository {

    private static final List<Student> students = new ArrayList<>();

    @PostConstruct
    public void initData() {
        Student student1 = new Student();
        Student student2 = new Student();
        Student student3 = new Student();
        student1.setName("Dasha");
        student2.setName("Misha");
        student3.setName("Maksim");
        student1.setAge(18);
        student2.setAge(19);
        student3.setAge(21);
        student1.setAddress("Minsk");
        student2.setAddress("Minsk");
        student3.setAddress("Saint-Petersburg");
        student1.setCourse(1);
        student2.setCourse(2);
        student3.setCourse(3);
    }

    public Student findStudent(String name) {
        Student result = null;
        for (Student student : students) {
            if (student.getName().equals(name)) {
                result = student;
            }
        }
        return result;
    }
}
