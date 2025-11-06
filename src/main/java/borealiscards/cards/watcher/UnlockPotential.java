package borealiscards.cards.watcher;

import borealiscards.cards.BaseCard;
import borealiscards.patches.rarities.CustomRarity;
import borealiscards.powers.PotentialPower;
import borealiscards.util.CardStats;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.actions.watcher.NotStanceCheckAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.stances.DivinityStance;
import com.megacrit.cardcrawl.vfx.combat.EmptyStanceEffect;

public class UnlockPotential extends BaseCard {
    public static final String ID = makeID(UnlockPotential.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CardColor.PURPLE,
            CardType.POWER,
            CustomRarity.EXOTIC,
            CardTarget.SELF,
            1
    );

    public UnlockPotential() {
        super(ID, info);
        setSelfRetain(false, true);
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        boolean canUse = super.canUse(p, m);
        if (!canUse) {
            return false;
        } else {
            if (AbstractDungeon.player.stance.ID != DivinityStance.STANCE_ID) {
                canUse = false;
                cantUseMessage = cardStrings.EXTENDED_DESCRIPTION[0];
            }
            return canUse;
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new NotStanceCheckAction("Neutral", new VFXAction(new EmptyStanceEffect(p.hb.cX, p.hb.cY), 0.1F)));
        addToBot(new ChangeStanceAction("Neutral"));
        addToBot(new ApplyPowerAction(p, p, new PotentialPower(p, 1)));
    }
}
