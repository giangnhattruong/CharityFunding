/*
 * CloudinaryConfig.java    1.00    2022-04-05
 */

package com.funix.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

/**
 * Cloudinary configuration.
 * @author Giang_Nhat_Truong
 *
 */
@Configuration
public class CloudinaryConfig {

	/**
	 * Get connect to Cloudinary API and
	 * create a Cloudinary instance for uploading,
	 * transforming, deleting images.
	 * @return
	 */
	@Bean
	public Cloudinary getCloudinary() {
		return new Cloudinary(ObjectUtils.asMap(
				"cloud_name", MyKey.CLOUDINARY_API_NAME, 
				"api_key", MyKey.CLOUDINARY_API_KEY,
				"api_secret", MyKey.CLOUDINARY_API_SECRET, 
				"secure", true));
	}

}
