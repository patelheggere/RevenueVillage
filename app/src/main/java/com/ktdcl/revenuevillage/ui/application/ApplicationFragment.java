package com.ktdcl.revenuevillage.ui.application;

import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ktdcl.revenuevillage.R;
import com.ktdcl.revenuevillage.model.DistrictModel;
import com.ktdcl.revenuevillage.model.RevenueData;
import com.ktdcl.revenuevillage.model.TalukModel;
import com.ktdcl.revenuevillage.model.VillageModel;
import com.ktdcl.revenuevillage.network.ApiInterface;
import com.ktdcl.revenuevillage.network.RetrofitInstance;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApplicationFragment extends Fragment {
    private HomeViewModel homeViewModel;
    private Spinner spinnerAc, spinnerDist, spinnerAcDist,spinnerAppDist, spinnerOppDist, spinnerAppTaluk, spinnerOppTaluk, spinnerAppVillage, spinnerOppVillage;
    private Spinner spinnerTaluk;
    private View mView;
    private List<DistrictModel> districtModelList;
    private List<TalukModel> talukModelList;
    private List<VillageModel> villageModelList;

    private List<String> mDistrictNamesList;
    private List<String> mAssemblyNamesList;
    private List<String> mTalukNames;
    private List<String> mTandaNames;
    private String dist=null, taluk=null, village=null, tandaRes = "yes", desiredJob=null, qual=null;
    private EditText editTextAppName, editTextOppName, editTextAppFatherName, editTextOppFatherName, editTextAppAge, editTextOppAge,
    editTextAppAdhar, editTextOppAdhar,editTextAppProfession, editTextOppProfession, editTextAppOtherVillage, editTextOppOtherVillage;
    private String appDist, appTaluk, appVillage, oppDist, oppTaluk, oppVillage, appOthers, oppOthers;
    private EditText newVillage, editTextSurveyNo, editTextHissa, editTextTax, editTextEstimatedValue, editTextMoolaGrama;
    private EditText editTextResDimen, editTextResNorth, editTextResSouth, editTextResEast, editTextResWest;
    private EditText editTextKottigeDimen, editTextKottigeNorth, editTextKottigeSouth, editTextKottigeEast, editTextKottigeWest;
    private EditText editTextToolsDimen, editTextToolsNorth, editTextToolsSouth, editTextToolsEast, editTextToolsWest;
    private EditText editTextOthersDimen, editTextOthersNorth, editTextOthersSouth, editTextOthersEast, editTextOthersWest;
    private EditText editTextTenure;
    private Button buttonSubmit;
    private String ac, acDist;
    private TextView totalDimen;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_home, container, false);
        initViews();
        return mView;
    }

    private void initViews() {

        editTextMoolaGrama = mView.findViewById(R.id.moola_gram);
        editTextAppOtherVillage = mView.findViewById(R.id.et_app_other_village);
        editTextOppOtherVillage = mView.findViewById(R.id.et_opp_other_village);

        editTextTenure = mView.findViewById(R.id.et_tenure);
        editTextResDimen = mView.findViewById(R.id.et_res_dimension);
        editTextResNorth = mView.findViewById(R.id.res_north);
        editTextResSouth = mView.findViewById(R.id.res_south);
        editTextResEast = mView.findViewById(R.id.res_east);
        editTextResWest = mView.findViewById(R.id.res_west);

        editTextKottigeDimen = mView.findViewById(R.id.et_kottige_dimen);
        editTextKottigeNorth = mView.findViewById(R.id.et_kottige_north);
        editTextKottigeSouth = mView.findViewById(R.id.et_kottige_south);
        editTextKottigeEast = mView.findViewById(R.id.et_kottige_east);
        editTextKottigeWest = mView.findViewById(R.id.et_kottige_west);

        editTextToolsDimen = mView.findViewById(R.id.et_tools_dimen);
        editTextToolsNorth = mView.findViewById(R.id.et_tools_north);
        editTextToolsSouth = mView.findViewById(R.id.et_tools_south);
        editTextToolsWest = mView.findViewById(R.id.et_tools_west);
        editTextToolsEast = mView.findViewById(R.id.et_tools_east);

        editTextOthersDimen = mView.findViewById(R.id.et_others_dimen);
        editTextOthersNorth = mView.findViewById(R.id.et_others_north);
        editTextOthersSouth = mView.findViewById(R.id.et_others_south);
        editTextOthersEast = mView.findViewById(R.id.et_others_east);
        editTextOthersWest = mView.findViewById(R.id.et_others_west);

        newVillage = mView.findViewById(R.id.et_new_name);
        editTextSurveyNo = mView.findViewById(R.id.et_survey_no);
        editTextHissa = mView.findViewById(R.id.et_hissa);
        editTextTax = mView.findViewById(R.id.et_tax);
        editTextEstimatedValue = mView.findViewById(R.id.et_estimate);

        editTextAppName = mView.findViewById(R.id.et_app_name);
        editTextAppFatherName = mView.findViewById(R.id.et_app_father_name);
        editTextAppAdhar = mView.findViewById(R.id.et_app_adhaar);
        editTextAppAge = mView.findViewById(R.id.et_app_age);
        editTextAppProfession = mView.findViewById(R.id.et_app_profession);

        editTextOppName = mView.findViewById(R.id.et_opp_name);
        editTextOppFatherName = mView.findViewById(R.id.et_opp_father);
        editTextOppAdhar = mView.findViewById(R.id.et_opp_adhar);
        editTextOppAge = mView.findViewById(R.id.et_opp_age);
        editTextOppProfession = mView.findViewById(R.id.et_opp_profession);

        editTextMoolaGrama = mView.findViewById(R.id.moola_gram);

        spinnerAc = mView.findViewById(R.id.ac_spinner);
        spinnerAcDist = mView.findViewById(R.id.sp_ac_dist);
        spinnerAppDist = mView.findViewById(R.id.sp_app_dist);
        spinnerOppDist = mView.findViewById(R.id.sp_opp_dist);

        spinnerAppTaluk = mView.findViewById(R.id.sp_app_taluk);
        spinnerOppTaluk = mView.findViewById(R.id.sp_opp_taluk);

        spinnerTaluk = mView.findViewById(R.id.sp_taluk);
        spinnerDist = mView.findViewById(R.id.sp_general_dist);

        spinnerAppVillage = mView.findViewById(R.id.sp_app_village);
        spinnerOppVillage = mView.findViewById(R.id.sp_opp_village);

        buttonSubmit = mView.findViewById(R.id.submit);
        totalDimen= mView.findViewById(R.id.total_dimen);

        initListener();
        setUpNetwork();
        getDistricts();
    }

    private void initListener() {
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitDetails();
            }
        });

        spinnerAc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position!=0)
                {
                    ac = districtModelList.get(position-1).getDist_name();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerAppDist.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //  product = mDistrictNamesList.get(position);
                if(position!=0)
                {
                    appDist = districtModelList.get(position-1).getDist_name();
                    getTaluks(districtModelList.get(position-1).getDist_id(), 1);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinnerAcDist.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //  product = mDistrictNamesList.get(position);
                if(position!=0)
                {
                    acDist = districtModelList.get(position-1).getDist_name();
                   // getTaluks(districtModelList.get(position-1).getDist_id(), 4);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerAppVillage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //  product = mDistrictNamesList.get(position);
                if(position!=0)
                {
                    appVillage = villageModelList.get(position-1).getTanda_name();
                    // getTaluks(districtModelList.get(position-1).getDist_id(), 4);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerOppVillage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //  product = mDistrictNamesList.get(position);
                if(position!=0)
                {
                    oppVillage = villageModelList.get(position-1).getTanda_name();
                    // getTaluks(districtModelList.get(position-1).getDist_id(), 4);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerOppDist.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //  product = mDistrictNamesList.get(position);
                if(position!=0)
                {
                    oppDist = districtModelList.get(position-1).getDist_name();
                    getTaluks(districtModelList.get(position-1).getDist_id(), 2);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerAppTaluk.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //  product = mDistrictNamesList.get(position);
                if(position!=0)
                {
                    appTaluk = talukModelList.get(position-1).getName();
                    getTanda(talukModelList.get(position-1).getTlk_id(), 1);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerOppTaluk.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //  product = mDistrictNamesList.get(position);
                if(position!=0)
                {
                    oppTaluk = talukModelList.get(position-1).getName();
                    getTanda(talukModelList.get(position-1).getTlk_id(), 2);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerDist.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //  product = mDistrictNamesList.get(position);
                if(position!=0)
                {
                    dist = districtModelList.get(position-1).getDist_name();
                    getTaluks(districtModelList.get(position-1).getDist_id(), 3);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        editTextResDimen.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                calculateTotal();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        editTextKottigeDimen.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                calculateTotal();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        editTextToolsDimen.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                calculateTotal();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        editTextOthersDimen.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
             calculateTotal();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }
    int total = 0, v=0, v2=0, v3=0, v4=0;

    private void calculateTotal() {
        if(editTextResDimen.getText()!=null && editTextResDimen.getText().toString().trim().length()>0) {
            v = Integer.parseInt(editTextResDimen.getText().toString().trim());
        }
        if(editTextToolsDimen.getText()!=null && editTextToolsDimen.getText().toString().trim().length()>0) {
            v2 = Integer.parseInt(editTextToolsDimen.getText().toString().trim());
        }
        if(editTextKottigeDimen.getText()!=null && editTextKottigeDimen.getText().toString().trim().length()>0) {
            v3 = Integer.parseInt(editTextKottigeDimen.getText().toString().trim());
        }
        if(editTextOthersDimen.getText()!=null && editTextOthersDimen.getText().toString().trim().length()>0) {
            v4 = Integer.parseInt(editTextOthersDimen.getText().toString().trim());
        }
        total = v+v2+v3+v4;
        totalDimen.setText(total+" ಚದರ ಅಡಿ");
    }

    private void submitDetails() {
        RevenueData revenueData = new RevenueData();
        if(ac!=null && ac.length()>0) {
            revenueData.setAc(ac);
        }
        else{
            alert_message_out("ಉಪವಿಭಾಗ ಅಧಿಕಾರಿ ಆಯ್ಕೆಮಾಡಿ");
            return;
        }

        if(acDist!=null && acDist.length()>0) {
            revenueData.setAcDist(acDist);
        }
        else{
            alert_message_out(getContext().getString(R.string.Select_District));
            return;
        }

        if(editTextAppName.getText()!=null && editTextAppName.getText().toString().length()>0) {
            revenueData.setAppName(editTextAppName.getText().toString().trim());
        }
        else{
            alert_message_out("ಅರ್ಜಿದಾರರ ಹೆಸರನ್ನು ನಮೂದಿಸಿ");
            return;
        }

        if(editTextAppFatherName.getText()!=null && editTextAppFatherName.getText().toString().length()>0) {
            revenueData.setAppFatherName(editTextAppFatherName.getText().toString().trim());
        }
        else{
            alert_message_out("ಅರ್ಜಿದಾರರ ತಂದೆ / ಗಂಡನ ಹೆಸರನ್ನು ನಮೂದಿಸಿ");
            return;
        }

        if(editTextAppAge.getText()!=null && editTextAppAge.getText().toString().length()>0) {
            revenueData.setAppAge(editTextAppAge.getText().toString().trim());
        }
        else{
            alert_message_out("ಅರ್ಜಿದಾರರ ವಯಸ್ಸನ್ನು ನಮೂದಿಸಿ");
            return;
        }

        if(editTextAppAdhar.getText()!=null && editTextAppAdhar.getText().toString().length()>0) {
            revenueData.setAppAdhar(editTextAppAdhar.getText().toString().trim());
        }
        else{
            alert_message_out("ಅರ್ಜಿದಾರರ ಆಧಾರ್ / ಪಡಿತರ ಚೀಟಿ ಸಂಖ್ಯೆಯನ್ನು ನಮೂದಿಸಿ");
            return;
        }

        if(editTextAppProfession.getText()!=null && editTextAppProfession.getText().toString().length()>0) {
            revenueData.setAppProfession(editTextAppProfession.getText().toString().trim());
        }
        else{
            alert_message_out("ಅರ್ಜಿದಾರರ ವೃತ್ತಿಯನ್ನು ನಮೂದಿಸಿ");
            return;
        }

        if(appDist!=null )
        {
            revenueData.setAppDist(appDist);
        }
        else
        {
            alert_message_out("ಅರ್ಜಿದಾರರ ಜಿಲ್ಲೆಯನ್ನು ಆಯ್ಕೆಮಾಡಿ");
            return;
        }
        if(appTaluk!=null )
        {
            revenueData.setAppTaluk(appTaluk);
        }
        else
        {
            alert_message_out("ಅರ್ಜಿದಾರರ ತಾಲ್ಲೂಕು  ಆಯ್ಕೆಮಾಡಿ");
            return;
        }

        if(appVillage!=null )
        {
            revenueData.setAppVillage(appVillage);
        }
        else
        {
            alert_message_out("ಅರ್ಜಿದಾರರ ಸ್ಥಳವನ್ನು  ಆಯ್ಕೆಮಾಡಿ");
            return;
        }


        revenueData.setAppOthers(editTextAppOtherVillage.getText().toString().trim());



        if(editTextOppName.getText()!=null && editTextOppName.getText().toString().length()>0) {
            revenueData.setOppName(editTextOppName.getText().toString().trim());
        }
        else{
            alert_message_out("ಪ್ರತಿವಾದಿ ಹೆಸರನ್ನು ನಮೂದಿಸಿ");
            return;
        }

        if(editTextOppFatherName.getText()!=null && editTextOppFatherName.getText().toString().length()>0) {
            revenueData.setOppFatherName(editTextOppFatherName.getText().toString().trim());
        }
        else{
            alert_message_out("ಪ್ರತಿವಾದಿ ತಂದೆ / ಗಂಡನ ಹೆಸರನ್ನು ನಮೂದಿಸಿ");
            return;
        }

        if(editTextOppAge.getText()!=null && editTextOppAge.getText().toString().length()>0) {
            revenueData.setOppAge(editTextOppAge.getText().toString().trim());
        }
        else{
            alert_message_out("ಪ್ರತಿವಾದಿ ವಯಸ್ಸನ್ನು ನಮೂದಿಸಿ");
            return;
        }


        if(editTextOppProfession.getText()!=null && editTextOppProfession.getText().toString().length()>0) {
            revenueData.setOppProfession(editTextOppProfession.getText().toString().trim());
        }
        else{
            alert_message_out("ಪ್ರತಿವಾದಿ ವೃತ್ತಿಯನ್ನು ನಮೂದಿಸಿ");
            return;
        }

        if(oppDist!=null )
        {
            revenueData.setOppDist(oppDist);
        }
        else
        {
            alert_message_out("ಪ್ರತಿವಾದಿ ಜಿಲ್ಲೆಯನ್ನು ಆಯ್ಕೆಮಾಡಿ");
            return;
        }
        if(oppTaluk!=null )
        {
            revenueData.setOppTaluk(oppTaluk);
        }
        else
        {
            alert_message_out("ಪ್ರತಿವಾದಿ ತಾಲ್ಲೂಕು  ಆಯ್ಕೆಮಾಡಿ");
            return;
        }

        if(oppVillage!=null )
        {
            revenueData.setOppVillage(oppVillage);
        }
        else
        {
            alert_message_out("ಪ್ರತಿವಾದಿ ಸ್ಥಳವನ್ನು  ಆಯ್ಕೆಮಾಡಿ");
            return;
        }


        revenueData.setOppAdhar(editTextOppAdhar.getText().toString().trim());
        revenueData.setOppOthers(editTextOppOtherVillage.getText().toString().trim());

        revenueData.setDist(dist);
        revenueData.setTaluk(taluk);
        revenueData.setVillage(village);

        revenueData.setNewVillage(newVillage.getText().toString().trim());
        revenueData.setSurveyNo(editTextSurveyNo.getText().toString().trim());
        revenueData.setHissaNo(editTextHissa.getText().toString().trim());
        revenueData.setTax(editTextTax.getText().toString().trim());

        if(editTextEstimatedValue!=null && editTextEstimatedValue.getText().toString().length()>0) {
            revenueData.setEstimatedValue(editTextEstimatedValue.getText().toString());
        }
        else
        {
            alert_message_out("ಅಂದಾಜು ಮೌಲ್ಯವನ್ನು ನಮೂದಿಸಿ");
            return;
        }

        if(editTextTenure!=null && editTextTenure.getText().toString().length()>0) {
            revenueData.setTenure(editTextTenure.getText().toString().trim());
        }
        else
        {
            alert_message_out("ವಾಸದ ಅವಧಿ");
            return;
        }

        revenueData.setResDimen(editTextResDimen.getText().toString().trim());
        revenueData.setResNorth(editTextResNorth.getText().toString().trim());
        revenueData.setResSouth(editTextResSouth.getText().toString().trim());
        revenueData.setResEast(editTextResEast.getText().toString().trim());
        revenueData.setResWest(editTextResWest.getText().toString().trim());

        revenueData.setKottigeDimen(editTextKottigeDimen.getText().toString().trim());
        revenueData.setKottigeEast(editTextKottigeEast.getText().toString().trim());
        revenueData.setKottigeNorth(editTextKottigeNorth.getText().toString().trim());
        revenueData.setKottigeSouth(editTextKottigeSouth.getText().toString().trim());
        revenueData.setKottigeWest(editTextKottigeWest.getText().toString().trim());

        revenueData.setToolsDimen(editTextToolsDimen.getText().toString().trim());
        revenueData.setToolsEast(editTextToolsEast.getText().toString().trim());
        revenueData.setToolsNorth(editTextToolsNorth.getText().toString().trim());
        revenueData.setToolsSouth(editTextToolsSouth.getText().toString().trim());
        revenueData.setToolsWest(editTextToolsWest.getText().toString().trim());

        revenueData.setOthersDimen(editTextOthersDimen.getText().toString().trim());
        revenueData.setOthersEast(editTextOthersEast.getText().toString().trim());
        revenueData.setOthersNorth(editTextOthersNorth.getText().toString().trim());
        revenueData.setOthersSouth(editTextOthersSouth.getText().toString().trim());
        revenueData.setOthersWest(editTextOthersWest.getText().toString().trim());

        revenueData.setTotalDimen(totalDimen.getText().toString());
        revenueData.setTime(System.currentTimeMillis());

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("revenueDetails");

        myRef.push().setValue(revenueData);

        Toast.makeText(getContext(), "Data saved Success", Toast.LENGTH_LONG).show();
        getActivity().recreate();
    }

    private ApiInterface apiInterface;

    private void setUpNetwork()
    {
        RetrofitInstance retrofitInstance = new RetrofitInstance();
        retrofitInstance.setClient();
        apiInterface = retrofitInstance.getClient().create(ApiInterface.class);
    }

    private void getTaluks(String dist_id, final int id) {
        Call<List<TalukModel>> listCall = apiInterface.getTaluks(dist_id);
        listCall.enqueue(new Callback<List<TalukModel>>() {
            @Override
            public void onResponse(Call<List<TalukModel>> call, Response<List<TalukModel>> response) {
                if(response.isSuccessful()){
                    talukModelList = response.body();
                    mTalukNames = new ArrayList<>();
                    mTalukNames.add(getActivity().getString(R.string.Select_Taluk));
                    if(talukModelList!=null && talukModelList.size()>0) {
                        for (int i = 0; i < talukModelList.size(); i++) {
                            mTalukNames.add(talukModelList.get(i).getName());
                        }
                        ArrayAdapter aa = new ArrayAdapter(getContext(), R.layout.spinner_item, mTalukNames);
                        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        if(id==2) {
                            spinnerOppTaluk.setAdapter(aa);
                            spinnerOppTaluk.setSelection(0);
                        }
                        else if(id==1) {
                            spinnerAppTaluk.setAdapter(aa);
                            spinnerAppTaluk.setSelection(0);
                        }
                        else if(id==3) {
                            spinnerTaluk.setAdapter(aa);
                            spinnerTaluk.setSelection(0);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<List<TalukModel>> call, Throwable t) {
                Toast.makeText(getContext(), R.string.something_wrong, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void getTanda(String tlkdId, final int id) {
        Call<List<VillageModel>> listCall = apiInterface.getTandasByTaluk(tlkdId);
        listCall.enqueue(new Callback<List<VillageModel>>() {
            @Override
            public void onResponse(Call<List<VillageModel>> call, Response<List<VillageModel>> response) {
                if(response.isSuccessful()){
                    villageModelList = response.body();
                    mTandaNames = new ArrayList<>();
                    mTandaNames.add(getContext().getString(R.string.Select_Tanda));
                    if(villageModelList!=null && villageModelList.size()>0) {
                        for (int i = 0; i < villageModelList.size(); i++) {
                            mTandaNames.add(villageModelList.get(i).getTanda_name());
                        }
                        ArrayAdapter aa = new ArrayAdapter(getContext(), R.layout.spinner_item, mTandaNames);
                        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                       if(id==1) {
                           spinnerAppVillage.setAdapter(aa);
                           spinnerAppVillage.setSelection(0);
                       }
                       else if(id==2) {
                           spinnerOppVillage.setAdapter(aa);
                           spinnerOppVillage.setSelection(0);
                       }

                    }
                }
            }

            @Override
            public void onFailure(Call<List<VillageModel>> call, Throwable t) {
                Toast.makeText(getContext(), R.string.something_wrong, Toast.LENGTH_LONG).show();

            }
        });
    }
    private boolean checkInternet(){
        ConnectivityManager mgr = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = mgr.getActiveNetworkInfo();

        if (netInfo != null) {
            if (netInfo.isConnected()) {
                return true;
            }else {
                return false;
            }
        } else {
            return false;
        }
    }
    private void getDistricts() {
        if(checkInternet()) {
            Call<List<DistrictModel>> listCall = apiInterface.getAllDistricts();
            listCall.enqueue(new Callback<List<DistrictModel>>() {
                @Override
                public void onResponse(Call<List<DistrictModel>> call, Response<List<DistrictModel>> response) {
                    if (response.isSuccessful()) {
                        districtModelList = response.body();
                        mDistrictNamesList = new ArrayList<>();
                        mDistrictNamesList.add(getActivity().getString(R.string.Select_District));
                        for (int i = 0; i < districtModelList.size(); i++) {
                            mDistrictNamesList.add(districtModelList.get(i).getDist_name());
                        }
                        ArrayAdapter aa = new ArrayAdapter(getActivity(), R.layout.spinner_item, mDistrictNamesList);
                        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerDist.setAdapter(aa);
                        spinnerDist.setSelection(0);

                        spinnerAc.setAdapter(aa);
                        spinnerAc.setSelection(0);

                        spinnerAppDist.setAdapter(aa);
                        spinnerAppDist.setSelection(0);

                        spinnerOppDist.setAdapter(aa);
                        spinnerOppDist.setSelection(0);

                        spinnerAcDist.setAdapter(aa);
                        spinnerAcDist.setSelection(0);

                    }
                }

                @Override
                public void onFailure(Call<List<DistrictModel>> call, Throwable t) {
                    Toast.makeText(getContext(), R.string.something_wrong, Toast.LENGTH_LONG).show();
                }
            });
        }
        else
        {
            Toast.makeText(getContext(), "No internet", Toast.LENGTH_LONG).show();
        }
    }

    public void alert_message_out(String output) {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
        alertDialogBuilder.setMessage(output);
        alertDialogBuilder.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

}
