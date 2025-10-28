package borealiscards.cards.ironclad;

import borealiscards.cards.BaseCard;
import borealiscards.util.CardStats;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class DreamlandExpress extends BaseCard {
    public static final String ID = makeID(DreamlandExpress.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CardColor.RED,
            CardType.ATTACK,
            CardRarity.UNCOMMON,
            CardTarget.ENEMY,
            1
    );

    public void applyPowers() {
        int realBaseDamage = baseDamage;
        baseDamage += playedThisTurn;
        super.applyPowers();
        baseDamage = realBaseDamage;
        isDamageModified = damage != baseDamage;
    }

    public void calculateCardDamage(AbstractMonster mo) {
        int realBaseDamage = baseDamage;
        baseDamage += playedThisTurn;
        super.calculateCardDamage(mo);
        baseDamage = realBaseDamage;
        isDamageModified = damage != baseDamage;
    }

    public static int playedThisTurn = 0;

    public DreamlandExpress() {
        super(ID, info);
        setDamage(6,2);
        setMagic(3,1);
        setExhaust(true);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractCard dreamland = new DreamlandExpress();
        if (upgraded) {
            dreamland.upgrade();
        }
        addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_HEAVY));
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                DreamlandExpress.playedThisTurn += magicNumber;
                this.isDone = true;
            }
        });
        addToBot(new MakeTempCardInHandAction(dreamland,1));
    }
}
