package br.com.erudio.services;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import br.com.erudio.config.FileStorageConfig;
import br.com.erudio.exceptions.FileStorageException;
import br.com.erudio.exceptions.MyFileNotFoundException;

@Service
public class FileStorageService {
	
	private final Path fileStorageLocation;

	@Autowired
	public FileStorageService(FileStorageConfig fileStorageConfig) {
		Path path = Paths.get(fileStorageConfig.getUploadDir())
			.toAbsolutePath().normalize();
		
		this.fileStorageLocation = path;
		
		//Caso o diretorio nao exista, ele cria
		try {
			Files.createDirectories(this.fileStorageLocation);
		} catch (Exception e) {
			throw new FileStorageException(
				"Could not create the directory where the uploaded files will be stored!", e);
		}
	}
	
	//Implementacao do servico de gravacao de arquivo em disco
	
	public String storeFile(MultipartFile file) {
	    String filename = StringUtils.cleanPath(file.getOriginalFilename());
	    try {
	        // Check if the filename contains invalid path sequence
	        if (filename.contains("..")) {
	            throw new FileStorageException("Sorry! Filename contains invalid path sequence " + filename);
	        }

	        // Check file size (example: max 5MB)
	        if (file.getSize() > 5 * 1024 * 1024) {
	            throw new FileStorageException("File size exceeds the maximum limit of 5MB");
	        }

	        // Validate file type (example: only text files allowed)
	        if (!filename.endsWith(".txt")) {
	            throw new FileStorageException("Invalid file type. Only .txt files are allowed");
	        }

	        Path targetLocation = this.fileStorageLocation.resolve(filename);
	        Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
	        
	        // Logging success
	        System.out.println("File " + filename + " stored successfully.");

	        return filename;
	    } catch (Exception e) {
	        // Logging failure
	        System.err.println("Could not store file " + filename + ". " + e.getMessage());

	        throw new FileStorageException("Could not store file " + filename + ". Please try again!", e);
	    }
	}
	
	
//	public String storeFile(MultipartFile file) {
//		String filename = StringUtils.cleanPath(file.getOriginalFilename());
//		try {
//			// Filename..txt
//			if (filename.contains("..")) {
//				throw new FileStorageException("Sorry! Filename contains invalid path sequence ");
//			}
//			Path targetLocation = this.fileStorageLocation.resolve(filename);
//			Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING); //o arquivo e craido vazio e vai de pedacinho montando ele
//			return filename;
//		} catch (Exception e) {
//			throw new FileStorageException(
//				"Could not store file " + filename + ". Please try again!", e);
//		}
//	}
	
	//Criando o controller responsavel pelo upload e download de arquivos
	public Resource loadFileAsResource(String filename) {
		try {
			Path filePath = this.fileStorageLocation.resolve(filename).normalize();
			Resource resource = new UrlResource(filePath.toUri());
			if (resource.exists()) return resource;
			else throw new MyFileNotFoundException("File not found");
		} catch (Exception e) {
			throw new MyFileNotFoundException("File not found" + filename, e);
		}
	}

}
