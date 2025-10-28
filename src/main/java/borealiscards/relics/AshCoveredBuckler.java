package borealiscards.relics;

import basemod.helpers.RelicType;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.StrengthPower;

import static borealiscards.BorealisCards.makeID;

public class AshCoveredBuckler extends BaseRelic {
    public static final String ID = makeID(AshCoveredBuckler.class.getSimpleName());

    public AshCoveredBuckler() {
        super(ID, RelicTier.UNCOMMON, LandingSound.SOLID);
        relicType = RelicType.RED;
    }

    public void onPlayerEndTurn() {
        if(AbstractDungeon.player.currentBlock >= 25) {
            this.flash();
            this.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new StrengthPower(AbstractDungeon.player, 2)));
            this.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}