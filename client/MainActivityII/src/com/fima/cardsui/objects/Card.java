package com.fima.cardsui.objects;

import android.content.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;
import com.fima.cardsui.*;
import com.testbuild.*;

public abstract class Card extends AbstractCard
{

	public interface OnCardSwiped
	{
		public void onCardSwiped(Card card, View layout);
	}

	private OnCardSwiped onCardSwipedListener;
	private OnClickListener mListener;
	protected View mCardLayout;

	public Card()
	{

	}

	public Card(String title)
	{
		this.title = title;
	}

	public Card(String title, int image)
	{
		this.title = title;
		this.image = image;
	}

	public Card(String title, String desc, int image)
	{
		this.title = title;
		this.desc = desc;
		this.image = image;
	}

	@Override
	public View getView(Context context, boolean swipable)
	{
		return getView(context, false);
	}

	@Override
	public View getView(Context context)
	{

		View view = LayoutInflater.from(context).inflate(getCardLayout(), null);

		mCardLayout = view;

		try
		{
			((FrameLayout) view.findViewById(R.id.cardContent))
				.addView(getCardContent(context));
		}
		catch (NullPointerException e)
		{
			e.printStackTrace();
		}

		// ((TextView) view.findViewById(R.id.title)).setText(this.title);

		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
			LinearLayout.LayoutParams.MATCH_PARENT,
			LinearLayout.LayoutParams.WRAP_CONTENT);
		int bottom = Utils.convertDpToPixelInt(context, 12);
		lp.setMargins(0, 0, 0, bottom);

		view.setLayoutParams(lp);

		return view;
	}

	public View getViewLast(Context context)
	{

		View view = LayoutInflater.from(context).inflate(getLastCardLayout(),
														 null);

		mCardLayout = view;

		try
		{
			((FrameLayout) view.findViewById(R.id.cardContent))
				.addView(getCardContent(context));
		}
		catch (NullPointerException e)
		{
			e.printStackTrace();
		}

		// ((TextView) view.findViewById(R.id.title)).setText(this.title);

		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
			LinearLayout.LayoutParams.MATCH_PARENT,
			LinearLayout.LayoutParams.WRAP_CONTENT);
		int bottom = Utils.convertDpToPixelInt(context, 12);
		lp.setMargins(0, 0, 0, bottom);

		view.setLayoutParams(lp);

		return view;
	}

	public View getViewFirst(Context context)
	{

		View view = LayoutInflater.from(context).inflate(getFirstCardLayout(),
														 null);

		mCardLayout = view;

		try
		{
			((FrameLayout) view.findViewById(R.id.cardContent))
				.addView(getCardContent(context));
		}
		catch (NullPointerException e)
		{
			e.printStackTrace();
		}

		// ((TextView) view.findViewById(R.id.title)).setText(this.title);

		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
			LinearLayout.LayoutParams.MATCH_PARENT,
			LinearLayout.LayoutParams.WRAP_CONTENT);
		int bottom = Utils.convertDpToPixelInt(context, 12);
		lp.setMargins(0, 0, 0, bottom);

		view.setLayoutParams(lp);

		return view;
	}

	public View getView(Context context, int stackPositionConstant)
	{

		View view = LayoutInflater.from(context).inflate(getCardLayout(stackPositionConstant), null);

		mCardLayout = view;

		try
		{
			((FrameLayout) view.findViewById(R.id.cardContent))
				.addView(getCardContent(context));
		}
		catch (NullPointerException e)
		{
			e.printStackTrace();
		}

		// ((TextView) view.findViewById(R.id.title)).setText(this.title);

		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
			LinearLayout.LayoutParams.MATCH_PARENT,
			LinearLayout.LayoutParams.WRAP_CONTENT);
		int bottom = Utils.convertDpToPixelInt(context, 12);
		lp.setMargins(0, 0, 0, bottom);

		view.setLayoutParams(lp);

		return view;
	}

	public View getViewLast(Context context, int stackPositionConstant)
	{

		View view = LayoutInflater.from(context).inflate(getLastCardLayout(stackPositionConstant),
														 null);

		mCardLayout = view;

		try
		{
			((FrameLayout) view.findViewById(R.id.cardContent))
				.addView(getCardContent(context));
		}
		catch (NullPointerException e)
		{
			e.printStackTrace();
		}

		// ((TextView) view.findViewById(R.id.title)).setText(this.title);

		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
			LinearLayout.LayoutParams.MATCH_PARENT,
			LinearLayout.LayoutParams.WRAP_CONTENT);
		int bottom = Utils.convertDpToPixelInt(context, 12);
		lp.setMargins(0, 0, 0, bottom);

		view.setLayoutParams(lp);

		return view;
	}

	public View getViewFirst(Context context, int stackPositionConstant)
	{

		View view = LayoutInflater.from(context).inflate(getFirstCardLayout(stackPositionConstant),
														 null);

		mCardLayout = view;

		try
		{
			((FrameLayout) view.findViewById(R.id.cardContent))
				.addView(getCardContent(context));
		}
		catch (NullPointerException e)
		{
			e.printStackTrace();
		}

		// ((TextView) view.findViewById(R.id.title)).setText(this.title);

		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
			LinearLayout.LayoutParams.MATCH_PARENT,
			LinearLayout.LayoutParams.WRAP_CONTENT);
		int bottom = Utils.convertDpToPixelInt(context, 12);
		lp.setMargins(0, 0, 0, bottom);

		view.setLayoutParams(lp);

		return view;
	}
	public abstract View getCardContent(Context context);

	public OnClickListener getClickListener()
	{
		return mListener;
	}

	public void setOnClickListener(OnClickListener listener)
	{
		mListener = listener;
	}

	public void OnSwipeCard()
	{
		if (onCardSwipedListener != null)
			onCardSwipedListener.onCardSwiped(this, mCardLayout);
		// TODO: find better implementation to get card-object's used content
		// layout (=> implementing getCardContent());
	}

	public OnCardSwiped getOnCardSwipedListener()
	{
		return onCardSwipedListener;
	}

	public void setOnCardSwipedListener(OnCardSwiped onEpisodeSwipedListener)
	{
		this.onCardSwipedListener = onEpisodeSwipedListener;
	}

	protected int getCardLayout()
	{
		return R.layout.item_card_empty;
	}

	protected int getLastCardLayout()
	{
		return R.layout.item_card_empty_last;
	}

	protected int getFirstCardLayout()
	{
		return R.layout.item_card_empty_first;
	}

	protected int getCardLayout(int stackPositionConst)
	{
		switch (stackPositionConst)
		{
			case CardStack.STACK_POS_TYPE_LEFT_TOP:
				return R.layout.item_card_empty_left_top;
			case CardStack.STACK_POS_TYPE_TOP:
				return R.layout.item_card_empty_top;
			case CardStack.STACK_POS_TYPE_RIGHT_TOP:
				return R.layout.item_card_empty_right_top;
			case CardStack.STACK_POS_TYPE_LEFT_MIDDLE:
				return R.layout.item_card_empty_left_middle;
			case CardStack.STACK_POS_TYPE_MIDDLE:
				return R.layout.item_card_empty_middle;
			case CardStack.STACK_POS_TYPE_RIGHT_MIDDLE:
				return R.layout.item_card_empty_right_middle;
			case CardStack.STACK_POS_TYPE_LEFT_BOTTOM:
				return R.layout.item_card_empty_left_bottom;
			case CardStack.STACK_POS_TYPE_BOTTOM:
				return R.layout.item_card_empty_bottom;
			case CardStack.STACK_POS_TYPE_RIGHT_BOTTOM:
				return R.layout.item_card_empty_right_bottom;
			default :
				return R.layout.item_card_empty;
		}
	}

	protected int getLastCardLayout(int stackPositionConst)
	{
		switch (stackPositionConst)
		{
			case CardStack.STACK_POS_TYPE_LEFT_TOP:
				return R.layout.item_card_empty_left_top;
			case CardStack.STACK_POS_TYPE_TOP:
				return R.layout.item_card_empty_top;
			case CardStack.STACK_POS_TYPE_RIGHT_TOP:
				return R.layout.item_card_empty_right_top;
			case CardStack.STACK_POS_TYPE_LEFT_MIDDLE:
				return R.layout.item_card_empty_left_middle;
			case CardStack.STACK_POS_TYPE_MIDDLE:
				return R.layout.item_card_empty_middle;
			case CardStack.STACK_POS_TYPE_RIGHT_MIDDLE:
				return R.layout.item_card_empty_right_middle;
			case CardStack.STACK_POS_TYPE_LEFT_BOTTOM:
				return R.layout.item_card_empty_left_bottom;
			case CardStack.STACK_POS_TYPE_BOTTOM:
				return R.layout.item_card_empty_bottom;
			case CardStack.STACK_POS_TYPE_RIGHT_BOTTOM:
				return R.layout.item_card_empty_right_bottom;
			default :
				return R.layout.item_card_empty;
		}
	}

	protected int getFirstCardLayout(int stackPositionConst)
	{
		switch (stackPositionConst)
		{
			case CardStack.STACK_POS_TYPE_LEFT_TOP:
				return R.layout.item_card_empty_left_top;
			case CardStack.STACK_POS_TYPE_TOP:
				return R.layout.item_card_empty_top;
			case CardStack.STACK_POS_TYPE_RIGHT_TOP:
				return R.layout.item_card_empty_right_top;
			case CardStack.STACK_POS_TYPE_LEFT_MIDDLE:
				return R.layout.item_card_empty_left_middle;
			case CardStack.STACK_POS_TYPE_MIDDLE:
				return R.layout.item_card_empty_middle;
			case CardStack.STACK_POS_TYPE_RIGHT_MIDDLE:
				return R.layout.item_card_empty_right_middle;
			case CardStack.STACK_POS_TYPE_LEFT_BOTTOM:
				return R.layout.item_card_empty_left_bottom;
			case CardStack.STACK_POS_TYPE_BOTTOM:
				return R.layout.item_card_empty_bottom;
			case CardStack.STACK_POS_TYPE_RIGHT_BOTTOM:
				return R.layout.item_card_empty_right_bottom;
			default :
				return R.layout.item_card_empty;
		}
	}

}