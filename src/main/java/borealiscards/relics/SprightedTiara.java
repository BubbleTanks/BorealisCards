package borealiscards.relics;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.combat.LightningEffect;

import static borealiscards.BorealisCards.makeID;

public class SprightedTiara extends BaseRelic {
    public static final String ID = makeID(SprightedTiara.class.getSimpleName());

    public SprightedTiara() {
        super(ID, RelicTier.COMMON, LandingSound.CLINK);
    }

    @Override
    public void onPlayerEndTurn() {
        flash();
        AbstractMonster abstractMonster = AbstractDungeon.getRandomMonster();

        if (abstractMonster != null) {
            float speedTime = 0.1F;
            if (Settings.FAST_MODE) {
                speedTime = 0.0F;
            }
            addToTop(new DamageAction((AbstractCreature)abstractMonster, new DamageInfo(AbstractDungeon.player, 5, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.NONE, true));
            addToTop(new VFXAction((AbstractGameEffect)new LightningEffect((abstractMonster).drawX, ((AbstractCreature)abstractMonster).drawY), speedTime));
            addToTop(new SFXAction("ORB_LIGHTNING_EVOKE"));
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}