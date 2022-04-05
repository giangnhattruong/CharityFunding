/*
 * Interface IImageAPI    1.00    2022-04-05
 */

package com.funix.multipart;

import org.springframework.web.multipart.MultipartFile;

/**
 * Interface for uploading, transforming and deleting images with API.
 * @author Giang_Nhat_Truong
 *
 */
public interface IImageAPI {
	
	/**
	 * Upload image to API server.
	 * @param multipartFile
	 * @return
	 */
	String uploadImage(MultipartFile multipartFile);
	
	/**
	 * Delete image from API server.
	 * @param imgURL
	 * @return
	 */
	String deleteImage(String imgURL);
	
	/**
	 * Transform image with API server.
	 * @param imgURL
	 * @param width
	 * @param height
	 * @return
	 */
	String transformImage(String imgURL, int width, int height);
	
}
