package com.example.android_magiworld;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.example.android_magiworld.Ressources.Mage;
import com.example.android_magiworld.Ressources.Rogue;
import com.example.android_magiworld.Ressources.Warrior;
import com.example.android_magiworld.Ressources.Character;

import java.util.ArrayList;
import java.util.List;

public class SecondCharactersActivity extends AppCompatActivity implements View.OnClickListener {

    private static Character player2;
    private EditText vLevel, vStrength, vAgility, vIntelligence, vPlayersName;
    private int level, life, strength, agility, intelligence;
    private String playersName;
    private TextView vLife;
    private TextView playersTurn;
    private Button nextBtn;
    private AlertDialog.Builder popUp;
    private ImageView warrior, rogue, mage;
    String character = null;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.characterchoice);

        vLevel = findViewById(R.id.level);
        vLife = findViewById(R.id.life);
        vStrength = findViewById(R.id.strength);
        vAgility = findViewById(R.id.agility);
        vIntelligence = findViewById(R.id.intelligence);
        vPlayersName = findViewById(R.id.name);

        playersTurn = findViewById(R.id.players_turn);
        nextBtn = findViewById(R.id.next_and_start);
        nextBtn.setOnClickListener(this);

        warrior = findViewById(R.id.warrior);
        rogue = findViewById(R.id.rogue);
        mage = findViewById(R.id.mage);

        warrior.setOnClickListener(this);
        rogue.setOnClickListener(this);
        mage.setOnClickListener(this);

        playersTurn.setText(getString(R.string.characterchoice, 2));

        popUp = new AlertDialog.Builder(this, R.style.MyDialogTheme);

        vLevel.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (vLevel.getText().toString().isEmpty())
                    level = 0;
                else
                    level = Integer.parseInt(vLevel.getText().toString());

                life = level * 5;
                vLife.setText(getString(R.string.life_display, life));
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (level > 100) {
                    vLevel.setError(getString(R.string.inferiorto100));
                }

                if (vLevel.getText().toString().equals("0"))
                    vLevel.setError(getString(R.string.superiorto0));
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.next_and_start)
            if (checkIfValid()) {
                introduction();
            }
        if (v.getId() == R.id.warrior) {
            character = "warrior";
            warrior.setBackgroundColor(getResources().getColor(R.color.lightWhite));
            rogue.setBackgroundColor(0x0);
            mage.setBackgroundColor(0x0);
        }
        if (v.getId() == R.id.rogue) {
            character = "rogue";
            rogue.setBackgroundColor(getResources().getColor(R.color.lightWhite));
            warrior.setBackgroundColor(0x0);
            mage.setBackgroundColor(0x0);
        }
        if (v.getId() == R.id.mage) {
            character = "mage";
            mage.setBackgroundColor(getResources().getColor(R.color.lightWhite));
            rogue.setBackgroundColor(0x0);
            warrior.setBackgroundColor(0x0);
        }
    }

    private boolean checkIfValid() {


        if (vLevel.getText().toString().isEmpty() || vStrength.getText().toString().isEmpty() ||
                vIntelligence.getText().toString().isEmpty() ||
                vAgility.getText().toString().isEmpty() || vPlayersName.getText().toString().isEmpty()) {

            if (vLevel.getText().toString().isEmpty())
                vLevel.setError("!");
            if (vStrength.getText().toString().isEmpty())
                vStrength.setError("!");
            if (vIntelligence.getText().toString().isEmpty())
                vIntelligence.setError("!");
            if (vAgility.getText().toString().isEmpty())
                vAgility.setError("!");
            if (vPlayersName.getText().toString().isEmpty())
                vPlayersName.setError("!");

            Toast.makeText(this, "Remplissez tous les champs!", Toast.LENGTH_LONG).show();
            return false;
        }

        if (character == null) {
            Toast.makeText(this, "Choisissez votre personnage!", Toast.LENGTH_LONG).show();
            return false;
        }


        strength = Integer.parseInt(vStrength.getText().toString());
        intelligence = Integer.parseInt(vIntelligence.getText().toString());
        agility = Integer.parseInt(vAgility.getText().toString());
        playersName = vPlayersName.getText().toString().trim();

        if ((strength + intelligence + agility) != level || level == 0) {
            popUp();
            return false;
        } else {
            switch (character) {
                case "warrior":
                    player2 = new Warrior(level, strength, intelligence, agility, playersName);
                    break;
                case "rogue":
                    player2 = new Rogue(level, strength, intelligence, agility, playersName);
                    break;
                case "mage":
                    player2 = new Mage(level, strength, intelligence, agility, playersName);
            }
        }
        return true;
    }


    private void popUp() {
        popUp.setTitle(getString(R.string.aie));
        if (level == 0)
            popUp.setMessage(getString(R.string.superiorto0));
        popUp.setMessage(getString(R.string.totalunderlevel));
        popUp.setPositiveButton(getString(R.string.back), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        popUp.show();
    }

    private void introduction() {
        final AlertDialog.Builder popUp = new AlertDialog.Builder(this, R.style.MyDialogTheme);
        popUp.setTitle(getString(R.string.introduction, 2));
        popUp.setMessage(player2.introduction());
        popUp.setPositiveButton(getString(R.string.next), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(getApplicationContext(), Begingame.class);
                startActivity(intent);
            }
        });
        popUp.show();
    }

    public static List<Character> getPlayers() {
        List<Character> playersList = new ArrayList<>();
        Character player1 = CharactersActivity.getPlayer();

        playersList.add(player1);
        playersList.add(player2);
        return playersList;
    }
}