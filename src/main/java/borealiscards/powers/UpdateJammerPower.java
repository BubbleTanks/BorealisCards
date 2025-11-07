package borealiscards.powers;

import borealiscards.BorealisCards;
import borealiscards.cards.defect.UpdateJammer;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.*;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.ThoughtBubble;

import static borealiscards.BorealisCards.makeID;

public class UpdateJammerPower extends BasePower {
    public static final String POWER_ID = makeID("UpdateJammerPower");
    private static final AbstractPower.PowerType TYPE = AbstractPower.PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    public UpdateJammerPower(AbstractCreature owner) {
        super(POWER_ID, TYPE, TURN_BASED, owner, -1);
    }

    @Override
    public void onEvokeOrb(AbstractOrb orb) {
        switch(orb.ID) {
            case Lightning.ORB_ID:
                addToBot(new ApplyPowerAction(owner, owner, new OrbShockPower(owner, 1)));
                break;
            case Frost.ORB_ID:
                addToBot(new ApplyPowerAction(owner, owner, new OrbFrostPower(owner, 1)));
                break;
            case Dark.ORB_ID:
                addToBot(new ApplyPowerAction(owner, owner, new OrbDarkPower(owner, 1)));
                break;
            case Plasma.ORB_ID:
                addToBot(new ApplyPowerAction(owner, owner, new OrbPlasmaPower(owner, 1)));
                break;
            case "borealiscards:Starlight":
                addToBot(new ApplyPowerAction(owner, owner, new OrbStarlightPower(owner, 1)));
                break;
            default:
                BorealisCards.logger.info("Modded orb detected!");
                if (!UpdateJammer.hasWarned) {
                    AbstractDungeon.effectList.add(new ThoughtBubble(AbstractDungeon.player.dialogX, AbstractDungeon.player.dialogY, 3.0F, DESCRIPTIONS[1], true));
                    UpdateJammer.hasWarned = true;
                }
                addToBot(new ApplyPowerAction(owner, owner, new OrbMysteryPower(owner, orb, 1)));
                break;
        }
        flash();
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0];
    }
}
