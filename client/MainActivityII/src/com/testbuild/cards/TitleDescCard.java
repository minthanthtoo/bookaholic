package com.testbuild.cards;

import android.content.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;
import com.fima.cardsui.objects.*;
import com.testbuild.*;

public class TitleDescCard extends Card
{

	private TextView descView;
	private boolean isExpanded;

	public TitleDescCard(String title){
		super(title);
	}

	@Override
	public View getCardContent(Context context) {
		View view = LayoutInflater.from(context).inflate(R.layout.card_ex, null);

		((TextView) view.findViewById(R.id.title)).setText(title);
		descView = (TextView)view.findViewById(R.id.description);
		if(isExpanded){
			descView.setMaxLines(Integer.MAX_VALUE);
		}
		descView.setOnClickListener(new OnClickListener(){

				public void onClick(View p1)
				{
					isExpanded=true;
					descView.setMaxLines(Integer.MAX_VALUE);
					descView.getParent().requestLayout();
				}


			});
		return view;
	}




}
