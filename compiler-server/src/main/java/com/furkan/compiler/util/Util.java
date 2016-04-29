package com.furkan.compiler.util;

import com.furkan.compiler.endpoint.LogClient;
import org.apache.ant.compress.taskdefs.Unzip;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileFilter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Util functions
 */
public class Util {

    @Autowired
    @Qualifier("logClient")
    private LogClient logClient;

    public static List<File> findFilesInDirectory(String directory, String extension)
    {
        // Top directory
        File topDirectory = new File(directory);

        // All directories
        List<File> directories = new ArrayList<>();
        directories.add(topDirectory);

        // Files
        List<File> files = new ArrayList<>();
        List<String> filterWildcards = new ArrayList<>();
        filterWildcards.add("*." + extension);

        FileFilter typeFilter = new WildcardFileFilter(filterWildcards);

        // Search files in directories
        while (!directories.isEmpty())
        {
            List<File> subDirectories = new ArrayList<>();
            for(File f : directories) {
                subDirectories.addAll(Arrays.asList(f.listFiles((FileFilter) DirectoryFileFilter.INSTANCE)));
                files.addAll(Arrays.asList(f.listFiles(typeFilter)));
            }
            directories.clear();
            directories.addAll(subDirectories);
        }
        return files;
    }

    public static void unzip(MultipartFile file, String projectName) throws IOException {
        File outputFile;

        final String fileName = file.getOriginalFilename();

        // Convert MultipartFile to java file
        outputFile = new File(fileName);
        boolean isFileCreated = outputFile.createNewFile();
        FileOutputStream fos = new FileOutputStream(outputFile);
        fos.write(file.getBytes());
        fos.close();

        // Unzip
        Unzip unzipper = new Unzip();
        unzipper.setSrc(outputFile);

        File folder = new File(projectName);
        if (!folder.exists()) {
            boolean isDirectoryCreated = folder.mkdir();
        }
        unzipper.setDest(folder);
        unzipper.execute();
    }
}
