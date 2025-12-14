package borealiscards.powers;

import borealiscards.BorealisCards;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;

public class InstabilityPower extends BasePower {
    public static final String POWER_ID = BorealisCards.makeID(InstabilityPower.class.getSimpleName());
    public static final PowerType TYPE = PowerType.DEBUFF;
    private static final boolean TURNBASED = false;

    public InstabilityPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURNBASED, owner, amount);
        updateDescription();
    }

    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);
        CardCrawlGame.sound.play("ORB_LIGHTNING_EVOKE",0.05f);
        AbstractDungeon.player.gameHandSize--;
        AbstractDungeon.effectsQueue.add(new BorderFlashEffect(Color.RED.cpy(), true));
        updateDescription();
    }

    public void onInitialApplication() {
        CardCrawlGame.sound.play("ORB_LIGHTNING_EVOKE",0.05f);
        AbstractDungeon.player.gameHandSize--;
        AbstractDungeon.effectsQueue.add(new BorderFlashEffect(Color.RED.cpy(), true));
    }

    public void atEndOfTurnPreEndTurnCards(boolean isPlayer) {
        if(AbstractDungeon.actionManager.cardsPlayedThisTurn.isEmpty()) {
            addToBot(new RemoveSpecificPowerAction(owner, owner, this));
        }
    }

    public void onRemove() {
        AbstractDungeon.player.gameHandSize+= this.amount;
    }

    public void updateDescription() {
        String plural = "s";
        if(this.amount == 1) plural = "";
        this.description = String.format(DESCRIPTIONS[0],this.amount,plural);
    }
}
