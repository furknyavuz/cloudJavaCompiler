package com.furkan.client.controller;

import com.amazonaws.util.json.JSONArray;
import com.amazonaws.util.json.JSONException;
import com.amazonaws.util.json.JSONObject;
import com.furkan.client.endpoint.UploadClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

/**
 * Controller for upload source code to compiler server
 */
@RestController
public class UploadController {

    @Autowired
    @Qualifier("uploadClient")
    private UploadClient uploadClient;

    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
    public @ResponseBody String uploadFile(@RequestParam(value = "file", required = true) MultipartFile file) throws IOException, JSONException {

        // Upload codes to compiler server
        byte[] rawOutput = uploadClient.upload(file);

        // Format incomming message
        String formattedOutput = new String(rawOutput, StandardCharsets.UTF_8);
        String[] lines = formattedOutput.split(System.getProperty("line.separator"));
        List<String> linesString = Arrays.asList(lines);

        // Convert output to json
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("result", new JSONArray(linesString));

        return jsonObj.toString();
    }
}
