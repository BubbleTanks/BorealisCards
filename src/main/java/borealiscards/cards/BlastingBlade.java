package borealiscards.cards;

import borealiscards.actions.BlastingBladeAction;
import borealiscards.util.CardStats;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class BlastingBlade extends BaseCard {
    public static final String ID = makeID(BlastingBlade.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CardColor.RED,
            CardType.ATTACK,
            CardRarity.COMMON,
            CardTarget.ENEMY,
            2
    );

    public BlastingBlade() {
        super(ID, info);
        setDamage(12,3);
        setMagic(10,0);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new BlastingBladeAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), magicNumber, damage, p, m));
    }
}
