import java.util.*;

public class Game {
    int nPlayers;
    Player current_player;
    List<Player> roundPlayers;
    List<Player> players = new ArrayList<Player>();
    List<Combination> history = new ArrayList<Combination>();
    Scanner sc;
    int latest_rank = 1;


    public Game() {
        sc = new Scanner(System.in);
    }

    public void start() {
        initPlayers();
        dealCards();

        while (latest_rank <= nPlayers - 1) {
            //till no one can take the same scheme of cards.
            playRound();
        }

        //set rank for the last player.
        for (int i = 0; i < nPlayers; i++) {
            Player p = players.get(i);
            if (p.rank == -1) p.rank = nPlayers;
        }

        showRank();
    }

    private void initPlayers() {
        nPlayers = util.getInt("Number of Players: ", 2, 4);
//        nPlayers=2;
        for (int i = 0; i < nPlayers; i++) {
            System.out.print("Name of Player #" + (i + 1) + ": ");
            String name = sc.next();

            Player p = new Player(i);
            p.name = name;
            players.add(p);
        }

        current_player = players.get((int) (Math.random() * nPlayers));

        System.out.println("===> Player " + current_player.name + " is selected to be first.");
    }

    private void dealCards() {
        List<Card> all_cards = new ArrayList<>();
        for (int id = 0; id < 13; id++)    //for 3~10 J Q K A 2
            for (int n = 0; n < 4; n++)    //4 card each
                all_cards.add(new Card(id));
        all_cards.add(new Card(13));    //joker
        all_cards.add(new Card(14));

        Collections.shuffle(all_cards);

        // divide equally
        for (int i = 0; i < nPlayers; i++) {
            for (int j = 0; j < 54 / nPlayers; j++) {
                players.get(i).cards.add(all_cards.get(0));
                all_cards.remove(0);
            }
        }

        // residuals
        for (int i = 0; i < all_cards.size(); i++) {
            players.get(i).cards.add(all_cards.get(i));
        }

        for (int i = 0; i < nPlayers; i++) {
            players.get(i).cards.sort(Comparator.comparingInt(Card::id));
//            players.get(i).showCards();
        }
    }

    void nextPlayer() {
        int id = current_player.id();
        if (id == nPlayers - 1) current_player = players.get(0);
        else current_player = players.get(id + 1);
    }

    private void informNextPlayer(Combination last_comb) {
        System.out.println("****************************************");

        for (int i = 0; i < history.size(); i++) {
            Combination comb = history.get(i);
            System.out.print(comb.player.name + " played: ");
            comb.printDetail();
        }

        System.out.println();
        System.out.println("===> It's 【" + current_player.name + "】's turn now!");
        System.out.println();
        System.out.println("==  Input Any Word to continue  ==");
        sc.next();

        current_player.showCards();
    }

    private void playRound() {
        boolean hasError=false;
        roundPlayers = new ArrayList<Player>();

        for (int i = 0; i < nPlayers; i++) {
            Player p = players.get(i);
            if (p.rank == -1) roundPlayers.add(p);
        }
        Combination last_comb = new Combination();

        while (roundPlayers.size() > 1) {
            if(!hasError) {
                informNextPlayer(last_comb);
            }

            Combination comb = Combination.read(current_player);
            comb.player = current_player;
            comb.parse();
            hasError = true;
            if (comb.scheme == Scheme.Undefined) {
                System.out.println("!!! Wrong Combination!(Any Word to Retry)");
                sc.next();
                continue;
            }
            if (comb.scheme == Scheme.Discard)
                if (last_comb.scheme == Scheme.Undefined) {
                    System.out.println("!!! Cannot Discard Now!(Any Word to Retry)");
                    sc.next();
                    continue;
                } else {
                    System.out.println("Discard Round.");
                    roundPlayers.remove(current_player);
                    nextPlayer();
                    continue;
                }

            //if first
            if (last_comb.scheme == Scheme.Undefined) {
                last_comb = comb;
                if (comb.scheme == Scheme.Boom) System.out.println("========BOOM========");
            }
            //if followed
            else if (comb.scheme == Scheme.Boom &&
                    (last_comb.scheme != Scheme.Boom || comb.id > last_comb.id)) {
                System.out.println();
                System.out.println("========BOOM========");
            } else if (last_comb.scheme != comb.scheme) {
                System.out.println("!!! Scheme not allowed!(Any Word to Retry)");
                sc.nextLine();
                continue;
            } else if (last_comb.scheme == Scheme.Array && last_comb.len != comb.len) {
                System.out.println("!!! Array length not equal!(Any Word to Retry)");
                sc.nextLine();
                continue;
            } else if (comb.id <= last_comb.id) {
                System.out.println("!!! Cannot play a smaller one!(Any Word to Retry)");
                sc.nextLine();
                continue;
            }

            current_player.cards.removeAll(Arrays.asList(comb.cards));
            if (current_player.cards.isEmpty()) {
                current_player.rank = latest_rank;
                roundPlayers.remove(current_player);
                latest_rank++;
                System.out.println("Congratulations " + current_player.name + "!");
                System.out.println("Your've got rank " + current_player.rank);
            }
            hasError=false;
            last_comb = comb;
            history.add(comb);
            nextPlayer();
            util.cls();
            continue;

        }
    }

    private void showRank() {

        players.sort(Comparator.comparingInt(Player::rank));

        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println("****************************************");
        for (int i = 0; i < nPlayers; i++) {
            Player p = players.get(i);
            System.out.println("【" + p.name + "】 is No." + p.rank);
        }
        System.out.println("****************************************");
        System.out.println();
        System.out.println();
        System.out.println();

    }

}
