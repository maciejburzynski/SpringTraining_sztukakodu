package pl.maltoza.tasks.Boundary;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class FileStorageService implements StorageService {
    private final Path path;

    public FileStorageService(Path path) {
        this.path = path;
    }

    @Override
    public void saveFile(Long taskId, MultipartFile file) throws IOException {

        String fileName = file.getName();
        Path targetPath = path.resolve(file.getName());
        Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);
    }
}
