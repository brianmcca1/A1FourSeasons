package bmccarthy;

import ks.common.games.Solitaire;
import ks.common.model.Card;
import ks.common.model.Column;
import ks.common.model.Move;
import ks.common.model.Pile;

public class MoveCrossToFoundation extends Move{

	Column cross;
	Pile foundation;
	Card cardBeingDragged;

	public MoveCrossToFoundation(Column from, Card cardBeingDragged, Pile to){
		this.foundation = to;
		this.cross = from;
		this.cardBeingDragged = cardBeingDragged;
	}
	@Override
	public boolean doMove(Solitaire game) {
		if(!valid(game)){
			return false;
		}

		foundation.add(cardBeingDragged);
		game.updateScore(1);
		return true;
	}

	@Override
	public boolean undo(Solitaire game) {
		if(foundation.empty()){
			return false;
		}
		cross.add(foundation.get());
		game.updateScore(-1);
		return true;
	}
	@Override
	public boolean valid(Solitaire game) {
		boolean validation = false;
		FourSeasons theGame = (FourSeasons) game;
		if(foundation.empty()){
			if(cardBeingDragged.getRank() == theGame.firstFoundationRank){

				validation = true;
			}

		} else {
			if((foundation.rank() + 1 == cardBeingDragged.getRank()) & foundation.suit() == cardBeingDragged.getSuit()){
				validation = true;
			} else if((foundation.rank() == 13) & (cardBeingDragged.getRank() == 1) & (foundation.suit() == cardBeingDragged.getSuit())){
				validation = true;
			}
		}
		return validation;
	}

}
