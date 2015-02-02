/**************************************************************************
 * CrudCreateMenuWrapper.java, MagicBinder Android
 *
 * Copyright 2015
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Feb 2, 2015
 *
 **************************************************************************/
package com.magicbinder.menu;


import android.content.Intent;
import android.support.v4.app.Fragment;

import android.support.v4.app.FragmentActivity;
import com.actionbarsherlock.internal.view.menu.ActionMenuItem;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

import com.magicbinder.R;

import com.magicbinder.menu.base.MenuWrapperBase;

/**
 * Menu wrapper for CRUD Create action.
 */
public class CrudCreateMenuWrapper implements MenuWrapperBase {
    /** Menu item ADD. */
    private MenuItem addItem;
    /** Menu Visibility. */
    private boolean visible = true;
    
    @Override
    public void initializeMenu(Menu menu, FragmentActivity activity,
            Fragment fragment, android.content.Context ctx) {
        
        if (fragment != null && fragment instanceof CrudCreateMenuInterface) {    
            
            this.addItem     = menu.add(
                    MagicBinderMenu.CRUDCREATE,
                    0,
                    Menu.NONE,
                    R.string.menu_item_create);
            this.addItem.setShowAsAction(
                    ActionMenuItem.SHOW_AS_ACTION_IF_ROOM
                    | ActionMenuItem.SHOW_AS_ACTION_WITH_TEXT);
            this.addItem.setVisible(false);
        }
    }

    @Override
    public void updateMenu(Menu menu, FragmentActivity activity,
            Fragment fragment, android.content.Context ctx) {
        if (fragment != null && fragment instanceof CrudCreateMenuInterface) {
            menu.setGroupVisible(
                    MagicBinderMenu.CRUDCREATE, this.visible);
        }
    }

    @Override
    public boolean dispatch(MenuItem item, android.content.Context ctx, Fragment fragment) {
        boolean result;
        if (fragment instanceof CrudCreateMenuInterface) {
            switch (item.getItemId()) {
                case 0:
                    ((CrudCreateMenuInterface) fragment).onClickAdd();
                    result = true;
                    break;
                default:
                    result = false;
                    break;
            }
        } else {
            result = false;
        }
        return result;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode,
            Intent data, android.content.Context ctx, Fragment fragment) {
        // We don't need this.
    }

    @Override
    public void clear(Menu menu, FragmentActivity activity,
            Fragment fragment, android.content.Context ctx) {

        if (fragment != null && fragment instanceof CrudCreateMenuInterface) {
            menu.removeGroup(MagicBinderMenu.CRUDCREATE);
        }
    }

    @Override
    public void hide(Menu menu, FragmentActivity activity, Fragment fragment,
            android.content.Context ctx) {
        this.visible = false;
    }

    @Override
    public void show(Menu menu, FragmentActivity activity, Fragment fragment,
            android.content.Context ctx) {
        this.visible = true;
    }

    /**
     * Implement this interface in your fragment or activity
     * to activate this menu.
     */
    public interface CrudCreateMenuInterface {
        /**
         * Called when user clicks on Add menu button.
         */
        void onClickAdd();
    }
}

