package bmccarthy;

import static org.junit.Assert.*;

import java.awt.event.MouseEvent;

import org.junit.Test;

import ks.client.gamefactory.GameWindow;
import ks.common.model.Card;
import ks.common.model.Deck;
import ks.launcher.Main;
import ks.tests.KSTestCase;

public class TestMoveToFoundation extends KSTestCase{
	
	@Test
	public void testWastepileToFoundation(){
		FourSeasons fourSeasons = new FourSeasons();
		GameWindow gw = Main.generateWindow(fourSeasons, Deck.OrderByRank);
		
		// Deal a card to the wastepile (Queen of Diamonds)
		MouseEvent pr = createPressed(fourSeasons, fourSeasons.deckView, 0, 0);
		fourSeasons.deckView.getMouseManager().handleMouseEvent(pr);
		
		assertEquals(fourSeasons.wastepile.count(), 1);
		assertEquals(fourSeasons.foundation[3].count(), 0);
		// Move the Queen of Diamonds from the wastepile to the second foundation spot
		pr = createPressed(fourSeasons, fourSeasons.wastePileView, 0, 0);
		fourSeasons.wastePileView.getMouseManager().handleMouseEvent(pr);
		
		MouseEvent rel = createReleased(fourSeasons, fourSeasons.foundationView[3], 0, 0);
		fourSeasons.foundationView[3].getMouseManager().handleMouseEvent(rel);
		
		assertEquals(fourSeasons.foundation[3].peek(), new Card(Card.QUEEN, Card.DIAMONDS));
		assertTrue(fourSeasons.undoMove());
		assertEquals(fourSeasons.foundation[3].count(), 0);
	}

	@Test
	public void testCrossToFoundation(){
		FourSeasons fourSeasons = new FourSeasons();
		GameWindow gw = Main.generateWindow(fourSeasons, Deck.OrderByRank);
		
		// Move the Queen of spades to the second foundation spot
		MouseEvent pr = createPressed(fourSeasons, fourSeasons.crossView[5], 0, 0);
		fourSeasons.crossView[5].getMouseManager().handleMouseEvent(pr);
		
		MouseEvent rel = createReleased(fourSeasons, fourSeasons.foundationView[2], 0, 0);
		fourSeasons.foundationView[2].getMouseManager().handleMouseEvent(rel);
		
		assertEquals(fourSeasons.foundation[2].count(), 1);
		assertTrue(fourSeasons.undoMove());
		
		// Move the King of hearts to the first foundation spot, which has the Queen of hearts
		pr = createPressed(fourSeasons, fourSeasons.crossView[2], 0, 0);
		fourSeasons.crossView[2].getMouseManager().handleMouseEvent(pr);
		
		rel = createReleased(fourSeasons, fourSeasons.foundationView[1], 0, 0);
		fourSeasons.foundationView[1].getMouseManager().handleMouseEvent(rel);
		
		assertEquals(fourSeasons.foundation[1].count(), 2);
		
		
	}

}
