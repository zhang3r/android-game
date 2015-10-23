package com.zhang3r.tarocotta.model.tiles.units.decorator;

import com.zhang3r.tarocotta.model.tiles.units.BaseUnit;

import junit.framework.TestCase;
import junit.framework.TestResult;

/**
 * Created by Zhang3r on 10/14/2015.
 */
public class FriendlyUnitTest extends TestCase {
    BaseUnit unit;
   public void setUp(){
        unit = new BaseUnit() {
           @Override
           public String getDescription() {
               return "";
           }
       };
   }
    public void testFriendlyUnit(){
        FriendlyUnit funit= new FriendlyUnit(unit);

        assertEquals(" This unit is loyal to your cause.", funit.getDescription());
        assertNotNull(funit.getUnit());
    }
}