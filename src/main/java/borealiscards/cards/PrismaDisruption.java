package borealiscards.cards;

import basemod.helpers.CardModifierManager;
import borealiscards.cardmods.PrismaMod;
import borealiscards.util.CardStats;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.SoulboundField;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class PrismaDisruption extends BaseCard {
    public static final String ID = makeID(PrismaDisruption.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CardColor.CURSE,
            CardType.CURSE,
            CardRarity.SPECIAL,
            CardTarget.NONE,
            -2
    );

    public PrismaDisruption() {
        super(ID, info);
        SoulboundField.soulbound.set(this, true);
    }

    public void triggerOnOtherCardPlayed(AbstractCard c) {
        CardModifierManager.addModifier(c, new PrismaMod());
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
    }
}
