package com.tihendri.swingy.model.characters;

import com.tihendri.swingy.model.artifacts.Artifact;

import javax.validation.constraints.NotNull;

public class Character {

    @NotNull(message = "Character cannot be null!")
    public String character;
    @NotNull(message = "Artifact cannot be null!")
    public Artifact artifact;
    @NotNull(message = "Stats cannot be null!")
    public Stats stats = new Stats();

    public Character() {}

    public Character(@NotNull String character, @NotNull Artifact artifact, @NotNull Stats stats) {
        this.character = character;
        this.artifact = artifact;
        this.stats = stats;
    }

    public String getCharacter() {
        return character;
    }

    public Artifact getArtifact() {
        return artifact;
    }

    public Stats getStats() {
        return stats;
    }

    public void setArtifact(Artifact artifact) {
        this.artifact = artifact;
    }

//    public void setCharacter(String character) {
//        this.character = character;
//    }

    public void setStats(Stats stats) {
        this.stats = stats;
    }
}
