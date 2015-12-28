package com.oose2015.group15.invest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.gson.Gson;

import org.junit.Test;

import com.oose2015.group15.invest.jsonobject.ClientProfileJson;
import com.oose2015.group15.invest.jsonobject.DeprecatedViewJson;
import com.oose2015.group15.invest.jsonobject.LoginResponse;
import com.oose2015.group15.invest.jsonobject.SignUpInfo;

public class JsonObjectTest {

	@Test
	public void clientProfileTest() {
		/*List<String> objectives = Arrays.asList(new String[]{"money", "money", "money"});
		ClientProfileJson c = new ClientProfileJson(objectives, "time", "risk", "adjusted",
				"liquid", true, 1);
		String json = new Gson().toJson(c);
		System.out.println(json);
		
		json = "{\"investmentObjectives\":[\"money\",\"money\",\"money\"],\"timeHorizon\":\"time\",\"riskTolerance\":\"risk\",\"adjustedRisk\":\"adjusted\",\"liquidityNeeds\":\"liquid\",\"hasDependents\":true,\"taxBracket\":1}";
		ClientProfileJson test = new Gson().fromJson(json, ClientProfileJson.class);
		System.out.println(test);*/
		ClientProfileJson p = new ClientProfileJson(true, true, false, false, false,
				1, 2, 1, true, 30);
		String json = new Gson().toJson(p);
		System.out.println(json);
		
		SignUpInfo s = new SignUpInfo("kathleen", "secret", p);
		json = new Gson().toJson(s);
		System.out.println(json);
		
		List<DeprecatedViewJson> recs = new ArrayList<DeprecatedViewJson>();
		recs.add(new DeprecatedViewJson("SYMC", "Symantec", "Technology"));
		
		LoginResponse lr = new LoginResponse(1, s, Arrays.asList(new String[]{"AAPL", "MSFT"}),
				recs);
		json = new Gson().toJson(lr);
		System.out.println(json);
	}
}
