package com.magicbinder.entity;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.List;
import java.io.Serializable;
import java.util.ArrayList;

import com.google.common.base.Objects.ToStringHelper;
import com.tactfactory.harmony.annotation.Column;
import com.tactfactory.harmony.annotation.Column.Type;
import com.tactfactory.harmony.annotation.Entity;
import com.tactfactory.harmony.annotation.GeneratedValue;
import com.tactfactory.harmony.annotation.OneToMany;
import com.tactfactory.harmony.annotation.GeneratedValue.Strategy;
import com.tactfactory.harmony.annotation.Id;

@Entity
public class Binder implements Serializable , Parcelable {

    /** Parent parcelable for parcellisation purposes. */
    protected List<Parcelable> parcelableParents;


    @Id
    @GeneratedValue(strategy=Strategy.MODE_IDENTITY)
    @Column(type=Type.INT,hidden=true)
    private int id;
    
    @Column(type=Type.STRING)
    private String name;
    
    @OneToMany(targetEntity="Binder_Card",mappedBy="binder")
    @Column(nullable=true, hidden=true)
    private ArrayList<Binder_Card> binders_cards_binder;


    /**
     * Default constructor.
     */
    public Binder() {

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
     * @return the binders_cards_binder
     */
    public ArrayList<Binder_Card> getBinders_cards_binder() {
         return this.binders_cards_binder;
    }

    /**
     * @param value the binders_cards_binder to set
     */
    public void setBinders_cards_binder(final ArrayList<Binder_Card> value) {
         this.binders_cards_binder = value;
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

        if (this.getBinders_cards_binder() != null) {
            dest.writeInt(this.getBinders_cards_binder().size());
            for (Binder_Card item : this.getBinders_cards_binder()) {
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

        int nbBinders_cards_binder = parc.readInt();
        if (nbBinders_cards_binder > -1) {
            ArrayList<Binder_Card> items =
                new ArrayList<Binder_Card>();
            for (int i = 0; i < nbBinders_cards_binder; i++) {
                items.add((Binder_Card) parc.readParcelable(
                        Binder_Card.class.getClassLoader()));
            }
            this.setBinders_cards_binder(items);
        }
    }


    /**
     * Parcel Constructor.
     *
     * @param parc The parcel to read from
     */
    public Binder(Parcel parc) {
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
    public static final Parcelable.Creator<Binder> CREATOR
        = new Parcelable.Creator<Binder>() {
        public Binder createFromParcel(Parcel in) {
            return new Binder(in);
        }
        
        public Binder[] newArray(int size) {
            return new Binder[size];
        }
    };
    /**
     * Return name of binder.
     * @return Name of binder.
     */
    @Override
    public String toString(){
        return this.name;
    }
    
    /**
     * Compare if binders are the same.
     * @param binder to compare.
     * @return true if equals or false if not equals.
     */
    public boolean equalsBinder(Binder binder){
        if(this.id == binder.getId()){
            return true;
        }
        return false;
    }

}
