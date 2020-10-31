package buzzardsView.calculator;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;


public class Settings extends AppCompatActivity {

    private EditText light1Name;
    private EditText light1Price;
    private EditText light1Watts;
    private EditText light2Name;
    private EditText light2Price;
    private EditText light2Watts;
    private EditText light3Name;
    private EditText light3Price;
    private EditText light3Watts;
    private EditText light4Name;
    private EditText light4Price;
    private EditText light4Watts;
    private Button applyBtn;
    private Button resetBtn;

    public double doubleFigure(String string) {
        double answer;
        answer = Double.parseDouble(string);
        return answer;
    }

    public void openCalc() {
        saveData();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onSupportNavigateUp(){ //makes back button work
        saveData();
        finish();
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        getSupportActionBar().setTitle(getResources().getString(R.string.incan_settings)); //changes title of page to settings
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //shows a back button
        light1Name = findViewById(R.id.light1Name);
        light1Price = findViewById(R.id.light1Price);
        light1Watts = findViewById(R.id.light1Watts);
        light2Name = findViewById(R.id.light2Name);
        light2Price = findViewById(R.id.light2Price);
        light2Watts = findViewById(R.id.light2Watts);
        light3Name = findViewById(R.id.light3Name);
        light3Price = findViewById(R.id.light3Price);
        light3Watts = findViewById(R.id.light3Watts);
        light4Name = findViewById(R.id.light4Name);
        light4Price = findViewById(R.id.light4Price);
        light4Watts = findViewById(R.id.light4Watts);
        applyBtn = findViewById(R.id.applyBtn);
        resetBtn = findViewById(R.id.resetBtn);



        final EditText[] editTextNameArray = {
                light1Name,
                light2Name,
                light3Name,
                light4Name,
        };
        final EditText[] editTextPriceArray = {
                light1Price,
                light2Price,
                light3Price,
                light4Price,
        };
        final EditText[] editTextWattsArray = {
                light1Watts,
                light2Watts,
                light3Watts,
                light4Watts
        };

        for (int i = 0; i < editTextNameArray.length; i++) {
            editTextNameArray[i].setHint(MainActivity.lightArray[i].getName());
        }

        for (int i = 0; i < editTextPriceArray.length; i++) {
            editTextPriceArray[i].setHint(getResources().getString(R.string.price_per_foot_hint) + MainActivity.lightArray[i].getPrice());
        }

        for (int i = 0; i < editTextWattsArray.length; i++) {
            editTextWattsArray[i].setHint(getResources().getString(R.string.watts_per_foot_hint) + MainActivity.lightArray[i].getWatts());
        }

        applyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for (int i = 0; i < editTextNameArray.length; i++) {
                    if (TextUtils.isEmpty(editTextNameArray[i].getText())) {
                        MainActivity.lightArray[i].getName();
                    } else {
                        MainActivity.lightArray[i].setName("" + editTextNameArray[i].getText());
                    }
                }

                for (int i = 0; i < editTextPriceArray.length; i++) {
                    if (TextUtils.isEmpty(editTextPriceArray[i].getText())) {
                        MainActivity.lightArray[i].getPrice();
                    } else {
                        MainActivity.lightArray[i].setPrice(doubleFigure(editTextPriceArray[i].getText().toString()));
                    }
                }

                for (int i = 0; i < editTextWattsArray.length; i++) {
                    if (TextUtils.isEmpty(editTextWattsArray[i].getText())) {
                        MainActivity.lightArray[i]. getWatts();
                    } else {
                        MainActivity.lightArray[i].setWatts(doubleFigure(editTextWattsArray[i].getText().toString()));
                    }
                }
                openCalc();
            }
        });

        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reset();
            }
        });



    }
    public void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences(MainActivity.SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        for (int i = 0; i < MainActivity.incanNamesPrefs.length; i++) {
            editor.putString(MainActivity.incanNamesPrefs[i], MainActivity.lightArray[i].getName());
        }
        for (int i = 0; i < MainActivity.incanPricesPrefs.length; i++) {
            putDouble(editor, MainActivity.incanPricesPrefs[i], MainActivity.lightArray[i].getPrice());
        }
        for (int i= 0; i < MainActivity.incanWattagesPrefs.length; i++) {
            putDouble(editor, MainActivity.incanWattagesPrefs[i], MainActivity.lightArray[i].getWatts());
        }
        editor.apply();
    }

    public void reset() {
        SharedPreferences sharedPreferences = getSharedPreferences(MainActivity.SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
        editor.apply();
        Light[] lightArray = new Light[]{
                new Light("C7", .41, 5),
                new Light("Mini", 3.5, 16),
                new Light("Net", 12.4,63.75),
                new Light("Timer", 9.97, 0)
        };
        MainActivity.lightArray = lightArray;
        openCalc();
    }

    SharedPreferences.Editor putDouble(final SharedPreferences.Editor edit, final String key, final double value) {
        return edit.putLong(key, Double.doubleToRawLongBits(value));
    }



}
