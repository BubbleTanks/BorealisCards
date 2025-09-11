package borealiscards.powers;

import borealiscards.orbs.Starlight;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;

import static borealiscards.BorealisCards.makeID;

public class OrbStarlightPower extends BasePower {
    public static final String POWER_ID = makeID(OrbStarlightPower.class.getSimpleName());
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;
    public static final AbstractOrb STARLIGHTORB = new Starlight();

    public OrbStarlightPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
        STARLIGHTORB.cX = AbstractDungeon.player.drawX;
        STARLIGHTORB.cY = AbstractDungeon.player.drawY + 100F * Settings.scale;
    }

    @Override
    public void atStartOfTurn() {
        for (int i = 0; i < amount; i++) {
            STARLIGHTORB.onStartOfTurn();
            flash();
        }
    }

    @Override
    public void updateDescription() {
        String plural = "s";
        if(this.amount == 1) plural = "";
        description = String.format(DESCRIPTIONS[0], amount, plural);
    }
}