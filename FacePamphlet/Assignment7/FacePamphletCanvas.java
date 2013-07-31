/*
 * File: FacePamphletCanvas.java
 * -----------------------------
 * This class represents the canvas on which the profiles in the social
 * network are displayed.  NOTE: This class does NOT need to update the
 * display when the window is resized.
 */


import acm.graphics.*;

import java.awt.*;
import java.util.*;

import javax.swing.*;

import sun.awt.GlobalCursorManager;

public class FacePamphletCanvas extends GCanvas 
					implements FacePamphletConstants {
	
	/** 
	 * Constructor
	 * This method takes care of any initialization needed for 
	 * the display
	 */
	public FacePamphletCanvas() {
	

	}

	
	/** 
	 * This method displays a message string near the bottom of the 
	 * canvas.  Every time this method is called, the previously 
	 * displayed message (if any) is replaced by the new message text 
	 * passed in.
	 */
	public void showMessage(String msg) {
		GLabel msgGLabel=new GLabel(msg);
		msgGLabel.setFont(MESSAGE_FONT);
		add(msgGLabel,(getWidth()-msgGLabel.getWidth())/2,getHeight()-BOTTOM_MESSAGE_MARGIN );
	}
	
	
	/** 
	 * This method displays the given profile on the canvas.  The 
	 * canvas is first cleared of all existing items (including 
	 * messages displayed near the bottom of the screen) and then the 
	 * given profile is displayed.  The profile display includes the 
	 * name of the user from the profile, the corresponding image 
	 * (or an indication that an image does not exist), the status of
	 * the user, and a list of the user's friends in the social network.
	 */
	public void displayProfile(FacePamphletProfile profile) {
		removeAll();
		displayName(profile.getName());
		displayImg(profile.getImage());
		displayStatus(profile.getStatus(),profile.getName());
		displayFriends(profile.getFriends());
	}
	
	private void displayFriends(Iterator<String> itr){
		GLabel headGLabel=new GLabel("Friends:");
		headGLabel.setFont( PROFILE_FRIEND_LABEL_FONT);
		double height=TOP_MARGIN+IMAGE_MARGIN+headGLabel.getHeight()-headGLabel.getAscent();
		add(headGLabel,getWidth()/2,height);
		while(itr.hasNext()){
			String friend=itr.next();
			GLabel friendGLabel=new GLabel(friend);
			friendGLabel.setFont(PROFILE_FRIEND_FONT);
			add(friendGLabel,getWidth()/2,height+FRIEND_MARGIN+friendGLabel.getHeight());
			height=height+FRIEND_MARGIN+friendGLabel.getHeight();
		}
	}
	
	private void displayStatus(String status,String name){
		String string;
		if(status.equals("")){
			string="There is no current status";
		}else{
			string=name+" is "+status;
		}
		GLabel statusGLabel=new GLabel(string);
		statusGLabel.setFont(PROFILE_STATUS_FONT);
		add(statusGLabel,LEFT_MARGIN,TOP_MARGIN+IMAGE_MARGIN+IMAGE_HEIGHT+STATUS_MARGIN);
	}
	
	private void displayImg(GImage img){
		if(img!=null){
			img.setSize(IMAGE_WIDTH,IMAGE_HEIGHT);
			add(img,LEFT_MARGIN,TOP_MARGIN+IMAGE_MARGIN);
		}else{
			GRect rect=new GRect(IMAGE_WIDTH,IMAGE_HEIGHT);
			GCompound imgCompound=new GCompound();
			GLabel noImg=new GLabel("No Image");
			noImg.setFont( PROFILE_IMAGE_FONT);
			imgCompound.add(noImg,(IMAGE_WIDTH-noImg.getWidth())/2,(IMAGE_HEIGHT-noImg.getDescent())/2);
			imgCompound.add(rect);
			add(imgCompound,LEFT_MARGIN,TOP_MARGIN+IMAGE_MARGIN);
		}
	}
	
	private void displayName(String str){
		GLabel name=new GLabel(str);
		name.setColor(Color.BLUE);
		name.setFont(PROFILE_NAME_FONT);
		add(name,LEFT_MARGIN,TOP_MARGIN+name.getDescent());
	}
}
