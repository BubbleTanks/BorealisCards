package borealiscards.cards.defect;

import borealiscards.cards.BaseCard;
import borealiscards.orbs.Starlight;
import borealiscards.util.CardStats;
import com.megacrit.cardcrawl.actions.utility.ScryAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;

public class AlphaTesting extends BaseCard {
    public static final String ID = makeID(AlphaTesting.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CardColor.BLUE,
            CardType.SKILL,
            CardRarity.COMMON,
            CardTarget.NONE,
            1
    );

    public AlphaTesting() {
        super(ID, info);
        setExhaust(true,false);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int starCounter = 0;
        for (AbstractOrb o : AbstractDungeon.player.orbs) {
            o.onStartOfTurn();
            o.onEndOfTurn();
            if(o.ID == Starlight.ORB_ID) {
                starCounter++;
            }
        }
        if(starCounter > 0) addToBot(new ScryAction(starCounter * 2));
    }
}
