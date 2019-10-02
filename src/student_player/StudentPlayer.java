package student_player;

import boardgame.Move;
import java.util.*;
import java.util.Map.Entry;
import pentago_swap.PentagoPlayer;
import pentago_swap.PentagoBoardState;
import pentago_swap.PentagoMove;

/** A player file submitted by a student. */
public class StudentPlayer extends PentagoPlayer {
	// constructor
    public StudentPlayer() {
        super("260740719");
    }
    // chooseMove
    public Move chooseMove(PentagoBoardState boardState) {
    		// choosing the winning move
        Move winMove = MyTools.getWin(boardState);
        if (winMove != null) {
    			return winMove;
        }
        // occupying the middle
        Move midMove = MyTools.getMiddle(boardState);
        if (midMove != null) {
        		return midMove;
        }
		// searching using minimax
		Map<Move, Integer> dictionary = new HashMap<Move, Integer>();
        for (Move a: boardState.getAllLegalMoves()) {
        		PentagoBoardState temp = (PentagoBoardState) boardState.clone();
        		dictionary.put(a, new Integer(0));
        		PentagoMove myMove = (PentagoMove) a;
    			temp.processMove(myMove);
    			Map<Move, Integer> dictionary2 = new HashMap<Move, Integer>();
    			for (Move b: temp.getAllLegalMoves()) {
    				PentagoBoardState temp2 = (PentagoBoardState) temp.clone();
    				PentagoMove yourMove = (PentagoMove) b;
    				temp2.processMove(yourMove);
        			int h = MyTools.heuristic(temp2);
    				dictionary2.put(b, h);
    			}
    			int minLose = Collections.min(dictionary2.values());
    			dictionary.put(a, minLose);
        }
		// choosing the most promising move
        int maxWin = Collections.max(dictionary.values());
        for (Entry<Move, Integer> b: dictionary.entrySet()) {
            if (b.getValue() == maxWin) {
                return b.getKey();
            }
        }
        return null;
    }
}

