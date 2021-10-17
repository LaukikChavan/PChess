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
		board.drawBoard();
	}

	@Override
	public void mouseClicked() {
		for (Tile tile: Board.board) {
			tile.mouseClicked();
		}
	}

	public static void main(String[] args) {
		PApplet.main("com.varunirani.PChess");
	}
}
