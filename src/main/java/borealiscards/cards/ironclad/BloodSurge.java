package borealiscards.cards.ironclad;

import borealiscards.cards.BaseCard;
import borealiscards.patches.LostHealthThisTurnPatch;
import borealiscards.util.CardStats;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.vfx.combat.InflameEffect;

public class BloodSurge extends BaseCard {
    public static final String ID = makeID(BloodSurge.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CardColor.RED,
            CardType.SKILL,
            CardRarity.RARE,
            CardTarget.SELF,
            1
    );

    public BloodSurge() {
        super(ID, info);
        setMagic(1,1);
        setExhaust(true);
        tags.add(CardTags.HEALING);
    }

    @Override // Gain Strength and HP
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new VFXAction(p, new InflameEffect(p), 1.0F));
        for (int i = 0; i < AbstractDungeon.player.damagedThisCombat; i++) {
            addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, magicNumber), magicNumber));
            addToBot(new HealAction(p, p, 1));
        }
    }

    @Override // Check for damage this turn
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        boolean canUse = super.canUse(p, m);
        if (!canUse) {
            return false;
        } else {
            if (!LostHealthThisTurnPatch.hurtThisTurn) {
                canUse = false;
                cantUseMessage = cardStrings.EXTENDED_DESCRIPTION[0];
            }
            return canUse;
        }
    }


}


