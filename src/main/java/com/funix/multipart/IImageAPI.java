package com.funix.multipart;

import org.springframework.web.multipart.MultipartFile;

public interface IImageAPI {
	String uploadImage(MultipartFile multipartFile);
	String deleteImage(String imgURL);
	String transformImage(String imgURL, int width, int height);
}
