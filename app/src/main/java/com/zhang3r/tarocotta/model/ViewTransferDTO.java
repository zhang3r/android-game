package com.zhang3r.tarocotta.model;

import com.zhang3r.tarocotta.model.army.Army;
import com.zhang3r.tarocotta.model.tiles.terrain.BaseTerrain;
import com.zhang3r.tarocotta.model.tiles.units.BaseUnit;
import com.zhang3r.tarocotta.terminate.TerminateCondition;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Zhang3r on 12/26/2015.
 */
public class ViewTransferDTO implements Serializable{
    int turn;
    Army friendly;
    Army enemy;
    TerminateCondition terminateCondition;
    BaseUnit currentlySelectedUnit;
    BaseTerrain currentlySelectedTerrain;

    public ViewTransferDTO(int turn, Army friendly, Army enemy, TerminateCondition cond, BaseUnit currentlySelectedUnit, BaseTerrain currentlySelectedTerrain){
        this.turn = turn;
        this.friendly = friendly;
        this.enemy = enemy;
        this.terminateCondition = cond;
        this.currentlySelectedTerrain = currentlySelectedTerrain;
        this.currentlySelectedUnit = currentlySelectedUnit;
    }

    public int getTurn() {
        return turn;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

    public Army getFriendly() {
        return friendly;
    }

    public void setFriendly(Army friendly) {
        this.friendly = friendly;
    }

    public Army getEnemy() {
        return enemy;
    }

    public void setEnemy(Army enemy) {
        this.enemy = enemy;
    }

    public TerminateCondition getTerminateCondition() {
        return terminateCondition;
    }

    public void setTerminateCondition(TerminateCondition terminateCondition) {
        this.terminateCondition = terminateCondition;
    }

    public BaseUnit getCurrentlySelectedUnit() {
        return currentlySelectedUnit;
    }

    public void setCurrentlySelectedUnit(BaseUnit currentlySelectedUnit) {
        this.currentlySelectedUnit = currentlySelectedUnit;
    }

    public BaseTerrain getCurrentlySelectedTerrain() {
        return currentlySelectedTerrain;
    }

    public void setCurrentlySelectedTerrain(BaseTerrain currentlySelectedTerrain) {
        this.currentlySelectedTerrain = currentlySelectedTerrain;
    }



}
