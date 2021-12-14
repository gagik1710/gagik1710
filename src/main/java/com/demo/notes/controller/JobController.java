package com.demo.notes.controller;

import com.demo.notes.service.JobService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/job")
@RequiredArgsConstructor
public class JobController {

    private final JobService jobService;

    @GetMapping("/json")
    public ResponseEntity<?> runJsonCreationJob() {
        return ResponseEntity.ok(jobService.writeJSON());
    }

    @GetMapping("/parquet")
    public ResponseEntity<?> runParquetCreationJob() {
        return ResponseEntity.ok(jobService.writeParquet());
    }
}
