//package com.NoStress.Build.service;
//
//import org.json.JSONException;
//import org.springframework.stereotype.Service;
//import org.springframework.web.client.RestTemplate;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.ResponseEntity;
//import org.json.JSONObject;
//
//import java.io.FileWriter;
//import java.io.IOException;
//import java.nio.file.Paths;
//
//@Service
//public class TestCaseService {
//
//    private static final String OPENAI_API_URL = "https://api.openai.com/v1/chat/completions";
//    private static final String OPENAI_API_KEY = "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";
//
//
//    public String generateAndExecuteTest(String apiUrl, String prompt) throws IOException, JSONException {
//        // Step 1: Send the prompt to ChatGPT
//        String jmeterScript = callChatGPT(apiUrl, prompt);
//
//        // Step 2: Save the generated JMeter script to a file
//        String scriptPath = saveJMeterScript(jmeterScript);
//
//        // Step 3: Execute the JMeter script
//        executeJMeterScript(scriptPath);
//
//        return scriptPath;
//    }
//
//    private String callChatGPT(String apiUrl, String prompt) throws JSONException {
//        RestTemplate restTemplate = new RestTemplate();
//
//        JSONObject requestBody = new JSONObject();
//        requestBody.put("model", "gpt-4");
//        requestBody.put("messages", new JSONObject[] {
//                new JSONObject().put("role", "user", "content",
//                        "Generate a valid JMeter XML script for the API: " + apiUrl +
//                                ". Scenario: " + prompt +
//                                ". Provide a complete, executable JMeter script.")
//        });
//        requestBody.put("max_tokens", 3000);
//        requestBody.put("temperature", 0.2);
//
//
//
//
//        // Construct the request payload for OpenAI
//        JSONObject requestBody = new JSONObject();
//        requestBody.put("model", "gpt-4o-mini");
//        requestBody.put("prompt", "Generate a valid JMeter XML script for the following API: " + apiUrl +
//                " based on this scenario: " + prompt +
//                ". Provide a complete, executable script.");
//        requestBody.put("max_tokens", 3000);
//        requestBody.put("temperature", 0.2); // Low randomness for predictable output
//
//        // Set headers
//        HttpHeaders headers = new HttpHeaders();
//        headers.set("Authorization", "Bearer " + OPENAI_API_KEY);
//        headers.set("Content-Type", "application/json");
//
//        HttpEntity<String> entity = new HttpEntity<>(requestBody.toString(), headers);
//
//        // Call OpenAI API
//        ResponseEntity<String> response = restTemplate.postForEntity(OPENAI_API_URL, entity, String.class);
//        System.out.println("Response from OpenAI: " + response.getBody());
//
//        // Parse and return the response text
//        JSONObject jsonResponse = new JSONObject(response.getBody());
//        return jsonResponse.getJSONArray("choices").getJSONObject(0).getString("text").trim();
//
//
//    }
//
//    private String saveJMeterScript(String jmeterScript) throws IOException {
//        String scriptPath = Paths.get(System.getProperty("user.dir"), "test.jmx").toString();
//        try (FileWriter writer = new FileWriter(scriptPath)) {
//            writer.write(jmeterScript);
//        }
//        return scriptPath;
//    }
//
//    private void executeJMeterScript(String scriptPath) throws IOException {
//        String command = "jmeter -n -t " + scriptPath + " -l result.jtl";
//        Runtime.getRuntime().exec(command);
//    }
//}


package com.NoStress.Build.service;

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

@Service
public class TestCaseService {

    private static final Logger logger = LoggerFactory.getLogger(TestCaseService.class);
    private static final String OPENAI_API_URL = "https://api.openai.com/v1/chat/completions";
    String apiKey = System.getenv("OPENAI_API_KEY");
    private static final String OPENAI_API_KEY = "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";

    public String generateAndExecuteTest(String apiUrl, String prompt) throws IOException {
        try {
            // Step 1: Call ChatGPT to generate JMeter script
            logger.info("Calling ChatGPT to generate JMeter script for API: {} with prompt: {}", apiUrl, prompt);
            String jmeterScript = callChatGPT(apiUrl, prompt);

            // Step 2: Save the script locally
            String scriptPath = saveJMeterScript(jmeterScript);

            // Step 3: Execute the script
            executeJMeterScript(scriptPath);

            logger.info("Test executed successfully. Script saved at: {}", scriptPath);
            return scriptPath;
        } catch (Exception e) {
            logger.error("Error during test case execution", e);
            return "Error during test case execution: " + e.getMessage();
        }
    }

    private String callChatGPT(String apiUrl, String prompt) throws JSONException {
        RestTemplate restTemplate = new RestTemplate();

        // Construct the payload for OpenAI
        JSONObject systemMessage = new JSONObject();
        systemMessage.put("role", "system");
        systemMessage.put("content", "You are a test automation assistant.");

        JSONObject userMessage = new JSONObject();
        userMessage.put("role", "user");
        userMessage.put("content",
                "Generate a valid JMeter XML script for the API: " + apiUrl +
                        ". Scenario: " + prompt +
                        ". Do not return anything apart from the executable JMeter script. Do not return a single other word or character beside the script as the content of your response will be directly pasted into the jmx file without any modification for further execution. Trim anything apart from the script. It should start wiht <xml");

        JSONObject requestBody = new JSONObject();
        requestBody.put("model", "gpt-3.5-turbo");
        requestBody.put("messages", new JSONArray().put(systemMessage).put(userMessage));
        requestBody.put("max_tokens", 3000);
        requestBody.put("temperature", 0.2);

        // Set headers
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + OPENAI_API_KEY);
        headers.set("Content-Type", "application/json");

        HttpEntity<String> entity = new HttpEntity<>(requestBody.toString(), headers);

        // Debugging: Log the payload
        logger.debug("##################################################################");
        logger.debug("##################################################################");
        logger.debug("##################################################################");
        logger.debug("##################################################################");
        logger.debug("##################################################################");
        logger.debug("##################################################################");
        logger.debug("Request Payload: {}", requestBody.toString(2));

        // Make the POST request to OpenAI API
        ResponseEntity<String> response = restTemplate.postForEntity(OPENAI_API_URL, entity, String.class);

        // Debugging: Log the response
        logger.debug("##################################################################");
        logger.debug("##################################################################");
        logger.debug("##################################################################");
        logger.debug("##################################################################");
        logger.debug("##################################################################");
        logger.debug("##################################################################");

        logger.debug("Response from OpenAI: {}", response.getBody());

        if (!response.getStatusCode().is2xxSuccessful()) {
            logger.error("Failed to call OpenAI API. Status code: {} Response: {}", response.getStatusCode(), response.getBody());
            throw new RuntimeException("Failed to call OpenAI API: " + response.getStatusCode() + " - " + response.getBody());
        }

        // Parse response
        JSONObject jsonResponse = new JSONObject(response.getBody());
        String generatedScript = jsonResponse.getJSONArray("choices").getJSONObject(0).getJSONObject("message").getString("content").trim();
        logger.debug("##################################################################");
        logger.debug("##################################################################");
        logger.debug("##################################################################");
        logger.debug("##################################################################");
        logger.debug("##################################################################");
        logger.debug("##################################################################");
        logger.info("Received JMeter script from OpenAI API");
        return generatedScript;
    }

    private String saveJMeterScript(String jmeterScript) throws IOException {
        String scriptPath = Paths.get(System.getProperty("user.dir"), "test.jmx").toString();
        try (FileWriter writer = new FileWriter(scriptPath)) {
            writer.write(jmeterScript);
            logger.debug("##################################################################");
            logger.debug("##################################################################");
            logger.debug("##################################################################");
            logger.debug("##################################################################");
            logger.debug("##################################################################");
            logger.debug("##################################################################");}
        logger.info("JMeter script saved at: {}", scriptPath);
        return scriptPath;
    }

    private void executeJMeterScript(String scriptPath) throws IOException {
        // Update the JMeter executable path to match your installation
        String jmeterPath = "C:/Users/stlp/Downloads/apache-jmeter-5.6.3/apache-jmeter-5.6.3/bin/jmeter.bat"; // Update this path

        // Log the execution details
        logger.debug("##################################################################");
        logger.debug("##################################################################");
        logger.debug("##################################################################");
        logger.debug("##################################################################");
        logger.debug("##################################################################");
        logger.debug("##################################################################");
        logger.info("JMeter script saved at: {}", scriptPath);

        System.out.println("Executing JMeter script at: " + scriptPath);


        logger.debug("##################################################################");
        logger.debug("##################################################################");
        logger.debug("##################################################################");
        logger.debug("##################################################################");
        logger.debug("##################################################################");
        logger.debug("##################################################################");
        logger.info("JJMeter executable path: {}", jmeterPath);

        // Build the process command
        ProcessBuilder processBuilder = new ProcessBuilder(jmeterPath, "-n", "-t", scriptPath,"-l","results.jtl");

        // Redirect output and errors for better visibility
        processBuilder.redirectOutput(ProcessBuilder.Redirect.INHERIT);
        processBuilder.redirectError(ProcessBuilder.Redirect.INHERIT);

        // Start the JMeter process
        processBuilder.start();
    }
}