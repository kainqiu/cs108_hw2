package tetris;

import static org.junit.Assert.*;

import org.junit.*;

public class BoardTest {
	Board b;
	Piece pyr1, pyr2, pyr3, pyr4, s, sRotated;

	private Piece stick1, stick2, stick3;
	private Piece square1, square2, square3;
	private Piece l1_1, l1_2, l1_3, l1_4, l1_5;
	private Piece l2_1, l2_2, l2_3, l2_4, l2_5;
	private Piece s1_1, s1_2, s1_3, s1_4, s1_5;
	private Piece s2_1, s2_2, s2_3, s2_4, s2_5;

	// This shows how to build things in setUp() to re-use
	// across tests.

	// In this case, setUp() makes shapes,
	// and also a 3X6 board, with pyr placed at the bottom,
	// ready to be used by tests.
	@Before
	public void setUp() throws Exception {
		b = new Board(4, 10);

		pyr1 = new Piece(Piece.PYRAMID_STR);
		pyr2 = pyr1.computeNextRotation();
		pyr3 = pyr2.computeNextRotation();
		pyr4 = pyr3.computeNextRotation();

		s = new Piece(Piece.S1_STR);
		sRotated = s.computeNextRotation();

		// stick
		stick1 = new Piece(Piece.STICK_STR);
		stick2 = stick1.computeNextRotation();
		stick3 = Piece.getPieces()[Piece.STICK];

		// square
		square1 = new Piece(Piece.SQUARE_STR);
		square2 = square1.computeNextRotation();
		square3 = Piece.getPieces()[Piece.SQUARE];

		// l1
		l1_1 = new Piece(Piece.L1_STR);
		l1_2 = l1_1.computeNextRotation();
		l1_3 = l1_2.computeNextRotation();
		l1_4 = l1_3.computeNextRotation();
		l1_5 = Piece.getPieces()[Piece.L1];

		// l2
		l2_1 = new Piece(Piece.L2_STR);
		l2_2 = l2_1.computeNextRotation();
		l2_3 = l2_2.computeNextRotation();
		l2_4 = l2_3.computeNextRotation();
		l2_5 = Piece.getPieces()[Piece.L2];

		// s1
		s1_1 = new Piece(Piece.S1_STR);
		s1_2 = s1_1.computeNextRotation();
		s1_3 = s1_2.computeNextRotation();
		s1_4 = s1_3.computeNextRotation();
		s1_5 = Piece.getPieces()[Piece.S1];

		// s2
		s2_1 = new Piece(Piece.S2_STR);
		s2_2 = s2_1.computeNextRotation();
		s2_3 = s2_2.computeNextRotation();
		s2_4 = s2_3.computeNextRotation();
		s2_5 = Piece.getPieces()[Piece.S2];

		
	}

	/*
	// Check the basic width/height/max after the one placement
	@Test
	public void testSample1() {
		System.out.println("begin test1");
		b.place(pyr1, 0, 0);
		assertEquals(1, b.getColumnHeight(0));
		assertEquals(2, b.getColumnHeight(1));
		assertEquals(2, b.getMaxHeight());
		assertEquals(3, b.getRowWidth(0));
		assertEquals(1, b.getRowWidth(1));
		assertEquals(0, b.getRowWidth(2));
		System.out.println("end test1");
	}

	// Place sRotated into the board, then check some measures
	@Test
	public void testSample2() {
		b.place(pyr1, 0, 0);
		System.out.println("in test2");
		b.commit();
		int result = b.place(sRotated, 1, 1);
		//System.out.println(b.toString());
		assertEquals(Board.PLACE_OK, result);
		assertEquals(1, b.getColumnHeight(0));
		assertEquals(4, b.getColumnHeight(1));
		assertEquals(3, b.getColumnHeight(2));
		assertEquals(4, b.getMaxHeight());
	}

	// Make  more tests, by putting together longer series of 
	// place, clearRows, undo, place ... checking a few col/row/max
	// numbers that the board looks right after the operations.

	// Check the basic width/height/max after the one placement, with place L1 root rotation
		@Test
		public void testSample3() {
			b.place(l1_1, 1, 0);
			assertEquals(0, b.getColumnHeight(0));
			assertEquals(3, b.getColumnHeight(1));
			assertEquals(1, b.getColumnHeight(2));
			assertEquals(3, b.getMaxHeight());
			assertEquals(2, b.getRowWidth(0));
			assertEquals(1, b.getRowWidth(1));
			assertEquals(1, b.getRowWidth(2));
			assertEquals(0, b.getRowWidth(3));
			assertEquals(0, b.getRowWidth(4));
		}
	*/
		// Check the basic width/height/max after the one placement
		@Test
		public void testSample4() {
			b.place(s2_1, 0, 0);
			b.commit();
			int result1 = b.place(square1, 2, 1);
			b.commit();
			int result2 = b.place(l2_3, 1, 2);
			b.commit();
			int result3 = b.place(stick1, 3, 4);
			b.commit();
			
			assertEquals(Board.PLACE_ROW_FILLED, result1);
			assertEquals(Board.PLACE_OK, result2);
			assertEquals(Board.PLACE_OK, result3);		
			
			assertEquals(2, b.getColumnHeight(0));
			assertEquals(5, b.getColumnHeight(1));
			assertEquals(5, b.getColumnHeight(2));
			assertEquals(8, b.getColumnHeight(3));
			assertEquals(8, b.getMaxHeight());
			assertEquals(2, b.getRowWidth(0));
			assertEquals(4, b.getRowWidth(1));
			assertEquals(3, b.getRowWidth(2));
			assertEquals(1, b.getRowWidth(3));
			assertEquals(3, b.getRowWidth(4));
			assertEquals(1, b.getRowWidth(5));
			assertEquals(1, b.getRowWidth(6));
			assertEquals(1, b.getRowWidth(7));
			assertEquals(0, b.getRowWidth(8));
			
			b.clearRows();
			assertEquals(0, b.getColumnHeight(0));
			assertEquals(4, b.getColumnHeight(1));
			assertEquals(4, b.getColumnHeight(2));
			assertEquals(7, b.getColumnHeight(3));
			assertEquals(7, b.getMaxHeight());
			assertEquals(2, b.getRowWidth(0));
			assertEquals(3, b.getRowWidth(1));
			assertEquals(1, b.getRowWidth(2));
			assertEquals(3, b.getRowWidth(3));
			assertEquals(1, b.getRowWidth(4));
			assertEquals(1, b.getRowWidth(5));
			assertEquals(1, b.getRowWidth(6));
			assertEquals(0, b.getRowWidth(7));
			assertEquals(0, b.getRowWidth(8));
			
			b.undo();
			assertEquals(Board.PLACE_ROW_FILLED, result1);
			assertEquals(Board.PLACE_OK, result2);
			assertEquals(Board.PLACE_OK, result3);		
			
			assertEquals(2, b.getColumnHeight(0));
			assertEquals(5, b.getColumnHeight(1));
			assertEquals(5, b.getColumnHeight(2));
			assertEquals(8, b.getColumnHeight(3));
			assertEquals(8, b.getMaxHeight());
			assertEquals(2, b.getRowWidth(0));
			assertEquals(4, b.getRowWidth(1));
			assertEquals(3, b.getRowWidth(2));
			assertEquals(1, b.getRowWidth(3));
			assertEquals(3, b.getRowWidth(4));
			assertEquals(1, b.getRowWidth(5));
			assertEquals(1, b.getRowWidth(6));
			assertEquals(1, b.getRowWidth(7));
			assertEquals(0, b.getRowWidth(8));
			
		}
		
		@Test
		public void testSample5() {
			b.place(pyr1, 0, 0);
			b.commit();
			int result1 = b.place(s1_2, 2, 0);
			b.commit();
			int result2 = b.place(pyr4, 0, 1);
			b.commit();
			int result3 = b.place(l1_3, 2, 2);
			b.commit();
			int result4 = b.place(pyr1, 1, 6);
			b.commit();
			int result5 = b.place(stick2, 0, 5);
			
			assertEquals(Board.PLACE_ROW_FILLED, result1);
			assertEquals(Board.PLACE_ROW_FILLED, result2);
			assertEquals(Board.PLACE_ROW_FILLED, result3);
			assertEquals(Board.PLACE_OK, result4);
			assertEquals(Board.PLACE_ROW_FILLED, result5);
			
			assertEquals(6, b.getColumnHeight(0));
			assertEquals(7, b.getColumnHeight(1));
			assertEquals(8, b.getColumnHeight(2));
			assertEquals(7, b.getColumnHeight(3));
			assertEquals(8, b.getMaxHeight());
			assertEquals(4, b.getRowWidth(0));
			assertEquals(4, b.getRowWidth(1));
			assertEquals(4, b.getRowWidth(2));
			assertEquals(2, b.getRowWidth(3));
			assertEquals(2, b.getRowWidth(4));
			assertEquals(4, b.getRowWidth(5));
			assertEquals(3, b.getRowWidth(6));
			assertEquals(1, b.getRowWidth(7));
			assertEquals(0, b.getRowWidth(8));
			
			b.clearRows();
			assertEquals(1, b.getColumnHeight(0));
			assertEquals(3, b.getColumnHeight(1));
			assertEquals(4, b.getColumnHeight(2));
			assertEquals(3, b.getColumnHeight(3));
			assertEquals(4, b.getMaxHeight());
			assertEquals(2, b.getRowWidth(0));
			assertEquals(2, b.getRowWidth(1));
			assertEquals(3, b.getRowWidth(2));
			assertEquals(1, b.getRowWidth(3));
			assertEquals(0, b.getRowWidth(4));
			assertEquals(0, b.getRowWidth(5));
			assertEquals(0, b.getRowWidth(6));
			assertEquals(0, b.getRowWidth(7));
			assertEquals(0, b.getRowWidth(8));
			
			b.undo();
			assertEquals(6, b.getColumnHeight(0));
			assertEquals(7, b.getColumnHeight(1));
			assertEquals(8, b.getColumnHeight(2));
			assertEquals(7, b.getColumnHeight(3));
			assertEquals(8, b.getMaxHeight());
			assertEquals(4, b.getRowWidth(0));
			assertEquals(4, b.getRowWidth(1));
			assertEquals(4, b.getRowWidth(2));
			assertEquals(2, b.getRowWidth(3));
			assertEquals(2, b.getRowWidth(4));
			assertEquals(4, b.getRowWidth(5));
			assertEquals(3, b.getRowWidth(6));
			assertEquals(1, b.getRowWidth(7));
			assertEquals(0, b.getRowWidth(8));
			
		}

}
