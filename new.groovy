import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.jira.issue.IssueManager;
import com.atlassian.jira.issue.Issue;
import com.atlassian.jira.issue.MutableIssue;
import com.atlassian.jira.user.ApplicationUser;
import com.atlassian.jira.bc.issue.IssueService;
import com.atlassian.jira.issue.IssueInputParameters;
import com.atlassian.jira.util.ErrorCollection;
import com.atlassian.jira.project.ProjectManager;
import com.atlassian.jira.project.Project
import com.atlassian.jira.bc.project.component.ProjectComponent
import com.atlassian.jira.issue.fields.CustomField
import com.onresolve.scriptrunner.canned.jira.admin.CopyProject
import com.atlassian.jira.bc.projectroles.ProjectRoleService
import com.atlassian.jira.security.roles.ProjectRoleActor
import com.atlassian.jira.util.SimpleErrorCollection
import com.atlassian.jira.security.roles.ProjectRoleManager




def projectManager = ComponentAccessor.getProjectManager()
def customFieldManager = ComponentAccessor.getCustomFieldManager()
def issueManager = ComponentAccessor.getIssueManager()

ApplicationUser adminUser = ComponentAccessor.getUserManager().getUserByName("admin")

// Comment below line, when applying the script
def issue = issueManager.getIssueObject("TIP-5040")

// Projectname
def cField = customFieldManager.getCustomFieldObject("customfield_10287") 

//Project Creation: Automated /  Manual / None
def projectcreation = customFieldManager.getCustomFieldObject("customfield_11600") 
String projectcreationFieldValue = issue.getCustomFieldValue(projectcreation) as String

log.error "projectcreationFieldValue : " + projectcreationFieldValue

String cFieldValue = issue.getCustomFieldValue(cField)

def shouldApplyComponent = false

def businessUser = new HashMap<String,String>();

businessUser.put('GCS','Yes')
businessUser.put('GES','Yes')
businessUser.put('GNS','No')
businessUser.put('GPG','Yes')
businessUser.put('GSG','Yes')
businessUser.put('GTNOG','No')
businessUser.put('HR','Yes')
businessUser.put('IT','Yes')

def productType = new HashMap<String,String>();
productType.put('PCR','No')
productType.put('Customer','No')
productType.put('Customer Impacting','No')
productType.put('Internal/Enterprise','Yes')

def cfBusinessUser = customFieldManager.getCustomFieldObject('customfield_14502')
def cfProductType = customFieldManager.getCustomFieldObject('customfield_14503')

def businessUserValue   = issue.getCustomFieldValue(cfBusinessUser) as String
def productTypeValue    = issue.getCustomFieldValue(cfProductType) as String

log.warn(businessUserValue)
log.warn(productTypeValue)

def businessUserValueFromMap    = businessUser.get(businessUserValue)
def productTypeValueFromMap     = productType.get(productTypeValue);

log.warn"BU value from Map: "+businessUserValueFromMap
log.warn"PT value from Map: "+productTypeValueFromMap

String[] projKeyArr = issue.getKey().split('-');

def newProjectKey = projKeyArr[0] + projKeyArr[1];

if(businessUserValueFromMap.equals('Yes') && productTypeValueFromMap.equals('Yes')){
    shouldApplyComponent = true;
}

if (projectcreationFieldValue.equals("Automated") || projectcreationFieldValue == null) {
    
	if (cFieldValue != null) {
        
        log.warn('NEW PROJECT NAME : '+projKeyArr[0] + projKeyArr[1])
		
        if (ComponentAccessor.getProjectManager().getProjectObjByKey(projKeyArr[0] + projKeyArr[1]) == null) {
		
            copyProject(newProjectKey);
		}
	}
} else {
	log.error "------------------else Manual--------------------"
}

def copyProject(String projectKey){

    log.warn('Creating new project in Copy Project Function')
    log.warn('With key : '+projectKey)
}
