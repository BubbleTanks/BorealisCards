package borealiscards.cards.watcher;

import borealiscards.cards.BaseCard;
import borealiscards.util.CardStats;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;

public class Firewatch extends BaseCard {
    public static final String ID = makeID(Firewatch.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CardColor.PURPLE,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            2
    );

    public Firewatch() {
        super(ID, info);
        setMagic(3,1);
        setCustomVar("vigorIncrease", VariableType.MAGIC, 3,1);
        setSelfRetain(true);
    }

    public void vigorUp() {
        baseMagicNumber += customVar("vigorIncrease");
        applyPowers();
    }

    public void applyPowers() {
        initializeDescription();
        super.applyPowers();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new VigorPower(p, magicNumber)));
    }
}
