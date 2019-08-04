package NotesApi;

import java.io.File;
import java.io.IOException;
import java.lang.ProcessBuilder.Redirect;

public class ProcessExecutor {
    public Process execute() throws IOException {
        Process p = null;
        ProcessBuilder pb = new ProcessBuilder("java", "-jar", "notes-api-0.1.0.jar");
        pb.directory(new File("build/libs"));
        File log = new File("log");
        pb.redirectErrorStream(true);
        pb.redirectOutput(Redirect.appendTo(log));
        p = pb.start();
        return p;
    }
}