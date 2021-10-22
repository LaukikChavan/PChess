package com.varunirani;

import com.varunirani.board.Board;
import com.varunirani.board.Tile;
import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PGraphics;
import processing.core.PSurface;

public class PChess extends PApplet {
	protected final int FONT_SIZE = 16;
	protected static PFont _font;
	private Board board;
	protected static PGraphics _g;
	protected static PSurface _surface;
	protected static int _height, _width, _mouseX, _mouseY;
	protected static boolean _mousePressed;

	@Override
	public void settings() {
//		pixelDensity(2);
		size(850, 850);
	}

	@Override
	public void setup() {
		_g = this.getGraphics();
		_surface = this.getSurface();
		_height = height;
		_width = width;
		_font = createFont("Google Sans", FONT_SIZE, true);
		board = new Board();
	}

	@Override
	public void draw() {
		background(38, 39, 48);
		_mouseX = mouseX;
		_mouseY = mouseY;
		_mousePressed = mousePressed;
		board.drawBoard();
	}

	@Override
	public void mousePressed() {
		for (Tile tile : Board.board) {
			if (tile.overTile) {
				if (Board.previousTile != null)
					Board.previousTile.squareColor = Board.previousTileCopy.squareColor;
				if (Board.targetTile != null)
					Board.targetTile.squareColor = Board.targetTileCopy.squareColor;
				Board.previousTile = tile;
				Board.previousTileCopy = tile.clone();
				tile.mousePressed();
			}
		}
	}

	@Override
	public void mouseDragged() {
		for (Tile tile : Board.board) {
			if (tile.isPressed) {
				tile.mouseDragged();
			}
		}
	}

	@Override
	public void mouseReleased() {
		for (Tile tile : Board.board) {
			if (tile.overTile) {
				Board.targetTile = tile;
				Board.targetTileCopy = tile.clone();
				tile.mouseReleased();
			}
		}
	}

	public static void main(String[] args) {
		PApplet.main("com.varunirani.PChess");
	}
}
