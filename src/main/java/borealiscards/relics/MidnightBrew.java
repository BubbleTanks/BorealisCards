package borealiscards.relics;

import borealiscards.potions.VialOfNight;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.potions.PotionSlot;

import static borealiscards.BorealisCards.makeID;

public class MidnightBrew extends BaseRelic {
    public static final String ID = makeID(MidnightBrew.class.getSimpleName());

    public MidnightBrew() {
        super(ID, RelicTier.BOSS, LandingSound.FLAT);
    }

    public void onEquip() {
        AbstractDungeon.player.potionSlots += 1;
        AbstractDungeon.player.potions.add(new PotionSlot(AbstractDungeon.player.potionSlots - 1));
        AbstractDungeon.player.obtainPotion(new VialOfNight());
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}