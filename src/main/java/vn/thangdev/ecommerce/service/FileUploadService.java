package vn.thangdev.ecommerce.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileUploadService {
	public String upload(MultipartFile file, String uploadDirAbsolute, String uploadDirRelative, String modelType) {

		if (file == null || file.isEmpty()) {
			return null;
		}

		try {

			Path uploadPath = Paths.get(uploadDirAbsolute);

			if (!Files.exists(uploadPath)) {
				Files.createDirectories(uploadPath);
			}

			String originalFilename = file.getOriginalFilename();

			String extension = "";

			if (originalFilename != null && originalFilename.contains(".")) {
				extension = originalFilename.substring(originalFilename.lastIndexOf("."));
			}

			String fileName = UUID.randomUUID() + extension;

			Path filePath = uploadPath.resolve(fileName);

			Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

			return uploadDirRelative + fileName;

		} catch (IOException e) {
			throw new RuntimeException("Có lỗi khi lưu file media", e);
		}
	}
}
