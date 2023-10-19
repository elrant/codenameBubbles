package team.elrant.bubbles;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
@Component
public class NpmStartRunner implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        try {
            String electronDirectory = "./electron";

            ProcessBuilder processBuilder = new ProcessBuilder("npm", "start");
            processBuilder.directory(new File(electronDirectory));
            Process process = processBuilder.start();

            // Optionally, you can capture and log the output of the npm start command
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            // Wait for the npm start process to finish
            int exitCode = process.waitFor();
            if (exitCode != 0) {
                System.err.println("npm start failed with exit code: " + exitCode);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
