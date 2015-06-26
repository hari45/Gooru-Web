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
package org.ednovo.gooru.application.client.home.presearch;

import java.util.List;
import java.util.Map;

import org.ednovo.gooru.application.client.gin.IsViewWithHandlers;
import org.ednovo.gooru.application.shared.model.search.SearchFilterDo;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTMLPanel;
/**
 *
 * @fileName : IsViewMorePeopleView.java
 *
 * @description :
 *
 *
 * @version : 1.0
 *
 * @date: 16-Jun-2015
 *
 * @Author tumbalam
 *
 * @Reviewer:
 */
public interface IsPreSearchView extends IsViewWithHandlers<PreSearchUiHandlers>{


	/**
	 *
	 * @function setButtonVisibility
	 *
	 * @created_date : 23-Jun-2015
	 *
	 * @description
	 *
	 *
	 * @parm(s) :
	 *
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 *
	 *
	 *
	 */
	void setButtonVisibility();

	/**
	 *
	 * @function OnStandardsClickEvent
	 *
	 * @created_date : 23-Jun-2015
	 *
	 * @description
	 *
	 *
	 * @parm(s) : @param addBtn
	 *
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 *
	 *
	 *
	 */
	void OnStandardsClickEvent(Button addBtn);

	/**
	 *
	 * @function setUpdatedStandards
	 *
	 * @created_date : 23-Jun-2015
	 *
	 * @description
	 *
	 *
	 * @parm(s) : @param standsListArray
	 *
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 *
	 *
	 *
	 */
	void setUpdatedStandards(List<Map<String, String>> standsListArray);

	/**
	 *
	 * @function setSearchFilter
	 *
	 * @created_date : 23-Jun-2015
	 *
	 * @description
	 *
	 *
	 * @parm(s) : @param searchFilterDo
	 *
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 *
	 *
	 *
	 */
	void setSearchFilter(SearchFilterDo searchFilterDo);

	/**
	 *
	 * @function setDefaults
	 *
	 * @created_date : 23-Jun-2015
	 *
	 * @description
	 *
	 *
	 * @parm(s) :
	 *
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 *
	 *
	 *
	 */
	void setDefaults();

	/**
	 *
	 * @function getPanelGrades
	 *
	 * @created_date : 25-Jun-2015
	 *
	 * @description
	 *
	 *
	 * @parm(s) : @return
	 *
	 * @return : HTMLPanel
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 *
	 *
	 *
	 */
	HTMLPanel getPanelGrades();

}
