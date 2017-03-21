package com.cong.chenchong.dialog;

import com.cong.chenchong.dialog.effects.BaseEffects;
import com.cong.chenchong.dialog.effects.FadeIn;
import com.cong.chenchong.dialog.effects.Fall;
import com.cong.chenchong.dialog.effects.FlipH;
import com.cong.chenchong.dialog.effects.FlipV;
import com.cong.chenchong.dialog.effects.NewsPaper;
import com.cong.chenchong.dialog.effects.RotateBottom;
import com.cong.chenchong.dialog.effects.RotateLeft;
import com.cong.chenchong.dialog.effects.Shake;
import com.cong.chenchong.dialog.effects.SideFall;
import com.cong.chenchong.dialog.effects.SlideBottom;
import com.cong.chenchong.dialog.effects.SlideLeft;
import com.cong.chenchong.dialog.effects.SlideRight;
import com.cong.chenchong.dialog.effects.SlideTop;
import com.cong.chenchong.dialog.effects.Slit;


/**
 * Created by lee on 2014/7/30.
 */
public enum  Effectstype {

    Fadein(FadeIn.class),
    Slideleft(SlideLeft.class),
    Slidetop(SlideTop.class),
    SlideBottom(SlideBottom.class),
    Slideright(SlideRight.class),
    Fall(Fall.class),
    Newspager(NewsPaper.class),
    Fliph(FlipH.class),
    Flipv(FlipV.class),
    RotateBottom(RotateBottom.class),
    RotateLeft(RotateLeft.class),
    Slit(Slit.class),
    Shake(Shake.class),
    Sidefill(SideFall.class);
    private Class<? extends BaseEffects> effectsClazz;

    private Effectstype(Class<? extends BaseEffects> mclass) {
        effectsClazz = mclass;
    }

    public BaseEffects getAnimator() {
        BaseEffects bEffects=null;
	try {
		bEffects = effectsClazz.newInstance();
	} catch (ClassCastException e) {
		throw new Error("Can not init animatorClazz instance");
	} catch (InstantiationException e) {
		// TODO Auto-generated catch block
		throw new Error("Can not init animatorClazz instance");
	} catch (IllegalAccessException e) {
		// TODO Auto-generated catch block
		throw new Error("Can not init animatorClazz instance");
	}
	return bEffects;
    }
}
