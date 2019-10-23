package com.aubgteam.auctionhouse.Services;

import com.aubgteam.auctionhouse.Models.Image;
import com.aubgteam.auctionhouse.Repositories.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

@Service
public class ImageService {
    @Autowired
    private ImageRepository imageRepository;

    public Image save(MultipartFile imagePath) throws IOException  {
        String fileName = StringUtils.cleanPath(imagePath.getOriginalFilename());

//        try {
//            // Check if the file's name contains invalid characters
////            if(fileName.contains("..")) {
////                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
//            }

            Image image = new Image(fileName, imagePath.getContentType(), Base64.getEncoder().encodeToString(imagePath.getBytes()));

            return imageRepository.save(image);
//        } catch (IOException ex) {
//            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
//        }


    }

    public Image get(long fileId) {
        return imageRepository.findById(fileId).orElse(null);
//                .orElseThrow(() -> new MyFileNotFoundException("File not found with id " + fileId));
    }

    public void delete(long imageId)
    {
        imageRepository.deleteById(imageId);
    }
//    public String getImageContentInBase64(long imageId)
//    {
//        return Base64.getEncoder().encodeToString(imageRepository.findById(imageId).orElse(null).());
//    }
}

