package borealiscards.patches;

import borealiscards.stances.TensionStance;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.esotericsoftware.spine.*;
import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.characters.Watcher;
import com.megacrit.cardcrawl.core.Settings;

import static borealiscards.BorealisCards.characterPath;

@SpirePatch2(clz = Watcher.class, method = "onStanceChange")
public class TensionStaffPatch {
    @SpirePrefixPatch
    public static SpireReturn<Void> TensionStaff(Watcher __instance, @ByRef TextureAtlas[] ___eyeAtlas, @ByRef Skeleton[] ___eyeSkeleton, @ByRef AnimationStateData[] ___eyeStateData, String newStance) {
        if(newStance.equals(TensionStance.STANCE_ID)) {
            ___eyeAtlas[0] = new TextureAtlas(characterPath("skeleton_1.atlas"));
            SkeletonJson json = new SkeletonJson(___eyeAtlas[0]);
            json.setScale(Settings.scale / 1.0F);
            SkeletonData skeletonData = json.readSkeletonData(Gdx.files.internal("borealiscards/images/character/skeleton_1.json"));
            ___eyeSkeleton[0] = new Skeleton(skeletonData);
            ___eyeSkeleton[0].setColor(Color.WHITE);
            ___eyeStateData[0] = new AnimationStateData(skeletonData);
            __instance.eyeState = new AnimationState(___eyeStateData[0]);
            ___eyeStateData[0].setDefaultMix(0.2F);
            __instance.eyeState.setAnimation(0, "Tension", true);
            return SpireReturn.Return();
        } else {
            ___eyeAtlas[0] = new TextureAtlas(Gdx.files.internal("images/characters/watcher/eye_anim/skeleton.atlas"));
            SkeletonJson json = new SkeletonJson(___eyeAtlas[0]);
            json.setScale(Settings.scale / 1.0F);
            SkeletonData skeletonData = json.readSkeletonData(Gdx.files.internal("images/characters/watcher/eye_anim/skeleton.json"));
            ___eyeSkeleton[0] = new Skeleton(skeletonData);
            ___eyeSkeleton[0].setColor(Color.WHITE);
            ___eyeStateData[0] = new AnimationStateData(skeletonData);
            __instance.eyeState = new AnimationState(___eyeStateData[0]);
            ___eyeStateData[0].setDefaultMix(0.2F);
        }
        return SpireReturn.Continue();
    }
}
