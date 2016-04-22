package bmccarthy;

import static org.junit.Assert.*;

import java.awt.event.MouseEvent;

import org.junit.Test;

import ks.client.gamefactory.GameWindow;
import ks.common.model.Card;
import ks.common.model.Deck;
import ks.launcher.Main;
import ks.tests.KSTestCase;

public class TestDealToWastepile extends KSTestCase{

	@Test
	public void testDeal() {
		
		FourSeasons fourSeasons = new FourSeasons();
		GameWindow gw = Main.generateWindow(fourSeasons, Deck.OrderBySuit);
			
		Card topCard = fourSeasons.deck.peek();
		DealToWastepile dtw = new DealToWastepile(fourSeasons.deck, fourSeasons.wastepile);
			
		assertTrue(dtw.valid(fourSeasons));
		
		dtw.doMove(fourSeasons);
		
		assertEquals(45, fourSeasons.deck.count());
		assertEquals(topCard, fourSeasons.wastepile.peek());
		
		int value = fourSeasons.getNumLeft().getValue();
		
		assertEquals(45, value);
		
		dtw.undo(fourSeasons);
		
		assertEquals(46, fourSeasons.deck.count());
	
	}
	
	@Test
	public void testClick(){
		FourSeasons fourSeasons = new FourSeasons();
		GameWindow gw = Main.generateWindow(fourSeasons, Deck.OrderBySuit);
		
		MouseEvent pr = createPressed(fourSeasons, fourSeasons.deckView, 0, 0);
		fourSeasons.deckView.getMouseManager().handleMouseEvent(pr);
		
		assertEquals(new Card(Card.SEVEN, Card.SPADES), fourSeasons.wastepile.peek());
		assertTrue(fourSeasons.undoMove());
		assertTrue(fourSeasons.wastepile.empty());
		
	}

}
