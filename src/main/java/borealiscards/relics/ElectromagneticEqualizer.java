package borealiscards.relics;

import basemod.AutoAdd;
import basemod.helpers.CardPowerTip;
import borealiscards.BorealisCards;
import borealiscards.cards.curses.ElectromagneticInstability;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;

import static borealiscards.BorealisCards.makeID;

@AutoAdd.Ignore
public class ElectromagneticEqualizer extends BaseRelic {
    public static final String ID = makeID(ElectromagneticEqualizer.class.getSimpleName());
    public ElectromagneticEqualizer() {
        super(ID, RelicTier.BOSS, LandingSound.CLINK);
        this.tips.add(new PowerTip(BorealisCards.keywords.get("Instability").PROPER_NAME, BorealisCards.keywords.get("Instability").DESCRIPTION));
        this.tips.add(new CardPowerTip(new ElectromagneticInstability()));
    }
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    public void onEquip() {
        AbstractDungeon.player.masterHandSize+=2;
        AbstractDungeon.player.energy.energyMaster++;
        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new ElectromagneticInstability(), Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new ElectromagneticInstability(), Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new ElectromagneticInstability(), Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
        UnlockTracker.markCardAsSeen("borealiscards:ElectromagneticInstability");
    }

    public void onUnequip() {
        AbstractDungeon.player.energy.energyMaster--;
        AbstractDungeon.player.masterHandSize-=2;
    }
}
