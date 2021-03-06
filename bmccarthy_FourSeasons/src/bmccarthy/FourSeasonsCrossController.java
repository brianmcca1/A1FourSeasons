package bmccarthy;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import heineman.Klondike;
import heineman.klondike.MoveCardToFoundationMove;
import heineman.klondike.MoveWasteToFoundationMove;
import ks.common.controller.SolitaireReleasedAdapter;
import ks.common.model.BuildablePile;
import ks.common.model.Card;
import ks.common.model.Column;
import ks.common.model.Move;
import ks.common.model.Pile;
import ks.common.view.BuildablePileView;
import ks.common.view.CardView;
import ks.common.view.ColumnView;
import ks.common.view.Container;
import ks.common.view.PileView;
import ks.common.view.Widget;

public class FourSeasonsCrossController extends SolitaireReleasedAdapter {
	/** The FourSeasons Game. */
	protected FourSeasons theGame;

	/** The specific Cross columnView being controlled. */
	protected ColumnView src;
	
	Move m;
	/**
	 * FoundationController constructor comment.
	 */
	public FourSeasonsCrossController(FourSeasons theGame, ColumnView cross) {
		super(theGame);

		this.theGame = theGame;
		this.src = cross;
	}
	/**
	 * Coordinate reaction to the completion of a Drag Event.
	 * <p>
	 * A bit of a challenge to construct the appropriate move, because cards
	 * can be dragged both from the WastePile (as a CardView object) and the 
	 * BuildablePileView (as a ColumnView).
	 * @param me java.awt.event.MouseEvent
	 */
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
		Column foundation = (Column) src.getModelElement();

		if(fromWidget instanceof PileView){
			// Coming from Wastepile
			Pile fromPile = (Pile) fromWidget.getModelElement();

			/** Must be the ColumnView widget being dragged. */
			CardView cardView = (CardView) draggingWidget;
			Card card = (Card) cardView.getModelElement();
			if (card == null) {
				System.err.println ("CrossController::mouseReleased(): somehow ColumnView model element is null.");
				c.releaseDraggingObject();			
				return;
			}

			m = new MoveWastepileToCross(fromPile, card, foundation);
		} else {
			// Coming from cross
			Column fromColumn = (Column) fromWidget.getModelElement();
			
			/** Must be the ColumnView widget being dragged. */
			CardView cardView = (CardView) draggingWidget;
			Card card = (Card) cardView.getModelElement();
			if (card == null) {
				System.err.println ("CrossController::mouseReleased(): somehow ColumnView model element is null.");
				c.releaseDraggingObject();			
				return;
			}

			m = new MoveCrossToCross(fromColumn, card, foundation);
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
	
	public void mousePressed(MouseEvent me){
		// The container manages several critical pieces of information; namely, it
				// is responsible for the draggingObject; in our case, this would be a CardView
				// Widget managing the card we are trying to drag between two piles.
				Container c = theGame.getContainer();
				
				/** Return if there is no card to be chosen. */
				Column cross = (Column) src.getModelElement();
				if (cross.count() == 0) {
					c.releaseDraggingObject();
					return;
				}
			
				// Get a card to move from PileView. Note: this returns a CardView.
				// Note that this method will alter the model for PileView if the condition is met.
				CardView cardView = src.getCardViewForTopCard (me);
				
				// an invalid selection of some sort.
				if (cardView == null) {
					c.releaseDraggingObject();
					return;
				}
				
				// If we get here, then the user has indeed clicked on the top card in the PileView and
				// we are able to now move it on the screen at will. For smooth action, the bounds for the
				// cardView widget reflect the original card location on the screen.
				Widget w = c.getActiveDraggingObject();
				if (w != Container.getNothingBeingDragged()) {
					System.err.println ("CrossController::mousePressed(): Unexpectedly encountered a Dragging Object during a Mouse press.");
					return;
				}
			
				// Tell container which object is being dragged, and where in that widget the user clicked.
				c.setActiveDraggingObject (cardView, me);
				
				// Tell container which source widget initiated the drag
				c.setDragSource (src);
			
				// The only widget that could have changed is ourselves. If we called refresh, there
				// would be a flicker, because the dragged widget would not be redrawn. We simply
				// force the WastePile's image to be updated, but nothing is refreshed on the screen.
				// This is patently OK because the card has not yet been dragged away to reveal the
				// card beneath it.  A bit tricky and I like it!
				src.redraw();
	}
}

