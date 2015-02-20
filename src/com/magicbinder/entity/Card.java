package com.magicbinder.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

import com.tactfactory.harmony.annotation.Column;
import com.tactfactory.harmony.annotation.Column.Type;
import com.tactfactory.harmony.annotation.Entity;
import com.tactfactory.harmony.annotation.GeneratedValue;
import com.tactfactory.harmony.annotation.GeneratedValue.Strategy;
import com.tactfactory.harmony.annotation.Id;
import com.tactfactory.harmony.annotation.ManyToMany;
import com.tactfactory.harmony.annotation.OneToMany;


@Entity
public class Card  implements Serializable , Parcelable {

    /** Parent parcelable for parcellisation purposes. */
    protected List<Parcelable> parcelableParents;


    @Id
    @GeneratedValue(strategy=Strategy.MODE_NONE)
    @Column(type=Type.INT)
    private int id;
    
    @Column(type=Type.STRING)
    private String name;
    
    @Column(type=Type.STRING)
    private String image;
    
    @Column(type=Type.INT)
    private int convertedManaCost;
    
    @Column(type=Type.STRING)
    private String typeCard;
    
    @Column(type=Type.STRING)
    private String rarity;
    
    @Column(type=Type.STRING)
    private String cardSetId;
    
    @ManyToMany(targetEntity="Color",inversedBy="cards")
    @Column(nullable=true, hidden=true)
    private ArrayList<Color> colors;
    
    @OneToMany(targetEntity="Binder_Card",mappedBy="card")
    private ArrayList<Binder_Card> binders_cards_card;


    /**
     * Default constructor.
     */
    public Card() {

    }

    /**
     * @return the id
     */
    public int getId() {
         return this.id;
    }

    /**
     * @param value the id to set
     */
    public void setId(final int value) {
         this.id = value;
    }

    /**
     * @return the name
     */
    public String getName() {
         return this.name;
    }

    /**
     * @param value the name to set
     */
    public void setName(final String value) {
         this.name = value;
    }

    /**
     * @return the image
     */
    public String getImage() {
         return this.image;
    }

    /**
     * @param value the image to set
     */
    public void setImage(final String value) {
         this.image = value;
    }

    /**
     * @return the convertedManaCost
     */
    public int getConvertedManaCost() {
         return this.convertedManaCost;
    }

    /**
     * @param value the convertedManaCost to set
     */
    public void setConvertedManaCost(final int value) {
         this.convertedManaCost = value;
    }

    /**
     * @return the typeCard
     */
    public String getTypeCard() {
         return this.typeCard;
    }

    /**
     * @param value the typeCard to set
     */
    public void setTypeCard(final String value) {
         this.typeCard = value;
    }

    /**
     * @return the rarity
     */
    public String getRarity() {
         return this.rarity;
    }

    /**
     * @param value the rarity to set
     */
    public void setRarity(final String value) {
         this.rarity = value;
    }

    /**
     * @return the cardSetId
     */
    public String getCardSetId() {
         return this.cardSetId;
    }

    /**
     * @param value the cardSetId to set
     */
    public void setCardSetId(final String value) {
         this.cardSetId = value;
    }

    /**
     * @return the colors
     */
    public ArrayList<Color> getColors() {
         return this.colors;
    }

    /**
     * @param value the colors to set
     */
    public void setColors(final ArrayList<Color> value) {
         this.colors = value;
    }

    /**
     * @return the binders_cards_card
     */
    public ArrayList<Binder_Card> getBinders_cards_card() {
         return this.binders_cards_card;
    }

    /**
     * @param value the binders_cards_card to set
     */
    public void setBinders_cards_card(final ArrayList<Binder_Card> value) {
         this.binders_cards_card = value;
    }
    /**
     * This stub of code is regenerated. DO NOT MODIFY.
     * 
     * @param dest Destination parcel
     * @param flags flags
     */
    public void writeToParcelRegen(Parcel dest, int flags) {
        if (this.parcelableParents == null) {
            this.parcelableParents = new ArrayList<Parcelable>();
        }
        if (!this.parcelableParents.contains(this)) {
            this.parcelableParents.add(this);
        }
        dest.writeInt(this.getId());
        dest.writeString(this.getName());
        dest.writeString(this.getImage());
        dest.writeInt(this.getConvertedManaCost());
        dest.writeString(this.getTypeCard());
        dest.writeString(this.getRarity());
        dest.writeString(this.getCardSetId());

        if (this.getColors() != null) {
            dest.writeInt(this.getColors().size());
            for (Color item : this.getColors()) {
                if (!this.parcelableParents.contains(item)) {
                    item.writeToParcel(this.parcelableParents, dest, flags);
                } else {
                    dest.writeParcelable(null, flags);
                }
            }
        } else {
            dest.writeInt(-1);
        }

        if (this.getBinders_cards_card() != null) {
            dest.writeInt(this.getBinders_cards_card().size());
            for (Binder_Card item : this.getBinders_cards_card()) {
                if (!this.parcelableParents.contains(item)) {
                    item.writeToParcel(this.parcelableParents, dest, flags);
                } else {
                    dest.writeParcelable(null, flags);
                }
            }
        } else {
            dest.writeInt(-1);
        }
        this.parcelableParents = null;    
    }

    /**
     * Regenerated Parcel Constructor. 
     *
     * This stub of code is regenerated. DO NOT MODIFY THIS METHOD.
     *
     * @param parc The parcel to read from
     */
    public void readFromParcel(Parcel parc) {
        this.setId(parc.readInt());
        this.setName(parc.readString());
        this.setImage(parc.readString());
        this.setConvertedManaCost(parc.readInt());
        this.setTypeCard(parc.readString());
        this.setRarity(parc.readString());
        this.setCardSetId(parc.readString());

        int nbColors = parc.readInt();
        if (nbColors > -1) {
            ArrayList<Color> items =
                new ArrayList<Color>();
            for (int i = 0; i < nbColors; i++) {
                items.add((Color) parc.readParcelable(
                        Color.class.getClassLoader()));
            }
            this.setColors(items);
        }

        int nbBinders_cards_card = parc.readInt();
        if (nbBinders_cards_card > -1) {
            ArrayList<Binder_Card> items =
                new ArrayList<Binder_Card>();
            for (int i = 0; i < nbBinders_cards_card; i++) {
                items.add((Binder_Card) parc.readParcelable(
                        Binder_Card.class.getClassLoader()));
            }
            this.setBinders_cards_card(items);
        }
    }


    /**
     * Parcel Constructor.
     *
     * @param parc The parcel to read from
     */
    public Card(Parcel parc) {
        // You can chose not to use harmony's generated parcel.
        // To do this, remove this line.
        this.readFromParcel(parc);

        // You can  implement your own parcel mechanics here.

    }

    /* This method is not regenerated. You can implement your own parcel mechanics here. */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        // You can chose not to use harmony's generated parcel.
        // To do this, remove this line.
        this.writeToParcelRegen(dest, flags);
        // You can  implement your own parcel mechanics here.
    }

    /**
     * Use this method to write this entity to a parcel from another entity.
     * (Useful for relations)
     *
     * @param parents The entity being parcelled that need to parcel this one
     * @param dest The destination parcel
     * @param flags The flags
     */
    public synchronized void writeToParcel(List<Parcelable> parents, Parcel dest, int flags) {
        this.parcelableParents = new ArrayList<Parcelable>(parents);
        dest.writeParcelable(this, flags);
        this.parcelableParents = null;
    }

    @Override
    public int describeContents() {
        // This should return 0 
        // or CONTENTS_FILE_DESCRIPTOR if your entity is a FileDescriptor.
        return 0;
    }

    /**
     * Parcelable creator.
     */
    public static final Parcelable.Creator<Card> CREATOR
        = new Parcelable.Creator<Card>() {
        public Card createFromParcel(Parcel in) {
            return new Card(in);
        }
        
        public Card[] newArray(int size) {
            return new Card[size];
        }
    };
    /**
     * Return name of card.
     * @return Name of card.
     */
    @Override
    public String toString(){
        return this.name;
    }
    /**
     * Compare if cards are the same.
     * @param card to compare.
     * @return true if equals or false if not equals.
     */
    public boolean equalsCard(Card card){
        if(this.id == card.getId()){
            return true;
        }
        return false;
    }

}
