package com.weather.bmi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;



public class MainActivity extends AppCompatActivity {

    //variables used in methods....
    public double Height1, WEIGHT;
    public int age1;
    public static String selected_item, selected_weight, gender;


    //view and button from xml....
    public Button submit;
    public EditText Height, Weight, AGE;
    public TextView tv, BMItv, underweight, classII, classIII, classI, sligtly, veryslightly, overweight, normal;
    public Spinner list, list2, list3;
    public ProgressBar bar;
    public ImageView reset , setting;


    //array list....
    ArrayList<String> arr = new ArrayList<>();
    ArrayList<String> arr2 = new ArrayList<>();
    ArrayList<String> arr3 = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //ID......
        submit = findViewById(R.id.submit);
        bar = findViewById(R.id.progressBar);
        Height = findViewById(R.id.height);
        Weight = findViewById(R.id.weight);
        tv = findViewById(R.id.finaltv);
        AGE = findViewById(R.id.age);
        BMItv = findViewById(R.id.final_bmitv);
        list = findViewById(R.id.spin);
        list2 = findViewById(R.id.spin2);
        list3 = findViewById(R.id.spin3);
        underweight = findViewById(R.id.underweight);
        classII = findViewById(R.id.obese_class2);
        normal = findViewById(R.id.normal);
        classIII = findViewById(R.id.obese_class3);
        overweight = findViewById(R.id.overweight);
        veryslightly = findViewById(R.id.very_severrely_under_weight);
        sligtly = findViewById(R.id.severly_underweight);
        classI = findViewById(R.id.obese_class1);
        reset = findViewById(R.id.imageView);
        setting = findViewById(R.id.setting);

        //spinner choice data....
        arr.add("cm");
        arr.add("mtr");

        //weight spinner data....

        arr2.add("kg");
        arr2.add("lb");

        //male / female spinner....
        arr3.add("male");
        arr3.add("female");

        // spinner adapter.....
        ArrayAdapter<String> adapt = new ArrayAdapter<>(this, R.layout.spinner, arr);
        list.setAdapter(adapt);

        //weight spinner adapter....
        ArrayAdapter<String> adapt2 = new ArrayAdapter<>(this, R.layout.spinner, arr2);
        list2.setAdapter(adapt2);

        //male / female spinner adapter
        ArrayAdapter<String> adapt3 = new ArrayAdapter<>(this, R.layout.spinner, arr3);
        list3.setAdapter(adapt3);


        //spinner on selected items...
        list.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    selected_item = list.getSelectedItem().toString();
                } else if (position == 1) {
                    selected_item = list.getSelectedItem().toString();
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selected_item = "cm";
            }
        });

        //weight spinner on selected item...
        list2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    selected_weight = list2.getSelectedItem().toString();
                } else if (position == 1) {
                    selected_weight = list2.getSelectedItem().toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selected_weight = "cm";
            }
        });


        //gender spinner on selected item...
        list3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    gender = list3.getSelectedItem().toString();
                } else if (position == 1) {
                    gender = list3.getSelectedItem().toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //do nothing
            }
        });

        //submit button in click_listener.....
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (selected_item.equals("cm")) {
                        String height = Height.getText().toString();
                        Height1 = Double.parseDouble(height);
                        if (Height1 > 250) {
                            Height1 = 0.00;
                        } else {
                            Height1 = Height1 / 100;
                        }
                    } else {

                        String height = Height.getText().toString();
                        Height1 = Double.parseDouble(height);
                        if (Height1 > 2.50) {
                            Height1 = 0.00;
                        }

                    }

                    if (selected_weight.equals("kg")) {

                        String weight = Weight.getText().toString();
                        WEIGHT = Double.parseDouble(weight);

                        if (WEIGHT > 250) {
                            WEIGHT = 0.00;
                        }
                    } else {
                        String weight = Weight.getText().toString();
                        WEIGHT = Double.parseDouble(weight);
                        if (WEIGHT > 551.15) {
                            WEIGHT = 0.00;
                        } else {
                            WEIGHT = WEIGHT * 0.45359237;
                        }

                    }
                    String Age = AGE.getText().toString();
                    age1 = Integer.parseInt(Age);

                    final_result(WEIGHT, Height1, age1);


                } catch (Exception e) {

                        tv.setText("Please fill all the feild.");
                        tv.setTextSize(18);
                        String ans = e.toString();
                        Log.d("new", ans);
                        BMItv.setText("0.0 ");
                        progbar(-100);

                }

            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BMItv.setText("0.0");
                tv.setText("Your BMI is ...");
                bar.incrementProgressBy(-100);
                view_color_reset(underweight, normal, classI, classII, classIII, sligtly, overweight , veryslightly);
                Height.setText(" ");
                Weight.setText(" ");
                AGE.setText(" ");
            }
        });

        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this , Settings.class);

                startActivity(intent);

            }
        });

    }

    void final_result(double weight1, double height, int age) {

        if (height == 0.00) {
            Toast.makeText(this , "please enter the height below 2.50m or 250cm", Toast.LENGTH_SHORT).show();
            weight1 = 0.00;
        } else if (weight1 == 0.00) {
            height = 0.00;
            Toast.makeText(this, "Please enter the weight below 250kg or 551.15lb", Toast.LENGTH_SHORT).show();
        }else {


            String age_group;
            if (age < 18) {
                age_group = "children";
                children_bmi(weight1, height, age_group);
                underweight.setText(" Underweight                                                      ≤5 ");
                normal.setText("  Normal                                                           6 - 84  ");
                overweight.setText("  Overweight                                                  85 - 94  ");
                classI.setText("  Obese                                                                 ≥95  ");
                veryslightly.setText("");
                sligtly.setText("");
                classII.setText("");
                classIII.setText("");
            } else {
                if (gender.equals("male")) {
                    adult_bmi(weight1, height, gender);
                    underweight.setText(" Underweight                                          17.0 - 18.4 ");
                    normal.setText(" Normal                                                    18.5 - 24.9 ");
                    overweight.setText(" Overweight                                             25.0 - 29.9 ");
                    classI.setText(" Obese Class I                                         30.0 - 34.9 ");
                    veryslightly.setText(" Very severly underweight                             ≤16.0 ");
                    sligtly.setText(" Severly underweight                             16.0 - 16.9 ");
                    classII.setText(" Obese Class II                                        30.0 - 34.9 ");
                    classIII.setText(" Obese Class III                                                ≥40.0 ");
                }
                if (gender.equals("female")) {
                    adult_bmi(weight1, height, gender);
                    underweight.setText(" Underweight                                          17.0 - 18.4 ");
                    normal.setText(" Normal                                                    18.5 - 24.9 ");
                    overweight.setText(" Overweight                                             25.0 - 29.9 ");
                    classI.setText(" Obese Class I                                         30.0 - 34.9 ");
                    veryslightly.setText(" Very severly underweight                             ≤16.0 ");
                    sligtly.setText(" Severly underweight                             16.0 - 16.9 ");
                    classII.setText(" Obese Class II                                        30.0 - 34.9 ");
                    classIII.setText(" Obese Class III                                                ≥40.0 ");
                }

            }
        }

    }

    void view_color_reset(TextView tv1,TextView tv2 , TextView tv3){

        tv1.setBackgroundResource(R.drawable.default_1);
        tv2.setBackgroundResource(R.drawable.default_1);
        tv3.setBackgroundResource(R.drawable.default_1);

    }

    void view_color_reset(TextView tv1,TextView tv2 , TextView tv3 ,TextView tv4,TextView tv5,TextView tv6,TextView tv7 ){

        tv1.setBackgroundResource(R.drawable.default_1);
        tv2.setBackgroundResource(R.drawable.default_1);
        tv3.setBackgroundResource(R.drawable.default_1);
        tv4.setBackgroundResource(R.drawable.default_1);
        tv5.setBackgroundResource(R.drawable.default_1);
        tv6.setBackgroundResource(R.drawable.default_1);
        tv7.setBackgroundResource(R.drawable.default_1);

    }

    void view_color_reset(TextView tv1,TextView tv2 , TextView tv3 ,TextView tv4,TextView tv5,TextView tv6,TextView tv7 , TextView tv8){

        tv1.setBackgroundResource(R.drawable.default_1);
        tv2.setBackgroundResource(R.drawable.default_1);
        tv3.setBackgroundResource(R.drawable.default_1);
        tv4.setBackgroundResource(R.drawable.default_1);
        tv5.setBackgroundResource(R.drawable.default_1);
        tv6.setBackgroundResource(R.drawable.default_1);
        tv7.setBackgroundResource(R.drawable.default_1);
        tv8.setBackgroundResource(R.drawable.default_1);

    }



    void progbar(double inc){
        bar.setMax(100);
        int increment = (int)inc;
        bar.incrementProgressBy(-100);
        bar.incrementProgressBy(increment);
    }

    void children_bmi(double weight , double height1 , String age_group){
        double child_weight = weight * 2.2 ;
        double child_height = height1 * 0.393701;

        double BMI = child_weight * child_height;
        DecimalFormat df = new DecimalFormat("##");
        String after_format = df.format(BMI);

        if (age_group.equals("children")){
            if (BMI < 5) {
                BMItv.setText(after_format);
                tv.setText("Your BMI is ...");
                underweight.setBackgroundResource(R.drawable.underweight);
                view_color_reset(normal , overweight , classI);
                progbar(BMI);

            } else if (BMI >= 5 && BMI < 85) {
                tv.setText("Your BMI is ...");
                BMItv.setText(after_format);
                normal.setBackgroundResource(R.drawable.background2);
                view_color_reset(underweight , overweight , classI);
                progbar(BMI);
            } else if (BMI >= 85 && BMI < 95) {
                tv.setText("Your BMI is ...");
                BMItv.setText(after_format);
                overweight.setBackgroundResource(R.drawable.overweight);
                view_color_reset(underweight , normal , classI);
                progbar(BMI);
            } else {
                tv.setText("Your BMI is ...");
                BMItv.setText(after_format);
                classI.setBackgroundResource(R.drawable.background_1);
                view_color_reset(underweight , normal , overweight);
                progbar(BMI);
            }
        }
    }

    void adult_bmi(double weight , double height , String gender){

            double bmi = weight / (height * height);

            DecimalFormat df = new DecimalFormat("#.##");
            String after_format = df.format(bmi);

            if (gender.equals("male")) {
                if (bmi < 15.9) {
                    tv.setText("Your BMI is ...");
                    BMItv.setText(after_format);
                    veryslightly.setBackgroundResource(R.drawable.very_sligtly_underweight);
                    view_color_reset(underweight, normal, classI, classII, classIII, sligtly, overweight);
                    progbar(bmi);
                } else if (bmi >= 16.0 && bmi <= 16.9) {
                    tv.setText("Your BMI is ...");
                    BMItv.setText(after_format);
                    sligtly.setBackgroundResource(R.drawable.severlyunderweight);
                    view_color_reset(underweight, normal, classI, classII, classIII, veryslightly, overweight);
                    progbar(bmi);
                } else if (bmi >= 17.0 && bmi <= 18.4) {
                    tv.setText("Your BMI is ...");
                    BMItv.setText(after_format);
                    underweight.setBackgroundResource(R.drawable.underweight);
                    view_color_reset(sligtly, normal, classI, classII, classIII, veryslightly, overweight);
                    progbar(bmi);
                } else if (bmi >= 18.5 && bmi <= 24.9) {
                    tv.setText("Your BMI is ...");
                    BMItv.setText(after_format);
                    normal.setBackgroundResource(R.drawable.background2);
                    view_color_reset(sligtly, underweight, classI, classII, classIII, veryslightly, overweight);
                    progbar(bmi);
                } else if (bmi >= 25.0 && bmi <= 29.9) {
                    tv.setText("Your BMI is ...");
                    BMItv.setText(after_format);
                    overweight.setBackgroundResource(R.drawable.overweight);
                    view_color_reset(sligtly, normal, classI, classII, classIII, veryslightly, underweight);
                    progbar(bmi);
                } else if (bmi >= 30.0 && bmi <= 34.9) {
                    tv.setText("Your BMI is ...");
                    BMItv.setText(after_format);
                    classI.setBackgroundResource(R.drawable.background_1);
                    view_color_reset(sligtly, normal, underweight, classII, classIII, veryslightly, overweight);
                    progbar(bmi);
                } else if (bmi >= 35.0 && bmi <= 39.9) {
                    tv.setText("Your BMI is ...");
                    BMItv.setText(after_format);
                    classII.setBackgroundResource(R.drawable.class2);
                    view_color_reset(sligtly, normal, underweight, classI, classIII, veryslightly, overweight);
                    progbar(bmi);
                } else {
                    tv.setText("Your BMI is ...");
                    BMItv.setText(after_format);
                    classIII.setBackgroundResource(R.drawable.class3);
                    view_color_reset(sligtly, normal, underweight, classI, classII, veryslightly, overweight);
                    progbar(bmi);
                }
            } else if (gender.equals("female")) {
                if (bmi < 15.9) {
                    tv.setText("Your BMI is ...");
                    BMItv.setText(after_format);
                    veryslightly.setBackgroundResource(R.drawable.very_sligtly_underweight);
                    view_color_reset(underweight, normal, classI, classII, classIII, sligtly, overweight);
                    progbar(bmi);
                } else if (bmi >= 16.0 && bmi <= 16.9) {
                    tv.setText("Your BMI is ...");
                    BMItv.setText(after_format);
                    sligtly.setBackgroundResource(R.drawable.severlyunderweight);
                    view_color_reset(underweight, normal, classI, classII, classIII, veryslightly, overweight);
                    progbar(bmi);
                } else if (bmi >= 17.0 && bmi <= 18.4) {
                    tv.setText("Your BMI is ...");
                    BMItv.setText(after_format);
                    underweight.setBackgroundResource(R.drawable.underweight);
                    view_color_reset(sligtly, normal, classI, classII, classIII, veryslightly, overweight);
                    progbar(bmi);
                } else if (bmi >= 18.5 && bmi <= 24.9) {
                    tv.setText("Your BMI is ...");
                    BMItv.setText(after_format);
                    normal.setBackgroundResource(R.drawable.background2);
                    view_color_reset(sligtly, underweight, classI, classII, classIII, veryslightly, overweight);
                    progbar(bmi);
                } else if (bmi >= 25.0 && bmi <= 29.9) {
                    tv.setText("Your BMI is ...");
                    BMItv.setText(after_format);
                    overweight.setBackgroundResource(R.drawable.overweight);
                    view_color_reset(sligtly, normal, classI, classII, classIII, veryslightly, underweight);
                    progbar(bmi);
                } else if (bmi >= 30.0 && bmi <= 34.9) {
                    tv.setText("Your BMI is ...");
                    BMItv.setText(after_format);
                    classI.setBackgroundResource(R.drawable.background_1);
                    view_color_reset(sligtly, normal, underweight, classII, classIII, veryslightly, overweight);
                    progbar(bmi);
                } else if (bmi >= 35.0 && bmi <= 39.9) {
                    tv.setText("Your BMI is ...");
                    BMItv.setText(after_format);
                    classII.setBackgroundResource(R.drawable.class2);
                    view_color_reset(sligtly, normal, underweight, classI, classIII, veryslightly, overweight);
                    progbar(bmi);
                } else {
                    tv.setText("Your BMI is ...");
                    BMItv.setText(after_format);
                    classIII.setBackgroundResource(R.drawable.class3);
                    view_color_reset(sligtly, normal, underweight, classI, classII, veryslightly, overweight);
                    progbar(bmi);
                }

            }




    }
}
// Abhishek mehta ***>>