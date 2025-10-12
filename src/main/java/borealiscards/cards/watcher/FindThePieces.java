package borealiscards.cards.watcher;

import basemod.helpers.CardModifierManager;
import borealiscards.cardmods.FindThePiecesMod;
import borealiscards.cards.BaseCard;
import borealiscards.util.CardStats;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.watcher.MantraPower;

public class FindThePieces extends BaseCard {
    public static final String ID = makeID(FindThePieces.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CardColor.PURPLE,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            1
    );

    public FindThePieces() {
        super(ID, info);
        setSelfRetain(true);
        setCostUpgrade(0);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int findThePieces = 0;

        for(AbstractCard c : AbstractDungeon.player.hand.group) {
            if(CardModifierManager.hasModifier(c, FindThePiecesMod.ID)){
                findThePieces++;
            }
        }

        addToBot(new ApplyPowerAction(p, p, new MantraPower(p, findThePieces)));
    }

    public void applyPowers() {
        int findThePieces = 0;

        for(AbstractCard c : AbstractDungeon.player.hand.group) {
            if(CardModifierManager.hasModifier(c, FindThePiecesMod.ID)){
                findThePieces++;
            }
        }

        if (findThePieces > 0) {
            rawDescription = cardStrings.DESCRIPTION + String.format(cardStrings.EXTENDED_DESCRIPTION[0], findThePieces);
            initializeDescription();
        }

        super.applyPowers();
    }
}
