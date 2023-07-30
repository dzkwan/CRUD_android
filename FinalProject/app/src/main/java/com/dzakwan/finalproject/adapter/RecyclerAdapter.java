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
  private boolean closeLayoutSwipe = false;

  public RecyclerAdapter(Context ctx, List<DataDiriKaryawan> listItem) {
    this.ctx = ctx;
    this.listItem = listItem;
  }

  // function untuk menampilkan checkbox recyclerview
  public void setShowAllCheckbox(boolean show) {
    this.showAllCheckbox = show;
    notifyItemRangeChanged(0, getItemCount());
  }

  // function untuk close swipe layout
  public void closeLayoutSwipe(boolean close) {
    this.closeLayoutSwipe = close;
    notifyItemRangeChanged(0, getItemCount());
  }

  // function notify multiple delete
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

    // binding swipereveallayout
    viewBinderHelper.bind(
      holder.swipeRevealLayout,
      String.valueOf(data.getId())
    );

    // only open satu swipe layout
    viewBinderHelper.setOpenOnlyOne(true);

    // lock swipe ketika checkbox show
    if (showAllCheckbox) {
      holder.swipeRevealLayout.setLockDrag(true);
    }

    // close swipe layout ketika click menu
    if (closeLayoutSwipe) {
      holder.swipeRevealLayout.close(true);
    }

    holder.tvKaryawan.setText(data.getNama());
    holder.tvDivisi.setText(data.getDivisi());
    holder.checkBox.setVisibility(showAllCheckbox ? View.VISIBLE : View.GONE);
    
    // button delete listitem
    holder.tvDelete.setOnClickListener(
      new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          viewBinderHelper.closeLayout(String.valueOf(data.getId()));
          showDialogKonfirmasiDelete(Integer.parseInt(data.getId()), holder);
        }
      }
    );

    // button edit listitem
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

    // click on listitem for detail
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

    // checkbox change listener
    holder.checkBox.setOnCheckedChangeListener(
      new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(
          CompoundButton buttonView,
          boolean isChecked
        ) {
          
          // masukkan item id data dan posisi kedalam list baru
          if (isChecked) {
            ((MainActivity) ctx).listIdDelete.add(
                Integer.parseInt(data.getId())
              );
            ((MainActivity) ctx).getposisicheckbox.add(
                holder.getAdapterPosition()
              );
          } else {
            ((MainActivity) ctx).listIdDelete.remove(
                Integer.valueOf(data.getId())
              );
            ((MainActivity) ctx).getposisicheckbox.remove(
                Integer.valueOf(holder.getAdapterPosition())
              );
          }
          
          // show delete icon when checkbox terpilih
          if (((MainActivity) ctx).listIdDelete.isEmpty()) {
            ((MainActivity) ctx).menu.findItem(R.id.icondelete)
              .setVisible(false);
          } else {
            ((MainActivity) ctx).menu.findItem(R.id.icondelete)
              .setVisible(true);
          }
        }
      }
    );
  }

  @Override
  public int getItemCount() {
    return listItem.size();
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
