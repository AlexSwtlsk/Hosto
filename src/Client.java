import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;
import java.util.Random;


public class Client {

    private static String host;
	private static int port;
	private static int nbVille;
	private static int nbRoute;
	private static int strategie;
	private static String nomFichier;
	private Socket s;
	private static int bestScore;
	private static int bestScoreServeur;
	
	public Client(String host,int port,int nbVille, int nbRoute,int strategie,String nomFichier){
		this.host=host;
		this.port=port;
		this.nbVille=nbVille;
		this.nbRoute=nbRoute;
		this.strategie=strategie;
		this.nomFichier=nomFichier;
	}
    
    public Client(Socket s){
	    this.s=s;
    }


	
	void run(){
        try{
            try{
        	Socket activeSocket = new Socket(host,port);
            BufferedReader in = new BufferedReader(new InputStreamReader(activeSocket.getInputStream()));
            
            PrintWriter out = new PrintWriter(activeSocket.getOutputStream());
            BufferedReader buf = new BufferedReader(new InputStreamReader(System.in));
			
            String outLigne="wannaPlay ? "+nbVille+" "+nbRoute;
			out.println(outLigne);
			out.flush();
			
			String inLigne="";
			inLigne=in.readLine();
			
			if(inLigne.equals("OK")){
				String line="";
				
				FileReader fr = new FileReader(nomFichier);
				BufferedReader br = new BufferedReader(fr);
				
				while((line=br.readLine())!=null){
				    out.println(line);
				    out.flush();				    	
				}
				
				out.println("finDeTransmission");
				out.flush();
				System.err.println("la transmission du fichier "+nomFichier+" s'est bien passee");
			}
			
			if((inLigne=in.readLine()).equals("KO")){
				System.err.println("Ce que je recois du serveur: "+inLigne);
				System.exit(1);
			}
			

		    CA clientCA = new CA(nomFichier,new Strategie2("multi",out,in));
		    
			switch(strategie){
	    		case 1:
	    			clientCA = new CA(nomFichier, new Strategie1("multi",out,in));
	    			break;
	    		case 2:
	    			clientCA = new CA(nomFichier, new Strategie2("multi",out,in));
	    			break;
	    		default:
	    			System.err.println("Erreur Client inattendue dans le lancement de strategie");
	    			System.exit(1);
			    }
			bestScore=bestScoreServeur=clientCA.getNbVille();
			System.err.println("Client resout");
			clientCA.resoudre();
			
			System.exit(0);
            }catch(ConnectException e){
            	System.err.println("Serveur hote indisponible.");
            	System.exit(1);
            }
			
        }catch(Exception e){
			e.printStackTrace();
		}
	}
	

	
	
}
