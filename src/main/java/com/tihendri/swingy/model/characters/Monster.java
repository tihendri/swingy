package com.tihendri.swingy.model.characters;

import com.tihendri.swingy.model.artifacts.Artifact;

public abstract class Monster {

    public int level;
    public int attack;
    public int defence;
    public int hitPoints;
    public int xp;
    public long id;
    public int xCoordinates;
    public int yCoordinates;
    public int idType;
    public Artifact artifact;
    public int counterId;
    public String name;

    public Monster(Artifact artifact, int attack, int defence, int hitPoints, int level, int xp) {
        this.level = level;
        this.attack = attack;
        this.defence = defence;
        this.hitPoints = hitPoints;
        this.xp = xp;
        this.id = nextId();
        this.artifact = artifact;
    }

    public int getMonsterCoordinatesX() {
        return this.xCoordinates;
    }

    public int getMonsterCoordinatesY() {
        return this.yCoordinates;
    }

    public int getMonsterIdType(){
        return this.idType;
    }

    public String getMonsterName() {
        return this.name;
    }

    public void setMonsterName(String name) {
        this.name = name;
    }
    public void setEnemyPosition(int cordX, int cordY) {
        this.xCoordinates = cordX;
        this.yCoordinates = cordY;
    }

    public long nextId() {
        counterId += 1;
        return counterId;
    }

    public void setIdType(int idType) {
        this.idType = idType;
    }

    public Artifact getArtifact() {
        return artifact;
    }

    public int getHitPoints() {
        return this.hitPoints;
    }

    public void setHitPoints(int hitPoints) {
        this.hitPoints += hitPoints;
        if (this.hitPoints <= 0) {
            this.hitPoints = 0;
        }
    }
}
