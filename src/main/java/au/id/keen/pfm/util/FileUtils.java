package au.id.keen.pfm.util;

import lombok.experimental.UtilityClass;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@UtilityClass
public class FileUtils {

    public static File getTempFile(MultipartFile pMultipartFile) throws IOException {
        File tempFile = File.createTempFile("temp", null);
        tempFile.deleteOnExit();
        pMultipartFile.transferTo(tempFile);
        return tempFile;
    }

}
