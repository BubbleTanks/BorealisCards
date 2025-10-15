package borealiscards.cards.ironclad;

import borealiscards.cards.BaseCard;
import borealiscards.powers.ReturnHPPower;
import borealiscards.util.CardStats;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class DamageVitals extends BaseCard {
    public static final String ID = makeID(DamageVitals.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CardColor.RED,
            CardType.ATTACK,
            CardRarity.UNCOMMON,
            CardTarget.ENEMY,
            1
    );

    public DamageVitals() {
        super(ID, info);
        setDamage(9, 3);
        setMagic(2,0);
        setExhaust(true);
        tags.add(CardTags.HEALING);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
        addToBot(new ApplyPowerAction(m, p, new ReturnHPPower(m, magicNumber), magicNumber));
    }
}


