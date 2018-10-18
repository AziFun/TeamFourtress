package com.fourtress.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CombinationLock extends Lock {

	private char[] combinationCode;

	public CombinationLock(char[] combinationCode) {
		this.combinationCode = combinationCode;
		lock();

	}

	public void tryLock() {
		Scanner comboScanner = new Scanner(System.in);
		System.out.println("Please enter the combination code which you want to try: ");
		String enteredCombo = comboScanner.nextLine();
		tryCombination(enteredCombo);
		comboScanner.close();
	}

	private void tryCombination(String codeAttempt) {
		char[] codeToTry = codeAttempt.toCharArray();
		if (combinationCode.equals(codeToTry)) {
			System.out.println("Combination was correct! The lock is unlocked ");
			unlock();
		} else if (codeToTry.length != codeAttempt.length()) {
			System.out.println("Combination was incorrect! Your code had " + codeAttempt.length()
					+ "digits and we expect: " + combinationCode.length);
		} else {
			List<String> checks = new ArrayList<>();
			for (int i = 0; i < codeToTry.length; i++) {
				Character attempt = codeToTry[i];
				Character actual = combinationCode[i];
				if (attempt == actual) {
					checks.add(i + ": Correct!");
				} else {
					checks.add(i + ": Incorrect!");
				}
			}
			System.out.println("Combination was incorrect!" + checks.toString());
		}

	}

}
