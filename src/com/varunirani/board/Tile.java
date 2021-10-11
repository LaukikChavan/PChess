package com.varunirani.board;

import com.varunirani.PChess;
import com.varunirani.piece.Piece;
import processing.core.PApplet;
import processing.core.PImage;

import java.awt.*;

public class Tile extends PApplet {
	private final int rank;
	private final int file;
	private final int x;
	private final int y;
	private final boolean isLightSquare;
	private final String position;
	private final Color lightColor = new Color(239, 240, 209);
	private final Color darkColor = new Color(75, 142, 109);
	private Piece[] currentPiece = {null, Piece.None};

	public Tile(int rank, int file, int x, int y, boolean isLightSquare) {
		this.rank = rank;
		this.file = file;
		this.x = x;
		this.y = y;
		this.isLightSquare = isLightSquare;
		this.position = (char) (file + 97) + "" + (rank + 1);
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	private String getImagePath() {
		String path = "assets/pieces_png/";
		String fileExt = ".png";
		String fileName = "";
		Piece pieceColor = currentPiece[0];
		Piece pieceType = currentPiece[1];

		if (pieceColor == Piece.White) {
			fileName += "_";
		}

		if (pieceColor == Piece.White) {
			fileName += pieceType.id.toUpperCase();
		} else {
			fileName += pieceType.id;
		}

		return path + fileName + fileExt;
	}

	public void drawTile() {
		Color squareColor = isLightSquare ? lightColor : darkColor;
		g = PChess.g;
		surface = PChess.surface;
		noStroke();
		fill(squareColor.getRGB());
		square(x, y, Board.TILE_SIZE);
		if (currentPiece[1] != Piece.None) {
			PImage pieceImage = loadImage(getImagePath());
			image(pieceImage, x, y, Board.TILE_SIZE, Board.TILE_SIZE);
		}
	}

	public void drawRankCoords(int r_x, int r_y) {
		fill(isLightSquare ? darkColor.getRGB() : lightColor.getRGB());
		textFont(PChess.font, PChess.FONT_SIZE);
		text(position.charAt(1), r_x, r_y);
	}

	public void drawFileCoords(int f_x, int f_y) {
		fill(isLightSquare ? darkColor.getRGB() : lightColor.getRGB());
		textFont(PChess.font, PChess.FONT_SIZE);
		text(position.charAt(0), f_x, f_y);
	}

	public String getPosition() {
		return position;
	}

	public int getFile() {
		return file;
	}

	public int getRank() {
		return rank;
	}

	public Piece[] getCurrentPiece() {
		return currentPiece;
	}

	public void setCurrentPiece(Piece[] currentPiece) {
		this.currentPiece = currentPiece;
	}

}
