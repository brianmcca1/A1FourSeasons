package bmccarthy;

import ks.common.games.Solitaire;
import ks.common.model.Card;
import ks.common.model.Column;
import ks.common.model.Deck;
import ks.common.model.Move;
import ks.common.model.Pile;
/**
 * Move card from top of wastepile to top of Cross column
 * @author Brian
 *
 */

public class MoveWastepileToCross extends Move{
	Card cardBeingDragged;
	Pile wastepile;
	Column cross;
	
	public MoveWastepileToCross(Pile from, Card cardBeingDragged, Column to){
		
		this.wastepile = from;
		this.cardBeingDragged = cardBeingDragged;
		this.cross = to;		
		
	}
	@Override
	public boolean doMove(Solitaire game) {
		// TODO Auto-generated method stub
		if(!valid(game)){
			return false;
		}
		
		cross.add(cardBeingDragged);
		return true;
	}

	@Override
	public boolean undo(Solitaire game) {
		if(cross.empty()){
			return false;
		}
		wastepile.add(cross.get());
		return true;
	}

	@Override
	public boolean valid(Solitaire game) {
		// TODO Auto-generated method stub
		
		boolean validation = false;
		
		if(cross.empty()){
			validation = true;
		} else {
			if(cross.rank() - 1 == cardBeingDragged.getRank()){
				validation = true;
			} else if ((cross.rank() == 1  ) & (cardBeingDragged.getRank() == 13)){
				validation = true;
			}
		}
		return validation;
		
			
	     
	}
	     
}


