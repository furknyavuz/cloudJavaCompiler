package com.furkan.compiler.controller;


import com.furkan.compiler.endpoint.LogClient;
import com.furkan.compiler.util.Util;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;

import java.io.*;
import java.util.List;

import static java.lang.Thread.sleep;

/**
 * Controller for compile source code
 * Capailities: Maven, Ant, Plain java files
 * and returns the filename of console output saved
 */
@Controller("compileController")
public class CompileController {

    @Autowired
    @Qualifier("logClient")
    private LogClient logClient;

    public String compile(String projectName, String fileName) {
        File outputFile;

        try {
            String nameWithoutExt = FilenameUtils.removeExtension(fileName);

            // Search for pom.xml and build.xml files
            File pomFile = new File(projectName + "/" + nameWithoutExt + "/pom.xml");
            File buildFile = new File(projectName + "/" + nameWithoutExt + "/build.xml");

            Runtime rt = Runtime.getRuntime();
            String project;
            String runCommand;

            // Define project and run commands
            if(pomFile.exists()) {
                // Maven project
                project = "Maven project: " + fileName;
                runCommand = "mvn install -f " + pomFile.getAbsolutePath();
            } else if(buildFile.exists()) {
                // Ant project
                project = "Ant project: " + fileName;
                runCommand = "ant -f " + buildFile.getAbsolutePath();
            } else {
                // Plain java files
                project = "Plain java files: " + fileName;
                runCommand = "javac ";
                List<File> files = Util.findFilesInDirectory(projectName + "/" + nameWithoutExt, "java");
                if (files == null || files.size() <= 0) {
                    return null;
                }
                for (File javaFile : files) {
                    runCommand += javaFile.getAbsolutePath() + " ";
                }
            }

            // Log project details
            logClient.log(project);
            logClient.log(runCommand);

            // Run command
            Process p = null;
            p = rt.exec(runCommand);
            Process finalP = p;
            new Thread(new Runnable() {
                public void run() {

                    // Open reader for capturing console outputs
                    BufferedReader input = new BufferedReader(new InputStreamReader(finalP.getInputStream()));
                    String line;

                    // Save console outputs to file
                    File outputFile = new File(nameWithoutExt + ".out");

                    try {
                        outputFile.createNewFile();
                        FileOutputStream fos = new FileOutputStream(outputFile);

                        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));

                        while ((line = input.readLine()) != null) {
                            logClient.log(line);
                            bw.write(line);
                            bw.newLine();
                        }

                        bw.close();
                    } catch (IOException e) {
                        logClient.log(e.getMessage());
                    }
                }
            }).start();

            // Wait for compiling process
            p.waitFor();
            sleep(1000);

            return nameWithoutExt + ".out";
        } catch (Exception ex) {
            logClient.log("Exception occured during reading the file: " + ex.getMessage());
        }
        return null;
    }
}
