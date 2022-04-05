/*
 * CloudinaryImpl.java    1.00    2022-04-05
 */

package com.funix.multipart;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;

/**
 * Upload, transform and delete images with Cloudinary API.
 * @author Giang_Nhat_Truong
 *
 */
public class CloudinaryImpl implements IImageAPI {
	
	/**
	 * Cloudinary object which contains methods to
	 * upload images to and manipulate images
	 * in Cloudinary server.
	 */
	private Cloudinary cloudinary;
	
	/**
	 * Inject Cloudinary instance from Controller.
	 * @param cloudinary
	 */
	public CloudinaryImpl(Cloudinary cloudinary) {
		this.cloudinary = cloudinary;
	}
	
	/**
	 * Upload an image to Cloudinary server.
	 * @param multipartFile
	 * @return
	 */
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
			e.printStackTrace();
		}
		
		return imgURL;
	}
	
	/**
	 * Delete an image from Cloudinary server.
	 * @param imgURL
	 * @return
	 */
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
	
	/**
	 * Transform an image from Cloudinary server.
	 * @param imgURL
	 * @param width
	 * @param height
	 * @return
	 */
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
	
	/**
	 * Convert and transfer MultipartFile value
	 * to File value.
	 * @param multipartFile
	 * @return
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	private File toFile(MultipartFile multipartFile) 
			throws IllegalStateException, IOException {
		File file = new File(multipartFile.getOriginalFilename());
		multipartFile.transferTo(file);
		return file;
	}
	
	/**
	 * Get image publicID from image URL.
	 * @param imgURL
	 * @return
	 */
	private String getPublicID(String imgURL) {
		String[] imgURLparts = imgURL.split("/");
		String imgURLLastPart = imgURLparts[imgURLparts.length - 1];
		return imgURLLastPart.substring(0, 
				imgURLLastPart.lastIndexOf("."));
	}
	
	/**
	 * Get image version from image URL.
	 * @param imgURL
	 * @return
	 */
	private String getVersion(String imgURL) {
		String[] imgURLparts = imgURL.split("/");
		String imgURLLastPart = imgURLparts[imgURLparts.length - 2];
		return imgURLLastPart.substring(1);
	}

}
