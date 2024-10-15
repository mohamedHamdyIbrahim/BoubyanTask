package com.example.boubyantask.services;

import com.example.boubyantask.dto.CoursesDto;
import com.example.boubyantask.entities.Courses;
import com.example.boubyantask.entities.Courseschedule;
import com.example.boubyantask.entities.User;
import com.example.boubyantask.mapper.CoursesMapper;
import com.example.boubyantask.repo.CoursesRepo;
import com.example.boubyantask.repo.UserRepo;
import com.itextpdf.layout.element.Paragraph;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Cell;
import java.io.FileOutputStream;
import java.util.List;

@AllArgsConstructor
@Service
public class CourseServiceImpl implements CourseService {

    private final CoursesRepo coursesRepo;

    private final UserRepo userRepo;

    private final CoursesMapper coursesMapper;

    @Cacheable("courses")
    @Override
    public List<CoursesDto> findAll() {
        System.err.println("Get All Notification");
        List<Courses> allCourses = coursesRepo.findAll();
        return coursesMapper.toDto(allCourses);

    }

    @Override
    public void registerCourse(String loginName, int courseID) {
        User selectedUser = userRepo.findUserByLoginname(loginName);
        Courses selectedCourse = coursesRepo.findCoursesById(courseID);
        if (!selectedUser.getCourses().contains(selectedCourse)) {
            selectedUser.getCourses().add(selectedCourse);

        } else {
            System.err.println("User already Registered to this course");
        }
        userRepo.save(selectedUser);
    }
    @Override
    public void unRegisterCourse(String loginName, int courseID) {
        User selectedUser = userRepo.findUserByLoginname(loginName);
        Courses selectedCourse = coursesRepo.findCoursesById(courseID);
        if (selectedUser.getCourses().contains(selectedCourse)) {
            selectedUser.getCourses().remove(selectedCourse);
        } else {
            System.err.println("User already was not Registered to this course");
        }
        userRepo.save(selectedUser);

    }
    @Override
    public String createPDF(int courseID) {
        Courses selectedCourse = coursesRepo.findCoursesById(courseID);
        String filePath="F:\\"+selectedCourse.getName()+".pdf";
        try {
            // Initialize PDF writer and document
            PdfWriter writer = new PdfWriter(new FileOutputStream(filePath));
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            // Create a table with 3 columns for Day, Hour, and CourseID
            Table table = new Table(3);
            table.addCell(new Cell().add(new Paragraph("Day")));
            table.addCell(new Cell().add(new Paragraph("Hour")));
            table.addCell(new Cell().add(new Paragraph("Course ID")));

            // Add data to the table
            for (Courseschedule schedule : selectedCourse.getCourseschedules()) {
                table.addCell(new Cell().add(new Paragraph(schedule.getDay())));
                table.addCell(new Cell().add(new Paragraph(schedule.getHour())));
                table.addCell(new Cell().add(new Paragraph(String.valueOf(schedule.getCourseid().getName()))));
            }


            // Add the table to the document
            document.add(table);

            // Close the document
            document.close();
            System.out.println("PDF created successfully!");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return filePath;
    }


}
