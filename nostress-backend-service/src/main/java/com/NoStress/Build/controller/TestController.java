//package com.NoStress.Build.controller;
//
//import com.NoStress.Build.service.TestCaseService;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.web.bind.annotation.*;
//
//import java.io.IOException;
//
//@RestController
//@RequestMapping("/api")
//public class TestController {
//
//    private static final Logger logger = LoggerFactory.getLogger(TestController.class);
//    private final TestCaseService testCaseService;
//
//    public TestController(TestCaseService testCaseService) {
//        this.testCaseService = testCaseService;
//    }
//
//    @PostMapping("/test")
//    public String createTest(@RequestParam String apiUrl, @RequestParam String prompt) {
//        logger.info("Received request to create test with API URL: {} and prompt: {}", apiUrl, prompt);
//
//        try {
//            return testCaseService.generateAndExecuteTest(apiUrl, prompt);
//        } catch (IOException e) {
//            logger.error("Error while generating or executing the test case", e);
//            return "Error: " + e.getMessage();
//        }
//    }
//}




//ABOVE IS THE PREV STABLE VERSION



























// STABLE VERSION 2
//
//
package com.NoStress.Build.controller;

import com.NoStress.Build.service.TestCaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import org.springframework.web.bind.annotation.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.stream.Collectors;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
//
//@RestController
//@RequestMapping("/api")
//public class TestController {
//
//    @PostMapping("/test")
//    public String executeTest(@RequestParam String apiUrl, @RequestParam String prompt) {
//        try {
//             Logger log = LoggerFactory.getLogger(TestCaseService.class);
//             log.info("######################################################");
//             log.info("######################################################");
//             log.info("######################################################");
//             log.info("######################################################");
//            log.info("I am in hereeeeeeeeeeeee!!!!!");
//            // Path to JMeter and JMX file
//            String jmeterPath = "C:/Users/stlp/Downloads/apache-jmeter-5.6.3/apache-jmeter-5.6.3/bin/jmeter.bat";
//            String jmxFilePath = "test.jmx"; // Update with the actual path
//
//            // Command to execute JMeter
//            ProcessBuilder processBuilder = new ProcessBuilder(
//                    jmeterPath,
//                    "-n", // Non-GUI mode
//                    "-t", jmxFilePath, // Test plan file
//                    "-l", "results.jtl" // Log file
//            );
//
//            // Redirect error and output streams
//            processBuilder.redirectErrorStream(true);
//            Process process = processBuilder.start();
//
//            // Capture output
//            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
//                String output = reader.lines().collect(Collectors.joining("\n"));
//                process.waitFor();
//                log.info("######################################################");
//                log.info("######################################################");
//                log.info("######################################################");
//                log.info("######################################################");
//                log.info("value of the output = {}",output);
//                return output;
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            return "Error occurred while executing JMeter test.";
//        }
//    }
//}



@RestController
@RequestMapping("/api")
public class TestController {

    @PostMapping("/test")
    public ResponseEntity<String> executeTest(@RequestParam String apiUrl, @RequestParam String prompt) {
        try {
            Logger log = LoggerFactory.getLogger(TestCaseService.class);
            log.info("######################################################");
                log.info("######################################################");
                log.info("######################################################");
                log.info("######################################################");
                log.info("I am in hereeeee -1");
            String jmeterPath = "C:/Users/stlp/Downloads/apache-jmeter-5.6.3/apache-jmeter-5.6.3/bin/jmeter.bat";
            String jmxFilePath = "test.jmx"; // Update with the actual path
            String resultsFilePath = "results.jtl"; // Update with the results path
            String reportPath = "./html-report"; // Update with the actual path

            // Command to generate JMeter HTML report
            ProcessBuilder processBuilder = new ProcessBuilder(
                    jmeterPath,
                    "-n",
                    "-t", jmxFilePath,
                    "-l", resultsFilePath,
                    "-e",
                    "-o", reportPath
            );

            processBuilder.redirectErrorStream(true);
            log.info("######################################################");
            log.info("######################################################");
            log.info("######################################################");
            log.info("######################################################");
            log.info("Value of output = {}",processBuilder.command());
            Process process = processBuilder.start();
            log.info("Now here");
            log.info("Value of process.getInputStream()= {}",process.getInputStream());
            log.info("Value of output = {}",process.getInputStream());
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            log.info("Value of output = {}",br.lines().collect(Collectors.joining("\n")));


            // Capture output logs
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                String output = reader.lines().collect(Collectors.joining("\n"));
                process.waitFor();
                System.out.println(output);
                log.info("######################################################");
                log.info("######################################################");
                log.info("######################################################");
                log.info("######################################################");
                log.info("Value of output = {}",output);
            }



            // Read HTML report as a response
            File htmlFile = new File(reportPath + "/index.html");
            if (htmlFile.exists()) {
                log.info("######################################################");
                log.info("######################################################");
                log.info("######################################################");
                log.info("######################################################");
                log.info("Caught");
                log.info("HTML file path: {}", htmlFile.getAbsolutePath());
                String htmlContent = new String(Files.readAllBytes(htmlFile.toPath()), StandardCharsets.UTF_8);
                return ResponseEntity.ok(htmlContent);
            } else {
                log.info("Caught-2");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("HTML report generation failed.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred while executing JMeter test.");
        }
    }
}
