package borealiscards.relics;

import basemod.helpers.RelicType;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.Plasma;

import static borealiscards.BorealisCards.makeID;

public class ElectricFurnace extends BaseRelic {
    public static final String ID = makeID(ElectricFurnace.class.getSimpleName());

    public ElectricFurnace() {
        super(ID, RelicTier.BOSS, LandingSound.HEAVY);
        relicType = RelicType.BLUE;
    }

    public void onUseCard(AbstractCard c, UseCardAction action) {
        if (c.costForTurn >= 3) {
            addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            flash();
            addToBot(new ChannelAction(new Plasma()));
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}