package com.NoStress.Build.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
public class TestResultsController {

    @GetMapping("/api/results")
    public ResponseEntity<String> getResults() throws IOException {
        Path resultsFilePath = Paths.get("results.jtl");
        String results = Files.readString(resultsFilePath);
        return ResponseEntity.ok(results);
    }
}

