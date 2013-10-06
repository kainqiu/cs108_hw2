// JBrainTetris.java

package tetris;

import java.awt.*;
import javax.swing.*;

import java.util.*;
import java.awt.event.*;
import javax.swing.event.*;

import java.awt.Toolkit;


public class JBrainTetris extends JTetris {
	
	protected JCheckBox brainMode;
	private DefaultBrain defaultBrain;
	protected int brainPieceCount = 0;
	protected Brain.Move brainMove;
	protected JSlider adversary;
	protected int adversaryLevel;
	protected JLabel adversaryLabel;
	
	/**
	 * Creates a new JBrainTetris where each tetris square
	 * is drawn with the given number of pixels.
	 */
	JBrainTetris(int pixels) {
		super(pixels);
		this.brainMove = new Brain.Move();
		this.defaultBrain = new DefaultBrain();
	}
	
	/**
	 Sets the enabling of the start/stop buttons
	 based on the gameOn state.
	*/
	private void enableButtons() {
		startButton.setEnabled(!gameOn);
		stopButton.setEnabled(gameOn);
	}
	
	/**
	 Override createControlPanel() to tack on a Brain label
	  and the JCheckBox that controls if the brain is active. 
	*/
	@Override
	public JComponent createControlPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		// COUNT
		countLabel = new JLabel("0");
		panel.add(countLabel);
		
		// SCORE
		scoreLabel = new JLabel("0");
		panel.add(scoreLabel);
		
		// TIME 
		timeLabel = new JLabel(" ");
		panel.add(timeLabel);

		panel.add(Box.createVerticalStrut(12));
		
		// START button
		startButton = new JButton("Start");
		panel.add(startButton);
		startButton.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				startGame();
 			}
		});
		
		// STOP button
		stopButton = new JButton("Stop");
		panel.add(stopButton);
		stopButton.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				stopGame();
			}
		});
		
		enableButtons();
		
		JPanel row = new JPanel();
		
		// SPEED slider
		panel.add(Box.createVerticalStrut(12));
		row.add(new JLabel("Speed:"));
		speed = new JSlider(0, 200, 75);	// min, max, current
		speed.setPreferredSize(new Dimension(100, 15));
		
		updateTimer();
		row.add(speed);
		
		panel.add(row);
		speed.addChangeListener( new ChangeListener() {
			// when the slider changes, sync the timer to its value
			public void stateChanged(ChangeEvent e) {
				updateTimer();
			}
		});
		
		// add adversary
		JPanel rowAdversary = new JPanel();	
		adversaryLabel = new JLabel("ok");
		rowAdversary.add(new JLabel("Adversary:"));	
		adversary = new JSlider(0, 100, 0);	// min, max, current
		adversary.setPreferredSize(new Dimension(100, 15));
		//updateAdversary();
		rowAdversary.add(adversary);
		rowAdversary.add(adversaryLabel);
		panel.add(rowAdversary);
		adversary.addChangeListener( new ChangeListener() {
			// when the slider changes, change the adversary label
			public void stateChanged(ChangeEvent e) {
				updateAdversaryLabel();
			}
		});
		
		testButton = new JCheckBox("Test sequence");
		panel.add(testButton);
		
		// add Brain checkbox
		panel.add(new JLabel("Brain:"));
		brainMode = new JCheckBox("Brain active"); 
		brainMode.setSelected(false);
		panel.add(brainMode);
			
		return panel;
	}
	
	/**
	 Updates the adversary label to reflect the current setting of the 
	 adversary slider.
	*/
	public void updateAdversaryLabel() {
		if(adversary.getValue() != 0) {
			adversaryLabel.setText("*ok*");
		} else {
			adversaryLabel.setText("ok");
		}
	}
	
	/**
	 Override pickNextPiece() including the effect of adversary
	*/
	@Override
	public Piece pickNextPiece() {
		System.out.println(">> in pickNextPiece");
		Piece piece = null;
		adversaryLevel = (int) adversary.getValue();
		if(adversaryLevel == 0) {
			System.out.println(">> in adver Level = 0");
			int pieceNum;	
			pieceNum = (int) (pieces.length * random.nextDouble());	
			piece = pieces[pieceNum];
		} else if(adversaryLevel == 100) {
			System.out.println(">> in adver level = 100");
			piece = pickAdversaryPiece();
			System.out.println("piece is -> " + String.valueOf(piece));
		} else {
			Random randAdversary = new Random();
		    int randomAdversary = randAdversary.nextInt(99) + 1;
		    if(randomAdversary >= adversary.getValue()) {
		    	System.out.println(">> in adver level = " + String.valueOf(randomAdversary));
		    	piece = super.pickNextPiece();
		    	System.out.println("####in NON adversary");
		    } else {
		    	System.out.println(">> in adver level = " + String.valueOf(randomAdversary));
		    	piece = pickAdversaryPiece();
		    	System.out.println("####in adversary");
		    }
		}
		return piece;
	}
	
	/**
	 Pick adversary piece, of which the best move has the worst score
	 */
	private Piece pickAdversaryPiece() {
		double worstScore = 0;
		Piece worstPiece = null;
		Brain.Move move = new Brain.Move();
		for(int i = 0; i < pieces.length; i++) {
			move = this.defaultBrain.bestMove(board, pieces[i], JTetris.HEIGHT, move);
			if(worstScore < move.score) {
				worstScore = move.score;
				worstPiece = move.piece;
			}
		}
		return worstPiece;
	}
	
	/**
	 Override the tick() when using brain
	*/
	@Override
	public void tick(int verb) {
		if (!gameOn) return;
		
		if (currentPiece != null) {
			board.undo();	// remove the piece from its old position
		}
		
		// Sets the newXXX ivars
		computeNewPosition(verb);
		
		// check if brain is on
		if(this.brainMode.isSelected() && verb == DOWN) {
			if(this.brainPieceCount != super.count) {
				board.undo();	
				System.out.println("Current x and y >>>>>>>>>>>>>>>>>>");
				System.out.println("x = " + String.valueOf(currentX) + ", y = " + String.valueOf(currentY));
				this.brainMove = this.defaultBrain.bestMove(board, currentPiece, JTetris.HEIGHT, this.brainMove);
				this.brainPieceCount = super.count;
				if(this.brainMove != null) {
					System.out.println("new best move >>>>>>>>>>>>>>>>>>>>>>>> ");
					System.out.println("x = " + String.valueOf(this.brainMove.x) + ", y = " + String.valueOf(this.brainMove.y));
				}
			}
			if(this.brainMove != null) {
				if(currentX > brainMove.x) {
					newX = currentX - 1;
					//computeNewPosition(LEFT);
				} else if (currentX < brainMove.x) {
					newX = currentX + 1;
					//computeNewPosition(RIGHT);
				}
				if(!currentPiece.equals(brainMove.piece)) {
					newPiece = currentPiece.fastRotation();
					//computeNewPosition(ROTATE);
				}
			}
		}
			
		
		// try out the new position (rolls back if it doesn't work)
		int result = setCurrent(newPiece, newX, newY);
		
		// if row clearing is going to happen, draw the
		// whole board so the green row shows up
		if (result ==  Board.PLACE_ROW_FILLED) {
			repaint();
		}
		

		boolean failed = (result >= Board.PLACE_OUT_BOUNDS);
		
		// if it didn't work, put it back the way it was
		if (failed) {
			if (currentPiece != null) board.place(currentPiece, currentX, currentY);
			repaintPiece(currentPiece, currentX, currentY);
		}
		
		/*
		 How to detect when a piece has landed:
		 if this move hits something on its DOWN verb,
		 and the previous verb was also DOWN (i.e. the player was not
		 still moving it),	then the previous position must be the correct
		 "landed" position, so we're done with the falling of this piece.
		*/
		if (failed && verb==DOWN && !moved) {	// it's landed
		
			int cleared = board.clearRows();
			if (cleared > 0) {
				// score goes up by 5, 10, 20, 40 for row clearing
				// clearing 4 gets you a beep!
				switch (cleared) {
					case 1: score += 5;	 break;
					case 2: score += 10;  break;
					case 3: score += 20;  break;
					case 4: score += 40; Toolkit.getDefaultToolkit().beep(); break;
					default: score += 50;  // could happen with non-standard pieces
				}
				updateCounters();
				repaint();	// repaint to show the result of the row clearing
			}
			
			
			// if the board is too tall, we've lost
			if (board.getMaxHeight() > board.getHeight() - TOP_SPACE) {
				stopGame();
			}
			// Otherwise add a new piece and keep playing
			else {
				addNewPiece();
			}
		}
		
		// Note if the player made a successful non-DOWN move --
		// used to detect if the piece has landed on the next tick()
		moved = (!failed && verb!=DOWN);
	}

	/**
	 Updates the count/score labels with the latest values.
	 */
	private void updateCounters() {
		countLabel.setText("Pieces " + count);
		scoreLabel.setText("Score " + score);
	}
	
	/**
	 Creates a frame with a JBrainTetris.
	*/
	public static void main(String[] args) {
		// Set GUI Look And Feel Boilerplate.
		// Do this incantation at the start of main() to tell Swing
		// to use the GUI LookAndFeel of the native platform. It's ok
		// to ignore the exception.
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception ignored) { }
		
		JBrainTetris brainTetris = new JBrainTetris(16);
		JFrame frame = JBrainTetris.createFrame(brainTetris);
		frame.setVisible(true);
	}
	
}
