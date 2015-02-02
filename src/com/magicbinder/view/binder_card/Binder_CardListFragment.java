/**************************************************************************
 * Binder_CardListFragment.java, MagicBinder Android
 *
 * Copyright 2015
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Feb 2, 2015
 *
 **************************************************************************/
package com.magicbinder.view.binder_card;

import java.util.ArrayList;

import com.magicbinder.criterias.base.CriteriaExpression;
import com.magicbinder.menu.CrudCreateMenuWrapper.CrudCreateMenuInterface;
import com.magicbinder.provider.Binder_CardProviderAdapter;
import com.magicbinder.provider.contract.Binder_CardContract;
import com.magicbinder.harmony.view.HarmonyListFragment;
import com.google.android.pinnedheader.headerlist.PinnedHeaderListView;

import android.content.Intent;

import android.os.Bundle;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;


import com.magicbinder.R;
import com.magicbinder.entity.Binder_Card;

/** Binder_Card list fragment.
 *
 * This fragment gives you an interface to list all your Binder_Cards.
 *
 * @see android.app.Fragment
 */
public class Binder_CardListFragment
        extends HarmonyListFragment<Binder_Card>
        implements CrudCreateMenuInterface {

    /** The adapter which handles list population. */
    protected Binder_CardListAdapter mAdapter;

    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {

        final View view =
                inflater.inflate(R.layout.fragment_binder_card_list,
                        null);

        this.initializeHackCustomList(view,
                R.id.binder_cardProgressLayout,
                R.id.binder_cardListContainer);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Give some text to display if there is no data.  In a real
        // application this would come from a resource.
        this.setEmptyText(
                getString(
                        R.string.binder_card_empty_list));

        // Create an empty adapter we will use to display the loaded data.
        ((PinnedHeaderListView) this.getListView())
                    .setPinnedHeaderEnabled(false);
        this.mAdapter = new Binder_CardListAdapter(this.getActivity());

        // Start out with a progress indicator.
        this.setListShown(false);

        // Prepare the loader.  Either re-connect with an existing one,
        // or start a new one.
        getLoaderManager().initLoader(0, null, this);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        /* Do click action inside your fragment here. */
    }

    @Override
    public Loader<android.database.Cursor> onCreateLoader(int id, Bundle bundle) {
        Loader<android.database.Cursor> result = null;
        CriteriaExpression crit = null;
        if (bundle != null) {
            crit = (CriteriaExpression) bundle.get(
                        CriteriaExpression.PARCELABLE);
        }

        if (crit != null) {
            result = new Binder_CardListLoader(this.getActivity(),
                Binder_CardProviderAdapter.BINDER_CARD_URI,
                Binder_CardContract.ALIASED_COLS,
                crit,
                null);
        } else {
            result = new Binder_CardListLoader(this.getActivity(),
                Binder_CardProviderAdapter.BINDER_CARD_URI,
                Binder_CardContract.ALIASED_COLS,
                null,
                null,
                null);
        }
        return result;
    }

    @Override
    public void onLoadFinished(
            Loader<android.database.Cursor> loader,
            android.database.Cursor data) {

        // Set the new data in the adapter.
        data.setNotificationUri(this.getActivity().getContentResolver(),
                Binder_CardProviderAdapter.BINDER_CARD_URI);

        ArrayList<Binder_Card> users = Binder_CardContract.cursorToItems(data);
        this.mAdapter.setNotifyOnChange(false);
        this.mAdapter.setData(
                new Binder_CardListAdapter
                    .Binder_CardSectionIndexer(users));
        this.mAdapter.setNotifyOnChange(true);
        this.mAdapter.notifyDataSetChanged();
        this.mAdapter.setPinnedPartitionHeadersEnabled(false);
        this.mAdapter.setSectionHeaderDisplayEnabled(false);

        if (this.getListAdapter() == null) {
            this.setListAdapter(this.mAdapter);
        }

        // The list should now be shown.
        if (this.isResumed()) {
            this.setListShown(true);
        } else {
            this.setListShownNoAnimation(true);
        }

        super.onLoadFinished(loader, data);
    }

    @Override
    public void onLoaderReset(Loader<android.database.Cursor> loader) {
        // Clear the data in the adapter.
        this.mAdapter.clear();
    }
    @Override
    public void onClickAdd() {
        Intent intent = new Intent(this.getActivity(),
                    Binder_CardCreateActivity.class);
        this.startActivity(intent);
    }

}
