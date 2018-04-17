import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Collections;
import java.util.Vector;
import java.util.concurrent.TimeUnit;

public class gomoku {
	/*
	 * The following variables include the weights, as well as utilities for the
	 * character and board representations.
	 */
	public double w0 = 1;
	public double w1;
	public double w2;
	public double w3;
	public double w4;
	public double w5;
	public double w6;
	public double w7;
	public double w8;
	public double w9;
	public double w10;
	public double w11;
	public double w12;
	public double w13;
	public double w14;
	public double w15;
	public double w16;
	public double w17;
	public double w18;
	public double w19;
	public double w20;
	public double w21;
	public double w22;
	public double w23;
	public double w24;
	public double w25;
	public double w26;
	public double w27;
	public double w28;
	public double w29;
	public double w30;
	public double w31;
	public double w32;
	public double w33;
	public double w34;
	public double w35;
	public double w36;
	public double w37;
	public double w38;
	public double w39;
	public double w40;
	public int size = 0;
	public int EMPTY = 0;
	public int MYCHAR;
	public int ENEMYCHAR;
	Vector<move> test;
	String naiveBest = "";
	static final int PORT = 17033;
	Socket sock = null;
	BufferedReader in;
	PrintWriter out;
	Vector<Double> vhat = new Vector<Double>(); // this vector stores the
	int[][] currentBoard; // 'strength' of every move
	// made.
	Vector<Double> target = new Vector<Double>();
	Vector<move> possibleMoves; // this vector contains the possible moves for a
	int depth;
	//public String host = "10.21.73.252";
	public String host = "localhost";

	/**
	 * This creates an instance of a gomoku game, and begins communications with
	 * the socket.
	 * 
	 * @param boardsize
	 *            is the size of the board space (assuming a square board)
	 */
	gomoku() {
		try {
			sock = new Socket(host, PORT);
			in = new BufferedReader(
					new InputStreamReader(sock.getInputStream()));
			out = new PrintWriter(sock.getOutputStream(), true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * This method retrieves the stored weights from the text file where they
	 * are stored.
	 */
	public void retriveWeights() throws NumberFormatException, IOException {
		@SuppressWarnings("resource")
		BufferedReader weighttext = new BufferedReader(new FileReader(
				"weights.txt"));
		w1 = Double.valueOf(weighttext.readLine());
		w2 = Double.valueOf(weighttext.readLine());
		w3 = Double.valueOf(weighttext.readLine());
		w4 = Double.valueOf(weighttext.readLine());
		w5 = Double.valueOf(weighttext.readLine());
		w6 = Double.valueOf(weighttext.readLine());
		w7 = Double.valueOf(weighttext.readLine());
		w8 = Double.valueOf(weighttext.readLine());
		w9 = Double.valueOf(weighttext.readLine());
		w10 = Double.valueOf(weighttext.readLine());
		w11 = Double.valueOf(weighttext.readLine());
		w12 = Double.valueOf(weighttext.readLine());
		w13 = Double.valueOf(weighttext.readLine());
		w14 = Double.valueOf(weighttext.readLine());
		w15 = Double.valueOf(weighttext.readLine());
		w16 = Double.valueOf(weighttext.readLine());
		w17 = Double.valueOf(weighttext.readLine());
		w18 = Double.valueOf(weighttext.readLine());
		w19 = Double.valueOf(weighttext.readLine());
		w20 = Double.valueOf(weighttext.readLine());
		w21 = Double.valueOf(weighttext.readLine());
		w22 = Double.valueOf(weighttext.readLine());
		w23 = Double.valueOf(weighttext.readLine());
		w24 = Double.valueOf(weighttext.readLine());
		w25 = Double.valueOf(weighttext.readLine());
		w26 = Double.valueOf(weighttext.readLine());
		w27 = Double.valueOf(weighttext.readLine());
		w28 = Double.valueOf(weighttext.readLine());
		w29 = Double.valueOf(weighttext.readLine());
		w30 = Double.valueOf(weighttext.readLine());
		w31 = Double.valueOf(weighttext.readLine());
		w32 = Double.valueOf(weighttext.readLine());
		w33 = Double.valueOf(weighttext.readLine());
		w34 = Double.valueOf(weighttext.readLine());
		w35 = Double.valueOf(weighttext.readLine());
		w36 = Double.valueOf(weighttext.readLine());
		w37 = Double.valueOf(weighttext.readLine());
		w38 = Double.valueOf(weighttext.readLine());
		w39 = Double.valueOf(weighttext.readLine());
		w40 = Double.valueOf(weighttext.readLine());

	}

	/**
	 * This method checks if a position is valid move.
	 * 
	 * @param row
	 *            - the row index
	 * @param column
	 *            - the column index
	 * @return returns true if a position is valid
	 */
	public boolean valid(int row, int column, int[][] board) {
		if (board[row][column] == 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * This method checks which board positions are valid moves.
	 */
	public void legalMoves(int[][] board) {
		possibleMoves = new Vector<move>(); // creates a new vector for every
											// board state.

		for (int cc = 0; cc < size; cc++) { // iterates the column of the board.
			for (int i = 0; i < size; i++) { // iterates the row of the board.
				if (board[cc][i] == 0) { // checks to see if a position is
											// populated by a 0 (equivalent to
											// empty).
					possibleMoves.add(new move(cc, i)); // if a move is valid,
														// it is addedd to the
														// vector of possible
														// moves.
				}
			}
		}
	}

	/**
	 * 
	 * @param row
	 *            - the row index
	 * @param column
	 *            - the column index
	 * @param Char
	 *            - the character sent by the server
	 */
	public void update(int row, int column, char Char, int[][] board) {
		int num = 0;
		if (Char == 'x') {
			num = 1; // x's are internally represented by 1's
		}
		if (Char == 'o') {
			num = 2; // o's are internally represented by 2's
		}
		if (Char == ' ') {
			num = 0; // spaces are internally represented by 0's
		}
		board[row][column] = num; // update the given position with the numeric
									// representation.

	}

	/**
	 * This method evaluates the number of enemy pieces above a valid move
	 * 
	 * @param possible
	 *            - the possible move which is being evaluated.
	 */
	public void evaluateUpEnemy(move possible, int[][] board) {
		int row = possible.row - 1;
		while (possible.row > 0) {
			if (board[row][possible.column] == ENEMYCHAR && row > 0) {
				possible.inRowUpEnemy++;
				row += -1;
			} else {
				break;
			}
		}
	}

	/**
	 * This method evaluates the number of enemy pieces below a valid move
	 * 
	 * @param possible
	 *            - the possible move which is being evaluated.
	 */
	public void evaluateDownEnemy(move possible, int[][] board) {
		int row = possible.row + 1;
		while (row < board.length) {
			if (board[row][possible.column] == ENEMYCHAR && row < board.length) {
				possible.inRowDownEnemy++;
				row += 1;
			} else {
				break;
			}
		}
	}

	/**
	 * This method evaluates the number of enemy pieces to the left of a valid
	 * move
	 * 
	 * @param possible
	 *            - the possible move which is being evaluated.
	 */
	public void evaluateLeftEnemy(move possible, int[][] board) {
		int column = possible.column - 1;
		while (possible.column > 0 && possible.row < board.length) {
			if (board[possible.row][column] == ENEMYCHAR && column > 0) {
				possible.inRowLeftEnemy++;
				column += -1;
			} else {
				break;
			}
		}
	}

	/**
	 * This method evaluates the number of enemy pieces to the right of a valid
	 * move
	 * 
	 * @param possible
	 *            - the possible move which is being evaluated.
	 */
	public void evaluateRightEnemy(move possible, int[][] board) {
		int column = possible.column + 1;
		while (column < board.length && possible.row < board.length) {
			if (board[possible.row][column] == ENEMYCHAR
					&& column < board.length) {
				possible.inRowRightEnemy++;
				column++;
			} else {
				break;
			}
		}
	}

	/**
	 * This method evaluates the number of enemy pieces diagonally up and to the
	 * left of a valid move
	 * 
	 * @param possible
	 *            - the possible move which is being evaluated.
	 */
	public void evaluateLeftUpEnemy(move possible, int[][] board) {
		int column = possible.column - 1;
		int row = possible.row - 1;
		while (column > 0 && row > 0) {
			if (board[row][column] == ENEMYCHAR && column < board.length
					&& row < board.length) {
				possible.inRowLeftUpEnemy++;
				column--;
				row--;
			} else {
				break;
			}
		}
	}

	/**
	 * This method evaluates the number of enemy pieces diagonally down and to
	 * the left of a valid move
	 * 
	 * @param possible
	 *            - the possible move which is being evaluated.
	 */
	public void evaluateLeftDownEnemy(move possible, int[][] board) {
		int column = possible.column - 1;
		int row = possible.row + 1;
		while (column > 0 && row < board.length) {
			if (board[row][column] == ENEMYCHAR && column > 0 && row > 0) {
				possible.inRowLeftDownEnemy++;
				column--;
				row++;
			} else {
				break;
			}
		}
	}

	/**
	 * This method evaluates the number of enemy pieces diagonally up and to the
	 * right of a valid move
	 * 
	 * @param possible
	 *            - the possible move which is being evaluated.
	 */
	public void evaluateRightUpEnemy(move possible, int[][] board) {
		int column = possible.column + 1;
		int row = possible.row - 1;
		while (column < board.length && row > 0) {
			if (board[row][column] == ENEMYCHAR && column < board.length
					&& row < board.length) {
				possible.inRowRightUpEnemy++;
				column++;
				row--;
			} else {
				break;
			}
		}
	}

	/**
	 * This method evaluates the number of enemy pieces diagonally down and to
	 * the right of a valid move
	 * 
	 * @param possible
	 *            - the possible move which is being evaluated.
	 */
	public void evaluateRightDownEnemy(move possible, int[][] board) {
		int column = possible.column + 1;
		int row = possible.row + 1;
		while (column < board.length && row < board.length) {
			if (board[row][column] == ENEMYCHAR && column < board.length
					&& row < board.length) {
				possible.inRowRightDownEnemy++;
				column++;
				row++;
			} else {
				break;
			}
		}
	}

	/**
	 * This method checks if a move is adjacent to two or more enemy pieces that
	 * are in a row.
	 * 
	 * @param m
	 *            - this is the possible move which is being evaluated.
	 */
	public void make3OrMoreEnemy(move m) {
		if (m.inRowLeftEnemy + m.inRowRightEnemy >= 2) {
			m.ThreeHorzEnemy++;
		}
		if (m.inRowDownEnemy + m.inRowUpEnemy >= 2) {
			m.ThreeVertEnemy++;
		}
		if (m.inRowLeftUpEnemy + m.inRowRightDownEnemy >= 2) {
			m.ThreeDiagMinusEnemy++;
		}
		if (m.inRowLeftDownEnemy + m.inRowRightUpEnemy >= 2) {
			m.ThreeDiagPlusEnemy++;
		}
	}

	/**
	 * This method checks if a move is adjacent to three or more enemy pieces
	 * that are in a row.
	 * 
	 * @param m
	 *            - this is the possible move which is being evaluated.
	 */
	public void make4OrMoreEnemy(move m) {
		if (m.inRowLeftEnemy + m.inRowRightEnemy >= 3) {
			m.FourHorzEnemy++;
		}
		if (m.inRowDownEnemy + m.inRowUpEnemy >= 3) {
			m.FourVertEnemy++;
		}
		if (m.inRowLeftUpEnemy + m.inRowRightDownEnemy >= 3) {
			m.FourDiagMinusEnemy++;
		}
		if (m.inRowLeftDownEnemy + m.inRowRightUpEnemy >= 3) {
			m.FourDiagPlusEnemy++;
		}
	}

	/**
	 * This method checks if a move is adjacent to four or more enemy pieces
	 * that are in a row, and would block a win.
	 * 
	 * @param m
	 *            - this is the possible move which is being evaluated.
	 */
	public void winEnemy(move m) {
		if (m.inRowLeftEnemy + m.inRowRightEnemy >= 3) {
			m.winHorzEnemy++;
		}
		if (m.inRowDownEnemy + m.inRowUpEnemy >= 3) {
			m.winVertEnemy++;
		}
		if (m.inRowLeftUpEnemy + m.inRowRightDownEnemy >= 3) {
			m.winDiagMinusEnemy++;
		}
		if (m.inRowLeftDownEnemy + m.inRowRightUpEnemy >= 3) {
			m.winDiagPlusEnemy++;
		}
	}

	/**
	 * This method evaluates the number of my pieces above a valid move
	 * 
	 * @param possible
	 *            - the possible move which is being evaluated.
	 */
	public void evaluateUp(move possible, int[][] board) {
		int row = possible.row - 1;
		while (possible.row > 0) {
			if (board[row][possible.column] == MYCHAR && row > 0) {
				possible.inRowUp++;
				row += -1;
			} else {
				break;
			}
		}
	}

	/**
	 * This method evaluates the number of my pieces below a valid move
	 * 
	 * @param possible
	 *            - the possible move which is being evaluated.
	 */
	public void evaluateDown(move possible, int[][] board) {
		int row = possible.row + 1;
		while (row < board.length) {
			if (board[row][possible.column] == MYCHAR && row < board.length) {
				possible.inRowDown++;
				row += 1;
			} else {
				break;
			}
		}
	}

	/**
	 * This method evaluates the number of my pieces to the left of a valid move
	 * 
	 * @param possible
	 *            - the possible move which is being evaluated.
	 */
	public void evaluateLeft(move possible, int[][] board) {
		int column = possible.column - 1;
		while (possible.column > 0 && possible.row < board.length) {
			if (board[possible.row][column] == MYCHAR && column > 0) {
				possible.inRowLeft++;
				column += -1;
			} else {
				break;
			}
		}
	}

	/**
	 * This method evaluates the number of my pieces to the right of a valid
	 * move
	 * 
	 * @param possible
	 *            - the possible move which is being evaluated.
	 */
	public void evaluateRight(move possible, int[][] board) {
		int column = possible.column + 1;
		while (column < board.length && possible.row < board.length) {
			if (board[possible.row][column] == MYCHAR && column < board.length) {
				possible.inRowRight++;
				column++;
			} else {
				break;
			}
		}
	}

	/**
	 * This method evaluates the number of my pieces diagonally up and to the
	 * left of a valid move
	 * 
	 * @param possible
	 *            - the possible move which is being evaluated.
	 */
	public void evaluateLeftUp(move possible, int[][] board) {
		int column = possible.column - 1;
		int row = possible.row - 1;
		while (column > 0 && row > 0) {
			if (board[row][column] == MYCHAR && column > 0 && row > 0) {
				possible.inRowLeftUp++;
				column--;
				row--;
			} else {
				break;
			}
		}
	}

	/**
	 * This method evaluates the number of my pieces diagonally down and to the
	 * left of a valid move
	 * 
	 * @param possible
	 *            - the possible move which is being evaluated.
	 */
	public void evaluateLeftDown(move possible, int[][] board) {
		int column = possible.column - 1;
		int row = possible.row + 1;
		while (column > 0 && row < board.length) {
			if (board[row][column] == MYCHAR && column > 0
					&& row < board.length) {
				possible.inRowLeftDown++;
				column--;
				row++;
			} else {
				break;
			}
		}
	}

	/**
	 * This method evaluates the number of my pieces diagonally up and to the
	 * right of a valid move
	 * 
	 * @param possible
	 *            - the possible move which is being evaluated.
	 */

	public void evaluateRightUp(move possible, int[][] board) {
		int column = possible.column + 1;
		int row = possible.row - 1;
		while (column < board.length && row > 0) {
			if (board[row][column] == MYCHAR && column < board.length
					&& row < board.length) {
				possible.inRowRightUp++;
				column++;
				row--;
			} else {
				break;
			}
		}
	}

	/**
	 * This method evaluates the number of my pieces diagonally down and to the
	 * right of a valid move
	 * 
	 * @param possible
	 *            - the possible move which is being evaluated.
	 */
	public void evaluateRightDown(move possible, int[][] board) {
		int column = possible.column + 1;
		int row = possible.row + 1;
		while (column < board.length && row < board.length) {
			if (board[row][column] == MYCHAR && column < board.length
					&& row < board.length) {
				possible.inRowRightDown++;
				column++;
				row++;
			} else {
				break;
			}
		}
	}

	/**
	 * This method checks if a move is adjacent to two or more of my pieces that
	 * are in a row.
	 * 
	 * @param m
	 *            - this is the possible move which is being evaluated.
	 */
	public void make3OrMore(move m) {
		if (m.inRowLeft + m.inRowRight >= 2) {
			++m.ThreeHorz;
		}
		if (m.inRowDown + m.inRowUp >= 2) {
			++m.ThreeVert;
		}
		if (m.inRowLeftUp + m.inRowRightDown >= 2) {
			++m.ThreeDiagMinus;
		}
		if (m.inRowLeftDown + m.inRowRightUp >= 2) {
			++m.ThreeDiagPlus;
		}
	}

	/**
	 * This method checks if a move is adjacent to three or more of pieces that
	 * are in a row.
	 * 
	 * @param m
	 *            - this is the possible move which is being evaluated.
	 */
	public void make4OrMore(move m) {
		if (m.inRowLeft + m.inRowRight >= 3) {
			++m.FourHorz;
		}
		if (m.inRowDown + m.inRowUp >= 3) {
			++m.FourVert;
		}
		if (m.inRowLeftUp + m.inRowRightDown >= 3) {
			++m.FourDiagMinus;
		}
		if (m.inRowLeftDown + m.inRowRightUp >= 3) {
			++m.FourDiagPlus;
		}
	}

	/**
	 * This method checks if a move creates a win.
	 * 
	 * @param m
	 *            - this is the possible move which is being evaluated.
	 */
	public void win(move m) {
		if (m.inRowLeft + m.inRowRight >= 4) {
			++m.winHorz;
		}
		if (m.inRowDown + m.inRowUp >= 4) {
			++m.winVert;
		}
		if (m.inRowLeftUp + m.inRowRightDown >= 4) {
			++m.winDiagMinus;
		}
		if (m.inRowLeftDown + m.inRowRightUp >= 4) {
			++m.winDiagPlus;
		}
	}

	/**
	 * This method evaluates the features (using the methods described above) of
	 * a board, and determines the best possible move.
	 * 
	 * @return returns a string containing the row and column of the best
	 *         possible move.
	 * @throws IOException
	 * @throws InterruptedException
	 */
	@SuppressWarnings("unchecked")
	public void evaluateMoves(int[][] board) throws IOException,
			InterruptedException {

		legalMoves(board); // creates a vector of all legal moves.
		for (int i = 0; i < possibleMoves.size(); i++) { // Evaluates the
															// features of the
															// board.
			evaluateLeft(possibleMoves.elementAt(i), board);
			evaluateRight(possibleMoves.elementAt(i), board);
			evaluateUp(possibleMoves.elementAt(i), board);
			evaluateDown(possibleMoves.elementAt(i), board);
			evaluateRightUp(possibleMoves.elementAt(i), board);
			evaluateRightDown(possibleMoves.elementAt(i), board);
			evaluateLeftUp(possibleMoves.elementAt(i), board);
			evaluateLeftDown(possibleMoves.elementAt(i), board);
			evaluateLeftEnemy(possibleMoves.elementAt(i), board);
			evaluateRightEnemy(possibleMoves.elementAt(i), board);
			evaluateUpEnemy(possibleMoves.elementAt(i), board);
			evaluateDownEnemy(possibleMoves.elementAt(i), board);
			evaluateRightUpEnemy(possibleMoves.elementAt(i), board);
			evaluateRightDownEnemy(possibleMoves.elementAt(i), board);
			evaluateLeftUpEnemy(possibleMoves.elementAt(i), board);
			evaluateLeftDownEnemy(possibleMoves.elementAt(i), board);
			make3OrMore(possibleMoves.elementAt(i));
			make4OrMore(possibleMoves.elementAt(i));
			win(possibleMoves.elementAt(i));
			make3OrMoreEnemy(possibleMoves.elementAt(i));
			make4OrMoreEnemy(possibleMoves.elementAt(i));
			winEnemy(possibleMoves.elementAt(i));
			possibleMoves.elementAt(i).strength = w0 // this is used to
														// calculate the
														// strength of possible
														// move.
					+ w1 * possibleMoves.elementAt(i).inRowDown
					+ w2 * possibleMoves.elementAt(i).inRowDownEnemy
					+ w3 * possibleMoves.elementAt(i).inRowLeft
					+ w4 * possibleMoves.elementAt(i).inRowLeftDown
					+ w5 * possibleMoves.elementAt(i).inRowLeftDownEnemy
					+ w6 * possibleMoves.elementAt(i).inRowLeftEnemy
					+ w7 * possibleMoves.elementAt(i).inRowRightUpEnemy
					+ w8 * possibleMoves.elementAt(i).inRowLeftUp
					+ w9 * possibleMoves.elementAt(i).inRowLeftUpEnemy
					+ w10 * possibleMoves.elementAt(i).inRowRight
					+ w11 * possibleMoves.elementAt(i).inRowRightEnemy
					+ w12 * possibleMoves.elementAt(i).inRowRightUp
					+ w13 * possibleMoves.elementAt(i).ThreeHorz
					+ w14 * possibleMoves.elementAt(i).ThreeVert
					+ w15 * possibleMoves.elementAt(i).ThreeDiagPlus
					+ w16 * possibleMoves.elementAt(i).ThreeDiagMinus
					+ w17 * possibleMoves.elementAt(i).FourHorz
					+ w18 * possibleMoves.elementAt(i).FourVert
					+ w19 * possibleMoves.elementAt(i).FourDiagPlus
					+ w20 * possibleMoves.elementAt(i).FourDiagMinus
					+ w21 * possibleMoves.elementAt(i).winHorz
					+ w22
					* possibleMoves.elementAt(i).winVert
					+ w23
					* possibleMoves.elementAt(i).winDiagPlus
					+ w24
					* possibleMoves.elementAt(i).winDiagMinus
					+ w25
					* possibleMoves.elementAt(i).ThreeHorzEnemy
					+ w26
					* possibleMoves.elementAt(i).ThreeVertEnemy
					+ w27
					* possibleMoves.elementAt(i).ThreeDiagPlusEnemy
					+ w28
					* possibleMoves.elementAt(i).ThreeDiagMinusEnemy
					+ w29
					* possibleMoves.elementAt(i).FourHorzEnemy
					+ w30
					* possibleMoves.elementAt(i).FourVertEnemy
					+ w31
					* possibleMoves.elementAt(i).FourDiagPlusEnemy
					+ w32
					* possibleMoves.elementAt(i).FourDiagMinusEnemy
					+ w33
					* possibleMoves.elementAt(i).winHorzEnemy
					+ w34
					* possibleMoves.elementAt(i).winVertEnemy
					+ w35
					* possibleMoves.elementAt(i).winDiagPlusEnemy

					+ w36
					* possibleMoves.elementAt(i).inRowRightDownEnemy

					+ w37
					* possibleMoves.elementAt(i).inRowRightUpEnemy
					+ w38
					* possibleMoves.elementAt(i).inRowUp
					+ w39
					* possibleMoves.elementAt(i).inRowUpEnemy
					+ w40
					* possibleMoves.elementAt(i).winDiagMinusEnemy;

		}

		Collections.sort(possibleMoves); // sorts the vector of possible moves
											// in descending order of strength.
											// naiveBest =
											// possibleMoves.elementAt(0).row +
											// " " // this selects the move
		// // with the greatest
		// // strength and
		// // retrieves its row and
		// // column.
		// + possibleMoves.elementAt(0).column + " "; // it is then
		// // formatted so it
		// // can be understood
		// // by the server.
		// vhat.add(possibleMoves.elementAt(0).strength); // this records the
		// strength of the move
		// made by adding it to
		// the vector v hat.
		// This is needed to
		// adjust the weights.
		// return a; // returns the row and column of the best possible move.

	}

	/**
	 * This method is used to updates the weights for each feature using a
	 * Gradient Decent based Artificial Neural Network method.
	 */
	public void updateWeights() {
		double vtrain;
		double sqrsum = 0;
		possibleMoves = (Vector<move>) test.clone();

		for (int i = 0; i < (vhat.size() - 2); i++) { // selects the strength of
														// each move made
														// throughout an entire
														// game.
			vtrain = .0001 * (vhat.elementAt(i + 1) - vhat.elementAt(i)); // subtracts
																			// the
																			// strength
																			// of
																			// a
																			// move
																			// by
																			// that
																			// of
																			// its
																			// previous
																			// move,
																			// and
																			// multiplies
																			// by
																			// a
																			// coefficient.

			w1 += vtrain * possibleMoves.elementAt(i).inRowDown; // a weight is
																	// then
																	// updated
																	// by taking
																	// its
																	// original
																	// value and
																	// adding it
																	// to the
																	// product
																	// of the
																	// vtrain
																	// value(explained
																	// above)
																	// and the
																	// number of
																	// instances
																	// of that
																	// feature.
			w2 += vtrain * possibleMoves.elementAt(i).inRowDownEnemy;
			w3 += vtrain * possibleMoves.elementAt(i).inRowLeft;
			w4 += vtrain * possibleMoves.elementAt(i).inRowLeftDown;
			w5 += vtrain * possibleMoves.elementAt(i).inRowLeftDownEnemy;
			w6 += vtrain * possibleMoves.elementAt(i).inRowLeftEnemy;
			w7 += vtrain * possibleMoves.elementAt(i).inRowRightUpEnemy;
			w8 += vtrain * possibleMoves.elementAt(i).inRowLeftUp;
			w9 += vtrain * possibleMoves.elementAt(i).inRowLeftUpEnemy;
			w10 += vtrain * possibleMoves.elementAt(i).inRowRight;
			w11 += vtrain * possibleMoves.elementAt(i).inRowRightEnemy;
			w12 += vtrain * possibleMoves.elementAt(i).inRowRightUp;
			w13 += vtrain * possibleMoves.elementAt(i).ThreeHorz;
			w14 += vtrain * possibleMoves.elementAt(i).ThreeVert;
			w15 += vtrain * possibleMoves.elementAt(i).ThreeDiagPlus;
			w16 += vtrain * possibleMoves.elementAt(i).ThreeDiagMinus;
			w17 += vtrain * possibleMoves.elementAt(i).FourHorz;
			w18 += vtrain * possibleMoves.elementAt(i).FourVert;
			w19 += vtrain * possibleMoves.elementAt(i).FourDiagPlus;
			w20 += vtrain * possibleMoves.elementAt(i).FourDiagMinus;
			w21 += vtrain * possibleMoves.elementAt(i).winHorz;
			w22 += vtrain * possibleMoves.elementAt(i).winVert;
			w23 += vtrain * possibleMoves.elementAt(i).winDiagPlus;
			w24 += vtrain * possibleMoves.elementAt(i).winDiagMinus;
			w25 += vtrain * possibleMoves.elementAt(i).ThreeHorzEnemy;
			w26 += vtrain * possibleMoves.elementAt(i).ThreeVertEnemy;
			w27 += vtrain * possibleMoves.elementAt(i).ThreeDiagPlusEnemy;
			w28 += vtrain * possibleMoves.elementAt(i).ThreeDiagMinusEnemy;
			w29 += vtrain * possibleMoves.elementAt(i).FourHorzEnemy;
			w30 += vtrain * possibleMoves.elementAt(i).FourVertEnemy;
			w31 += vtrain * possibleMoves.elementAt(i).FourDiagPlusEnemy;
			w32 += vtrain * possibleMoves.elementAt(i).FourDiagMinusEnemy;
			w33 += vtrain * possibleMoves.elementAt(i).winHorzEnemy;
			w34 += vtrain * possibleMoves.elementAt(i).winVertEnemy;
			w35 += vtrain * possibleMoves.elementAt(i).winDiagPlusEnemy;
			w36 += vtrain * possibleMoves.elementAt(i).inRowRightDownEnemy;
			w37 += vtrain * possibleMoves.elementAt(i).inRowRightUpEnemy;
			w38 += vtrain * possibleMoves.elementAt(i).inRowUp;
			w39 += vtrain * possibleMoves.elementAt(i).inRowUpEnemy;
			w40 += vtrain * possibleMoves.elementAt(i).winDiagMinusEnemy;
		}
	}

	/**
	 * This method selects a random, but legal, move.
	 * 
	 * @return returns the row and column of a valid random move.
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public String random(int board[][]) throws IOException,
			InterruptedException {
		legalMoves(board);// compiles a vector of valid moves.
		int x = (int) (Math.random() * possibleMoves.size()); // selects a
																// random one.
		String a = possibleMoves.elementAt(x).row + " " // retrieves row and
														// column
				+ possibleMoves.elementAt(x).column + " ";
		return a; // returns the row and column of a random move in the correct
					// format for the server.
	}

	/**
	 * This method evaluates the state of the board, and updates the internal
	 * representation.
	 * 
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public void readState() throws IOException, InterruptedException {
		@SuppressWarnings("resource")
		String inputLine; // a line message from the server
		int line = 0; // the current line number being read
		while ((inputLine = in.readLine()) != null) { // while their is a
														// message from the
														// server

			if (line == 0) { // if it is the first line, evaluate
								// win/lose/draw/forfeit
				if (inputLine.charAt(0) == 'f') { // this is a forfeit
					System.out.println(inputLine); // print the reason for
													// forfeit
					exit(); // end communications
				}
				if (inputLine.charAt(0) == 'd') { // this is a draw
					System.out.println(inputLine); // print draw
					exit(); // end communications

				}
				if (inputLine.charAt(0) == 'w') { // this is a win
					System.out.println(inputLine);// print win
					vhat.setElementAt(700.0, vhat.size() - 1); // select the
																// last move
																// made and set
																// its strength
																// to 100
					updateWeights(); // update the weights (this is how the
										// program learns)
					PrintWriter writer = new PrintWriter("weights.txt", "UTF-8"); // then
																					// overwrite
																					// the
																					// old
																					// weights
																					// with
																					// the
																					// new
																					// ones.
					writer.println(w1);
					writer.println(w2);
					writer.println(w3);
					writer.println(w4);
					writer.println(w5);
					writer.println(w6);
					writer.println(w7);
					writer.println(w8);
					writer.println(w9);
					writer.println(w10);
					writer.println(w11);
					writer.println(w12);
					writer.println(w13);
					writer.println(w14);
					writer.println(w15);
					writer.println(w16);
					writer.println(w17);
					writer.println(w18);
					writer.println(w19);
					writer.println(w20);
					writer.println(w21);
					writer.println(w22);
					writer.println(w23);
					writer.println(w24);
					writer.println(w25);
					writer.println(w26);
					writer.println(w27);
					writer.println(w28);
					writer.println(w29);
					writer.println(w30);
					writer.println(w31);
					writer.println(w32);
					writer.println(w33);
					writer.println(w34);
					writer.println(w35);
					writer.println(w36);
					writer.println(w37);
					writer.println(w38);
					writer.println(w39);
					writer.println(w40);
					writer.close();
					exit(); // end communications with the server
				}
				if (inputLine.charAt(0) == 'l') { // this is a lose
					System.out.println(inputLine); // print lose
					vhat.setElementAt(-700.0, vhat.size() - 1); // set the
																// strength of
																// the final
																// move made to
																// be -100;
					updateWeights(); // update the weights
					PrintWriter writer = new PrintWriter("weights.txt", "UTF-8"); // overwrite
																					// the
																					// preexisting
																					// weights
																					// with
																					// the
																					// new
																					// ones.
					writer.println(w1);
					writer.println(w2);
					writer.println(w3);
					writer.println(w4);
					writer.println(w5);
					writer.println(w6);
					writer.println(w7);
					writer.println(w8);
					writer.println(w9);
					writer.println(w10);
					writer.println(w11);
					writer.println(w12);
					writer.println(w13);
					writer.println(w14);
					writer.println(w15);
					writer.println(w16);
					writer.println(w17);
					writer.println(w18);
					writer.println(w19);
					writer.println(w20);
					writer.println(w21);
					writer.println(w22);
					writer.println(w23);
					writer.println(w24);
					writer.println(w25);
					writer.println(w26);
					writer.println(w27);
					writer.println(w28);
					writer.println(w29);
					writer.println(w30);
					writer.println(w31);
					writer.println(w32);
					writer.println(w33);
					writer.println(w34);
					writer.println(w35);
					writer.println(w36);
					writer.println(w37);
					writer.println(w38);
					writer.println(w39);
					writer.println(w40);
					writer.close();
					exit();// end communications with the server
				}

			}
			if (line == 1 && size == 0) {
				size = inputLine.length();
				depth = 21-size;
				currentBoard = new int[size][size];
			}
			if (line > 0 && line < size + 1) { // if the line is part of the
												// game state sent by the server
				for (int i = 0; i < size; i++) { // process each character of a
													// line
					update((line - 1), i, inputLine.charAt(i), currentBoard); // update
																				// each
																				// position
				}

			}
			if (line == size + 1) { // this signifies which character is mine
				if (inputLine.charAt(0) == 'x') {
					MYCHAR = 1;
					ENEMYCHAR = 2;
				}
				if (inputLine.charAt(0) == 'o') {
					MYCHAR = 2;
					ENEMYCHAR = 1;
				}
				break;
			}
			line++;
		}

	}

	/**
	 * This method handles the processes for playing an entire game.
	 * 
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public void play() throws IOException, InterruptedException {

		int move = 1;
		move bestMove = null;
		while (true) {
			boolean block = false;
			// long startTime = System.nanoTime();
			// ... the code being measured ...

			if (move == 1) {
				readState(); // reads the state of the board given by the
								// server.
				out.println(random(currentBoard)); // on the first move, play
													// randomly.
				move++;
				vhat.addElement(5.0);

			} else {
				readState(); // reads the state of the board given by the
								// server.
				evaluateMoves(currentBoard);
				test = (Vector<move>) possibleMoves.clone();
				double bestValue = -10000;
				// for(int i = 0; i <= 5; i++ ){
				// move m = possibleMoves.elementAt(i);
				for (move m : test) {
					if(m.winDiagMinus > 0 || m.winDiagPlus > 0  || m.winHorz > 0|| m.winVert > 0 || m.winDiagMinusEnemy > 0 || m.winDiagPlusEnemy > 0  || m.winHorzEnemy > 0|| m.winVertEnemy > 0){
						bestMove = m;
						block = true;
						System.out.println("BLOCK");
					}
				}
					if(block == false){
						for (move m : test) {
					int[][] temp = currentBoard.clone();
					for (int i = 0; i < currentBoard.length; i++) {
						temp[i] = currentBoard[i].clone();
					}

					temp[m.row][m.column] = MYCHAR;
					double score = alphaBeta(temp, Double.MIN_VALUE,
							Double.MAX_VALUE, true, depth);

					if (score > bestValue) {
						bestValue = score;
						// System.out.println("so far best is : " + bestValue);
						bestMove = m;
					}
				}
				}

				out.println(bestMove.row + " " + bestMove.column);
				vhat.add(bestMove.strength);
			}
		}

		//
		// out.println(naiveBest); // on all other moves play intentional moves.
	}

	// long estimatedTime = System.nanoTime() - startTime;
	// estimatedTime = TimeUnit.MILLISECONDS.convert(estimatedTime,
	// TimeUnit.NANOSECONDS);
	// System.out.println(estimatedTime);
	// }
	// }

	public double alphaBeta(int[][] board, double a, double b, boolean player,
			int depth) throws IOException, InterruptedException {
		double score;
		evaluateMoves(board);
		int[][] temp = new int[size][size];
		if (possibleMoves.size() == 1 || depth == 0) { // TODO add threshold
			score = possibleMoves.elementAt(0).strength;
			return score;
		}
		// for(int i = 0; i <= 5; i++ ){
		// move m = possibleMoves.elementAt(i);
		for (move m : possibleMoves) {
			temp = board.clone();
			for (int i = 0; i < board.length; i++) {
				temp[i] = board[i].clone();
			}

			else
				temp[m.row][m.column] = ENEMYCHAR;
			score = alphaBeta(temp, a, b, false, depth - 1);
			if (player == true) { // MAX
				if (score > a)
					a = score;
				if (a >= b)
					return a;
				return a;
			} else { // MIN
				if (score < b)
					b = score;
				if (b <= a)
					return b;
				return b;
			}
		}
		if (player == true)
			return a;
		return b;

	}

	/**
	 * This method closes all communications with the server.
	 * 
	 * @throws IOException
	 */

	public void exit() throws IOException {
		out.close();
		in.close();
		sock.close();
		System.exit(0);
	}

	/**
	 * This main method initializes the program.
	 * 
	 * @param args
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws IOException,
			InterruptedException {
		gomoku x = new gomoku(); // create a new instance of the board
		x.retriveWeights(); // initialize weights
		x.play(); // play the game

	}
}