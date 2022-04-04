package test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Test {

	public static void main(String[] args) {
		testEmail("abc@test.com");
		testPassword("sdB123#asf");
		testPhone("0978523650");
	}
	
	public static boolean testEmail(String email) {
		String emailRegex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
		System.out.println("Email: " + email.matches(emailRegex));
		return email.matches(emailRegex);
	}
	
	public static boolean testPassword(String password) {
		String passwordRegex = 
				"^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)"
				+ "(?=.*[@#$!%*?&])[A-Za-z\\d@#$!%*?&]{8,10}$";
		System.out.println("Password: " + password.matches(passwordRegex));
		return password.matches(passwordRegex);
	}
	
	public static boolean testPhone(String phone) {
		String phoneRegex = "^\\d{10}$";
		System.out.println("Phone: " + phone.matches(phoneRegex));
		return phone.matches(phoneRegex);
	}

	public static void testIO() {
		try (FileReader reader = new FileReader("testInput.csv");
				FileWriter writer = new FileWriter("testOutput.txt", false)) {
			BufferedReader bufferReader = new BufferedReader(reader);
			StringBuilder content = new StringBuilder();
			String output = "";
			int lineNumber = 0;
			
			while (true) {
				String line = bufferReader.readLine();
				lineNumber++;
				
				if (lineNumber == 1) {
					continue;
				}
				
				if (line == null) {
					break;
				}
					
				content.append(line);
				content.append("\n");
			}
			
			output = content.toString();
			writer.write(output);
			System.out.println("Test IO done.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
