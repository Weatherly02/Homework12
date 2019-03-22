package edu.dmacc.codedsm.bjhw11;

public class Card {

    public String suit;
    public Integer value;


    public Card(String suit, Integer value) {
        this.suit = suit;
        this.value = value;
    }

    @Override
    public String toString() {
        return "Card{" +
                "suit='" + suit + '\'' +
                ", value=" + value +
                '}';
    }
//    public int card.value();
//    {
//        switch(card.value) {
//            card.value; "13":
//            card.value; "12":
//            card.value "11":
//                return 10;
//            default:
//                return Integer.parseInt(card.value);
//        }
//    }
//    /** The value of a Jack of any suit. */
//    public static void value "11" = new cardValue("10");
//
//    /** The value of a Queen of any suit. */
//    public static void value "12 = new cardValue(12);
//
//    /** The value of a King of any suit. */
//    public static void value "13" = new CardValue(13);


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Card card = (Card) o;

        if (suit != null ? !suit.equals(card.suit) : card.suit != null) return false;
        return value != null ? value.equals(card.value) : card.value == null;

    }

    @Override
    public int hashCode() {
        int result = suit != null ? suit.hashCode() : 0;
        result = 31 * result + (value != null ? value.hashCode() : 0);
        return result;
    }

}
