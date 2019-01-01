package com.harthoric.board.games.hangman;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;

public class Hangman {

	private static ArrayList<String> words = new ArrayList<String>();
	static {
		words.add("baffoon");
		words.add("jazz");
		words.add("blizzard");
		words.add("absurd");
		words.add("crypt");
		words.add("exodus");
		words.add("gazebo");
		words.add("frazzled");
		words.add("kilobyte");
		words.add("nowadays");
		words.add("oxidise");
		words.add("sphinx");
		words.add("antidisestablishmentarianism");
	}

	public static void main(String args[]) {
		try {
			LineIterator it = FileUtils.lineIterator(new File("res/hangman/stages"), "UTF-8");
			String line = "", manASCII = "";
			ArrayList<String> hangmen = new ArrayList<>();
			// Store each stage of the hanging man
			while (it.hasNext()) {
				line = it.nextLine();
				if (line.equals("================")) {
					hangmen.add(manASCII);
					manASCII = "";
				} else
					manASCII += line + "\r\n";
			}

			// Get a random word
			String answer = words.get(new Random().nextInt(words.size())).toLowerCase();
			// To store all attempted characters
			ArrayList<String> guessStrings = new ArrayList<>();
			// The guessed character
			String guess = "";
			// Number of guesses the user has had
			int guesses = 0;
			// Scanner for system input
			Scanner scanner = new Scanner(System.in);
			// The hidden answer displayed to the console
			String hiddenAnswer = hideAnswer(answer.split(""), "", answer.replaceAll(".", "_"));

			// Displayed welcome to console
			System.out.println(hangmen.get(guesses));
			System.out.println(hiddenAnswer);
			System.out.println("Hello, welcome to hangman. \nTake a guess:");
			guess = scanner.nextLine().toLowerCase();

			// Loop until all stages have been shown
			while (guesses != hangmen.size() - 1) {
				// Check whether the guess matches the actual answer
				if (guess.length() > 1 && answer.equals(guess))
					hiddenAnswer = guess;
				// Check whether the user has found the answer - through entering the characters
				// individually
				if (answer.equals(hiddenAnswer)) {
					System.out.println("Well done, that is the correct word!");
					break;
					// Check whether word contains the character entered
				} else if (answer.contains(guess)) {
					System.out.printf("\nCongratulations, the answer does contain '%s'!\n", guess);
					hiddenAnswer = hideAnswer(answer.split(""), guess, hiddenAnswer);
					// Check whether the character entered has previously been entered - no
					// duplicates
				} else if (!guessStrings.contains(guess)) {
					guesses++;
					guessStrings.add(guess);
				}
				System.out.println(hangmen.get(guesses) + "\n" + hiddenAnswer);
				System.out.println("Wrong answers: " + guessStrings);
				System.out.println("Take another guess!");
				guess = scanner.nextLine().toLowerCase();
			}
			scanner.close();
			// If the user did not get the answer, display the answer
			if (!answer.equals(hiddenAnswer))
				System.out.printf("Unlucky, all your guesses are up! The actual answer was %s", answer);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String hideAnswer(String answer[], String character, String hiddenAnswer) {
		String newHidden = "";
		String hiddenAnswers[] = hiddenAnswer.split("");
		for (int i = 0; i < answer.length; i++) {
			newHidden += hiddenAnswers[i].equals("_") ? answer[i].equals(character) ? character : "_"
					: hiddenAnswers[i];
		}
		return newHidden;
	}

}
