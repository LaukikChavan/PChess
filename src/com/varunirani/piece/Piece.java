package com.varunirani.piece;

public enum Piece {
	None(""),
	King("k"),
	Pawn("p"),
	Knight("n"),
	Bishop("b"),
	Rook("r"),
	Queen("q"),

	White("_w"),
	Black("_b");

	public final String id;

	Piece(String id) {
		this.id = id;
	}

}

