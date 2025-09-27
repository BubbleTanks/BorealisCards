package borealiscards.powers;

import basemod.helpers.CardModifierManager;
import borealiscards.cardmods.BurningMontanteMod;
import com.evacipated.cardcrawl.mod.stslib.patches.bothInterfaces.OnCreateCardInterface;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

import static borealiscards.BorealisCards.makeID;

public class BurningMontantePower extends BasePower implements OnCreateCardInterface {
    public static final String POWER_ID = makeID(BurningMontantePower.class.getSimpleName());
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    public BurningMontantePower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
        updateCards();
    }

    private void updateCards() {
        for (AbstractCard c : new ArrayList<AbstractCard>() {{
            addAll(AbstractDungeon.player.hand.group);
            addAll(AbstractDungeon.player.discardPile.group);
            addAll(AbstractDungeon.player.drawPile.group);
            addAll(AbstractDungeon.player.limbo.group);
        }}) if(c.type == AbstractCard.CardType.ATTACK && c.cost >= 2 && !CardModifierManager.hasModifier(c, BurningMontanteMod.ID)) CardModifierManager.addModifier(c, new BurningMontanteMod());
    }

    public void onUseCard(AbstractCard card, UseCardAction action) {
        for(int i = amount; i > 0; i--) {
            if (!card.purgeOnUse && CardModifierManager.hasModifier(card, BurningMontanteMod.ID)) {
                this.flash();
                AbstractMonster m = null;
                if (action.target != null) {
                    m = (AbstractMonster) action.target;
                }

                AbstractCard tmp = card.makeSameInstanceOf();
                AbstractDungeon.player.limbo.addToBottom(tmp);
                tmp.current_x = card.current_x;
                tmp.current_y = card.current_y;
                tmp.target_x = (float) Settings.WIDTH / 2.0F - 300.0F * Settings.scale;
                tmp.target_y = (float) Settings.HEIGHT / 2.0F;
                if (m != null) {
                    tmp.calculateCardDamage(m);
                }

                tmp.purgeOnUse = true;
                AbstractDungeon.actionManager.addCardQueueItem(new CardQueueItem(tmp, m, card.energyOnUse, true, true), true);
            }
        }
    }

    @Override
    public void updateDescription() {
        String plural = "s";
        if(amount == 1) plural = "";
        description = String.format(DESCRIPTIONS[0], amount, plural);
    }

    @Override
    public void onCreateCard(AbstractCard c) {
        if(c.type == AbstractCard.CardType.ATTACK && c.cost == 2 && !CardModifierManager.hasModifier(c, BurningMontanteMod.ID)) CardModifierManager.addModifier(c, new BurningMontanteMod());
    }

    @Override
    public void onDrawOrDiscard(){
        updateCards();
    }
}