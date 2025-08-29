package borealiscards.powers;

import basemod.helpers.CardModifierManager;
import borealiscards.actions.RondelmancyAction;
import borealiscards.cardmods.RondelmancyMod;
import com.evacipated.cardcrawl.mod.stslib.patches.bothInterfaces.OnCreateCardInterface;
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
    public void onCreateCard(AbstractCard abstractCard) {
        if(abstractCard.cardID == Shiv.ID && !CardModifierManager.hasModifier(abstractCard, RondelmancyMod.ID) && !rondelmancyVar) {
            rondelmancyVar = true;
            for (int i = this.amount; i > 0; i--) {
                AbstractCard card = new Shiv();
                CardModifierManager.addModifier(card, new RondelmancyMod());
                addToBot(new MakeTempCardInHandAction(card, 1));
            }
            addToBot(new RondelmancyAction(this));
        }
    }
}
