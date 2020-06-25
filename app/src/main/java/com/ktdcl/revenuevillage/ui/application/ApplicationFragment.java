package com.ktdcl.revenuevillage.ui.application;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.ktdcl.revenuevillage.R;
import com.ktdcl.revenuevillage.model.DistrictModel;
import com.ktdcl.revenuevillage.model.RevenueData;
import com.ktdcl.revenuevillage.model.TalukModel;
import com.ktdcl.revenuevillage.model.VillageModel;
import com.ktdcl.revenuevillage.network.ApiInterface;
import com.ktdcl.revenuevillage.network.RetrofitInstance;

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


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_home, container, false);
        initViews();
        return mView;
    }

    private void initViews() {

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
                    dist = districtModelList.get(position-1).getDist_name();
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
                    dist = districtModelList.get(position-1).getDist_name();
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
                    dist = districtModelList.get(position-1).getDist_name();
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
                    taluk = talukModelList.get(position-1).getName();
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
                    taluk = talukModelList.get(position-1).getName();
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
    }

    private void submitDetails() {
        RevenueData revenueData = new RevenueData();
        revenueData.setAc(ac);
        revenueData.setAcDist(acDist);

        revenueData.setAppName(editTextAppName.getText().toString().trim());
        revenueData.setAppFatherName(editTextAppFatherName.getText().toString().trim());
        revenueData.setAppAge(editTextAppAge.getText().toString().trim());
        revenueData.setAppAdhar(editTextAppAdhar.getText().toString().trim());
        revenueData.setAppProfession(editTextAppProfession.getText().toString().trim());

        Toast.makeText(getContext(), "Data saved Success", Toast.LENGTH_LONG).show();
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
}
