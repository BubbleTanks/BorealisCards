package borealiscards.relics;

import basemod.BaseMod;
import borealiscards.ui.ModConfig;
import borealiscards.util.RandomPathSelector;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.relics.PandorasBox;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.TreasureRoomBoss;

import static borealiscards.BorealisCards.makeID;

public class AdventuringPaint extends BaseRelic {
    public static final String ID = makeID(AdventuringPaint.class.getSimpleName());

    public AdventuringPaint() {
        super(ID, RelicTier.BOSS, LandingSound.MAGICAL);
    }

    public void onEquip() {
        if (AbstractDungeon.getCurrRoom() == null ||
                !(AbstractDungeon.getCurrRoom() instanceof TreasureRoomBoss)) {
            RandomPathSelector.selectRandomPath();
        }
    }

    public void beforeEnergyPrep() {
        if (AbstractDungeon.getCurrMapNode() != null &&
                RandomPathSelector.isOnPath(AbstractDungeon.getCurrMapNode())) {
            this.beginLongPulse();
            this.flash();
            ++AbstractDungeon.player.energy.energyMaster;
        }
    }

    public void onVictory() {
        if (this.pulse) {
            --AbstractDungeon.player.energy.energyMaster;
            this.stopPulse();
        }

    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}

