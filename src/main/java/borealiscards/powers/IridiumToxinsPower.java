package borealiscards.powers;

import borealiscards.util.TextureLoader;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.PoisonPower;

import static borealiscards.BorealisCards.makeID;

public class IridiumToxinsPower extends BasePower {
    public static final String POWER_ID = makeID(IridiumToxinsPower.class.getSimpleName());
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    public IridiumToxinsPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
        amount2 = 0;

        for (AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
            for (AbstractPower p : m.powers) {
                if (p.ID == PoisonPower.POWER_ID) {

                    String unPrefixed = "YellowPoison";
                    Texture normalTexture = TextureLoader.getPowerTexture(unPrefixed);
                    Texture hiDefImage = TextureLoader.getHiDefPowerTexture(unPrefixed);
                    if (hiDefImage != null) {
                        p.region128 = new TextureAtlas.AtlasRegion(hiDefImage, 0, 0, hiDefImage.getWidth(), hiDefImage.getHeight());
                        if (normalTexture != null)
                            p.region48 = new TextureAtlas.AtlasRegion(normalTexture, 0, 0, normalTexture.getWidth(), normalTexture.getHeight());
                    }

                }
            }
        }

    }

    @Override
    public void updateDescription() {
        description = String.format(DESCRIPTIONS[0], amount, amount2);
    }
}