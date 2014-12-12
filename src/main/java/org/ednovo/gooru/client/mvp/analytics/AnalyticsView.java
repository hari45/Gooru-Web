package org.ednovo.gooru.client.mvp.analytics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.ednovo.gooru.client.gin.AppClientFactory;
import org.ednovo.gooru.client.gin.BaseViewWithHandlers;
import org.ednovo.gooru.client.mvp.analytics.util.AnalyticsUtil;
import org.ednovo.gooru.client.uc.tooltip.ToolTip;
import org.ednovo.gooru.shared.i18n.MessageProperties;
import org.ednovo.gooru.shared.model.analytics.GradeJsonData;
import org.ednovo.gooru.shared.util.StringUtil;

import com.google.gwt.ajaxloader.client.Properties;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.EventTarget;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Widget;

public class AnalyticsView extends BaseViewWithHandlers<AnalyticsUiHandlers> implements IsAnalyticsView {

	private static AnalyticsViewUiBinder uiBinder = GWT
			.create(AnalyticsViewUiBinder.class);

	interface AnalyticsViewUiBinder extends UiBinder<Widget, AnalyticsView> {
	}
	
	private static MessageProperties i18n = GWT.create(MessageProperties.class);

	AnalyticsCssBundle res;
	
	@UiField Button btnCollectionSummary,btnCollectionProgress,btnCollectionResponses;
	
	@UiField ListBox loadCollections;
	
	@UiField HTMLPanel collectionProgressSlot,collectionSummarySlot;
	
	@UiField Image collectionProgressQuestionimg,collectionSummaryQuestionimg,collectionExportQuestionimg;
	
	boolean isSummayClicked=false,isProgressClicked=false,isPersonalizedBtnClicked=false;
	
	Map<String,GradeJsonData> loadcollectionsmap=new HashMap<String, GradeJsonData>();
	
	final String CLEARSUMMARY="ClearSummary",CLEARPROGRESS="ClearProgress",SUMMARY="Summary",PROGRESS="Progress",BELOWSCORE="BelowScore",ABOVESCORE="AboveScore";
	
	String unitCollectionId;
	int selectedUnitNumber;
	String unitId; 
	ToolTip toolTip;
	/**
	 * Default constructor
	 */
	public AnalyticsView() {
		this.res = AnalyticsCssBundle.INSTANCE;
		res.unitAssignment().ensureInjected();
		setWidget(uiBinder.createAndBindUi(this));	
		btnCollectionSummary.addClickHandler(new ViewAssignmentClickEvent("Summary"));
		btnCollectionProgress.addClickHandler(new ViewAssignmentClickEvent("Progress"));
		btnCollectionResponses.addClickHandler(new ViewAssignmentClickEvent(""));
		setStaticData();
	}
	@Override
	public void setGradeCollectionData(ArrayList<GradeJsonData> gradeData) {
		loadcollectionsmap.clear();
		loadCollections.clear();
		if(gradeData!=null){
			for (GradeJsonData gradeJsonData : gradeData) {
				loadcollectionsmap.put(gradeJsonData.getResourceGooruOId(), gradeJsonData);
				loadCollections.addItem(gradeJsonData.getTitle(), gradeJsonData.getResourceGooruOId());
			}
		}
	}
	/**
	 * This method is used to set static text.
	 */
	void setStaticData(){
		
		collectionProgressQuestionimg.setUrl("images/question.png");
		collectionSummaryQuestionimg.setUrl("images/question.png");
		collectionExportQuestionimg.setUrl("images/question.png");
	
		collectionProgressQuestionimg.addMouseOverHandler(new QuestionMouseToolTip("1",collectionProgressQuestionimg));
		collectionSummaryQuestionimg.addMouseOverHandler(new QuestionMouseToolTip("2",collectionSummaryQuestionimg));
		collectionExportQuestionimg.addMouseOverHandler(new QuestionMouseToolTip("3",collectionExportQuestionimg));
		
		collectionProgressQuestionimg.addMouseOutHandler(new QuestionMouseOutToolTip());
		collectionSummaryQuestionimg.addMouseOutHandler(new QuestionMouseOutToolTip());
		collectionExportQuestionimg.addMouseOutHandler(new QuestionMouseOutToolTip());
		
		StringUtil.setAttributes(loadCollections.getElement(), "ddlLoadCollections", null, null);
		StringUtil.setAttributes(btnCollectionSummary.getElement(), "btnCollectionSummary", i18n.GL2296(), i18n.GL2296());
		StringUtil.setAttributes(btnCollectionProgress.getElement(), "btnCollectionProgress", i18n.GL2296(), i18n.GL2296());
		StringUtil.setAttributes(btnCollectionResponses.getElement(), "btnCollectionResponses", i18n.GL2258(),i18n.GL2258());
	}
	/**
	 * This class is used to hide the popup
	 */
	public class QuestionMouseOutToolTip implements MouseOutHandler{
		@Override
		public void onMouseOut(MouseOutEvent event) {
			EventTarget target = event.getRelatedTarget();
			  if (Element.is(target)) {
				  if (!toolTip.getElement().isOrHasChild(Element.as(target))){
					  toolTip.hide();
				  }
			  }
		}
	}
	/**
	 * This inner class is used to handle the mouse over events.
	 */
	public class QuestionMouseToolTip implements MouseOverHandler{
		String fromString="";
		Image image;
		QuestionMouseToolTip(String fromString,Image image){
			this.fromString=fromString;
			this.image=image;
		}
		@Override
		public void onMouseOver(MouseOverEvent event) {
			String setText="";
			if(fromString.equalsIgnoreCase("1")){
				setText=i18n.GL3089();
			}else if(fromString.equalsIgnoreCase("2")){
				setText=i18n.GL3088();
			}else{
				setText=i18n.GL3090();
			}
			toolTip = new ToolTip(setText,"");
			toolTip.getTootltipContent().getElement().setAttribute("style", "width: 258px;");
			toolTip.getElement().getStyle().setBackgroundColor("transparent");
			toolTip.getElement().getStyle().setPosition(Position.ABSOLUTE);
			toolTip.setPopupPosition(image.getAbsoluteLeft()-(50+22), image.getAbsoluteTop()+22);
			toolTip.show();
		}
	}
	public class ViewAssignmentClickEvent implements ClickHandler{
		private String clicked;
		public ViewAssignmentClickEvent(String clicked){
			this.clicked=clicked;
		}
		@Override
		public void onClick(ClickEvent event) {
			String selectedCollectionId=loadCollections.getValue(loadCollections.getSelectedIndex());
			String selectedCollectionTitle=loadCollections.getItemText(loadCollections.getSelectedIndex());
			if(clicked.equalsIgnoreCase(PROGRESS)){
				if(isProgressClicked){
					isProgressClicked=false;
					btnCollectionProgress.setText(i18n.GL2296());
					getUiHandlers().setClickedTabPresenter(CLEARPROGRESS,selectedCollectionId,selectedCollectionTitle);
				}else{
					isProgressClicked=true;
					btnCollectionProgress.setText(i18n.GL2297());
					getUiHandlers().setClickedTabPresenter(PROGRESS,selectedCollectionId,selectedCollectionTitle);
				}
			}else if(clicked.equalsIgnoreCase(SUMMARY)){
				if(isSummayClicked){
					isSummayClicked=false;
					btnCollectionSummary.setText(i18n.GL2296());
					getUiHandlers().setClickedTabPresenter(CLEARSUMMARY,selectedCollectionId,selectedCollectionTitle);
				}else{
					isSummayClicked=true;
					btnCollectionSummary.setText(i18n.GL2297());
					getUiHandlers().setClickedTabPresenter(SUMMARY,selectedCollectionId,selectedCollectionTitle);
				}
			}else{
				String classpageId=AppClientFactory.getPlaceManager().getRequestParameter("classpageid", null);
				getUiHandlers().exportOEPathway(classpageId, "",AnalyticsUtil.getTimeZone());
			}
		}
	}

	/* (non-Javadoc)
	 * @see org.ednovo.gooru.client.mvp.analytics.IsAnalyticsView#resetData()
	 */
	@Override
	public void resetData(){
		loadCollections.clear();
	}
	
	/* (non-Javadoc)
	 * @see com.gwtplatform.mvp.client.ViewImpl#setInSlot(java.lang.Object, com.google.gwt.user.client.ui.Widget)
	 */
	@Override
	public void setInSlot(Object slot, Widget content) {
		if(content!=null){
			if(slot==AnalyticsPresenter.COLLECTION_PROGRESS_SLOT){
				collectionProgressSlot.clear();
				collectionProgressSlot.add(content);
			}else if(slot==AnalyticsPresenter.COLLECTION_SUMMARY_SLOT){
				collectionSummarySlot.clear();
				collectionSummarySlot.add(content);
			}
		}
	}

	/**
	 * This will set the unit collection id
	 * @param unitCollectionId
	 */
	public void setUnitCollectionId(String unitCollectionId) {
		this.unitCollectionId = unitCollectionId;
	}
	public HTMLPanel getCollectionSummarySlot(){
		return collectionSummarySlot;
	}
	public HTMLPanel getCollectionProgressSlot(){
		return collectionProgressSlot;
	}
	/**
	 * This will set the styles for the data table cells.
	 * @return
	 */
	com.google.gwt.visualization.client.Properties getPropertiesCell(){
			  Properties properties=Properties.create();
			  properties.set("style", "text-align:center;font-weight:bold;background-color: red;");
			  com.google.gwt.visualization.client.Properties p=properties.cast();
			  return p;
	}
}
