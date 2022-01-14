import com.atlassian.jira.component.ComponentAccessor
import com.atlassian.jira.issue.context.IssueContext
import com.atlassian.jira.issue.context.IssueContextImpl
import com.atlassian.jira.issue.fields.config.manager.PrioritySchemeManager


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


def cfBusinessUser = customFieldManager.getCustomFieldObject()
def cfProductType = customFieldManager.getCustomFieldObject()

def businessUserValue   = customFieldManager.getValue(cfBusinessUser,issue);
def productTypeValue    = customFieldManager.getValue(cfProductType,issue);

def businessUserValueFromMap    = businessUser.get(businessUserValue)
def productTypeValueFromMap     = productType.get(productTypeValue);

def shouldApplyComponent = false;

if(businessUserValueFromMap.equals('Yes') && productTypeValueFromMap.equals('Yes')){
    shouldApplyComponent = true;
}





