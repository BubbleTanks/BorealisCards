package borealiscards.cards.watcher;

import borealiscards.cards.BaseCard;
import borealiscards.powers.TraumaturgyPower;
import borealiscards.stances.TensionStance;
import borealiscards.util.CardStats;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.stances.NeutralStance;

public class Traumaturgy extends BaseCard {
    public static final String ID = makeID(Traumaturgy.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CardColor.PURPLE,
            CardType.POWER,
            CardRarity.RARE,
            CardTarget.SELF,
            2
    );

    public Traumaturgy() {
        super(ID, info);
        setCostUpgrade(1);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new TraumaturgyPower(p)));
        if(AbstractDungeon.player.stance.ID == NeutralStance.STANCE_ID) {
            addToBot(new ChangeStanceAction(new TensionStance()));
        }
    }
}

