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
	private final boolean isLightSquare;
	private final String position;
	private final Color lightColor = new Color(239, 240, 209);
	private final Color darkColor = new Color(75, 142, 109);
	private Piece[] currentPiece = {null, Piece.None};
	public boolean overTile = false, isDragging = false;
	PImage pieceImage;
	private int xOffset, yOffset;
	private int imageX, imageY;

	public Tile(int rank, int file, int x, int y, boolean isLightSquare) {
		this.g = _g;
		this.surface = _surface;
		this.rank = rank;
		this.file = file;
		this.x = x;
		this.y = y;
		imageX = x;
		imageY = y;
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
		noStroke();
		fill(squareColor.getRGB());
		square(x, y, Board.TILE_SIZE);
	}

	public void drawImages() {
		if (currentPiece[1] != Piece.None && pieceImage != null) {
			image(pieceImage, imageX, imageY, Board.TILE_SIZE, Board.TILE_SIZE);
		}
	}

	public void drawRankCoords(int r_x, int r_y) {
		fill(isLightSquare ? darkColor.getRGB() : lightColor.getRGB());
		textFont(_font, FONT_SIZE);
		text(position.charAt(1), r_x, r_y);
	}

	public void drawFileCoords(int f_x, int f_y) {
		fill(isLightSquare ? darkColor.getRGB() : lightColor.getRGB());
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

	public void setCurrentPiece(Piece[] currentPiece) {
		this.currentPiece = currentPiece;
//		String path = getImagePath();
//		String file = path.split("/")[path.split("/").length - 1];
//		* !file.equals(".png")
		if (currentPiece[1] != Piece.None) {
			pieceImage = loadImage(getImagePath());
		} else {
			pieceImage = null;
		}
	}

	public void checkMouseOver() {
		if (_mouseX > x && _mouseX < x + Board.TILE_SIZE &&
				_mouseY > y && _mouseY < y + Board.TILE_SIZE) {
			overTile = true;
			if (currentPiece[1] == Piece.None) {
				cursor(ARROW);
			} else {
				cursor(HAND);
			}
			fill(0, 50);
			square(x, y, Board.TILE_SIZE);
		} else {
			overTile = false;
		}
	}

	@Override
	public void mousePressed() {
		isDragging = overTile;
		xOffset = _mouseX - x;
		yOffset = _mouseY - y;

		if (position.equals(Board.tileCopy.position)) {
			pieceImage = Board.tileCopy.pieceImage;
		} else {
			setCurrentPiece(new Piece[]{null, Piece.None});
		}
	}

	@Override
	public void mouseDragged() {
		if (isDragging) {
			imageX = _mouseX - xOffset;
			imageY = _mouseY - yOffset;
//			image(pieceImage, imageX, imageY, Board.TILE_SIZE, Board.TILE_SIZE);
//			if (Board.tileCopy.currentPiece[1] != Piece.None && Board.tileCopy.pieceImage != null) {
//				image(Board.tileCopy.pieceImage, _mouseX - xOffset, _mouseY - yOffset, Board.TILE_SIZE, Board.TILE_SIZE);
//			}
		}
	}

	@Override
	public void mouseReleased() {
		isDragging = false;
		if (!position.equals(Board.tileCopy.position)) {
			setCurrentPiece(Board.tileCopy.getCurrentPiece());
			Board.previousTile.setCurrentPiece(new Piece[]{null, Piece.None});
		}
	}

	public Piece[] getCurrentPiece() {
		return currentPiece;
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
