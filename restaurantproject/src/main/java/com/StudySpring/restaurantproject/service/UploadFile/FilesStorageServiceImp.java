package com.StudySpring.restaurantproject.service.UploadFile;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

public class FilesStorageServiceImp implements FilesStorageService{
    @Value("${fileUpload.rootPath}")
    private String rootPath;

    private Path root;
    @Override
    public void init() {
        try{
            root = Paths.get(rootPath);
            if(Files.notExists(root)){
                Files.createDirectories(root);
            }
        }catch (Exception e){
            System.out.println("Error create file");
        }
    }

    @Override
    public boolean save(MultipartFile file) {
        try {
            init();
            Files.copy(file.getInputStream(), root.resolve(file.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);
            return true;
        }catch(Exception e){
            System.out.println("Error save file!");
            return false;
        }
    }

    @Override
    public Resource load(String filename) {
        try{
            init();
            Path file = root.resolve(filename);
            Resource resource = new UrlResource(file.toUri());
            if(resource.exists() || resource.isReadable()){
                return resource;
            }
        }catch(Exception e){
            System.out.println("Error load file!");
        }
        return null;
    }

    @Override
    public void deleteAll() {

    }

    @Override
    public Stream<Path> loadAll() {
        return Stream.empty();
    }
}
