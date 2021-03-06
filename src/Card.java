/**
 * The class describing a single card.
 * ( it ignores the color type :> )
 */

public class Card{

    int id;

    public Card(String _name){
        id = name2id(_name);
    }

    public Card(int _id){
        id = _id;
    }

    public int id(){
        return id;
    }

    public String name(){
        return id2name(id);
    }

    static final String[] name_list = {
            "3","4","5","6","7","8","9","10",
            "J","Q","K","A","2",
            "Small-Joker","Big-Joker"
    };

    static int name2id(String name){
        for(int i=0; i<name_list.length; i++)
            if(name.equals(name_list[i]))
                return i;
        return -1;
    }

    static String id2name(int id){
        if(id>=0 && id<name_list.length)
            return name_list[id];
        return "BAD_CARD_ID";
    }

    public boolean isEqualTo(Card another) {
        return this.id() == another.id();
    }
}
