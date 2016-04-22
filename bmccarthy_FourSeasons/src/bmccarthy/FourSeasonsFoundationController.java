package bmccarthy;

import java.awt.event.MouseEvent;

import ks.common.controller.SolitaireReleasedAdapter;
import ks.common.games.Solitaire;
import ks.common.model.Card;
import ks.common.model.Column;
import ks.common.model.Move;
import ks.common.model.Pile;
import ks.common.view.CardView;
import ks.common.view.ColumnView;
import ks.common.view.Container;
import ks.common.view.PileView;
import ks.common.view.Widget;

public class FourSeasonsFoundationController extends SolitaireReleasedAdapter {

	protected FourSeasons theGame;
	
	protected PileView src;
	
	Move m;
	
	public FourSeasonsFoundationController(FourSeasons theGame, PileView foundation) {
		super(theGame);
		
		this.theGame = theGame;
		this.src = foundation;		
	}
	
	public void mouseReleased(MouseEvent me) {
		Container c = theGame.getContainer();

		/** Return if there is no card being dragged chosen. */
		Widget draggingWidget = c.getActiveDraggingObject();
		if (draggingWidget == Container.getNothingBeingDragged()) {
			System.err.println ("FoundationController::mouseReleased() unexpectedly found nothing being dragged.");
			c.releaseDraggingObject();		
			return;
		}

		/** Recover the from BuildablePile OR waste Pile */
		Widget fromWidget = c.getDragSource();
		if (fromWidget == null) {
			System.err.println ("FoundationController::mouseReleased(): somehow no dragSource in container.");
			c.releaseDraggingObject();
			return;
		}

		
		// Determine the To Pile
		Pile foundation = (Pile) src.getModelElement();

		if(fromWidget instanceof ColumnView){
			// Card is coming from the Cross
			Column fromColumn = (Column) fromWidget.getModelElement();
			
			CardView cardView = (CardView) draggingWidget;
			Card card = (Card) cardView.getModelElement();
			if (card == null) {
				System.err.println ("FoundationController::mouseReleased(): somehow ColumnView model element is null.");
				c.releaseDraggingObject();			
				return;
			}
			
			m = new MoveCrossToFoundation(fromColumn, card, foundation);
			
		} else {
			// Card is coming from the wastepile
			Pile fromPile = (Pile) fromWidget.getModelElement();

			/** Must be the ColumnView widget being dragged. */
			CardView cardView = (CardView) draggingWidget;
			Card card = (Card) cardView.getModelElement();
			if (card == null) {
				System.err.println ("FoundationController::mouseReleased(): somehow ColumnView model element is null.");
				c.releaseDraggingObject();			
				return;
			}

		
			m = new MoveWastepileToFoundation(fromPile, card, foundation);
		}

		if (m.doMove (theGame)) {
			// Success
			theGame.pushMove (m);
		} else {
			fromWidget.returnWidget (draggingWidget);
		}

		// release the dragging object, (this will reset dragSource)
		c.releaseDraggingObject();

		// finally repaint
		c.repaint();
	}

}
