package com.dawood.hotelbooking.service;

import com.cloudinary.Cloudinary;
import com.dawood.hotelbooking.exceptions.BookingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CloudinaryService {

  private final Cloudinary cloudinary;

  public String uploadFile(MultipartFile file, String folderName){
    try {
      HashMap<String, Object> options = new HashMap<>();
      options.put("folder", folderName);
      Map uploadedFolder = cloudinary.uploader().upload(file.getBytes(),options);
      String publicId = (String) uploadedFolder.get("public_id");
      return cloudinary.url().secure(true).generate(publicId);

    }catch (Exception e){
      e.printStackTrace();
      throw new BookingException("Unable to upload image to cloudinary");
    }
  }
}
