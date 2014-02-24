class TestEnsemble{
    
    public static void main (String[] args)
    {
    	Ensemble e1 = new Ensemble (7);
    	e1.add(1);e1.add(2); e1.add(5);
    	System.out.println(e1.cardinal());
    	e1.affiche();
    	Ensemble e2 = new Ensemble (7);
    	e2.add(1);e2.add(2); e2.add(5);
    	e2.affiche();
	
    	Ensemble e3=new Ensemble(2);
    	e3.add(1);
    	e3.affiche();
    	e1.union(e2);
    	if(e1.inclus(e1)) 
    		System.out.println("vrai");
	
    }
}
