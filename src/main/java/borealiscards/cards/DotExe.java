package borealiscards.cards;

import borealiscards.util.CardStats;
import com.megacrit.cardcrawl.actions.watcher.ChooseOneAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

public class DotExe extends BaseCard {
    public static final String ID = makeID(DotExe.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CardColor.BLUE,
            CardType.SKILL,
            CardRarity.RARE,
            CardTarget.NONE,
            2
    );

    public DotExe() {
        super(ID, info);
        setExhaust(true, false);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        ArrayList<AbstractCard> orbChoices = new ArrayList();
        orbChoices.add(new Thunderbolt());
        orbChoices.add(new Permafrost());
        orbChoices.add(new Shadowfall());
        orbChoices.add(new Cosmos());
        addToBot(new ChooseOneAction(orbChoices));
    }
}


