package bmccarthy;

import static org.junit.Assert.*;

import org.junit.Test;

import ks.client.gamefactory.GameWindow;
import ks.common.model.Deck;
import ks.launcher.Main;

public class TestInitialDeal {

	@Test
	public void testDeal() {
		FourSeasons fourSeasons = new FourSeasons();
		GameWindow gw = Main.generateWindow(fourSeasons, 123);
		
		assertFalse(fourSeasons.cross[1].empty());
		assertFalse(fourSeasons.cross[2].empty());
		assertFalse(fourSeasons.cross[3].empty());
		assertFalse(fourSeasons.cross[4].empty());
		assertFalse(fourSeasons.cross[5].empty());
		assertFalse(fourSeasons.foundation[1].empty());
		assertTrue(fourSeasons.foundation[2].empty());
		assertTrue(fourSeasons.foundation[3].empty());
		assertTrue(fourSeasons.foundation[4].empty());
		assertTrue(fourSeasons.wastepile.empty());
		assertEquals(fourSeasons.deck.count(), 46);
		
		gw.dispose();	
	}
}
