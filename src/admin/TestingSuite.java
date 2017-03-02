package admin;


import java.net.MalformedURLException;
import java.util.LinkedList;
import java.util.List;

import org.imt.atlantique.sss.kms.connectors.ws.ExecutionException_Exception;
import org.imt.atlantique.sss.kms.connectors.ws.InterruptedException_Exception;
import org.imt.atlantique.sss.kms.connectors.ws.RequestInvocationException_Exception;
import org.jaatadia.soap_interaction.RequestManager;
import org.jaatadia.soap_interaction.RequestWrapper;
import org.jaatadia.soap_interaction.ResultWrapper;

import data.SocialData;


public class TestingSuite {
	
	
	static void Main(String[] argv){
		try {
			TestingSuite t = new TestingSuite();
			t.init();
			t.test();
		} catch (MalformedURLException | ExecutionException_Exception | InterruptedException_Exception | RequestInvocationException_Exception e) {
			e.printStackTrace();
		}
	}
	
	



	private SocialData sd;
	RequestManager reqM;
	
	public TestingSuite() throws MalformedURLException, ExecutionException_Exception, InterruptedException_Exception, RequestInvocationException_Exception {
		
		reqM = new RequestManager("http://192.168.223.129:8080/CrudService/CrudWS?WSDL");
		
	}
	
	private void init() throws ExecutionException_Exception, InterruptedException_Exception, RequestInvocationException_Exception {
		List<RequestWrapper> req = new LinkedList<RequestWrapper>();
		req.add(new RequestWrapper("IoTF2B506Project","createDrug").add("Drug","tafirol"));
		req.add(new RequestWrapper("IoTF2B506Project","createPerson").add("User","mark").add("Circle", "circle1"));
		req.add(new RequestWrapper("IoTF2B506Project","createTwitterAccount").add("User","mark")
				.add("consumerKey", "")
				.add("consumerSecret", "")
				.add("accessToken", "")
				.add("accesTokenSecret", "")
		);
		req.add(new RequestWrapper("IoTF2B506Project","createPrescription").add("User","Mark").add("Prescription","pmark1").add("Drug", "tafirol").add("Day","Sun").add("Hour","22-00-00"));
		
		System.out.println("Initialsing");
		for (RequestWrapper r : req){
			System.out.println(r.toString());
			reqM.invokeCreate(r);
		}
		
	}
	
	public void test() throws ExecutionException_Exception, InterruptedException_Exception, RequestInvocationException_Exception{
		System.out.println("Testing");
		sd = new SocialData("mark");
		System.out.println(sd.getAccessToken());
		System.out.println(sd.getAccessTokenSecret());
		System.out.println(sd.getconsumerSecret());
		System.out.println(sd.getConsumerKey());
		
		List<RequestWrapper> req = new LinkedList<RequestWrapper>();
		req.add(new RequestWrapper("IoTF2B506Project","getPrescriptions").add("User","Mark"));
		req.add(new RequestWrapper("IoTF2B506Project","getPoints").add("User","Mark"));
		
		for (RequestWrapper r : req){
			System.out.println(r.toString());
			ResultWrapper res = reqM.invokeRead(r);
			System.out.println(res.toString());
		}
		
	}
}