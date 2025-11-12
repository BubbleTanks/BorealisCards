package borealiscards.relics;

import basemod.helpers.RelicType;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import static borealiscards.BorealisCards.makeID;

public class AlloyWorshipUnit extends BaseRelic {
    public static final String ID = makeID(AlloyWorshipUnit.class.getSimpleName());

    public AlloyWorshipUnit() {
        super(ID, RelicTier.BOSS, LandingSound.HEAVY);
        relicType = RelicType.RED;
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card.type == AbstractCard.CardType.ATTACK) {
            addToTop(new LoseHPAction(AbstractDungeon.player, AbstractDungeon.player, 1));
        }
    }

    @Override
    public void atTurnStartPostDraw() {
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                int gainStrength = 0;
                for (AbstractCard c : AbstractDungeon.player.hand.group) {
                    gainStrength += c.costForTurn;
                }
                flash();
                addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new LoseStrengthPower(AbstractDungeon.player, gainStrength)));
                addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new StrengthPower(AbstractDungeon.player, gainStrength)));
                addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, AlloyWorshipUnit.this));
                this.isDone = true;
            }
        });
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}