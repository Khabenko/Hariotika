package Domain;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import State.MainState;
import State.StateManager;

import static API.Client.battle;
import static API.Client.setBattle;

/**
 * Created by Maka on 04.02.2018.
 */

public class AfterBattleWindow extends Window {
    public AfterBattleWindow(Skin skin, StateManager sm) {

        super("Battle finish", skin);

        TextButton backButton = new TextButton("Back",skin);
        backButton.setSize(250,150);
        final StateManager finalSM = sm;


        defaults().pad(2);
        Label winner;

        if (battle.getWinner()!=null)
        winner = new Label("Winner " + battle.getWinner().getName(), skin);
        else
            winner = new Label("No winner =  draw ", skin);

        add(winner).setActorX(50);

        add().row();
        Label Damage = new Label(battle.getPlayer1().getName()+" damaged " + battle.getPlayer1Damaged(), skin);
        add(Damage).setActorX(50);
        add().row();
        Label Damage2 = new Label(battle.getPlayer2().getName()+" damaged " + battle.getPlayer2Damaged(), skin);
        add(Damage2).setActorX(50);
        add().row();
        Label EXP1 = new Label(battle.getPlayer1().getName()+" Exp " + battle.getPlayer1EXP(), skin);
        add(EXP1).setActorX(50);
        add().row();
        Label EXP2 = new Label(battle.getPlayer2().getName()+" Exp " + battle.getPlayer2EXP(), skin);
        add(EXP2).setActorX(50);
        add().row();
        add().row();


        TextButton  battleButton = backButton;
        add(battleButton).width(100).height(60);

        pack();
        setKeepWithinStage(true);




        backButton.addListener( new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                setBattle(null);
                finalSM.set(new MainState(finalSM));

            };
        });

    }






}
