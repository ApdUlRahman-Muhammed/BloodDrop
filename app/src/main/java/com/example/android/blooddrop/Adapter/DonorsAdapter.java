package com.example.android.blooddrop.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.blooddrop.Model.DataDonor;
import com.example.android.blooddrop.R;

import java.util.ArrayList;


public class DonorsAdapter extends RecyclerView.Adapter<DonorsAdapter.ViewHolderDonors> {

    public Context context;
    public ArrayList<DataDonor> donors;
    private ListItemClickListener mOnClickListener;

    public DonorsAdapter(ArrayList<DataDonor> donors, Context context, ListItemClickListener listener) {

        this.donors = donors;
        this.context = context;
        mOnClickListener = listener;

    }

    @Override
    public ViewHolderDonors onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.donors_items, null);
        DonorsAdapter.ViewHolderDonors viewHolder = new DonorsAdapter.ViewHolderDonors(itemLayoutView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolderDonors holder, int position) {
        DataDonor dataDonorModel = donors.get(position);


        holder.Name.setText(dataDonorModel.getDonorName());
        holder.Address.setText(dataDonorModel.getDonorAddress());
        holder.Phone.setText(dataDonorModel.getDonorPhone());
        holder.bloodType.setText(dataDonorModel.getDonorBloodType());

        holder.bind(donors.get(position), mOnClickListener);
    }

    @Override
    public int getItemCount() {
        return donors.size();
    }

    public void filterList(ArrayList<DataDonor> filterdNames) {
        this.donors = filterdNames;
        notifyDataSetChanged();
    }

    public interface ListItemClickListener {
        void onListItemClick(DataDonor clicked);
    }

    public class ViewHolderDonors extends RecyclerView.ViewHolder {
        public TextView Name, Address, Phone, bloodType;

        public ViewHolderDonors(View itemView) {
            super(itemView);
            Name = (TextView) itemView.findViewById(R.id.textViewName);
            Address = (TextView) itemView.findViewById(R.id.textViewAddress);
            Phone = (TextView) itemView.findViewById(R.id.textViewPhone);
            bloodType = (TextView) itemView.findViewById(R.id.textViewBlood);


        }

        public void bind(final DataDonor dataDonor, final ListItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onListItemClick(dataDonor);

                }
            });
        }
    }
}
