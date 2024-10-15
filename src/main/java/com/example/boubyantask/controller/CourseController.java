package com.example.boubyantask.controller;

import com.example.boubyantask.dto.CoursesDto;
import com.example.boubyantask.services.CourseService;
import lombok.AllArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class CourseController {

    private final CourseService courseService;

    @GetMapping("/courses")
    public List<CoursesDto> findAll() {
        return courseService.findAll();
    }

    @PostMapping("/courseRegistration/{loginName}/{CourseID}")
    public void registerCourse(@PathVariable("loginName") String loginName, @PathVariable("CourseID") int courseID) {
        courseService.registerCourse(loginName, courseID);
    }

    @PostMapping("/CancelCourseRegistration/{loginName}/{CourseID}")
    public void cancelRegisterCourse(@PathVariable("loginName") String loginName, @PathVariable("CourseID") int courseID) {
        courseService.unRegisterCourse(loginName, courseID);
    }

    @GetMapping("/courseSchedule/{CourseID}")
    public ResponseEntity<ByteArrayResource> Download(@PathVariable("CourseID") int courseID)  {
        String fileName = courseService.createPDF(courseID);

        File file = new File(fileName);

        if (!file.exists() || !file.isFile()) {
            System.err.println("File not found: " + fileName);
            return ResponseEntity.notFound().build(); // Return 404 if the file is not found
        }

        try (FileInputStream fis = new FileInputStream(file);
             ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {

            byte[] buffer = new byte[1024];
            int len;
            while ((len = fis.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, len);
            }

            // Convert the ByteArrayOutputStream to an array of bytes
            byte[] bytes = byteArrayOutputStream.toByteArray();

            // Set headers for file download
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", file.getName());
            headers.setContentLength(file.length());

            // Return the file content along with the headers
            return ResponseEntity.ok().headers(headers).body(new ByteArrayResource(bytes));

        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build(); // Return 500 in case of an internal server error
        }
    }

}
