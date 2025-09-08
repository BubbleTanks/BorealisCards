package borealiscards.cards;

import borealiscards.actions.LivingBladeAction;
import borealiscards.util.CardStats;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class LivingBlade extends BaseCard {
    public static final String ID = makeID(LivingBlade.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CardColor.PURPLE,
            CardType.ATTACK,
            CardRarity.UNCOMMON,
            CardTarget.ENEMY,
            -1
    );

    public AbstractCard makeStatEquivalentCopy() {
        AbstractCard retVal = super.makeStatEquivalentCopy();
        retVal.magicNumber = this.magicNumber;
        retVal.baseMagicNumber = this.baseMagicNumber;
        return retVal;
    }

    public void applyPowers() {
        magicNumber = misc;
        super.applyPowers();
        initializeDescription();
    }

    public void onLoadedMisc() {
        baseMagicNumber = misc;
    }

    public LivingBlade() {
        super(ID, info);
        setExhaust(true);
        setDamage(2,1);
        misc = 2;
        setMagic(misc);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new LivingBladeAction(uuid, p, m, magicNumber, damage, damageTypeForTurn, freeToPlayOnce, energyOnUse));
    }
};
