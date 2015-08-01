package com.zhang3r.onelevelgame.terminate;

import com.zhang3r.onelevelgame.model.army.Army;

/**
 * Created by Zhang3r on 8/1/2015.
 */
public interface TerminateCondition {

    public boolean isWin(Army army);
    public boolean isLose(Army army);

    public String getTerminateString();
}
