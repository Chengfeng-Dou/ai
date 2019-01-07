package pku.edu.ai.styleswap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import pku.edu.ai.styleswap.python.PyConnector;
import pku.edu.ai.styleswap.utils.FileUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;

@Controller
@SpringBootApplication
public class StyleSwapApplication {
    @Value("${savepath}")
    private String savePath;

    private final PyConnector pyConnector;

    @Autowired
    public StyleSwapApplication(PyConnector pyConnector) {
        this.pyConnector = pyConnector;
    }


    @RequestMapping("/")
    public String chooseFile() {
        return "choose-pic";
    }

    @RequestMapping("/upload")
    @ResponseBody
    public String uploadFile(String service,
                             MultipartHttpServletRequest request) {
        HttpSession session = request.getSession();
        String sessionId = session.getId();
        MultipartFile file = request.getFile("file");
        FileUtil.uploadFile(file,
                String.format("%s%s%s%s", savePath, File.separator, sessionId, File.separator),
                String.format("%s.jpg", service));
        return "{}";
    }

    @RequestMapping(value = "/getResult", method = RequestMethod.POST)
    public String getResult(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        String sessionId = session.getId();

        String dirPath = String.format("%s%s%s%s", savePath, File.separator, sessionId, File.separator);
        String outputPath = dirPath + "output.jpg";
        String contentPath = dirPath + "content.jpg";
        String stylePath = dirPath + "style.jpg";

        try {
            pyConnector.generateOutput(contentPath, stylePath, outputPath);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        model.addAttribute("output", String.format("%s%soutput.jpg", sessionId, File.separator));

        return "result";
    }

    public static void main(String[] args) {
        SpringApplication.run(StyleSwapApplication.class, args);
    }


}

