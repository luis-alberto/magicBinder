package com.magicbinder.entity;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.List;
import java.io.Serializable;
import java.util.ArrayList;

import com.tactfactory.harmony.annotation.Column;
import com.tactfactory.harmony.annotation.Column.Type;
import com.tactfactory.harmony.annotation.Entity;
import com.tactfactory.harmony.annotation.GeneratedValue;
import com.tactfactory.harmony.annotation.GeneratedValue.Strategy;
import com.tactfactory.harmony.annotation.Id;
import com.tactfactory.harmony.annotation.ManyToMany;

@Entity
public class Color implements Serializable , Parcelable {

    /** Parent parcelable for parcellisation purposes. */
    protected List<Parcelable> parcelableParents;


    @Id
    @GeneratedValue(strategy=Strategy.MODE_IDENTITY)
    @Column(type=Type.INT,hidden=true)
    private int id;
    
    @Column(type=Type.STRING)
    private String label;
    
    @ManyToMany(targetEntity="Card", mappedBy="colors")
    @Column(nullable=true, hidden=true)
    private ArrayList<Card> cards;


    /**
     * Default constructor.
     */
    public Color() {

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
     * @return the label
     */
    public String getLabel() {
         return this.label;
    }

    /**
     * @param value the label to set
     */
    public void setLabel(final String value) {
         this.label = value;
    }

    /**
     * @return the cards
     */
    public ArrayList<Card> getCards() {
         return this.cards;
    }

    /**
     * @param value the cards to set
     */
    public void setCards(final ArrayList<Card> value) {
         this.cards = value;
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
        dest.writeString(this.getLabel());

        if (this.getCards() != null) {
            dest.writeInt(this.getCards().size());
            for (Card item : this.getCards()) {
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
        this.setLabel(parc.readString());

        int nbCards = parc.readInt();
        if (nbCards > -1) {
            ArrayList<Card> items =
                new ArrayList<Card>();
            for (int i = 0; i < nbCards; i++) {
                items.add((Card) parc.readParcelable(
                        Card.class.getClassLoader()));
            }
            this.setCards(items);
        }
    }


    /**
     * Parcel Constructor.
     *
     * @param parc The parcel to read from
     */
    public Color(Parcel parc) {
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
    public static final Parcelable.Creator<Color> CREATOR
        = new Parcelable.Creator<Color>() {
        public Color createFromParcel(Parcel in) {
            return new Color(in);
        }
        
        public Color[] newArray(int size) {
            return new Color[size];
        }
    };
    
    /**
     * Return label of color.
     * @return String Label of color.
     */
    @Override
    public String toString(){
        return this.label;
    }
    
    /**
     * Compare if colors are the same.
     * @param color to compare.
     * @return true if equals or false if not equals.
     */
    public boolean equalsColor(Color color){
        if(this.id == color.getId()){
            return true;
        }
        return false;
    }

}
