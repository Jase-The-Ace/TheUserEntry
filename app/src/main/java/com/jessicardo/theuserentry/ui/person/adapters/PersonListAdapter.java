package com.jessicardo.theuserentry.ui.person.adapters;


import com.jessicardo.theuserentry.ui.common.adapters.JBaseAdapter;
import com.jessicardo.theuserentry.ui.common.interfaces.ListItemSelectedListener;
import com.jessicardo.theuserentry.ui.common.widgets.FooterProgressView;
import com.jessicardo.theuserentry.ui.person.viewmodel.PersonInfo;
import com.jessicardo.theuserentry.ui.person.widgets.PersonListRow;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


public class PersonListAdapter extends JBaseAdapter {

    public static final int VIEW_TYPE_LIST = 0;

    public static final int VIEW_TYPE_FOOTER = 1;

    private int mCurrentViewType = VIEW_TYPE_LIST;

    private final ArrayList<PersonInfo> mData;

    private ListItemSelectedListener mOnItemSelectedListener, mDeleteListener;

    private boolean mShowLoader;

    public PersonListAdapter(ArrayList<PersonInfo> data) {
        this.mData = data;
    }

    @Override
    public int getItemViewType(int position) {
        return getViewType(position);
    }

    private int getViewType(int position) {
        int type;
        if (isItemFooter(position)) {
            type = VIEW_TYPE_FOOTER;
        } else {
            type = mCurrentViewType;
        }
        return type;
    }

    private boolean isItemFooter(int position) {
        return mData.get(position) == null;
    }

    @Override
    public PersonListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
            int viewType) {
        View row = null;
        ViewHolder vh = null;
        switch (viewType) {
            case VIEW_TYPE_LIST:
                row = new PersonListRow(parent.getContext());
                vh = new ViewHolder(row);
                break;
            case VIEW_TYPE_FOOTER:
                row = new FooterProgressView(parent.getContext());
                vh = new ViewHolder(row, false);
                break;
        }

        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        ViewHolder holder = (ViewHolder) viewHolder;
        holder.bindData(position);
        switch (getViewType(position)) {
            case VIEW_TYPE_LIST:
                updateListRow(position, holder.getRow());
                break;
            case VIEW_TYPE_FOOTER:
                updateFooterRow(holder.getRow());
                break;
        }
    }

    public void updateListRow(int position, View rowView) {

        PersonListRow row = (PersonListRow) rowView;

        PersonInfo contact = mData.get(position);

        row.updateUI(contact, position);

        row.setDeleteListener(new ListItemSelectedListener() {
            @Override
            public void onListItemSelected(int position, View view) {
                if (mDeleteListener != null) {
                    mDeleteListener.onListItemSelected(position, view);
                }
            }
        });

    }

    public void updateFooterRow(View rowView) {
        FooterProgressView row = (FooterProgressView) rowView;

        row.toggleLoader(mShowLoader);
    }

    public void setOnItemSelectedListener(ListItemSelectedListener onItemSelectedListener) {
        mOnItemSelectedListener = onItemSelectedListener;
    }

    public void setDeleteListener(
            ListItemSelectedListener deleteListener) {
        mDeleteListener = deleteListener;
    }

    private void setShowLoader(boolean showLoader) {
        setShowLoader(showLoader, true);
    }

    private void setShowLoader(boolean showLoader, boolean notifyDataSetChanged) {
        mShowLoader = showLoader;
        if (notifyDataSetChanged) {
            notifyDataSetChanged();
        }
    }

    public void showLoader() {
        setShowLoader(true);
    }

    public void hideLoader() {
        setShowLoader(false);
    }

    public void hideLoader(boolean notifyDataSetChanged) {
        setShowLoader(false, notifyDataSetChanged);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public PersonInfo getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private class ViewHolder extends RecyclerView.ViewHolder {

        private View mRow;

        private int mPosition;

        public ViewHolder(View row) {
            this(row, true);
        }

        public ViewHolder(View row, boolean setListeners) {
            super(row);
            mRow = row;
            if (setListeners) {
                setListeners();
            }
        }

        public void bindData(int position) {
            mPosition = position;
        }

        public View getRow() {
            return mRow;
        }

        private void setListeners() {
            mRow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mOnItemSelectedListener != null) {
                        mOnItemSelectedListener.onListItemSelected(mPosition, mRow);
                    }
                }
            });
        }
    }
}
