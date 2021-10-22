package com.varunirani.board;

import com.varunirani.PChess;
import com.varunirani.piece.Piece;

import java.util.HashMap;

public class Board extends PChess {
	private static final int NUM_RANKS = 8, NUM_FILES = 8;
	public static int TILE_SIZE;
	public static Tile[] board = new Tile[64];
	public static HashMap<Character, Integer> pieceFromTypeSymbol;
	public static HashMap<Integer, Character> symbolFromPieceType;
	public static Tile previousTile, previousTileCopy, targetTile, targetTileCopy;
	public static int colorToPlay;

	public Board() {
		this.g = _g;
		this.surface = _surface;
		TILE_SIZE = _height / NUM_RANKS;
		pieceFromTypeSymbol = new HashMap<>() {
			{
				put('k', Piece.King);
				put('p', Piece.Pawn);
				put('n', Piece.Knight);
				put('b', Piece.Bishop);
				put('r', Piece.Rook);
				put('q', Piece.Queen);
			}
		};
		symbolFromPieceType = new HashMap<>() {
			{
				put(Piece.King, 'k');
				put(Piece.Pawn, 'p');
				put(Piece.Knight, 'n');
				put(Piece.Bishop, 'b');
				put(Piece.Rook, 'r');
				put(Piece.Queen, 'q');
			}
		};
		String fen = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 0";
		setPiecesFromFen(fen);
	}

	public void drawBoard() {
		for (Tile tile : board) {
			tile.drawTile();
		}
		for (Tile tile : board) {
			tile.drawImages();
		}
		for (Tile tile : board) {
			tile.checkMouseOver();
		}
	}

	/*
	 * FEN start at a8
	 * lowercase - black
	 * uppercase - white
	 * / - next rank
	 * number - leave squares blank
	 * player to move
	 * castling options
	 * en-passant targets (one square behind actual target)
	 * half move counter (once reaches 50 it's draw)
	 * full move counter
	 * */

	private void setPiecesFromFen(String fen) {
		int startX = _width / 2 - 4 * TILE_SIZE;
		int startY = 0;
		String[] fenSplit = fen.split(" ");
		String pieceArrangement = fenSplit[0];
		String playerToMove = fenSplit[1];
		if (playerToMove.equals("w"))
			colorToPlay = Piece.White;
		else
			colorToPlay = Piece.Black;
		String castlingRights = fenSplit[2];
		String enPassantTarget = fenSplit[3];
		int halfMoveCount = Integer.parseInt(fenSplit[4]);
		int fullMoveCount = Integer.parseInt(fenSplit[5]);

		int file = 0, rank = NUM_RANKS - 1;

		for (char symbol : pieceArrangement.toCharArray()) {
			int x = startX + file * TILE_SIZE; // correct
			int y = startY + TILE_SIZE * (NUM_RANKS - rank % 8 - 1);
			if (symbol == '/') {
				file = 0;
				rank--;
			} else {
				if (Character.isDigit(symbol)) {
					for (int i = 0; i < Integer.parseInt(Character.toString(symbol)); i++) {
						x = startX + file * TILE_SIZE;
						y = startY + TILE_SIZE * (NUM_RANKS - rank % 8 - 1);
						board[rank * NUM_RANKS + file] = new Tile(rank, file, x, y, Piece.None);
						file++;
					}
				} else {
					int pieceColor = Character.isUpperCase(symbol) ? Piece.White : Piece.Black;
					int pieceType = pieceFromTypeSymbol.get(Character.toLowerCase(symbol));
					board[rank * NUM_RANKS + file] = new Tile(rank, file, x, y, pieceColor | pieceType);
					file++;
				}
			}
		}
	}
}
