package borealiscards.cards;

import borealiscards.powers.AcrimonyPower;
import borealiscards.util.CardStats;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PoisonPower;

public class HeavyFog extends BaseCard {
    public static final String ID = makeID(HeavyFog.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CardColor.GREEN,
            CardType.SKILL,
            CardRarity.COMMON,
            CardTarget.ENEMY,
            2
    );

    public HeavyFog() {
        super(ID, info);
        setMagic(7,2);
        setCustomVar("magic2",VariableType.MAGIC,2,1);

    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(m, p, new PoisonPower(m, p, magicNumber), magicNumber, AbstractGameAction.AttackEffect.POISON));
        addToBot(new ApplyPowerAction(m, p, new AcrimonyPower(m, customVar("magic2")), customVar("magic2")));
    }
}


