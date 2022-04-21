/*
 * Interface IProcessFile.java    1.00    2022-04-21.
 */

package com.funix.processfile;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.funix.model.DonationHistory;

/**
 * Process uploaded files.
 * @author Giang_Nhat_Truong
 *
 */
public interface IProcessFile {

	/**
	 * Get list of donation history from uploaded file.
	 * @param file
	 * @return
	 */
	List<DonationHistory> getDonationHistory(MultipartFile multipartFile);
}
