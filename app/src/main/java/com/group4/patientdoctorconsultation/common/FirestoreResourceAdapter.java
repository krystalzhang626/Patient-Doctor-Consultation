package com.group4.patientdoctorconsultation.common;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;
import java.util.Objects;

public abstract class FirestoreResourceAdapter<T, V extends ViewDataBinding>
        extends RecyclerView.Adapter<FirestoreResourceViewHolder<V>> {

    protected final ClickListener<T> clickListener;
    private List<T> listItems;

    public FirestoreResourceAdapter(final ClickListener<T> listener) {
        this.clickListener = Objects.requireNonNull(listener);
    }

    @Override
    public final FirestoreResourceViewHolder<V> onCreateViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        V binding = createBinding(inflater, parent);
        return new FirestoreResourceViewHolder<>(binding);
    }

    @Override
    public final void onBindViewHolder(FirestoreResourceViewHolder<V> holder, int position) {
        bind(holder.binding, listItems.get(position));
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return listItems == null ? 0 : listItems.size();
    }

    public void replaceListItems(List<T> newList){
        listItems = newList;
        notifyDataSetChanged();
    }

    protected abstract V createBinding(LayoutInflater inflater, ViewGroup parent);
    protected abstract void bind(V binding, T item);

}
