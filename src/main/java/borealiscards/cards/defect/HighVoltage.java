package borealiscards.cards.defect;

import borealiscards.cards.BaseCard;
import borealiscards.util.CardStats;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.Lightning;

public class HighVoltage extends BaseCard {
    public static final String ID = makeID(HighVoltage.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CardColor.BLUE,
            CardType.ATTACK,
            CardRarity.COMMON,
            CardTarget.ENEMY,
            3
    );

    public HighVoltage() {
        super(ID, info);
        setDamage(10,3);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HEAVY));
        addToBot(new ChannelAction(new Lightning()));
        addToBot(new ChannelAction(new Lightning()));
        addToBot(new ChannelAction(new Lightning()));
        if(upgraded) addToBot(new ChannelAction(new Lightning()));
    }
}
