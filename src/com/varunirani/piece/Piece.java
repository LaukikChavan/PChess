package com.varunirani.piece;

public class Piece {
	public static final int None = 0;
	public static final int King = 1;
	public static final int Pawn = 2;
	public static final int Knight = 3;
	public static final int Bishop = 4;
	public static final int Rook = 5;
	public static final int Queen = 6;

	public static final int White = 8;
	public static final int Black = 16;

	public static final int TypeMask = 0b00111;
	public static final int BlackMask = 0b10000;
	public static final int WhiteMask = 0b01000;
	public static final int ColorMask = WhiteMask | BlackMask;

	public static boolean IsColor(int piece, int colour) {
		return (piece & ColorMask) == colour;
	}

	public static int Color(int piece) {
		return piece & ColorMask;
	}

	public static int PieceType(int piece) {
		return piece & TypeMask;
	}

	public static boolean IsRookOrQueen(int piece) {
		return (piece & 0b110) == 0b110;
	}

	public static boolean IsBishopOrQueen(int piece) {
		return (piece & 0b101) == 0b101;
	}

	public static boolean IsSlidingPiece(int piece) {
		return (piece & 0b100) != 0;
	}
}

