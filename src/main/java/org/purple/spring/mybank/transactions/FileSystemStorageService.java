package org.purple.spring.mybank.transactions;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

import org.purple.spring.mybank.errors.TransactionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileSystemStorageService implements StorageService {

	private final Path rootLocation;

	@Autowired
	public FileSystemStorageService(StorageProperties properties) {
		if(properties.getLocation().trim().length() == 0) {
			throw new TransactionException("File upload location cannot be empty");
		}
		this.rootLocation = Paths.get(properties.getLocation());
	}

	@Override
	public void store(MultipartFile file) {
		try {
			if (file.isEmpty()) {
				throw new TransactionException("File is empty");
			}
			Path destinationFile = rootLocation.resolve(Paths.get(file.getOriginalFilename())).normalize()
					.toAbsolutePath();
			if (!destinationFile.getParent().equals(rootLocation.toAbsolutePath())) {
				throw new TransactionException("Cannot store file outside current directory");
			}
			try (InputStream inputStream = file.getInputStream()) {
				Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
			}
		} catch (IOException e) {
			throw new TransactionException("Failed to store file", e);
		}
	}

	@Override
	public Stream<Path> loadAll() {
		try {
			return Files.walk(rootLocation, 1).filter(path -> !path.equals(rootLocation)).map(rootLocation::relativize);
		} catch (IOException e) {
			throw new TransactionException("Failed to read stored files", e);
		}
	}

	@Override
	public Path load(String filename) {
		return rootLocation.resolve(filename);
	}

	@Override
	public Resource loadAsResource(String filename) {
		try {
			Path file = load(filename);
			Resource resource = new UrlResource(file.toUri());
			if (resource.exists() && resource.isReadable()) {
				return resource;
			} else {
				throw new StorageFileNotFoundException("Could not read file: " + file);
			}
		} catch (MalformedURLException e) {
			throw new StorageFileNotFoundException("Could not read file: " + filename, e);
		}
	}
	
	@Override
	public void init() {
		try {
			Files.createDirectories(rootLocation);
		}
		catch (IOException e) {
			throw new TransactionException("Could not initialize storage", e);
		}
	}

}
