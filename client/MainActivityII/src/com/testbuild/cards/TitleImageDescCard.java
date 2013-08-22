package com.testbuild.cards;

import android.content.*;
import android.view.*;
import android.widget.*;
import com.fima.cardsui.objects.*;
import com.testbuild.*;

public class TitleImageDescCard extends Card {

	public TitleImageDescCard(String title, int image){
		super(title, image);
	}

	@Override
	public View getCardContent(Context context) {
		View view = LayoutInflater.from(context).inflate(R.layout.card_picture, null);

		((TextView) view.findViewById(R.id.title)).setText(title);
		((ImageView) view.findViewById(R.id.imageView1)).setImageResource(image);
		
		return view;
	}

	
	
	
}