package borealiscards.cards.curses;

import basemod.AutoAdd;
import borealiscards.cards.BaseCard;
import borealiscards.patches.AddCardToPilePatch;
import borealiscards.powers.InstabilityPower;
import borealiscards.util.CardStats;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.SoulboundField;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

@AutoAdd.Ignore
public class ElectromagneticInstability extends BaseCard implements AddCardToPilePatch.AddToPileInterface {
    public static final String ID = makeID(ElectromagneticInstability.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CardColor.CURSE,
            CardType.CURSE,
            CardRarity.SPECIAL,
            CardTarget.SELF,
            -2
    );

    public ElectromagneticInstability() {
        super(ID, info);
        SoulboundField.soulbound.set(this,true);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
    }

    @Override
    public void onAddCard(CardGroup group, AbstractCard card) {
        if(group == AbstractDungeon.player.hand) {
            addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new InstabilityPower(AbstractDungeon.player, 1)));
        }
    }
}
