package com.example.sudokuproject;

import java.util.Random;
import java.util.Stack;

public class SudokuPuzzle {

	public int[][] actual = new int[9][9];
	public String[][] possible = new String[9][9];
	int totalscore = 0;
	Stack<Integer> ActualStack = new Stack<Integer>();
	Stack<String> PossibleStack = new Stack<String>();
	public int[][] actual_backup = new int[9][9];

	public String CalculatePossibleValues(int col, int row) {

		String str;
		if (possible[col][row] == null) {
			str = "123456789";
		} else
			str = possible[col][row];
		// int c = 0, r = 0;
		for (int r = 0; r < 9; r++) {
			if (actual[col][r] != 0) {
				str = str.replace(Integer.toString(actual[col][r]), "");
			}
		}
		for (int c = 0; c < 9; c++) {
			if (actual[c][row] != 0) {
				str = str.replace(Integer.toString(actual[c][row]), "");
			}
		}
		int startC = col - (col % 3);
		int startR = row - (row % 3);

		for (int i = startR; i <= startR + 2; i++) {
			for (int j = startC; j <= startC + 2; j++) {
				if (actual[j][i] != 0) {
					str = str.replace(Integer.toString(actual[j][i]), "");
				}
			}
		}
		return str;
	}

	public boolean CheckColumnsAndRows() {
		boolean changes = false;
		for (int row = 0; row < 9; row++) {
			for (int col = 0; col < 9; col++) {
				if (actual[col][row] == 0) {
					possible[col][row] = CalculatePossibleValues(col, row);
					if (possible[col][row].length() == 1) {
						actual[col][row] = Integer.parseInt(possible[col][row]);

						System.out.println("Sử dụng CSME: " + col + " " + row
								+ " " + Integer.parseInt(possible[col][row]));

						changes = true;
						totalscore += 1;
					}
				}
			}
		}
		return changes;
	}

	public boolean IsSolvePuzzle() {
		String pattern;
		int r, c;
		// Kiem tra hang
		for (r = 0; r < 9; r++) {
			pattern = "123456789";
			for (c = 0; c < 9; c++) {
				pattern = pattern.replace(Integer.toString(actual[c][r]), "");
			}
			if (pattern.length() > 0) {
				return false;
			}
		}
		// Kiem tra cot
		for (c = 0; c < 9; c++) {
			pattern = "123456789";
			for (r = 0; r < 9; r++) {
				pattern = pattern.replace(Integer.toString(actual[c][r]), "");
			}
			if (pattern.length() > 0) {
				return false;
			}
		}
		// kiem tra o vuong
		for (c = 0; c < 9; c = c + 3) {
			pattern = "123456789";
			for (r = 0; r < 9; r = r + 3) {
				for (int cc = 0; cc < 3; cc++) {
					for (int rr = 0; rr < 3; rr++) {
						pattern = pattern.replace(
								Integer.toString(actual[c + cc][r + rr]), "");
					}
				}
			}
			if (pattern.length() > 0) {
				return false;
			}
		}
		return true;
	}

	public boolean LookForLoneRangersinMinigrids() {
		boolean changes = false;
		boolean NextMiniGrid = false;
		int occurrence = 0;
		int cPos = 0, rPos = 0;
		for (int n = 0; n < 9; n++) {
			for (int r = 0; r < 9; r += 3) {
				for (int c = 0; c < 9; c += 3) {
					NextMiniGrid = false;
					occurrence = 0;
					for (int rr = 0; rr < 3; rr++) {
						for (int cc = 0; cc < 3; cc++) {
							if (actual[c + cc][r + rr] == 0
									&& possible[c + cc][r + rr]
											.contains(Integer.toString(n))) {
								occurrence += 1;
								cPos = c + cc;
								rPos = r + rr;
								if (occurrence > 1) {
									NextMiniGrid = true;
									break;
								}
							}
							if (NextMiniGrid == true) {
								break;
							}
						}
					}
				}
			}

			if (!NextMiniGrid && occurrence == 1) {
				System.out.println("Sử dụng LoneRangersinMinigrids: " + cPos
						+ " " + rPos + " " + n);
				actual[cPos][rPos] = n;
				changes = true;

				totalscore += 2;
			}
		}
		return changes;

	}

	public boolean LookForLoneRangersinRows() {
		boolean changes = false;
		int occurrence = 0;
		int cPos = 0, rPos = 0;
		for (int r = 0; r < 9; r++) {
			for (int n = 0; n < 9; n++) {
				occurrence = 0;
				for (int c = 0; c < 9; c++) {
					if (actual[c][r] == 0
							&& possible[c][r].contains(Integer.toString(n))) {
						occurrence += 1;
						if (occurrence > 1) {
							break;
						}
						cPos = c;
						rPos = r;
					}
				}
				if (occurrence == 1) {
					System.out.println("Sử dụng LoneRangersinRows: " + cPos
							+ " " + rPos + " " + n);
					actual[cPos][rPos] = n;

					changes = true;
					totalscore += 2;
				}
			}
		}
		return changes;
	}

	public boolean LookForLoneRangersinColumns() {
		boolean changes = false;
		int occurrence = 0;
		int cPos = 0, rPos = 0;
		for (int c = 0; c < 9; c++) {
			for (int n = 0; n < 9; n++) {
				occurrence = 0;
				for (int r = 0; r < 9; r++) {
					if (actual[c][r] == 0
							&& possible[c][r].contains(Integer.toString(n))) {
						occurrence += 1;
						if (occurrence > 1) {
							break;
						}
						cPos = c;
						rPos = r;
					}
				}
				if (occurrence == 1) {
					System.out.println("Sử dụng LoneRangersinCollums: " + cPos
							+ " " + rPos + " " + n);
					actual[cPos][rPos] = n;

					changes = true;
					totalscore += 2;
				}
			}
		}
		return changes;
	}

	public boolean LookForTwinsinMinigrids() {
		boolean changes = false;

		for (int r = 0; r < 9; r++) {
			for (int c = 0; c < 9; c++) {

				if (actual[c][r] == 0 && possible[c][r].length() == 2) {

					int startC = c - (c % 3);
					int startR = r - (r % 3);
					for (int rr = startR; rr <= startR + 2; rr++) {
						for (int cc = startC; cc <= startC + 2; cc++) {
							if (!((cc != c) && (rr != r))
									&& (possible[cc][rr] == possible[c][r])) {

								for (int rrr = startR; rrr <= startR + 2; rrr++) {
									for (int ccc = startC; ccc <= startC + 2; ccc++) {
										if (actual[ccc][rrr] == 0
												&& (possible[ccc][rrr] != possible[c][r])) {

											String original_possible = possible[ccc][rrr];

											possible[ccc][rrr] = possible[ccc][rrr]
													.replace(
															Character
																	.toString(possible[c][r]
																			.charAt(0)),
															"");
											possible[ccc][rrr] = possible[ccc][rrr]
													.replace(
															Character
																	.toString(possible[c][r]
																			.charAt(1)),
															"");
											if (original_possible != possible[ccc][rrr]) {
												changes = true;
											}
											/*
											 * if (possible[ccc][rrr]=="") {
											 * 
											 * }
											 */
											if (possible[ccc][rrr].length() == 1) {
												actual[ccc][rrr] = Integer
														.parseInt(possible[ccc][rrr]);
												totalscore += 3;
												System.out
														.println("LookForTwinsinMinigrids "
																+ ccc
																+ " "
																+ rrr
																+ " "
																+ Integer
																		.parseInt(possible[ccc][rrr]));

											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
		return changes;
	}

	public boolean LookForTwinsinRows() {
		boolean changes = false;
		for (int r = 0; r < 9; r++) {
			for (int c = 0; c < 9; c++) {
				if (actual[c][r] == 0 && possible[c][r].length() == 2) {
					System.out.println("test");
					for (int cc = c + 1; cc < 9; cc++) {
						if (possible[cc][r] == possible[c][r]) {
							for (int ccc = 0; ccc < 9; ccc++) {
								if ((actual[ccc][r] == 0) && (ccc != c)
										&& (ccc != cc)) {
									String original_possible = possible[ccc][r];
									possible[ccc][r] = possible[ccc][r]
											.replace(Character
													.toString(possible[c][r]
															.charAt(0)), "");
									possible[ccc][r] = possible[ccc][r]
											.replace(Character
													.toString(possible[c][r]
															.charAt(1)), "");
									if (original_possible != possible[ccc][r]) {
										changes = true;

									}
									if (possible[ccc][r] == "") {

									}
									if (possible[ccc][r].length() == 1) {
										actual[ccc][r] = Integer
												.parseInt(possible[ccc][r]);
										totalscore += 3;
										System.out
												.println("LookForTwinsinRows");
									}
								}

							}
						}
					}
				}
			}
		}
		return changes;
	}

	public String RandomizeThePossibleValues(String str) {

		char[] s = new char[str.length()];
		char temp;
		int j = 0;
		s = str.toCharArray();
		Random rd = new Random();
		for (int i = 0; i <= str.length() - 1; i++) {
			j = rd.nextInt(str.length());
			temp = s[i];
			s[i] = s[j];
			s[j] = temp;
		}
		String str1 = new String("");
		for (int i = 0; i < s.length; i++) {
			str1 = str1 + s[i];
		}

		return str1;

	}

	public int FindCollumCellWithFewestPossibleValues() {
		int min = 10;
		int a = 0;
		for (int r = 0; r < 9; r++) {
			for (int c = 0; c < 9; c++) {
				if (actual[c][r] == 0 && possible[c][r].length() < min) {
					min = possible[c][r].length();
					a = c;
				}
			}
		}
		return a;

	}

	public int FindRowCellWithFewestPossibleValues() {
		int min = 10;
		int a = 0;
		for (int r = 0; r < 9; r++) {
			for (int c = 0; c < 9; c++) {
				if (actual[c][r] == 0 && possible[c][r].length() < min) {
					min = possible[c][r].length();
					a = r;
				}
			}
		}
		return a;

	}

	public void SolvePuzzleByBruteForce() {
		int c = FindCollumCellWithFewestPossibleValues();
		int r = FindRowCellWithFewestPossibleValues();
		totalscore += 5;
		String possibleValues = RandomizeThePossibleValues(possible[c][r]);
		System.out.print(possibleValues);

	}

	public void GenerateNewPuzzle(int level, int score) {

		String str;
		int numberofemptycells = 0;
		for (int r = 0; r < 9; r++) {
			for (int c = 0; c < 9; c++) {
				actual[c][r] = 0;
				possible[c][r] = "";
			}
		}
		ActualStack.clear();
		PossibleStack.clear();

		if (!SolvePuzzle()) {
			SolvePuzzleByBruteForce();
		}
		actual_backup = actual.clone();

		switch (level) {
		case 1:
			numberofemptycells = 43;
			break;
		case 2:
			numberofemptycells = 48;
			break;
		case 3:
			numberofemptycells = 51;
			break;
		case 4:
			numberofemptycells = 56;
			break;

		}
		ActualStack.clear();
		PossibleStack.clear();

	}

	public void CreateEmplyCells(int empty) {
		int c = 0, r = 0;
		String[] emptyCells = new String[empty - 1];
		for (int i = 0; i <= (empty / 2); i++) {
			boolean duplicate = false;
			do {
				duplicate = false;
				do {
					c = RandomNumber(0, 8);
					r = RandomNumber(0, 4);
				} while (r == 4 && c > 4);
				for (int j = 0; j <= i; j++) {
					if (emptyCells[j] == (Integer.toString(c) + Integer
							.toString(r))) {
						duplicate = true;
						break;
					}
				}
				if (!duplicate) {
					emptyCells[i] = Integer.toString(c) + Integer.toString(r);
					actual[c][r] = 0;
					possible[c][r] = null;
					emptyCells[empty - 2 - i] = Integer.toString(8 - c)
							+ Integer.toString(8 - r);
					actual[8 - c][8 - r] = 0;
					possible[8 - c][8 - r] = null;

				}
			} while (duplicate);
		}
	}

	public boolean LookForTwinsinColumns() {
		boolean changes = false;
		for (int c = 0; c < 9; c++) {
			for (int r = 0; r < 9; r++) {
				if (actual[c][r] == 0 && possible[c][r].length() == 2) {
					for (int rr = r + 1; rr < 9; rr++) {
						if (possible[c][rr] == possible[c][r]) {
							for (int rrr = 0; rrr < 9; rrr++) {
								if ((actual[c][rrr] == 0) && (rrr != r)
										&& (rrr != rr)) {
									String original_possible = possible[c][rrr];
									possible[c][rrr] = possible[c][rrr]
											.replace(Character
													.toString(possible[c][r]
															.charAt(0)), "");
									possible[c][rrr] = possible[c][rrr]
											.replace(Character
													.toString(possible[c][r]
															.charAt(1)), "");
									if (original_possible != possible[c][rrr]) {
										changes = true;
									}
									if (possible[c][rrr].length() == 1) {
										actual[c][rrr] = Integer
												.parseInt(possible[c][rrr]);
										totalscore += 3;
										System.out
												.println("LookForTwinsinColumns");
									}
								}
							}
						}
					}
				}
			}
		}
		return changes;
	}

	public boolean LookForTripletsinMinigrids() {
		boolean changes = false;
		for (int r = 0; r < 9; r++) {
			for (int c = 0; c < 9; c++) {
				if (actual[c][r] == 0 && possible[c][r].length() == 3) {
					String tripletsLocation = Integer.toString(c)
							+ Integer.toString(r);
					int startR = 0;
					int startC = 0;
					startC = c - (c % 3);
					startR = r - (r % 3);
					for (int rr = startR; rr <= startR + 2; rr++) {
						for (int cc = startC; cc <= startC + 2; cc++) {
							if ((!((cc == c) && (rr == r)))
									&& ((possible[cc][rr] == possible[c][r]) || (possible[cc][rr]
											.length() == 2
											&& possible[c][r]
													.contains(Character
															.toString(possible[cc][rr]
																	.charAt(0))) && possible[c][r]
												.contains(Character
														.toString(possible[cc][rr]
																.charAt(1)))))) {

							}

						}
					}
					if (tripletsLocation.length() == 6) {
						for (int rrr = startR; rrr <= startR + 2; rrr++) {
							for (int ccc = startC; ccc <= startC + 2; ccc++) {
								if (actual[ccc][rrr] == 0
										&& ccc != Integer.valueOf(Character
												.toString(tripletsLocation
														.charAt(0)))
										&& rrr != Integer.valueOf(Character
												.toString(tripletsLocation
														.charAt(1)))
										&& ccc != Integer.valueOf(Character
												.toString(tripletsLocation
														.charAt(2)))
										&& rrr != Integer.valueOf(Character
												.toString(tripletsLocation
														.charAt(3)))
										&& ccc != Integer.valueOf(Character
												.toString(tripletsLocation
														.charAt(4)))
										&& rrr != Integer.valueOf(Character
												.toString(tripletsLocation
														.charAt(5)))) {
									String original_possible = possible[ccc][rrr];
									possible[ccc][rrr] = possible[ccc][rrr]
											.replace(Character
													.toString(possible[c][r]
															.charAt(0)), "");
									possible[ccc][rrr] = possible[ccc][rrr]
											.replace(Character
													.toString(possible[c][r]
															.charAt(1)), "");
									possible[ccc][rrr] = possible[ccc][rrr]
											.replace(Character
													.toString(possible[c][r]
															.charAt(2)), "");
									if (original_possible != possible[ccc][rrr]) {
										changes = true;
									}
									if (possible[ccc][rrr] == "") {

									}
									if (possible[ccc][rrr].length() == 1) {
										actual[ccc][rrr] = Integer
												.valueOf(possible[ccc][rrr]);
										totalscore += 4;
									}
								}
							}
						}
					}
				}
			}
		}
		return changes;
	}

	public boolean SolvePuzzle() {
		boolean changes = false;
		boolean exitloop = false;

		do {
			do {
				do {

					do {
						do {
							do {
								do {
									changes = CheckColumnsAndRows();
									if (IsSolvePuzzle()) {
										exitloop = true;
										break;
									}

								} while (changes);
								if (exitloop) {
									break;
								}
								changes = LookForLoneRangersinMinigrids();
								if (IsSolvePuzzle()) {
									break;
								}
							} while (changes);
							if (exitloop) {
								break;
							}
							changes = LookForLoneRangersinRows();
							if (IsSolvePuzzle()) {
								exitloop = true;
								break;
							}
						} while (changes);
						if (exitloop) {
							break;
						}
						changes = LookForLoneRangersinColumns();
						if (IsSolvePuzzle()) {
							exitloop = true;
							break;
						}
					} while (changes);
					changes = LookForTwinsinMinigrids();
					if (IsSolvePuzzle()) {
						exitloop = true;
						break;
					}
				} while (changes);
				changes = LookForTwinsinRows();
				if (IsSolvePuzzle()) {
					exitloop = true;
					break;
				}
			} while (changes);
			changes = LookForTwinsinColumns();
			if (IsSolvePuzzle()) {
				exitloop = true;
				break;
			}
		} while (changes);

		if (IsSolvePuzzle()) {
			return true;
		} else
			return false;
	}

	public int RandomNumber(int from, int to) {
		Random rd = new Random();
		return from + rd.nextInt(to - from + 1);
	}

	public int countNumber0() {
		int dem = 0;
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (actual[i][j] == 0) {
					dem++;
				}
			}
		}
		return dem;
	}

	public int[][] CreateFullPuzzle() {
		int[][] fullpuzzle = new int[9][9];
		Generate g = new Generate();
		g.InitValue();
		g.generate(1);
		fullpuzzle = g.getActual();
		return fullpuzzle;
	}

	public void CreateNewPuzzle(int level) {
		int emptyCell = 0;
		switch (level) {
		case 1:
			emptyCell = RandomNumber(30, 40);
			break;
		case 2:
			emptyCell = RandomNumber(40, 45);
			break;
		case 3:
			emptyCell = RandomNumber(45, 60);
			break;
		case 4:
			emptyCell = RandomNumber(60, 75);
			break;
		default:
			break;
		}

	}
}
