package game;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;


public class CombatRulesTest {
    @Test 
    public void testCombatRules_constructor() {
        assertNotNull(new CombatRules());
    }

    @ParameterizedTest
    @CsvSource({"-100", "-101", "-1", "-2", "-3", "0"}) 
    public void testCombatRules_isDeadTrueOnValuesBelowOne(int modifier) {
        assertTrue(CombatRules.isDead(modifier));
    }

    @ParameterizedTest
    @CsvSource({"1", "2", "3", "4", "100"}) 
    public void testCombatRules_isDeadFalseOnValuesMoreThanOrEqualToOne(int modifier) {
        assertFalse(CombatRules.isDead(modifier));
    }

    @Test 
    public void testCombatRules_calculateNewHpDecrementsHpByGivenAmount() {
        int modifier = -50;
        int res = CombatRules.calculateNewHp(100, 100, modifier);
        
        assertEquals(50, res);
    }
    
    @Test 
    public void testCombatRules_calculateNewHpIncrementsHpByGivenAmount() {
        int modifier = 10;
        int res = CombatRules.calculateNewHp(80, 100, modifier);

        assertEquals(90, res);
    }
    
    @Test 
    public void testCombatRules_calculateNewHpClampHpToTheMaximumSetAmount() {
        int modifier = 20;
        int res = CombatRules.calculateNewHp(80, 80, modifier);

        assertEquals(80, res);
    }
}
