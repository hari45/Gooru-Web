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
package org.ednovo.gooru.client.uc.tooltip;

import org.ednovo.gooru.application.shared.i18n.MessageProperties;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

public class StudyToolTip extends Composite {
	
	
	private MessageProperties i18n = GWT.create(MessageProperties.class); 
	
	@UiField HTMLPanel studyText,guidanceText;
	public StudyToolTip(){
		initWidget(studyToolTipUiBinder.createAndBindUi(this));
		studyText.getElement().setInnerHTML(i18n.GL0182());
		studyText.getElement().setId("pnlStudyText");
		studyText.getElement().setAttribute("alt", i18n.GL0182());
		studyText.getElement().setAttribute("title", i18n.GL0182());
		
		guidanceText.getElement().setInnerHTML(i18n.GL1066());
		guidanceText.getElement().setId("pnlGuidanceText");
		guidanceText.getElement().setAttribute("alt", i18n.GL1066());
		guidanceText.getElement().setAttribute("title", i18n.GL1066());
	}
	
	public interface StudyToolTipUiBinder extends UiBinder<Widget, StudyToolTip>{
		
	}
	
	public static StudyToolTipUiBinder studyToolTipUiBinder=GWT.create(StudyToolTipUiBinder.class);
	
	
}
