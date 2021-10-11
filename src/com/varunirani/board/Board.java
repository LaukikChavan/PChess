package com.varunirani.board;

import com.varunirani.PChess;
import com.varunirani.piece.Piece;
import processing.core.PApplet;

import java.util.Arrays;
import java.util.HashMap;

public class Board extends PApplet {
	private static final int NUM_RANKS = 8, NUM_FILES = 8;
	public static int TILE_SIZE;
	public static HashMap<String, Tile> chessTiles = new HashMap<>();

	public Board() {
		TILE_SIZE = PChess.s_height / NUM_RANKS;
	}

	public void drawBoard() {
		g = PChess.g;
		int startX = PChess.s_width / 2 - 4 * TILE_SIZE;
		int startY = PChess.s_height - TILE_SIZE;
		for (int rank = 0; rank < NUM_RANKS; rank++) {
			for (int file = 0; file < NUM_FILES; file++) {
				boolean isLightSquare = (rank + file) % 2 != 0;
				int x = startX + TILE_SIZE * file;
				int y = startY - TILE_SIZE * rank;
				Tile tile = new Tile(rank, file, x, y, isLightSquare);
				chessTiles.put(tile.getPosition(), tile);
			}
		}
		String fen = "3k3r/2N2qpp/5n2/2Qnp3/1p1P4/2P3PB/4PP2/R1B2RK1 w - - 44 22";
		drawPiecesFromFen(fen);
		for (Tile tile : chessTiles.values()) {
			tile.drawTile();
//			System.out.println(tile.getPosition() + " " + Arrays.toString(tile.getCurrentPiece()));
			if (tile.getFile() == NUM_FILES - 1) {
				tile.drawRankCoords(tile.getX() + TILE_SIZE - PChess.FONT_SIZE, tile.getY() + (3 * PChess.FONT_SIZE) / 2);
			}
			if (tile.getRank() == 0) {
				tile.drawFileCoords(tile.getX() + PChess.FONT_SIZE / 2, tile.getY() + TILE_SIZE - PChess.FONT_SIZE / 2);
			}
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

	private void drawPiecesFromFen(String fen) {
		HashMap<Character, Piece> pieceFromTypeSymbol = new HashMap<>() {
			{
				put('k', Piece.King);
				put('p', Piece.Pawn);
				put('n', Piece.Knight);
				put('b', Piece.Bishop);
				put('r', Piece.Rook);
				put('q', Piece.Queen);
			}
		};

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
