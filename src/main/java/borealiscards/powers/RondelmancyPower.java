package borealiscards.powers;

import com.evacipated.cardcrawl.mod.stslib.patches.bothInterfaces.OnCreateCardInterface;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.tempCards.Shiv;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static borealiscards.BorealisCards.makeID;

public class RondelmancyPower extends BasePower implements OnCreateCardInterface {
    public static final String POWER_ID = makeID("RondelmancyPower");
    private static final AbstractPower.PowerType TYPE = AbstractPower.PowerType.BUFF;
    private static final boolean TURN_BASED = false;
    public boolean rondelmancyVar = false;

    public RondelmancyPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    @Override
    public void onCreateCard(AbstractCard card) {
        if(Shiv.ID.equals(card.cardID) && !rondelmancyVar) {
            rondelmancyVar = true;
            flash();
            addToBot(new MakeTempCardInHandAction(new Shiv(), amount));
            addToBot(new AbstractGameAction() {
                @Override
                public void update() {
                    rondelmancyVar = false;
                    this.isDone = true;
                }
            });
        }
    }

    @Override
    public void updateDescription() {
        description = String.format(DESCRIPTIONS[0], amount);
    }
}
