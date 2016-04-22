package bmccarthy;

import ks.common.games.Solitaire;
import ks.common.model.Card;
import ks.common.model.Column;
import ks.common.model.Move;


public class MoveCrossToCross extends Move{
	Column crossSource;
	Column crossTarget;
	Card cardBeingDragged;

	public MoveCrossToCross(Column from, Card cardBeingDragged, Column to){
		this.crossTarget = to;
		this.crossSource = from;
		this.cardBeingDragged = cardBeingDragged;
	}
	@Override
	public boolean doMove(Solitaire game) {
		if(!valid(game)){
			return false;
		}

		crossTarget.add(cardBeingDragged);
		
		return true;
	}

	@Override
	public boolean undo(Solitaire game) {
		if(crossTarget.empty()){
			return false;
		}
		crossSource.add(crossTarget.get());
	
		return true;
	}
	@Override
	public boolean valid(Solitaire game) {
boolean validation = false;
		if(crossTarget.empty()){
			validation = true;
		} else {
			if(crossTarget.rank() - 1 == cardBeingDragged.getRank()){
				validation = true;
			} else if ((crossTarget.rank() == 1  ) & (cardBeingDragged.getRank() == 13)){
				validation = true;
			}
		}
		return validation;
		
			
	     
	}
}
