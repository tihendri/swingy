package com.tihendri.swingy.model.characters;

import com.tihendri.swingy.model.artifacts.Artifact;

import javax.validation.constraints.NotNull;

public class Character {

    @NotNull(message = "Character cannot be null!")
    public String characterName;

    @NotNull(message = "Artifact cannot be null!")
    public Artifact artifact;

    @NotNull(message = "Stats cannot be null!")
    public Stats stats = new Stats();

    public Character() {}

    public Character(@NotNull String characterName, @NotNull Artifact artifact, @NotNull Stats stats) {
        this.characterName = characterName;
        this.artifact = artifact;
        this.stats = stats;
    }

    public String getCharacterName() {
        return characterName;
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
}
