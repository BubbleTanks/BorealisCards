package borealiscards.relics;

import borealiscards.util.RandomPathSelector;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.TreasureRoomBoss;

import static borealiscards.BorealisCards.makeID;

// this relic and all the associated wizardry brought to you by Cany0u Industries

public class AdventuringPaint extends BaseRelic {
    public static final String ID = makeID(AdventuringPaint.class.getSimpleName());
    private boolean lineFollower = false;

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
            lineFollower = true;
            ++AbstractDungeon.player.energy.energyMaster;
        }
    }

    public void justEnteredRoom(AbstractRoom room) {
        if (AbstractDungeon.getCurrMapNode() != null) {
            if (RandomPathSelector.isOnPath(AbstractDungeon.getCurrMapNode())) {
                if (grayscale) flash();
                grayscale = false;
            } else {
                if (!grayscale) flash();
                grayscale = true;
            }
        }
    }

    public void onVictory() {
        if (lineFollower) {
            --AbstractDungeon.player.energy.energyMaster;
            lineFollower = false;
        }

    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}

