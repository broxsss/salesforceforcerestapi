package com.force.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.testng.Assert.assertNotNull;

import java.util.List;



public class JodaTimeSample {

    ForceApi api;

    @BeforeClass
    public void init() {
        ObjectMapper om = new ObjectMapper();
        om.registerModule(new JodaModule());
        api = new ForceApi(new ApiConfig()
                .setUsername(Fixture.get("username"))
                .setPassword(Fixture.get("password"))
                .setObjectMapper(om));
    }

    @Test
    public void testTypedQuery() {
        List<JodaTimeAccount> result = api.query("SELECT name, createddate FROM Account",JodaTimeAccount.class).getRecords();
        assertNotNull(result.get(0).getName());
        assertNotNull(result.get(0).getCreatedDate());
        System.out.println("Account "+result.get(0).getName()+" was created in "+result.get(0).getCreatedDate().monthOfYear().getAsText());
    }
}
