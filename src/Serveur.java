import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;


public class Serveur extends Thread{

	private static int port;
	private static int strategie;
	private Socket s;
	
	public Serveur(int port,int strategie){
		this.port=port;
		this.strategie=strategie;
	}
	public Serveur(Socket s){
		this.s=s;
	}

	public void run(){
		int nbVilleClient=0,nbRouteClient=0;
		try{
			BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
			PrintWriter out = new PrintWriter(s.getOutputStream());
			String ligne="";
			ligne=in.readLine();
			System.err.println("Recu par le client: "+ligne);

			String []nb=ligne.split(" ");	
			if(nb[0].equals("wannaPlay") && nb[1].equals("?")){
			    try{
			    	nbVilleClient=Integer.parseInt(nb[2]);
				nbRouteClient=Integer.parseInt(nb[3]);
				}
			    catch (NumberFormatException exception){
			    	System.err.println("Probleme de lecture de la ville et/ou de la route");
				    in.close();
				    out.close();
				    s.close();
			    }
			}
			    System.err.println("Le nombre de ville du client: "+nbVilleClient);
			    System.err.println("Le nombre de route du client: "+nbRouteClient);

			    out.println("OK");
			    out.flush();
				File temp = new File("temp.ca");
				System.err.println("le fichier "+temp+" a ete cree");
				PrintWriter writer = new PrintWriter((new BufferedWriter(new FileWriter(temp))));
				
				while(!((ligne=in.readLine()).equals("finDeTransmission"))){
					//System.err.println(ligne);
					writer.println(ligne);
				}
				writer.close();
			
				CA serveur= new CA ("temp.ca",new Strategie1("multi",out,in));

				if(nbVilleClient!=serveur.getNbVille() || nbRouteClient!=serveur.getNbRoute())
				{
				    out.println("KO");
				    out.flush();
				    in.close();
				    out.close();
				    s.close();
				    System.err.println("erreur dans le nombre de ville/route");
				    System.exit(1);
			    }
				else 
				    {
					out.println("OK");
					out.flush();
				    }
				switch(strategie){
		    		case 1:
		    			serveur = new CA("temp.ca", new Strategie1("multi",out,in));
		    			break;
		    		case 2:
		    			serveur = new CA("temp.ca", new Strategie2("multi",out,in));
		    			break;
		    		default:
		    			System.err.println("Erreur inattendue dans le lancement de strategie");
		    			System.exit(1);
				    }
				serveur.resoudre();
				
				in.close();
				out.close();
				s.close();
		}		
		catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public static void main ( String[]args) throws IOException{
		try{
			ServerSocket passiveSocket = new ServerSocket(port,strategie);
			while(true){
				Socket activeSocket = passiveSocket.accept();
				Serveur s=new Serveur(activeSocket);
				s.start();
			}
		}
		catch(BindException e){
			System.err.println("Port deja utilise !");
			System.exit(1);
		}
	}
}
