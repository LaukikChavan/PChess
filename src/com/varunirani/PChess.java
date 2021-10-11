package com.varunirani;

import com.varunirani.board.Board;
import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PGraphics;
import processing.core.PSurface;

public class PChess extends PApplet {
	public static PFont font;
	public static int _mouseX;
	public static int _mouseY;
	private Board board;
	public static PGraphics g;
	public static PSurface surface;
	public static int s_width, s_height;
	public static final int FONT_SIZE = 16;

	@Override
	public void settings() {
		pixelDensity(2);
		size(850, 850);
	}

	@Override
	public void setup() {
		g = this.getGraphics();
		surface = this.getSurface();
		s_width = width;
		s_height = height;
		font = createFont("Google Sans", FONT_SIZE, true);
		board = new Board();
	}

	@Override
	public void draw() {
		background(38, 39, 48);
		_mouseX = mouseX;
		_mouseY = mouseY;
		board.drawBoard();
	}

	public static void main(String[] args) {
		PApplet.main("com.varunirani.PChess");
	}
}
