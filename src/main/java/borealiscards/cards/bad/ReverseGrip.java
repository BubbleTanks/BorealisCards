package borealiscards.cards.bad;

import borealiscards.cards.BaseCard;
import borealiscards.util.CardStats;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class ReverseGrip extends BaseCard {
    public static final String ID = makeID(ReverseGrip.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CardColor.COLORLESS,
            CardType.ATTACK,
            CardRarity.COMMON,
            CardTarget.SELF,
            1
    );

    public ReverseGrip() {
        super(ID, info);
        setDamage(6, 3);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(p, new DamageInfo(p, damage), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
    }
}


