package pku.edu.ai.styleswap.python;

import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class PyConnector {
    private final static String COMMAND_FILE_NAME = "test_all_layer.py";


    public void generateOutput(String content, String style, String output) throws IOException, InterruptedException {
        String command = String.format("python %s --content_path %s --style_path %s --output_path %s"
                , COMMAND_FILE_NAME, content, style, output);
        Process process = Runtime.getRuntime().exec(command);
        process.waitFor();
    }
}
