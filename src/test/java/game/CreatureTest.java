 package game;

 import static org.junit.jupiter.api.Assertions.assertEquals;
 import static org.junit.jupiter.api.Assertions.assertFalse;
 import static org.junit.jupiter.api.Assertions.assertTrue;

 import org.junit.jupiter.api.BeforeEach;
 import org.junit.jupiter.api.Test;
 import org.junit.jupiter.params.ParameterizedTest;
 import org.junit.jupiter.params.provider.CsvSource;

 import asciiPanel.AsciiPanel;

 public class CreatureTest {
     String CAUSE_OF_DEATH = "testing";
     Creature creature1, creature2, creature3;
     World world;

     @BeforeEach
     public void arrangeCreature() {
         world = new WorldBuilder(20, 20, 2).makeCaves().build();
         creature1 = new Creature(world, 'X', AsciiPanel.brightRed, "Creature1", 100, 0, 0);
         creature2 = new Creature(world, 'Z', AsciiPanel.brightRed, "Creature2", 80, 0, 0);
         creature2.modifyMaxHp(100);
         creature3 = new Creature(world, 'Y', AsciiPanel.brightRed, "Creature3", 80, 0, 0);
     }
    
     @Test
     public void testCalculateNewHp_decrementsHpByGivenAmount() {
         int modifier = -50;
         creature1.modifyHp(modifier, CAUSE_OF_DEATH);

         assertEquals(50, creature1.hp());
         assertFalse(CombatRules.isDead(creature1.hp()));
     }
    
     @Test
     public void testCalculateNewHp_incrementsHpByGivenAmount() {
         int modifier = 10;
         creature2.modifyHp(modifier, CAUSE_OF_DEATH);

         assertEquals(90, creature2.hp());
         assertFalse(CombatRules.isDead(creature2.hp()));
     }
    
     @Test
     public void testCalculateNewHp_clampHpToTheMaximumSetAmount() {
         int modifier = 20;
         creature3.modifyHp(modifier, CAUSE_OF_DEATH);

         assertEquals(80, creature3.hp());
         assertFalse(CombatRules.isDead(creature3.hp()));
     }
    
     @ParameterizedTest
     @CsvSource({"-100, 0, true", "-101, -1, true"})
     public void testCalculateNewHp_performKillActionOnCreatureIfHpBelowZero(int modifier, int expectedHp, boolean isDead) {
         creature1.modifyHp(modifier, CAUSE_OF_DEATH);

         assertEquals(expectedHp, creature1.hp());
         assertTrue(CombatRules.isDead(creature1.hp()));
     }

     @Test
     public void testCalculateNewHp_doesNotChangeHpOnZeroModifier() {
         int modifier = 0;
         creature1.modifyHp(modifier, CAUSE_OF_DEATH);

         assertEquals(100, creature1.hp());
         assertFalse(CombatRules.isDead(creature1.hp()));
     }
 }
