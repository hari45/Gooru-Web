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
package org.ednovo.gooru.client.mvp.play.collection.info;


import java.util.ArrayList;

import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.ednovo.gooru.client.PlaceTokens;
import org.ednovo.gooru.client.event.InvokeLoginEvent;
import org.ednovo.gooru.client.gin.AppClientFactory;
import org.ednovo.gooru.client.gin.BaseViewWithHandlers;
import org.ednovo.gooru.client.mvp.addTagesPopup.AddTagesPopupView;
import org.ednovo.gooru.client.mvp.play.collection.preview.PreviewPlayerPresenter;
import org.ednovo.gooru.client.mvp.rating.RatingWidgetView;
import org.ednovo.gooru.client.mvp.rating.events.DeletePlayerStarReviewEvent;
import org.ednovo.gooru.client.mvp.rating.events.DeletePlayerStarReviewHandler;
import org.ednovo.gooru.client.mvp.rating.events.OpenReviewPopUpEvent;
import org.ednovo.gooru.client.mvp.rating.events.UpdateRatingsInRealTimeEvent;
import org.ednovo.gooru.client.mvp.rating.events.UpdateRatingsInRealTimeHandler;
import org.ednovo.gooru.client.mvp.shelf.collection.tab.collaborators.vc.SuccessPopupViewVc;
import org.ednovo.gooru.client.uc.DownToolTipWidgetUc;
import org.ednovo.gooru.client.uc.HTMLEventPanel;
import org.ednovo.gooru.client.uc.PlayerBundle;
import org.ednovo.gooru.client.uc.StandardSgItemVc;
import org.ednovo.gooru.client.uc.ToolTipPopUp;
import org.ednovo.gooru.shared.model.code.CodeDo;
import org.ednovo.gooru.shared.model.content.CollectionItemDo;
import org.ednovo.gooru.shared.model.content.LicenseDo;
import org.ednovo.gooru.shared.model.content.ResoruceCollectionDo;
import org.ednovo.gooru.shared.model.search.ResourceSearchResultDo;
import org.ednovo.gooru.shared.util.MessageProperties;
import org.ednovo.gooru.shared.util.StringUtil;

import com.google.gwt.core.client.GWT;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.dom.client.ScrollEvent;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.resources.css.ast.CssProperty.Value;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;

public class ResourceInfoView extends BaseViewWithHandlers<ResourceInfoUiHandlers> implements IsResourceInfoView,MessageProperties{
	
	public static final String STANDARD_CODE = "code";
	public static final String STANDARD_DESCRIPTION = "description";
	private String title;
	private boolean isRatingUpdated=true;
	
	@UiField HTMLPanel resourceDescription,resourceDescriptionTitle,rightsLogoContainer,courseInfo,reosourceReleatedCollections,mobileFriendly,collectionsText,originalUrlText,publisherPanel,coursePanel,gradesPanel,
	mobileFriendlyPanel,DataTypePanel,interactivityTypePanel,eduAllignPanel,eduUsePanel,eduRolePanel,ageRangePanel,dKnowledgePanel,
	readingLevelPanel,languagePanel,countryCodePanel,copyRightPanel,hostPanel,
	accessibilityPanel,controlPanel,accessHazardPanel,mediaFeaturePanel,accessModePanel,thumbnailPanel,dateCreatedPanel,
	authorPanel,eduUseType,keyWordsPanel,keywordsInfo,readingLevelType,accessModeType,mediaFeatureType,dKnowledgeType,
	momentsoflearningPanel,momentsoflearningType,thumbnailurlValue,oerPanel,schoolLevelPanel,addsPanel,addsInfo,aggregatorPanel,aggregatorVal,lblPublisher;
	
	@UiField static  HTMLPanel standardsContentContainer;
	
	@UiField ScrollPanel scrollPanel;
	

	
	@UiField Label resourceTypeImage,resourceView,collectionsCount,lblresourceType,publisherText,courseText,legalText,learningobjectiveText,
					standardsText,hideText,resourceInfoText,gradeTitle,gradesText,originalUrlTitle,timeRequiredLabel,mbFriendlyLbl,
					mbFriendlyText,dataTypeLbl,dataTypeFormat,interactiveLbl,interactiveType,eduAllignLbl,eduAllignType,eduUseLbl,
					eduRoleLbl,eduRoleType,ageRangeLbl,ageRangeType,dKnowledgeLbl,readingLevelLbl,
					languageLbl,languageType,countryCodeLbl,countryCodeType,
					copyRightType,copyRightLbl,hostType,hostLbl,controlType,controlLbl,
					acessHazardlLbl,acessHazardType,mediaFeatureLbl,accessModelLbl,accesibilityLbl,generalLbl,
					thumbnailText,educationallLbl,resourceInfoLbl,dateCreatedLbl,
					createdDateInfo,authorLbl,authorName,keywordsTitle,timeRequiredvalue,
					momentsoflearningLbl,oerLbl,oerAvailability,schoolLevelLbl,addsTitle,schoolLevelType,aggregatorText;
	
	@UiField static Label standaInfo;
	
	@UiField FlowPanel standardsInfoConatiner,licenceContainer,ratingWidgetPanel;
	
	@UiField HTML resourceInfoSeparator,resourcetypeSeparator,lblcollectionName;
	@UiField
	HTMLEventPanel hideButton;
	
	@UiField Button addTagsBtn;
	
	AddTagesPopupView popup;
	
	CollectionItemDo collectionItemDoGlobal = new CollectionItemDo();
	
	ToolTipPopUp toolTip ; 
	
	private static final String SEPARATOR="|";
	
	private static final String ALL_GRADES = "ALL GRADES";
	
    private static final  String PAGE_SIZES="20";
    
    private static final String NOT_FRIENDY_TAG="not_iPad_friendly";
    
    
    private int collectionItemSizeData=0;
    
    private int totalItemSize=0;
    
    private Integer currentPageSize=1;
    
    private String gooruResourceOId;
    
    boolean isEducationalInfo=false;
    
    boolean isAccessibilityInfo=false;
    
    boolean isResourceInfo=false;
    
    boolean isGeneralInfo=false;
    
    boolean isTimeDuration =false;
    
    private RatingWidgetView ratingWidgetView=null;
   
    boolean isAggregator =false;
  
    boolean isPublisher =false;
   
	private static ResourceInfoViewUiBinder uiBinder = GWT.create(ResourceInfoViewUiBinder.class);

	interface ResourceInfoViewUiBinder extends UiBinder<Widget, ResourceInfoView> {
		
	}
	@Inject
	public ResourceInfoView(){
		setWidget(uiBinder.createAndBindUi(this));
		standardsInfoConatiner.clear();
		publisherText.setText(GL0566);
		courseText.setText(GL1701+ ""+GL_SPL_SEMICOLON);
		legalText.setText(GL0730+ ""+GL_SPL_SEMICOLON);
		standardsText.setText(GL1877+GL_SPL_SEMICOLON);
		collectionsText.getElement().setInnerHTML(GL0620);
		hideText.setText(GL0592);
		resourceInfoText.setText(GL0621);
		gradeTitle.setText(GL0165+ ""+GL_SPL_SEMICOLON);
		originalUrlTitle.setText(GL0976+ ""+GL_SPL_SEMICOLON);
		generalLbl.setText(GL1708);
		resourceInfoLbl.setText(GL1716);
		educationallLbl.setText(GL1720);
		
		timeRequiredLabel.setText(GL1685+GL_SPL_SEMICOLON);
		timeRequiredvalue.setText("");
		
		//resourceInfoSeparatorTimeLbl.setStyleName(PlayerBundle.INSTANCE.getPlayerStyle().sourceSepartor());
		resourceDescription.getElement().setAttribute("style", "margin-top:5px;");
		AppClientFactory.getEventBus().addHandler(UpdateRatingsInRealTimeEvent.TYPE,setRatingWidgetMetaData);
		AppClientFactory.getEventBus().addHandler(DeletePlayerStarReviewEvent.TYPE,deleteStarRating);
	}
	
	/**
	 * Average star ratings widget will get integrated.
	 */
	private void setAvgRatingWidget() {
		ratingWidgetPanel.clear();
		ratingWidgetView=new RatingWidgetView();
		if(collectionItemDoGlobal.getResource().getRatings()!=null){
			ratingWidgetView.getRatingCountLabel().setText(collectionItemDoGlobal.getResource().getRatings().getCount().toString());
			if(collectionItemDoGlobal.getResource().getRatings().getCount()>0)
			{
				ratingWidgetView.getRatingCountLabel().getElement().removeAttribute("class");
				ratingWidgetView.getRatingCountLabel().getElement().setAttribute("style", "cursor: pointer;text-decoration: none !important;color: #1076bb;");
				ratingWidgetView.getRatingCountLabel().addClickHandler(new ShowRatingPopupEvent());
			}
			ratingWidgetView.setAvgStarRating(collectionItemDoGlobal.getResource().getRatings().getAverage());
		}
		
	
		ratingWidgetPanel.getElement().getStyle().setMarginRight(10, Unit.PX);
		ratingWidgetPanel.add(ratingWidgetView);
	}
	
	/**
	 * 
	 * Inner class implementing {@link ClickEvent}
	 *
	 */
	private class ShowRatingPopupEvent implements ClickHandler{
		@Override
		public void onClick(ClickEvent event) {
			/**
			 * OnClick of count label event to invoke Review pop-pup
			 */

			AppClientFactory.fireEvent(new OpenReviewPopUpEvent(collectionItemDoGlobal.getResource().getGooruOid(),"",collectionItemDoGlobal.getResource().getUser().getUsername())); 
		}
	}


	@Override
	public void setResourceMedaDataInfo(CollectionItemDo collectionItemDo) {
		
		isEducationalInfo=false;
		isAccessibilityInfo=false;
		isResourceInfo=false;
		isGeneralInfo=false;
		
		isTimeDuration =false;
		isAggregator =false;
		isPublisher=false;
		
		collectionItemDoGlobal = collectionItemDo;
		if(!AppClientFactory.getCurrentPlaceToken().equalsIgnoreCase(PlaceTokens.COLLECTION_PLAY)){
			setAvgRatingWidget();
		}
		if(collectionItemDo.getResource().getMediaType()!=null){
			if(collectionItemDo.getResource().getMediaType().equals(NOT_FRIENDY_TAG)){	
				//mobileFriendly.setStyleName(PlayerBundle.INSTANCE.getPlayerStyle().ipadFriendlyIconBlock());
			}
		}
		if(collectionItemDo.getResource().getResourceFormat()!=null){
			setResourceTypeImage(collectionItemDo.getResource().getResourceFormat().getDisplayName());
		}
		setResourceAttribution(collectionItemDo.getResource().getResourceSource()!=null?collectionItemDo.getResource().getResourceSource().getAttribution():
				null,collectionItemDo.getResource().getTaxonomySet());
		setResourceDescription(collectionItemDo.getResource().getDescription());
		setResourceViewsCount(collectionItemDo.getViews());
		setResourceLicenceLogo(collectionItemDo.getResource().getAssetURI(), collectionItemDo.getResource().getLicense());
		renderStandards(standardsInfoConatiner,collectionItemDo.getStandards());
		setGrades(collectionItemDo.getResource().getGrade());
		setOriginalUrl(collectionItemDo.getResource().getAssetURI(),collectionItemDo.getResource().getFolder(),
							collectionItemDo.getResource().getUrl(),collectionItemDo.getResource().getResourceType().getName());
		loadResourceReleatedCollections(collectionItemDo.getResource().getGooruOid());
		//setPublisher(collectionItemDo.getResource().getResourceSource()!=null?collectionItemDo.getResource().getResourceSource().getAttribution():"",collectionItemDo.getResource().getUrl());
		
		if(collectionItemDo.getResource().getPublisher()!=null){
			setPublisherDetails(collectionItemDo.getResource().getPublisher());
		}
		
		
		/*if(AppClientFactory.getCurrentPlaceToken().equalsIgnoreCase(PlaceTokens.PREVIEW_PLAY)){*/
		if(collectionItemDo.getResource().getThumbnails()!=null){
			setThumbnailUrl(collectionItemDo.getResource().getThumbnails().getUrl());
		}
		
		if(collectionItemDo.getResource().getAggregator()!=null){
			setAggregatorvalues(collectionItemDo.getResource().getAggregator());
		}
		
		/*if(collectionItemDo.getResource().getCreatedOn()!=null){
			setCreatedDate(collectionItemDo.getResource().getCreatedOn());
		}*/

		/*if(AppClientFactory.getCurrentPlaceToken().equalsIgnoreCase(PlaceTokens.PREVIEW_PLAY)){
			lblcollectionName.setVisible(true);
			lblcollectionName.setText(title);
			lblcollectionName.getElement().setAttribute("style", "margin-left: 40px;position: relative;top: 14px;font-weight: bold;");
		 }else{
		    lblcollectionName.setVisible(false);
		 	}*/
			
			lblcollectionName.setHTML(removeHtmlTags(collectionItemDo.getResource().getTitle()));
			lblcollectionName.setStyleName(PlayerBundle.INSTANCE.getPlayerStyle().resourceTitleStyleName());
			
		//setHostDetails(collectionItemDo.getResource().getHost());
			if(collectionItemDo.getResource().getCustomFieldValues()!=null ){
				clearALlPanels();
				if(collectionItemDo.getResource().getCustomFieldValues().getCfHost()!=null){
				setHostDetails(collectionItemDo.getResource().getCustomFieldValues().getCfHost());
				}
				if(collectionItemDo.getResource().getCustomFieldValues().getCfOER()!=null){
				setOerDetails(collectionItemDo.getResource().getCustomFieldValues().getCfOER());
				}
				if(collectionItemDo.getResource().getCustomFieldValues().getCfEducationalAlignment()!=null){
					setedAlignDetails(collectionItemDo.getResource().getCustomFieldValues().getCfEducationalAlignment());
				}
				if(collectionItemDo.getResource().getCustomFieldValues().getCfEndUser()!=null){
				   seteducationalRoleDetails(collectionItemDo.getResource().getCustomFieldValues().getCfEndUser());
				}
				if(collectionItemDo.getResource().getCustomFieldValues().getCfLearningMode()!=null){
					setinteractivityTypeDetails(collectionItemDo.getResource().getCustomFieldValues().getCfLearningMode());
				}
				if(collectionItemDo.getResource().getCustomFieldValues().getCfAge()!=null){
					setageRangeDetails(collectionItemDo.getResource().getCustomFieldValues().getCfAge());
				}
				if(collectionItemDo.getResource().getCustomFieldValues().getCfCountryCode()!=null){
					setCountryCodeDetails(collectionItemDo.getResource().getCustomFieldValues().getCfCountryCode());
				}
				if(collectionItemDo.getResource().getCustomFieldValues().getCfLanguageCode()!=null){
					setlanguageDetails(collectionItemDo.getResource().getCustomFieldValues().getCfLanguageCode());
				}
				if(collectionItemDo.getResource().getCustomFieldValues().getCfDataType()!=null){
					setdataTypeDetails(collectionItemDo.getResource().getCustomFieldValues().getCfDataType());
				}
				if(collectionItemDo.getResource().getCustomFieldValues().getCfAuthor()!=null){
					setAuthorDetails(collectionItemDo.getResource().getCustomFieldValues().getCfAuthor());
				}
				if(collectionItemDo.getResource().getCustomFieldValues().getCfCopyrightHolder()!=null){
					setCopyRightHolderDetails(collectionItemDo.getResource().getCustomFieldValues().getCfCopyrightHolder());
				}
				if(collectionItemDo.getResource().getCustomFieldValues().getCfControlFlexibility()!=null){
					setcontrolflexibilityDetails(collectionItemDo.getResource().getCustomFieldValues().getCfControlFlexibility());
				}
				if(collectionItemDo.getResource().getCustomFieldValues().getCfAccessHazard()!=null){
					setAcessHazardDetails(collectionItemDo.getResource().getCustomFieldValues().getCfAccessHazard());
				}
				if(collectionItemDo.getResource().getCustomFieldValues().getCfSchoolLevel()!=null){
					setSchoolLevelDetails(collectionItemDo.getResource().getCustomFieldValues().getCfSchoolLevel());
				}
				if(collectionItemDo.getResource().getCustomFieldValues().getCfDuration()!=null){
					setTimeDurationDetails(collectionItemDo.getResource().getCustomFieldValues().getCfDuration());
				}
				if(collectionItemDo.getResource().getCustomFieldValues().getCfReadingLevel()!=null){
					List<String> readingLeveldetails = new ArrayList<String>();
					String[] readinglevellist=collectionItemDo.getResource().getCustomFieldValues().getCfReadingLevel().split(",");
					for(int rlevel=0;rlevel<readinglevellist.length;rlevel++){
						readingLeveldetails.add(readinglevellist[rlevel]);
					}
					showreadingLevelDetails(readingLeveldetails);
				}
				
				if(collectionItemDo.getResource().getCustomFieldValues().getCfKeywords()!=null){
				List<String> keyworddetails = new ArrayList<String>();
				String[] keywordslist=collectionItemDo.getResource().getCustomFieldValues().getCfKeywords().split(",");										
				for(int klevel=0;klevel<keywordslist.length;klevel++){
					keyworddetails.add(keywordslist[klevel]);
				}
				setkeywordsDetails(keyworddetails);
				}
				
				if(collectionItemDo.getResource().getCustomFieldValues().getCfAds()!=null){
				List<String> addsdetails = new ArrayList<String>();
				String[] addslist=collectionItemDo.getResource().getCustomFieldValues().getCfAds().split(",");										
				for(int alevel=0;alevel<addslist.length;alevel++){
					addsdetails.add(addslist[alevel]);
				}
				setaddsDetails(addsdetails);
				}
				
				if(collectionItemDo.getResource().getCustomFieldValues().getCfAccessMode()!=null){
				List<String> acessmodedetails = new ArrayList<String>();
				String[] accesslist=collectionItemDo.getResource().getCustomFieldValues().getCfAccessMode().split(",");	
				for(int accesslevel=0;accesslevel<accesslist.length;accesslevel++){
					acessmodedetails.add(accesslist[accesslevel]);
				}
				setacessmodeDetails(acessmodedetails);
				}
				
				if(collectionItemDo.getResource().getCustomFieldValues().getCfMediaFeature()!=null){
				List<String> mediafeaturesdetails = new ArrayList<String>();
				String[] mediafeaturelist=collectionItemDo.getResource().getCustomFieldValues().getCfMediaFeature().split(",");	
				for(int mflevel=0;mflevel<mediafeaturelist.length;mflevel++){
					mediafeaturesdetails.add(mediafeaturelist[mflevel]);
				}
				setmediafeaturesDetails(mediafeaturesdetails);
				}
			
			}
	
		List<String> eduUsedetails = new ArrayList<String>();

		if(collectionItemDo.getResource().getEducationalUse()!=null){
		if(collectionItemDo.getResource().getEducationalUse().size()>0){
		for(int i=0;i<collectionItemDo.getResource().getEducationalUse().size();i++){
			if(collectionItemDo.getResource().getEducationalUse().get(i).isSelected())
			{
			eduUsedetails.add(collectionItemDo.getResource().getEducationalUse().get(i).getValue());
			}
		}
		seteducationaluseDetails(eduUsedetails);
		}else{
			eduUsePanel.setVisible(false);
		}
		}
		List<String> depthofknowledgedetails = new ArrayList<String>();
		List<String> momentoflearningdetails = new ArrayList<String>();
		
		if(collectionItemDo.getResource().getResourceFormat().getValue().equalsIgnoreCase("question")){
		 depthofknowledgedetails = new ArrayList<String>();
			if(collectionItemDo.getResource().getDepthOfKnowledges()!=null){
			if(collectionItemDo.getResource().getDepthOfKnowledges().size()>0){
			for(int i=0;i<collectionItemDo.getResource().getDepthOfKnowledges().size();i++){
				if(collectionItemDo.getResource().getDepthOfKnowledges().get(i).isSelected())
				{
				depthofknowledgedetails.add(collectionItemDo.getResource().getDepthOfKnowledges().get(i).getValue());
				}
			}
			setDepthofknowledgeDetails(depthofknowledgedetails);
			//dKnowledgePanel.setVisible(true);
			}else{
				dKnowledgePanel.setVisible(false);
			}
			}else{
				dKnowledgePanel.setVisible(false);
			}
			momentsoflearningPanel.setVisible(false);
		}
		else{
			 momentoflearningdetails = new ArrayList<String>();
			if(collectionItemDo.getResource().getMomentsOfLearning()!=null){
			if(collectionItemDo.getResource().getMomentsOfLearning().size()>0){
			for(int i=0;i<collectionItemDo.getResource().getMomentsOfLearning().size();i++){
				if(collectionItemDo.getResource().getMomentsOfLearning().get(i).isSelected())
				{
				momentoflearningdetails.add(collectionItemDo.getResource().getMomentsOfLearning().get(i).getValue());
				}
			}
			setmonentoflearningDetails(momentoflearningdetails);
			//momentsoflearningPanel.setVisible(true);
			}else{
				momentsoflearningPanel.setVisible(false);	
			}
			}else{
				momentsoflearningPanel.setVisible(false);
			}
			dKnowledgePanel.setVisible(false);
		}
	
		setmobilefriendlynessdetails(collectionItemDo.getResource().getMediaType());
		
		resourceTypeImage.getElement().setAttribute("style", "position: relative;margin-top:10px;margin-bottom: 10px;");

		//resourcetypeSeparator.setStyleName(PlayerBundle.INSTANCE.getPlayerStyle().bulletBlack());
		resourceInfoSeparator.setStyleName(PlayerBundle.INSTANCE.getPlayerStyle().bulletBlack());
		timeRequiredvalue.setVisible(true);
		
		timeRequiredLabel.setVisible(true);
		accessibilityPanel.setVisible(true);
		
		/*}else{
			
			resourcetypeSeparator.setHTML(SEPARATOR);
			resourcetypeSeparator.setStyleName(PlayerBundle.INSTANCE.getPlayerStyle().sourceSepartor());
			resourceTypeImage.getElement().setAttribute("style", "position: relative;");
			learningobjectiveText.setText(GL0904 + ""+GL_SPL_SEMICOLON);
			resourceTypeImage.getElement().setAttribute("style","top:15px;position: relative;left:50px;");
		
			momentsoflearningPanel.setVisible(false);
			thumbnailPanel.setVisible(false);
			licenceCodePanel.setVisible(false);
			dateCreatedPanel.setVisible(false);
			//lblcollectionName.setVisible(false);
			timeRequiredvalue.setVisible(false);
			authorPanel.setVisible(false);
			gooruSubjectPanel.setVisible(false);
			generalLbl.setVisible(false);
			educationallLbl.setVisible(false);
			resourceInfoLbl.setVisible(false);
			accesibilityLbl.setVisible(false);
			timeRequiredLabel.setVisible(false);
			resourceInfoSeparatorTimeLbl.setVisible(false);
			contributorPanel.setVisible(false);
			mobileFriendlyPanel.setVisible(false);
			DataTypePanel.setVisible(false);
			interactivityTypePanel.setVisible(false);
			eduAllignPanel.setVisible(false);
			eduUsePanel.setVisible(false);
			eduRolePanel.setVisible(false);
			ageRangePanel.setVisible(false);
			dKnowledgePanel.setVisible(false);
			readingLevelPanel.setVisible(false);
			hasAdaptationPanel.setVisible(false);
			languagePanel.setVisible(false);
			countryCodePanel.setVisible(false);
			isAdaptationPanel.setVisible(false);
			copyRightPanel.setVisible(false);
			hostPanel.setVisible(false);
			gooruCoursePanel.setVisible(false);
			accessibilityAPIPanel.setVisible(false);
			accessibilityPanel.setVisible(false);
			controlPanel.setVisible(false);
			accessHazardPanel.setVisible(false);
			mediaFeaturePanel.setVisible(false);
			accessModePanel.setVisible(false);
			keyWordsPanel.setVisible(false);
			eduUseType.setVisible(false);
			gooruCourseInfo.setVisible(false);
			accessModeType.setVisible(false);
			mediaFeatureType.setVisible(false);
			accessibilityAPIType.setVisible(false);
		}*/
	
		if(collectionItemDo.getResource().getCustomFieldValues()!=null){
		if(collectionItemDo.getResource().getCustomFieldValues().getCfAccessMode()==null
				&& collectionItemDo.getResource().getCustomFieldValues().getCfMediaFeature()==null
				&& collectionItemDo.getResource().getCustomFieldValues().getCfControlFlexibility()==null
				&& collectionItemDo.getResource().getCustomFieldValues().getCfAccessHazard()==null){
		}else{
			if(collectionItemDo.getResource().getCustomFieldValues().getCfAccessMode()!=null && !collectionItemDo.getResource().getCustomFieldValues().getCfAccessMode().equalsIgnoreCase("")&&!collectionItemDo.getResource().getCustomFieldValues().getCfAccessMode().equalsIgnoreCase("null")){
				isAccessibilityInfo=true;
			}
			if(collectionItemDo.getResource().getCustomFieldValues().getCfMediaFeature()!=null && !collectionItemDo.getResource().getCustomFieldValues().getCfMediaFeature().equalsIgnoreCase("")&&!collectionItemDo.getResource().getCustomFieldValues().getCfMediaFeature().equalsIgnoreCase("null")){
				isAccessibilityInfo=true;
			}
			if(collectionItemDo.getResource().getCustomFieldValues().getCfControlFlexibility()!=null && !collectionItemDo.getResource().getCustomFieldValues().getCfControlFlexibility().equalsIgnoreCase("")&&!collectionItemDo.getResource().getCustomFieldValues().getCfControlFlexibility().equalsIgnoreCase("null")){
				isAccessibilityInfo=true;
			}
			if(collectionItemDo.getResource().getCustomFieldValues().getCfAccessHazard()!=null && !collectionItemDo.getResource().getCustomFieldValues().getCfAccessHazard().equalsIgnoreCase("")&&!collectionItemDo.getResource().getCustomFieldValues().getCfAccessHazard().equalsIgnoreCase("null")){
				isAccessibilityInfo=true;
			}
		}
	}
		if(isAccessibilityInfo){
			accesibilityLbl.setText(GL1703);
			accesibilityLbl.setVisible(true);
			accessibilityPanel.setVisible(true);
		}else{
			accesibilityLbl.setVisible(false);
			accessibilityPanel.setVisible(false);
		}	
		
		if(collectionItemDo.getResource().getCustomFieldValues()!=null){
		if(collectionItemDo.getResource().getCustomFieldValues().getCfCountryCode() == null
		&& collectionItemDo.getResource().getCustomFieldValues().getCfLanguageCode() ==null
		&& collectionItemDo.getResource().getCustomFieldValues().getCfDataType() ==null
		&& collectionItemDo.getResource().getCustomFieldValues().getCfAuthor() ==null
		&& collectionItemDo.getResource().getCustomFieldValues().getCfCopyrightHolder() ==null
		&& collectionItemDo.getResource().getCustomFieldValues().getCfKeywords() ==null
		&& collectionItemDo.getResource().getCustomFieldValues().getCfAds() ==null){
			
		}else{
			if(collectionItemDo.getResource().getCustomFieldValues().getCfCountryCode()!=null && !collectionItemDo.getResource().getCustomFieldValues().getCfCountryCode().equalsIgnoreCase("")&&!collectionItemDo.getResource().getCustomFieldValues().getCfCountryCode().equalsIgnoreCase("null")){
				isResourceInfo=true;
			}
			if(collectionItemDo.getResource().getCustomFieldValues().getCfLanguageCode()!=null && !collectionItemDo.getResource().getCustomFieldValues().getCfLanguageCode().equalsIgnoreCase("")&&!collectionItemDo.getResource().getCustomFieldValues().getCfLanguageCode().equalsIgnoreCase("null")){
				isResourceInfo=true;
			}
			if(collectionItemDo.getResource().getCustomFieldValues().getCfDataType()!=null && !collectionItemDo.getResource().getCustomFieldValues().getCfDataType().equalsIgnoreCase("")&&!collectionItemDo.getResource().getCustomFieldValues().getCfDataType().equalsIgnoreCase("null")){
				isResourceInfo=true;
			}
			if(collectionItemDo.getResource().getCustomFieldValues().getCfAuthor()!=null && !collectionItemDo.getResource().getCustomFieldValues().getCfAuthor().equalsIgnoreCase("")&&!collectionItemDo.getResource().getCustomFieldValues().getCfAuthor().equalsIgnoreCase("null")){
				isResourceInfo=true;
			}
			if(collectionItemDo.getResource().getCustomFieldValues().getCfCopyrightHolder()!=null && !collectionItemDo.getResource().getCustomFieldValues().getCfCopyrightHolder().equalsIgnoreCase("")&&!collectionItemDo.getResource().getCustomFieldValues().getCfCopyrightHolder().equalsIgnoreCase("null")){
				isResourceInfo=true;
			}
			if(collectionItemDo.getResource().getCustomFieldValues().getCfKeywords()!=null && !collectionItemDo.getResource().getCustomFieldValues().getCfKeywords().equalsIgnoreCase("")&&!collectionItemDo.getResource().getCustomFieldValues().getCfKeywords().equalsIgnoreCase("null")){
				isResourceInfo=true;
			}
			if(collectionItemDo.getResource().getCustomFieldValues().getCfAds()!=null && !collectionItemDo.getResource().getCustomFieldValues().getCfAds().equalsIgnoreCase("")&&!collectionItemDo.getResource().getCustomFieldValues().getCfAds().equalsIgnoreCase("null")){
				isResourceInfo=true;
			}
		}
		}
		if(isResourceInfo){
			resourceInfoLbl.setVisible(true);
		}else{
			resourceInfoLbl.setVisible(false);
		}
		
		if(collectionItemDo.getResource().getGrade()==null
				&& collectionItemDo.getResource().getUrl()==null 
				&& collectionItemDo.getResource().getTaxonomySet()==null
				&& collectionItemDo.getResource().getLicense() ==null 
				&& collectionItemDo.getStandards()==null 
				&& collectionItemDo.getResource().getResourceSource()==null
				&& collectionItemDo.getResource().getCustomFieldValues()==null && collectionItemDo.getResource().getAggregator()==null && collectionItemDo.getResource().getPublisher()==null){
		     }else{
						if(collectionItemDo.getResource().getGrade()!=null && !collectionItemDo.getResource().getGrade().equalsIgnoreCase("")&&!collectionItemDo.getResource().getGrade().equalsIgnoreCase("null")){
							isGeneralInfo=true;
				  		}
				  		 if(collectionItemDo.getResource().getUrl()!=null&&!collectionItemDo.getResource().getUrl().equalsIgnoreCase("")&&!collectionItemDo.getResource().getUrl().equalsIgnoreCase("null")){
				  			isGeneralInfo=true;
				  		} 
				  		 if(collectionItemDo.getResource().getTaxonomySet()!=null && collectionItemDo.getResource().getTaxonomySet().size()>0){
				  			List<String> coursesList=new ArrayList<String>();
				  			Set<CodeDo>	taxonomoyList = collectionItemDo.getResource().getTaxonomySet();
				  			if(taxonomoyList!=null){
				  				Iterator<CodeDo> taxonomyIterator=taxonomoyList.iterator();
				  				while (taxonomyIterator.hasNext()) {
				  					CodeDo codeDo=taxonomyIterator.next();
				  					if(codeDo.getDepth()==2){
				  						coursesList.add(codeDo.getLabel());
				  					}
				  				}
				  			}
				  			if(coursesList.size()>0){
				  				isGeneralInfo=true;
				  			}
				  		}
				  		 if(collectionItemDo.getStandards()!=null && collectionItemDo.getStandards().size()>0){
				  			List<Map<String,String>> standardsList1	=collectionItemDo.getStandards();
				  			Iterator<Map<String, String>> iterator = standardsList1.iterator();
				  			int count = 0;
				  			while (iterator.hasNext()) {
				  				Map<String, String> standard = iterator.next();
				  				String stdCode = standard.get(STANDARD_CODE);
				  				String stdDec = standard.get(STANDARD_DESCRIPTION);
				  				count++;
				  			}
				  			if(count>0){
				  				isGeneralInfo=true;	
				  			}
				 			} 
				  		 if(collectionItemDo.getResource().getLicense()!=null && collectionItemDo.getResource().getLicense().getIcon()!=null &&!collectionItemDo.getResource().getLicense().getIcon().trim().equals("") ){
				 				isGeneralInfo=true;	
				 			}
				  		 if(collectionItemDo.getResource().getResourceSource()!=null && collectionItemDo.getResource().getResourceSource().getAttribution()!=null && !collectionItemDo.getResource().getResourceSource().getAttribution().equalsIgnoreCase("") && !collectionItemDo.getResource().getResourceSource().getAttribution().equalsIgnoreCase("null")){
				  				isGeneralInfo=true;
				 			}
				  		 if(collectionItemDo.getResource().getCustomFieldValues()!=null && collectionItemDo.getResource().getCustomFieldValues().getCfHost()!=null && !collectionItemDo.getResource().getCustomFieldValues().getCfHost().equalsIgnoreCase("")&&!collectionItemDo.getResource().getCustomFieldValues().getCfHost().equalsIgnoreCase("null")){
				  			isGeneralInfo=true;	
				 			}
				  		 if(collectionItemDo.getResource().getCustomFieldValues()!=null && collectionItemDo.getResource().getCustomFieldValues().getCfOER()!=null && !collectionItemDo.getResource().getCustomFieldValues().getCfOER().equalsIgnoreCase("")&&!collectionItemDo.getResource().getCustomFieldValues().getCfOER().equalsIgnoreCase("null")){
				  			isGeneralInfo=true;
				  		}
				  		 if(collectionItemDo.getResource().getAggregator()!=null && collectionItemDo.getResource().getAggregator().size()>0 ){
				  			isGeneralInfo=true;
				  		}
				  		 if(collectionItemDo.getResource().getPublisher()!=null && collectionItemDo.getResource().getPublisher().size()>0 ){
				  			isGeneralInfo=true;
				  		}
		}
		if(isGeneralInfo){
			generalLbl.setVisible(true);
		}else{
			generalLbl.setVisible(false);
		}
		
		
		if(collectionItemDo.getResource().getCustomFieldValues()!=null){
				if(collectionItemDo.getResource().getCustomFieldValues().getCfEducationalAlignment()==null
					&& collectionItemDo.getResource().getCustomFieldValues().getCfEndUser()==null
					&& collectionItemDo.getResource().getCustomFieldValues().getCfLearningMode()==null
					&& collectionItemDo.getResource().getCustomFieldValues().getCfAge()==null
					&& collectionItemDo.getResource().getCustomFieldValues().getCfReadingLevel()==null
					&& collectionItemDo.getResource().getCustomFieldValues().getCfSchoolLevel()==null ){
				}else{
					if(collectionItemDo.getResource().getCustomFieldValues().getCfEducationalAlignment()!=null && !collectionItemDo.getResource().getCustomFieldValues().getCfEducationalAlignment().equalsIgnoreCase("")&&!collectionItemDo.getResource().getCustomFieldValues().getCfEducationalAlignment().equalsIgnoreCase("null")){
						isEducationalInfo=true;
					}
					 if(collectionItemDo.getResource().getCustomFieldValues().getCfEndUser()!=null && !collectionItemDo.getResource().getCustomFieldValues().getCfEndUser().equalsIgnoreCase("")&&!collectionItemDo.getResource().getCustomFieldValues().getCfEndUser().equalsIgnoreCase("null")){
						isEducationalInfo=true;
					}
					 if(collectionItemDo.getResource().getCustomFieldValues().getCfLearningMode()!=null && !collectionItemDo.getResource().getCustomFieldValues().getCfLearningMode().equalsIgnoreCase("")&&!collectionItemDo.getResource().getCustomFieldValues().getCfLearningMode().equalsIgnoreCase("null")){
						isEducationalInfo=true;
					}
					 if(collectionItemDo.getResource().getCustomFieldValues().getCfAge()!=null && !collectionItemDo.getResource().getCustomFieldValues().getCfAge().equalsIgnoreCase("")&&!collectionItemDo.getResource().getCustomFieldValues().getCfAge().equalsIgnoreCase("null")){
						isEducationalInfo=true;
					}
					 if(collectionItemDo.getResource().getCustomFieldValues().getCfReadingLevel()!=null && !collectionItemDo.getResource().getCustomFieldValues().getCfReadingLevel().equalsIgnoreCase("")&&!collectionItemDo.getResource().getCustomFieldValues().getCfReadingLevel().equalsIgnoreCase("null")){
						isEducationalInfo=true;
					}
					 if(collectionItemDo.getResource().getCustomFieldValues().getCfSchoolLevel()!=null && !collectionItemDo.getResource().getCustomFieldValues().getCfSchoolLevel().equalsIgnoreCase("")&&!collectionItemDo.getResource().getCustomFieldValues().getCfSchoolLevel().equalsIgnoreCase("null")){
						isEducationalInfo=true;
					}
				}
			}
			if (collectionItemDo.getResource().getResourceFormat().getValue().equalsIgnoreCase("question")) {
				if (depthofknowledgedetails != null	&& depthofknowledgedetails.size() > 0) {
					dKnowledgePanel.setVisible(true);
					isEducationalInfo = true;
				} else {
					dKnowledgePanel.setVisible(false);
				}
				momentsoflearningPanel.setVisible(false);
			} else {
				if (momentoflearningdetails != null	&& momentoflearningdetails.size() > 0) {
					momentsoflearningPanel.setVisible(true);
					isEducationalInfo = true;
				}else{
					momentsoflearningPanel.setVisible(false);
				}
				dKnowledgePanel.setVisible(false);
			}
			
			if(eduUsedetails!=null && eduUsedetails.size()>0){ 
					eduUsePanel.setVisible(true);
					isEducationalInfo=true;
			}else{
				eduUsePanel.setVisible(false);
			}
			
			if(isEducationalInfo){
				educationallLbl.setVisible(true);
			}else{
				educationallLbl.setVisible(false);
			}
			
			if(isTimeDuration){
				timeRequiredLabel.setVisible(true);
				timeRequiredvalue.setVisible(true);
			}else{
				timeRequiredLabel.setVisible(false);
				timeRequiredvalue.setVisible(false);
			}
			
			if(isAggregator){
				aggregatorPanel.setVisible(true);
				aggregatorText.setText(GL1748);
				aggregatorText.setVisible(true);
				aggregatorVal.setVisible(true);
			}else{
				aggregatorPanel.setVisible(false);
				aggregatorText.setVisible(false);
				aggregatorVal.setVisible(false);
			}
			if(isPublisher){
				publisherPanel.setVisible(true);
				publisherText.setVisible(true);
				lblPublisher.setVisible(true);
			}else{
				publisherPanel.setVisible(false);
				publisherText.setVisible(false);
				lblPublisher.setVisible(false);
			}
			
	}
	
	private void setPublisherDetails(List<String> publisher) {
		// TODO Auto-generated method stub
		lblPublisher.clear();
		if(publisher == null || publisher.size() == 0 || publisher.contains(null) || publisher.contains("") ){
		}else{
		if(publisher.size()>0){
			if(publisher.size()==1){
				final Label publisherLabel=new Label(publisher.get(0));
				publisherLabel.getElement().setAttribute("style", "float: left;");
				lblPublisher.add(publisherLabel);
				isPublisher =true;
			} if(publisher.size()==2){
				final Label publisherLabel=new Label(publisher.get(0)+","+publisher.get(1));
				publisherLabel.getElement().setAttribute("style", "float: left;");
				lblPublisher.add(publisherLabel);
				isPublisher =true;
			}
		}
		if(publisher.size()>2){
			final Label publisherLabelCountLabel=new Label("+"+(publisher.size()-2)); 
			publisherLabelCountLabel.setStyleName(PlayerBundle.INSTANCE.getPlayerStyle().resourceCourseNum());
			final Label publisherLabel=new Label(publisher.get(0)+","+publisher.get(1));
			publisherLabel.getElement().setAttribute("style", "float:left;");
			lblPublisher.add(publisherLabel);
			lblPublisher.add(publisherLabelCountLabel);
			Widget publisherwidget = getCommonwidget(publisher);
			publisherLabelCountLabel.addMouseOverHandler(new MouseOverShowToolTip(publisherwidget));
			publisherLabelCountLabel.addMouseOutHandler(new MouseOutHideToolTip());
			isPublisher =true;
		}
		}
	
	}

	private void setAggregatorvalues(List<String> aggregatorlist) {
		// TODO Auto-generated method stub
		aggregatorVal.clear();
		if(aggregatorlist == null || aggregatorlist.size() == 0 || aggregatorlist.contains(null) || aggregatorlist.contains("") ){
		}else{
		if(aggregatorlist.size()>0){
			if(aggregatorlist.size()==1){
				final Label aggregatorLabel=new Label(aggregatorlist.get(0));
				aggregatorLabel.getElement().setAttribute("style", "float: left;");
				aggregatorVal.add(aggregatorLabel);
				isAggregator =true;
			} if(aggregatorlist.size()==2){
				final Label aggregatorLabel=new Label(aggregatorlist.get(0)+","+aggregatorlist.get(1));
				aggregatorLabel.getElement().setAttribute("style", "float: left;");
				aggregatorVal.add(aggregatorLabel);
				isAggregator =true;
			}
		}
		if(aggregatorlist.size()>2){
			final Label aggregatorCountLabel=new Label("+"+(aggregatorlist.size()-2)); 
			aggregatorCountLabel.setStyleName(PlayerBundle.INSTANCE.getPlayerStyle().resourceCourseNum());
			final Label aggregatorLabel=new Label(aggregatorlist.get(0)+","+aggregatorlist.get(1));
			aggregatorLabel.getElement().setAttribute("style", "float:left;");
			aggregatorVal.add(aggregatorLabel);
			aggregatorVal.add(aggregatorCountLabel);
			Widget aggregatorwidget = getCommonwidget(aggregatorlist);
			aggregatorCountLabel.addMouseOverHandler(new MouseOverShowToolTip(aggregatorwidget));
			aggregatorCountLabel.addMouseOutHandler(new MouseOutHideToolTip());
			isAggregator =true;
		}
		}
	}

	private void setTimeDurationDetails(String cfDuration) {
		// TODO Auto-generated method stub
		if(cfDuration.contains(" ")){
			String[] spiltTimeBySpace	= cfDuration.split(" ");
		if(spiltTimeBySpace.length>0){
			if(spiltTimeBySpace[1].contains(":")){
					String[] 	spiltTimeAfterColon = spiltTimeBySpace[1].split(":");
					if(spiltTimeAfterColon.length>2){
						if(spiltTimeAfterColon[2].length()>2){
							timeRequiredvalue.setText(spiltTimeAfterColon[1]+GL0958+" "+spiltTimeAfterColon[2].substring(0, 2)+GL0959);
							isTimeDuration =true;
						}
					}
			}
	}
	
	}
	
	}
	
	private void setaddsDetails(List<String> addsdetails) {
		// TODO Auto-generated method stub
		addsInfo.clear();
		if(addsdetails == null || addsdetails.size() == 0 || addsdetails.contains(null) || addsdetails.contains("") ){
			addsPanel.setVisible(false);
		}else{
			addsTitle.setText(GL1878+GL_SPL_SEMICOLON);
		if(addsdetails.size()>0){
			if(addsdetails.size()==1){
				final Label addsLabel=new Label(addsdetails.get(0));
				addsLabel.getElement().setAttribute("style", "float: left;");
				addsInfo.add(addsLabel);
				addsPanel.setVisible(true);
			} if(addsdetails.size()==2){
				final Label addsLabel=new Label(addsdetails.get(0)+","+addsdetails.get(1));
				addsLabel.getElement().setAttribute("style", "float: left;");
				addsInfo.add(addsLabel);
				addsPanel.setVisible(true);
			}
			
		}
		if(addsdetails.size()>2){
			final Label addscountLabel=new Label("+"+(addsdetails.size()-2)); 
			addscountLabel.setStyleName(PlayerBundle.INSTANCE.getPlayerStyle().resourceCourseNum());
			final Label addsLabelNew=new Label(addsdetails.get(0)+","+addsdetails.get(1));
			addsLabelNew.getElement().setAttribute("style", "float:left;");
			addsInfo.add(addsLabelNew);
			addsInfo.add(addscountLabel);
			Widget addswidget = getCommonwidget(addsdetails);
			addscountLabel.addMouseOverHandler(new MouseOverShowToolTip(addswidget));
			addscountLabel.addMouseOutHandler(new MouseOutHideToolTip());
			addsPanel.setVisible(true);
		}
		}
		
	
	}

	private void setSchoolLevelDetails(String cfSchoolLevel) {
		// TODO Auto-generated method stub
		if(cfSchoolLevel!=null&&!cfSchoolLevel.equalsIgnoreCase("")&&!cfSchoolLevel.equalsIgnoreCase("null")){
			schoolLevelPanel.setVisible(true);
			schoolLevelLbl.setText(GL1868+GL_SPL_SEMICOLON);
			schoolLevelType.setText(cfSchoolLevel);
		}else{
			schoolLevelPanel.setVisible(false);
		}
	}

	private void clearALlPanels() {
		// TODO Auto-generated method stub
		hostPanel.setVisible(false);
		oerPanel.setVisible(false);
		eduAllignPanel.setVisible(false);
		eduRolePanel.setVisible(false);
		interactivityTypePanel.setVisible(false);
		ageRangePanel.setVisible(false);
		countryCodePanel.setVisible(false);
		languagePanel.setVisible(false);
		DataTypePanel.setVisible(false);
		authorPanel.setVisible(false);
		copyRightPanel.setVisible(false);
		controlPanel.setVisible(false);
		accessHazardPanel.setVisible(false);
		schoolLevelPanel.setVisible(false);
		readingLevelPanel.setVisible(false);
		keyWordsPanel.setVisible(false);
		addsPanel.setVisible(false);
		accessModePanel.setVisible(false);
		mediaFeaturePanel.setVisible(false);
	}

	private void setOerDetails(String oerdetails) {
		// TODO Auto-generated method stub
		if(oerdetails!=null&&!oerdetails.equalsIgnoreCase("")&&!oerdetails.equalsIgnoreCase("null")){
			oerPanel.setVisible(true);
			oerLbl.setText(GL1705+GL_SPL_SEMICOLON);
			oerAvailability.setText(oerdetails);
		}else{
			oerPanel.setVisible(false);
		}
	}

	private void setmonentoflearningDetails(List<String> momentoflearningdetails) {
		momentsoflearningType.clear();
		if(momentoflearningdetails == null || momentoflearningdetails.size() == 0 || momentoflearningdetails.contains(null) || momentoflearningdetails.contains("") ){
			momentsoflearningPanel.setVisible(false);
		}else{
			momentsoflearningLbl.setText(GL1678+GL_SPL_SEMICOLON);
		if(momentoflearningdetails.size()>0){
			final Label momentsofLabel=new Label(momentoflearningdetails.get(0));
			momentsofLabel.getElement().setAttribute("style", "float: left;");
			momentsoflearningType.add(momentsofLabel);
			momentsoflearningPanel.setVisible(true);
		}
		if(momentoflearningdetails.size()>2){
			final Label momentsofLabel=new Label("+"+(momentoflearningdetails.size()-2)); 
			momentsofLabel.setStyleName(PlayerBundle.INSTANCE.getPlayerStyle().resourceCourseNum());
			momentsoflearningType.add(momentsofLabel);
			Widget momentswidget = getCommonwidget(momentoflearningdetails);
			momentsofLabel.addMouseOverHandler(new MouseOverShowToolTip(momentswidget));
			momentsofLabel.addMouseOutHandler(new MouseOutHideToolTip());
			momentsoflearningPanel.setVisible(true);
		}
		}
		
	}

	private void setDepthofknowledgeDetails(List<String> depthOfKnowledgedetails) {
		// TODO Auto-generated method stub
		dKnowledgeType.clear();
		if(depthOfKnowledgedetails == null || depthOfKnowledgedetails.size() == 0 || depthOfKnowledgedetails.contains(null) || depthOfKnowledgedetails.contains("") ){
			dKnowledgePanel.setVisible(false);
		}else{
			dKnowledgeLbl.setText(GL1693+GL_SPL_SEMICOLON);
		if(depthOfKnowledgedetails.size()>0){
			if(depthOfKnowledgedetails.size()==1){
				final Label deapthknowledgeLabel=new Label(depthOfKnowledgedetails.get(0));
				deapthknowledgeLabel.getElement().setAttribute("style", "float: left;");
				dKnowledgeType.add(deapthknowledgeLabel);
				dKnowledgePanel.setVisible(true);
			} if(depthOfKnowledgedetails.size()==2){
				final Label deapthknowledgeLabel=new Label(depthOfKnowledgedetails.get(0)+","+depthOfKnowledgedetails.get(1));
				deapthknowledgeLabel.getElement().setAttribute("style", "float: left;");
				dKnowledgeType.add(deapthknowledgeLabel);
				dKnowledgePanel.setVisible(true);
			}
			
		}
		if(depthOfKnowledgedetails.size()>2){
			final Label deapthknowledgeLabel=new Label("+"+(depthOfKnowledgedetails.size()-2)); 
			deapthknowledgeLabel.setStyleName(PlayerBundle.INSTANCE.getPlayerStyle().resourceCourseNum());
			final Label deapthknowledgeLabelNew=new Label(depthOfKnowledgedetails.get(0)+","+depthOfKnowledgedetails.get(1));
			deapthknowledgeLabelNew.getElement().setAttribute("style", "float:left;");
			dKnowledgeType.add(deapthknowledgeLabelNew);
			dKnowledgeType.add(deapthknowledgeLabel);
			Widget depthofwidget = getCommonwidget(depthOfKnowledgedetails);
			deapthknowledgeLabel.addMouseOverHandler(new MouseOverShowToolTip(depthofwidget));
			deapthknowledgeLabel.addMouseOutHandler(new MouseOutHideToolTip());
			dKnowledgePanel.setVisible(true);
		}
		}
		
	}

	private void seteducationaluseDetails(List<String> eduUsedetails) {
		// TODO Auto-generated method stub
		eduUseType.clear();
		if(eduUsedetails == null || eduUsedetails.size() == 0 || eduUsedetails.contains(null) || eduUsedetails.contains("") ){
			eduUsePanel.setVisible(false);
		}else{
			educationallLbl.setText(GL1720);
		if(eduUsedetails.size()>0){
			final Label eduUseLabel=new Label(eduUsedetails.get(0));
			eduUseLabel.getElement().setAttribute("style", "float: left;");
			eduUseType.add(eduUseLabel);
			eduUsePanel.setVisible(true);
			educationallLbl.setText(GL1720);
			eduUseLbl.setText(GL1664+GL_SPL_SEMICOLON);
			educationallLbl.setVisible(true);
		}
		if(eduUsedetails.size()>2){
			final Label eduUseLabel=new Label("+"+(eduUsedetails.size()-2)); 
			eduUseLabel.setStyleName(PlayerBundle.INSTANCE.getPlayerStyle().resourceCourseNum());
			eduUseType.add(eduUseLabel);
			Widget eduusewidget = getCommonwidget(eduUsedetails);
			eduUseLabel.addMouseOverHandler(new MouseOverShowToolTip(eduusewidget));
			eduUseLabel.addMouseOutHandler(new MouseOutHideToolTip());
			eduUsePanel.setVisible(true);
			educationallLbl.setText(GL1720);
			eduUseLbl.setText(GL1664+GL_SPL_SEMICOLON);
			educationallLbl.setVisible(true);
		}
		}
	}

	private void setAcessHazardDetails(String accesshazard) {
		// TODO Auto-generated method stub
		if(accesshazard!=null&&!accesshazard.equalsIgnoreCase("")&&!accesshazard.equalsIgnoreCase("null")){
			accessHazardPanel.setVisible(true);
			acessHazardType.setText(accesshazard);
			acessHazardlLbl.setText(GL1705+GL_SPL_SEMICOLON);
		}else{
		accessHazardPanel.setVisible(false);
		}
	}

	private void setcontrolflexibilityDetails(String controlflexibility) {
		// TODO Auto-generated method stub
		if(controlflexibility!=null&&!controlflexibility.equalsIgnoreCase("")&&!controlflexibility.equalsIgnoreCase("null")){
			controlPanel.setVisible(true);
			controlType.setText(controlflexibility);
			controlLbl.setText(GL1704+GL_SPL_SEMICOLON);
		}else{
			controlPanel.setVisible(false);
		}
	}

	private void setmediafeaturesDetails(List<String> mediaFeatures) {
		// TODO Auto-generated method stub
		mediaFeatureType.clear();
		if(mediaFeatures == null || mediaFeatures.size() == 0 || mediaFeatures.contains(null) || mediaFeatures.contains("") ){
			mediaFeaturePanel.setVisible(false);
		}else{
			mediaFeatureLbl.setText(GL1706+GL_SPL_SEMICOLON);
		if(mediaFeatures.size()>0){
			
			if(mediaFeatures.size()==1){
				final Label mediafeatureLabel=new Label(mediaFeatures.get(0));
				mediafeatureLabel.getElement().setAttribute("style", "float: left;");
				mediaFeatureType.add(mediafeatureLabel);
				mediaFeaturePanel.setVisible(true);
			} if(mediaFeatures.size()==2){
				final Label mediafeatureLabel=new Label(mediaFeatures.get(0)+","+mediaFeatures.get(1));
				mediafeatureLabel.getElement().setAttribute("style", "float: left;");
				mediaFeatureType.add(mediafeatureLabel);
				mediaFeaturePanel.setVisible(true);
			}
		}
		if(mediaFeatures.size()>2){
			
			final Label mediafetureCountLabel=new Label("+"+(mediaFeatures.size()-2)); 
			mediafetureCountLabel.setStyleName(PlayerBundle.INSTANCE.getPlayerStyle().resourceCourseNum());
			final Label mediafeatureLabelNew=new Label(mediaFeatures.get(0)+","+mediaFeatures.get(1));
			mediafeatureLabelNew.getElement().setAttribute("style", "float:left;");
			mediaFeatureType.add(mediafeatureLabelNew);
			mediaFeatureType.add(mediafetureCountLabel);
			Widget mfwidget = getCommonwidget(mediaFeatures);
			mediafetureCountLabel.addMouseOverHandler(new MouseOverShowToolTip(mfwidget));
			mediafetureCountLabel.addMouseOutHandler(new MouseOutHideToolTip());
			mediaFeaturePanel.setVisible(true);
		}
		}
	}

	private void setacessmodeDetails(List<String> acessmode) {
		accessModeType.clear();
		if(acessmode == null || acessmode.size() == 0 || acessmode.contains(null) || acessmode.contains("") ){
			accessModePanel.setVisible(false);
		}else{
			accessModelLbl.setText(GL1707+GL_SPL_SEMICOLON);
		if(acessmode.size()>0){
			
			if(acessmode.size()==1){
				final Label accessmodeLabel=new Label(acessmode.get(0));
				accessmodeLabel.getElement().setAttribute("style", "float: left;");
				accessModeType.add(accessmodeLabel);
				accessModePanel.setVisible(true);
			} if(acessmode.size()==2){
				final Label accessmodeLabel=new Label(acessmode.get(0)+","+acessmode.get(1));
				accessmodeLabel.getElement().setAttribute("style", "float: left;");
				accessModeType.add(accessmodeLabel);
				accessModePanel.setVisible(true);
			}
		}
		if(acessmode.size()>2){
			
			final Label acessmodeCountLabel=new Label("+"+(acessmode.size()-2)); 
			acessmodeCountLabel.setStyleName(PlayerBundle.INSTANCE.getPlayerStyle().resourceCourseNum());
			final Label accessmodeLabelNew=new Label(acessmode.get(0)+","+acessmode.get(1));
			accessmodeLabelNew.getElement().setAttribute("style", "float:left;");
			accessModeType.add(accessmodeLabelNew);
			accessModeType.add(acessmodeCountLabel);
			Widget accesswidget = getCommonwidget(acessmode);
			acessmodeCountLabel.addMouseOverHandler(new MouseOverShowToolTip(accesswidget));
			acessmodeCountLabel.addMouseOutHandler(new MouseOutHideToolTip());
			accessModePanel.setVisible(true);
		}
		}
	}

	private void setmobilefriendlynessdetails(String mediaType) {
		// TODO Auto-generated method stub
		if(mediaType!=null&&!mediaType.equalsIgnoreCase("")&&!mediaType.equalsIgnoreCase("null")){
			if(mediaType.equals(NOT_FRIENDY_TAG)){
				mobileFriendlyPanel.setVisible(true);
				mbFriendlyLbl.setText(GL1687+GL_SPL_SEMICOLON);
				mbFriendlyText.setText(GL1735.toUpperCase());
				isAccessibilityInfo=true;
			}else{
				mobileFriendlyPanel.setVisible(true);
				mbFriendlyLbl.setText(GL1687+GL_SPL_SEMICOLON);
				mbFriendlyText.setText(GL_GRR_YES.toUpperCase());
				isAccessibilityInfo=true;
			}
		}else{
			mobileFriendlyPanel.setVisible(false);
		}
	}

	private void showreadingLevelDetails(List<String> readinglevel) {
		// TODO Auto-generated method stub
		readingLevelType.clear();
		if(readinglevel == null || readinglevel.size() == 0 || readinglevel.contains(null) || readinglevel.contains("") ){
			readingLevelPanel.setVisible(false);
		}else{
		if(readinglevel.size()>0){
			if(readinglevel.size()==1){
				readingLevelLbl.setText(GL1694+GL_SPL_SEMICOLON);
				final Label readingLabel=new Label(readinglevel.get(0));
				readingLabel.getElement().setAttribute("style", "float: left;");
				readingLevelType.add(readingLabel);
				readingLevelPanel.setVisible(true);
			} if(readinglevel.size()==2){
				readingLevelLbl.setText(GL1694+GL_SPL_SEMICOLON);
				final Label readingLabel=new Label(readinglevel.get(0)+","+readinglevel.get(1));
				readingLabel.getElement().setAttribute("style", "float: left;");
				readingLevelType.add(readingLabel);
				readingLevelPanel.setVisible(true);
			}
		}
		if(readinglevel.size()>2){
			
			readingLevelLbl.setText(GL1694+GL_SPL_SEMICOLON);
			final Label readingCountLabel=new Label("+"+(readinglevel.size()-2)); 
			readingCountLabel.setStyleName(PlayerBundle.INSTANCE.getPlayerStyle().resourceCourseNum());
			final Label readingLabelNew=new Label(readinglevel.get(0)+","+readinglevel.get(1));
			readingLabelNew.getElement().setAttribute("style", "float:left;");
			readingLevelType.add(readingLabelNew);
			readingLevelType.add(readingCountLabel);
			Widget readingwidget = getCommonwidget(readinglevel);
			readingCountLabel.addMouseOverHandler(new MouseOverShowToolTip(readingwidget));
			readingCountLabel.addMouseOutHandler(new MouseOutHideToolTip());
			readingLevelPanel.setVisible(true);
		}
		}
	}

	private void setkeywordsDetails(List<String> keywords) {
		// TODO Auto-generated method stub
		keywordsInfo.clear();
		if(keywords == null || keywords.size() == 0 || keywords.contains(null) || keywords.contains("") ){
			keyWordsPanel.setVisible(false);
		}else{
		if(keywords.size()>0){
			if(keywords.size()==1){
				keywordsTitle.setText(GL1876+GL_SPL_SEMICOLON);
				final Label keywordLabel=new Label(keywords.get(0));
				keywordLabel.getElement().setAttribute("style", "float: left;");
				keywordsInfo.add(keywordLabel);
				keyWordsPanel.setVisible(true);
			} if(keywords.size()==2){
				keywordsTitle.setText(GL1876+GL_SPL_SEMICOLON);
				final Label keywordLabel=new Label(keywords.get(0)+","+keywords.get(1));
				keywordLabel.getElement().setAttribute("style", "float: left;");
				keywordsInfo.add(keywordLabel);
				keyWordsPanel.setVisible(true);
			}
		}
		if(keywords.size()>2){
			keywordsTitle.setText(GL1876+GL_SPL_SEMICOLON);
			final Label keywordCountLabel=new Label("+"+(keywords.size()-2)); 
			keywordCountLabel.setStyleName(PlayerBundle.INSTANCE.getPlayerStyle().resourceCourseNum());
			final Label keywordsLabelNew=new Label(keywords.get(0)+","+keywords.get(1));
			keywordsLabelNew.getElement().setAttribute("style", "float:left;");
			keywordsInfo.add(keywordsLabelNew);
			keywordsInfo.add(keywordCountLabel);
			Widget keywordwidget = getCommonwidget(keywords);
			keywordCountLabel.addMouseOverHandler(new MouseOverShowToolTip(keywordwidget));
			keywordCountLabel.addMouseOutHandler(new MouseOutHideToolTip());
			keyWordsPanel.setVisible(true);
		}
		}
	}

	private void setCopyRightHolderDetails(String copyRightHolder) {
		// TODO Auto-generated method stub
		if(copyRightHolder!=null&&!copyRightHolder.equalsIgnoreCase("")&&!copyRightHolder.equalsIgnoreCase("null")){
			copyRightPanel.setVisible(true);
			copyRightType.setText(copyRightHolder);
			copyRightLbl.setText(GL1699+GL_SPL_SEMICOLON);
		}else{
			copyRightPanel.setVisible(false);
		}
	}

	private void setAuthorDetails(String author) {
		// TODO Auto-generated method stub
		if(author!=null&&!author.equalsIgnoreCase("")&&!author.equalsIgnoreCase("null")){
			authorPanel.setVisible(true);
			authorLbl.setText(GL0573+GL_SPL_SEMICOLON);
			authorName.setText(author);
		}else{
			authorPanel.setVisible(false);
		}
	}

	private void setdataTypeDetails(String dataType) {
		// TODO Auto-generated method stub
		if(dataType!=null&&!dataType.equalsIgnoreCase("")&&!dataType.equalsIgnoreCase("null")){
			DataTypePanel.setVisible(true);
			dataTypeFormat.setText(dataType);
			dataTypeLbl.setText(GL1688+GL_SPL_SEMICOLON);
		}else{
			DataTypePanel.setVisible(false);
		}
	}

	private void setlanguageDetails(String language) {
		// TODO Auto-generated method stub
		if(language!=null&&!language.equalsIgnoreCase("")&&!language.equalsIgnoreCase("null")){
			languagePanel.setVisible(true);
			languageType.setText(language);
			languageLbl.setText(GL1696+GL_SPL_SEMICOLON);
		}else{
			languagePanel.setVisible(false);
		}
	}

	private void setCountryCodeDetails(String countryCode) {
		// TODO Auto-generated method stub
		if(countryCode!=null&&!countryCode.equalsIgnoreCase("")&&!countryCode.equalsIgnoreCase("null")){
			countryCodePanel.setVisible(true);
			countryCodeType.setText(countryCode);
			countryCodeLbl.setText(GL1697+GL_SPL_SEMICOLON);
		}else{
			countryCodePanel.setVisible(false);
		}
	}

	private void setageRangeDetails(String ageRange) {
		// TODO Auto-generated method stub
		if(ageRange!=null&&!ageRange.equalsIgnoreCase("")&&!ageRange.equalsIgnoreCase("null")){
			ageRangePanel.setVisible(true);
			ageRangeType.setText(ageRange);
			ageRangeLbl.setText(GL1692+GL_SPL_SEMICOLON);
		}else{
			ageRangePanel.setVisible(false);
		}
	}

	private void setinteractivityTypeDetails(String interactivityType) {
		// TODO Auto-generated method stub
		if(interactivityType!=null&&!interactivityType.equalsIgnoreCase("")&&!interactivityType.equalsIgnoreCase("null")){
			interactivityTypePanel.setVisible(true);
			interactiveType.setText(interactivityType);
			interactiveLbl.setText(GL1689+GL_SPL_SEMICOLON);
		}else{
			interactivityTypePanel.setVisible(false);
		}
	}

	private void seteducationalRoleDetails(String educationalRole) {
		if(educationalRole!=null&&!educationalRole.equalsIgnoreCase("")&&!educationalRole.equalsIgnoreCase("null")){
			eduRolePanel.setVisible(true);
			eduRoleType.setText(educationalRole);
			eduRoleLbl.setText(GL1691+GL_SPL_SEMICOLON);
		}else{
			eduRolePanel.setVisible(false);
		}
	}


	private void setedAlignDetails(String educationalAlignment) {
		if(educationalAlignment!=null&&!educationalAlignment.equalsIgnoreCase("")&&!educationalAlignment.equalsIgnoreCase("null")){
			eduAllignPanel.setVisible(true);
			eduAllignType.setText(educationalAlignment);
			eduAllignLbl.setText(GL1690+GL_SPL_SEMICOLON);
		}else{
			eduAllignPanel.setVisible(false);
		}
	}

	private void setHostDetails(String host) {
		if(host!=null&&!host.equalsIgnoreCase("")&&!host.equalsIgnoreCase("null")){
			hostPanel.setVisible(true);
			hostLbl.setText(GL1700+GL_SPL_SEMICOLON);
			hostType.setText(host);
		}else{
			hostPanel.setVisible(false);
		}
	}

	/*private void setCreatedDate(Date createdOn) {
		
		String dateString = DateTimeFormat.getFormat("MM/dd/yyyy").format(createdOn);
		dateCreatedPanel.setVisible(true);
		createdDateInfo.setText(dateString);
		dateCreatedLbl.setText(GL1717+GL_SPL_SEMICOLON);
	}*/

	private void setThumbnailUrl(String url) {
		thumbnailurlValue.clear();
		if(url==null||url.equalsIgnoreCase("")||url.equalsIgnoreCase("null")){
			thumbnailPanel.setVisible(false);
		}else{
			thumbnailPanel.setVisible(false);
		/*	if(!url.substring(0, 4).equalsIgnoreCase("http")){
				url= "http://"+url;
			}
			Anchor thumbnailAnchor=new Anchor(url);
			thumbnailAnchor.setHref(url);
			thumbnailAnchor.setStyleName("");
			thumbnailAnchor.setTarget("_blank");
			
			thumbnailurlValue.add(thumbnailAnchor);
			thumbnailText.setText(GL1718+GL_SPL_SEMICOLON);*/
		}
		
	}

	public void setResourceDescription(String resourceDescription){
		this.resourceDescription.clear();
		this.resourceDescriptionTitle.clear();
		if(resourceDescription!=null && !resourceDescription.equalsIgnoreCase("null") && !resourceDescription.equalsIgnoreCase("")){
			this.resourceDescription.setVisible(true);
			this.resourceDescriptionTitle.setVisible(true);
			//this.resourceDescriptionTitle.setVisible(false);
			this.learningobjectiveText.setVisible(true);
			if(resourceDescription.length()>415){
				resourceDescription =(resourceDescription.substring(0, 415))+"...";
				this.resourceDescription.add(setText(resourceDescription));
				this.resourceDescriptionTitle.add(setText(GL1242+GL_SPL_SEMICOLON));
			}
			else{
				if(setText(resourceDescription).equals("")){
					this.resourceDescription.setVisible(false);
					this.resourceDescriptionTitle.setVisible(false);
				}else{
					this.resourceDescription.setVisible(true);
					this.resourceDescriptionTitle.setVisible(true);
					//this.resourceDescriptionTitle.setVisible(false);
					this.resourceDescription.add(setText(resourceDescription));
					this.resourceDescriptionTitle.add(setText(GL1242+GL_SPL_SEMICOLON));
				}
				
			}
		}
		else
		{
			//this.resourceDescription.add(setText(GL0977));
			this.resourceDescription.setVisible(false);
			this.resourceDescriptionTitle.setVisible(false);
			this.learningobjectiveText.setVisible(false);
		}
	}
	
	public void setResourceAttribution(String attribution,Set<CodeDo> taxonomoyList){
		
		List<String> coursesList=new ArrayList<String>();
		if(taxonomoyList!=null){
			Iterator<CodeDo> taxonomyIterator=taxonomoyList.iterator();
			while (taxonomyIterator.hasNext()) {
				CodeDo codeDo=taxonomyIterator.next();
				if(codeDo.getDepth()==2){
					coursesList.add(codeDo.getLabel());
				}
			}
			
		}
		courseInfo.clear();
		if(coursesList.size()>0){
			final Label courseInfoLabel=new Label(coursesList.get(0));
			courseInfoLabel.setStyleName(PlayerBundle.INSTANCE.getPlayerStyle().resourceCourseLabel());
			courseInfo.add(courseInfoLabel);
			coursePanel.setVisible(true);
		}
		if(coursesList.size()>1){
			final Label courseCountLabel=new Label("+"+(coursesList.size()-1)); 
			courseCountLabel.setStyleName(PlayerBundle.INSTANCE.getPlayerStyle().resourceCourseNum());
			courseInfo.add(courseCountLabel);
			Widget Coursewidget = getToolTipwidgets(coursesList);
			courseCountLabel.addMouseOverHandler(new MouseOverShowToolTip(Coursewidget));
			courseCountLabel.addMouseOutHandler(new MouseOutHideToolTip());
			 coursePanel.setVisible(true);
			
		}
		if(coursesList.size()==0){
			 /*Label courseInfoLabel=new Label();
			 courseInfoLabel.setText(GL0977);
			 courseInfo.clear();
			 courseInfo.add(courseInfoLabel);*/
			 coursePanel.setVisible(false);
		}
		
	}
	private Widget getCommonwidget(List<String> commonList) {
		
		FlowPanel toolTipwidgets = new FlowPanel();
		for(int i=2;i<commonList.size();i++){
			Label commonLabel = new Label(commonList.get(i));
			toolTipwidgets.add(commonLabel);
		}
		return toolTipwidgets;
	}
	private Widget getToolTipwidgets(List<String> coursesList) {
		
		FlowPanel toolTipwidgets = new FlowPanel();
		for(int i=1;i<coursesList.size();i++){
			Label courseLabel = new Label(coursesList.get(i));
			toolTipwidgets.add(courseLabel);
		}
		return toolTipwidgets;
	}
	public void setResourceViewsCount(String viewCount){
		String viewCountLabel=viewCount.equals("1")?viewCount+" "+GL1428:viewCount+" "+GL0934;
		resourceView.setText(viewCountLabel);
		//resourceViewsCount.setText(viewCountLabel);
	}
	
	
	public void setCourseInfo(){
		
	}
	/*public void setPublisher(String publisherName,String resourceUrl){
		if(publisherName==null||publisherName.equalsIgnoreCase("")||publisherName.equalsIgnoreCase("null")){
			//lblPublisher.setText(GL0977);
			publisherPanel.setVisible(false);
		}
		else{
			if((!resourceUrl.startsWith("https://docs.google.com"))&&(!resourceUrl.startsWith("http://docs.google.com"))){
				lblPublisher.setText(publisherName);
				publisherPanel.setVisible(true);
				}
				else{
				//	lblPublisher.setText(GL0977);	
					publisherPanel.setVisible(false);
				}
		}
	}*/
	public void setGrades(String gradesText){
		
		if(gradesText!=null&&!gradesText.equalsIgnoreCase("")&&!gradesText.equalsIgnoreCase("null")){
				this.gradesText.setText(getGrades(gradesText));
				gradesPanel.setVisible(true);
			}else{
				gradesPanel.setVisible(false);
				//this.gradesText.setText(GL0977);
			}
	}
	public void setOriginalUrl(String assetUri,String folder,String originalUrl,String resourceTypeName){
		this.originalUrlText.clear();
		if(originalUrl!=null&&!originalUrl.equalsIgnoreCase("")&&!originalUrl.equalsIgnoreCase("null")){
			if(resourceTypeName.equalsIgnoreCase("image/png")){
				if(!originalUrl.substring(0, 4).equalsIgnoreCase("http")){
					originalUrl=assetUri+folder+originalUrl;
				}
			}
			String[] urlFormat = originalUrl.split("\\.");
			String urlExtension = urlFormat[urlFormat.length - 1];
			if(urlExtension.equalsIgnoreCase("pdf")){
				if(!originalUrl.substring(0, 4).equalsIgnoreCase("http")){
					originalUrl=assetUri+folder+originalUrl;
				}
			}
			Anchor originalUrlAnchor=new Anchor(originalUrl);
			originalUrlAnchor.setHref(originalUrl);
			originalUrlAnchor.setStyleName("");
			originalUrlAnchor.setTarget("_blank");
			this.originalUrlText.add(originalUrlAnchor);
			this.originalUrlTitle.setVisible(true);
			this.originalUrlText.setVisible(true);
		}else{
			/*HTML html=new HTML(GL0977);
			html.setStyleName("");
			this.originalUrlText.add(html);*/
			this.originalUrlTitle.setVisible(false);
			this.originalUrlText.setVisible(false);
		}
	}
	
	public String getGrades(String grade){
		if (grade != null) {
			grade = grade.replace("null,", "").replace("null ,", "").replace("null", "");
			
		}
		if (StringUtil.hasValidString(grade)) {
			boolean isKindergarten = false;
			boolean isHigherEducation = false;

			if (grade.indexOf("Kindergarten") != -1) {
				isKindergarten = true;
			}

			if (grade.indexOf("Higher Education") != -1) {
				isHigherEducation = true;
			}
			if (grade.indexOf("-") > 0) {
				if (grade.indexOf(",") == -1) {
					grade = generateGradeIfHypen(grade);
					
				}
			}

			List<String> gradeList = Arrays.asList(grade.split(","));
			int gradeListSize = gradeList.size();

			StringBuilder finalGradeStringB = new StringBuilder();
			
			
			List<Integer> gradeListInt = new ArrayList<Integer>();
			//finalGradeStringB.append(gradeListSize > 1 ? "Grades: " : "Grade: ");
			
			for (String eachGrade1 : gradeList) {
				if (!eachGrade1.equalsIgnoreCase("Kindergarten")
						&& !eachGrade1.equalsIgnoreCase("Higher Education")) {

					eachGrade1 = eachGrade1.replaceAll("th", "")
							.replaceAll("TH", "").replaceAll("st", "")
							.replaceAll("ST", "").replaceAll("nd", "")
							.replaceAll("ND", "").replaceAll("rd", "")
							.replaceAll("RD", "");
					eachGrade1 = eachGrade1.toLowerCase()
							.replaceAll("Grade", "").replaceAll("grade", "");
					eachGrade1 = eachGrade1.toLowerCase().replaceAll("K-", "")
							.replaceAll("k-", "");
					eachGrade1 = eachGrade1.toLowerCase().replaceAll("K", "")
							.replaceAll("k", "");
					try {
					//	gradeListInt.clear();
						String grad[] = generateGradeIfHypen(eachGrade1).trim().split(",");
						for (int i = 0; i < grad.length; i++) {
 
							gradeListInt.add(Integer.parseInt(grad[i]));
							
						}

					} catch (Exception e) {
						//gradeListInt.add(0);
						e.printStackTrace();
					}
				}
			}
			gradeListInt = sortList(gradeListInt);
			String finalGrde = formatGrades(gradeListInt);
			if(finalGrde.equalsIgnoreCase(ALL_GRADES)){
				finalGradeStringB.append(ALL_GRADES);
			}else{
			   if(isKindergarten&&isHigherEducation){
				    finalGradeStringB.append(finalGrde.equalsIgnoreCase("")?"Kindergarten":"Kindergarten, ");
					finalGradeStringB.append(finalGrde);
					finalGradeStringB.append(finalGrde.equalsIgnoreCase("")?", Higher Education":", Higher Education");
			   }else if(isKindergarten){
					finalGradeStringB.append(finalGrde.equalsIgnoreCase("")?"Kindergarten":"Kindergarten, ");
					finalGradeStringB.append(finalGrde);
				}else if(isHigherEducation){
					finalGradeStringB.append(finalGrde);
					finalGradeStringB.append(finalGrde.equalsIgnoreCase("")?"Higher Education":", Higher Education");
				}else{
					finalGradeStringB.append(finalGrde);
				}
			}
			grade = finalGradeStringB.toString();
			
		}
		return grade;
	}
	private String generateGradeIfHypen(String grade) {
		String gradeList[];
	 
		StringBuilder gradeStr = new StringBuilder();
		gradeList = grade.split("-");
		if (gradeList.length >= 2) {
			int start = Integer.parseInt(gradeList[0].trim());
			int end = Integer.parseInt(gradeList[1].trim());
			if (start < end) {
				for (int i = start; i <= end; i++) {
					if (i == end) {
						gradeStr.append(i);
					} else {
						gradeStr.append(i).append(",");
					}
				}
			}
		} else {
			gradeStr.append(Math.round(Double.parseDouble(gradeList[0].trim())));
			/*gradeStr.append(Integer.parseInt("0"));*/
		}
		return gradeStr.toString();
	}	
	public List<Integer> sortList(List<Integer> list) {

		int listSize = list.size();

		for (int i = 0; i < listSize; i++) {

			for (int j = 1; j < listSize - i; j++) {
				if (list.get(j - 1) > list.get(j)) {
					int temp = list.get(j - 1);
					list.set(j - 1, list.get(j));
					list.set(j, temp);
				}
			}
		}

		return list;
	}
	private String formatGrades(List<Integer> list) {
		
    	StringBuffer grade = new StringBuffer();
		try {
			if(list.size()>3){
                int firstGrade=list.get(0);
                int lastGrade=list.get(list.size()-1);
                String displayGrade=firstGrade+"-"+lastGrade;
                if(list.size()>=5){
                	if(firstGrade<=4&&lastGrade>=9){
                		grade.append("ALL GRADES");
                	}
                	else{
                		grade.append(firstGrade);
                		grade.append("-");
                		grade.append(lastGrade);
                	}
                	
                }else{
                        if(displayGrade.equalsIgnoreCase("1-12")){
                                grade.append("ALL GRADES");
                        }else{
                                grade.append(firstGrade);
                                grade.append("-");
                                grade.append(lastGrade);
                        }
                }
        }else{
                for(int i=0;i<list.size();i++){
                	grade.append(list.get(i));
                	if(i!=(list.size()-1)){
						grade.append(", ");
					}
                }
        }
			
			
		} catch (Exception e) {
		}
		return grade.toString();
	}

	public HTML setText(String text){
		text=text.replaceAll("</p>", " ").replaceAll("&nbsp;", " ").replaceAll("<p>", "")
					.replaceAll("<span>", "").replaceAll("<br data-mce-bogus=\"1\">", "").replaceAll("<br", "").replaceAll(">", "");
		HTML html=new HTML(text);
		html.setStyleName("");
		return html;
	}
	
	public void setResourceLicenceLogo(String assetUrl,LicenseDo licenseDo){
		if(licenseDo!=null){
			if(licenseDo.getIcon()!=null&&!licenseDo.getIcon().trim().equals("")){
				Image image=new Image();
				image.setUrl(assetUrl+licenseDo.getIcon());
				image.addMouseOverHandler(new MouseOverShowStandardToolTip(licenseDo.getCode()));
				//image.addMouseOverHandler(new MouseOverShowStandardToolTip(licenseDo.getCode()+"         "+licenseDo.getName()));
				image.addMouseOutHandler(new MouseOutHideToolTip());
				licenceContainer.setVisible(true);
				rightsLogoContainer.clear();
				rightsLogoContainer.add(image);
			}
			else{
				licenceContainer.setVisible(false);
				rightsLogoContainer.clear();
				//rightsLogoContainer.add(setText(GL0977));
			}
		}else{
			licenceContainer.setVisible(false);
			rightsLogoContainer.clear();
			//rightsLogoContainer.add(setText(GL0977));
		}		
	}	
	
	
	
	public static void renderStandards(FlowPanel standardsContainer, List<Map<String,String>> standardsList) {
		standardsContainer.clear();
		
		if (standardsList != null) {
			standaInfo.setVisible(false);
			standardsContentContainer.setVisible(true);
			//List<Map<String, String>> standards = searchResultDo.getStandards();
			Iterator<Map<String, String>> iterator = standardsList.iterator();
			int count = 0;
			FlowPanel toolTipwidgets = new FlowPanel();
			while (iterator.hasNext()) {
				Map<String, String> standard = iterator.next();
				String stdCode = standard.get(STANDARD_CODE);
				String stdDec = standard.get(STANDARD_DESCRIPTION);
				if (count > 2) {
					if (count < 18){
						StandardSgItemVc standardItem = new StandardSgItemVc(stdCode, stdDec);
						toolTipwidgets.add(standardItem);
					}
				} else {
					DownToolTipWidgetUc toolTipUc = new DownToolTipWidgetUc(new Label(stdCode), new Label(stdDec), standardsList);
					toolTipUc.setStyleName(PlayerBundle.INSTANCE.getPlayerStyle().getstandardMoreInfo());
					standardsContainer.add(toolTipUc);
				}
				count++;
			}
			if (standardsList.size()>18){
				final Label left = new Label("+"+(standardsList.size() - 18));
				toolTipwidgets.add(left);
				standardsContentContainer.setVisible(true);
			}
			if (standardsList.size() > 2) {
				Integer moreStandardsCount = standardsList.size() - 3;
				if (moreStandardsCount >0){
					DownToolTipWidgetUc toolTipUc = new DownToolTipWidgetUc(new Label("+" + moreStandardsCount), toolTipwidgets, standardsList);
					toolTipUc.setStyleName(PlayerBundle.INSTANCE.getPlayerStyle().getstandardMoreLink());
					standardsContainer.add(toolTipUc);
					standardsContentContainer.setVisible(true);
				}
			}
			if(standardsList.size()==0)
			{
				//standaInfo.setVisible(true);
				standardsContentContainer.setVisible(false);
			}
		}
		else{
			standardsContentContainer.setVisible(false);
		}
	}
	
	public void loadResourceReleatedCollections(String resourceGooruOid){
		reosourceReleatedCollections.clear();
		collectionItemSizeData=0;
		currentPageSize=1;
		collectionsCount.setText("");
		gooruResourceOId = resourceGooruOid;
		getUiHandlers().getCollectionList(resourceGooruOid, currentPageSize.toString(), PAGE_SIZES); 
		
	}
	
	public String getResourceTypeImage(String resourceType){
		if(resourceType.equalsIgnoreCase("Video")||resourceType.equalsIgnoreCase("Videos")){
			return PlayerBundle.INSTANCE.getPlayerStyle().videoResourceTypeInfo();
		}else if(resourceType.equalsIgnoreCase("Interactive")){
			return PlayerBundle.INSTANCE.getPlayerStyle().interactiveResourceTypeInfo();
		}
		else if(resourceType.equalsIgnoreCase("Website")||resourceType.equalsIgnoreCase("Webpage")){
			return PlayerBundle.INSTANCE.getPlayerStyle().websiteResourceTypeInfo();		
		}
		else if(resourceType.equalsIgnoreCase("Slide")){
			return PlayerBundle.INSTANCE.getPlayerStyle().imageResourceTypeInfo();
		}
		else if(resourceType.equalsIgnoreCase("Textbook")){
			return PlayerBundle.INSTANCE.getPlayerStyle().textResourceTypeInfo();
		}
		else if(resourceType.equalsIgnoreCase("Question")){
			return PlayerBundle.INSTANCE.getPlayerStyle().questionResourceTypeInfo();
		}
		else if(resourceType.equalsIgnoreCase("lesson")){
			return PlayerBundle.INSTANCE.getPlayerStyle().lessonResourceTypeInfo();
			
		}else if(resourceType.equalsIgnoreCase("Handout")){
			return PlayerBundle.INSTANCE.getPlayerStyle().textResourceTypeInfo();
		} else if(resourceType.equalsIgnoreCase("text")){
			return PlayerBundle.INSTANCE.getPlayerStyle().textResourceTypeInfo();
		}
		else if(resourceType.equalsIgnoreCase("image")){
			return PlayerBundle.INSTANCE.getPlayerStyle().imageResourceTypeInfo();
		}
		else if(resourceType.equalsIgnoreCase("audio")){
			return PlayerBundle.INSTANCE.getPlayerStyle().audioResourceTypeInfo();
		}else if(resourceType.equalsIgnoreCase("exam")){
			return PlayerBundle.INSTANCE.getPlayerStyle().websiteResourceTypeInfo();
		}
		else {
			return PlayerBundle.INSTANCE.getPlayerStyle().otherResourceTypeInfo();
		}
	}
	public void setResourceTypeImage(String resourceType){
		if(resourceType!=null){
			resourceType=resourceType.toLowerCase();
			if(resourceType.equalsIgnoreCase("lesson")||resourceType.equalsIgnoreCase("textbook")||resourceType.equalsIgnoreCase("handout"))
			{
				resourceType=resourceType.replaceAll("lesson", "Text").replaceAll("textbook", "Text").replaceAll("handout", "Text");
			}
			if(resourceType.equalsIgnoreCase("slide"))
			{
				resourceType=resourceType.replaceAll("slide","Image");
			}
			if(resourceType.equalsIgnoreCase("exam")||resourceType.equalsIgnoreCase("challenge")||resourceType.equalsIgnoreCase("website"))
			{
				resourceType=resourceType.replaceAll("exam","Webpage").replaceAll("challenge", "Webpage").replaceAll("website", "Webpage");
			}
			lblresourceType.setText(resourceType);
			resourceTypeImage.setStyleName(getResourceTypeImage(resourceType));
		}
	}
	private String removeHtmlTags(String html){
        html = html.replaceAll("</p>", " ").replaceAll("<p>", "").replaceAll("<br data-mce-bogus=\"1\">", "").replaceAll("<br>", "").replaceAll("</br>", "");
        return html;
	}
	
	@Override
	public void loadResourceCollection(ResoruceCollectionDo resoruceCollectionDo) {
		List<ResourceSearchResultDo> resourceSearchResultList=resoruceCollectionDo.getSearchResults();
		collectionsCount.setText("("+resoruceCollectionDo.getTotalHitCount()+")");
		totalItemSize = resoruceCollectionDo.getTotalHitCount();
		collectionItemSizeData=currentPageSize*Integer.parseInt(PAGE_SIZES);
		for(int i=0;i<resourceSearchResultList.size();i++){
			reosourceReleatedCollections.add(new ResourceCollectionView(resourceSearchResultList.get(i)));
		}
	}
	
	@UiHandler("scrollPanel")
	public void onScrollReosourceReleatedCollections(ScrollEvent scrollEvent ){
		
		if(scrollPanel.getVerticalScrollPosition() == scrollPanel.getMaximumVerticalScrollPosition() && collectionItemSizeData<totalItemSize){
			currentPageSize=currentPageSize+1;
			getUiHandlers().getCollectionList(gooruResourceOId, currentPageSize.toString(), PAGE_SIZES);
		}
		
	}
	
	public class MouseOverShowStandardToolTip implements MouseOverHandler
	{
		String desc=null;
		
		public MouseOverShowStandardToolTip(String description) {
			this.desc = description;
		}
		
		@Override
		public void onMouseOver(MouseOverEvent event) {
			toolTip = new ToolTipPopUp(desc, (event.getRelativeElement().getAbsoluteLeft()-83),(event.getRelativeElement().getAbsoluteTop()+22));
			toolTip.setStyleName("");
			toolTip.show();
			toolTip.getElement().getStyle().setZIndex(99999);
		}
	}
	
	public class MouseOverShowToolTip implements MouseOverHandler
	{
		Widget widget;
		
		public MouseOverShowToolTip(Widget coursewidget) {
			this.widget = coursewidget;
		}

		@Override
		public void onMouseOver(MouseOverEvent event) {
			//toolTip = new ToolTipPopUp(widget,getWidget().getAbsoluteLeft() + (getWidget().getOffsetWidth() / 2) - (tooltipPopUpUc.getOffsetWidth() / 2), getWidget().getAbsoluteTop() + getWidget().getOffsetHeight());	
			toolTip = new ToolTipPopUp(widget,(event.getRelativeElement().getAbsoluteLeft()-55),(event.getRelativeElement().getAbsoluteTop()+5)); 
			toolTip.setStyleName(PlayerBundle.INSTANCE.getPlayerStyle().courseTooltip());
			toolTip.show();
		}
		
	}
	
	public class MouseOutHideToolTip implements MouseOutHandler
	{
		@Override
		public void onMouseOut(MouseOutEvent event) {
			toolTip.hide();
		}
		
	}

	public HTMLEventPanel getHideButton()
	{
		return hideButton;
	}
	
	/**
	 * 
	 * @function onhideBtnClicked 
	 * 
	 * @created_date : 11-Dec-2013
	 * 
	 * @description
	 * 
	 * 
	 * @parm(s) : @param clickEvent
	 * 
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 */
	@UiHandler("hideButton")
	public void onhideBtnClicked(ClickEvent clickEvent) 
	{
		PlaceRequest collectionRequest = AppClientFactory.getPlaceManager().getCurrentPlaceRequest();
		String collectionId = collectionRequest.getParameter("id", null);
		String collectionItemId = collectionRequest.getParameter("rid", null);
		String chkViewParam = collectionRequest.getParameter("view", null);
		
		Map<String,String> params = new LinkedHashMap<String,String>();
		params.put("id", collectionId);
		params = PreviewPlayerPresenter.setConceptPlayerParameters(params);
		
	if(AppClientFactory.getCurrentPlaceToken().contains(PlaceTokens.RESOURCE_PLAY))
	{
		PlaceRequest request=new PlaceRequest(PlaceTokens.RESOURCE_PLAY).
				with("id", collectionId);
		AppClientFactory.getPlaceManager().revealPlace(false,request,true);
	}
	else if(AppClientFactory.getCurrentPlaceToken().contains(PlaceTokens.COLLECTION_PLAY) && chkViewParam == null && collectionItemId != null)
	{
		PlaceRequest request=new PlaceRequest(PlaceTokens.COLLECTION_PLAY).
				with("id", collectionId).with("rid", collectionItemId);
		AppClientFactory.getPlaceManager().revealPlace(false,request,true);
	}
	else if(AppClientFactory.getCurrentPlaceToken().contains(PlaceTokens.PREVIEW_PLAY) && chkViewParam == null && collectionItemId != null)
	{
		params.put("rid", collectionItemId);
		PlaceRequest placeRequest=AppClientFactory.getPlaceManager().preparePlaceRequest(PlaceTokens.PREVIEW_PLAY, params);
		AppClientFactory.getPlaceManager().revealPlace(false, placeRequest, true);
	}
	else if(AppClientFactory.getCurrentPlaceToken().contains(PlaceTokens.COLLECTION_PLAY) && chkViewParam == null && collectionItemId == null)
	{
		PlaceRequest request=new PlaceRequest(PlaceTokens.COLLECTION_PLAY).
				with("id", collectionId);
		AppClientFactory.getPlaceManager().revealPlace(false,request,true);
	}
	else if(AppClientFactory.getCurrentPlaceToken().contains(PlaceTokens.PREVIEW_PLAY) && chkViewParam == null && collectionItemId == null)
	{
		PlaceRequest placeRequest=AppClientFactory.getPlaceManager().preparePlaceRequest(PlaceTokens.PREVIEW_PLAY, params);
		AppClientFactory.getPlaceManager().revealPlace(false, placeRequest, true);
	}
	else if(AppClientFactory.getCurrentPlaceToken().contains(PlaceTokens.COLLECTION_PLAY) && chkViewParam.equalsIgnoreCase("end"))
	{
		PlaceRequest request=new PlaceRequest(PlaceTokens.COLLECTION_PLAY).
				with("id", collectionId).with("view", "end");
		AppClientFactory.getPlaceManager().revealPlace(false,request,true);
	}
	else if(AppClientFactory.getCurrentPlaceToken().contains(PlaceTokens.PREVIEW_PLAY) && chkViewParam.equalsIgnoreCase("end"))
	{
		params.put("view", "end");
		PlaceRequest placeRequest=AppClientFactory.getPlaceManager().preparePlaceRequest(PlaceTokens.PREVIEW_PLAY, params);
		AppClientFactory.getPlaceManager().revealPlace(false, placeRequest, true);
	}
	}

	@Override
	public void setCollectionTitle(String mycollectionTitle) {
		// TODO Auto-generated method stub
		this.title =mycollectionTitle;
	}
	
	@UiHandler("addTagsBtn")
	public void onAddTagsBtnClicked(ClickEvent clickEvent) {
		if(AppClientFactory.isAnonymous()) {
			AppClientFactory.fireEvent(new InvokeLoginEvent());
		} else {
			/*		PlaceRequest collectionRequest = AppClientFactory.getPlaceManager().getCurrentPlaceRequest();
			String resourceIdVal = collectionRequest.getParameter("rid", null);*/
			popup=new AddTagesPopupView(collectionItemDoGlobal.getResource().getGooruOid()) {
				
				@Override
				public void closePoup(boolean isCancelclicked) {
			        this.hide();
			        if(!isCancelclicked){
			        	 SuccessPopupViewVc success = new SuccessPopupViewVc() {

								@Override
								public void onClickPositiveButton(ClickEvent event) {
									this.hide();
								}
							};
							success.setGlassStyleName(PlayerBundle.INSTANCE.getPlayerStyle().resourceTagsGlassPanel());
							success.setHeight("253px");
							success.setWidth("450px");
							success.setPopupTitle(GL1795);
							success.setDescText(GL1796);
							success.enableTaggingImage();
							success.setPositiveButtonText(GL0190);
							success.center();
							success.show();
							success.getElement().getStyle().setZIndex(99999);
			        }
				}
			};
			popup.show();
			popup.setPopupPosition(popup.getAbsoluteLeft(),Window.getScrollTop()+10);
		}
	}
	
	UpdateRatingsInRealTimeHandler setRatingWidgetMetaData = new UpdateRatingsInRealTimeHandler() {	
		
		@Override
		public void updateRatingInRealTime(String gooruOid, double average,Integer count) {
			if(collectionItemDoGlobal.getResource()!=null){
				if(collectionItemDoGlobal.getResource().getGooruOid().equals(gooruOid)){
					ratingWidgetView.getRatingCountLabel().setText(count.toString()); 
					ratingWidgetView.setAvgStarRating(average);
					if(count==1&&isRatingUpdated){
						isRatingUpdated=false;
						ratingWidgetView.getRatingCountLabel().getElement().removeAttribute("class");
						ratingWidgetView.getRatingCountLabel().getElement().setAttribute("style", "cursor: pointer;text-decoration: none !important;color: #1076bb;");
						ratingWidgetView.getRatingCountLabel().addClickHandler(new ShowRatingPopupEvent());
					}
				}
			}
		}
	};
	
	
	DeletePlayerStarReviewHandler deleteStarRating = new DeletePlayerStarReviewHandler(){

		@Override
		public void deleteStarRatings() {
			String zeroCount = "0";
			if(ratingWidgetView!=null){
				if(Integer.parseInt(ratingWidgetView.getRatingCountLabel().getText())==1){
					ratingWidgetView.getRatingCountLabel().getElement().removeAttribute("class");
					ratingWidgetView.getRatingCountLabel().getElement().setAttribute("style", "cursor: none;text-decoration: none !important;color: grey");
					ratingWidgetView.setAvgStarRating(0);
					ratingWidgetView.getRatingCountLabel().setText(zeroCount); 
				}
			}
			
		}
		
	};
}