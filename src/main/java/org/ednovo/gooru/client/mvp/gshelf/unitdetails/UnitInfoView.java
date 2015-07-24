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
package org.ednovo.gooru.client.mvp.gshelf.unitdetails;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.client.gin.BaseViewWithHandlers;
import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.application.shared.model.code.CourseSubjectDo;
import org.ednovo.gooru.application.shared.model.folder.CreateDo;
import org.ednovo.gooru.application.shared.model.folder.FolderDo;
import org.ednovo.gooru.client.mvp.gshelf.ShelfTreeWidget;
import org.ednovo.gooru.client.mvp.gshelf.util.CourseGradeWidget;
import org.ednovo.gooru.client.mvp.gshelf.util.LiPanelWithClose;
import org.ednovo.gooru.client.uc.LiPanel;
import org.ednovo.gooru.client.uc.UlPanel;
import org.ednovo.gooru.client.util.SetStyleForProfanity;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.ScrollEvent;
import com.google.gwt.event.dom.client.ScrollHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

/**
 * @author Search Team
 *
 */
public class UnitInfoView extends BaseViewWithHandlers<UnitInfoUiHandlers> implements IsUnitInfoView {

	private static UnitInfoViewUiBinder uiBinder = GWT.create(UnitInfoViewUiBinder.class);
	
	@UiTemplate("UnitInfoView.ui.xml")
	interface UnitInfoViewUiBinder extends UiBinder<Widget, UnitInfoView> {
	}	
	
	public MessageProperties i18n = GWT.create(MessageProperties.class);

	@UiField HTMLPanel unitInfo,pnlGradeContainer,pnlGradeDescContainer;
	@UiField UlPanel ulMainGradePanel,ulSelectedItems;
	@UiField Button saveUnitBtn,nextCreateLessonBtn,taxonomyBtn,taxonomyToggleBtn;
	@UiField TextBox unitTitle;
	@UiField ScrollPanel scrollCoursediv;
	@UiField Label lblErrorMessage,lblErrorMessageForBig,lblErrorMessageForEssential;
	@UiField TextArea txaBigIdeas,txaEssentialQuestions;
	
	Map<Integer, ArrayList<String>> selectedValues=new HashMap<Integer,ArrayList<String>>();
	
	int domainPagination,domainPaginationCourseId = 0;
	
	List<LiPanelWithClose> unitLiPanelWithCloseArray = new ArrayList<LiPanelWithClose>();
	
	CourseGradeWidget courseGradeWidget,courseGradeWidget1;
	public FolderDo courseObj;
	final String ACTIVE="active";
	
	private static final String UNIT = "Unit";
	private static final String O1_LEVEL = "o1";
	
	LiPanel tempLiPanel=null;
	
	boolean isCreateLessonClicked=true;
	/**
	 * Class constructor 
	 * @param eventBus {@link EventBus}
	 */
	@Inject
	public UnitInfoView() {
		setWidget(uiBinder.createAndBindUi(this));
		unitInfo.getElement().setId("pnlCourseInfo");
		pnlGradeContainer.getElement().setId("pnlGradeContainer");
		ulMainGradePanel.getElement().setId("ulMainGradePanel");
		lblErrorMessage.setText("Please Enter Valid UnitName");
		lblErrorMessage.setVisible(false);
		taxonomyBtn.getElement().setId("taxonomyBtn");
		taxonomyToggleBtn.getElement().setId("taxonomyToggleBtn");
		taxonomyBtn.addClickHandler(new OnClickTaxonomy());
		taxonomyToggleBtn.addClickHandler(new OnClickTaxonomy());
		unitTitle.getElement().setPropertyString("placeholder", i18n.GL3364());
		unitTitle.addBlurHandler(new BlurHandler() {
			@Override
			public void onBlur(BlurEvent event) {
				SetStyleForProfanity.SetStyleForProfanityForTextBox(unitTitle, lblErrorMessage, false);
			}
		});
		txaBigIdeas.addBlurHandler(new BlurHandler() {
			@Override
			public void onBlur(BlurEvent event) {
				SetStyleForProfanity.SetStyleForProfanityForTextArea(txaBigIdeas, lblErrorMessageForBig, false);
			}
		});
		txaEssentialQuestions.addBlurHandler(new BlurHandler() {
			@Override
			public void onBlur(BlurEvent event) {
				SetStyleForProfanity.SetStyleForProfanityForTextArea(txaEssentialQuestions, lblErrorMessageForEssential, false);
			}
		});
	}
	
	/**
	 * This method will display the Grades according to the subject
	 */
	@Override
	public void showCourseDetailsBasedOnSubjectd(final List<CourseSubjectDo> libraryCodeDo,final int selectedId,int resultscourseId) {
		pnlGradeContainer.clear();
		if(libraryCodeDo.size()>0){
			domainPaginationCourseId = resultscourseId;
			courseGradeWidget=new CourseGradeWidget(libraryCodeDo,selectedValues.get(selectedId),"domain") {
				@Override
				public void setSelectedGrade(final CourseSubjectDo courseObj, final long codeId,boolean isAdd) {
					for(CourseSubjectDo courseSubjectDo : libraryCodeDo) {
						if(courseSubjectDo.getSubdomainId()==codeId){
							pnlGradeDescContainer.getElement().setInnerHTML(courseSubjectDo.getDescription());
						}
					}
					if(isAdd){
						final LiPanelWithClose liPanelWithClose=new LiPanelWithClose(courseObj.getName());
						liPanelWithClose.getCloseButton().addClickHandler(new ClickHandler() {
							@Override
							public void onClick(ClickEvent event) {
								//This will remove the selected value when we are trying by close button
								for(Iterator<Map.Entry<Integer,ArrayList<String>>>it=selectedValues.entrySet().iterator();it.hasNext();){
								     Map.Entry<Integer, ArrayList<String>> entry = it.next();
								     if(entry.getValue().contains(courseObj.getName())){
								    	 entry.getValue().remove(courseObj.getName());
								     }
								 }
								removeGradeWidget(courseGradeWidget.getGradePanel(),codeId);
								liPanelWithClose.removeFromParent();
							}
						});
						selectedValues.get(selectedId).add(courseObj.getName());
						liPanelWithClose.setId(codeId);
						liPanelWithClose.setName(courseObj.getName());
						liPanelWithClose.setRelatedId(courseObj.getCourseId());
						liPanelWithClose.setRelatedSubjectId(courseObj.getSubjectId());
						ulSelectedItems.add(liPanelWithClose);
					}else{
						if(selectedValues.get(selectedId).contains(courseObj.getName())){
							selectedValues.get(selectedId).remove(courseObj.getName());
						}
						removeGradeWidget(ulSelectedItems,codeId);
					}
				}
			};
			pnlGradeContainer.add(courseGradeWidget);
			if(libraryCodeDo.size()>=20){
				domainPagination = 20;
				scrollCoursediv.addScrollHandler(new ScrollHandler() {
					@Override
					public void onScroll(ScrollEvent event) {						
						if(domainPagination<=80){
						 getUiHandlers().getPaginatedDomainsBasedOnCourseId(domainPaginationCourseId, selectedId, domainPagination);
						}
						domainPagination = domainPagination+20;
					}
				});
			}
		}
	}
	
	/**
	 * This method will display the Grades according to the subject
	 */
	@Override
	public void appendDoamins(final List<CourseSubjectDo> libraryCodeDo,final int selectedId) {
			courseGradeWidget1=new CourseGradeWidget(libraryCodeDo,selectedValues.get(selectedId),"domain") {
			@Override
			public void setSelectedGrade(final CourseSubjectDo courseObj, final long codeId,boolean isAdd) {
				for(CourseSubjectDo courseSubjectDo : libraryCodeDo) {
					if(courseSubjectDo.getSubdomainId()==codeId){
						pnlGradeDescContainer.getElement().setInnerHTML(courseSubjectDo.getDescription());
					}
				}
				if(isAdd){
					final LiPanelWithClose liPanelWithClose=new LiPanelWithClose(courseObj.getName());
					liPanelWithClose.getCloseButton().addClickHandler(new ClickHandler() {
						@Override
						public void onClick(ClickEvent event) {
							//This will remove the selected value when we are trying by close button
							for(Iterator<Map.Entry<Integer,ArrayList<String>>>it=selectedValues.entrySet().iterator();it.hasNext();){
							     Map.Entry<Integer, ArrayList<String>> entry = it.next();
							     if(entry.getValue().contains(courseObj.getName())){
							    	 entry.getValue().remove(courseObj.getName());
							     }
							 }
							removeGradeWidget(courseGradeWidget1.getGradePanel(),codeId);
							liPanelWithClose.removeFromParent();
						}
					});
					selectedValues.get(selectedId).add(courseObj.getName());
					liPanelWithClose.setId(codeId);
					liPanelWithClose.setName(courseObj.getName());
					liPanelWithClose.setRelatedId(courseObj.getCourseId());
					liPanelWithClose.setRelatedSubjectId(courseObj.getSubjectId());
					ulSelectedItems.add(liPanelWithClose);
				}else{
					if(selectedValues.get(selectedId).contains(courseObj.getName())){
						selectedValues.get(selectedId).remove(courseObj.getName());
					}
					removeGradeWidget(ulSelectedItems,codeId);
				}
			}
		};
		pnlGradeContainer.add(courseGradeWidget1);
	}

	/**
	 * This method will remove the widget based on the codeId in the UlPanel
	 * @param ulPanel
	 * @param codeId
	 */
	public void removeGradeWidget(UlPanel ulPanel,long codeId){
		Iterator<Widget> widgets=ulPanel.iterator();
		while (widgets.hasNext()) {
			Widget widget=widgets.next();
			if(widget instanceof LiPanelWithClose){
				LiPanelWithClose obj=(LiPanelWithClose) widget;
				if(obj.getId()==codeId){
					obj.removeFromParent();
				}
			}
			if(widget instanceof LiPanel){
				LiPanel obj=(LiPanel) widget;
				if(obj.getCodeId()==codeId){
					obj.removeStyleName("active");
				}
			}
		}
	}

	@Override
	public void setCourseList(List<CourseSubjectDo> libraryCode,int selectedId) {
		ulMainGradePanel.clear();
		if (libraryCode.size()>0) {
			for (CourseSubjectDo libraryCodeDo : libraryCode) {
				String titleText=libraryCodeDo.getName().trim();
				if(!selectedValues.containsKey(libraryCodeDo.getCourseId())){
					selectedValues.put(libraryCodeDo.getCourseId(), new ArrayList<String>());
				}
				LiPanel liPanel=new LiPanel();
				Anchor title=new Anchor(titleText);
				title.addClickHandler(new ClickOnSubject(titleText,liPanel,libraryCodeDo.getCourseId()));
				liPanel.add(title);
				if(selectedId==libraryCodeDo.getCourseId()){
					liPanel.addStyleName(ACTIVE);
					tempLiPanel=liPanel;
					getUiHandlers().getDomainsBasedOnCourseId(libraryCodeDo.getCourseId(), libraryCodeDo.getCourseId());
				}
				ulMainGradePanel.add(liPanel);
			}
		}
	}
	/**
	 * This inner class is used to get selected subjects grades
	 */
	class ClickOnSubject implements ClickHandler{
		String selectedText;
		LiPanel liPanel;
		int courseId;
		ClickOnSubject(String selectedText,LiPanel liPanel,int courseId){
			this.selectedText=selectedText;
			this.liPanel=liPanel;
			this.courseId=courseId;
		}
		@Override
		public void onClick(ClickEvent event) {
			if(tempLiPanel!=null){
				tempLiPanel.removeStyleName(ACTIVE);
				tempLiPanel=liPanel;
			}else{
				tempLiPanel=liPanel;
			}
			if(liPanel.getStyleName().contains(ACTIVE)){
				if(selectedValues.get(courseId).size()>0){
					getUiHandlers().getDomainsBasedOnCourseId(courseId, courseId);
				}else{
					liPanel.removeStyleName(ACTIVE);
				}
			}else{
				liPanel.addStyleName(ACTIVE);
				getUiHandlers().getDomainsBasedOnCourseId(courseId, courseId);
			}
		}
	}
	
	@UiHandler("saveUnitBtn")
	public void clickOnSaveUnitBtn(ClickEvent saveCourseEvent){
		TreeItem currentShelfTreeWidget = getUiHandlers().getSelectedWidget();
		String courseId=AppClientFactory.getPlaceManager().getRequestParameter(O1_LEVEL,null);
		saveUnitBtn.addStyleName("disabled");
		saveUnitBtn.setEnabled(false);
		CreateDo createOrUpDate=new CreateDo();
		createOrUpDate.setTitle(unitTitle.getText());
		createOrUpDate.setIdeas(txaBigIdeas.getText());
		createOrUpDate.setQuestions(txaEssentialQuestions.getText());
		createOrUpDate.setSubdomainIds(getSelectedSubDomainIds());
		if(validateInputs()){
			lblErrorMessage.setVisible(false);
			unitTitle.removeStyleName("textAreaErrorMessage");
			getUiHandlers().checkProfanity(unitTitle.getText().trim(),false,0,courseId,createOrUpDate,currentShelfTreeWidget);
		}else{
			Window.scrollTo(unitTitle.getAbsoluteLeft(), unitTitle.getAbsoluteTop()-(unitTitle.getOffsetHeight()*3));
			lblErrorMessage.setVisible(true);
			unitTitle.setStyleName("textAreaErrorMessage");
			unitTitle.addStyleName("form-control");
			resetBtns();
		}
	}
	
	@UiHandler("nextCreateLessonBtn")
	public void clickOnNextLessonBtn(ClickEvent saveCourseEvent){
		TreeItem currentShelfTreeWidget = getUiHandlers().getSelectedWidget();
		String courseId=AppClientFactory.getPlaceManager().getRequestParameter(O1_LEVEL,null);
		nextCreateLessonBtn.addStyleName("disabled");
		nextCreateLessonBtn.setEnabled(false);
		CreateDo createOrUpDate=new CreateDo();
		createOrUpDate.setTitle(unitTitle.getText());
		createOrUpDate.setIdeas(txaBigIdeas.getText());
		createOrUpDate.setQuestions(txaEssentialQuestions.getText());
		createOrUpDate.setSubdomainIds(getSelectedSubDomainIds());
		if(validateInputs()){
			lblErrorMessage.setVisible(false);
			unitTitle.removeStyleName("textAreaErrorMessage");
			getUiHandlers().checkProfanity(unitTitle.getText().trim(),true,0,courseId,createOrUpDate,currentShelfTreeWidget);
		}else{
			Window.scrollTo(unitTitle.getAbsoluteLeft(), unitTitle.getAbsoluteTop()-(unitTitle.getOffsetHeight()*3));
			lblErrorMessage.setVisible(true);
			unitTitle.setStyleName("textAreaErrorMessage");
			unitTitle.addStyleName("form-control");
			resetBtns();
		}
	}
	/**
	 * This method is used to call create and update API
	 * @param index
	 * @param isCreate
	 */
	@Override
	public void callCreateAndUpdate(boolean isCreate,boolean result,int index,String courseId,CreateDo createOrUpDate, TreeItem currentShelfTreeWidget){
		if(result && index==0){
			SetStyleForProfanity.SetStyleForProfanityForTextBox(unitTitle, lblErrorMessage, result);
			isCreateLessonClicked=true;
		}else if(result && index==1){
			SetStyleForProfanity.SetStyleForProfanityForTextArea(txaBigIdeas, lblErrorMessageForBig, result);
			isCreateLessonClicked=true;
		}else if(result && index==2){
			SetStyleForProfanity.SetStyleForProfanityForTextArea(txaEssentialQuestions, lblErrorMessageForEssential, result);
			isCreateLessonClicked=true;
		}else{
			if(index==0){
				getUiHandlers().checkProfanity(createOrUpDate.getIdeas().trim(),isCreate,1,courseId,createOrUpDate,currentShelfTreeWidget);
			}else if(index==1){
				getUiHandlers().checkProfanity(createOrUpDate.getQuestions().trim(),isCreate,2,courseId,createOrUpDate,currentShelfTreeWidget);
			}else if(index==2){
				if(courseObj!=null && courseObj.getGooruOid()!=null){
					getUiHandlers().updateUnitDetails(createOrUpDate,courseObj.getGooruOid(),isCreate,courseObj,currentShelfTreeWidget);
				}else{
					getUiHandlers().createAndSaveUnitDetails(createOrUpDate,isCreate,courseObj,courseId,currentShelfTreeWidget);
				}
			}
		}
	}
	/**
	 * This method is used to get the selected course id's
	 * @return
	 */
	public List<Integer> getSelectedSubDomainIds(){
		List<Integer> taxonomyCourseIds=new ArrayList<Integer>();
		Iterator<Widget> widgets=ulSelectedItems.iterator();
		List<CourseSubjectDo> courseList=new ArrayList<CourseSubjectDo>();
		while (widgets.hasNext()) {
			Widget widget=widgets.next();
			if(widget instanceof LiPanelWithClose){
				LiPanelWithClose obj=(LiPanelWithClose) widget;
				Integer intVal = (int)obj.getId();
				taxonomyCourseIds.add(intVal);
				CourseSubjectDo courseObj=new CourseSubjectDo();
				courseObj.setId((int)obj.getId());
				courseObj.setName(obj.getName());
				courseObj.setSubjectId(obj.getRelatedSubjectId());
				courseObj.setCourseId(obj.getRelatedId());
				courseList.add(courseObj);
			}
		}
		if(courseObj!=null){
			courseObj.setSubdomain(courseList);
		}
		return taxonomyCourseIds;
	}

	@Override
	public void setCouseData(FolderDo courseObj) {
		this.courseObj=courseObj;
		unitTitle.setText(courseObj==null?"":!courseObj.getTitle().equalsIgnoreCase(i18n.GL3364())?courseObj.getTitle():"");
		txaBigIdeas.setText(courseObj!=null?(courseObj.getIdeas()!=null?courseObj.getIdeas():""):"");
		txaEssentialQuestions.setText(courseObj!=null?(courseObj.getQuestions()!=null?courseObj.getQuestions():""):"");
		
		ulSelectedItems.clear();
		selectedValues.clear();
		//This will push the previous selected values to map
		if(courseObj!=null && courseObj.getSubdomain()!=null){
			//To set default selection if the user is already selected any subject
			for (final CourseSubjectDo courseSubjectDo : courseObj.getSubdomain()) {
				if(selectedValues.containsKey(courseSubjectDo.getCourseId())){
					selectedValues.get(courseSubjectDo.getCourseId()).add(courseSubjectDo.getName());
				}else{
					selectedValues.put(courseSubjectDo.getCourseId(), new ArrayList<String>());
					selectedValues.get(courseSubjectDo.getCourseId()).add(courseSubjectDo.getName());
				}
				final LiPanelWithClose liPanelWithClose=new LiPanelWithClose(courseSubjectDo.getName());
				liPanelWithClose.getCloseButton().addClickHandler(new ClickHandler() {
					@Override 
					public void onClick(ClickEvent event) {
						for(Iterator<Map.Entry<Integer,ArrayList<String>>>it=selectedValues.entrySet().iterator();it.hasNext();){
						     Map.Entry<Integer, ArrayList<String>> entry = it.next();
						     if(entry.getValue().contains(courseSubjectDo.getName())){
						    	 entry.getValue().remove(courseSubjectDo.getName());
						     }
						 }
						removeGradeWidget(courseGradeWidget.getGradePanel(),courseSubjectDo.getId());
						liPanelWithClose.removeFromParent();
					}
				});
				liPanelWithClose.setId(courseSubjectDo.getId());
				liPanelWithClose.setName(courseSubjectDo.getName());
				liPanelWithClose.setRelatedId(courseSubjectDo.getCourseId());
				liPanelWithClose.setRelatedSubjectId(courseSubjectDo.getSubjectId());
				ulSelectedItems.add(liPanelWithClose);
			}
		}
		getUiHandlers().callCourseInfoTaxonomy();
	}
	
	private class OnClickTaxonomy implements ClickHandler{
		@Override
		public void onClick(ClickEvent event) {
			unitLiPanelWithCloseArray.clear();
			for(int i=0;i<ulSelectedItems.getWidgetCount();i++){
				unitLiPanelWithCloseArray.add((LiPanelWithClose) ulSelectedItems.getWidget(i));
			}
			getUiHandlers().invokeTaxonomyPopup(UNIT,unitLiPanelWithCloseArray);
		}
	}
	
	/**
	 * Adds the selected domains from the taxonomy popup into unit info view.
	 */
	@Override
	public void addTaxonomyData(List<LiPanelWithClose> liPanelWithCloseArray,List<LiPanelWithClose> removedLiPanelWithCloseArray) {
		for(int i=0;i<liPanelWithCloseArray.size();i++){
			if(isWidgetExists(liPanelWithCloseArray.get(i).getId())){
				ulSelectedItems.add(liPanelWithCloseArray.get(i));
			}
			if(i<removedLiPanelWithCloseArray.size()){
				removeFromUlSelectedItemsContainer(removedLiPanelWithCloseArray.get(i).getId());
			}
		}
	}
	
	/**
	 * Checks the selected widgets in info view got from taxonomy popup.
	 * @param id
	 * @return
	 */
	private boolean isWidgetExists(long id) {
		boolean flag = true;
		Iterator<Widget> widgets = ulSelectedItems.iterator();
		while(widgets.hasNext()){
			Widget widget = widgets.next();
			if(widget instanceof LiPanelWithClose && ((LiPanelWithClose) widget).getId() == id){
				flag = false;
			}
		}
		return flag; 
	}

	/**
	 * Removes the widget, which has been removed from taxonomy popup from info view 
	 * @param removeWidgetId
	 */
	private void removeFromUlSelectedItemsContainer(long removeWidgetId) {
		Iterator<Widget> widgets = ulSelectedItems.iterator();
		while(widgets.hasNext()){
			Widget widget = widgets.next();
			if(widget instanceof LiPanelWithClose && ((LiPanelWithClose) widget).getId() == removeWidgetId){
				widget.removeFromParent();
			}
		}
	}

	public boolean validateInputs(){
		String collectionTitleStr=unitTitle.getText().trim();
		if(collectionTitleStr.equalsIgnoreCase("")||collectionTitleStr.equalsIgnoreCase(i18n.GL3364())){
			return false;
		}else{
			return true;
		}
	}
	@UiHandler("unitTitle")
	public void collectionTitleKeyUphandler(KeyUpEvent event){
		unitTitle.removeStyleName("textAreaErrorMessage");
		lblErrorMessage.setVisible(false);
	}
	@Override
	public void resetBtns() {
		nextCreateLessonBtn.removeStyleName("disabled");
		nextCreateLessonBtn.setEnabled(true);
		saveUnitBtn.removeStyleName("disabled");
		saveUnitBtn.setEnabled(true);
	}
}
