package com.youth.xframe.widget.dialog;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.youth.xframe.R;
import com.youth.xframe.adapter.ShareAdapter;
import com.youth.xframe.widget.XToast;


public class ShareDialogFragment extends BaseDialogFragment {

    public static ShareDialogFragment newInstance(Context context, String title, int height) {
        ShareDialogFragment dialogFragment = new ShareDialogFragment();
        dialogFragment.mContext = context;
        dialogFragment.setArguments(initArgs(title, height));

        return dialogFragment;
    }

    @Override
    public int setStyleId() {
        return R.style.CustomDialog;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_share, container,  false);
        initDialogTitle(view);
        GridView gridView = (GridView) view.findViewById(R.id.share_grid);
        TextView closeDialog = (TextView) view.findViewById(R.id.close);

        ShareAdapter adapter = new ShareAdapter(mContext);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new ShareItemClickListener(this));
        closeDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        return view;
    }

    private class ShareItemClickListener implements AdapterView.OnItemClickListener {
        private DialogFragment dialog;

        public ShareItemClickListener(DialogFragment dialog) {
            this.dialog = dialog;
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            TextView title = (TextView)(view.findViewById(R.id.share_title));
            XToast.success("分享成功!");
            dismiss();
        }
    }

    private void shareWeixin() {

    }

    private void shareFriendCircle() {

    }

    private void shareWeibo() {

    }

    private void shareQQ() {

    }

    private void shareQzone() {

    }
}
