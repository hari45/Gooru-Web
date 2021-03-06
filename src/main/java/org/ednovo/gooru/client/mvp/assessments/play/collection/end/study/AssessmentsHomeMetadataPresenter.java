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
package org.ednovo.gooru.client.mvp.assessments.play.collection.end.study;



import org.ednovo.gooru.application.shared.model.content.CollectionDo;
import org.ednovo.gooru.client.mvp.assessments.play.collection.AssessmentsPlayerPresenter;
import org.ednovo.gooru.client.mvp.assessments.play.collection.preview.AssessmentsPreviewPlayerPresenter;
import org.ednovo.gooru.client.mvp.gshelf.ShelfMainPresenter;

import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.PresenterWidget;

public class AssessmentsHomeMetadataPresenter extends PresenterWidget<IsAssessmentsHomeMetadataView> implements AssessmentsHomeMetadataUiHandlers{

	private AssessmentsPreviewPlayerPresenter previewPlayerPresenter=null;
	
	ShelfMainPresenter shelfMainPresenter;

	private AssessmentsPlayerPresenter collectionPlayerPresenter=null;
	@Inject
	public AssessmentsHomeMetadataPresenter(EventBus eventBus, IsAssessmentsHomeMetadataView view,ShelfMainPresenter shelfMainPresenter) {
		super(eventBus, view);
		getView().setUiHandlers(this);
		this.shelfMainPresenter=shelfMainPresenter;
	}
	public void setCollectionMetadata(CollectionDo collectionDo){
		getView().setCollectionMetadata(collectionDo);
	}
	@Override
	public void resetCollectionActivityEventId() {
		if(previewPlayerPresenter!=null){
			previewPlayerPresenter.resetcollectionActivityEventId();
		}
	}

	public AssessmentsPreviewPlayerPresenter getPreviewPlayerPresenter() {
		return previewPlayerPresenter;
	}
	public void setPreviewPlayerPresenter(AssessmentsPreviewPlayerPresenter previewPlayerPresenter) {
		this.previewPlayerPresenter = previewPlayerPresenter;
	}
	@Override
	public void triggerCollectionShareDataEvent(String collectionId,
			String itemType, String shareType, boolean confirmStatus) {
		if(collectionPlayerPresenter!=null){
			collectionPlayerPresenter.triggerCollectionShareDataEvent( collectionId, itemType,  shareType,  confirmStatus);
		}

	}
	public AssessmentsPlayerPresenter getCollectionPlayerPresenter() {
		return collectionPlayerPresenter;
	}
	public void setCollectionPlayerPresenter(AssessmentsPlayerPresenter collectionPlayerPresenter) {
		this.collectionPlayerPresenter = collectionPlayerPresenter;
	}
	@Override
	public void setDefultVersionAndType() {
		shelfMainPresenter.SetDefaultTypeAndVersion();
	}

}
