package actions;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Font;
import mainline.UserInterface.TabNames;

/*******
 * <p> Title: TaskSpecific Class</p>
 * 
 * <p> Description: A JavaFX application: This controller class established the user interface for
 * Tasks Tab objects by inheriting the ListItem and building upon it.</p>
 * 
 * <p> Copyright: Lynn Robert Carter © 2019-05-11 </p>
 * 
 * @author Lynn Robert Carter
 * 
 * @version 1.00	2019-05-11 Baseline
 * 
 */
public class TaskSpecific extends ListItem {

	/**********************************************************************************************

	Class Attributes

	 **********************************************************************************************/

	//---------------------------------------------------------------------------------------------
	// These attributes enable us to hide the details of the tab height and the height and width of
	// of the window decorations for the code that implements this user interface
	private int xOffset = 0;
	private int yOffset = 0;

	//---------------------------------------------------------------------------------------------
	// The GUI objects that make up the Task specific elements of the user interface
	//
	// These items are the middle column middle to lower half
	private Label theTitle = new Label("Artifacts are selected from this Effort Category:");
	private ObservableList <String> effortCategoryList = FXCollections.observableArrayList("None Selected");
	private ComboBox <String> chooseUsedEffortCategory = new ComboBox <String>(FXCollections.observableArrayList(effortCategoryList));
	private Label theRolesTitle = new Label("Roles performed as part of this task:");
	protected TableView <RolesItem> theRolesDetailList = new TableView <RolesItem>();
	private TableColumn <RolesItem, String> theRolesNameColumn = new TableColumn <>("Produced Artifact Names");
	private ComboBox <String> theRole = new ComboBox <String>(FXCollections.observableArrayList());
	private Button btnInsertRole = new Button("Insert the artifact into the above list");
	private Button btnMoveUpRole = new Button("Move Up");
	private Button btnMoveDnRole = new Button("Move Dn");
	private Button btnDeleteRole = new Button("Delete");
	private Label theRoleTitle = new Label("Select a role to be added to the list:");
	

	// These GUI elements for the right most column
	private Label theArtifactsUsedTitle = new Label("Artifacts used by this task:");
	protected TableView <ECUsedItem> theUsedDetailList = new TableView <ECUsedItem>();
	private TableColumn <ECUsedItem, String> theUsedNameColumn = new TableColumn <>("Used Artifact Names");
	private ComboBox <String> theUsedArtifact = new ComboBox <String>(FXCollections.observableArrayList());
	private Button btnInsertUsed = new Button("Insert the artifact into the above list");
	private Button btnMoveUpUsed = new Button("Move Up");
	private Button btnMoveDnUsed = new Button("Move Dn");
	private Button btnDeleteUsed = new Button("Delete");
	private Label theUsedTitle = new Label("Select an artifact to be added to the list:");

	private Label theArtifactsProducedTitle = new Label("Artifacts produced by this task:");
	protected TableView <ECProducedItem> theProducedDetailList = new TableView <ECProducedItem>();
	private TableColumn <ECProducedItem, String> theProducedNameColumn = new TableColumn <>("Produced Artifact Names");
	private ComboBox <String> theProducedArtifact = new ComboBox <String>(FXCollections.observableArrayList());
	private Button btnInsertProduced = new Button("Insert the artifact into the above list");
	private Button btnMoveUpProduced = new Button("Move Up");
	private Button btnMoveDnProduced = new Button("Move Dn");
	private Button btnDeleteProduced = new Button("Delete");
	private Label theProducedTitle = new Label("Select an artifact to be added to the list:");

	//---------------------------------------------------------------------------------------------
	// The following are the GUI objects that make up the Task specific elements of the user 
	// interface	
	private String currentSelectedEffortCategory = "";
	private ObservableList <String> artifactBaseSet = FXCollections.observableArrayList();
	private ObservableList <String> artifactsAvailableForUsed = FXCollections.observableArrayList();
	private ObservableList <String> artifactsAvailableForProduced = FXCollections.observableArrayList();
	private ObservableList <String> roleBaseSet = FXCollections.observableArrayList();

	private ObservableList <String> artifactsUsedSelected = FXCollections.observableArrayList();
	private ObservableList <String> artifactsProducedSelected = FXCollections.observableArrayList();
	private ObservableList <String> rolesSelected = FXCollections.observableArrayList();

	private ObservableList <String> artifactsUsedAvailable = FXCollections.observableArrayList();
	private ObservableList <String> artifactsProducedAvailable = FXCollections.observableArrayList();
	private ObservableList <String> rolesAvailable = FXCollections.observableArrayList();

	private boolean updatingTheList = false;
	private EffortCategorySpecific saveEcs = null;
	private RoleSpecific saveRole = null;
	private ListItem saveEc = null;
	private ListItem saveArt = null;
	
	// This is the Task specific Detail list
	protected ObservableList <TaskDetail> theTaskDetailList = FXCollections.observableArrayList();


	/**********
	 * This constructor establishes the base ListItem and then initializes the Task specific
	 * attributes for the application.
	 * 
	 * @param g		The Group link is used to establish the list of GUI elements for this tab
	 * @param x		The x offset for the GUI elements to fit into the decorative borders
	 * @param y		The y offset
	 * @param t		The enumeration that helps select the right strings for labels, etc.
	 */
	public TaskSpecific(Group g, int x, int y, TabNames t) {
		super(g, x, y, t);
		xOffset = x;
		yOffset = y;

		// Setup the title and the ComboBox which selects one of the Effort Category entries to
		// us from which the list of artifacts is defined
		setupLabelUI(theTitle, "Arial", 14, 100, Pos.BASELINE_LEFT, 360 + xOffset, 260 + yOffset);
		setupComboBoxUI(chooseUsedEffortCategory, 200, 370 + xOffset, 280 + yOffset);
		chooseUsedEffortCategory.setOnAction((event) -> { chooseUsedEffortCategory();});
		setupLabelUI(theRolesTitle, "Arial", 14, 100, Pos.BASELINE_LEFT, 360 + xOffset, 325 + yOffset);
		
		setupTableViewRoles(theRolesDetailList, 250, 150, 370 + xOffset,  345 + yOffset, false);
		// Establish the columns in the Table View
		theRolesNameColumn.setCellValueFactory(new PropertyValueFactory<>("item"));
		theRolesDetailList.getColumns().add(theRolesNameColumn);
		theRolesNameColumn.setMaxWidth(245);
		theRolesNameColumn.setMinWidth(245);

		// Set up the label for the ComboBox below the table view of Artifacts Used
		setupLabelUI(theRoleTitle, "Arial", 14, 100, Pos.BASELINE_LEFT, 360 +10 + xOffset, 505 + yOffset);

		// This ComboBox specifies an available option. No action is performed when one is selected
		// The user must press the insert button to cause the selected item to be added to the list
		setupComboBoxUI(theRole, 245, 370 + xOffset, 523 + yOffset);
		
		// This button adds the currently selected Artifact to the list of Artifacts produced
		setupButtonUI(btnInsertRole, "Arial", 14, 245, Pos.BASELINE_CENTER, 360 + 10 + xOffset, 555 + yOffset);
		btnInsertRole.setOnAction((event) -> { insertRole(); });
		btnInsertRole.setTooltip(new Tooltip("Click here to move the above selected entry up one position"));

		// Set up the Move Up button. It moves the current selected TableView entry up one
		// position. Link the button to the action method and define the tool tip to help the user
		// understand the user interface.
		setupButtonUI(btnMoveUpRole, "Arial", 14, 70, Pos.BASELINE_CENTER, 360 + 10 + xOffset, 585 + yOffset);
		btnMoveUpRole.setOnAction((event) -> { moveRoleUp(); });
		btnMoveUpRole.setTooltip(new Tooltip("Click here to move the above selected entry up one position"));

		// Set up the Move Dn button. It moves the current selected TabkeView entry down one
		// position. Link the button to the action method and define the tool tip to help the user
		// understand the user interface.
		setupButtonUI(btnMoveDnRole, "Arial", 14, 70, Pos.BASELINE_CENTER, 360 + 100 + xOffset, 585 + yOffset);
		btnMoveDnRole.setOnAction((event) -> { moveRoleDn(); });
		btnMoveDnRole.setTooltip(new Tooltip("Click here to move the above selected entry down one position"));

		// Set up the Delete button. It removes the current selected TabkeView entry. Link the 
		// button to the action method and define the tool tip to help the user understand the
		// user interface.
		setupButtonUI(btnDeleteRole, "Arial", 14, 70, Pos.BASELINE_CENTER, 360 + 185 + xOffset, 585 + yOffset);
		btnDeleteRole.setOnAction((event) -> { deleteRole(); });
		btnDeleteRole.setTooltip(new Tooltip("Click here to delete the above selected entry"));

		
		// Set up the right most column of the GUI.
		
		// This title define the artifacts used by this task
		setupLabelUI(theArtifactsUsedTitle, "Arial", 14, 100, Pos.BASELINE_LEFT, 685 + xOffset, 20 + yOffset);

		// Set up the TableView object for the list of artifacts used by this task
		setupTableViewUsedUI(theUsedDetailList, 250, 150, 695 + xOffset,  40 + yOffset, false);
		// Establish the columns in the Table View
		theUsedNameColumn.setCellValueFactory(new PropertyValueFactory<>("item"));
		theUsedDetailList.getColumns().add(theUsedNameColumn);
		theUsedNameColumn.setMaxWidth(245);
		theUsedNameColumn.setMinWidth(245);

		// Set up the label for the ComboBox below the table view of Artifacts Used
		setupLabelUI(theUsedTitle, "Arial", 14, 100, Pos.BASELINE_LEFT, 700 + xOffset, 200 + yOffset);

		// This ComboBox specifies available artifacts. No action is performed when one is selected
		// The user must press the insert button to cause the selected item to be added to the list
		setupComboBoxUI(theUsedArtifact, 245, 700 + xOffset, 218 + yOffset);

		// This button adds the currently selected Artifact to the list of Artifacts used
		setupButtonUI(btnInsertUsed, "Arial", 14, 245, Pos.BASELINE_CENTER, 700 + xOffset, 250 + yOffset);
		btnInsertUsed.setOnAction((event) -> { insertUsedArtifact(); });
		btnInsertUsed.setTooltip(new Tooltip("Click here to move the above selected entry up one position"));


		// Set up the Move Up button. It moves the current selected TableView artifact up one
		// position. Link the button to the action method and define the tool tip to help the user
		// understand the user interface.
		setupButtonUI(btnMoveUpUsed, "Arial", 14, 70, Pos.BASELINE_CENTER, 700 + xOffset, 280 + yOffset);
		btnMoveUpUsed.setOnAction((event) -> { moveUsedArtifactUp(); });
		btnMoveUpUsed.setTooltip(new Tooltip("Click here to move the above selected entry up one position"));

		// Set up the Move Dn button. It moves the current selected TabkeView artifact down one
		// position. Link the button to the action method and define the tool tip to help the user
		// understand the user interface.
		setupButtonUI(btnMoveDnUsed, "Arial", 14, 70, Pos.BASELINE_CENTER, 790 + xOffset, 280 + yOffset);
		btnMoveDnUsed.setOnAction((event) -> { moveUsedArtifactDn(); });
		btnMoveDnUsed.setTooltip(new Tooltip("Click here to move the above selected entry down one position"));

		// Set up the Delete button. It removes the current selected TabkeView artifact. Link the 
		// button to the action method and define the tool tip to help the user understand the
		// user interface.
		setupButtonUI(btnDeleteUsed, "Arial", 14, 70, Pos.BASELINE_CENTER, 875 + xOffset, 280 + yOffset);
		btnDeleteUsed.setOnAction((event) -> { deleteUsedArtifact(); });
		btnDeleteUsed.setTooltip(new Tooltip("Click here to delete the above selected entry"));

		// This is the title for the Artifacts Produced Table View List and collection of buttons
		setupLabelUI(theArtifactsProducedTitle, "Arial", 14, 100, Pos.BASELINE_LEFT, 685 + xOffset, 325 + yOffset);

		// Set up the TableView object for the list of artifacts produced by this task
		setupTableViewProducedUI(theProducedDetailList, 250, 150, 695 + xOffset,  345 + yOffset, false);
		// Establish the columns in the Table View
		theProducedNameColumn.setCellValueFactory(new PropertyValueFactory<>("item"));
		theProducedDetailList.getColumns().add(theProducedNameColumn);
		theProducedNameColumn.setMaxWidth(245);
		theProducedNameColumn.setMinWidth(245);

		// Set up the label for the ComboBox below the artifacts produced table view 
		setupLabelUI(theProducedTitle, "Arial", 14, 100, Pos.BASELINE_LEFT, 700 + xOffset, 505 + yOffset);

		// This ComboBox specifies an available option. No action is performed when one is selected
		// The user must press the insert button to cause the selected item to be added to the list
		setupComboBoxUI(theProducedArtifact, 245, 700 + xOffset, 523 + yOffset);

		// This button adds the currently selected Artifact to the list of Artifacts produced
		setupButtonUI(btnInsertProduced, "Arial", 14, 245, Pos.BASELINE_CENTER, 690 + 10 + xOffset, 555 + yOffset);
		btnInsertProduced.setOnAction((event) -> { insertProducedArtifact(); });
		btnInsertProduced.setTooltip(new Tooltip("Click here to move the above selected entry up one position"));

		// Set up the Move Up button. It moves the current selected TableView entry up one
		// position. Link the button to the action method and define the tool tip to help the user
		// understand the user interface.
		setupButtonUI(btnMoveUpProduced, "Arial", 14, 70, Pos.BASELINE_CENTER, 690 + 10 + xOffset, 585 + yOffset);
		btnMoveUpProduced.setOnAction((event) -> { moveProducedArtifactUp(); });
		btnMoveUpProduced.setTooltip(new Tooltip("Click here to move the above selected entry up one position"));

		// Set up the Move Dn button. It moves the current selected TabkeView entry down one
		// position. Link the button to the action method and define the tool tip to help the user
		// understand the user interface.
		setupButtonUI(btnMoveDnProduced, "Arial", 14, 70, Pos.BASELINE_CENTER, 690 + 100 + xOffset, 585 + yOffset);
		btnMoveDnProduced.setOnAction((event) -> { moveProducedArtifactDn(); });
		btnMoveDnProduced.setTooltip(new Tooltip("Click here to move the above selected entry down one position"));

		// Set up the Delete button. It removes the current selected TabkeView entry. Link the 
		// button to the action method and define the tool tip to help the user understand the
		// user interface.
		setupButtonUI(btnDeleteProduced, "Arial", 14, 70, Pos.BASELINE_CENTER, 690 + 185 + xOffset, 585 + yOffset);
		btnDeleteProduced.setOnAction((event) -> { deleteProducedArtifact(); });
		btnDeleteProduced.setTooltip(new Tooltip("Click here to delete the above selected entry"));

		// When the Name field is changed, this causes a change even which deals with display the
		// red lines and text warning the user about changes that have not been saved
		theNameField.setOnAction((event) -> { changeEvent(); });

		// The default state is the content has been changed message is hidden
		hideTheContentChangedMessage();

		// Add all of the GUI elements to this window so they can be displayed
		g.getChildren().addAll(
				// Middle column, lower half GUI elements
				theTitle, chooseUsedEffortCategory, theRolesTitle, theRolesDetailList,
				theRoleTitle, theRole, btnInsertRole, btnMoveUpRole, btnMoveDnRole, btnDeleteRole, 
				
				// Right column, upper half GUI elements
				theArtifactsUsedTitle, theUsedDetailList, theUsedTitle, theUsedArtifact, btnInsertUsed, 
				btnMoveUpUsed, btnMoveDnUsed, btnDeleteUsed, 
				
				// Right column, lower half GUI elements
				theArtifactsProducedTitle, theProducedDetailList, theProducedTitle, theProducedArtifact, btnInsertProduced, 
				btnMoveUpProduced, btnMoveDnProduced, btnDeleteProduced);
	}

	/**********
	 * Private local method to initialize the standard fields for a JavaFX Label object
	 * 
	 * @param l		The Label object to be initialized
	 * @param ff	The font face for the label's text
	 * @param f		The font size for the label's text
	 * @param w		The minimum width for the Label
	 * @param p		The alignment for the text within the specified width
	 * @param x		The x-axis location for the Label
	 * @param y		The y-axis location for the Label
	 */
	private void setupLabelUI(Label l, String ff, double f, double w, Pos p, double x, double y){
		l.setFont(Font.font(ff, f));
		l.setMinWidth(w);
		l.setAlignment(p);
		l.setLayoutX(x);
		l.setLayoutY(y);
	}

	/**********
	 * Private local method to initialize the standard fields for a JavaFX ComboBox object
	 * 
	 * @param c		The ComboBox object
	 * @param w		The width of the GUI element
	 * @param x		The x-axis location for the ComboBox
	 * @param y		The y-axis location for the ComboBox
	 */
	private void setupComboBoxUI(ComboBox <String> c, double w, double x, double y){
		c.setMinWidth(w);
		c.setLayoutX(x);
		c.setLayoutY(y);
	}
	
	/**********
	 * Private local method to initialize the standard fields for a TableView
	 * 
	 * @param a		The TableView to be initialized
	 * @param w		The minimum width for the TextArea
	 * @param h		The maximum height for the TextArea
	 * @param x		The x-axis location for the TextArea
	 * @param y		The y-axis location for the TextArea
	 * @param e		A flag that specific if the TextArea is editable
	 */
	private void setupTableViewRoles(TableView<RolesItem> a, double w, double h, double x, double y, boolean e){
		a.setMinWidth(w);
		a.setMaxWidth(w);
		a.setMinHeight(h);
		a.setMaxHeight(h);
		a.setLayoutX(x);
		a.setLayoutY(y);		
		a.setEditable(e);
	}


	/**********
	 * Private local method to initialize the standard fields for a TableView
	 * 
	 * @param a		The TableView to be initialized
	 * @param w		The minimum width for the TextArea
	 * @param h		The maximum height for the TextArea
	 * @param x		The x-axis location for the TextArea
	 * @param y		The y-axis location for the TextArea
	 * @param e		A flag that specific if the TextArea is editable
	 */
	private void setupTableViewUsedUI(TableView<ECUsedItem> a, double w, double h, double x, double y, boolean e){
		a.setMinWidth(w);
		a.setMaxWidth(w);
		a.setMinHeight(h);
		a.setMaxHeight(h);
		a.setLayoutX(x);
		a.setLayoutY(y);		
		a.setEditable(e);
	}
	
	/**********
	 * Private local method to initialize the standard fields for a TableView
	 * 
	 * @param a		The TableView to be initialized
	 * @param w		The minimum width for the TextArea
	 * @param h		The maximum height for the TextArea
	 * @param x		The x-axis location for the TextArea
	 * @param y		The y-axis location for the TextArea
	 * @param e		A flag that specific if the TextArea is editable
	 */
	private void setupTableViewProducedUI(TableView<ECProducedItem> a, double w, double h, double x, double y, boolean e){
		a.setMinWidth(w);
		a.setMaxWidth(w);
		a.setMinHeight(h);
		a.setMaxHeight(h);
		a.setLayoutX(x);
		a.setLayoutY(y);		
		a.setEditable(e);
	}

	/**********
	 * Private local method to initialize the standard fields for a button
	 * 
	 * @param b		The Button to be initialized
	 * @param ff	The font face for the label's text
	 * @param f		The font size for the label's text
	 * @param w		The minimum width for the TextArea
	 * @param p		The alignment for the text within the specified width
	 * @param x		The x-axis location for the TextField
	 * @param y		The y-axis location for the TextField
	 */
	private void setupButtonUI(Button b, String ff, double f, double w, Pos p, double x, double y){
		b.setFont(Font.font(ff, f));
		b.setMinWidth(w);
		b.setAlignment(p);
		b.setLayoutX(x);
		b.setLayoutY(y);		
	}

	/**********
	 * This method is one of a pair that is used to ensure that the list of all possible options
	 * for a list agrees with the list of available to select items and the list of selected items.
	 * This method returns true only if the specified String is NOT in the specified list of
	 * Strings. Otherwise, it returns false;
	 * 
	 * @param strList	The specified list of Strings
	 * @param str		The specified String
	 * @return			True if str is NOT in the strList; else false
	 */
	private Boolean strIsNotInTheList(ObservableList <String> strList, String str) {
		
		// Check the second parameter against every ItemEntry in the list
		for (String s : strList)
			// If the second parameter is in the list, return false
			if (s.equals(str)) return false;
		
		// If the second parameter is *not* in the list, return true
		return true;
	}
	
	/**********
	 * This method is the second of a pair that is used to ensure that the list of all possible
	 * options for a list agrees with the list of available to select items and the list of 
	 * selected items. This method returns false only if the specified String is NOT in the specified list of
	 * Strings. Otherwise, it returns true;
	 * 
	 * @param strList	The specified list of Strings
	 * @param str		The specified String
	 * @return			False if str is NOT in the strList; else true
	 */
	private Boolean strIsInTheList(ObservableList <String> strList, String str) {
		
		// Check the second parameter against every ItemEntry in the list
		for (String sItem : strList)
			// If the second parameter is in the list, return false
			if (sItem.equals(str)) return true;
		
		// If the second parameter is *not* in the list, return true
		return false;
	}
	
	/**********
	 * This private method is called four times each time this tab is activated.  It rebuilds the
	 * Options list from the Tab's list and duplicates it into the Available list.  The method then
	 * checks each item in the Selected list to ensure it is still in the Options list.  If it
	 * isn't, it is removed. Finally the Available list is created by removing all of the Selected
	 * items from the stating state where it contains all of the Options.
	 * 
	 * @param tabList		The list from one of the tabs
	 * @param options		The list of all possible options for this tab
	 * @param selected		The list of all selected options from the tab
	 * @param available		The list of all of the possible options that have not yet been selected
	 */
	private void repopulateAList(ObservableList <String> tabList, ObservableList <String> options, 
			ObservableList <String> selected, ObservableList <String> available) {
		
		// Clear the generated lists
		options.clear();
		available.clear();
		
		// 1. Build the Option list and the Available list from the  Tab's list
		for (int ndx = 0; ndx < tabList.size(); ndx++) {
			options.add(tabList.get(ndx));
			available.add(tabList.get(ndx));
		}
		
		// 2. Examine each Selected list item to ensure it is in STILL in the Option list
		//		If not, remove it from the Selected list.
		for (int ndx = 0; ndx < selected.size(); ndx++)
			if (strIsNotInTheList(options, selected.get(ndx)))
				selected.remove(ndx--);							// This auto-decrement after the
																// remove is really important!

		// 3. Construct the Available List by removing all Selected items from the Option's list
		for (int ndx = 0; ndx < available.size(); ndx++)
			if (strIsInTheList(selected, available.get(ndx))) 
				available.remove(ndx--);						// This auto-decrement after the
																// remove is really important!
	}
	
	/**********
	 * This private method is called four times each time this tab is activated.  It uses the
	 * Tab list and to populate the Available list.  The method then checks each item in the 
	 * Selected list to ensure it is still in the Tab list.  If it isn't, it is removed. Finally 
	 * the Available list is finalized by removing all of the Selected items from the starting 
	 * state where it contains all of the Tab elements.
	 * 
	 * @param tabList		The list from one of the tabs
	 * @param selected		The list of all selected options from the tab
	 * @param available		The list of all of the possible options that have not yet been selected
	 */
	private void repopulateRList(ObservableList <String> tabList, ObservableList <String> selected, 
			ObservableList <String> available) {
		
		// Clear the generated lists
		available.clear();
		
		// 1. Build the Available list from the Tab's list
		for (int ndx = 0; ndx < tabList.size(); ndx++)
			available.add(tabList.get(ndx));
		
		// 2. Examine each Selected list item to ensure it is in STILL in the tabList
		//		If not, remove it from the Selected list.
		for (int ndx = 0; ndx < selected.size(); ndx++)
			if (strIsNotInTheList(tabList, selected.get(ndx)))
				selected.remove(ndx--);							// This auto-decrement after the
																// remove is really important!

		// 3. Construct the Available List by removing all Selected items from the Option's list
		for (int ndx = 0; ndx < available.size(); ndx++)
			if (strIsInTheList(selected, available.get(ndx))) 
				available.remove(ndx--);						// This auto-decrement after the
																// remove is really important!
	}

	/**********
	 * Handle the editTheEntry Button action by doing the super class function and then the Tab
	 * specific work
	 */	
	protected void editTheEntry() {
		super.editTheEntry();
		if (tabSpecificIndex == -1) return;
		
		// If the generic code was successful, 
		TaskDetail taskTemp = theTaskDetailList.get(tabSpecificIndex);
		artifactsUsedSelected = taskTemp.selectedUsedList;
		artifactsProducedSelected = taskTemp.selectedProducedList;
		
		// Re-populate the lists
		repopulateAList(artifactBaseSet, artifactsAvailableForUsed, artifactsUsedSelected, 
				artifactsUsedAvailable);
		repopulateAList(artifactBaseSet, artifactsAvailableForProduced, 
				artifactsProducedSelected, artifactsProducedAvailable);
		repopulateRList(roleBaseSet, rolesSelected, rolesAvailable);
		
		// Set up the ComboBoxes
		theUsedArtifact.getSelectionModel().clearAndSelect(0);
		theProducedArtifact.getSelectionModel().clearAndSelect(0);
		theRole.getSelectionModel().clearAndSelect(0);	
		
		// Set up the used Table View
		ObservableList <ECUsedItem> usedList = theUsedDetailList.getItems();
		usedList.clear();
		for (int ndx=0; ndx < artifactsUsedSelected.size(); ndx++)
			usedList.add(new ECUsedItem(artifactsUsedSelected.get(ndx)));
		theUsedDetailList.getSelectionModel().clearAndSelect(0);
		
		// Set up the produced Table View
		ObservableList <ECProducedItem> producedList = theProducedDetailList.getItems();
		producedList.clear();
		for (int ndx=0; ndx < artifactsProducedSelected.size(); ndx++)
			producedList.add(new ECProducedItem(artifactsProducedSelected.get(ndx)));
		theProducedDetailList.getSelectionModel().clearAndSelect(0);
		
		// Set up the roles Table View
		ObservableList <RolesItem> roleList = theRolesDetailList.getItems();
		roleList.clear();
		for (int ndx=0; ndx < rolesSelected.size(); ndx++)
			roleList.add(new RolesItem(rolesSelected.get(ndx)));
		theRolesDetailList.getSelectionModel().clearAndSelect(0);
	}
	
	/**********
	 * Handle the saveTheEntry Button action by doing the super class function and then the Tab
	 * specific work
	 */	
	protected void saveTheEntry() {
		super.saveTheEntry();
		if (tabSpecificIndex == -1) return;
		hideTheContentChangedMessage();
		TaskDetail taskTemp = new TaskDetail(artifactsUsedSelected, artifactsProducedSelected, rolesSelected);
		theTaskDetailList.set(tabSpecificIndex, taskTemp);
	}
	
	/**********
	 * Handle the AddTheEntryToTheBottom Button action by doing the super class function and then the Tab
	 * specific work
	 */	
	protected void AddTheEntryToTheBottom() {
		super.AddTheEntryToTheBottom();
		if (tabSpecificIndex == -1) return;
		TaskDetail taskTemp = new TaskDetail(artifactsUsedSelected, artifactsProducedSelected, rolesSelected);
		theTaskDetailList.add(tabSpecificIndex, taskTemp);
	}
	
	/**********
	 * Handle the AddTheEntryAbove Button action by doing the super class function and then the Tab
	 * specific work
	 */	
	protected void AddTheEntryAbove() {
		super.AddTheEntryAbove();
		if (tabSpecificIndex == -1) return;
		TaskDetail taskTemp = new TaskDetail(artifactsUsedSelected, artifactsProducedSelected, rolesSelected);
		theTaskDetailList.add(tabSpecificIndex, taskTemp);
	}
	
	/**********
	 * Handle the AddTheEntryBelow Button action by doing the super class function and then the Tab
	 * specific work
	 */	
	protected void AddTheEntryBelow() {
		super.AddTheEntryBelow();
		if (tabSpecificIndex == -1) return;
		TaskDetail taskTemp = new TaskDetail(artifactsUsedSelected, artifactsProducedSelected, rolesSelected);
		theTaskDetailList.add(tabSpecificIndex+1, taskTemp);
	}
	
	/**********
	 * Handle the clearTheEntry Button action by doing the super class function and then the Tab
	 * specific work
	 */	
	protected void clearTheEntry() {
		super.clearTheEntry();
		if (tabSpecificIndex == -1) return;
	}


	/**********
	 * This methods is used to establish the Used Table View list based on a list of Strings
	 * 
	 * @param src	The list of names to be used to create the table view list
	 * @return		The table view list
	 */
	private ObservableList<ECUsedItem> establishUsedDetailList(ObservableList <String> src) {
		// Fetch the table view list from the right most GUI Table View
		ObservableList<ECUsedItem> result = theUsedDetailList.getItems();

		// Clear the list
		result.clear();

		// If there is no list of strings, we are done
		if (src == null) return result;

		// Create a Used list item for each String in the source list
		for (int i = 0; i < src.size(); i++) {
			// Create a new item entry based on those values
			ECUsedItem def = new ECUsedItem(src.get(i));
			// Add the new item entry at the end of the list
			result.add(def);
		}

		// Return the result
		return result;
	}


	/**********
	 * This methods is used to establish the Produced Table View list based on a list of Strings
	 * 
	 * @param src	The list of names to be used to create the table view list
	 * @return		The table view list
	 */
	private ObservableList<ECProducedItem> establishProducedDetailList(ObservableList <String> src) {
		// Fetch the table view list from the right most GUI Table View
		ObservableList<ECProducedItem> result = theProducedDetailList.getItems();

		// Clear the list
		result.clear();

		// If there is no list of strings, we are done
		if (src == null) return result;

		// Create a list item for each String in the source list
		for (int i = 0; i < src.size(); i++) {
			// Create a new item entry based on those values
			ECProducedItem def = new ECProducedItem(src.get(i));
			// Add the new item entry at the end of the list
			result.add(def);
		}

		// Return the result
		return result;
	}

	/**********
	 * This method is called by the UserInterface each time the user changes tabs and selects this
	 * Task tab
	 * 
	 * @param ecs	This is a link to the EffortCategorySpecific tab list
	 * @param ec	This is the selected Effort Category Item
	 * @param art	This is the list of artifacts 
	 */
	public void repopulate(EffortCategorySpecific ecs, ListItem ec, ListItem art, RoleSpecific r) {
		// When we re-populate, we do not what event processing to happen
		updatingTheList = true;
		
		// Remember theRoleSpecific object
		saveRole = r;
		
		// Construct the lists for this tab based on these inputs
		constructTheLists(ecs, ec, art, r);
		
		// If, when the lists are constructed, no Effort Category has been selected, choose the 
		// zero item in the list: None selected
		if (chooseUsedEffortCategory.getSelectionModel().getSelectedIndex() == -1) {
			chooseUsedEffortCategory.getSelectionModel().clearAndSelect(0);
		}

		// We are done with the re-population, so user events can now be processed
		updatingTheList = false;
	}

	/**********
	 * This helper method sees if a specified String is in the List
	 * 
	 * @param str	The String we are looking to find
	 * @param lst	The list of items that has a name field that is a String
	 * 
	 * @return		true if a match is found else false
	 */
	private boolean isInTheList(String str, ListItem lst) {
		ObservableList <ListItemEntry> oList = lst.getTheList();
		for (int ndx = 0; ndx < oList.size(); ndx++)
			if (oList.get(ndx).getListItemName().contentEquals(str)) return true;
		return false;
	}

	/**********
	 * This helper method sees if a string is NOT in the list of Artifacts
	 * 
	 * @param str	The String we are looking to find
	 * 
	 * @return		true is not found, else false
	 */
	private boolean artifactIsNotAvailable(String str) {
		for (int ndx = 0; ndx < artifactBaseSet.size(); ndx++)
			if (str.contentEquals(artifactBaseSet.get(ndx))) return false;
		return true;
	}

	/**********
	 * This helper method sees if a string is NOT in the UsedSelected list
	 * 
	 * @param str	The String we are looking to find
	 * 
	 * @return		true is not found in the list, else false
	 */
	private boolean artifactHasNotBeenUsedSelected(String str) {
		for (int ndx = 0; ndx < artifactsUsedSelected.size(); ndx++)
			if (str.contentEquals(artifactsUsedSelected.get(ndx))) return false;
		return true;
	}

	/**********
	 * This helper method sees if a string is NOT in the ProducedSelected list
	 * 
	 * @param str	The String we are looking to find
	 * 
	 * @return		true is not found in the list, else false
	 */
	private boolean artifactHasNotBeenProducedSelected(String str) {
		for (int ndx = 0; ndx < artifactsProducedSelected.size(); ndx++)
			if (str.contentEquals(artifactsProducedSelected.get(ndx))) return false;
		return true;
	}
	
	/**********
	 * This helper method sees if a string is NOT in the list of roles
	 * 
	 * @param str	The String we are looking to find
	 * 
	 * @return		true is not found, else false
	 */
	private boolean roleIsNotAvailable(String str) {
		for (int ndx = 0; ndx < roleBaseSet.size(); ndx++)
			if (str.contentEquals(roleBaseSet.get(ndx))) return false;
		return true;
	}
	
	/**********
	 * This helper method sees if a string is NOT in the list of roles
	 * 
	 * @param str	The String we are looking to find
	 * 
	 * @return		true is not found, else false
	 */
	private boolean roleHasBeenSelected(String str) {
		for (int ndx = 0; ndx < rolesSelected.size(); ndx++)
			if (str.contentEquals(rolesSelected.get(ndx))) return true;
		return false;
	}

	/**********
	 * This method clears all of the local lists in the right third of the Task tab
	 */
	private void clearTheRightMostLists() {

		theUsedArtifact.setItems(FXCollections.observableArrayList());
		theProducedArtifact.setItems(FXCollections.observableArrayList());
		theUsedDetailList.setItems(FXCollections.observableArrayList());
		theProducedDetailList.setItems(FXCollections.observableArrayList());

		ObservableList<ECUsedItem> emptyECUsedItem = FXCollections.observableArrayList();
		ObservableList<ECProducedItem> emptyECProducedItem = FXCollections.observableArrayList();
		theUsedDetailList.setItems(emptyECUsedItem);
		theUsedDetailList.getSelectionModel().clearSelection();
		theProducedDetailList.setItems(emptyECProducedItem);
		theProducedDetailList.getSelectionModel().clearSelection();

		ObservableList<String> emptyString = FXCollections.observableArrayList();
		theUsedArtifact.setItems(emptyString);
		theUsedArtifact.getSelectionModel().clearSelection();
		theProducedArtifact.setItems(emptyString);
		theProducedArtifact.getSelectionModel().clearSelection();
	}

	/**********
	 * This method clears all of the local lists in the Task specific tab
	 */
	private void clearTheLists() {
		effortCategoryList.clear();
		effortCategoryList.add("None Selected");
		chooseUsedEffortCategory.setItems(effortCategoryList);
		chooseUsedEffortCategory.getSelectionModel().clearAndSelect(0);

		clearTheRightMostLists();
	}

	/**********
	 * Given a list of Effort Categories, this method produces a list for the ComboBox
	 * 
	 * @param ec	The list of Effort Categories
	 */
	private void setupTheEffortCategoryComboBox(ListItem ec) {
		ObservableList <String> tmp = FXCollections.observableArrayList();

		tmp.add("None Selected");
		for (int ndx = 0; ndx < ec.getTheList().size(); ndx++)
			tmp.add(ec.getTheList().get(ndx).getListItemName());
		chooseUsedEffortCategory.setItems(tmp);
		effortCategoryList = tmp;
		chooseUsedEffortCategory.setItems(effortCategoryList);
	}
	
	private void constructTheRolesList(ListItem r) {
		roleBaseSet.clear();
		rolesAvailable.clear();
		for (int ndx=0; ndx < r.getTheList().size(); ndx++) {
			roleBaseSet.add(r.getTheList().get(ndx).getListItemName());
			rolesAvailable.add(r.getTheList().get(ndx).getListItemName());
		}
		
		for (int ndx = 0; ndx < rolesSelected.size(); ndx++)
			if (roleIsNotAvailable(rolesSelected.get(ndx)))
				rolesSelected.remove(ndx--);
		
		for (int ndx = 0; ndx < rolesAvailable.size(); ndx++)
			if (roleHasBeenSelected(rolesAvailable.get(ndx)))
				rolesAvailable.remove(ndx--);
		
		theRolesDetailList.getItems().clear();
		for (int ndx = 0; ndx < rolesSelected.size(); ndx++) {
			RolesItem ri = new RolesItem(rolesSelected.get(ndx));
			theRolesDetailList.getItems().add(ri);
		}
		theRolesDetailList.getSelectionModel().clearAndSelect(0);
		theRole.setItems(rolesAvailable);
		theRole.getSelectionModel().clearAndSelect(0);
	}

	/**********
	 * Given the parameters, this method produces all of the Table Views and ComboBoxes needed
	 * 
	 * @param ecs	The list to the Effort Category Tab list of Effort Categories
	 * @param ec	The selected Effort Category item
	 * @param art	The list of artifacts from the Effort Category item
	 */
	private void constructTheLists(EffortCategorySpecific ecs, ListItem ec, ListItem art, ListItem r) {

		// Save the parameter for internal calls
		saveEcs = ecs;
		saveEc = ec;
		saveArt = art;
		
		// Construct the Roles lists
		constructTheRolesList(r);

		// Fetch the most recent selected Effort Category, if there was one.
		currentSelectedEffortCategory = chooseUsedEffortCategory.getValue();
		if (currentSelectedEffortCategory == null)
			currentSelectedEffortCategory = "";

		// If the Effort Categories have not been set up, clear the lists just return 
		if (ec == null || ec.getTheList().isEmpty()) {
			clearTheLists();
			return;
		}

		// There is one or more Effort Categories, so populate this tab's ComboBox
		setupTheEffortCategoryComboBox(ec);

		// Try to find the most recent selected effort category in the list
		int ndx = -1;
		if (currentSelectedEffortCategory.length() > 0 && effortCategoryList.size() > 0)
			ndx = effortCategoryList.indexOf(currentSelectedEffortCategory);

		// See if there was a previously selected Effort Category in the currentSelectedEffortCategory
		if (ndx < 0) {
			// If the previously used Effort Category is no longer there, do not select an item
			clearTheRightMostLists();
			chooseUsedEffortCategory.getSelectionModel().clearSelection();
		}
		else {
			// The previous selected Effort Category is there, so we will use it
			chooseUsedEffortCategory.getSelectionModel().select(ndx);
		}
		int selectedNdx = -1;
		// Find the Effort Categories in the list and set up the lists accordingly
		for (int ecNdx = 0; ecNdx < ec.getTheList().size(); ecNdx++)
			if (ec.getTheList().get(ecNdx).getListItemName().equals(currentSelectedEffortCategory)) {
				selectedNdx = ecNdx + 1;
				break;
		}

		// If there is a list of selected items and one was selected, process it
		if (selectedNdx > 0) {

			// Build the list of potential artifacts from the currently selected Effort Category and definitions
			ObservableList <String> temp = ecs.theEffortCategoryDetailList.get(selectedNdx-1).getSelectedArtifactList();
			
			artifactBaseSet.clear();
			artifactsAvailableForUsed.clear();
			artifactsAvailableForProduced.clear();
			roleBaseSet.clear();
			for (int copyNdx = 0; copyNdx < temp.size(); copyNdx++)
				if (isInTheList(temp.get(copyNdx), art)) {
					artifactBaseSet.add(temp.get(copyNdx));
					artifactsAvailableForUsed.add(temp.get(copyNdx));
					artifactsAvailableForProduced.add(temp.get(copyNdx));
				}
			
			// Build the list of potential role names from the Roles Tab
			roleBaseSet.clear();
			for (int copyNdx = 0; copyNdx < r.getTheList().size(); copyNdx++)
				roleBaseSet.add(r.getTheList().get(copyNdx).getListItemName());

			// Rebuild the list of current selected item give the previous set of selected items
			// and the current available list
			for (int index = 0; index < artifactsUsedSelected.size(); index++)
				if (artifactIsNotAvailable(artifactsUsedSelected.get(index))) 
					artifactsUsedSelected.remove(index--);
			
			for (int index = 0; index < artifactsProducedSelected.size(); index++)
				if (artifactIsNotAvailable(artifactsProducedSelected.get(index))) 
					artifactsProducedSelected.remove(index--);

			// Rebuild the list of current available items by removing those items that have been selected
			artifactsUsedAvailable.clear();
			for (int index = 0; index < artifactsAvailableForUsed.size(); index++)
				if (artifactHasNotBeenUsedSelected(artifactsAvailableForUsed.get(index))) 
					artifactsUsedAvailable.add(artifactsAvailableForUsed.get(index));

			artifactsProducedAvailable.clear();
			for (int index = 0; index < artifactsAvailableForProduced.size(); index++)
				if (artifactHasNotBeenProducedSelected(artifactsAvailableForProduced.get(index)))
					artifactsProducedAvailable.add(artifactsAvailableForProduced.get(index));
			
			// Populate the TableView GUI objects with the just re-populated selected lists
			theUsedDetailList.setItems(establishUsedDetailList(artifactsUsedSelected));
			theUsedDetailList.getSelectionModel().select(0);
			theProducedDetailList.setItems(establishProducedDetailList(artifactsProducedSelected));
			theProducedDetailList.getSelectionModel().select(0);

			// Populate the ComboBox GUI objects with the just re-populated available listed
			theUsedArtifact.setItems(artifactsUsedAvailable);
			theUsedArtifact.getSelectionModel().select(0);
			theProducedArtifact.setItems(artifactsProducedAvailable);
			theProducedArtifact.getSelectionModel().select(0);
		}
		else 
			// There was not a selected EffortCategory item, so clear the tab's right most third
			clearTheRightMostLists();
	}

	/**********
	 * This event handler is used to select an EffortCategory and populate the right third of the
	 * tab accordingly
	 */
	private void chooseUsedEffortCategory() {
		// Do not enter into a update loop
		if (updatingTheList) return;
		updatingTheList = true;

		// Get the CobmoBox current value
		currentSelectedEffortCategory = chooseUsedEffortCategory.getValue();
		
		// If no item has been selected, select the first item in the list, None Selected
		if (currentSelectedEffortCategory == null)
			currentSelectedEffortCategory = "None Selected";
		
		// Given the current state, construct the Table View and ComboBox lists
		constructTheLists(saveEcs, saveEc, saveArt, saveRole);
		
		// Signal that we are done updating the lists and it is okay to process user input
		updatingTheList = false;
	}
	
	
	/**********
	 * Shift the specified String from the Available List to the Selected List and then set up the 
	 * Table View and the ComboBox to reflect this action
	 * 
	 * @param str	The string to be moved from the Available to the Selected List
	 * @param src	The Available List
	 * @param dst	The Selected List
	 */
	private void shiftUsed(TableView <ECUsedItem> dl, ComboBox <String> acombo, String str, 
			ObservableList <String> src, ObservableList <String> dst) {
		// Scan through the Available List, find the String, and remove it from the list
		for (int ndx = 0; ndx < src.size(); ndx++)
			if (src.get(ndx).equals(str)) {
				src.remove(ndx);
				break;
			}
		
		// Add the String to the end of the Selected List
		dst.add(str);
		
		// Fetch the list of items from the right most Table View GUI items and change it to
		// agree with the new Selected List
		ObservableList<ECUsedItem> de = dl.getItems();				// Fetch the list
		de.clear();												// Clear it
		int lastNdx = 0;										// Keep track of the insert index
		for (int i = 0; i < dst.size(); i++) {
			if (dst.get(i).equals(str))
				lastNdx = i;
			// Create a new item entry based on those values
			ECUsedItem def = new ECUsedItem(dst.get(i));
			// Add the new item entry at the end of the list
			de.add(def);
		}
		
		// Insert the new list into the Table View, Scroll to the point of insertion, and select it
		dl.setItems(de);
		dl.scrollTo(lastNdx);
		dl.getSelectionModel().clearAndSelect(lastNdx);
		
		// Select the first item in the Available list
		acombo.getSelectionModel().clearAndSelect(0);
	}
	
	/**********
	 * Shift the specified String from the Available List to the Selected List and then set up the 
	 * Table View and the ComboBox to reflect this action
	 * 
	 * @param str	The string to be moved from the Available to the Selected List
	 * @param src	The Available List
	 * @param dst	The Selected List
	 */
	private void shiftProduced(TableView <ECProducedItem> dl, ComboBox <String> acombo, String str, 
			ObservableList <String> src, ObservableList <String> dst) {
		// Scan through the Available List, find the String, and remove it from the list
		for (int ndx = 0; ndx < src.size(); ndx++)
			if (src.get(ndx).equals(str)) {
				src.remove(ndx);
				break;
			}
		
		// Add the String to the end of the Selected List
		dst.add(str);
		
		// Fetch the list of items from the right most Table View GUI items and change it to
		// agree with the new Selected List
		ObservableList<ECProducedItem> de = dl.getItems();				// Fetch the list
		de.clear();												// Clear it
		int lastNdx = 0;										// Keep track of the insert index
		for (int i = 0; i < dst.size(); i++) {
			if (dst.get(i).equals(str))
				lastNdx = i;
			// Create a new item entry based on those values
			ECProducedItem def = new ECProducedItem(dst.get(i));
			// Add the new item entry at the end of the list
			de.add(def);
		}
		
		// Insert the new list into the Table View, Scroll to the point of insertion, and select it
		dl.setItems(de);
		dl.scrollTo(lastNdx);
		dl.getSelectionModel().clearAndSelect(lastNdx);
		
		// Select the first item in the Available list
		acombo.getSelectionModel().clearAndSelect(0);
	}

	/**********
	 * The method is used to shift an items back from the Selected List to the Available List when
	 * a selected items is deleted.
	 * 
	 * @param str	The name to be shifted back from the Selected List to the Available List
	 * @param mstr	The Master list, so the new item is inserted in the right place
	 * @param avl	The Available List
	 * @param sel	The Selected List
	 */
	private void shiftBack(ComboBox <String> acombo, String str, ObservableList <String> mstr, 
			ObservableList <String> avl, ObservableList <String> sel) {
		// We will scan through the Master List which gives the order of items that the user specified
		// The Master List should be the union of the Available List and the Selected List
		int theSize = mstr.size();	// Set up the size of the master list
		int availableIndex = 0;		// Establish the index for the Available List
		int selectIndex = 0;		// Establish the index for the Selected List
		
		// The Master list elements are either in the Available List or the Selected List.  The
		// order of the items in the Available and Selected List match the order in the Master List.
		// We go through the Master list starting at the beginning and examine each item for the
		// that list.  We know it will be in one or the other
		for (int masterIndex = 0; masterIndex < theSize; masterIndex++)
			// If the item from the master list matched the specified String, we know it must be in
			// in the Selected List and so we can remove it.
			if (str.equals(mstr.get(masterIndex))) {
				// Remove it from the selected list at this point
				sel.remove(selectIndex);
				
				// The item has been found. Add it to the available list at this point.
				avl.add(availableIndex, str);
				
				// Once the shift has been performed, there is no need to continue the loop
				break;
			}
			else {
				// If the Specified String does not match the element from the Master list, it
				// must be in either the Selected List or the Available list.
				// 
				// So, we see if it is in the Available list.
				if (availableIndex < avl.size() && mstr.get(masterIndex).contentEquals(avl.get(availableIndex)))
					// If it is in the Available List, we increment that index
					availableIndex++;
				else
					// Otherwise if must be in the Selected List, so we increment that index
					selectIndex++;
			}
		
		// With the shift back performed, we reset the comboBox and select what we just put back
		acombo.setItems(avl);
		acombo.getSelectionModel().clearAndSelect(availableIndex);
	}

	/**********
	 * This event handler processes the Insert button for the used artifact set of buttons
	 */
	private void insertUsedArtifact() {
		if (updatingTheList) return;
		// This avoids needless repetitive calls due to programmatic requests
		updatingTheList = true;
		
		// Check to be sure there is an item in the ComboBox to insert
		if (theUsedArtifact.getItems().size() < 1 || 
				theUsedArtifact.getSelectionModel().getSelectedIndex() < 0) {
			Alert alert = new Alert(AlertType.WARNING, "Insert is only valid when there's an item to insert!", ButtonType.OK);
			alert.showAndWait();
			updatingTheList = false;
			return;
		}
		
		// Signal that a change has been made and do the change
		showTheContentChangedMessage();
		String str = theUsedArtifact.getValue();
		
		// Perform the change on the appropriate set of lists
		shiftUsed(theUsedDetailList, theUsedArtifact, str, artifactsUsedAvailable, artifactsUsedSelected);
		
		// We are done updating the lists
		updatingTheList = false;	

	}

	/**********
	 * This event handler processes the Move Up button for the used artifact set of buttons
	 */
	private void moveUsedArtifactUp() {
		// Fetch the index of the selected row in the TableView
		int theIndex = theUsedDetailList.getSelectionModel().getSelectedIndex();
		
		// If no row is selected, give a warning and ignore the request
		if (theIndex == -1) {
			Alert alert = new Alert(AlertType.WARNING, "Move Up is only valid when a row has been selected!", ButtonType.OK);
			alert.showAndWait();
			return;
		}
		
		// If the selected row is the top most row, it is not possible to move it up, so give an
		// appropriate warning and ignore the request
		if (theIndex == 0) {
			Alert alert = new Alert(AlertType.WARNING, "Move Up is not valid for the first row!", ButtonType.OK);
			alert.showAndWait();
			return;
		}
		
		// Updating the list is indeed happening
		updatingTheList = true;
		
		// Make the Content Changed Message visible
		showTheContentChangedMessage();
		
		// Given a valid and proper index, produce an observable list from the TableView and swap
		// the selected item with the one above it.
		ObservableList<ECUsedItem> de = theUsedDetailList.getItems();
		ECUsedItem tempDE = de.get(theIndex-1);
		de.set(theIndex-1, de.get(theIndex));
		de.set(theIndex, tempDE);
		
		// Place the updated list back into the table view
		theUsedDetailList.setItems(de);
		
		// Select the entry that was moved up and make sure it is visible
		theUsedDetailList.scrollTo(theIndex-1);
		theUsedDetailList.getSelectionModel().select(theIndex-1);
		
		// Done updating the list
		updatingTheList = false;

	}

	/**********
	 * This event handler processes the Move Dn button for the used artifact set of buttons
	 */
	private void moveUsedArtifactDn() {
		// Fetch the index of the selected row in the TableView
		int theIndex = theUsedDetailList.getSelectionModel().getSelectedIndex();
		
		// If no row is selected, give a warning and ignore the request
		if (theIndex == -1) {
			Alert alert = new Alert(AlertType.WARNING, "Move Dn is only valid when a row has been selected!", ButtonType.OK);
			alert.showAndWait();
			return;
		}
		
		// If the selected row is the bottom row, it is not possible to move it down, so give an
		// appropriate warning and ignore the request
		ObservableList<ECUsedItem> de = theUsedDetailList.getItems();
		if (theIndex == de.size()-1) {
			Alert alert = new Alert(AlertType.WARNING, "Move Up is not valid for the last row!", ButtonType.OK);
			alert.showAndWait();
			return;
		}
		
		// Updating the list
		updatingTheList = true;
		
		// Make the Content Changed Message visible
		showTheContentChangedMessage();		
		
		// Given a valid and proper index, produce an observable list from the TableView and swap
		// the selected item with the one above it.
		ECUsedItem tempDE = de.get(theIndex+1);
		de.set(theIndex+1, de.get(theIndex));
		de.set(theIndex, tempDE);

		// Place the updated list back into the table view
		theUsedDetailList.setItems(de);
		
		// Select the entry that was moved down and make sure it is visible
		theUsedDetailList.scrollTo(theIndex+1);
		theUsedDetailList.getSelectionModel().select(theIndex+1);
		
		// Done updating the list
		updatingTheList = false;
	}
	
	/**********
	 * This event handler processes the Delete button for the used artifact set of buttons
	 */
	private void deleteUsedArtifact() {
		// Fetch the index of the selected row in the TableView
		int theIndex = theUsedDetailList.getSelectionModel().getSelectedIndex();
		
		// If no row is selected, give a warning and ignore the request
		if (theIndex == -1) {
			Alert alert = new Alert(AlertType.WARNING, "Delete is only valid when a row has been selected!", ButtonType.OK);
			alert.showAndWait();
			return;
		}
		
		// Updating the list
		updatingTheList = true;
		
		showTheContentChangedMessage();
		
		// If beyond the end of the list just return (This should never happen,
		// so we have not spent effort raising a warning.)
		ObservableList<ECUsedItem> de = theUsedDetailList.getItems();
		if (theIndex > de.size()-1) return;
		
		// Save the name that is being removed
		String removedName = de.get(theIndex).getItem();
		
		// Remove the item from the TableView list, shift any below up.
		de.remove(theIndex);
		
		// Place the updated list back into the TableView
		theUsedDetailList.setItems(de);
		
		// De-select the TableView so no entry is selected
		theUsedDetailList.getSelectionModel().select(-1);

		// Add the deleted item back into the available list and update that comboBox
		// Get the index from the choice comboBox so we know which set of lists to update
		shiftBack(theUsedArtifact, removedName, artifactBaseSet, artifactsUsedAvailable, artifactsUsedSelected);
		
		// Done updating the list
		updatingTheList = false;
	}
	
	/**********
	 * This event handler processes the Insert button for the produced artifact set of buttons
	 */
	private void insertProducedArtifact() {
		if (updatingTheList) return;
		// This avoids needless repetitive calls due to programmatic requests
		updatingTheList = true;
		
		// Check to be sure there is an item in the ComboBox to insert
		if (theProducedArtifact.getItems().size() < 1 || 
				theProducedArtifact.getSelectionModel().getSelectedIndex() < 0) {
			Alert alert = new Alert(AlertType.WARNING, "Insert is only valid when there's an item to insert!", ButtonType.OK);
			alert.showAndWait();
			updatingTheList = false;
			return;
		}
		
		// Signal that a change has been made and do the change
		showTheContentChangedMessage();
		String str = theProducedArtifact.getValue();
		
		// Perform the change on the appropriate set of lists
		shiftProduced(theProducedDetailList, theProducedArtifact, str, artifactsProducedAvailable, artifactsProducedSelected);
		
		// We are done updating the lists
		updatingTheList = false;	

	}
	
	/**********
	 * This event handler processes the Move Up button for the produced artifact set of buttons
	 */
	private void moveProducedArtifactUp() {
		// Fetch the index of the selected row in the TableView
		int theIndex = theProducedDetailList.getSelectionModel().getSelectedIndex();
		
		// If no row is selected, give a warning and ignore the request
		if (theIndex == -1) {
			Alert alert = new Alert(AlertType.WARNING, "Move Up is only valid when a row has been selected!", ButtonType.OK);
			alert.showAndWait();
			return;
		}
		
		// If the selected row is the top most row, it is not possible to move it up, so give an
		// appropriate warning and ignore the request
		if (theIndex == 0) {
			Alert alert = new Alert(AlertType.WARNING, "Move Up is not valid for the first row!", ButtonType.OK);
			alert.showAndWait();
			return;
		}
		
		// Updating the list is indeed happening
		updatingTheList = true;
		
		// Make the Content Changed Message visible
		showTheContentChangedMessage();
		
		// Given a valid and proper index, produce an observable list from the TableView and swap
		// the selected item with the one above it.
		ObservableList<ECProducedItem> de = theProducedDetailList.getItems();
		ECProducedItem tempDE = de.get(theIndex-1);
		de.set(theIndex-1, de.get(theIndex));
		de.set(theIndex, tempDE);
		
		// Place the updated list back into the table view
		theProducedDetailList.setItems(de);
		
		// Select the entry that was moved up and make sure it is visible
		theProducedDetailList.scrollTo(theIndex-1);
		theProducedDetailList.getSelectionModel().select(theIndex-1);
		
		// Done updating the list
		updatingTheList = false;
	}
	
	/**********
	 * This event handler processes the Movbe Dn button for the produced artifact set of buttons
	 */
	private void moveProducedArtifactDn() {
		// Fetch the index of the selected row in the TableView
		int theIndex = theProducedDetailList.getSelectionModel().getSelectedIndex();
		
		// If no row is selected, give a warning and ignore the request
		if (theIndex == -1) {
			Alert alert = new Alert(AlertType.WARNING, "Move Dn is only valid when a row has been selected!", ButtonType.OK);
			alert.showAndWait();
			return;
		}
		
		// If the selected row is the bottom row, it is not possible to move it down, so give an
		// appropriate warning and ignore the request
		ObservableList<ECProducedItem> de = theProducedDetailList.getItems();
		if (theIndex == de.size()-1) {
			Alert alert = new Alert(AlertType.WARNING, "Move Up is not valid for the last row!", ButtonType.OK);
			alert.showAndWait();
			return;
		}
		
		// Updating the list
		updatingTheList = true;
		
		// Make the Content Changed Message visible
		showTheContentChangedMessage();		
		
		// Given a valid and proper index, produce an observable list from the TableView and swap
		// the selected item with the one above it.
		ECProducedItem tempDE = de.get(theIndex+1);
		de.set(theIndex+1, de.get(theIndex));
		de.set(theIndex, tempDE);

		// Place the updated list back into the table view
		theProducedDetailList.setItems(de);
		
		// Select the entry that was moved down and make sure it is visible
		theProducedDetailList.scrollTo(theIndex+1);
		theProducedDetailList.getSelectionModel().select(theIndex+1);
		
		// Done updating the list
		updatingTheList = false;
	}
	
	/**********
	 * This event handler processes the Delete button for the produced artifact set of buttons
	 */
	private void deleteProducedArtifact() {
		// Fetch the index of the selected row in the TableView
		int theIndex = theProducedDetailList.getSelectionModel().getSelectedIndex();
		
		// If no row is selected, give a warning and ignore the request
		if (theIndex == -1) {
			Alert alert = new Alert(AlertType.WARNING, "Delete is only valid when a row has been selected!", ButtonType.OK);
			alert.showAndWait();
			return;
		}
		
		// Updating the list
		updatingTheList = true;
		
		showTheContentChangedMessage();
		
		// If beyond the end of the list just return (This should never happen,
		// so we have not spent effort raising a warning.)
		ObservableList<ECProducedItem> de = theProducedDetailList.getItems();
		if (theIndex > de.size()-1) return;
		
		// Save the name that is being removed
		String removedName = de.get(theIndex).getItem();
		
		// Remove the item from the TableView list, shift any below up.
		de.remove(theIndex);
		
		// Place the updated list back into the TableView
		theProducedDetailList.setItems(de);
		
		// De-select the TableView so no entry is selected
		theProducedDetailList.getSelectionModel().select(-1);

		// Add the deleted item back into the available list and update that comboBox
		// Get the index from the choice comboBox so we know which set of lists to update
		shiftBack(theProducedArtifact, removedName, artifactBaseSet, artifactsProducedAvailable, artifactsProducedSelected);
		
		// Done updating the list
		updatingTheList = false;

	}
	
	
	/**********
	 * Shift the specified String from the Available List to the Selected List and then set up the 
	 * Table View and the ComboBox to reflect this action
	 * 
	 * @param str	The string to be moved from the Available to the Selected List
	 * @param src	The Available List
	 * @param dst	The Selected List
	 */
	private void shiftRoleToSelected(TableView <RolesItem> r, String str, ObservableList <String> src, 
			ObservableList <String> dst) {
		
		// Scan through the Available List, find the String, and remove it from the list
		for (int ndx = 0; ndx < src.size(); ndx++)
			if (src.get(ndx).equals(str)) {
				src.remove(ndx);
				break;
			}
		
		// Add the String to the end of the Selected List
		dst.add(str);
		
		// Fetch the list of items from the right most Table View GUI items and change it to
		// agree with the new Selected List
		ObservableList<RolesItem> ri = r.getItems();				// Fetch the list
		ri.clear();												// Clear it
		int matchNdx = 0;										// Keep track of the insert index
		for (int i = 0; i < dst.size(); i++) {
			if (dst.get(i).equals(str))
				matchNdx = i;
			// Create a new item entry based on those values
			RolesItem def = new RolesItem(dst.get(i));
			// Add the new item entry at the end of the list
			ri.add(def);
		}
		
		// Insert the new list into the Table View, Scroll to the point of insertion, and select it
		r.setItems(ri);
		r.scrollTo(matchNdx);
		r.getSelectionModel().clearAndSelect(matchNdx);
		
		// Select the first item in the Available list
		theRole.getSelectionModel().clearAndSelect(0);
	}

	
	/**********
	 * Shift the specified String from the Available List to the Selected List and then set up the 
	 * Table View and the ComboBox to reflect this action
	 * 
	 * @param str	The string to be moved from the Available to the Selected List
	 * @param src	The Selected List
	 * @param dst	The Available List
	 */
	private void shiftRoleToAvailable(TableView <RolesItem> r, String str, ObservableList <String> src, 
			ObservableList <String> dst) {

		// We will scan through the Master List which gives the order of items that the user specified
		// The Master List should be the union of the Available List and the Selected List
		int theSize = saveRole.getTheList().size();	// Set up the size of the of both lists 
		int availableIndex = 0;						// Establish the index for the Available List
		int selectIndex = 0;						// Establish the index for the Selected List
		int matchNdx = 0;							// Where the move value went into the ComboBox
		
		System.out.println("str: " + str);
		// The Master list elements are either in the Available List or the Selected List.  The
		// order of the items in the Available and Selected List match the order in the Master List.
		// We go through the Master list starting at the beginning and examine each item for the
		// that list.  We know it will be in one or the other
		for (int masterIndex = 0; masterIndex < theSize; masterIndex++)
			// If the item from the master list matched the specified String, we know it must be in
			// in the Selected List and so we can remove it.
			if (str.equals(saveRole.getTheList().get(masterIndex).getListItemName())) {
				// Remove it from the selected list at this point
				src.remove(selectIndex);
				
				// The item has been found. Add it to the available list at this point.
				dst.add(availableIndex, str);
				matchNdx = availableIndex;
				
				// Once the shift has been performed, there is no need to continue the loop
				break;
			}
			else {
				// If the Specified String does not match the element from the Master list, it
				// must be in either the Selected List or the Available list.
				// 
				// So, we see if it is in the Available list.
				if (availableIndex < dst.size() && r.getItems().get(masterIndex).getItem().contentEquals(dst.get(availableIndex)))
					// If it is in the Available List, we increment that index
					availableIndex++;
				else
					// Otherwise if must be in the Selected List, so we increment that index
					selectIndex++;
			}
		// With the shift back performed, we reset the comboBox and select what we just put back
		theRole.setItems(dst);
		theRole.getSelectionModel().clearAndSelect(matchNdx);
	}

	/**********
	 * This event handler processes the Insert button for the used artifact set of buttons
	 */
	private void insertRole() {
		if (updatingTheList) return;
		// This avoids needless repetitive calls due to programmatic requests
		updatingTheList = true;
		
		// Check to be sure there is an item in the ComboBox to insert
		if (theRole.getItems().size() < 1 || 
				theRole.getSelectionModel().getSelectedIndex() < 0) {
			Alert alert = new Alert(AlertType.WARNING, "Insert is only valid when there's an item to insert!", ButtonType.OK);
			alert.showAndWait();
			updatingTheList = false;
			return;
		}
		
		// Signal that a change has been made and do the change
		showTheContentChangedMessage();
		String str = theRole.getValue();
		
		// Perform the change on the appropriate set of lists
		shiftRoleToSelected(theRolesDetailList, str, rolesAvailable, rolesSelected);
		
		// We are done updating the lists
		updatingTheList = false;	

	}

	/**********
	 * This event handler processes the Move Up button for the used artifact set of buttons
	 */
	private void moveRoleUp() {
		// Fetch the index of the selected row in the TableView
		int theIndex = theRolesDetailList.getSelectionModel().getSelectedIndex();
		
		// If no row is selected, give a warning and ignore the request
		if (theIndex == -1) {
			Alert alert = new Alert(AlertType.WARNING, "Move Up is only valid when a row has been selected!", ButtonType.OK);
			alert.showAndWait();
			return;
		}
		
		// If the selected row is the top most row, it is not possible to move it up, so give an
		// appropriate warning and ignore the request
		if (theIndex == 0) {
			Alert alert = new Alert(AlertType.WARNING, "Move Up is not valid for the first row!", ButtonType.OK);
			alert.showAndWait();
			return;
		}
		
		// Updating the list is indeed happening
		updatingTheList = true;
		
		// Make the Content Changed Message visible
		showTheContentChangedMessage();
		
		// Given a valid and proper index, produce an observable list from the TableView and swap
		// the selected item with the one above it.
		ObservableList<RolesItem> de = theRolesDetailList.getItems();
		RolesItem tempDE = de.get(theIndex-1);
		de.set(theIndex-1, de.get(theIndex));
		de.set(theIndex, tempDE);
		
		// Place the updated list back into the table view
		theRolesDetailList.setItems(de);
		
		// Select the entry that was moved up and make sure it is visible
		theUsedDetailList.scrollTo(theIndex-1);
		theUsedDetailList.getSelectionModel().select(theIndex-1);
		
		// Done updating the list
		updatingTheList = false;

	}

	/**********
	 * This event handler processes the Move Dn button for the used artifact set of buttons
	 */
	private void moveRoleDn() {
		// Fetch the index of the selected row in the TableView
		int theIndex = theRolesDetailList.getSelectionModel().getSelectedIndex();
		
		// If no row is selected, give a warning and ignore the request
		if (theIndex == -1) {
			Alert alert = new Alert(AlertType.WARNING, "Move Dn is only valid when a row has been selected!", ButtonType.OK);
			alert.showAndWait();
			return;
		}
		
		// If the selected row is the bottom row, it is not possible to move it down, so give an
		// appropriate warning and ignore the request
		ObservableList<RolesItem> de = theRolesDetailList.getItems();
		if (theIndex == de.size()-1) {
			Alert alert = new Alert(AlertType.WARNING, "Move Up is not valid for the last row!", ButtonType.OK);
			alert.showAndWait();
			return;
		}
		
		// Updating the list
		updatingTheList = true;
		
		// Make the Content Changed Message visible
		showTheContentChangedMessage();		
		
		// Given a valid and proper index, produce an observable list from the TableView and swap
		// the selected item with the one above it.
		RolesItem tempDE = de.get(theIndex+1);
		de.set(theIndex+1, de.get(theIndex));
		de.set(theIndex, tempDE);

		// Place the updated list back into the table view
		theRolesDetailList.setItems(de);
		
		// Select the entry that was moved down and make sure it is visible
		theRolesDetailList.scrollTo(theIndex+1);
		theRolesDetailList.getSelectionModel().select(theIndex+1);
		
		// Done updating the list
		updatingTheList = false;
	}
	
	/**********
	 * This event handler processes the Delete button for the used artifact set of buttons
	 */
	private void deleteRole() {
		// Fetch the index of the selected row in the TableView
		int theIndex = theRolesDetailList.getSelectionModel().getSelectedIndex();
		
		// If no row is selected, give a warning and ignore the request
		if (theIndex == -1) {
			Alert alert = new Alert(AlertType.WARNING, "Delete is only valid when a row has been selected!", ButtonType.OK);
			alert.showAndWait();
			return;
		}
		
		// Updating the list
		updatingTheList = true;
		
		showTheContentChangedMessage();
		
		// Get the list of items
		ObservableList<RolesItem> de = theRolesDetailList.getItems();
		
		// Save the name that is being removed
		String removedName = de.get(theIndex).getItem();
		
		// Remove the item from the TableView list, shift any below up.
		de.remove(theIndex);
		
		// Place the updated list back into the TableView
		theRolesDetailList.setItems(de);
		
		// De-select the TableView so no entry is selected
		theRolesDetailList.getSelectionModel().select(-1);

		// Add the deleted item back into the available list and update that comboBox
		// Get the index from the choice comboBox so we know which set of lists to update
		shiftRoleToAvailable(theRolesDetailList, removedName, rolesSelected, rolesAvailable);
		
		// Done updating the list
		updatingTheList = false;
	}



	/**********
	 * This nested protected Class is used to populate the rightmost TableView GUI.  It is just a 
	 * list of String objects.  The methods for this class are totally obvious, so I'll not 
	 * document them.
	 * 
	 * @author Lynn Robert Carter
	 *
	 */
	protected class ECUsedItem {
		private String item = "";

		public ECUsedItem(String s) {
			item = s;
		}

		public String toString() {
			return item;
		}

		public String getItem() {
			return item;
		}

		public void setItem(String s) {
			item = s;
		}
	}


	/**********
	 * This nested protected Class is used to populate the rightmost TableView GUI.  It is just a 
	 * list of String objects.  The methods for this class are totally obvious, so I'll not 
	 * document them.
	 * 
	 * @author Lynn Robert Carter
	 *
	 */
	protected class ECProducedItem {
		private String item = "";

		public ECProducedItem(String s) {
			item = s;
		}

		public String toString() {
			return item;
		}

		public String getItem() {
			return item;
		}

		public void setItem(String s) {
			item = s;
		}
	}
	

	/**********
	 * This nested protected Class is used to populate the middle lower TableView GUI.  It is just a 
	 * list of String objects.  The methods for this class are totally obvious, so I'll not 
	 * document them.
	 * 
	 * @author Lynn Robert Carter
	 *
	 */
	protected class RolesItem {
		private String item = "";

		public RolesItem(String s) {
			item = s;
		}

		public String toString() {
			return item;
		}

		public String getItem() {
			return item;
		}

		public void setItem(String s) {
			item = s;
		}
	}


	/**********
	 * This nested protected Class is used to define a specific Effort Category with a set of four
	 * lists.  Each of the four is just a list of String objects, but each time a specific Effort
	 * Category is selected, all four of the Selected lists and all of the dependent lists must be
	 * set up.  The methods for this class are totally obvious, so I'll not document them.
	 * 
	 * @author Lynn Robert Carter
	 *
	 */
	protected class TaskDetail {
		private ObservableList<String> selectedUsedList = FXCollections.observableArrayList();
		private ObservableList<String> selectedProducedList = FXCollections.observableArrayList();
		private ObservableList<String> selectedRoleList = FXCollections.observableArrayList();
		
		public TaskDetail(ObservableList<String> used, ObservableList<String> produced, 
				ObservableList<String> role) {
			// Make shallow copies of each of the four lists 
			selectedUsedList = FXCollections.observableArrayList(used);
			selectedProducedList = FXCollections.observableArrayList(produced);
			selectedRoleList = FXCollections.observableArrayList(role);
		}
		
		public String toString() {
			return "\n     " + selectedUsedList + "\n     " + selectedProducedList;
		}
		
		public ObservableList<String> getSelectedUsedList() {
			return selectedUsedList;
		}

		public void setSelectedUsedList(ObservableList<String> sul) {
			selectedUsedList = sul;
		}
		
		public ObservableList<String> getSelectedProducedList() {
			return selectedProducedList;
		}

		public void setSelectedProducedList(ObservableList<String> spl) {
			selectedProducedList = spl;
		}
		
		public ObservableList<String> getSelectedRoleList() {
			return selectedRoleList;
		}

		public void setSelectedRoleList(ObservableList<String> srl) {
			selectedRoleList = srl;
		}

	}
	
	
	/**********
	 * This is is a debugging display a list method
	 * 	

	private String display(ObservableList <String> src) {
		String result = "[";
		for (int ndx = 0; ndx < src.size(); ndx++)
			result += src.get(ndx) + (ndx < src.size()-1 ? "," : "");
		return result + "]";
	}
	 */
}
