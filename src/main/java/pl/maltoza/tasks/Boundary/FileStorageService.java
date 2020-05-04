package pl.maltoza.tasks.Boundary;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class FileStorageService implements StorageService {
    private final Path path;

    public FileStorageService(Path path) {
        this.path = path;
    }

    @Override
    public String saveFile(Long taskId, MultipartFile file) throws IOException {
        String filename = file.getOriginalFilename();
        Path targetPath = path.resolve(filename);
        Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);
        return filename;
    }

    @Override
    public Resource loadFile(String filename) throws MalformedURLException {
        return new UrlResource(path.resolve(filename).toUri());
    }
}
