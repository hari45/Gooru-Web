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

import org.ednovo.gooru.application.client.gin.BaseUiHandlers;
import org.ednovo.gooru.client.mvp.gshelf.util.LiPanelWithClose;
/**
 *
 * @fileName : ViewMorePeopleUiHandlers.java
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
public interface PreSearchUiHandlers extends BaseUiHandlers{

	/**
	 *
	 * @function refreshSearch
	 *
	 * @created_date : 23-Jun-2015
	 *
	 * @description
	 *
	 *
	 * @parm(s) : @param query
	 *
	 * @parm(s) : @param filterStd
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 *
	 *
	 *
	 */
	void refreshSearch(String query, String filterStd);

	void showStandardsPopup(String standardVal, String standardsDesc,
			List<LiPanelWithClose> collectionLiPanelWithCloseArray);

	void setSelectedStandards(List<Map<String, String>> standListArray);

	public void loadSubjects();

}
