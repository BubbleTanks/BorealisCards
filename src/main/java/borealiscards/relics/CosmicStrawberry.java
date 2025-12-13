package borealiscards.relics;

import borealiscards.patches.rarities.CustomRarity;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.actions.utility.UnlimboAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import java.util.ArrayList;

import static borealiscards.BorealisCards.makeID;

public class CosmicStrawberry extends BaseRelic {
    public static final String ID = makeID(CosmicStrawberry.class.getSimpleName());

    public CosmicStrawberry() {
        super(ID, RelicTier.SHOP, LandingSound.FLAT);
    }

    public void atBattleStart() {
        ArrayList<AbstractCard> rareCards = new ArrayList<>();

        addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        flash();

        for (AbstractCard c : AbstractDungeon.player.drawPile.group) {
            if (c.rarity == AbstractCard.CardRarity.RARE || c.rarity == CustomRarity.EXOTIC) {
                rareCards.add(c);
            }
        }

        if  (!rareCards.isEmpty()) {
            AbstractCard c2 = rareCards.get(AbstractDungeon.cardRng.random(rareCards.size()-1));

            AbstractDungeon.player.hand.group.remove(c2);
            AbstractDungeon.player.drawPile.group.remove(c2);
            AbstractDungeon.player.discardPile.group.remove(c2);
            AbstractDungeon.getCurrRoom().souls.remove(c2);
            AbstractDungeon.player.limbo.group.add(c2);
            c2.targetAngle = 0.0F;
            c2.lighten(false);
            c2.drawScale = 0.12F;
            c2.targetDrawScale = 0.75F;
            c2.applyPowers();
            int energy = EnergyPanel.getCurrentEnergy();
            EnergyPanel.setEnergy(AbstractDungeon.player.energy.energyMaster);
            addToTop(new AbstractGameAction() {
                @Override
                public void update() {
                    EnergyPanel.setEnergy(energy);
                    this.isDone = true;
                }
            });
            addToTop(new NewQueueCardAction(c2, true, false, true));
            addToTop(new UnlimboAction(c2));
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}