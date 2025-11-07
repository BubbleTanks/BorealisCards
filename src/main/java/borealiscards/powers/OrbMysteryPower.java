package borealiscards.powers;

import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.NonStackablePower;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static borealiscards.BorealisCards.makeID;

public class OrbMysteryPower extends BasePower implements NonStackablePower {
    public static final String POWER_ID = makeID(OrbMysteryPower.class.getSimpleName());
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;
    private final AbstractOrb MYSTERYORB;

    public OrbMysteryPower(AbstractCreature owner, AbstractOrb o, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
        MYSTERYORB = o;
        MYSTERYORB.cX = AbstractDungeon.player.drawX;
        MYSTERYORB.cY = AbstractDungeon.player.drawY + 100F * Settings.scale;
        updateDescription();
    }

    public boolean isStackable(AbstractPower p) {
        if (p instanceof OrbMysteryPower) {
            if (((OrbMysteryPower) p).MYSTERYORB.ID == this.MYSTERYORB.ID) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void atEndOfTurnPreEndTurnCards(boolean isPlayer) {
        for (int i = 0; i < amount; i++) {
            MYSTERYORB.applyFocus();
            MYSTERYORB.onEndOfTurn();
            flash();
        }
    }

    @Override
    public void atStartOfTurn() {
        for (int i = 0; i < amount; i++) {
            MYSTERYORB.applyFocus();
            MYSTERYORB.onStartOfTurn();
            flash();
        }
    }

    @Override
    public void updateDescription() {
        String orb = DESCRIPTIONS[1];
        if (MYSTERYORB != null) {
            orb = MYSTERYORB.name;
        }
        String plural = "s";
        if(this.amount == 1) plural = "";
        description = String.format(DESCRIPTIONS[0], amount, orb, plural);
        PowerStrings strings = CardCrawlGame.languagePack.getPowerStrings(ID);
        name = strings.NAME + orb;
    }
}