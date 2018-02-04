package Domain;

import java.util.ArrayList;

/**
 * Created by Maka on 18.11.2017.
 */

public class Battle {

    long number;
    boolean finished = false;
    long timer;
    String log = null;

    private Character winner;

    private Character player1;
    private Character player2;

    private boolean player1IsReady = false;
    private boolean player2IsReady = false;

    private ArrayList<PartOfBody> player1Defance;
    private ArrayList<PartOfBody> player2Defance;

    private int player1Damaged;
    private int player2Damaged;

    private PartOfBody player1Def;
    private PartOfBody player2Def;

    private PartOfBody player1Hit;
    private PartOfBody player2Hit;


    public Battle(long number,Character player1, Character player2) {
        this.timer = 30;
        this.number = number;
        this.player1 = player1;
        this.player2 = player2;
        this.player1Defance =  new ArrayList<PartOfBody>();
        this.player2Defance = new ArrayList<PartOfBody>();
    }


    public void setTimer(long timer) {
        this.timer = timer;
    }

    public boolean isPlayer1IsReady() {
        return player1IsReady;
    }

    public void setPlayer1IsReady(boolean player1IsReady) {
        this.player1IsReady = player1IsReady;
    }

    public boolean isPlayer2IsReady() {
        return player2IsReady;
    }

    public void setPlayer2IsReady(boolean player2IsReady) {
        this.player2IsReady = player2IsReady;
    }

    public PartOfBody getPlayer1Def() {
        return player1Def;
    }

    public void setPlayer1Def(PartOfBody player1Def) {
        this.player1Def = player1Def;
    }

    public PartOfBody getPlayer2Def() {
        return player2Def;
    }

    public void setPlayer2Def(PartOfBody player2Def) {
        this.player2Def = player2Def;
    }

    public Character getPlayer1() {
        return player1;
    }

    public void setPlayer1(Character player1) {
        this.player1 = player1;
    }

    public Character getPlayer2() {
        return player2;
    }

    public void setPlayer2(Character player2) {
        this.player2 = player2;
    }

    public PartOfBody getPlayer1Hit() {
        return player1Hit;
    }

    public void setPlayer1Hit(PartOfBody player1Hit) {
        this.player1Hit = player1Hit;
    }

    public PartOfBody getPlayer2Hit() {
        return player2Hit;
    }

    public void setPlayer2Hit(PartOfBody player2Hit) {
        this.player2Hit = player2Hit;
    }

    public long getNumber() {
        return number;
    }

    public void setNumber(long number) {
        this.number = number;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }

    public long getTimer() {
        return timer;
    }

    public Character getWinner() {
        return winner;
    }

    public int getPlayer1Damaged() {
        return player1Damaged;
    }

    public void setPlayer1Damaged(int player1Damaged) {
        this.player1Damaged = player1Damaged;
    }

    public int getPlayer2Damaged() {
        return player2Damaged;
    }

    public void setPlayer2Damaged(int player2Damaged) {
        this.player2Damaged = player2Damaged;
    }

    public void setWinner(Character winner) {
        this.winner = winner;
    }

    public ArrayList<PartOfBody> getPlayer1DefanceList() {
        return player1Defance;
    }

    public ArrayList<PartOfBody> getPlayer2DefanceList() {
        return player2Defance;
    }
}
