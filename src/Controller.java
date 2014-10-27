

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
/**
 * Servlet implementation class Controller
 */
@WebServlet(name="Controller",urlPatterns={"/","/home","/prepare","/checkPayment","/releaseOrder"})
public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static String HOST = "localhost";
       
	ClientConfig config = new DefaultClientConfig();
	Client client = Client.create(config);
	WebResource service = client.resource(getBaseURI());
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Controller() {
        super();
        
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Error Handling e.g. paying when payment done etc.
		// TODO OPTIONS couldn't figure out how to do it
		List<Order> allOrds = service.path("rest").path("orders").accept(
				MediaType.APPLICATION_XML).header("Auth", "abc123").get(new GenericType<List<Order>>(){});
		List<Order> ords = new ArrayList<Order>();
		for(Order o : allOrds){
			if(o.getReleased() == null)
				ords.add(o);
		}
		
		if(request.getParameter("preparing") != null){
			
			Order o = service.path("rest").path("orders/"+request.getParameter("id")).accept(
					MediaType.APPLICATION_XML).header("Auth", "abc123").get(Order.class);
			
			o.setStatus("1");
			
			service.path("rest").path("orders")
			.path(o.getId()).accept(MediaType.APPLICATION_XML).header("Auth","abc123")
			.put(ClientResponse.class, o);
			response.sendRedirect("home?status=Marked%20Preparing&id="+request.getParameter("id"));
		}
		
		else if(request.getParameter("checkPay") != null){
			ClientResponse res = service.path("rest").path("payments/"+request.getParameter("id")).accept(
					MediaType.APPLICATION_XML).header("Auth", "def456").get(ClientResponse.class);
			if(res.getStatus() == 400)
				response.sendRedirect("home?status=Not%20Paid%20Yet&id="+request.getParameter("id"));
			else
				response.sendRedirect("home?status=Paid&id="+request.getParameter("id"));
		}
		
		else if(request.getParameter("release") != null){
			Order o = service.path("rest").path("orders/"+request.getParameter("id")).accept(
					MediaType.APPLICATION_XML).header("Auth", "abc123").get(Order.class);
			
			o.setReleased("1");
			
			service.path("rest").path("orders")
			.path(o.getId()).accept(MediaType.APPLICATION_XML).header("Auth","abc123")
			.put(ClientResponse.class, o);
			
			response.sendRedirect("home?status=Released&id="+request.getParameter("id"));
		}
	
		else{
			request.setAttribute("id", request.getParameter("id"));
			request.setAttribute("status", request.getParameter("status"));
			request.setAttribute("orders", ords);
			request.getRequestDispatcher("home.jsp").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response);
	}
	
	private static URI getBaseURI() {
		return UriBuilder
				.fromUri("http://" + HOST + ":8080/CoffeeService")
				.build();
	}

}
