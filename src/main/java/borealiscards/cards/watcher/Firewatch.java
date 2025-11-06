package borealiscards.cards.watcher;

import borealiscards.cards.BaseCard;
import borealiscards.patches.rarities.CustomRarity;
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
            CustomRarity.SHOP,
            CardTarget.SELF,
            2
    );

    public Firewatch() {
        super(ID, info);
        setMagic(4,2);
        setCustomVar("vigorIncrease", VariableType.MAGIC, 4,2);
        setSelfRetain(true);
    }

    public void vigorUp() {
        baseMagicNumber += customVar("vigorIncrease");
        this.isMagicNumberModified = true;
        super.applyPowers();
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
