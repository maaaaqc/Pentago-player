package student_player;

import boardgame.Move;
import pentago_swap.PentagoBoardState;
import pentago_swap.PentagoBoardState.Piece;
import pentago_swap.PentagoMove;

public class MyTools {
	// getWin
	public static Move getWin(PentagoBoardState boardState) {
		for (Move a: boardState.getAllLegalMoves()) {
			PentagoBoardState temp = (PentagoBoardState) boardState.clone();
			temp.processMove((PentagoMove) a);
			if (temp.getWinner() == boardState.getTurnPlayer()) {
				return a;
			}
		}
		return null;
	}
	// getMiddle
	public static Move getMiddle(PentagoBoardState boardState) {
		int count = 0;
		Piece temp = Piece.WHITE;
		if (boardState.getTurnPlayer() == 1) {
			temp = Piece.BLACK;
		}
		if (boardState.getPieceAt(1,1) == temp) {
			count++;
		}
		if (boardState.getPieceAt(1,4) == temp) {
			count++;
		}
		if (boardState.getPieceAt(4,1) == temp) {
			count++;
		}
		if (boardState.getPieceAt(4,4) == temp) {
			count++;
		}
		if (count >= 2) {
			return null;
		}
		if (boardState.getPieceAt(1,1) == Piece.EMPTY) {
			return (new PentagoMove("1 1 TL TR " + boardState.getTurnPlayer()));
		}
		if (boardState.getPieceAt(1,4) == Piece.EMPTY) {
			return (new PentagoMove("1 4 TL TR " + boardState.getTurnPlayer()));
		}
		if (boardState.getPieceAt(4,1) == Piece.EMPTY) {
			return (new PentagoMove("4 1 TL TR " + boardState.getTurnPlayer()));
		}
		if (boardState.getPieceAt(4,4) == Piece.EMPTY) {
			return (new PentagoMove("4 4 TL TR " + boardState.getTurnPlayer()));
		}
		return null;
	}
	// checkFive
	public static int checkFive(Piece[] pieces, boolean whitePlayer){
		int blackcount = 0;
		int whitecount = 0;
		boolean containBlack = false;
		boolean containWhite = false;
		for (int i = 0; i < 5; i++) {
			if (pieces[i] == Piece.BLACK) {
				containBlack = true;
				blackcount++;
			}
			if (pieces[i] == Piece.WHITE) {
				containWhite = true;
				whitecount++;
			}
		}
		if (blackcount == 5 && !containWhite && !whitePlayer) {
			return 100000;
		}
		if (blackcount == 5 && !containWhite && whitePlayer) {
			return -100000;
		}
		if (whitecount == 5 && !containBlack && whitePlayer) {
			return 100000;
		}
		if (whitecount == 5 && !containBlack && !whitePlayer) {
			return -100000;
		}
		if (containBlack && !containWhite && whitePlayer) {
			return (int) (-3*Math.pow(blackcount, 6));
		}
		if (containBlack && !containWhite && !whitePlayer) {
			return (int) (Math.pow(blackcount, 6));
		}
		if (!containBlack && containWhite && whitePlayer) {
			return (int) (Math.pow(whitecount, 6));
		}
		if (!containBlack && containWhite && !whitePlayer) {
			return (int) (-3*Math.pow(whitecount, 6));
		}
		return 0;
	}
	// heuristic
	public static int heuristic(PentagoBoardState boardState) {
		int count = 0;
		boolean white = boardState.firstPlayer() == boardState.getTurnPlayer();
		// vertical
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 2; j++) {
				Piece[] pieces = {boardState.getPieceAt(i,j), boardState.getPieceAt(i,j+1),
					boardState.getPieceAt(i,j+2), boardState.getPieceAt(i,j+3), 
					boardState.getPieceAt(i,j+4)};
				count += checkFive(pieces, white);
			}
		}
		// horizontal
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 2; j++) {
				Piece[] pieces = {boardState.getPieceAt(j,i), boardState.getPieceAt(j+1,i),
					boardState.getPieceAt(j+2,i), boardState.getPieceAt(j+3,i), 
					boardState.getPieceAt(j+4,i)};
				count += checkFive(pieces, white);
			}
		}
		// diagonal
		Piece[] pieces = {boardState.getPieceAt(0,0), boardState.getPieceAt(1,1),
				boardState.getPieceAt(2,2), boardState.getPieceAt(3,3), 
				boardState.getPieceAt(4,4)};
		count += checkFive(pieces, white);
		Piece[] pieces2 = {boardState.getPieceAt(0,1), boardState.getPieceAt(1,2),
				boardState.getPieceAt(2,3), boardState.getPieceAt(3,4), 
				boardState.getPieceAt(4,5)};
		count += checkFive(pieces2, white);
		Piece[] pieces3 = {boardState.getPieceAt(1,0), boardState.getPieceAt(2,1),
				boardState.getPieceAt(3,2), boardState.getPieceAt(4,3), 
				boardState.getPieceAt(5,4)};
		count += checkFive(pieces3, white);
		Piece[] pieces4 = {boardState.getPieceAt(1,1), boardState.getPieceAt(2,2), 
				boardState.getPieceAt(3,3), boardState.getPieceAt(4,4),
				boardState.getPieceAt(5,5)};
		count += checkFive(pieces4, white);
		Piece[] pieces5 = {boardState.getPieceAt(4,0), boardState.getPieceAt(3,1),
				boardState.getPieceAt(2,2), boardState.getPieceAt(1,3), 
				boardState.getPieceAt(0,4)};
		count += checkFive(pieces5, white);
		Piece[] pieces6 = {boardState.getPieceAt(4,1), boardState.getPieceAt(3,2),
				boardState.getPieceAt(2,3), boardState.getPieceAt(1,4), 
				boardState.getPieceAt(0,5)};
		count += checkFive(pieces6, white);
		Piece[] pieces7 = {boardState.getPieceAt(5,0), boardState.getPieceAt(4,1),
				boardState.getPieceAt(3,2), boardState.getPieceAt(2,3), 
				boardState.getPieceAt(1,4)};
		count += checkFive(pieces7, white);
		Piece[] pieces8 = {boardState.getPieceAt(5,1), boardState.getPieceAt(4,2), 
				boardState.getPieceAt(3,3), boardState.getPieceAt(2,4),
				boardState.getPieceAt(1,5)};
		count += checkFive(pieces8, white);
		return count;
	}
}