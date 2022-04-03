package com.funix.multipart;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;

public class CloudinaryImpl implements IImageAPI {
	private Cloudinary cloudinary;
	
	public CloudinaryImpl(Cloudinary cloudinary) {
		this.cloudinary = cloudinary;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public String uploadImage(MultipartFile multipartFile) {
		String imgURL = "";
		
		try {
			File file = toFile(multipartFile);
			Map<String, Object> result = cloudinary
					.uploader().upload(file, ObjectUtils.emptyMap());
			imgURL = (String) result.get("url");
		} catch (IllegalStateException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return imgURL;
	}
	
	@Override
	public String deleteImage(String imgURL) {
		String result = "";
		String publicID = getPublicID(imgURL);
		
		try {
			result = (String) cloudinary
					.uploader().destroy(publicID, ObjectUtils.emptyMap())
					.get("result");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public String transformImage(String imgURL, int width, int height) {
		String publicID = getPublicID(imgURL);
		String version = getVersion(imgURL);
		
		return cloudinary.url()
				.format("jpg").version(version).transformation(
			    new Transformation().width(width).height(height).crop("fill"))
				.imageTag(publicID, ObjectUtils.emptyMap());
	}
	
	private File toFile(MultipartFile multipartFile) 
			throws IllegalStateException, IOException {
		File file = new File(multipartFile.getOriginalFilename());
		multipartFile.transferTo(file);
		return file;
	}
	
	private String getPublicID(String imgURL) {
		String[] imgURLparts = imgURL.split("/");
		String imgURLLastPart = imgURLparts[imgURLparts.length - 1];
		return imgURLLastPart.substring(0, 
				imgURLLastPart.lastIndexOf("."));
	}
	
	private String getVersion(String imgURL) {
		String[] imgURLparts = imgURL.split("/");
		String imgURLLastPart = imgURLparts[imgURLparts.length - 2];
		return imgURLLastPart.substring(1);
	}

}
