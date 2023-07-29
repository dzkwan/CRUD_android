package com.dzakwan.finalproject.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
import com.apachat.swipereveallayout.core.SwipeLayout;
import com.apachat.swipereveallayout.core.ViewBinder;
import com.dzakwan.finalproject.AddEditActivity;
import com.dzakwan.finalproject.MainActivity;
import com.dzakwan.finalproject.R;
import com.dzakwan.finalproject.helper.DbHelperKaryawan;
import com.dzakwan.finalproject.model.DataDiriKaryawan;
import java.util.ArrayList;
import java.util.List;

public class RecyclerAdapter
  extends RecyclerView.Adapter<RecyclerAdapter.viewHolder> {

  Context ctx;
  public static final String TAG_ID = "id";
  public static final String TAG_ISEDIT = "isEdit";
  private List<DataDiriKaryawan> listItem;

  private ViewBinder viewBinderHelper = new ViewBinder();
  DbHelperKaryawan SQLkaryawan;

  private boolean showAllCheckbox = false;

  public RecyclerAdapter(Context ctx, List<DataDiriKaryawan> listItem) {
    this.ctx = ctx;

    this.listItem = listItem;
  }

  public void setShowAllCheckbox(boolean show) {
    this.showAllCheckbox = show;
    notifyItemRangeChanged(0, getItemCount());
  }

  public void notifymultipleremove(int posisi) {
    listItem.remove(posisi);
    notifyItemRemoved(posisi);
  }

  @NonNull
  @Override
  public viewHolder onCreateViewHolder(
    @NonNull ViewGroup parent,
    int viewType
  ) {
    View view = LayoutInflater
      .from(parent.getContext())
      .inflate(R.layout.list_row, parent, false);
    return new viewHolder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull viewHolder holder, int position) {
    SQLkaryawan = new DbHelperKaryawan(ctx);
    DataDiriKaryawan data = listItem.get(position);
    viewBinderHelper.bind(
      holder.swipeRevealLayout,
      String.valueOf(data.getId())
    );
    viewBinderHelper.setOpenOnlyOne(true);
    holder.tvKaryawan.setText(data.getNama());
    holder.tvDivisi.setText(data.getDivisi());
    holder.checkBox.setVisibility(showAllCheckbox ? View.VISIBLE : View.GONE);
    holder.checkBox.setChecked(!showAllCheckbox);
    holder.tvDelete.setOnClickListener(
      new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          viewBinderHelper.closeLayout(String.valueOf(data.getId()));

          showDialogKonfirmasiDelete(Integer.parseInt(data.getId()), holder);
        }
      }
    );
    holder.tvEdit.setOnClickListener(
      new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          Intent intent = new Intent(ctx, AddEditActivity.class);
          intent.putExtra(TAG_ID, data.getId());
          intent.putExtra(TAG_ISEDIT, 1);

          ctx.startActivity(intent);
        }
      }
    );
    holder.layoutItem.setOnClickListener(
      new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          Intent intent = new Intent(ctx, AddEditActivity.class);
          intent.putExtra(TAG_ID, data.getId());

          ctx.startActivity(intent);
        }
      }
    );
    holder.checkBox.setOnCheckedChangeListener(
      new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(
          CompoundButton buttonView,
          boolean isChecked
        ) {
          if (isChecked) {
            ((MainActivity) ctx).listIdDelete.add(
                Integer.parseInt(data.getId())
              );
            ((MainActivity) ctx).getposisicheckbox.add(
                holder.getAdapterPosition()
              );
            Log.d("add listiddelete", "" + ((MainActivity) ctx).listIdDelete);
            Log.d("get posisi", "" + ((MainActivity) ctx).getposisicheckbox);
          } else {
            ((MainActivity) ctx).listIdDelete.remove(
                Integer.valueOf(data.getId())
              );
            ((MainActivity) ctx).getposisicheckbox.remove(
                Integer.valueOf(holder.getAdapterPosition())
              );
            Log.d(
              "delete listiddelete",
              "" + ((MainActivity) ctx).listIdDelete
            );
            Log.d("delete posisi", "" + ((MainActivity) ctx).getposisicheckbox);
          }
        }
      }
    );
  }

  @Override
  public int getItemCount() {
    return listItem.size();
  }

  public void closeAllLayoutSwipe(View view) {
    if (!(view instanceof SwipeLayout)) {
      view.setOnTouchListener(
        new View.OnTouchListener() {
          @Override
          public boolean onTouch(View v, MotionEvent event) {
            Log.d("close swipe", "called");
            return false;
          }
        }
      );
    }
  }

  void showDialogKonfirmasiDelete(int id, viewHolder holder) {
    new AlertDialog.Builder(ctx)
      .setTitle("Delete Data")
      .setMessage("yakin ingin menghapus?")
      .setPositiveButton(
        "Yes",
        new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int which) {
            SQLkaryawan.delete(id);
            listItem.remove(holder.getAdapterPosition());
            notifyItemRemoved(holder.getAdapterPosition());
          }
        }
      )
      .setNegativeButton("No", null)
      .show();
  }

  public class viewHolder extends RecyclerView.ViewHolder {

    public SwipeLayout swipeRevealLayout;
    private View layoutItem;
    private CheckBox checkBox;

    private TextView tvKaryawan, tvDivisi, tvDelete, tvEdit;

    public viewHolder(@NonNull View itemView) {
      super(itemView);
      swipeRevealLayout = itemView.findViewById(R.id.swipeLayout);
      layoutItem = itemView.findViewById(R.id.layout_itemlist);
      checkBox = itemView.findViewById(R.id.checkbox);
      tvDelete = itemView.findViewById(R.id.tv_delete);
      tvEdit = itemView.findViewById(R.id.tv_edit);
      tvKaryawan = itemView.findViewById(R.id.tv_nama_listrow);
      tvDivisi = itemView.findViewById(R.id.tv_divisi_listrow);
    }
  }
}
