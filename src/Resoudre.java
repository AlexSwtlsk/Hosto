import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.SocketException;


public abstract class Resoudre {

	protected CA ca;
	protected PrintWriter out;
	protected String option;
	protected BufferedReader in;
	/*
	 * Fonction résoudre, appelée par CA
	 */
	public Resoudre(String option, PrintWriter out, BufferedReader in){
		this.option=option;
		this.out=out;
		this.in=in;
	}

	public abstract void resout(CA t) throws IOException, SocketException;
	
	
	
}
