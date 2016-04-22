package bmccarthy;



import ks.common.controller.SolitaireMouseMotionAdapter;
import ks.common.games.Solitaire;
import ks.common.games.SolitaireUndoAdapter;
import ks.common.model.Card;
import ks.common.model.Column;
import ks.common.model.Deck;
import ks.common.model.Pile;
import ks.common.view.CardImages;
import ks.common.view.ColumnView;
import ks.common.view.DeckView;
import ks.common.view.IntegerView;
import ks.common.view.PileView;
import ks.launcher.Main;

public class FourSeasons extends Solitaire{

	Deck deck;
	Column cross[] = new Column[6];
	Pile foundation[] = new Pile[5];
	Pile wastepile;
	int firstFoundationRank;
	DeckView deckView;
	ColumnView crossView[] = new ColumnView[6];
	PileView foundationView[] = new PileView[5];
	PileView wastePileView;
	IntegerView scoreView;
	IntegerView numLeftView;
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "bmccarthy-FourSeasons";
	}

	@Override
	public boolean hasWon() {
		return getScoreValue() == 52;
	}

	@Override
	public void initialize() {
		//

		// initialize model
		initializeModel(getSeed());
		initializeView();
		initializeControllers();

				// prepare game by dealing facedown cards to all columns, then one face up
		updateScore(1);
		updateNumberCardsLeft(46);
		
		for (int crossNum=1; crossNum <= 5; crossNum++) {

			// This one is face up.
			cross[crossNum].add (deck.get());
		}
		foundation[1].add(deck.get());
		this.firstFoundationRank = foundation[1].peek().getRank();
		
	
		
	}

	private void initializeControllers() {
	
		deckView.setMouseAdapter(new FourSeasonsDeckController (this, deck, wastepile));
		deckView.setMouseMotionAdapter (new SolitaireMouseMotionAdapter(this));
		deckView.setUndoAdapter (new SolitaireUndoAdapter(this));
		
		wastePileView.setMouseAdapter(new FourSeasonsWastePileController (this, wastePileView));
		wastePileView.setMouseMotionAdapter (new SolitaireMouseMotionAdapter(this));
		wastePileView.setUndoAdapter(new SolitaireUndoAdapter(this));
		
		for(int i = 1; i <= 5; i++){
			crossView[i].setMouseAdapter(new FourSeasonsCrossController (this, crossView[i]));
			crossView[i].setMouseMotionAdapter(new SolitaireMouseMotionAdapter(this));
			crossView[i].setUndoAdapter(new SolitaireUndoAdapter(this));
		}
		
		for(int i = 1; i <= 4; i++){
			foundationView[i].setMouseAdapter(new FourSeasonsFoundationController (this, foundationView[i]));
			foundationView[i].setMouseMotionAdapter(new SolitaireMouseMotionAdapter(this));
			foundationView[i].setUndoAdapter(new SolitaireUndoAdapter(this));			
		}
		
	}

	private void initializeView() {
		CardImages ci = getCardImages();
		
		deckView = new DeckView(deck);
		deckView.setBounds(20, 20, ci.getWidth(), ci.getHeight());
		container.addWidget(deckView);
		
		wastePileView = new PileView(wastepile);
		wastePileView.setBounds(20, 40 + ci.getHeight(), ci.getWidth(), ci.getHeight());
		container.addWidget(wastePileView);
		
		for (int crossNum = 1; crossNum <=5; crossNum++) {
			crossView[crossNum] = new ColumnView (cross[crossNum]);
			crossView[crossNum].setBounds (30*crossNum + 20 + (crossNum)*ci.getWidth(), ci.getHeight() + 40, ci.getWidth(), 13 * ci.getHeight());
			container.addWidget(crossView[crossNum]);
		}

		for(int foundationNum = 1; foundationNum <= 4; foundationNum++){
			foundationView[foundationNum] = new PileView(foundation[foundationNum]);
			foundationView[foundationNum].setBounds(30*foundationNum + 20 + foundationNum*ci.getWidth(), 20, ci.getWidth(), ci.getHeight());
			container.addWidget(foundationView[foundationNum]);
		}
		
		scoreView = new IntegerView (getScore());
		scoreView.setFontSize(14);
		scoreView.setBounds (120+7*ci.getWidth(), 20, 100, 60);
		container.addWidget (scoreView);

		numLeftView = new IntegerView (getNumLeft());
		numLeftView.setFontSize (14);
		numLeftView.setBounds (120 + 7*ci.getWidth(), 100, 100, 20);
		container.addWidget (numLeftView);
	}

	private void initializeModel(int seed) {
		// Develop deck
		deck = new Deck("d");
		deck.create(seed);
		model.addElement(deck);
		
		// Develop Cross piles
		for(int i = 1; i <= 5; i++){
			cross[i] = new Column("cross" + i);
			model.addElement(cross[i]);
		
		}
		
		// Develop Foundation piles
		for(int i = 1; i <= 4; i++){
			foundation[i] = new Pile("foundation" + i);
			model.addElement(foundation[i]);
		}
		
		// Develop Wastepile
		wastepile = new Pile("wastepile");
		model.addElement(wastepile);
	}
	 
	public static void main (String []args) {
		// Seed is to ensure we get the same initial cards every time.
		// Here the seed is to "order by suit."
		Main.generateWindow(new FourSeasons(), Deck.OrderBySuit);
	}

}
