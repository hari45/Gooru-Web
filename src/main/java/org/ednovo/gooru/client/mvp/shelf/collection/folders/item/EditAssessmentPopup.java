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
package org.ednovo.gooru.client.mvp.shelf.collection.folders.item;

import java.util.HashMap;
import java.util.Map;

import org.ednovo.gooru.client.gin.AppClientFactory;
import org.ednovo.gooru.client.uc.TextBoxWithPlaceholder;
import org.ednovo.gooru.client.util.SetStyleForProfanity;
import org.ednovo.gooru.shared.i18n.MessageProperties;
import org.ednovo.gooru.shared.model.folder.FolderDo;
import org.ednovo.gooru.shared.util.StringUtil;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.Widget;

public abstract class EditAssessmentPopup extends PopupPanel {

	private static EditAssessmentPopupUiBinder uiBinder = GWT
			.create(EditAssessmentPopupUiBinder.class);

	interface EditAssessmentPopupUiBinder extends
			UiBinder<Widget, EditAssessmentPopup> {
	}
	
	static MessageProperties i18n = GWT.create(MessageProperties.class);

	@UiField Button btnSaveAssessment,btnCancelAssessment;
	@UiField Label lblExistingAssessmentError,lblExistingAssessmentURLError;
	@UiField TextBoxWithPlaceholder txtExistingAssessmentTitle,txtExistingAssessmentURL;
	@UiField TextArea txtExistingAssessmentDescription;
	@UiField RadioButton rdBtnAssessmentPublic,rdBtnAssessmentShare,rdBtnAssessmentPrivate,requireLoginYes,requireLoginNo;
	String privacy="";
	final String PUBLIC="public",ANYONEWITHLINK="anyonewithlink",PRIVATE="private";
	
	public FolderDo folderDo=null;
	
	public EditAssessmentPopup() {
		setWidget(uiBinder.createAndBindUi(this));
	}
	public EditAssessmentPopup(FolderDo folderDo) {
		setWidget(uiBinder.createAndBindUi(this));
		this.folderDo=folderDo;
		
		txtExistingAssessmentTitle.setPlaceholder(i18n.GL3168());
		txtExistingAssessmentURL.setPlaceholder(i18n.GL3124());
		if(!StringUtil.isEmpty(folderDo.getTitle()))
		txtExistingAssessmentTitle.setText(folderDo.getTitle());
		if(!StringUtil.isEmpty(folderDo.getUrl()))
		txtExistingAssessmentURL.setText(folderDo.getUrl());
		if(!StringUtil.isEmpty(folderDo.getGoals())){
			txtExistingAssessmentDescription.setText(folderDo.getGoals());
		}
		if(PUBLIC.equalsIgnoreCase(folderDo.getSharing())){
			rdBtnAssessmentPublic.setValue(true);
		}else{
			if(PRIVATE.equalsIgnoreCase(folderDo.getSharing())){
				rdBtnAssessmentPrivate.setValue(true);
			}else{
				rdBtnAssessmentShare.setValue(true);
			}
		}
		if(folderDo.getSettings()!=null && folderDo.getSettings().getIsLoginRequired()!=null && folderDo.getSettings().getIsLoginRequired().equalsIgnoreCase("true")){
			requireLoginYes.setValue(true);
		}else{
			requireLoginNo.setValue(true);
		}
		// This will handle the focus on existing assessment title.
		txtExistingAssessmentTitle.addFocusHandler(new FocusHandler() {
			@Override
			public void onFocus(FocusEvent event) {
				if (lblExistingAssessmentError.isVisible()) {
					lblExistingAssessmentError.setVisible(false);
					txtExistingAssessmentTitle.getElement().removeAttribute("style");
				}
			}
		});
		// This will handle the focus on existing assessment URL.
		txtExistingAssessmentURL.addFocusHandler(new FocusHandler() {
			@Override
			public void onFocus(FocusEvent event) {
				if (lblExistingAssessmentURLError.isVisible()) {
					lblExistingAssessmentURLError.setVisible(false);
					txtExistingAssessmentURL.getElement().removeAttribute("style");
				}
			}
		});
	}
	@UiHandler("btnSaveAssessment")
	public void clickEventOnSaveAssessment(final ClickEvent event){
		//Code when save or update assessment clicked
		final String assessmentExistingTitle=txtExistingAssessmentTitle.getText();
		final String assessmentURL=txtExistingAssessmentURL.getText();
		if(rdBtnAssessmentPublic.getValue()){
			privacy=PUBLIC;
		}
		if(rdBtnAssessmentShare.getValue()){
			privacy=ANYONEWITHLINK;
		}
		if(rdBtnAssessmentPrivate.getValue()){
			privacy=PRIVATE;
		}
		
		if(StringUtil.isEmpty(assessmentExistingTitle)){
			lblExistingAssessmentError.setVisible(true);
			lblExistingAssessmentError.setText(i18n.GL1026());
		}else if(StringUtil.isEmpty(assessmentURL)){
			lblExistingAssessmentError.setVisible(false);
			lblExistingAssessmentError.setText("");
			lblExistingAssessmentURLError.setVisible(true);
			lblExistingAssessmentURLError.setText(i18n.GL3166());
		}else{
			if(!StringUtil.urlValidatior(assessmentURL)){
				lblExistingAssessmentError.setVisible(false);
				lblExistingAssessmentError.setText("");
				lblExistingAssessmentURLError.setVisible(true);
				lblExistingAssessmentURLError.setText(i18n.GL0926());
			}else{
				final Map<String, String> parms = new HashMap<String, String>();
				parms.put("text", assessmentExistingTitle);
				AppClientFactory.getInjector().getResourceService().checkProfanity(parms,new AsyncCallback<Boolean>() {
					@Override
					public void onSuccess(Boolean value) {
						if(value){
							//Displaying error message
							SetStyleForProfanity.SetStyleForProfanityForTextBoxWithPlaceholder(txtExistingAssessmentTitle, lblExistingAssessmentError, value);
						}else{
							lblExistingAssessmentError.setVisible(false);
							lblExistingAssessmentError.setText("");
							parms.put("text", assessmentURL);
							AppClientFactory.getInjector().getResourceService().checkProfanity(parms,new AsyncCallback<Boolean>() {
								@Override
								public void onSuccess(Boolean result) {
									if(result){
										//Displaying error message
										SetStyleForProfanity.SetStyleForProfanityForTextBoxWithPlaceholder(txtExistingAssessmentURL, lblExistingAssessmentURLError, result);
									}else{
										lblExistingAssessmentURLError.setVisible(false);
										lblExistingAssessmentURLError.setText("");
										//Update code here
										AppClientFactory.getInjector().getResourceService().updateAssessmentDetails(folderDo.getGooruOid(), assessmentExistingTitle, assessmentURL,txtExistingAssessmentDescription.getText(),privacy,requireLoginYes.getValue().toString(), new AsyncCallback<FolderDo>() {
											
											@Override
											public void onSuccess(FolderDo result) {
												clickEventOnSaveAssessmentHandler(result);
											}
											
											@Override
											public void onFailure(Throwable caught) {
											}
										});
									}
								}
								@Override
								public void onFailure(Throwable caught) {
									
								}
							});
						}
					}
					@Override
					public void onFailure(Throwable caught) {
					}
				});
			}
		}
	}
	@UiHandler("btnCancelAssessment")
	public void clickEventOnCancelAssessment(ClickEvent event){
		clickEventOnCancelAssessmentHandler(event);
	}
	@UiHandler("rdBtnAssessmentPublic")
	public void onClickOfPublicRadioButton(ClickEvent e){
		resetReadioButtons();
		rdBtnAssessmentPublic.setValue(true);
	}
	@UiHandler("rdBtnAssessmentShare")
	public void onClickOfShareRadioButton(ClickEvent e){
		resetReadioButtons();
		rdBtnAssessmentShare.setValue(true);
	}
	@UiHandler("rdBtnAssessmentPrivate")
	public void onClickOfPrivateRadioButton(ClickEvent e){
		resetReadioButtons();
		rdBtnAssessmentPrivate.setValue(true);
	}
	public void resetReadioButtons(){
		rdBtnAssessmentPublic.setValue(false);
		rdBtnAssessmentShare.setValue(false);
		rdBtnAssessmentPrivate.setValue(false);
	}
	@UiHandler("requireLoginYes")
	public void onClickOfrequireLoginYesRadioButton(ClickEvent e){
		resetRequireReadioButtons();
		requireLoginYes.setValue(true);
	}
	@UiHandler("requireLoginNo")
	public void onClickOfrequireLoginNoRadioButton(ClickEvent e){
		resetRequireReadioButtons();
		requireLoginNo.setValue(true);
	}
	public void resetRequireReadioButtons(){
		requireLoginYes.setValue(false);
		requireLoginNo.setValue(false);
	}
	abstract void clickEventOnSaveAssessmentHandler(FolderDo result);
	abstract void clickEventOnCancelAssessmentHandler(ClickEvent event);
}
