package com.fima.cardsui;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.fima.cardsui.objects.AbstractCard;
import com.fima.cardsui.objects.CardStack;

public class StackAdapter extends BaseAdapter {

	private Context mContext;
	private ArrayList<AbstractCard> mStacks;
	private boolean mSwipeable;

	public StackAdapter(Context context, ArrayList<AbstractCard> stacks,
						boolean swipable) {
		mContext = context;
		mStacks = stacks;
		mSwipeable = swipable;

	}

	@Override
	public int getCount() {
		return mStacks.size();
	}

	@Override
	public CardStack getItem(int position) {
		return (CardStack) mStacks.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final CardStack stack = getItem(position);
		stack.setAdapter(this);
		stack.setPosition(position);

		// TODO: caching is not working well

		// if (convertView != null) {
		// CardStack tagStack = (CardStack) convertView.getTag();
		// ArrayList<Card> tagCards = tagStack.getCards();
		// ArrayList<Card> cards = stack.getCards();
		// Card lastTagCard = tagCards.get(tagCards.size()-1);
		// if (!lastTagCard.equals(cards.get(cards.size()-1))) {
		// convertView = stack.getView(mContext);
		// convertView.setTag(stack);
		// }
		// } else if (convertView == null) {
		convertView = stack.getView(mContext, mSwipeable);
		// convertView.setTag(stack);
		// }

		return convertView;
	}

	public View getView(int position, int columnCount, View convertView, ViewGroup parent) {
		final CardStack stack = getItem(position);
		stack.setAdapter(this);
		stack.setPosition(position);

		// TODO: caching is not working well

		// if (convertView != null) {
		// CardStack tagStack = (CardStack) convertView.getTag();
		// ArrayList<Card> tagCards = tagStack.getCards();
		// ArrayList<Card> cards = stack.getCards();
		// Card lastTagCard = tagCards.get(tagCards.size()-1);
		// if (!lastTagCard.equals(cards.get(cards.size()-1))) {
		// convertView = stack.getView(mContext);
		// convertView.setTag(stack);
		// }
		// } else if (convertView == null) {
		//	convertView = stack.getView(mContext, mSwipeable);
		// convertView.setTag(stack);
		// }

		/***** start: cardStack position fixing *****/
		int b = (position+1)/columnCount;
		int c = (position+1)%columnCount;
		if(c == 1){// if position is in the 1st column
			if(b==0){
				position = CardStack.STACK_POS_TYPE_LEFT_TOP;
			}else if(getCount() <= position+columnCount){
				position = CardStack.STACK_POS_TYPE_LEFT_BOTTOM;
			}else{
				position = CardStack.STACK_POS_TYPE_LEFT_MIDDLE;
			}
		}else if(c==0){// if position is in the last column
			if(b==1){
				position = CardStack.STACK_POS_TYPE_RIGHT_TOP;
			}else if(getCount() <= position+columnCount){
				position = CardStack.STACK_POS_TYPE_RIGHT_BOTTOM;
			}else{
				position = CardStack.STACK_POS_TYPE_RIGHT_MIDDLE;
			}
		}else{ // if columnCount > 2 && position is in any of middle columns
			if(b==0){
				position = CardStack.STACK_POS_TYPE_TOP;
			}else if(getCount() <= position+columnCount){
				position = CardStack.STACK_POS_TYPE_BOTTOM;
			}else{
				position = CardStack.STACK_POS_TYPE_MIDDLE;
			}
		}
		convertView = stack.getView(mContext, mSwipeable,position);
		/***** end: cardStack position fixing *****/

		return convertView;
	}

	public void setItems(ArrayList<AbstractCard> stacks) {
		mStacks = stacks;
		notifyDataSetChanged();
	}

	public void setSwipeable(boolean b) {
		mSwipeable = b;
	}

	public void setItems(CardStack cardStack, int position) {
		mStacks.set(position, cardStack);
	}

}