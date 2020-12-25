package com.example.dynamicspaceallocation.activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.example.dynamicspaceallocation.R;
import com.example.dynamicspaceallocation.app_utility.AppClass;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import java.util.Objects;

public class PersonalDetails extends AppCompatActivity {

    ActionBar actionBar;
    AutoCompleteTextView acProvince;
//    EditText etCity, etFirstName, etHomeAddress, etIDNumber, etLastName;
    TextInputEditText etCity, etFirstName, etHomeAddress, etIDNumber, etLastName;
    TextInputLayout ilCity,ilFirstName, ilHomeAddress, ilIDNumber, ilLastName, ilProvince;
    Spinner spGender, spRace;
    TextView tvGender, tvRace;
    private String[] sGenderList, sProvinceList, sRaceList;     //holds list of gender & race items
    private String sGender, sProvince, sRace;                   //stores a gender & race item obtained from their respective lists
    ArrayAdapter<String> aGender, aProvince, aRace;             //adapters for holding gender & race arrays


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_details_activity);
        actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle("\t\t\tPersonal Details");

        acProvince = findViewById(R.id.acProvince);
        etCity = findViewById(R.id.etCity);
        etFirstName = findViewById(R.id.etFirstName);
        etHomeAddress = findViewById(R.id.etHomeAddress);
        etIDNumber = findViewById(R.id.etIDNumber);
        etLastName = findViewById(R.id.etLastName);
        ilCity = findViewById(R.id.ilCity);
        ilFirstName = findViewById(R.id.ilFirstName);
        ilHomeAddress = findViewById(R.id.ilHomeAddress);
        ilIDNumber = findViewById(R.id.ilIDNumber);
        ilLastName = findViewById(R.id.ilLastName);
        ilProvince = findViewById(R.id.ilProvince);
        spGender = findViewById(R.id.spGender);
        spRace = findViewById(R.id.spRace);
        tvGender = findViewById(R.id.tvGender);
        tvRace = findViewById(R.id.tvRace);

        genderSpinner(spGender);
        raceSpinner(spRace);
        setProvince();

        etCity.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    validateCity(((EditText) v).getText());
                } //end if
            }
        });
        etFirstName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    validateNames(((EditText) v).getText(), ilFirstName);
                } //end if
            }
        });
        etHomeAddress.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    validateHomeAddress(((EditText) v).getText());
                } //end if
            }
        });
        etIDNumber.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    validateIDNumber(((EditText) v).getText());
                } //end if
            }
        });
        etLastName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    validateNames(((EditText) v).getText(), ilLastName);
                } //end if
            }
        });
        acProvince.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    validateProvince(((EditText) v).getText());
                } //end if
            }
        });
    } //end onCreate()

    //Custom Methods
    public void btnNext_onClick(View view) {
        Intent intent;

        try {
            intent = new Intent(PersonalDetails.this, Register.class);
            //
            if(sGender.matches(" "))
                Toast.makeText(this, "Please select gender!", Toast.LENGTH_SHORT).show();

            if(sRace.matches(" "))
                Toast.makeText(this, "Please select race!", Toast.LENGTH_SHORT).show();

            if(!Objects.requireNonNull(etCity.getText()).toString().isEmpty() && !Objects.requireNonNull(etFirstName.getText()).toString().isEmpty() &&
                    !Objects.requireNonNull(etIDNumber.getText()).toString().isEmpty() && !Objects.requireNonNull(etHomeAddress.getText()).toString().isEmpty() &&
                    !Objects.requireNonNull(etLastName.getText()).toString().isEmpty() && !Objects.requireNonNull(acProvince.getText()).toString().isEmpty()) {
                ilLastName.setError(null);
                ilFirstName.setError(null);
                ilHomeAddress.setError(null);
                ilIDNumber.setError(null);
                ilLastName.setError(null);
                ilProvince.setError(null);

                //validate id number
                if(Objects.requireNonNull(etIDNumber.getText()).toString().length() == 13) {
                    if (AppClass.isIDNumberValid(etIDNumber.getText().toString())) {
                        //save personal info to an intent then send it to the registering page
                        intent.putExtra("firstName", etFirstName.getText().toString());
                        intent.putExtra("lastName", etLastName.getText().toString());
                        intent.putExtra("idNumber", etIDNumber.getText().toString());
                        intent.putExtra("gender", sGender);
                        intent.putExtra("race", sRace);
                        intent.putExtra("city", etCity.getText().toString());
                        intent.putExtra("homeAddress", etHomeAddress.getText().toString());
                        intent.putExtra("province", sProvince);

                        startActivity(intent);
                    } //end if
                    else
                        ilIDNumber.setError("Invalid SA ID number.");
                } //end if
                else
                    ilIDNumber.setError("ID number must be 13 characters long.");
            } //end if
            else {
                if(!Objects.requireNonNull(etCity.getText()).toString().isEmpty())
                    ilCity.setError(null);
                else
                    ilCity.setError("City/Town required");

                if(!Objects.requireNonNull(etFirstName.getText()).toString().isEmpty())
                    ilFirstName.setError(null);
                else
                    ilFirstName.setError("First name required");

                if(!Objects.requireNonNull(etHomeAddress.getText()).toString().isEmpty())
                    ilHomeAddress.setError(null);
                else
                    ilHomeAddress.setError("Home address required");

                if(!Objects.requireNonNull(etIDNumber.getText()).toString().isEmpty())
                    ilIDNumber.setError(null);
                else
                    ilIDNumber.setError("Identity number required");

                if(!Objects.requireNonNull(etLastName.getText()).toString().isEmpty())
                    ilLastName.setError(null);
                else
                    ilLastName.setError("Last name required");

                if(!Objects.requireNonNull(acProvince.getText()).toString().isEmpty())
                    ilProvince.setError(null);
                else
                    ilProvince.setError("Province required");
            } //end else
        } //end try
        catch (Exception ex) {
            Toast.makeText(this, "Error: " + ex.getMessage(), Toast.LENGTH_SHORT).show();
        } //end catch()
    } //end btnNext()

    public void genderSpinner(Spinner spinner) {
        /*
            the purpose of this method is to populate a gender spinner
            wth gender items from the gender array
        */

        //set the gender list to the array
        sGenderList = getResources().getStringArray(R.array.gender_array);
        //set the adapter
        aGender = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, sGenderList);
        //set the adapter
        spinner.setAdapter(aGender);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(parent.getSelectedItemPosition() == 0) {
                    sGender = " ";
                } //end if
                else {
                    sGender = sGenderList[position];
                } //end else
            } //end onItemSelected()
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            } //end onNothingSelected()
        });
    } //end genderSpinner()

    public void raceSpinner(Spinner spinner) {
        /*
            the purpose of this method is to populate a race spinner
            wth race items from the race array
        */

        //set the race list to the array
        sRaceList = getResources().getStringArray(R.array.ethnic_array);
        //set the adapter
        aRace = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, sRaceList);
        //set the adapter
        spinner.setAdapter(aRace);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(parent.getSelectedItemPosition() == 0) {
                    sRace = " ";
                } //end if
                else {
                    sRace = sRaceList[position];
                } //end else
            } //end onItemSelected()
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            } //end onNothingSelected()
        });

    } //end raceSpinner()

    public void setProvince() {
        sProvinceList = getResources().getStringArray(R.array.province_array);
        aProvince = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, sProvinceList);

        acProvince.setAdapter(aProvince);
        acProvince.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(parent.getSelectedItemPosition() == 0)
                    sProvince = " ";
                else
                    sProvince = sProvinceList[position];
            } //end onItemSelected()
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            } //end
        });
    } //end setProvinces()

    public void validateCity(Editable city) {
        //check if input text is empty
        if(!TextUtils.isEmpty(city))
            ilCity.setError(null);
        else
            ilCity.setError("City/Town required");
    } //end validateCity()

    public void validateHomeAddress(Editable homeAddress) {
        //check if input text is empty
        if(!TextUtils.isEmpty(homeAddress))
            ilHomeAddress.setError(null);
        else
            ilHomeAddress.setError("Home address required");
    } //end validateHomeAddress()

    public void validateIDNumber(Editable idNumber) {
        //check if id number text field are empty
        if(!TextUtils.isEmpty(idNumber)) {
            if(Objects.requireNonNull(etIDNumber.getText()).toString().length() == 13) {
//                BigInteger idNumber_ = new BigInteger(Objects.requireNonNull(etIDNumber.getText()).toString());

                if(AppClass.isIDNumberValid(etIDNumber.getText().toString()))
                    ilIDNumber.setError(null);
                else
                    ilIDNumber.setError("Invalid SA ID number");
            } //end if
            else
                ilIDNumber.setError("ID number must be 13 characters long");
        } //end if
        else
            ilIDNumber.setError("Identity number required");
    } //end validateIDNumber()

    public void validateNames(Editable name, TextInputLayout textInputLayout) {
        //check if input text is empty
        if(!TextUtils.isEmpty(name))
            textInputLayout.setError(null);
        else
            textInputLayout.setError("Name required");
    } //end validateNames()

    public void validateProvince(Editable province) {
        //check if input text is empty
        if(!TextUtils.isEmpty(province))
            ilProvince.setError(null);
        else
            ilProvince.setError("Province required");
    } //end validateProvince()
} //end class PersonalDetails
