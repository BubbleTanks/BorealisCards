package borealiscards.powers;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import static borealiscards.BorealisCards.makeID;

public class PotentialPower extends BasePower {
    public static final String POWER_ID = makeID(PotentialPower.class.getSimpleName());
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    public PotentialPower(AbstractCreature owner, int amount) {super(POWER_ID, TYPE, TURN_BASED, owner, amount);}

    public void onVictory() {
        for (int i = amount; i > 0; i--) {
            AbstractDungeon.getCurrRoom().addNoncampRelicToRewards(returnRandomRelicTier());
        }
    }

    private AbstractRelic.RelicTier returnRandomRelicTier() {
        int roll = AbstractDungeon.relicRng.random(0, 99);

        if (roll < 50) {
            return AbstractRelic.RelicTier.COMMON;
        }
        if (roll > 82) {
            return AbstractRelic.RelicTier.RARE;
        }

        return AbstractRelic.RelicTier.UNCOMMON;
    }

    @Override
    public void updateDescription() {
        String plural = "s";
        if(amount == 1) plural = "";
        description = String.format(DESCRIPTIONS[0], amount, plural);
    }
}