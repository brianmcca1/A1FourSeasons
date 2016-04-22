package bmccarthy;

import ks.common.games.Solitaire;
import ks.common.model.Card;
import ks.common.model.Deck;
import ks.common.model.Move;
import ks.common.model.Pile;
/**
 * Move card from top of deck to top of wastepile
 * @author Brian
 *
 */

public class DealToWastepile extends Move{
	Deck deck;
	Pile wastepile;
	
	public DealToWastepile(Deck deck, Pile wastepile){
		super();
		this.deck = deck;
		this.wastepile = wastepile;
		
	}
	@Override
	public boolean doMove(Solitaire game) {
		// TODO Auto-generated method stub
		if(!valid(game)){
			return false;
		}
		
		Card card = deck.get();
		wastepile.add(card);
		game.updateNumberCardsLeft(-1);
		return true;
	}

	@Override
	public boolean undo(Solitaire game) {
		Card c = wastepile.get();
		deck.add(c);
		game.updateNumberCardsLeft(+1);
		return true;
	}

	@Override
	public boolean valid(Solitaire game) {
		// TODO Auto-generated method stub

		 return !deck.empty();
	     
	}
	     
}


