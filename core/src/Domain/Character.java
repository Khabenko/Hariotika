package Domain;



import com.badlogic.gdx.graphics.Texture;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.UUID;

import javax.imageio.ImageIO;


public class Character {

    private UUID id;
    private String name;
    private boolean inBattle;
    private Texture avatar;

    private int strength;
    private int agility;
    private int intuition;
    private int vitality;
    private int intelligence;
    private int wisdom;
    private int armor;

    private int maxHP;
    private int HP;
    private int maxMP;
    private int MP;
    private String login;
    private int lvl;
    private int experience;
    private int expnextlvl;


    private int decreasePower_Crit;
    private int decreasePersen_Crit;
    private int evesion;
    private int decreaseEnemyEvesion;
    private int armor_penetration;
    private int chance_criticalPhyAttack;
    private int power_criticalPhyAttack;
    private int chance_counterattack;
    private int chance_parry;
    private double hp_perSec;
    private int mp_perSec;
    private int pointCharacteristics;
    private int phy_attack;





    public Character(String name, String login) {
        this.name = name;
        this.login = login;

    }

    public Character() {

    }


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public int getAgility() {
        return agility;
    }

    public void setAgility(int agility) {
        this.agility = agility;
    }

    public int getIntuition() {
        return intuition;
    }

    public void setIntuition(int intuition) {
        this.intuition = intuition;
    }

    public int getVitality() {
        return vitality;
    }

    public void setVitality(int vitality) {
        this.vitality = vitality;
    }

    public int getIntelligence() {
        return intelligence;
    }

    public void setIntelligence(int intelligence) {
        this.intelligence = intelligence;
    }

    public int getWisdom() {
        return wisdom;
    }

    public void setWisdom(int wisdom) {
        this.wisdom = wisdom;
    }

    public int getArmor() {
        return armor;
    }

    public void setArmor(int armor) {
        this.armor = armor;
    }

    public int getMaxHP() {
        return maxHP;
    }

    public void setMaxHP(int maxHP) {
        this.maxHP = maxHP;
    }

    public int getHP() {
        return HP;
    }

    public void setHP(int HP) {
        this.HP = HP;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public int getLvl() {
        return lvl;
    }

    public void setLvl(int lvl) {
        this.lvl = lvl;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public Texture getAvatar() {
        return avatar;
    }

    public void setAvatar(Texture avatar) {
        this.avatar = avatar;
    }

    public boolean isInBattle() {
        return inBattle;
    }

    public void setInBattle(boolean inBattle) {
        this.inBattle = inBattle;
    }

    public int getExpnextlvl() {
        return expnextlvl;
    }

    public void setExpnextlvl(int expnextlvl) {
        this.expnextlvl = expnextlvl;
    }

    public void setAvatar(byte[] result) {
    }

    public int getDecreasePower_Crit() {
        return decreasePower_Crit;
    }

    public void setDecreasePower_Crit(int decreasePower_Crit) {
        this.decreasePower_Crit = decreasePower_Crit;
    }

    public int getDecreasePersen_Crit() {
        return decreasePersen_Crit;
    }

    public void setDecreasePersen_Crit(int decreasePersen_Crit) {
        this.decreasePersen_Crit = decreasePersen_Crit;
    }

    public int getEvesion() {
        return evesion;
    }

    public void setEvesion(int evesion) {
        this.evesion = evesion;
    }

    public int getDecreaseEnemyEvesion() {
        return decreaseEnemyEvesion;
    }

    public void setDecreaseEnemyEvesion(int decreaseEnemyEvesion) {
        this.decreaseEnemyEvesion = decreaseEnemyEvesion;
    }

    public int getArmor_penetration() {
        return armor_penetration;
    }

    public void setArmor_penetration(int armor_penetration) {
        this.armor_penetration = armor_penetration;
    }

    public int getChance_criticalPhyAttack() {
        return chance_criticalPhyAttack;
    }

    public void setChance_criticalPhyAttack(int chance_criticalPhyAttack) {
        this.chance_criticalPhyAttack = chance_criticalPhyAttack;
    }

    public int getPower_criticalPhyAttack() {
        return power_criticalPhyAttack;
    }

    public void setPower_criticalPhyAttack(int power_criticalPhyAttack) {
        this.power_criticalPhyAttack = power_criticalPhyAttack;
    }

    public int getChance_counterattack() {
        return chance_counterattack;
    }

    public void setChance_counterattack(int chance_counterattack) {
        this.chance_counterattack = chance_counterattack;
    }

    public int getChance_parry() {
        return chance_parry;
    }

    public void setChance_parry(int chance_parry) {
        this.chance_parry = chance_parry;
    }

    public double getHp_perSec() {
        return hp_perSec;
    }

    public void setHp_perSec(double hp_perSec) {
        this.hp_perSec = hp_perSec;
    }

    public int getMp_perSec() {
        return mp_perSec;
    }

    public void setMp_perSec(int mp_perSec) {
        this.mp_perSec = mp_perSec;
    }

    public int getPointCharacteristics() {
        return pointCharacteristics;
    }

    public void setPointCharacteristics(int pointCharacteristics) {
        this.pointCharacteristics = pointCharacteristics;
    }

    public int getMaxMP() {
        return maxMP;
    }

    public void setMaxMP(int maxMP) {
        this.maxMP = maxMP;
    }

    public int getMP() {
        return MP;
    }

    public void setMP(int MP) {
        this.MP = MP;
    }

    public int getPhy_attack() {
        return phy_attack;
    }

    public void setPhy_attack(int phy_attack) {
        this.phy_attack = phy_attack;
    }
}
