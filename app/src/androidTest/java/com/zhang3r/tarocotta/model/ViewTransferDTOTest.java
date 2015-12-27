package com.zhang3r.tarocotta.model;

import com.zhang3r.tarocotta.constants.IGameConstants;
import com.zhang3r.tarocotta.model.army.Army;
import com.zhang3r.tarocotta.model.tiles.terrain.BaseTerrain;
import com.zhang3r.tarocotta.model.tiles.units.BaseUnit;
import com.zhang3r.tarocotta.terminate.AllUnitsDestroyed;
import com.zhang3r.tarocotta.terminate.TerminateCondition;

import junit.framework.TestCase;

/**
 * Created by Zhang3r on 12/26/2015.
 */
public class ViewTransferDTOTest extends TestCase {

    public void testDTOCreation(){
        ViewTransferDTO dto = new ViewTransferDTO( 1, new Army(), new Army(), new AllUnitsDestroyed(), new BaseUnit(IGameConstants.UnitType.FOOT), new BaseTerrain());

        assertNotNull(dto.getCurrentlySelectedTerrain());
        assertNotNull(dto.getEnemy());
        assertNotNull(dto.getFriendly());
        assertNotNull(dto.getTurn());
        assertNotNull(dto.getCurrentlySelectedUnit());
        assertNotNull(dto.getTerminateCondition());


    }

}