package borealiscards.relics;

import borealiscards.ui.ModConfig;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
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

    public void instantObtain(AbstractPlayer p, int slot, boolean callOnEquip) {
        if (slot >= p.relics.size() && AbstractDungeon.player.hasRelic(relicReplaceID)) {
            for (int i=0; i<AbstractDungeon.player.relics.size(); ++i) {
                if (AbstractDungeon.player.relics.get(i).relicId.equals(relicReplaceID)) {
                    super.instantObtain(p, i, callOnEquip);
                    break;
                }
            }
        }
        super.instantObtain(p,slot,callOnEquip);
    }

    public boolean canSpawn()
    {
        if(!ModConfig.Relics) return false;
        return AbstractDungeon.player.hasRelic(relicReplaceID);
    }
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}

