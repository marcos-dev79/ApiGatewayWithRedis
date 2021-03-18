package org.example;

import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class TestUtils {
        public static String readResponseFromFile(String orderFileName) {
            try {
                File resource = new ClassPathResource(orderFileName).getFile();
                return new String(Files.readAllBytes(resource.toPath()));

            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }
}
