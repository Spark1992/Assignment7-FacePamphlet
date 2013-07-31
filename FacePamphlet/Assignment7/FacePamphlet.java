/* 
 * File: FacePamphlet.java
 * -----------------------
 * When it is finished, this program will implement a basic social network
 * management system.
 */

import acm.program.*;
import acm.graphics.*;
import acm.util.*;

import java.awt.event.*;
import java.util.Iterator;

import javax.swing.*;


public class FacePamphlet extends Program 
					implements FacePamphletConstants {

	/**
	 * This method has the responsibility for initializing the 
	 * interactors in the application, and taking care of any other 
	 * initialization that needs to be performed.
	 */
	public void init() {
		//set window size
		setSize(APPLICATION_WIDTH,APPLICATION_HEIGHT);
		initInteractors();
		addActionListeners();
		database=new FacePamphletDatabase();
		canvas=new  FacePamphletCanvas();
		add(canvas);
    }
    
  
    /**
     * This class is responsible for detecting when the buttons are
     * clicked or interactors are used, so you will have to add code
     * to respond to these actions.
     */
    public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand()=="Add"){
			
			//add profile pair
			if(database.containsProfile(nameTextField.getText())){
				//replace profile
				updateCurrentProfile(nameTextField.getText());
				canvas.displayProfile(currentProfile);
				canvas.showMessage("Profile already exist.");
				
			}else{
				//add new profile
				database.addProfile(new FacePamphletProfile(nameTextField.getText()));
				updateCurrentProfile(nameTextField.getText());
				canvas.displayProfile(currentProfile);
				canvas.showMessage("Profile added: "+nameTextField.getText());
			}
		}

		//delete button
		if(e.getActionCommand()=="Delete"){
			if(database.containsProfile(nameTextField.getText())){
				
				//update other profile's friend list
				FacePamphletProfile temp= database.getProfile(nameTextField.getText());
				Iterator<String> itr=temp.getFriends();
				while(itr.hasNext()){
					String string=itr.next();
					database.getProfile(string).removeFriend(nameTextField.getText());
				}
				
				//delete profile
				database.deleteProfile(nameTextField.getText());
				
				canvas.removeAll();
				canvas.showMessage(nameTextField.getText()+" deleted.");
				updateCurrentProfile("false");
			}else {
				canvas.removeAll();
				canvas.showMessage("profile doesn't exist.");
				updateCurrentProfile("false");
			}
		}
		
		//Look up button
		if(e.getActionCommand()=="Look up"){
			if(database.containsProfile(nameTextField.getText())){
	
			//look up profile
				updateCurrentProfile(nameTextField.getText());
				canvas.displayProfile(database.getProfile(nameTextField.getText()));
				canvas.showMessage("This is profile of "+nameTextField.getText());
				
	
		}else {
				canvas.removeAll();
				canvas.showMessage("profile doesn't exist.");
				updateCurrentProfile("false");
			}
		}
		
		//change status field and button
		if(e.getSource()==changeStatusField||e.getActionCommand()=="Change Status"){
			if(currentProfile==null){
				canvas.removeAll();
				canvas.showMessage("There is no current profile.");
			}else{
				if(changeStatusField.getText().equals("")){
					canvas.displayProfile(currentProfile);
					canvas.showMessage("Please enter your status.");
				}else{
					currentProfile.setStatus(changeStatusField.getText());
					canvas.displayProfile(currentProfile);
					canvas.showMessage("The status already changed,the current status is "+currentProfile.getStatus());
				}
			}
		}
		
		//change picture field and button
		if(e.getSource()==changePictureField||e.getActionCommand()=="Change Picture"){
			GImage img=null;
			if(currentProfile!=null){
				if(!changePictureField.getText().equals("")){
					try {
						img=new GImage(changePictureField.getText());
				
					} catch (Exception e2) {
						canvas.showMessage("Error exist:");
					}
				}
				
				if(img!=null){
					currentProfile.setImage(img);
					canvas.displayProfile(currentProfile);
					canvas.showMessage("The picture is added");
				}else{
					canvas.displayProfile(currentProfile);
					canvas.showMessage("The picture is not found");
				}
			}else{
				canvas.displayProfile(currentProfile);
				canvas.showMessage("There is no current profile.");
			}
		}
		
		// add friend pair
		if(e.getSource()==addFriendField||e.getActionCommand()=="Add Friend"){
			if(currentProfile!=null){
				//check whether profile exist
				if(database.containsProfile(addFriendField.getText())){
					//check whether friendship already exist
					if(!currentProfile.addFriend(addFriendField.getText())){
						canvas.displayProfile(currentProfile);
						canvas.showMessage("Friend already exist");
					}else{
						database.getProfile(addFriendField.getText()).addFriend(currentProfile.getName());
						canvas.displayProfile(currentProfile);
						canvas.showMessage("Friend added.");
					}
				}else{
					//if profile doesn't exist.
					canvas.displayProfile(currentProfile);
					canvas.showMessage("Profile doesn't exist.");
				}
			}else{
				canvas.removeAll();
				canvas.showMessage("There is no current profile.");
			}
		}
		

	}
    
    private void updateCurrentProfile(String name){
    	if(name.equals("false")){
    		currentProfile=null;
    	}else{
    		currentProfile=database.getProfile(name);	
    	}
    	
    }
    
    private void initInteractors(){
    	add(new JLabel("name"),NORTH);
		
		//add name text field
		nameTextField=new JTextField(TEXT_FIELD_SIZE);
		add(nameTextField,NORTH);
		nameTextField.addActionListener(this);
		
		add(new JButton("Add"),NORTH);
		add(new JButton("Delete"),NORTH);
		add(new JButton("Look up"),NORTH);
		
		//add change status text field 
		changeStatusField=new JTextField(TEXT_FIELD_SIZE);
		add(changeStatusField,WEST);
		changeStatusField.addActionListener(this);
		
		//add change picture button
		add(new JButton("Change Status"),WEST);
		
		//add padding
		add(new JLabel( EMPTY_LABEL_TEXT),WEST);
		
		//add change picture text field
		changePictureField=new JTextField(TEXT_FIELD_SIZE);
		add(changePictureField,WEST);
		changePictureField.addActionListener(this);
		
		//add change picture button
		add(new JButton("Change Picture"),WEST);
		
		//add padding
		add(new JLabel( EMPTY_LABEL_TEXT),WEST);
		
		//add add friend text field
		addFriendField=new JTextField(TEXT_FIELD_SIZE);
		add(addFriendField,WEST);
		addFriendField.addActionListener(this);
		
		//add change picture button
		add(new JButton("Add Friend"),WEST);
		
    }
    
    private JTextField nameTextField;
    private JTextField changeStatusField;
    private JTextField changePictureField;
    private JTextField addFriendField;
    private FacePamphletDatabase database;
    private FacePamphletProfile currentProfile;
    private  FacePamphletCanvas canvas;

}
