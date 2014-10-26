

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Random;

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
import com.sun.jersey.api.representation.Form;
/**
 * Servlet implementation class Controller
 */
@WebServlet(name="Controller",urlPatterns={"/","/home","/prepare"})
public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private List<Order> orders;
       
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
		List<Order> ords = service.path("rest").path("orders").accept(
				MediaType.APPLICATION_XML).header("Auth", "abc123").get(new GenericType<List<Order>>(){});
		
		if(request.getParameter("prepare") != null){
			//set preparing to 1
			response.sendRedirect("home?status=Marked%20Preparing");
		}
		else if(request.getParameter("checkPay") != null){
			//check paid
			//if(paid)
				response.sendRedirect("home?status=Paid");
			//else
				response.sendRedirect("home?status=Not%20Paid");
		}
		else if(request.getParameter("release") != null){
			//release process
			response.sendRedirect("home?status=Released");
		}
	
		else{
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
		return UriBuilder.fromUri(
				"http://localhost:8080/CoffeeService").build();
	}

}
