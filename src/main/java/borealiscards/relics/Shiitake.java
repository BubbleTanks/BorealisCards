package borealiscards.relics;

import basemod.BaseMod;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static borealiscards.BorealisCards.makeID;

public class Shiitake extends BaseRelic {
    public static final String ID = makeID(Shiitake.class.getSimpleName());

    public Shiitake() {
        super(ID, RelicTier.BOSS, LandingSound.FLAT);
    }

    public void onEquip() {
        ++AbstractDungeon.player.energy.energyMaster;
        BaseMod.MAX_HAND_SIZE -= 3;
    }

    public void onUnequip() {
        --AbstractDungeon.player.energy.energyMaster;
        BaseMod.MAX_HAND_SIZE += 3;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}