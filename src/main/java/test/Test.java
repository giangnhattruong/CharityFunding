package test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Test {

	public static void main(String[] args) {
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
