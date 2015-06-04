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
package org.ednovo.gooru.client.mvp.play.resource.question;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import org.ednovo.gooru.client.gin.AppClientFactory;
import org.ednovo.gooru.client.mvp.dnd.Draggable;
import org.ednovo.gooru.client.mvp.play.resource.question.event.ResetDragDropEvent;
import org.ednovo.gooru.client.mvp.play.resource.question.event.ResetDragDropHandler;
import org.ednovo.gooru.client.uc.PlayerBundle;
import org.ednovo.gooru.shared.i18n.MessageProperties;
import org.ednovo.gooru.shared.model.content.CollectionItemDo;
import org.ednovo.gooru.shared.model.content.QuestionAnswerDo;
import org.ednovo.gooru.shared.util.AttemptedAnswersDo;
import org.ednovo.gooru.shared.util.RandomIterator;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiConstructor;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public abstract  class HotTextAnswersQuestionView extends Composite{

	@UiField Button checkAnswer;
	@UiField FlowPanel optionsContainerFpnl;
	HTAnswerDragPanelVc optionsContainer;
	@UiField Label messageBodyText;
	@UiField HTMLPanel answerText;

	private CollectionItemDo collectionItemDo;

	private AttemptedAnswersDo attemptedAnswerDo=null;
	
	private boolean isCheckButtonEnabled=true;
	
	String[] correctAnsSequence;
	String[] attemptAnsSequence;
	
	private static String SPACE=" ";
	private static String STYLE_HIGHLIGHT="htHiglightText";
	private static String STYLE_CORRECT="correct";
	private static String STYLE_INCORRECT="inCorrect";
	private static String STYLE_INACTIVE_BUTTON="htPlayerSubmitInActiveButton";
	private static String STYLE_DND_CORRECT="dragDropAnsCorrect";
	private static String STYLE_DND_INCORRECT="dragDropAnsInCorrect";
	private static String DOT=".";

	private static HotTextAnswersQuestionViewUiBinder uiBinder = GWT.create(HotTextAnswersQuestionViewUiBinder.class);

	interface HotTextAnswersQuestionViewUiBinder extends UiBinder<Widget, HotTextAnswersQuestionView> {

	}

	private MessageProperties i18n = GWT.create(MessageProperties.class);

	public HotTextAnswersQuestionView(){
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiConstructor
	public HotTextAnswersQuestionView(CollectionItemDo collectionItemDo,AttemptedAnswersDo attemptedAnswerDo){
		initWidget(uiBinder.createAndBindUi(this));
		AppClientFactory.getEventBus().addHandler(ResetDragDropEvent.TYPE,resetReorderData);
		PlayerBundle.INSTANCE.getPlayerStyle().ensureInjected();
		this.collectionItemDo=collectionItemDo;
		this.attemptedAnswerDo=attemptedAnswerDo;
		setQuestionTypeCaption();

		answerText.getElement().setInnerHTML(i18n.GL0665());
		checkAnswer.setText(i18n.GL0666());
	}

	private void setQuestionTypeCaption(){
		messageBodyText.setText(i18n.GL1457()+i18n.GL_SPL_FULLSTOP());
		optionsContainerFpnl.clear();
		if(collectionItemDo!=null && collectionItemDo.getResource()!=null && collectionItemDo.getResource().getAnswers()!=null && collectionItemDo.getResource().getType()==9){
			messageBodyText.setText(i18n.GL3234()+i18n.GL_SPL_FULLSTOP());
			optionsContainerFpnl.addStyleName("drapDropContainer");
			optionsContainer=new HTAnswerDragPanelVc();
			optionsContainerFpnl.add(optionsContainer);
			Label label = new Label("");
			label.setStyleName("dragDropSpacer");
			optionsContainer.superAdd(label);
			Label toplabel = new Label("");
			toplabel.setStyleName("dragDropSpacer");
			optionsContainer.add(toplabel);
		}else{
			optionsContainerFpnl.removeStyleName("drapDropContainer");
		}
		renderQuestionAnswerOptions();
	}

	private void renderQuestionAnswerOptions(){

		if(collectionItemDo!=null && collectionItemDo.getResource()!=null && collectionItemDo.getResource().getAnswers()!=null){
			TreeSet<QuestionAnswerDo> answersSet=collectionItemDo.getResource().getAnswers();
			List<QuestionAnswerDo> answerListSet=new ArrayList<QuestionAnswerDo>(answersSet);

			if(collectionItemDo.getResource().getType()==8){

				Iterator<QuestionAnswerDo> answersList=answersSet.iterator();
				while (answersList.hasNext()) {
					QuestionAnswerDo questionAnswerDo=answersList.next();
					String text=removeHtmlTags(questionAnswerDo.getAnswerText());
					String[] temp;
					if(collectionItemDo.getResource().getAttributes().getHlType().equalsIgnoreCase(i18n.GL3219())){
						temp = text.split(" ");
					
					for(int k=0;k<temp.length;k++){
						
						final InlineLabel lbl=new InlineLabel(temp[k]+SPACE);
						
						if(lbl.getText().startsWith("${") && lbl.getText().endsWith("}$"+SPACE) ){
							String lblText=lbl.getText().replaceAll("[${}]", "");
							lbl.setText(lblText);
							lbl.getElement().setId(STYLE_CORRECT);
						}else{
							lbl.getElement().setId(STYLE_INCORRECT);
						}
						
						lbl.addStyleName("htPlayerAns");
						lbl.addClickHandler(new ClickHandler() {
							@Override
							public void onClick(ClickEvent event) {
								clearAnswers();

								if(lbl.getStyleName().contains(STYLE_HIGHLIGHT)){
									lbl.removeStyleName(STYLE_HIGHLIGHT);
								}else{

									lbl.addStyleName(STYLE_HIGHLIGHT);
								}
								enableCheckAnswerButton();
							}
						});
						optionsContainerFpnl.add(lbl);
					}
					}else{
						temp = text.split("\\.");
						for(int k=0;k<temp.length;k++){
							if(temp[k].trim().length()>0){
								System.out.println("temp[k]--"+temp[k]);
							final InlineLabel lbl=new InlineLabel(temp[k]+DOT);
							
							if(lbl.getText().startsWith("${") ||  lbl.getText().startsWith(" ${") ){
								String lblText=lbl.getText().replaceAll("[${}]", "");
								lbl.setText(lblText);
								lbl.getElement().setId(STYLE_CORRECT);
							}else{
								String lblText=lbl.getText().replaceAll("[${}]", "");
								lbl.setText(lblText);
								lbl.getElement().setId(STYLE_INCORRECT);
							}
							lbl.addStyleName("htPlayerAns");
							lbl.addClickHandler(new ClickHandler() {
								@Override
								public void onClick(ClickEvent event) {
									clearAnswers();

									if(lbl.getStyleName().contains(STYLE_HIGHLIGHT)){
										lbl.removeStyleName(STYLE_HIGHLIGHT);
									}else{

										lbl.addStyleName(STYLE_HIGHLIGHT);
									}
									enableCheckAnswerButton();
								}
							});
							optionsContainerFpnl.add(lbl);
								
						}
					}
					}
				}
			}else{
				correctAnsSequence=RandomIterator.getRandomStringArray(answerListSet.size()) ;
				attemptAnsSequence=new String[answerListSet.size()];
				List rList= RandomIterator.getRandomList(answerListSet.size());
				for(int i=0;i<answerListSet.size();i++){
					int randomSeq=(Integer) rList.get(i);
					QuestionAnswerDo questionAnswerDo=answerListSet.get(randomSeq);
					HTAnswerChoiceOptionView htAnswerOptionView=new HTAnswerChoiceOptionView(questionAnswerDo.getAnswerText(),("(" + (char) (65 + i) + ") "));
					
					htAnswerOptionView.addDomHandler(new ClickHandler() {
						
						@Override
						public void onClick(ClickEvent event) {
							clearReorderAnswers();
							enableCheckAnswerButton();
						}
					}, ClickEvent.getType());
					
					/*htAnswerOptionView.setAnswerId(questionAnswerDo.getAnswerId());
					htAnswerOptionView.setAnswerCorrect(questionAnswerDo.isIsCorrect());*/
					htAnswerOptionView.getElement().setId(String.valueOf(randomSeq));
					int k=i+1;
					optionsContainer.addDraggable(htAnswerOptionView,k,randomSeq);
					enableCheckAnswerButton();
					//showPreviousResult(questionAnswerDo.getAnswerId(),htAnswerOptionView);
				}
			}
		}


	}

	public void showPreviousResult(int answerId,HTAnswerChoiceOptionView htAnswerOptionView){
		if(attemptedAnswerDo!=null){
			Map<Integer,Boolean> answerOptionCount=attemptedAnswerDo.getAnswerOptionResult();
			if(answerOptionCount.containsKey(answerId) && answerOptionCount.get(answerId)!=null){
				if(answerOptionCount.get(answerId)){
					if(htAnswerOptionView.isAnswerCorrect()){
						htAnswerOptionView.getElement().getStyle().setBackgroundColor("#ddd");
					}else{
						htAnswerOptionView.getElement().getStyle().setBackgroundColor("red");
					}
				}else{
					if(!htAnswerOptionView.isAnswerCorrect()){
						htAnswerOptionView.getElement().getStyle().setBackgroundColor("#ddd");
					}else{
						htAnswerOptionView.getElement().getStyle().setBackgroundColor("red");
					}
				}
			}
		}
	}


	@UiHandler("checkAnswer")
	public void checkButtonClickEvent(ClickEvent event){
		if(isCheckButtonEnabled){
			showCorrectResult();
			isCheckButtonEnabled=false;
			checkAnswer.removeStyleName("primary");
			checkAnswer.addStyleName(STYLE_INACTIVE_BUTTON);
		}
		
	}

	private void enableCheckAnswerButton(){
		boolean isOptionSelected=false;

		if(collectionItemDo.getResource().getType()==8){

			for(int i=0;i<optionsContainerFpnl.getWidgetCount();i++){

				InlineLabel widget=(InlineLabel) optionsContainerFpnl.getWidget(i);
				if(widget.getStyleName().contains(STYLE_HIGHLIGHT)){
					isOptionSelected=true;
				}
			}
		}else{
			isOptionSelected=true;
		}


		if(isOptionSelected){
			isCheckButtonEnabled=true;
			checkAnswer.removeStyleName(STYLE_INACTIVE_BUTTON);
			checkAnswer.addStyleName("primary");
		}else{
			isCheckButtonEnabled=false;
			checkAnswer.removeStyleName("primary");
			checkAnswer.addStyleName(STYLE_INACTIVE_BUTTON);
		}
	}

	private void showCorrectResult(){
		
		if(collectionItemDo.getResource().getType()==9){
			boolean HTDragChoiceStatus=true;
		int j=0;
		List<Integer> answerIds=new ArrayList<Integer>();
		List<String> userAttemptedValueList=new ArrayList<String>();
		for(int i=0;i<optionsContainer.getWidgetCount();i++){
			Widget widget=optionsContainer.getWidget(i);
			
			if(widget instanceof Draggable){
				Draggable draggable=(Draggable)widget;
				HTAnswerChoiceOptionView htAnswerOption=(HTAnswerChoiceOptionView) draggable.getWidget();
				userAttemptedValueList.add("["+htAnswerOption.getAnswerText()+"]");
			}
			
			Element el=(Element) widget.getElement().getLastChild();
			
			if(el.getId()!=null && !el.getId().equalsIgnoreCase("")){
				if(el.getId().equalsIgnoreCase(correctAnsSequence[j])){
					el.addClassName(STYLE_DND_CORRECT);
				}else{
					el.addClassName(STYLE_DND_INCORRECT);
					HTDragChoiceStatus=false;
				}
				j++;
			}
		}
		String attemptStatus=HTDragChoiceStatus==true?"correct":"wrong";
		createSesstionItemAttemptForHTDragDrop(answerIds,userAttemptedValueList,attemptStatus);
		
		}else{
			for(int i=0;i<optionsContainerFpnl.getWidgetCount();i++){

				InlineLabel lbl=(InlineLabel) optionsContainerFpnl.getWidget(i);

				if(lbl.getStyleName().contains(STYLE_HIGHLIGHT)){

					if(lbl.getElement().getId().equalsIgnoreCase(STYLE_CORRECT)){
						lbl.addStyleName(STYLE_CORRECT);
					}else {
						lbl.addStyleName(STYLE_INCORRECT);
					}
				}

			}
		}

	}
	
	private void clearAnswers(){
		for(int i=0;i<optionsContainerFpnl.getWidgetCount();i++){

			InlineLabel lbl=(InlineLabel) optionsContainerFpnl.getWidget(i);

			if(lbl.getStyleName().contains(STYLE_HIGHLIGHT)){

				if(lbl.getElement().getId().equalsIgnoreCase(STYLE_CORRECT)){
					lbl.removeStyleName(STYLE_CORRECT);
				}else {
					lbl.removeStyleName(STYLE_INCORRECT);
				}
			}

		}
		
	}
	
	private void clearReorderAnswers(){
		
		for(int i=0;i<optionsContainer.getWidgetCount();i++){
			Widget widget=optionsContainer.getWidget(i);
			
			Element el=(Element) widget.getElement().getLastChild();
			
			if(el.getId()!=null && !el.getId().equalsIgnoreCase("")){
					el.removeClassName(STYLE_DND_CORRECT);
					el.removeClassName(STYLE_DND_INCORRECT);
			}
			
		}
		
	}
	
	/**
	 * This method is used to remove HTMLTags from the String
	 * @param text
	 * @return
	 */
	private String removeHtmlTags(String text){
		/**
		 * Commented the following line to fix issue with displaying math symbols. 
		 */
		text=text.replaceAll("</p>", " ").replaceAll("<p>", "").replaceAll("<br data-mce-bogus=\"1\">", "").replaceAll("<br>", "").replaceAll("</br>", "");
		return text;
	}
	
	ResetDragDropHandler resetReorderData=new ResetDragDropHandler() {
		
		@Override
		public void resetReorder(int widgetIndex) {
			
			if(checkAnswer.getStyleName().contains(STYLE_INACTIVE_BUTTON)){
				clearReorderAnswers();
				enableCheckAnswerButton();
			}
		}
	};
	
	public abstract void createSesstionItemAttemptForHTDragDrop(List<Integer> answerIds,List<String> userAttemptedAnswers,String attemptStatus);

}
