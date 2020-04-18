package pl.maltoza.tasks.Boundary;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

public interface StorageService {

    void saveFile(Long taskId, MultipartFile file) throws IOException;
}
