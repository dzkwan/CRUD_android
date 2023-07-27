package com.dzakwan.finalproject.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.dzakwan.finalproject.AddEditActivity;
import com.dzakwan.finalproject.R;
import com.dzakwan.finalproject.helper.DbHelperKaryawan;
import com.dzakwan.finalproject.model.DataDiriKaryawan;

import io.github.rexmtorres.android.swipereveallayout.SwipeRevealLayout;
import io.github.rexmtorres.android.swipereveallayout.ViewBinderHelper;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.viewHolder> {
    Context ctx;
    public static final String TAG_ID = "id";
    public static final String TAG_ISEDIT = "isEdit";
    private List<DataDiriKaryawan> listItem;
    private ViewBinderHelper viewBinderHelper = new ViewBinderHelper();
    DbHelperKaryawan SQLkaryawan;

    public RecyclerAdapter(
            Context ctx,
            List<DataDiriKaryawan> listItem
    ) {
        this.ctx = ctx;
        this.listItem = listItem;
    }


    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        SQLkaryawan = new DbHelperKaryawan(ctx);
        DataDiriKaryawan data = listItem.get(position);
        viewBinderHelper.bind(holder.swipeRevealLayout, String.valueOf(data.getId()));
        holder.tvKaryawan.setText(data.getNama());
        holder.tvDivisi.setText(data.getDivisi());
        holder.tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogKonfirmasiDelete(Integer.parseInt(data.getId()), holder);
            }
        });
        holder.tvEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ctx, AddEditActivity.class);
                intent.putExtra(TAG_ID, data.getId());
                intent.putExtra(TAG_ISEDIT, 1);
                ctx.startActivity(intent);

            }
        });
        holder.layoutItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ctx, AddEditActivity.class);
                intent.putExtra(TAG_ID, data.getId());
                ctx.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listItem.size();
    }

    void showDialogKonfirmasiDelete(int id, viewHolder holder) {
        new AlertDialog.Builder(ctx)
                .setTitle("Delete Data")
                .setMessage("yakin ingin menghapus?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SQLkaryawan.delete(id);
                        listItem.remove(holder.getAdapterPosition());
                        notifyItemRemoved(holder.getAdapterPosition());
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        private SwipeRevealLayout swipeRevealLayout;
        private View layoutItem;
        TextView tvKaryawan, tvDivisi, tvDelete, tvEdit;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            swipeRevealLayout = itemView.findViewById(R.id.swipeLayout);
            tvDelete = itemView.findViewById(R.id.tv_delete);
            tvEdit = itemView.findViewById(R.id.tv_edit);
            layoutItem = itemView.findViewById(R.id.layout_itemlist);
            tvKaryawan = itemView.findViewById(R.id.tv_nama_listrow);
            tvDivisi = itemView.findViewById(R.id.tv_divisi_listrow);
        }
    }
}
