package borealiscards.cards.watcher;

import borealiscards.cards.BaseCard;
import borealiscards.patches.rarities.CustomRarity;
import borealiscards.powers.IncensedPower;
import borealiscards.util.CardStats;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.stances.WrathStance;

public class AmbrosialSpice extends BaseCard {
    public static final String ID = makeID(AmbrosialSpice.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CardColor.PURPLE,
            CardType.POWER,
            CustomRarity.SHOP,
            CardTarget.SELF,
            1
    );

    public static int spiceChance = 5;

    public AmbrosialSpice() {
        super(ID, info);
        setCostUpgrade(0);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        switch (playedThisCombat()) {
            case 1:
                addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, 1)));
                addToBot(new ApplyPowerAction(p, p, new DexterityPower(p, 1)));
                addToBot(new DrawCardAction(1));
                spiceChance = 10;
                break;
            case 2:
                addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, 2)));
                addToBot(new DrawCardAction(2));
                spiceChance = 20;
                break;
            case 3:
                addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, 2)));
                addToBot(new ApplyPowerAction(p, p, new DexterityPower(p, -2)));
                addToBot(new DrawCardAction(1));
                spiceChance = 30;
                break;
            case 4:
                addToBot(new ChangeStanceAction(new WrathStance()));
                addToBot(new ApplyPowerAction(p, p, new IncensedPower(p)));
                addToBot(new ApplyPowerAction(p, p, new DexterityPower(p, -1)));
                addToBot(new DrawCardAction(1));
                spiceChance = 40;
                break;
            default:
                addToBot(new ApplyPowerAction(p, p, new VulnerablePower(p, 1, true)));
                addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, 3)));
                addToBot(new ApplyPowerAction(p, p, new DexterityPower(p, -1)));
                addToBot(new DrawCardAction(1));
                spiceChance = 50;

        }
    }

    private int playedThisCombat() {
        int i = 0;

        for (AbstractCard c : AbstractDungeon.actionManager.cardsPlayedThisCombat) {
            if (c.cardID == AmbrosialSpice.ID) {
                i++;
            }
        }

        return i;
    }

    public void applyPowers() {
        switch (playedThisCombat()) {
            case 0:
                rawDescription = cardStrings.DESCRIPTION;
                break;
            case 1:
                rawDescription = cardStrings.EXTENDED_DESCRIPTION[0];
                break;
            case 2:
                rawDescription = cardStrings.EXTENDED_DESCRIPTION[1];
                break;
            case 3:
                rawDescription = cardStrings.EXTENDED_DESCRIPTION[2];
                break;
            default:
                rawDescription = cardStrings.EXTENDED_DESCRIPTION[3];
        }
        initializeDescription();
        super.applyPowers();
    }
}
