package borealiscards.cards;

import borealiscards.patches.LostHealthThisTurnPatch;
import borealiscards.util.CardStats;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Backpedal extends BaseCard {
    public static final String ID = makeID(Backpedal.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CardColor.RED,
            CardType.SKILL,
            CardRarity.COMMON,
            CardTarget.SELF,
            1
    );

    public Backpedal() {
        super(ID, info);
        setBlock(5,3);
        setCustomVar("block2",VariableType.BLOCK,10,2);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p,block));
        if(LostHealthThisTurnPatch.hurtThisTurn){
            addToBot(new GainBlockAction(p,customVar("block2")));
        }
    }

    public void triggerOnGlowCheck() {
        if (LostHealthThisTurnPatch.hurtThisTurn) {
            this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
        } else {
            this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
        }
    }
}


