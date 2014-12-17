package com.example.sudokuproject;

import java.util.ArrayList;
import java.util.Random;

public class Generate {
	ArrayList cols[];
	ArrayList rows[];
	ArrayList boxes[];
	private int sudoku[] = new int[82];

	/*
	 * public static void main(String... args){
	 * 
	 * 
	 * Generate g = new Generate(); g.InitValue(); g.generate(1); String s= "";
	 * for(int i = 1; i <= 81; i++) { s += g.sudoku[i] + " "; if(i%9==0) s+=
	 * "\n"; }
	 * 
	 * int pos = 1; for (int i = 0; i < 9; i++) { for (int j = 0; j < 9; j++) {
	 * actual[i][j] = Integer.toString(sudoku[pos]); pos++; } }
	 * 
	 * for (int r = 0; r < 9; r++) { for (int c = 0; c < 9; c++) {
	 * System.out.print(actual[c][r] + " "); if (c == 8) System.out.println("");
	 * } } }
	 */
	public int[][] getActual() {
		int[][] actual = new int[9][9];
		int pos = 1;
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				actual[i][j] = sudoku[pos];
				pos++;
			}
		}

		return actual;
	}

	public void InitValue() {
		cols = new ArrayList[10];
		rows = new ArrayList[10];
		boxes = new ArrayList[10];

		for (int i = 1; i <= 9; i++) {
			ArrayList<Integer> arraycol = new ArrayList<Integer>();
			ArrayList<Integer> arrayrow = new ArrayList<Integer>();
			ArrayList<Integer> arraybox = new ArrayList<Integer>();
			for (int j = 0; j <= 9; j++) {
				arraycol.add(j);
				arrayrow.add(j);
				arraybox.add(j);
			}
			cols[i] = arraycol;
			rows[i] = arrayrow;
			boxes[i] = arraybox;
		}
	}

	int getBox(int row, int col) {
		if (row <= 3)
			if (col <= 3)
				return 1;
			else if (col <= 6)
				return 2;
			else
				return 3;

		else if (row <= 6)
			if (col <= 3)
				return 4;
			else if (col <= 6)
				return 5;
			else
				return 6;

		else if (row <= 9)
			if (col <= 3)
				return 7;
			else if (col <= 6)
				return 8;
			else
				return 9;
		return 0;
	}

	public boolean generate(int pos) {
		ArrayList<Integer> pool = new ArrayList<Integer>();

		int row = (int) (Math.ceil((double) pos / 9));
		int col = pos % 9;
		if (col == 0)
			col = 9;
		int box = getBox(row, col);
		for (int i = 1; i <= 9; i++)
			if (rows[row].contains(i) && cols[col].contains(i)
					&& boxes[box].contains(i)) {
				pool.add(i);
			}

		int rd;
		Integer sel = 0;
		boolean check = false;
		if (pool.size() == 0)
			return false;

		while (!check) {
			if (pool.size() == 0)
				return false;
			else {
				rd = (int) (Math.ceil(Math.random() * (pool.size() - 1)));
				sel = pool.get(rd);

				sudoku[pos] = sel;

				rows[row].remove((Integer) sel);
				cols[col].remove((Integer) sel);
				boxes[box].remove((Integer) sel);
				pool.remove(sel);

				if (pos == 81)
					return true;
				else {
					check = generate(pos + 1);

					if (!check) {
						rows[row].add((Integer) sel);
						cols[col].add((Integer) sel);
						boxes[box].add((Integer) sel);
					}
				}
			}
		}
		return true;
	}

	public int[][] CreateEmplyCells(int empty) {
		int[][] actual = getActual();
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
					// possible[c][r] =null;
					emptyCells[empty - 2 - i] = Integer.toString(8 - c)
							+ Integer.toString(8 - r);
					actual[8 - c][8 - r] = 0;
					// possible[8-c][8-r] = null;

				}
			} while (duplicate);
		}
		return actual;
	}

	public int RandomNumber(int from, int to) {
		Random rd = new Random();
		return from + rd.nextInt(to - from + 1);
	}
}
