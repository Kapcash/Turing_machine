package data;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * The Machine contains all the loaded data from configuration file
 * Machine is a singleton. 
 */
public class Machine {
	
	/**
	 * List of all transitions in the program
	 * Diplayed in a table on the GUI
	 */
	private ArrayList<Transition> trans;

	/**
	 * List of character the user can use for the input word
	 */
	private ArrayList<Character> machine_alphabet;

	/**
	 * List of character the machine can use to write down on tape
	 */
	private ArrayList<Character> tape_alphabet;
	
	/**
	 * The list of stopping states (for step by step)
	 */
	private ArrayList<String> stop_states;
	
	/**
	 * List of all the configurations during the programm
	 */
	private ArrayList<String> configurations;

	/**
	 * The initial state, within the machine starts
	 */
	private String init_state;

	/**
	 * The state where the word is accepted
	 */
	private String accept_state;

	/**
	 * The state where the word is rejected
	 */	
	private String reject_state;
	
	/**
	 * Return the Machine, after checking all configuration file's syntax
	 * @param path The path of configuration file
	 * @return Return the instance of the Machine
	 * @throws Exception If an error occured in the syntax checking
	 */
	public static Machine getMachine(String path) throws Exception{
		TuringSyntaxe ts = TuringSyntaxe.getInstance();
		return ts.checkAndCreate(path);		
	}

	public Machine (ArrayList<Transition> trans,
					ArrayList<Character> machine_alphabet,
					ArrayList<Character> tape_alphabet,
					ArrayList<String> stop_states,
					String init_state,
					String accept_state,
					String reject_state
					){

		this.machine_alphabet = machine_alphabet;
		this.tape_alphabet = tape_alphabet;
		this.trans = trans;
		this.init_state = init_state;
		this.accept_state = accept_state;
		this.reject_state = reject_state;
		this.stop_states = stop_states;		
		configurations = new ArrayList<String>();

	}

	/**
	 *	For given state and symbol, return the unique associated Transition
	 *	@param redChar the current symbol
	 *	@param state the current state
	 *	@return Return the next Transition object to run from the given arguments
	 */
	public Transition getTransitionFromSym(Character redChar, String state){
		Transition ret = null;
		Iterator<Transition> it = trans.iterator();
		while(it.hasNext() && ret == null){
			Transition index = it.next();
			if(index.getCurrentState().equals(state) && index.getReadSymbole().equals(redChar)){
				ret = index;
			}
		}
		if(ret == null) throw new NoSuchElementException("Etat "+state+" symbole "+redChar+" introuvable");
		return ret;
	}

	/**
	 *	Indicates if the state is the accept state 	
	 *	@param state The state to check
	 *	@return Return true if the state is the accept one
	 */
	public boolean isAccept(String state){
		boolean ret = false;
		if (accept_state.equals(state)) {
			ret = true;
		}
		return ret;
	}

	/**
	 *	Indicates if the state is the reject state 	
	 *	@param state The state to check
	 *	@return Return true if the state is the reject one
	 */
	public boolean isReject(String state){
		boolean ret = false;
		if (reject_state!= null && reject_state.equals(state)) {
			ret = true;
		}
		return ret;
	}
	
	/**
	 * Indicates if the state must stop the machine
	 * @param state The state to check
	 * @return Return true if the state is stopping
	 */
	public boolean isStop(String state){
		boolean ret = false;
		if(stop_states.contains(state)){
			ret = true;
		}
		return ret;
	}
	
	public void addConfig(String conf){
		configurations.add(conf);
	}
	
	
	
	/* --- Getters --- */

	public ArrayList<Transition> getTrans() {
		return trans;
	}
	
	public ArrayList<Character> getMachineAlpha() {
		return machine_alphabet;
	}
	
	public ArrayList<Character> getTapeAlpha() {
		return tape_alphabet;
	}
	
	public String getInitState() {
		return init_state;
	}
	
	public String getAcceptState() {
		return accept_state;
	}
	
	public String getRejectState() {
		return reject_state;
	}

	public ArrayList<String> getStopStates() {
		return stop_states;
	}
	
	public void resetConfigurations() {
		configurations = new ArrayList<String>();
	}
	
	public ArrayList<String> getConfigurations() {
		return configurations;
	}
}
