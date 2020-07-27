package com.ktdcl.revenuevillage.ui.revenue_app;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.ktdcl.revenuevillage.R;
import com.ktdcl.revenuevillage.model.DistrictModel;
import com.ktdcl.revenuevillage.model.TalukModel;
import com.ktdcl.revenuevillage.model.VillageModel;

import java.util.List;

public class NotificationsFragment extends Fragment {
    private Spinner spinnerAc, spinnerDist, spinnerTanda,spinnerAppDist, spinnerOppDist, spinnerAppTaluk, spinnerOppTaluk, spinnerAppVillage, spinnerOppVillage;
    private Spinner spinnerTaluk;
    private View mView;
    private List<DistrictModel> districtModelList;
    private List<TalukModel> talukModelList;
    private List<VillageModel> villageModelList;

    private List<String> mDistrictNamesList;
    private List<String> mAssemblyNamesList;
    private List<String> mTalukNames;
    private List<String> mTandaNames;
    private Button addSign, addPreview;
    private int[] counts = new int[100];
    private int count=1000;
    private LinearLayout signLyout;

    private String dist=null, taluk=null, village=null, tandaRes = "yes", desiredJob=null, qual=null;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);
        spinnerTaluk = root.findViewById(R.id.sp_general_taluk);
        spinnerDist = root.findViewById(R.id.sp_general_dist);
        spinnerAppVillage = root.findViewById(R.id.sp_general_village);
        spinnerTanda = root.findViewById(R.id.sp_general_tanda);
        addSign = root.findViewById(R.id.addSign);
        addPreview = root.findViewById(R.id.preview);
        signLyout = root.findViewById(R.id.signLayout);
        initData();
        return root;
    }

    private void initData() {
        addSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addSignLayout();
            }
        });
    }

    private void addSignLayout()
    {
        LinearLayout parent = new LinearLayout(getContext());

        parent.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        parent.setOrientation(LinearLayout.HORIZONTAL);
        EditText textView = new EditText(getContext());
        textView.setHint("Name");
        textView.setId(count);
        count++;

        ImageView imageView = new ImageView(getContext());
        imageView.setId(count);
        count++;

        Button button = new Button(getContext());
        button.setId(count);
        button.setText("Capture sign photo");

        parent.addView(textView);
        parent.addView(button);
        parent.addView(imageView);

        signLyout.addView(parent);
    }
}
