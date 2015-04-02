/*******************************************************************************
 * Copyright 2013 Ednovo d/b/a Gooru. All rights reserved.

 * 
 *  http://www.goorulearning.org/
 * 
 *  Permission is hereby granted, free of charge, to any person obtaining
 *  a copy of this software and associated documentation files (the
 *  "Software"), to deal in the Software without restriction, including
 *  without limitation the rights to use, copy, modify, merge, publish,
 *  distribute, sublicense, and/or sell copies of the Software, and to
 *  permit persons to whom the Software is furnished to do so, subject to
 *  the following conditions:
 * 
 *  The above copyright notice and this permission notice shall be
 *  included in all copies or substantial portions of the Software.
 * 
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 *  EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 *  MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 *  NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 *  LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 *  OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 *  WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 ******************************************************************************/
package org.ednovo.gooru.client.mvp.rating.events;

import com.google.gwt.event.shared.GwtEvent;

public class UpdateRatingsInRealTimeEvent extends GwtEvent<UpdateRatingsInRealTimeHandler> {

	public static final Type<UpdateRatingsInRealTimeHandler> TYPE = new Type<UpdateRatingsInRealTimeHandler>();
	
	private String gooruOid;
	private double average;
	private Integer count;
	
	public UpdateRatingsInRealTimeEvent(String gooruOid, double average,Integer count) {
		this.gooruOid=gooruOid;
		this.average=average;
		this.count=count;
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<UpdateRatingsInRealTimeHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(UpdateRatingsInRealTimeHandler handler) {
		handler.updateRatingInRealTime(gooruOid,average,count);
	}

}
