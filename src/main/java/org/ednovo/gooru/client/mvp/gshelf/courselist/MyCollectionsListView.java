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
package org.ednovo.gooru.client.mvp.gshelf.courselist;

import java.util.Iterator;

import org.ednovo.gooru.application.client.gin.BaseViewWithHandlers;
import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.client.mvp.gshelf.util.ContentWidgetWithMove;
import org.ednovo.gooru.shared.util.ClientConstants;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class MyCollectionsListView  extends BaseViewWithHandlers<MyCollectionsListUiHandlers> implements IsMyCollectionsListView,ClientConstants  {

	private static MyCollectionsListViewUiBinder uiBinder = GWT.create(MyCollectionsListViewUiBinder.class);

	interface MyCollectionsListViewUiBinder extends UiBinder<Widget, MyCollectionsListView> {
	}
<<<<<<< HEAD:src/main/java/org/ednovo/gooru/client/mvp/gshelf/courselist/MyCollectionsListView.java
	
	@UiField HTMLPanel courseListContainer;
	@UiField VerticalPanel pnlCourseList;

	String type;
	
	public MyCollectionsListView() {
=======

	private static MessageProperties i18n = GWT.create(MessageProperties.class);

	@UiField HTMLPanel courseListContainer;
	@UiField VerticalPanel pnlCourseList;

	public CourseListView() {
>>>>>>> 6842ea90eae61387f006de50081453398ec950a1:src/main/java/org/ednovo/gooru/client/mvp/gshelf/courselist/CourseListView.java
		setWidget(uiBinder.createAndBindUi(this));
		courseListContainer.getElement().setId("gShelfCousesList");
	}

	private void resetWidgetPositions(){
		Iterator<Widget> widgets=pnlCourseList.iterator();
		int index=0;
		while (widgets.hasNext()){
			Widget widget=widgets.next();
			if(widget instanceof ContentWidgetWithMove){
				ContentWidgetWithMove contentWidgetWithMove=(ContentWidgetWithMove) widget;
				contentWidgetWithMove.getH3Panel().setText(type+" "+(index+1));
				contentWidgetWithMove.getTextBox().setText("");
				contentWidgetWithMove.getTextBox().getElement().setAttribute("index",index+"");
				index++;
			}
		}
	}

	@Override
	public void setData(String type) {
		this.type=type;
		for (int i = 0; i <10; i++) {
			final ContentWidgetWithMove widgetMove=new ContentWidgetWithMove(i,type) {
				@Override
				public void moveWidgetPosition(String movingPosition,String currentWidgetPosition, boolean isDownArrow) {
					int movingIndex= Integer.parseInt(movingPosition);
					if(pnlCourseList.getWidgetCount()>=movingIndex){
						if(!isDownArrow){
							movingIndex= (movingIndex-1);
							int currentIndex= Integer.parseInt(currentWidgetPosition);
							pnlCourseList.insert(pnlCourseList.getWidget(currentIndex), movingIndex);
						}else{
							int currentIndex= Integer.parseInt(currentWidgetPosition);
							pnlCourseList.insert(pnlCourseList.getWidget(currentIndex), movingIndex);
						}
						resetWidgetPositions();
					}
				}
			};
			pnlCourseList.add(widgetMove);
		}
	}
}
