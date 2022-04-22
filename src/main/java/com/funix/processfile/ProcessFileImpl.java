/*
 * ProcessFile.java    1.00    2022-04-21.
 */

package com.funix.processfile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.funix.model.DonationHistory;
import com.funix.service.NullConvert;

/**
 * Process history uploaded files in CSV.
 * @author Giang_Nhat_Truong
 *
 */
public class ProcessFileImpl implements IProcessFile {

	/**
	 * Get list of donation history from uploaded file in CSV.
	 * @param file
	 * @return
	 */
	@Override
	public List<DonationHistory> getDonationHistory(MultipartFile file) {
		List<DonationHistory> historyList = new ArrayList<>();
		
		if (file.getOriginalFilename().endsWith("csv")) {
			try (BufferedReader bufferReader = 
					new BufferedReader(new InputStreamReader(file
							.getInputStream(), "UTF-8"))) {
				int lineNumber = 0;
				
				while (true) {
					String line = bufferReader.readLine();
					lineNumber++;
					
					// Screen out file header.
					if (lineNumber == 1) {
						continue;
					}
					
					if (line == null) {
						break;
					}
					
					// Get row values and add to DonationHistory object.
					DonationHistory history = getHistory(line);
					historyList.add(history);
				}
			} catch (IllegalStateException | IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		return historyList;
	}
	
	/**
	 * Get a history from file row/line.
	 * @param line
	 * @return
	 */
	private DonationHistory getHistory(String line) {
		DonationHistory history = new DonationHistory();
		String[] values = line.split(",");
		history.setUserID(NullConvert.toInt(values[0]));
		history.setCampaignID(NullConvert.toInt(values[1]));
		history.setDonation(NullConvert.toDouble(values[2]));
		history.setTransactionCode(values[3]);
		DateTimeFormatter formatter = DateTimeFormatter
				.ofPattern("yy/MM/dd");
		history.setDonationDate(LocalDate.parse(values[4], formatter));
		return history;
	}

}
