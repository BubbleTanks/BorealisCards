package borealiscards.relics;

import borealiscards.SpireFields.DonutField;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static borealiscards.BorealisCards.makeID;

public class BigDonut extends BaseRelic {
    public static final String ID = makeID(BigDonut.class.getSimpleName());

    public BigDonut() {
        super(ID, RelicTier.RARE, LandingSound.FLAT);
    }

    public void onEquip() {
        DonutField.GainMaxHp.doNotHeal.set(AbstractDungeon.player, true);
        AbstractDungeon.player.increaseMaxHp(21, true);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}