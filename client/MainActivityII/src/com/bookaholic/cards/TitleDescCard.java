package com.bookaholic.cards;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.bookaholic.R;
import com.fima.cardsui.objects.Card;

public class TitleDescCard extends Card {

	private TextView descView;
	private boolean isExpanded;

	public TitleDescCard(String title) {
		super(title);
	}

	@Override
	public View getCardContent(Context context) {
		View view = LayoutInflater.from(context)
				.inflate(R.layout.card_ex, null);

		((TextView) view.findViewById(R.id.title)).setText(title);
		descView = (TextView) view.findViewById(R.id.description);
		if (isExpanded) {
			descView.setMaxLines(Integer.MAX_VALUE);
		}
		descView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View p1) {
				isExpanded = true;
				descView.setMaxLines(Integer.MAX_VALUE);
				descView.getParent().requestLayout();
			}

		});
		return view;
	}

}
