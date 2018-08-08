package com.dlh.zambas.product.validate.comments;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.dlh.zambas.product.model.Comments;

public class ValidateComments {
	
	private static Set<String> cussWords = new HashSet<String>();
	private boolean cussWordUsed = false;
	
	static{
		cussWords.add("objectionable");
		cussWords.add("robbery");
		cussWords.add("loot");
		cussWords.add("any slang");
	}

	public Map<String, String> validateComments(Comments comments){
		Map<String, String> commentsMap = new HashMap<String, String>();
		String commentedSentence = comments.getComments();
		String[] words = commentedSentence.split("\\s+");
		for (int index = 0; index < words.length; index++) {
		   if(cussWords.contains(words[index])){
			   cussWordUsed = true;
			   break;
		   }
		}
		if(cussWordUsed){
			commentsMap.put(comments.getId(), "You are not allowed to post words like " + cussWords);
		}else{
			commentsMap.put(comments.getId(), "We appreciate your time to give feedback");
		}
		return commentsMap;
		
	}

	public boolean isCussWordUsed() {
		return cussWordUsed;
	}

	public void setCussWordUsed(boolean cussWordUsed) {
		this.cussWordUsed = cussWordUsed;
	}

	/*public Map<String, String> validateComments(Comments comments, HashSet<ObjectionableContent> newHashSet) {
		// TODO Auto-generated method stub
		return null;
	}
	*/
	
}
