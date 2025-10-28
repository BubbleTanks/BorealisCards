package borealiscards.cards.watcher;

import borealiscards.cards.BaseCard;
import borealiscards.util.CardStats;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class HandOfGod extends BaseCard {
    public static final String ID = makeID(HandOfGod.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CardColor.PURPLE,
            CardType.ATTACK,
            CardRarity.RARE,
            CardTarget.ENEMY,
            1
    );

    public void applyPowers() {
        baseDamage = misc;
        super.applyPowers();
        initializeDescription();
    }

    public void onLoadedMisc() {
        baseDamage = misc;
    }

    public void upgrade() {
        if (!upgraded) {
            misc += 5;
            baseDamage = misc;
            this.upgradedDamage = true;
            super.upgrade();
        }
    }

    public HandOfGod() {
        super(ID, info);
        misc = 28;
        setDamage(misc);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.LIGHTNING));
    }
}
