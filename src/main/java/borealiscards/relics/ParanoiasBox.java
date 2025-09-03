package borealiscards.relics;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.PandorasBox;

import static borealiscards.BorealisCards.makeID;

public class ParanoiasBox extends BaseRelic {
    public static final String ID = makeID(ParanoiasBox.class.getSimpleName());

    private static final String relicReplaceID = PandorasBox.ID;

    public ParanoiasBox() {
        super(ID, RelicTier.UNCOMMON, LandingSound.MAGICAL);

    }

    @Override
    public void obtain() {
        if (AbstractDungeon.player.hasRelic(relicReplaceID)) {
            for (int i=0; i<AbstractDungeon.player.relics.size(); ++i) {
                if (AbstractDungeon.player.relics.get(i).relicId.equals(relicReplaceID)) {
                    instantObtain(AbstractDungeon.player, i, true);
                    break;
                }
            }
        } else {
            super.obtain();
        }
    }

    public boolean canSpawn() {
        return AbstractDungeon.player.hasRelic("Pandora's Box");
    }
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}

