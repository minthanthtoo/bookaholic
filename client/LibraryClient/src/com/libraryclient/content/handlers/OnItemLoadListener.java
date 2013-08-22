package com.libraryclient.content.handlers;

import com.libraryclient.connection.*;
import com.libraryclient.content.*;
import com.libraryclient.content.items.*;

public interface OnItemLoadListener<I extends Item>
{

	public void onItemLoaded(Connector r, I i);

	public void onStartLoading(Connector r);

	public void onFinishLoading(Connector r);

}
