package com.ervinaldo.springboot.backend.apirest.models.service;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UploadFileServiceImp implements IUploadFileService {
	private final Logger log = LoggerFactory.getLogger(UploadFileServiceImp.class);
	private final static String DIRECTORY_UPLOAD = "uploads";

	@Override
	public Resource upload(String photoName) throws MalformedURLException {
		Path pathFile = this.getPath(photoName);
		log.info(pathFile.toString());
		Resource resource = null;
		resource = new UrlResource(pathFile.toUri());
		if (!resource.isReadable()) {
			pathFile = Paths.get("src/main/resources/static/images").resolve("default_user.png").toAbsolutePath();
			resource = new UrlResource(pathFile.toUri());
			log.error("Could not load image: " + photoName);
		}
		return resource;
	}

	@Override
	public String copy(MultipartFile file) throws IOException {
		String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename().replace(" ", "");
		Path pathToSave = this.getPath(fileName);
		log.info(pathToSave.toString());
		Files.copy(file.getInputStream(), pathToSave);
		return fileName;
	}

	@Override
	public boolean delete(String photoName) {
		Path lastPhotoPath = this.getPath(photoName);
		File fileLastPhoto = lastPhotoPath.toFile();
		if (fileLastPhoto.exists() && fileLastPhoto.canRead()) {
			fileLastPhoto.delete();
			return true;
		}
		return false;
	}

	@Override
	public Path getPath(String photoName) {
		// TODO Auto-generated method stub
		return Paths.get(DIRECTORY_UPLOAD).resolve(photoName).toAbsolutePath();
	}

}
