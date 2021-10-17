package com.varunirani.board;

import com.varunirani.PChess;
import com.varunirani.piece.Piece;
import processing.core.PImage;

import java.awt.*;

public class Tile extends PChess implements Cloneable {
	private final int rank;
	private final int file;
	private final int x;
	private final int y;
	private final String position;
	private final Color lightColor = new Color(239, 240, 209);
	private final Color darkColor = new Color(75, 142, 109);
	private int currentPiece;
	PImage pieceImage;
	private boolean overTile;

	public Tile(int rank, int file, int x, int y, int currentPiece) {
		this.g = _g;
		this.surface = _surface;
		this.rank = rank;
		this.file = file;
		this.x = x;
		this.y = y;
		this.currentPiece = currentPiece;
		if (currentPiece != Piece.None) {
			pieceImage = loadImage(getImagePath());
		} else {
			pieceImage = null;
		}
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

		if (Piece.IsColor(currentPiece, Piece.White)) {
			fileName += "_";
		}

		if (Piece.IsColor(currentPiece, Piece.White)) {
			fileName += Board.symbolFromPieceType.get(Piece.PieceType(currentPiece)).toString().toUpperCase();
		} else {
			fileName += Board.symbolFromPieceType.get(Piece.PieceType(currentPiece)).toString();
		}

		return path + fileName + fileExt;
	}

	public void drawTile() {
		Color squareColor = (rank + file) % 2 != 0 ? lightColor : darkColor;
		noStroke();
		fill(squareColor.getRGB());
		square(x, y, Board.TILE_SIZE);
	}

	public void drawImages() {
		if (currentPiece != Piece.None && pieceImage != null) {
			image(pieceImage, x, y, Board.TILE_SIZE, Board.TILE_SIZE);
		}
	}

	public void drawRankCoords(int r_x, int r_y) {
		fill(Piece.IsColor(currentPiece, Piece.White) ? darkColor.getRGB() : lightColor.getRGB());
		textFont(_font, FONT_SIZE);
		text(position.charAt(1), r_x, r_y);
	}

	public void drawFileCoords(int f_x, int f_y) {
		fill(Piece.IsColor(currentPiece, Piece.White) ? darkColor.getRGB() : lightColor.getRGB());
		textFont(_font, FONT_SIZE);
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

	public void setCurrentPiece(int currentPiece) {
		this.currentPiece = currentPiece;
	}

	public void checkMouseOver() {
		int mouseX = PChess._mouseX;
		int mouseY = PChess._mouseY;
		overTile = mouseX > x && mouseX < x + Board.TILE_SIZE &&
				mouseY > y && mouseY < y + Board.TILE_SIZE;

		if (overTile) {
			fill(0, 50);
			square(x, y, Board.TILE_SIZE);
		}
	}

	@Override
	public void mouseClicked() {

	}

	@Override
	public Tile clone() {
		try {
			return (Tile) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new AssertionError();
		}
	}
}
