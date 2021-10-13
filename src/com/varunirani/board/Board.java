package com.varunirani.board;

import com.varunirani.PChess;
import com.varunirani.piece.Piece;

import java.util.HashMap;

public class Board extends PChess {
	private static final int NUM_RANKS = 8, NUM_FILES = 8;
	public static int TILE_SIZE;
	public static HashMap<String, Tile> chessTiles = new HashMap<>();
	HashMap<Character, Piece> pieceFromTypeSymbol;
	public static Tile tileCopy, previousTile;

	public Board() {
		this.g = _g;
		this.surface = _surface;
		TILE_SIZE = _height / NUM_RANKS;
		int startX = _width / 2 - 4 * TILE_SIZE;
		int startY = _height - TILE_SIZE;
		for (int rank = 0; rank < NUM_RANKS; rank++) {
			for (int file = 0; file < NUM_FILES; file++) {
				boolean isLightSquare = (rank + file) % 2 != 0;
				int x = startX + TILE_SIZE * file;
				int y = startY - TILE_SIZE * rank;
				Tile tile = new Tile(rank, file, x, y, isLightSquare);
				chessTiles.put(tile.getPosition(), tile);
			}
		}
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
		String fen = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 0";
		setPiecesFromFen(fen);
	}

	public void drawBoard() {
		for (Tile tile : chessTiles.values()) {
			if (tile.getFile() == NUM_FILES - 1) {
				tile.drawRankCoords(tile.getX() + TILE_SIZE - FONT_SIZE, tile.getY() + (3 * FONT_SIZE) / 2);
			}
			if (tile.getRank() == 0) {
				tile.drawFileCoords(tile.getX() + FONT_SIZE / 2, tile.getY() + TILE_SIZE - FONT_SIZE / 2);
			}
			tile.drawTile();
			tile.checkMouseOver();
		}
		for (Tile tile: chessTiles.values()) {
			tile.drawImages();
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
		String[] fenSplit = fen.split(" ");
		String pieceArrangement = fenSplit[0];
		String playerToMove = fenSplit[1];
		String castlingRights = fenSplit[2];
		String enPassantTarget = fenSplit[3];
		int halfMoveCount = Integer.parseInt(fenSplit[4]);
		int fullMoveCount = Integer.parseInt(fenSplit[5]);

		int file = 0, rank = NUM_RANKS - 1;

		for (char symbol : pieceArrangement.toCharArray()) {
			if (symbol == '/') {
				file = 0;
				rank--;
			} else {
				if (Character.isDigit(symbol)) {
					file += Integer.parseInt(Character.toString(symbol));
				} else {
					Piece pieceColor = Character.isUpperCase(symbol) ? Piece.White : Piece.Black;
					Piece pieceType = pieceFromTypeSymbol.get(Character.toLowerCase(symbol));
					chessTiles.get((char) (file + 97) + "" + (rank + 1)).setCurrentPiece(new Piece[]{pieceColor, pieceType});
					file++;
				}
			}
		}
	}
}
