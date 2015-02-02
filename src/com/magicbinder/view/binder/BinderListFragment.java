/**************************************************************************
 * BinderListFragment.java, MagicBinder Android
 *
 * Copyright 2015
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Feb 2, 2015
 *
 **************************************************************************/
package com.magicbinder.view.binder;

import java.util.ArrayList;

import com.magicbinder.criterias.base.CriteriaExpression;
import com.magicbinder.menu.CrudCreateMenuWrapper.CrudCreateMenuInterface;
import com.magicbinder.provider.BinderProviderAdapter;
import com.magicbinder.provider.contract.BinderContract;
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
import com.magicbinder.entity.Binder;

/** Binder list fragment.
 *
 * This fragment gives you an interface to list all your Binders.
 *
 * @see android.app.Fragment
 */
public class BinderListFragment
        extends HarmonyListFragment<Binder>
        implements CrudCreateMenuInterface {

    /** The adapter which handles list population. */
    protected BinderListAdapter mAdapter;

    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {

        final View view =
                inflater.inflate(R.layout.fragment_binder_list,
                        null);

        this.initializeHackCustomList(view,
                R.id.binderProgressLayout,
                R.id.binderListContainer);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Give some text to display if there is no data.  In a real
        // application this would come from a resource.
        this.setEmptyText(
                getString(
                        R.string.binder_empty_list));

        // Create an empty adapter we will use to display the loaded data.
        ((PinnedHeaderListView) this.getListView())
                    .setPinnedHeaderEnabled(false);
        this.mAdapter = new BinderListAdapter(this.getActivity());

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
            result = new BinderListLoader(this.getActivity(),
                BinderProviderAdapter.BINDER_URI,
                BinderContract.ALIASED_COLS,
                crit,
                null);
        } else {
            result = new BinderListLoader(this.getActivity(),
                BinderProviderAdapter.BINDER_URI,
                BinderContract.ALIASED_COLS,
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
                BinderProviderAdapter.BINDER_URI);

        ArrayList<Binder> users = BinderContract.cursorToItems(data);
        this.mAdapter.setNotifyOnChange(false);
        this.mAdapter.setData(
                new BinderListAdapter
                    .BinderSectionIndexer(users));
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
                    BinderCreateActivity.class);
        this.startActivity(intent);
    }

}
