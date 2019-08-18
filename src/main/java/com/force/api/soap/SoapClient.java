package com.force.api.soap;

import java.lang.reflect.Field;
import java.util.List;
import com.force.api.ForceApi;
import com.sforce.soap.partner.Connector;
import com.sforce.soap.partner.DeleteResult;
import com.sforce.soap.partner.Error;
import com.sforce.soap.partner.GetUserInfoResult;
import com.sforce.soap.partner.MergeRequest;
import com.sforce.soap.partner.MergeResult;
import com.sforce.soap.partner.PartnerConnection;
import com.sforce.soap.partner.SaveResult;
import com.sforce.soap.partner.StatusCode;
import com.sforce.soap.partner.sobject.SObject;
import com.sforce.ws.ConnectionException;
import com.sforce.ws.ConnectorConfig;
import org.apache.commons.collections4.ListUtils;


/**
 * Simple Salesforce SOAP API client. Uses WDSL generated from CMT 11.0 release.
 *
 * @author akshay
 */
public class SoapClient {
    

    private PartnerConnection connection;

    public SoapClient(String username, String password) {
        ConnectorConfig config = new ConnectorConfig();
        config.setUsername(username);
        config.setPassword(password);


        try {
            connection = Connector.newConnection(config);
        } catch (ConnectionException e) {
           e.printStackTrace();
        }
    }

    
    public void delete(List<String> ids) {

        if (ids.size() > 199) {
            // Break the list into groups of 199 or less and call delete() separately for each
            List<List<String>> idLists = ListUtils.partition(ids, 199);

            for (List<String> list : idLists) {
                delete(list);
            }

            // Done with all the IDs, no need to continue
            return;
        }

        DeleteResult[] deleteResults = null;

        String[] idArray = ids.toArray(new String[0]);

        try {
            deleteResults = connection.delete(idArray);
        } catch (ConnectionException e) {
            e.printStackTrace();
        }

        // Process the results array and log out ID-by-ID results.
        for (int index = 0; index < deleteResults.length; index++) {
            if (deleteResults[index].isSuccess() ) {
               System.out.println("SOAP API: Deleted SObject '{}'."+ idArray[index]);
            } else {
                // So the call failed. Log out the error if something was returned
                if (deleteResults[index].getErrors().length > 0) {

                    Error error = deleteResults[index].getErrors()[0];

                    // If the object had already been deleted this is really not an error so log it differently
                    if (error.getStatusCode() == StatusCode.ENTITY_IS_DELETED) {
                        System.out.println("SOAP API: SObject '{}' was already deleted."+ idArray[index]);
                    } else {
                    	 System.out.println("SOAP API: Could not delete SObject '{}': {}."+ idArray[index]+ error.getMessage());
                    }
                }
            }
        }
    }


    public boolean mergeSObjects(SObject masterObject, List<String> contactIds) {

    	 System.out.println("Merge SObjects ({}) '{}' into SObject id '{}'."+ masterObject.getType()+ contactIds+ masterObject.getField("Id"));

        if (masterObject.getType() == null) {
           
        }

        if (contactIds.isEmpty()) {
           
        }

        if (contactIds.size() > 2) {
           
        }

        MergeRequest mergeRequest = new MergeRequest();

        mergeRequest.setMasterRecord(masterObject);
        mergeRequest.setRecordToMergeIds((String[]) contactIds.toArray());

        MergeResult mergeResult = null;

        try {
            mergeResult = connection.merge(new MergeRequest[] { mergeRequest })[0];
        } catch (ConnectionException e) {
           
        }

        if (!mergeResult.isSuccess()) {
            for (com.sforce.soap.partner.Error error : mergeResult.getErrors()) {
                
            }
        }

        System.out.println("Merge result: {}"+ mergeResult.isSuccess());
        System.out.println("Merged Record Ids: {}" + mergeResult.getMergedRecordIds());

        return mergeResult.isSuccess();
    }
    
    /**
     * Get User Info for the currently logged in user
     * @return SFDC UserInfo
     * @throws ConnectionException 
     */
    public GetUserInfoResult getUserInfo() throws ConnectionException {
        try {
            return connection.getUserInfo();
        } catch (ConnectionException e) {
        }
        return connection.getUserInfo();
    }

}
