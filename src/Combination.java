import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

/**
 * This class describes and resolves the combination of card that player plays.
 */
public class Combination {
    int id;
    Scheme scheme;
    int len=0;
    Card[] cards;
    Player player;

    public Combination(){
        id=0;
        scheme = Scheme.Undefined;
    }

    public void printDetail(){
        String s=scheme.name()+": ";
        for (int i = 0; i < cards.length; i++) {
            s += cards[i].name()+", ";
        }
        System.out.println(s);
    }

    static public Combination read(Player current_player) {
        Combination comb = new Combination();
        Set<Card> selected_set = new HashSet<Card>();
        while (true) {
            int x = util.getInt("Enter the 【index】 you choose(0 to end)", 0, current_player.cards.size());
            if (x == 0) break;
            selected_set.add(current_player.cards.get(x - 1));
        }
        if(selected_set.isEmpty()) comb.cards = new Card[0];
        else comb.cards = selected_set.toArray(new Card[1]);
        return comb;
    }

    public void parse() {
        Arrays.sort(cards, Comparator.comparingInt(Card::id));
        if (cards.length == 0) {
            scheme = Scheme.Discard;
            id = 0;
        } else if (cards.length == 1) {
            scheme = Scheme.Single;
            id = cards[0].id();
        } else if (cards[0].id() == 13 && cards[1].id() == 14) {
            scheme = Scheme.Boom;
            id = 14;
        } else if (cards.length == 2 &&
                cards[0].isEqualTo(cards[1])) {
            id = cards[0].id();
            scheme = Scheme.Double;
        } else if (cards.length == 3 &&
                cards[0].isEqualTo(cards[2])) {
            id = cards[0].id();
            scheme = Scheme.Triple;
        } else if (cards.length == 4 &&
                cards[0].isEqualTo(cards[3])) {
            id = cards[0].id();
            scheme = Scheme.Boom;
        } else {

            if (isArray(cards)) {
                id = cards[0].id();
                scheme = Scheme.Array;
                len = cards.length;
            }
        }
    }

    public boolean isArray(Card[] cards) {
        int len = cards.length;
        if (len < 3 || cards[len - 1].id() > 13) return false;

        for (int i = 1; i < len; i++) {
            if (cards[i].id() != cards[i - 1].id() + 1) return false;
        }
        return true;
    }
}
