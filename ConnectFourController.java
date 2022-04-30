/*******************************************************************
Connect Four game
Lior Mary 
*********************************************************************/

import java.util.LinkedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class ConnectFourController {

	@FXML
	private GridPane gameBoard;
	
	private int count1, count2, count3, count4, count5, count6, count7;
	private boolean turn; // blue= false, red = true
	final double RADIUS = 32; // radius of every circle
	private LinkedList<Circle> circles = new LinkedList<Circle>(); //save the circles that in the grid pane
	final int ROWS = 5; //number of rows
	final int COLUMNS = 6; //number of columns
	private char[][] matrix = new char[6][7]; //save if there is blue or red in the grid pane

	//reset all the values before the game starts
	public void initialize() {
		resetGame();
	} 

	//when the user press on button number one
	@FXML
	void buttonOnePressed(ActionEvent event) {
		count1 = buttonPressed(count1, 0);
	}

	//when the user press on button number two
	@FXML
	void buttonTwoPressed(ActionEvent event) {
		count2 = buttonPressed(count2, 1);
	}

	//when the user press on button number three
	@FXML
	void buttonThreePressed(ActionEvent event) {
		count3 = buttonPressed(count3, 2);
	}

	//when the user press on button number four
	@FXML
	void buttonFourPressed(ActionEvent event) {
		count4 = buttonPressed(count4, 3);
	}

	//when the user press on button number five
	@FXML
	void buttonFivePressed(ActionEvent event) {
		count5 = buttonPressed(count5, 4);
	}

	//when the user press on button number six
	@FXML
	void buttonSixPressed(ActionEvent event) {
		count6 = buttonPressed(count6, 5);
	}

	//when the user press on button number seven
	@FXML
	void buttonSevenPressed(ActionEvent event) {
		count7 = buttonPressed(count7, 6);
	}

	//clear the game board
	@FXML
	void clearPressed(ActionEvent event) {
		resetGame();
	}

	//what to do when button pressed
	private int buttonPressed(int count, int column) {
		if (count >= 0) { // if the column is not full
			if(turn) { // if its red player turn to play
				Circle player1 = new Circle(); //create red circle
				player1.setFill(Color.RED);
				player1.setRadius(RADIUS);
				gameBoard.add(player1, column, count); // put the red circle in the game board
				circles.add(player1); // add to the linked list of the circles
				matrix[count][column] = 'R'; // save that there is a red circle in this column and row

				//check if the red player win
				if (checkWinner(gameBoard.getRowIndex(player1), gameBoard.getColumnIndex(player1)) == 1) {
					Alert alert = new Alert(null, "Red player has won the game! ", ButtonType.OK);
					alert.showAndWait();
					resetGame();
				} 
				turn = false; //the next round is blue player turn to play
			}
			else { // if its blue player turn to play
				Circle player2 = new Circle(); //create blue circle
				player2.setFill(Color.BLUE);
				player2.setRadius(RADIUS);
				gameBoard.add(player2, column, count); // put the blue circle in the game board
				circles.add(player2); // add to the linked list of the circles
				matrix[count][column] = 'B'; // save that there is a blue circle in this column and row
				
				//check if the blue player win
				if ((checkWinner(gameBoard.getRowIndex(player2), gameBoard.getColumnIndex(player2))) == 2) {
					Alert alert = new Alert(null, "Blue player has won the game! ", ButtonType.OK);
					alert.showAndWait();
					resetGame();
				} 
				turn = true; //the next round is red player turn to play
			}
			count--; //update the number of the free places in the column
		}
		else { //if the column is full
			Alert alert = new Alert(null, "This column is full \n Pleae choose enother column ", ButtonType.OK);
			alert.showAndWait();
		}
		return count;
	}

	//check who is the winner
	public int checkWinner(int row, int column) {
		int right = 0;
		int left = 0;
		int bottom = 0;
		int top = 0;
		int rightAndTop = 0;
		int rightAndBottom = 0;
		int leftAndBottom = 0;
		int leftAndTop = 0;

		// Horizontal check of the red player
		for(int i = 1; column + i <= COLUMNS ; i++) {
			if (matrix[row][column + i] == 'R') {
				right++;
			}
			else break;
		}
		if (right >= 3) return 1;

		for(int i = 1; 0 <= column - i  ; i++) {
			if (matrix[row][column - i] == 'R') {
				left++;
			}
			else break;
		}
		if (left >= 3 || left + right >= 3) return 1;


		// Vertical check of the red player
		for(int i = 1; row + i <= ROWS ; i++) {
			if (matrix[row + i][column] == 'R') {
				bottom++;
			}
			else break;
			if (bottom == 3) return 1;
		}


		for(int i = 1; 0 <= row - i  ; i++) {
			if (matrix[row - i][column] == 'R') {
				top++;
			}
			else break;
			if (top == 3 || top + bottom >= 3) return 1;     
		}

		// Ascending diagonal check of the red player
		for (int i = 1; row + i <= ROWS && column + i <= COLUMNS ; i++) {
			if (matrix[row + i][column + i] == 'R') {
				rightAndBottom++;
			}
			else break;
			if (rightAndBottom == 3) return 1;
		}
		
		// Descending diagonal check of the red player
		for (int i = 1; 0 <= row - i && 0 <= column - i ; i++) {
			if (matrix[row - i][column - i] == 'R') {
				leftAndTop++;
			}
			else break;
			if (leftAndTop == 3 || rightAndBottom + leftAndTop >= 3 ) return 1;
		}

		// Ascending diagonal check of the red player
		for (int i = 1; column + i <= COLUMNS && 0 <= row - i ; i++) {
			if (matrix[row - i][column + i] == 'R') {
				rightAndTop++;
			}
			else break;
			if (rightAndTop == 3) return 1;

		}
		// Descending diagonal check of the red player
		for (int i = 1; row + i <= ROWS && 0 <= column - i ; i++) {
			if (matrix[row + i][column - i] == 'R') {
				leftAndBottom++;
			}
			else break;
			if (leftAndBottom == 3 || leftAndBottom + rightAndTop >= 3) return 1;

		}

		// Horizontal check of the blue player
		for(int i = 1; column + i <= COLUMNS ; i++) {
			if (matrix[row][column + i] == 'B') {
				right++;
			}
			else break;
		}
		if (right >= 3) return 2;

		for(int i = 1; 0 <= column - i  ; i++) {
			if (matrix[row][column - i] == 'B') {
				left++;
			}
			else break;
		}
		if (left >= 3 || left + right >= 3) return 2;


		// Vertical check of the blue player
		for(int i = 1; row + i <= ROWS ; i++) {
			if (matrix[row + i][column] == 'B') {
				bottom++;
			}
			else break;
			if (bottom == 3) return 2;
		}


		for(int i = 1; 0 <= row - i  ; i++) {
			if (matrix[row - i][column] == 'B') {
				top++;
			}
			else break;
			if (top == 3 || top + bottom >= 3) return 2;     
		}

		// Descending diagonal check of the blue player
		for (int i = 1; row + i <= ROWS && column + i <= COLUMNS ; i++) {
			if (matrix[row + i][column + i] == 'B') {
				rightAndBottom++;
			}
			else break;
			if (rightAndBottom == 3) return 2;
		}
		
		// Ascending diagonal check of the blue player
		for (int i = 1; 0 <= row - i && 0 <= column - i ; i++) {
			if (matrix[row - i][column - i] == 'B') {
				leftAndTop++;
			}
			else break;
			if (leftAndTop == 3 || rightAndBottom + leftAndTop >= 3 ) return 2;
		}

		// Ascending diagonal check of the blue player
		for (int i = 1; column + i <= COLUMNS && 0 <= row - i ; i++) {
			if (matrix[row - i][column + i] == 'B') {
				rightAndTop++;
			}
			else break;
			if (rightAndTop == 3) return 2;

		}
		
		// Descending diagonal check of the blue player
		for (int i = 1; row + i <= ROWS && 0 <= column - i ; i++) {
			if (matrix[row + i][column - i] == 'B') {
				leftAndBottom++;
			}
			else break;
			if (leftAndBottom == 3 || leftAndBottom + rightAndTop >= 3) return 2;

		}
		return 3;
	}

	//reset the game
	private void resetGame() {
		gameBoard.getChildren().removeAll(circles); //remove all circles from the game board
		circles.clear(); //delete all the circles in the linked list
		for (int i = 0; i < matrix.length; i++) { // reset the matrix 
			for (int j = 0; j < matrix.length; j++) {
				matrix[i][j] = '0';
			}
		}
		
		//reset the free places in every column
		count1 = 5;
		count2 = 5;
		count3 = 5;
		count4 = 5;
		count5 = 5;
		count6 = 5;
		count7 = 5;
		
		turn = true; //red player always the first to play
	}
}

