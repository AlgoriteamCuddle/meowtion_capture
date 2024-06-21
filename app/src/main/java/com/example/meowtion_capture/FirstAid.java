package com.example.meowtion_capture;


import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;


public class FirstAid extends AppCompatActivity {
    private TextView eatingTextView;
    private TextView sitting_standingTextView;
    private TextView lyingdownTextView;
    private TextView threatenedTextView;
    private TextView sudden_changeTextView;
    private TextView lethargyTextView;
    private TextView painTextView;
    private TextView vomittingTextView;
    private TextView stressTextView;
    private TextView chokingTextView;
    private TextView strokeTextView;
    private TextView seizuresTextView;
    private TextView shockTextView;
    private TextView bleedingTextView;
    private LinearLayout eatingDescContainer;
    private LinearLayout sitting_standingDesc;
    private LinearLayout lyingdownDesc;
    private LinearLayout threatenedDesc;
    private LinearLayout sudden_changeDesc;
    private LinearLayout lethargyDesc;
    private LinearLayout painDesc;
    private LinearLayout vomittingDesc;
    private LinearLayout stressDesc;
    private LinearLayout chokingDesc;
    private LinearLayout strokeDesc;
    private LinearLayout seizuresDesc;
    private LinearLayout shockDesc;
    private LinearLayout bleedingDesc;

    private String userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moreinformation);


        TextView dashboardButton = findViewById(R.id.dashboard_button);
        ImageView logoutButton = findViewById(R.id.logout_button);


        eatingTextView = findViewById(R.id.eating);
        eatingDescContainer = findViewById(R.id.eating_desc_container);


        sitting_standingTextView = findViewById(R.id.sitting_standing);
        sitting_standingDesc = findViewById(R.id.sitting_standing_desc);


        lyingdownTextView = findViewById(R.id.lyingdown);
        lyingdownDesc = findViewById(R.id.lyingdown_desc);


        threatenedTextView = findViewById(R.id.threatened);
        threatenedDesc = findViewById(R.id.threatened_desc);


        sudden_changeTextView = findViewById(R.id.sudden_change);
        sudden_changeDesc = findViewById(R.id.sudden_change_desc);


        lethargyTextView = findViewById(R.id.lethargy);
        lethargyDesc = findViewById(R.id.lethargy_desc);


        painTextView = findViewById(R.id.pain);
        painDesc = findViewById(R.id.pain_desc);


        vomittingTextView = findViewById(R.id.vomitting);
        vomittingDesc = findViewById(R.id.vomitting_desc);


        stressTextView = findViewById(R.id.stress);
        stressDesc = findViewById(R.id.stress_desc);


        chokingTextView = findViewById(R.id.choking);
        chokingDesc = findViewById(R.id.choking_desc);


        strokeTextView = findViewById(R.id.stroke);
        strokeDesc = findViewById(R.id.stroke_desc);


        seizuresTextView = findViewById(R.id.seizures);
        seizuresDesc = findViewById(R.id.seizures_desc);


        shockTextView = findViewById(R.id.shock);
        shockDesc = findViewById(R.id.shock_desc);


        bleedingTextView = findViewById(R.id.bleeding);
        bleedingDesc = findViewById(R.id.bleeding_desc);

        Intent intent = getIntent();
        userId = intent.getStringExtra("user_id");
        dashboardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToDashboard();
            }
        });


        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToLogIn();
            }
        });

        eatingNotDetected();
        eatingNotDetectedDesc();


        sitting_standingNotDetected();
        sitting_standingNotDetectedDesc();


        lyingdown_NotDetected();
        lyingdown_NotDetectedDesc();


        threatened_NotDetected();
        threatened_NotDetectedDesc();


        sudden_changeNotDetected();
        sudden_changeNotDetectedDesc();


        lethargy_head();
        lethargy_description();


        pain_head();
        pain_description();


        vomitting_head();
        vomitting_description();


        stress_head();
        stress_description();


        choking_head();
        choking_description();


        stroke_head();
        stroke_description();


        seizures_head();
        seizures_description();


        shock_head();
        shock_description();


        bleeding_head();
        bleeding_description();


    }


    public void eatingNotDetected() {
        String eating_header = "1. What should I do if the behavioral report did not detect my cat eating for a long period of time?";
        displayEatingHeader(eating_header);
    }


    public void eatingNotDetectedDesc() {
        String[] eating_desc = {
                "Check if the cat appears lethargic, is lying down more than usual, or shows no interest in food or water.",
                "Inspect the cat's mouth for any sores, broken teeth, or signs of pain.",
                "Ensure the food provided is fresh and appetizing. Consider trying a different brand or type of food.",
                "If the cat looks tired and is lying down continuously, visit the veterinarian as loss of appetite can be a sign of illness."
        };
        displayEatingDesc(eating_desc);
    }


    public void sitting_standingNotDetected() {
        String sitting_standing_header = "2. What should I do if my cat is detected sitting or standing for long periods without lying down or eating?";
        displaySittingStandingHeader(sitting_standing_header);
    }
    public void sitting_standingNotDetectedDesc() {
        String[] sitting_standing_desc = {
                "Observe if the cat seems restless or in discomfort. This could indicate stress, pain, or anxiety.",
                "Check for any visible injuries or signs of distress.",
                "Ensure the environment is calm and quiet, and provide a comfortable resting area.",
                "If the cat continues to avoid lying down or eating, consult with a veterinarian to rule out underlying health issues."
        };
        displaySittingStandingDesc(sitting_standing_desc);
    }


    public void lyingdown_NotDetected() {
        String lyingdown_header = "3. What should I do if the behavioral report shows my cat lying down continuously without any other activity?";
        displayLyingdownHeader(lyingdown_header);
    }
    public void lyingdown_NotDetectedDesc() {
        String[] lyingdown_desc = {
                "Monitor if the cat is breathing normally and does not have a fever (check for a warm nose or paws).",
                "Gently try to engage the cat in play or offer food to check its responsiveness.",
                "Ensure the cat is hydrated by offering fresh water and monitoring if it drinks.",
                "If the cat is unresponsive, seems weak, or refuses to eat or drink, seek veterinary assistance immediately."
        };
        displayLyingdownDesc(lyingdown_desc);
    }


    public void threatened_NotDetected() {
        String threatened_header = "4. What should I do if the behavioral report detects threatened behavior in my cat?";
        displayThreatenedHeader(threatened_header);
    }


    public void threatened_NotDetectedDesc() {
        String[] threatened_desc = {
                "Identify any potential sources of stress or fear in the environment, such as other animals, loud noises, or unfamiliar people.",
                "Provide a safe and quiet space for the cat to retreat to and feel secure.",
                "Avoid handling the cat too much when it shows signs of fear or aggression to prevent bites or scratches.",
                "If the behavior persists, consider consulting with a veterinarian or a pet behaviorist for advice on managing anxiety or stress."
        };
        displayThreatenedDesc(threatened_desc);
    }
    public void sudden_changeNotDetected() {
        String sudden_change_header = "5. What should I do if the behavioral report indicates a sudden change in my cat's behavior pattern?";
        displaySuddenChangeHeader(sudden_change_header);
    }


    public void sudden_changeNotDetectedDesc() {
        String[] sudden_change_desc = {
                "Note any recent changes in the cat's environment, diet, or routine that could contribute to behavioral changes.",
                "Ensure the cat is not in pain by checking for injuries or signs of illness (vomiting, diarrhea, coughing, etc.).",
                "Provide consistent feeding times, clean litter boxes, and a comfortable resting area.",
                "If the behavior change is drastic and persists, schedule a veterinary visit for a comprehensive health check."
        };
        displaySuddenChangeDesc(sudden_change_desc);
    }


    public void lethargy_head() {
        String lethargy_header = "For lethargy and lack of appetite";
        displayLethargyHeader(lethargy_header);
    }


    public void lethargy_description() {
        String[] lethargy_desc = {"Ensure the cat is hydrated, offer palatable food, and keep it in a calm environment. If symptoms persist, seek veterinary care."};
        displaylethargyDesc(lethargy_desc);
    }


    public void pain_head() {
        String pain_header = "For signs of pain or injury";
        displayPainHeader(pain_header);
    }


    public void pain_description() {
        String[] pain_desc = {"Gently examine the cat for any obvious wounds or swelling. Keep the cat still and calm and seek veterinary help if needed."};
        displaypainDesc(pain_desc);
    }


    public void vomitting_head() {
        String vomit_header = "For vomiting or diarrhea";
        displayVomittingHeader(vomit_header);
    }


    public void vomitting_description() {
        String[] vomit_desc = {"Withhold food for a short period (4-6 hours) to allow the stomach to settle, then offer bland food like boiled chicken. Ensure the cat stays hydrated. If the condition does not improve, visit the vet."};
        displayvomittingDesc(vomit_desc);
    }


    public void stress_head() {
        String stress_header = "For signs of stress or anxiety";
        displayStressHeader(stress_header);
    }


    public void stress_description() {
        String[] stress_desc = {"Create a safe and quiet space, use calming pheromones or products designed to reduce anxiety, and maintain a consistent routine. Consult a vet if the anxiety is severe."};
        displaystressDesc(stress_desc);
    }


    public void choking_head() {
        String choking_header = "For choking";
        displayChokingHeader(choking_header);
    }


    public void choking_description() {
        String[] choking_desc = {"Hold your pet's body against your stomach with their paws facing down and head facing up. Locate the soft area under their ribs where your closed fist fits snugly. Using one hand, pull up and inward sharply towards your stomach 2 to 3 times to perform abdominal thrusts."};
        displaychokingDesc(choking_desc);
    }


    public void stroke_head() {
        String stroke_header = "For heatstroke";
        displayStrokeHeader(stroke_header);
    }


    public void stroke_description() {
        String[] stroke_desc = {"Immediately remove your cat from the hot environment and apply tepid or cool water to their fur and skin. Use a fan or gently fan them to aid in heat dissipation."};
        displaystrokeDesc(stroke_desc);
    }


    public void seizures_head() {
        String seizures_header = "For seizures";
        displaySeizuresHeader(seizures_header);
    }


    public void seizures_description() {
        String[] seizures_desc = {"Maintain a cool room temperature as seizures can lead to increased body heat. Avoid moving your pet until the seizure stops unless directed otherwise by your vet. Contact your vet if the seizure lasts longer than two minutes or if it's not the first seizure in the past 24 hours."};
        displayseizuresDesc(seizures_desc);
    }


    public void shock_head() {
        String shock_header = "For shock";
        displayShockHeader(shock_header);
    }


    public void shock_description() {
        String[] shock_desc = {"Keep the cat calm and warm by wrapping them in a blanket. Elevate their hindquarters slightly unless they have head, neck, or back injuries. Seek immediate veterinary attention."};
        displayshockDesc(shock_desc);
    }


    public void bleeding_head() {
        String bleeding_header = "For internal bleeding";
        displayBleedingHeader(bleeding_header);
    }


    public void bleeding_description() {
        String[] bleeding_desc = {"Look for signs such as pale gums, rapid breathing, or weakness. Keep the cat calm and minimize movement. Apply gentle pressure to any visible wounds and transport them to the nearest veterinary clinic as soon as possible."};
        displaybleedingDesc(bleeding_desc);
    }




    private void displayEatingHeader(String eating_header) {
        eatingTextView.setText(eating_header);
    }
    private void displayEatingDesc(String[] eating_desc) {
        for (String desc : eating_desc) {
            TextView textView = new TextView(this);
            textView.setText("\u2022 " + desc); // Add bullet point
            textView.setTextSize(14);
            eatingDescContainer.addView(textView);
        }
    }


    private void displaySittingStandingHeader(String siting_standing_header) {
        sitting_standingTextView.setText(siting_standing_header);
    }
    private void displaySittingStandingDesc(String[] sitting_standing_desc) {
        for (String desc : sitting_standing_desc) {
            TextView textView = new TextView(this);
            textView.setText("\u2022 " + desc); // Add bullet point
            textView.setTextSize(14);
            sitting_standingDesc.addView(textView);
        }
    }


    private void displayLyingdownHeader(String lyingdown_header) {
        lyingdownTextView.setText(lyingdown_header);
    }
    private void displayLyingdownDesc(String[] lyingdown_desc) {
        for (String desc : lyingdown_desc) {
            TextView textView = new TextView(this);
            textView.setText("\u2022 " + desc); // Add bullet point
            textView.setTextSize(14);
            lyingdownDesc.addView(textView);
        }
    }


    private void displayThreatenedHeader(String threatened_header) {
        threatenedTextView.setText(threatened_header);
    }
    private void displayThreatenedDesc(String[] threatened_desc) {
        for (String desc : threatened_desc) {
            TextView textView = new TextView(this);
            textView.setText("\u2022 " + desc); // Add bullet point
            textView.setTextSize(14);
            threatenedDesc.addView(textView);
        }
    }


    private void displaySuddenChangeHeader(String sudden_change_header) {
        sudden_changeTextView.setText(sudden_change_header);
    }
    private void displaySuddenChangeDesc(String[] sudden_change_desc) {
        for (String desc : sudden_change_desc) {
            TextView textView = new TextView(this);
            textView.setText("\u2022 " + desc); // Add bullet point
            textView.setTextSize(14);
            sudden_changeDesc.addView(textView);
        }
    }


    private void displayLethargyHeader(String lethargy_header) {
        lethargyTextView.setText(lethargy_header);
    }
    private void displaylethargyDesc(String[] lethargy_desc) {
        for (String desc : lethargy_desc) {
            TextView textView = new TextView(this);
            textView.setText("\u2022 " + desc); // Add bullet point
            textView.setTextSize(14);
            lethargyDesc.addView(textView);
        }
    }


    private void displayPainHeader(String pain_header) {
        painTextView.setText(pain_header);
    }
    private void displaypainDesc(String[] pain_desc) {
        for (String desc : pain_desc) {
            TextView textView = new TextView(this);
            textView.setText("\u2022 " + desc); // Add bullet point
            textView.setTextSize(14);
            painDesc.addView(textView);
        }
    }


    private void displayVomittingHeader(String vomit_header) {
        vomittingTextView.setText(vomit_header);
    }
    private void displayvomittingDesc(String[] vomit_desc) {
        for (String desc : vomit_desc) {
            TextView textView = new TextView(this);
            textView.setText("\u2022 " + desc); // Add bullet point
            textView.setTextSize(14);
            vomittingDesc.addView(textView);
        }
    }


    private void displayStressHeader(String stress_header) {
        stressTextView.setText(stress_header);
    }
    private void displaystressDesc(String[] stress_desc) {
        for (String desc : stress_desc) {
            TextView textView = new TextView(this);
            textView.setText("\u2022 " + desc); // Add bullet point
            textView.setTextSize(14);
            stressDesc.addView(textView);
        }
    }


    private void displayChokingHeader(String choking_header) {
        chokingTextView.setText(choking_header);
    }
    private void displaychokingDesc(String[] choking_desc) {
        for (String desc : choking_desc) {
            TextView textView = new TextView(this);
            textView.setText("\u2022 " + desc); // Add bullet point
            textView.setTextSize(14);
            chokingDesc.addView(textView);
        }
    }


    private void displayStrokeHeader(String stroke_header) {
        strokeTextView.setText(stroke_header);
    }
    private void displaystrokeDesc(String[] stroke_desc) {
        for (String desc : stroke_desc) {
            TextView textView = new TextView(this);
            textView.setText("\u2022 " + desc); // Add bullet point
            textView.setTextSize(14);
            strokeDesc.addView(textView);
        }
    }


    private void displaySeizuresHeader(String seizures_header) {
        seizuresTextView.setText(seizures_header);
    }
    private void displayseizuresDesc(String[] seizures_desc) {
        for (String desc : seizures_desc) {
            TextView textView = new TextView(this);
            textView.setText("\u2022 " + desc); // Add bullet point
            textView.setTextSize(14);
            seizuresDesc.addView(textView);
        }
    }


    private void displayShockHeader(String shock_header) {
        shockTextView.setText(shock_header);
    }
    private void displayshockDesc(String[] shock_desc) {
        for (String desc : shock_desc) {
            TextView textView = new TextView(this);
            textView.setText("\u2022 " + desc); // Add bullet point
            textView.setTextSize(14);
            shockDesc.addView(textView);
        }
    }


    private void displayBleedingHeader(String bleeding_header) {
        bleedingTextView.setText(bleeding_header);
    }
    private void displaybleedingDesc(String[] bleeding_desc) {
        for (String desc : bleeding_desc) {
            TextView textView = new TextView(this);
            textView.setText("\u2022 " + desc); // Add bullet point
            textView.setTextSize(14);
            bleedingDesc.addView(textView);
        }
    }




    private void navigateToDashboard() {
        Intent intent = new Intent(this, Dashboard.class);
        intent.putExtra("user_id", userId);
        startActivity(intent);
    }


    private void navigateToLogIn() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}

