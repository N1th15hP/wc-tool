package com.wc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class WordCount {



	private static String getProcessedOutput(String command) {
		String[] commandParts = command.split("\\s+");
		String result = "";


			switch (commandParts[1]) {
				case "-c": {
					result = getBytesCount(commandParts[2]) + " " + new File(commandParts[2]).getName();
					break;
				}
	
				case "-l": {
					result = getLinesCount(commandParts[2])+ " " + new File(commandParts[2]).getName();
					break;
				}
				
				case "-w": {
					result = getWordsCount(commandParts[2])+ " " + new File(commandParts[2]).getName();
					break;
				}
				
				case "-m": {
					result = getCharactersCount(commandParts[2])+ " " + new File(commandParts[2]).getName();
					break;
				}
				
				default : {
					result += " " +getBytesCount(commandParts[1]);
					result += " " +getLinesCount(commandParts[1]);
					result += " " +getWordsCount(commandParts[1]);
					result += " " +getCharactersCount(commandParts[1]);
					result += " " + new File(commandParts[1]).getName();
				}
			}


		return result;
	}
	
	// wc -m C:\Users\Nithish\Downloads\test.txt
	private static String getCharactersCount(String fileLocation) {
		File file = new File(fileLocation);

		int charactersCount = 0;
		String line;

		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
			while ((line = reader.readLine()) != null) {
				charactersCount += line.length();
			}
		} catch (FileNotFoundException e) {
			return "Unable to find " + file.getName();
		} catch (IOException e) {
			return "Error occured on accessing " + file.getName() + " : " + e.getMessage();
		}

		return String.valueOf(charactersCount);
	}

	// wc -w C:\Users\Nithish\Downloads\test.txt
	private static String getWordsCount(String fileLocation) {
		File file = new File(fileLocation);
		int wordsCount = 0;
		
		try(BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)))){
			boolean inWord = false;
			int c;
			
            while((c = reader.read()) != -1) {
                if (Character.isWhitespace(c)) {
                    if (inWord) {
                        inWord = false;
                    }
                } else {
                    if (!inWord) {
                        inWord = true;
                        wordsCount++;
                    }
                }
            }
		} catch (FileNotFoundException e) {
			return "Unable to find " + file.getName();
		} catch (IOException e) {
			return "Error occured on accessing " + file.getName() + " : " + e.getMessage();
		}
		return String.valueOf(wordsCount);
	}

	// wc -l C:\Users\Nithish\Downloads\test.txt
	private static String getLinesCount(String fileLocation) {
		File file = new File(fileLocation);

		int linesCount = 0;

		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
			while (reader.readLine() != null) {
				linesCount++;
			}
		} catch (FileNotFoundException e) {
			return "Unable to find " + file.getName();
		} catch (IOException e) {
			return "Error occured on accessing " + file.getName() + " : " + e.getMessage();
		}

		return String.valueOf(linesCount);
	}

	// wc -c C:\Users\Nithish\Downloads\test.txt
	private static String getBytesCount(String fileLocation) {
		File file = new File(fileLocation);
		return String.valueOf(file.length());
	}

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);

		while (true) {
			System.out.print("wc :> ");

			String command = scanner.nextLine();
			if (command.isEmpty()) {
				continue;
			}

			if (command.equalsIgnoreCase("exit")) {
				break;
			}

			String output = getProcessedOutput(command);
			System.out.println(output);
		}

		scanner.close();

	}

}
