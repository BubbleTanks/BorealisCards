package borealiscards.cards.ironclad;

import borealiscards.cards.BaseCard;
import borealiscards.util.CardStats;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class UndyingSpear extends BaseCard {
    public static final String ID = makeID(UndyingSpear.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CardColor.RED,
            CardType.ATTACK,
            CardRarity.RARE,
            CardTarget.ENEMY,
            1
    );

    public UndyingSpear() {
        super(ID, info);
        setDamage(14,5);
        setMagic(2);
        tags.add(CardTags.HEALING);
    }

    public void triggerOnExhaust() {
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                AbstractDungeon.player.increaseMaxHp(magicNumber, false);
                this.isDone = true;
            }
        });
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
    }
}


