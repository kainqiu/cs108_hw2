package tetris;

import static org.junit.Assert.*;
import java.util.*;

import org.junit.*;

/*
  Unit test for Piece class -- starter shell.
 */
public class PieceTest {
	// You can create data to be used in the your
	// test cases like this. For each run of a test method,
	// a new PieceTest object is created and setUp() is called
	// automatically by JUnit.
	// For example, the code below sets up some
	// pyramid and s pieces in instance variables
	// that can be used in tests.
	private Piece pyr1, pyr2, pyr3, pyr4;
	private Piece s, sRotated;
	private Piece stick1, stick2, stick3;
	private Piece square1, square2, square3;
	private Piece l1_1, l1_2, l1_3, l1_4, l1_5;
	private Piece l2_1, l2_2, l2_3, l2_4, l2_5;
	private Piece s1_1, s1_2, s1_3, s1_4, s1_5;
	private Piece s2_1, s2_2, s2_3, s2_4, s2_5;
	

	@Before
	public void setUp() throws Exception {	
		// pyramid
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
	
	// Here are some sample tests to get you started
	
	@Test
	public void testSampleSize1() {
		// Check size of pyr piece
		assertEquals(3, pyr1.getWidth());
		assertEquals(2, pyr1.getHeight());
		
		// Now try after rotation
		// Effectively we're testing size and rotation code here
		assertEquals(2, pyr2.getWidth());
		assertEquals(3, pyr2.getHeight());
		
		// Now try with some other piece, made a different way
		Piece l = new Piece(Piece.STICK_STR);
		assertEquals(1, l.getWidth());
		assertEquals(4, l.getHeight());
	}
	
	@Test
	public void testSampleSize2() {
		// Check size of piece
		assertEquals(3, pyr1.getWidth());
		assertEquals(1, stick1.getWidth());
		assertEquals(2, square1.getWidth());
		assertEquals(2, l1_1.getWidth());
		assertEquals(2, l2_1.getWidth());
		assertEquals(3, s1_1.getWidth());
		assertEquals(3, s2_1.getWidth());
		
		assertEquals(2, pyr1.getHeight());
		assertEquals(4, stick1.getHeight());
		assertEquals(2, square1.getHeight());
		assertEquals(3, l1_1.getHeight());
		assertEquals(3, l2_1.getHeight());
		assertEquals(2, s1_1.getHeight());
		assertEquals(2, s2_1.getHeight());
		
		// Now try after rotation
		// Effectively we're testing size and rotation code here
		assertEquals(2, pyr2.getWidth());
		assertEquals(4, stick2.getWidth());
		assertEquals(2, square2.getWidth());
		assertEquals(3, l1_2.getWidth());
		assertEquals(3, l2_2.getWidth());
		assertEquals(2, s1_2.getWidth());
		assertEquals(2, s2_2.getWidth());
		
		assertEquals(3, pyr2.getHeight());
		assertEquals(1, stick2.getHeight());
		assertEquals(2, square2.getHeight());
		assertEquals(2, l1_2.getHeight());
		assertEquals(2, l2_2.getHeight());
		assertEquals(3, s1_2.getHeight());
		assertEquals(3, s2_2.getHeight());
			
		// Now try with some other piece, made a different way
		assertEquals(3, pyr1.getWidth());
		assertEquals(1, stick3.getWidth());
		assertEquals(2, square3.getWidth());
		assertEquals(2, l1_5.getWidth());
		assertEquals(2, l2_5.getWidth());
		assertEquals(3, s1_5.getWidth());
		assertEquals(3, s2_5.getWidth());
		
		assertEquals(2, pyr1.getHeight());
		assertEquals(4, stick3.getHeight());
		assertEquals(2, square3.getHeight());
		assertEquals(3, l1_5.getHeight());
		assertEquals(3, l2_5.getHeight());
		assertEquals(2, s1_5.getHeight());
		assertEquals(2, s2_5.getHeight());
		
	}
	
	
	// Test the skirt returned by a few pieces
	@Test
	public void testSampleSkirt() {
		// Note must use assertTrue(Arrays.equals(... as plain .equals does not work
		// right for arrays.
		assertTrue(Arrays.equals(new int[] {0, 0, 0}, pyr1.getSkirt()));
		assertTrue(Arrays.equals(new int[] {1, 0, 1}, pyr3.getSkirt()));
		
		assertTrue(Arrays.equals(new int[] {0, 0, 1}, s.getSkirt()));
		assertTrue(Arrays.equals(new int[] {1, 0}, sRotated.getSkirt()));
		
		assertTrue(Arrays.equals(new int[] {0}, stick1.getSkirt()));
		assertTrue(Arrays.equals(new int[] {0, 0, 0, 0}, stick2.getSkirt()));
		
		assertTrue(Arrays.equals(new int[] {0, 0}, square1.getSkirt()));
		assertTrue(Arrays.equals(new int[] {0, 0}, square2.getSkirt()));
		
		assertTrue(Arrays.equals(new int[] {0, 0}, l1_1.getSkirt()));
		assertTrue(Arrays.equals(new int[] {0, 0, 0}, l1_2.getSkirt()));
		assertTrue(Arrays.equals(new int[] {2, 0}, l1_3.getSkirt()));
		assertTrue(Arrays.equals(new int[] {0, 1, 1}, l1_4.getSkirt()));
		
		assertTrue(Arrays.equals(new int[] {0, 0}, l2_1.getSkirt()));
		assertTrue(Arrays.equals(new int[] {1, 1, 0}, l2_2.getSkirt()));
		assertTrue(Arrays.equals(new int[] {0, 2}, l2_3.getSkirt()));
		assertTrue(Arrays.equals(new int[] {0, 0, 0}, l2_4.getSkirt()));
		
		assertTrue(Arrays.equals(new int[] {0, 0, 1}, s1_1.getSkirt()));
		assertTrue(Arrays.equals(new int[] {1, 0}, s1_2.getSkirt()));
		
		assertTrue(Arrays.equals(new int[] {1, 0, 0}, s2_1.getSkirt()));
		assertTrue(Arrays.equals(new int[] {0, 1}, s2_2.getSkirt()));
	}
	
	// Test the fastRotation()
	@Test
	public void testEquals() {
		assertTrue(s1_1.equals(s1_3));
		assertTrue(s2_1.equals(s2_3));
		assertTrue(l1_1.equals(l1_4.computeNextRotation()));
		assertTrue(l2_1.equals(l2_4.computeNextRotation()));
		assertTrue(stick1.equals(stick3));
		assertTrue(square2.equals(square3));
		assertTrue(pyr1.equals(pyr4.computeNextRotation()));
	}
	
	// Test the fastRotation()
	@Test
	public void testFastRotataion() {
		assertTrue(s1_1.equals(s1_2.fastRotation()));
		assertTrue(s1_1.equals(s1_1.fastRotation().fastRotation()));
		assertTrue(s2_2.equals(s2_1.fastRotation()));
		assertTrue(s2_1.equals(s2_1.fastRotation().fastRotation().fastRotation().fastRotation()));
		assertTrue(l1_2.equals(l1_5.fastRotation()));
		assertTrue(l2_3.equals(l2_5.fastRotation().fastRotation()));
		assertTrue(stick1.equals(stick3.fastRotation().fastRotation()));
		assertTrue(square2.equals(square3.fastRotation()));
		assertTrue(pyr1.equals(pyr1.fastRotation().fastRotation().fastRotation().fastRotation()));
	}
	
	
}
