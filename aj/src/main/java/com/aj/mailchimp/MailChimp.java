package com.aj.mailchimp;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;

import com.ecwid.maleorang.MailchimpClient;
import com.ecwid.maleorang.MailchimpObject;
import com.ecwid.maleorang.annotation.Field;
import com.ecwid.maleorang.method.v3_0.lists.members.EditMemberMethod;
import com.ecwid.maleorang.method.v3_0.lists.members.GetMembersMethod;
import com.ecwid.maleorang.method.v3_0.lists.members.MemberInfo;

@Controller
@ComponentScan("com.aj")
public class MailChimp{
	@Autowired
	protected SessionFactory sessionFactory;

	protected Session getSession(){
		return sessionFactory.getCurrentSession();
	}

	private static final String API_KEY="MAILCHIMP_KEY";
	private static final String LIST_ID="LIST_ID";
	private static final String EMAIL="EMAIL_TO_UNSUBSCRIBE";
	//private final MailChimp mailChimpClient=new MailChimp();
	
	public static class MergeVars extends MailchimpObject {

		@Field
		public String EMAIL, FNAME, LNAME;
	
		public MergeVars() { 
		} 
	
		public MergeVars(String email, String fname, String lname) { 
		this.EMAIL = email;
		this.FNAME = fname;
		this.LNAME = lname;
		} 
	} 
	
	public static void main(String[] args) throws Exception { 
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		
		System.out.print("Enter api key: ");
		String apikey = in.readLine().trim();
		
		System.out.print("Enter list id: ");
		String listId = in.readLine().trim();
		
		System.out.print("Enter email: ");
		String email = in.readLine().trim();
		
		MailchimpClient client = new MailchimpClient(apikey);
		try {
			EditMemberMethod.CreateOrUpdate method = new EditMemberMethod.CreateOrUpdate(listId, email);
			method.status = "subscribed";
			method.merge_fields = new MailchimpObject();
			method.merge_fields.mapping.put("FNAME", "***");
			method.merge_fields.mapping.put("LNAME", "***");
		
			MemberInfo member = client.execute(method);
			System.err.println("The user has been successfully subscribed: " + member);

		GetMembersMethod method1 = new GetMembersMethod(listId);
		client.execute(method1);

			} finally {
				client.close();
			}
		
/*		// Subscribe a person 
		MailChimp subscribeMethod = new MailChimp();
		subscribeMethod. .apikey = apikey;
		subscribeMethod.id = listId;
		subscribeMethod.email = new Email();
		subscribeMethod.email.email = email;
		subscribeMethod.double_optin = false;
		subscribeMethod.update_existing = true;
		subscribeMethod.merge_vars = new MergeVars(email, "Vasya", "Pupkin");
		mailChimpClient.execute(subscribeMethod);
		
		System.out.println(email+" has been successfully subscribed to the list. Now will check it...");
	
		// check his status 
		MemberInfoMethod memberInfoMethod = new MemberInfoMethod();
		memberInfoMethod.apikey = apikey;
		memberInfoMethod.id = listId;
		memberInfoMethod.emails = Arrays.asList(subscribeMethod.email);
	
		MemberInfoResult memberInfoResult = mailChimpClient.execute(memberInfoMethod);
		MemberInfoData data = memberInfoResult.data.get(0);
		System.out.println(data.email+"'s status is "+data.status);
	
		// Close http-connection when the MailChimpClient object is not needed any longer. 
		// Generally the close method should be called from a "finally" block. 
		mailChimpClient.close();*/
	}
}