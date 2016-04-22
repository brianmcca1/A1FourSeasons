package bmccarthy;

import static org.junit.Assert.*;

import java.awt.event.MouseEvent;

import org.junit.Test;

import ks.client.gamefactory.GameWindow;
import ks.common.model.Card;
import ks.common.model.Deck;
import ks.common.view.PileView;
import ks.launcher.Main;
import ks.tests.KSTestCase;

public class TestMoveToCross extends KSTestCase{

	@Test
	public void testMoveWasteToCross() {
		FourSeasons fourSeasons = new FourSeasons();
		GameWindow gw = Main.generateWindow(fourSeasons, Deck.OrderByRank);
		
		// Deal a card to the wastepile (deals Queen of Diamonds)
		MouseEvent pr = createPressed(fourSeasons, fourSeasons.deckView, 0, 0);
		fourSeasons.deckView.getMouseManager().handleMouseEvent(pr);
		
		// Move the Queen of Diamonds to the first cross, which has a King of Spades
		pr = createPressed(fourSeasons, fourSeasons.wastePileView, 0, 0);
		fourSeasons.wastePileView.getMouseManager().handleMouseEvent(pr);
		
		MouseEvent rel = createReleased(fourSeasons, fourSeasons.crossView[1], 0, 0);
		fourSeasons.crossView[1].getMouseManager().handleMouseEvent(rel);
		
		assertEquals(fourSeasons.cross[1].count(), 2);
		assertEquals(fourSeasons.cross[1].peek(), new Card(Card.QUEEN, Card.DIAMONDS));
		assertTrue(fourSeasons.wastepile.empty());
		
		// Deal the next card to the wastepile, Queen of Clubs
		pr = createPressed(fourSeasons, fourSeasons.deckView, 0, 0);
		fourSeasons.deckView.getMouseManager().handleMouseEvent(pr);
		// Attempt to move the Queen of Clubs to the fifth cross, which has the Queen of Spades
		MoveWastepileToCross mcm2 = new MoveWastepileToCross(fourSeasons.wastepile, fourSeasons.wastepile.peek(), fourSeasons.cross[5]);
		
		assertFalse(mcm2.doMove(fourSeasons));	
		assertEquals(fourSeasons.wastepile.count(), 1);
		mcm2 = new MoveWastepileToCross(fourSeasons.wastepile, fourSeasons.wastepile.peek(), fourSeasons.cross[2]);
		assertTrue(mcm2.doMove(fourSeasons));
		assertTrue(mcm2.undo(fourSeasons));
	}
	
	@Test
	public void testMoveCrossToCross(){
		FourSeasons fourSeasons = new FourSeasons();
		GameWindow gw = Main.generateWindow(fourSeasons, Deck.OrderByRank);
		
		MouseEvent pr = createPressed(fourSeasons, fourSeasons.crossView[5], 0, 0);
		fourSeasons.crossView[5].getMouseManager().handleMouseEvent(pr);
		
		MouseEvent rel = createReleased(fourSeasons, fourSeasons.crossView[1], 0, 0);
		fourSeasons.crossView[1].getMouseManager().handleMouseEvent(rel);
		
		assertEquals(fourSeasons.cross[1].peek(), new Card(Card.QUEEN, Card.SPADES));
		assertEquals(fourSeasons.cross[1].count(), 2);
		
		assertTrue(fourSeasons.undoMove());
		assertEquals(fourSeasons.cross[5].count(), 1);
		
		MoveCrossToCross ctc1 = new MoveCrossToCross(fourSeasons.cross[2], fourSeasons.cross[2].peek(), fourSeasons.cross[3]);
		assertFalse(ctc1.doMove(fourSeasons));
		MoveCrossToCross ctc2 = new MoveCrossToCross(fourSeasons.cross[5], fourSeasons.cross[5].peek(), fourSeasons.cross[2]);
		assertTrue(ctc2.doMove(fourSeasons));
		MoveCrossToCross ctc3 = new MoveCrossToCross(fourSeasons.cross[1], fourSeasons.cross[1].peek(), fourSeasons.cross[5]);
		ctc3.doMove(fourSeasons);
		assertEquals(fourSeasons.cross[5].count(), 1);
		
		
	}

}
