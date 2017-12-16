package API;

import Domain.Battle;
import Domain.Character;

/**
 * Created by Maka on 13.12.2017.
 */

public class HariotikaMessage {


    private Command command;
    private WsCode code;

    private Character character;
    private Battle battle;

    private String login;
    private String password;

    private String hit;
    private String def;
    private String charName;
    private int timer;




    public HariotikaMessage() {

    }

    public HariotikaMessage(Command command, WsCode code, String login, String password) {
        this.command = command;
        this.code = code;
        this.login = login;
        this.password = password;
    }

    public HariotikaMessage(Command command, WsCode code) {
        this.command = command;
        this.code = code;
    }


    public HariotikaMessage(Command command, WsCode code, Character character) {
        this.command = command;
        this.code = code;
        this.character = character;
    }

    public HariotikaMessage(Command command, WsCode code, Battle battle) {
        this.command = command;
        this.code = code;
        this.battle = battle;
    }

    public WsCode getCode() {
        return code;
    }

    public void setCode(WsCode code) {
        this.code = code;
    }

    public Character getCharacter() {
        return character;
    }

    public void setCharacter(Character character) {
        this.character = character;
    }

    public Battle getBattle() {
        return battle;
    }

    public void setBattle(Battle battle) {
        this.battle = battle;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Command getCommand() {
        return command;
    }

    public void setCommand(Command command) {
        this.command = command;
    }

    public String getHit() {
        return hit;
    }

    public void setHit(String hit) {
        this.hit = hit;
    }

    public String getDef() {
        return def;
    }

    public void setDef(String def) {
        this.def = def;
    }

    public int getTimer() {
        return timer;
    }

    public void setTimer(int timer) {
        this.timer = timer;
    }

    public String getCharName() {
        return charName;
    }

    public void setCharName(String charName) {
        this.charName = charName;
    }
}
