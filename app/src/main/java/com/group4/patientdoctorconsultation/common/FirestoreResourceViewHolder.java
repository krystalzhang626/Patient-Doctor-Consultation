package com.group4.patientdoctorconsultation.common;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;

class FirestoreResourceViewHolder<T extends ViewDataBinding> extends RecyclerView.ViewHolder {
    final T binding;

    FirestoreResourceViewHolder(T binding) {
        super(binding.getRoot());
        this.binding = binding;
    }
}
