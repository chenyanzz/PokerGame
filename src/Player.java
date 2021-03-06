import java.util.ArrayList;
import java.util.List;

/**
 * This class describes the info of a player.
 */

public class Player {
    private int id;
    String name;
    List<Card> cards;

    int rank = -1;

    public Player(int _id) {
        id = _id;
        name = "Player-" + id;
        cards = new ArrayList<Card>();
    }

    int id() {
        return id;
    }

    int rank(){
        return rank;
    }

    public void showCards() {
//        System.out.println("===> Player Name: " + name);
        if (cards.size() == 0) {
            System.out.println("Cards Cleared!!");
            return;
        }

        System.out.println("===> Cards Remain: ");
        final int CARDS_PER_LINE = 6;
        for (int i = 0; i < cards.size(); i++) {
            System.out.print("[" + ((i + 1) > 9 ? "" : " ") + (i + 1) + "]" + cards.get((i)).name() + "\t");

            if ((i+1) % CARDS_PER_LINE == 0) System.out.println();
        }

        System.out.println();
        System.out.println();
    }
}
